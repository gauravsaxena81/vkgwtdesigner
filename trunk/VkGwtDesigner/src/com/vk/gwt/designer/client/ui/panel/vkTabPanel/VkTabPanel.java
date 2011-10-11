package com.vk.gwt.designer.client.ui.panel.vkTabPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderText;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkTabPanel extends TabPanel implements HasVkWidgets, IVkPanel, HasVkAnimation, HasVkTabHeaderText, HasVkTabHeaderHtml, HasVkEnabled, HasVkSwitchNumberedWidget
, HasVkBeforeSelectionHandler,HasVkSelectionHandler{
	public static final String NAME = "Tab Panel";
	private HandlerRegistration beforeSelectionHandler;
	private HandlerRegistration selectionHandler;
	private String beforeSelectionJs = "";
	private String selectionJs = "";
	private IVkWidget vkParent;
	@Override
	public void add(Widget widget)
	{
		super.add(widget, "Untitled");
		if(widget instanceof IVkWidget)
			((IVkWidget)widget).setVkParent(this);
		selectTab(getWidgetCount() - 1);
	}
	@Override
	public void setTabHTML(String html) {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			setTabHeaderHtml(getSelectedTab(), html);
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public String getTabHTML() {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			return getTabHeaderHtml(selectedIndex);
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public void setTabText(String text) {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			setTabHeaderText(getSelectedTab(), text);
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	
	@Override
	public String getTabText() {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			return super.getTabBar().getTabHTML(selectedIndex);
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public boolean isEnabled() {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			return getTabEnabled(getSelectedTab());
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
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
				EventHelper.getInstance().executeEvent(beforeSelectionJs, eventproperties);
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
		selectionHandler = null;
		selectionJs = js.trim();
		if(!selectionJs.isEmpty())
		{
		selectionHandler = super.addSelectionHandler(new SelectionHandler<Integer>() {
			@Override
			public void onSelection(SelectionEvent<Integer> event) {
				Map<String, String> eventproperties = new HashMap<String, String>();
				eventproperties.put("item", event.getSelectedItem().toString());
				EventHelper.getInstance().executeEvent(selectionJs, eventproperties);
			}
		});
	}
	}
	@Override
	public void setEnabled(boolean enabled) {
		int selectedIndex = getSelectedTab();
		if(selectedIndex > -1)
			setTabEnabled(getSelectedTab(), enabled);
		else
		{
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("No Tab has been selected");
			throw new IllegalStateException("No Tab has been selected");
		}
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {
		int widgetCount = this.getWidgetCount();
		for(int i = 0 ; i < widgetCount; i++)
		{
			((VkTabPanel)targetWidget).setTabHeaderHtml(i, this.getTabHeaderHtml(i));
			((VkTabPanel)targetWidget).setEnabled(this.getTabEnabled(i));
		}
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public void showWidget(int index) {
		selectTab(index);		
	}
	@Override
	public int getCurrentlyShowingWidget() {
		return getSelectedTab();
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
	}
	/**************************Export attribute Methods********************************/
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
	public void setTabHeaderText(int index, String text) {
		super.getTabBar().setTabText(index, text);
	}
	@Export
	public String getTabHeaderText(int index) {
		Element div = DOM.createDiv();
		div.setInnerHTML(getTabHeaderHtml(index));
		return div.getInnerText();
	}
	@Export
	public void setTabHeaderHtml(int index, String html) {
		super.getTabBar().setTabHTML(index, html);
	}
	@Export
	public String getTabHeaderHtml(int index) {
		return super.getTabBar().getTabHTML(index);
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
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
