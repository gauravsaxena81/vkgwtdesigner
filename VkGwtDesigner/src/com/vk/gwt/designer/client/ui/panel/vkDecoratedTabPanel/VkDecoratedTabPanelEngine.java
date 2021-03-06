/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.gwt.designer.client.ui.panel.vkDecoratedTabPanel;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.UndoHelper;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDecoratedTabPanelEngine extends VkAbstractWidgetEngine<VkDecoratedTabPanel> {
	private final String DISABLE_TAB = "Disable a Tab";
	private final String ENABLE_TAB = "Enable a Tab";
	@Override
	public VkDecoratedTabPanel getWidget() {
		VkDecoratedTabPanel widget = new VkDecoratedTabPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		optionList.remove(HasVkEnabled.NAME);
		optionList.add(ENABLE_TAB);
		optionList.add(DISABLE_TAB);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDecoratedTabPanel widget = (VkDecoratedTabPanel)invokingWidget;
		if(attributeName.equals(ENABLE_TAB))
			enableTab(widget);
		else if(attributeName.equals(DISABLE_TAB)) 
			disableTab(widget);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	
	private void enableTab(final VkDecoratedTabPanel widget) {
		ListBox listBox = new ListBox();
		for(int i = 0, len = widget.getTabCount(); i < len; i++)
			listBox.addItem(widget.getTabHeaderText(i), Integer.toString(i));
		listBox.setWidth("300px");
		VkDesignerUtil.showAddListDialog("Add Tab number to enable", listBox, new IDialogCallback() {
			@Override
			public void save(String text) {
				final int tabNumber = Integer.parseInt(text);
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						widget.setTabEnabled(tabNumber, true);
					}}, new Command() {
					@Override
					public void execute() {
						widget.setTabEnabled(tabNumber, false);
					}
				});
			}
		});		
	}
	private void disableTab(final VkDecoratedTabPanel widget) {
		ListBox listBox = new ListBox();
		for(int i = 0, len = widget.getTabCount(); i < len; i++)
			listBox.addItem(widget.getTabHeaderText(i), Integer.toString(i));
		listBox.setWidth("300px");
		VkDesignerUtil.showAddListDialog("Add Tab number to disable", listBox, new IDialogCallback() {
			@Override
			public void save(String text) {
				final int tabNumber = Integer.parseInt(text);
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						widget.setTabEnabled(tabNumber, false);
						widget.selectTab(tabNumber);
					}}, new Command() {
					@Override
					public void execute() {
						widget.setTabEnabled(tabNumber, true);
					}
				});
			}
		});		
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> widgetList = ((IVkPanel)widget).iterator();
			int widgetIndex = 0;
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{headerHtml:'").append(((VkDecoratedTabPanel)widget).getTabHeaderHtml(widgetIndex)).append("'");
				buffer.append(",enabled:").append(((VkDecoratedTabPanel)widget).getTabEnabled(widgetIndex++));
				buffer.append(",child:");
				if(child instanceof IVkWidget)
					buffer.append(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	@Override
	protected void serializeAttributes(StringBuffer buffer, Widget widgetSource)
	{
		if(!widgetSource.getStyleName().isEmpty())
			buffer.append(",className:'" + widgetSource.getStyleName() + "'");
		if(!widgetSource.getTitle().isEmpty())
			buffer.append(",title:'" + widgetSource.getTitle() + "'");
		if(widgetSource instanceof HasVkAnimation)
			buffer.append(",'" ).append(HasVkAnimation.NAME).append("':").append(((HasVkAnimation)widgetSource).isAnimationEnabled());
		if(widgetSource instanceof HasVkSwitchNumberedWidget)
			buffer.append(",'" ).append(HasVkSwitchNumberedWidget.NAME).append("':").append(((HasVkSwitchNumberedWidget)widgetSource).getCurrentlyShowingWidget());
		
		if(widgetSource instanceof HasVkBeforeSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkBeforeSelectionHandler.NAME).append("':").append("'")
				.append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkSelectionHandler.NAME).append("':").append("'")
				.append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).replace("'", "\\'")).append("'");
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			VkStateHelper.getInstance().getEngine().addWidget(widget, ((IVkPanel)parent));
			int tabIndex = ((VkDecoratedTabPanel)parent).getWidgetCount() - 1;
			((VkDecoratedTabPanel)parent).setTabHeaderHtml(tabIndex, childObj.get("headerHtml").isString().stringValue());
			((VkDecoratedTabPanel)parent).setTabEnabled(tabIndex, childObj.get("enabled").isBoolean().booleanValue());
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
		addAttributes(jsonObj, parent);
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONBoolean attributeBooleanObj;
		JSONNumber attributeNumberObj;
		JSONValue attributeJsObj = childObj.get("style");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "style", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("title");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "title", attributeStringObj.stringValue()); 
		attributeJsObj = childObj.get("className");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "className", attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkAnimation.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAnimation)widget).setAnimationEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkSwitchNumberedWidget.NAME);
		if(attributeJsObj != null && (attributeNumberObj = attributeJsObj.isNumber()) != null)
			((HasVkSwitchNumberedWidget)widget).showWidget((int)attributeNumberObj.doubleValue());
		
		attributeJsObj = childObj.get(HasVkBeforeSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkBeforeSelectionHandler)widget).addBeforeSelectionHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSelectionHandler)widget).addSelectionHandler(attributeStringObj.stringValue());
	}
	@Override
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
		if(sourceWidget instanceof IVkPanel && targetWidget instanceof IVkPanel)
		{
			Iterator<Widget> widgets = ((IVkPanel)sourceWidget).iterator();
			while(widgets.hasNext())
			{
				Widget currentWidget = widgets.next();
				Widget newWidget = VkStateHelper.getInstance().getEngine().getWidget(((IVkWidget)currentWidget).getWidgetName());
				VkStateHelper.getInstance().setDesignerMode(false);
				if(currentWidget instanceof IVkWidget)
					VkStateHelper.getInstance().getEngine().addWidget(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget) currentWidget).getWidgetName()).deepClone(currentWidget, newWidget), (IVkPanel)targetWidget);
				VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
			}
		}
		VkStateHelper.getInstance().setDesignerMode(false);
		((IVkWidget)sourceWidget).clone(targetWidget);
		//VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		copyAttributes(sourceWidget, targetWidget);
		VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
		return targetWidget;
	}
}
