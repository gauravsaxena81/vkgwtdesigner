package com.vk.gwt.designer.client.Panels;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkTabPanel extends TabPanel implements HasVkWidgets, IPanel, HasVkAnimation, HasVkHtml, HasVkText, HasVkEnabled, HasVkBeforeSelectionHandler, 
HasVkSelectionHandler{
	public static final String NAME = "Tab Panel";
	private HandlerRegistration beforeSelectionHandler;
	private String beforeSelectionJs;
	private HandlerRegistration selectionHandler;
	private String selectionJs;
	@Override
	public void add(Widget widget)
	{
		super.add(widget, "Untitled");
		selectTab(getWidgetCount() - 1);
	}
	@Override
	public void setHTML(String html) {
		addTabHeaderHtml(getSelectedTab(), html);
	}
	@Override
	public String getHTML() {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			return super.getTabBar().getTabHTML(getSelectedTab());
		else
		{
			Window.alert("No Tab has been added");
			throw new IllegalStateException("No Tab has been added");
		}
	}
	@Override
	public void setText(String text) {
		addTabHeaderText(getSelectedTab(), text);
	}
	
	@Override
	public String getText() {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			return super.getTabBar().getTabHTML(selectedIndex);
		else
		{
			Window.alert("No Tab has been added");
			throw new IllegalStateException("No Tab has been added");
		}
	}
	@Override
	public boolean isEnabled() {
		return getTabEnabled(getSelectedTab());
	}
	@Override
	public void addBeforeSelectionHandler(String js) {
		if(beforeSelectionHandler != null)
			beforeSelectionHandler.removeHandler();
		beforeSelectionJs = js;
		beforeSelectionHandler = super.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				Map<String, String> eventproperties = new HashMap<String, String>();
				eventproperties.put("item", event.getItem().toString());
				VkDesignerUtil.executeEvent(beforeSelectionJs, eventproperties);
			}
		});
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
		selectionJs = js;
		selectionHandler = super.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Map<String, String> eventproperties = new HashMap<String, String>();
				eventproperties.put("item", event.getSelectedItem().toString());
				VkDesignerUtil.executeEvent(selectionJs, eventproperties);
			}
		});
	}
	@Override
	public void setEnabled(boolean enabled) {
		setTabEnabled(getSelectedTab(), enabled);
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void setAnimationEnabled(boolean enable)
	{
		super.setAnimationEnabled(enable);
	}
	@Override
	@Export
	public boolean isAnimationEnabled()
	{
		return super.isAnimationEnabled();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
	@Override
	@Export
	public void selectTab(int index)
	{
		super.selectTab(index);
	}
	@Export
	public int getSelectedTab()
	{
		return super.getTabBar().getSelectedTab();
	}
	@Export
	public void addTabHeaderText(int index, String text) {
		super.getTabBar().setTabText(index, text);
	}
	@Export
	public void addTabHeaderHtml(int index, String html) {
		super.getTabBar().setTabHTML(index, html);
	}
	@Export
	public void setTabEnabled(int index, boolean enabled) {
		super.getTabBar().setTabEnabled(index, enabled);
	}
	@Export
	public boolean getTabEnabled(int index) {
		return super.getTabBar().isTabEnabled(index);
	}
	@Export
	public int getTabCount() {
		return super.getTabBar().getTabCount();
	}
}
