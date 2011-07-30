package com.vk.gwt.designer.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratedTabBar;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderText;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkDecoratedTabBar extends DecoratedTabBar implements IVkWidget, HasVkBeforeSelectionHandler, HasVkSelectionHandler, HasVkTabHeaderText, HasVkTabHeaderHtml{
	public static final String NAME = "Decorated Tab Bar";
	private HandlerRegistration beforeSelectionHandler;
	private HandlerRegistration selectionHandler;
	private String beforeSelectionJs = "";
	private String selectionJs = "";
	@Override
	public void addBeforeSelectionHandler(String js) {
		if(beforeSelectionHandler != null)
			beforeSelectionHandler.removeHandler();
		beforeSelectionHandler = null;
		beforeSelectionJs = js.trim();
		if(!beforeSelectionJs.isEmpty())
		{
			beforeSelectionHandler = super.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
				@Override
				public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
					Map<String, String> eventproperties = new HashMap<String, String>();
					eventproperties.put("item", event.getItem().toString());
					VkDesignerUtil.executeEvent(beforeSelectionJs, eventproperties);
				}
			});
		}
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkSelectionHandler.NAME))
			return selectionJs;
		else if (eventName.equals(HasVkBeforeSelectionHandler.NAME))
			return beforeSelectionJs;
		else
			return "";
	}
	@Override
	public void addSelectionHandler(String js) {
		if(selectionHandler != null)
			selectionHandler.removeHandler();
		selectionHandler = null;
		selectionJs = js.trim();
		if(!selectionJs.isEmpty())
		{
			selectionHandler = super.addSelectionHandler(new SelectionHandler<Integer>() {
				@Override
				public void onSelection(SelectionEvent<Integer> event) {
					Map<String, String> eventproperties = new HashMap<String, String>();
					eventproperties.put("item", event.getSelectedItem().toString());
					VkDesignerUtil.executeEvent(selectionJs, eventproperties);
				}
			});
		}
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public String getTabText() {
		if(getSelectedTab() > -1)
			return getTabHTML(getSelectedTab());
		else
		{
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Please select a tab before this operation");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public void setTabText(String text) {
		if(getSelectedTab() > -1)
			setTabText(getSelectedTab(), text);
		else if(VkDesignerUtil.isDesignerMode)
			Window.alert("Please select a tab before this operation");
	}
	@Override
	public String getTabHTML() {
		if(getSelectedTab() > -1)
			return getTabHTML(getSelectedTab());
		else
		{
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Please select a tab before this operation");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public void setTabHTML(String html) {
		if(getSelectedTab() > -1)
			setTabHTML(getSelectedTab(), html);
		else if(VkDesignerUtil.isDesignerMode)
			Window.alert("Please select a tab before this operation");
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getSelectedTab(){
		return super.getSelectedTab();
	}
	@Override
	@Export
	public int getTabCount() {
		return super.getTabCount();
	}
	@Override
	@Export
	public String getTabHTML(int index) {
		return super.getTabHTML(index);
	}
	@Export
	public void insertTabText(String text, int beforeIndex) {
		super.insertTab(text, false, beforeIndex);
	}
	@Export
	public void insertTabHtml(String html, int beforeIndex) {
		super.insertTab(html, true, beforeIndex);
	}
	@Override
	@Export
	public boolean isTabEnabled(int index) {
		return super.isTabEnabled(index);
	}
	@Override
	@Export
	public void removeTab(int index) {
		super.removeTab(index);
	}
	@Override
	@Export
	public boolean selectTab(int index){
		return super.selectTab(index);
	}
	@Override
	@Export
	public void setTabEnabled(int index, boolean enabled) {
		super.setTabEnabled(index, enabled);
	}
	@Override
	@Export
	public void setTabHTML(int index, String html) {
		super.setTabHTML(index, html);
	}
	@Override
	@Export
	public void setTabText(int index, String text) {
		super.setTabText(index, text);
	}

}
