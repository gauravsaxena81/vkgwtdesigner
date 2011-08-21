package com.vk.gwt.designer.client.engine;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;

public class VkTabPanelEngine extends VkAbstractWidgetEngine<VkTabPanel> {
	private final String DISABLE_TAB = "Disable a Tab";
	private final String ENABLE_TAB = "Enable a Tab";
	@Override
	public VkTabPanel getWidget() {
		VkTabPanel widget = new VkTabPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.remove(HasVkEnabled.NAME);
		optionList.add(ENABLE_TAB);
		optionList.add(DISABLE_TAB);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkTabPanel widget = (VkTabPanel)invokingWidget;
		if(attributeName.equals(ENABLE_TAB)) {
			ListBox listBox = new ListBox();
			for(int i = 0, len = widget.getTabCount(); i < len; i++)
				listBox.addItem(widget.getTabHeaderText(i), Integer.toString(i));
			listBox.setWidth("300px");
			VkDesignerUtil.getEngine().showAddListDialog("Add Tab number to enable", listBox
			, new IEventRegister() {
				@Override
				public void registerEvent(String text) {
					int tabNumber = Integer.parseInt(text);
					widget.setTabEnabled(tabNumber, true);
					widget.selectTab(tabNumber);
				}
			});
		} else if(attributeName.equals(DISABLE_TAB)) {
			ListBox listBox = new ListBox();
			for(int i = 0, len = widget.getTabCount(); i < len; i++)
				listBox.addItem(widget.getTabHeaderText(i), Integer.toString(i));
			listBox.setWidth("300px");
			VkDesignerUtil.getEngine().showAddListDialog("Add Tab number to disable", listBox
			, new IEventRegister() {
				@Override
				public void registerEvent(String text) {
					widget.setTabEnabled(Integer.parseInt(text), false);
				}
			});
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IPanel)
		{
			Iterator<Widget> widgetList = ((IPanel)widget).iterator();
			int widgetIndex = 0;
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{headerHtml:'").append(((VkTabPanel)widget).getTabHeaderHtml(widgetIndex)).append("'");
				buffer.append(",enabled:").append(((VkTabPanel)widget).getTabEnabled(widgetIndex++));
				buffer.append(",child:");
				if(child instanceof IVkWidget)
					buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
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
		addAttributes(jsonObj, parent);
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			VkDesignerUtil.getEngine().addWidget(widget, ((IPanel)parent));
			int tabIndex = ((VkTabPanel)parent).getWidgetCount() - 1;
			((VkTabPanel)parent).setTabHeaderHtml(tabIndex, childObj.get("headerHtml").isString().stringValue());
			((VkTabPanel)parent).setTabEnabled(tabIndex, childObj.get("enabled").isBoolean().booleanValue());
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
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
		boolean isVkDesignerMode = VkDesignerUtil.isDesignerMode;
		if(sourceWidget instanceof IPanel && targetWidget instanceof IPanel)
		{
			Iterator<Widget> widgets = ((IPanel)sourceWidget).iterator();
			while(widgets.hasNext())
			{
				Widget currentWidget = widgets.next();
				Widget newWidget = VkDesignerUtil.getEngine().getWidget(((IVkWidget)currentWidget).getWidgetName());
				VkDesignerUtil.isDesignerMode = false;
				if(currentWidget instanceof IVkWidget)
					VkDesignerUtil.getEngine().addWidget(VkDesignerUtil.getEngineMap().get(((IVkWidget) currentWidget).getWidgetName()).deepClone(currentWidget, newWidget), (IPanel)targetWidget);
				VkDesignerUtil.isDesignerMode = isVkDesignerMode;
			}
		}
		VkDesignerUtil.isDesignerMode = false;
		((IVkWidget)sourceWidget).clone(targetWidget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		VkDesignerUtil.isDesignerMode = isVkDesignerMode;
		return targetWidget;
	}
}
