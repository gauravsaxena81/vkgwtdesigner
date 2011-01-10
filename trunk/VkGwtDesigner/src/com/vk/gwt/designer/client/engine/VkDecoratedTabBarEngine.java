package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkDecoratedTabBar;

public class VkDecoratedTabBarEngine extends VkAbstractWidgetEngine<VkDecoratedTabBar> {
	private final String ADD_TAB = "Add a Tab";
	private final String REMOVE_TAB = "Remove Current Tab";
	private final String EDIT_TAB = "Edit Current Tab";
	private final String ADD_ENABLED = "Set Current Tab Enabled";
	private final String MAKE_ENABLE = "Enable a Tab";
	@Override
	public VkDecoratedTabBar getWidget() {
		VkDecoratedTabBar widget = new VkDecoratedTabBar();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.add(ADD_TAB);
		optionList.add(EDIT_TAB);
		optionList.add(REMOVE_TAB);
		optionList.add(ADD_ENABLED);
		optionList.add(MAKE_ENABLE);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDecoratedTabBar widget = (VkDecoratedTabBar)invokingWidget;
		if(attributeName.equals(ADD_TAB))
		{
			final TextArea ta = new TextArea();
			ta.setSize("100px", "50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
				, new IEventRegister() {
				@Override
				public void registerEvent(String js) {
					widget.addTab(ta.getText(), true);
				}
			});
		}
		else if (attributeName.equals(EDIT_TAB))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
			{
				final TextArea ta = new TextArea();
				ta.setText(widget.getTabHTML(widget.getSelectedTab()));
				ta.setSize("100px", "50px");
				VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
					, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						widget.setTabHTML(widget.getSelectedTab(), ta.getText());
					}
				});
			}
		}
		else if(attributeName.equals(REMOVE_TAB))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				widget.removeTab(widget.getSelectedTab());
		}
		else if(attributeName.equals(ADD_ENABLED))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Please Select a tab before this operation");
			else
				attributeName = HasVkEnabled.NAME;
		}
		else if(attributeName.equals(MAKE_ENABLE))
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
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		TabBar tabBar =  (TabBar)widget;
		buffer.append(",tabHtml:[");
		for(int i = 0; i < tabBar.getTabCount(); i++)
			buffer.append("'").append(tabBar.getTabHTML(i)).append("',");
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	@Override
	protected void serializeAttributes(StringBuffer buffer, Widget widgetSource)
	{
		if(!widgetSource.getStyleName().isEmpty())
			buffer.append(",className:'" + widgetSource.getStyleName() + "'");
		if(!widgetSource.getTitle().isEmpty())
			buffer.append(",title:'" + widgetSource.getTitle() + "'");
		
		if(widgetSource instanceof HasVkBeforeSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkBeforeSelectionHandler.NAME).append("':").append("'")
				.append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkSelectionHandler.NAME).append("':").append("'")
				.append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).replace("'", "\\'")).append("'");
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		TabBar tabBar = (TabBar)parent;
		JSONArray tabHtmlArray = jsonObj.get("tabHtml").isArray();
		for(int i = 0; i < tabHtmlArray.size(); i++)
			tabBar.addTab(tabHtmlArray.get(i).isString().stringValue(), true);
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONValue attributeJsObj = childObj.get("style");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "style", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("title");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "title", attributeStringObj.stringValue()); 
		attributeJsObj = childObj.get("className");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "className", attributeStringObj.stringValue());
		
		attributeJsObj = childObj.get(HasVkBeforeSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkBeforeSelectionHandler)widget).addBeforeSelectionHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSelectionHandler)widget).addSelectionHandler(attributeStringObj.stringValue());
	}
}
