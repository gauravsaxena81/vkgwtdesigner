package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkTabBar;

public class VkTabBarEngine extends VkAbstractWidgetEngine<VkTabBar> {
	private final String ADD_TAB = "Add a Tab";
	private final String REMOVE_TAB = "Remove Current Tab";
	private final String EDIT_TAB = "Edit Current Tab";
	private final String DISABLE_TAB = "Disable a Tab";
	private final String ENABLE_TAB = "Enable a Tab";

	@Override
	public VkTabBar getWidget() {
		VkTabBar widget = new VkTabBar();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.add(3, ADD_TAB);
		optionList.add(4, EDIT_TAB);
		optionList.add(5, REMOVE_TAB);
		optionList.add(6, DISABLE_TAB);
		optionList.add(7, ENABLE_TAB);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkTabBar tabbar = (VkTabBar)invokingWidget;
		if(attributeName.equals(ADD_TAB))
		{
			final TextArea ta = new TextArea();
			ta.setSize("300px", "100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
				, new IEventRegister() {
				@Override
				public void registerEvent(String js) {
					tabbar.addTab(ta.getText(), true);
				}
			});
		}
		else if (attributeName.equals(EDIT_TAB))
		{
			if(tabbar.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
			{
				final TextArea ta = new TextArea();
				ta.setText(tabbar.getTabHTML(tabbar.getSelectedTab()));
				ta.setSize("300px", "100px");
				VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
					, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						tabbar.setTabHTML(tabbar.getSelectedTab(), ta.getText());
					}
				});
			}
		}
		else if(attributeName.equals(REMOVE_TAB))
		{
			if(tabbar.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				tabbar.removeTab(tabbar.getSelectedTab());
		}
		else if(attributeName.equals(DISABLE_TAB))
			enableTab(tabbar, false);
		else if(attributeName.equals(ENABLE_TAB))
			enableTab(tabbar, true);
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void enableTab(final VkTabBar tabbar, final boolean enable) {
		if(tabbar.getTabCount() == 0)
			Window.alert("No tabs were found");
		else{
			ListBox listBox = new ListBox();
			listBox.setWidth("200px");
			for(int i = 0, len = tabbar.getTabCount(); i < len; i++)
				if(tabbar.isTabEnabled(i) != enable)
					listBox.addItem(Integer.toString(i), Integer.toString(i));
			if(listBox.getItemCount() > 0){
				VkDesignerUtil.getEngine().showAddListDialog("Add Tab number to enable", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String text) {
						tabbar.setTabEnabled(Integer.parseInt(text), enable);
					}
				});
			} else {
				Window.alert("All tabs are already " + (enable ? "enabled" : "disabled"));
			}
		}
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		TabBar tabBar =  (TabBar)widget;
		buffer.append(",tabs:[");
		for(int i = 0; i < tabBar.getTabCount(); i++)
		{
			buffer.append("{html:").append("'").append(tabBar.getTabHTML(i)).append("',");
			buffer.append("enabled:").append(tabBar.isTabEnabled(i)).append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]").append(",selectedTab:").append(tabBar.getSelectedTab());
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
		if(!widgetSource.getElement().getId().isEmpty())
			buffer.append(",id:'" + widgetSource.getElement().getId() + "'");
		
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
		JSONArray tabsArray = jsonObj.get("tabs").isArray();
		addAttributes(jsonObj, parent);
		for(int i = 0; i < tabsArray.size(); i++)
		{
			JSONObject tab = tabsArray.get(i).isObject();
			tabBar.addTab(tab.get("html").isString().stringValue(), true);
			tabBar.setTabEnabled(tabBar.getTabCount() - 1, tab.get("enabled").isBoolean().booleanValue());
		}
		int selectedTab = (int) jsonObj.get("selectedTab").isNumber().doubleValue();
		if(selectedTab > -1)
			tabBar.selectTab(selectedTab);
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		int widgetCount = ((VkTabBar)widgetSource).getTabCount();
		for(int i = 0 ; i < widgetCount; i++)
		{
			((VkTabBar)widgetTarget).setTabHTML(i, ((VkTabBar)widgetSource).getTabHTML(i));
			((VkTabBar)widgetTarget).setTabEnabled(i ,((VkTabBar)widgetSource).isTabEnabled(i));
		}
	}
}
