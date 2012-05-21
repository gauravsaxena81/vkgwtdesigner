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
package com.vk.gwt.designer.client.ui.widget.tabBar.vkDecoratedTabBar;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.UndoHelper;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.designer.VkStateHelper;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkTabBar.VkTabBar;

public class VkDecoratedTabBarEngine extends VkAbstractWidgetEngine<VkDecoratedTabBar> {
	private final String ADD_TAB = "Add a Tab";
	private final String REMOVE_TAB = "Remove Current Tab";
	private final String EDIT_TAB = "Edit Current Tab";
	private final String DISABLE_TAB = "Disable a Tab";
	private final String ENABLE_TAB = "Enable a Tab";
	@Override
	public VkDecoratedTabBar getWidget() {
		VkDecoratedTabBar widget = new VkDecoratedTabBar();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		optionList.add(3, ADD_TAB);
		optionList.add(4, EDIT_TAB);
		optionList.add(5, REMOVE_TAB);
		optionList.add(6, DISABLE_TAB);
		optionList.add(7, ENABLE_TAB);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDecoratedTabBar tabbar = (VkDecoratedTabBar)invokingWidget;
		if(attributeName.equals(ADD_TAB))
			addTab(tabbar);
		else if (attributeName.equals(EDIT_TAB))
			editTab(tabbar);
		else if(attributeName.equals(REMOVE_TAB))
			removeTab(tabbar);
		else if(attributeName.equals(DISABLE_TAB))
			enableTab(tabbar, false);
		else if(attributeName.equals(ENABLE_TAB))
			enableTab(tabbar, true);
		VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void editTab(final VkDecoratedTabBar tabbar) {
		if(tabbar.getSelectedTab() < 0)
			Window.alert("Select a tab before this operation");
		else {
			final TextArea ta = new TextArea();
			ta.setText(tabbar.getTabHTML(tabbar.getSelectedTab()));
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide HTML for tab name", ta
				, new IDialogCallback() {
				@Override
				public void save(String js) {
					final int tabNumber = tabbar.getSelectedTab();
					final String html = ta.getText();
					final String priorHtml = tabbar.getTabHTML(tabNumber);
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							tabbar.setTabHTML(tabNumber, html);
						}}, new Command(){
						@Override
						public void execute() {
							tabbar.setTabHTML(tabNumber, priorHtml);
						}});
				}
			});	
		}
	}
	private void addTab(final VkDecoratedTabBar tabbar) {
		final TextArea ta = new TextArea();
		ta.setSize("300px", "100px");
		VkDesignerUtil.showAddTextAttributeDialog("Please provide HTML for tab name", ta
			, new IDialogCallback() {
			@Override
			public void save(String js) {
				final int tabNumber = tabbar.getTabCount();
				final String html = ta.getText();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						tabbar.addTab(html, true);
					}}, new Command(){
					@Override
					public void execute() {
						tabbar.removeTab(tabNumber);
					}});
			}
		});		
	}
	private void removeTab(final VkDecoratedTabBar tabbar) {
		if(tabbar.getSelectedTab() < 0)
			Window.alert("Select a tab before this operation");
		else {
			final int tabNumber = tabbar.getSelectedTab();
			final String priorHtml = tabbar.getTabHTML(tabNumber);
			final boolean enabled = tabbar.isTabEnabled(tabNumber);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					tabbar.removeTab(tabbar.getSelectedTab());
				}}, new Command(){
				@Override
				public void execute() {
					tabbar.insertTab(priorHtml, tabNumber);
					tabbar.setTabEnabled(tabNumber, enabled);
				}});
		}
	}
	private void enableTab(final VkDecoratedTabBar tabbar, final boolean enable) {
		if(tabbar.getTabCount() == 0)
			Window.alert("No tabs were found");
		else{
			ListBox listBox = new ListBox();
			listBox.setWidth("200px");
			for(int i = 0, len = tabbar.getTabCount(); i < len; i++)
				if(tabbar.isTabEnabled(i) != enable)
					listBox.addItem(Integer.toString(i), Integer.toString(i));
			if(listBox.getItemCount() > 0){
				VkDesignerUtil.showAddListDialog("Add Tab number to enable", listBox, new IDialogCallback() {
					@Override
					public void save(String text) {
						final int tabNumber = Integer.parseInt(text);
						final boolean priorEnabled = tabbar.isTabEnabled(tabNumber);
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								tabbar.setTabEnabled(tabNumber, enable);
							}}, new Command(){
							@Override
							public void execute() {
								tabbar.setTabEnabled(tabNumber, priorEnabled);
							}});
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
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		TabBar tabBar =  (VkDecoratedTabBar)widget;
		serializeAttributes(buffer, tabBar);
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
