package com.vk.gwt.designer.client.engine;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;

public class VkDecoratedTabPanelEngine extends VkAbstractWidgetEngine<VkDecoratedTabPanel> {
	private final String ADD_ENABLED = "Set Current Tab Enabled";
	private final String MAKE_ENABLE = "Enable a Tab";
	@Override
	public VkDecoratedTabPanel getWidget() {
		VkDecoratedTabPanel widget = new VkDecoratedTabPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.add(ADD_ENABLED);
		optionList.add(MAKE_ENABLE);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDecoratedTabPanel widget = (VkDecoratedTabPanel)invokingWidget;
		if(attributeName.equals(ADD_ENABLED))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				attributeName = HasVkEnabled.NAME;
		}
		else if(attributeName.equals(MAKE_ENABLE))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
			{
				VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add Tab number to enable", new TextBox()
				, new IEventRegister() {
					@Override
					public void registerEvent(String text) {
						try{
							int index = Integer.parseInt(text);
							if(index >= widget.getTabCount())
								Window.alert("tab number should be less than maximum number of tabs");
							else
								widget.setTabEnabled(Integer.parseInt(text), true);
						}catch(NumberFormatException e)
						{
							Window.alert("Tab number cannot be non-numeric");
						}
					}
				});
			}
		}
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
				buffer.append("{headerHtml:'").append(((VkDecoratedTabPanel)widget).getTabHeaderHtml(widgetIndex));
				buffer.append("',enabled:").append(((VkDecoratedTabPanel)widget).getTabEnabled(widgetIndex++));
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
			int tabIndex = ((VkDecoratedTabPanel)parent).getWidgetCount() - 1;
			((VkDecoratedTabPanel)parent).setTabHeaderHtml(tabIndex, childObj.get("headerHtml").isString().stringValue());
			((VkDecoratedTabPanel)parent).setTabEnabled(tabIndex, childObj.get("enabled").isBoolean().booleanValue());
			//addAttributes(childWidgetObj, widget);
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONBoolean attributeBooleanObj;
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
		
		attributeJsObj = childObj.get(HasVkBeforeSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkBeforeSelectionHandler)widget).addBeforeSelectionHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSelectionHandler)widget).addSelectionHandler(attributeStringObj.stringValue());
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		int widgetCount = ((VkDecoratedTabPanel)widgetSource).getWidgetCount();
		for(int i = 0 ; i < widgetCount; i++)
		{
			((VkDecoratedTabPanel)widgetTarget).setTabHeaderHtml(i, ((VkDecoratedTabPanel)widgetSource).getTabHeaderHtml(i));
			((VkDecoratedTabPanel)widgetTarget).setEnabled(((VkDecoratedTabPanel)widgetSource).getTabEnabled(i));
		}
	}
}
