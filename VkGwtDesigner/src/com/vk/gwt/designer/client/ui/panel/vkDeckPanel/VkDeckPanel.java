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
package com.vk.gwt.designer.client.ui.panel.vkDeckPanel;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

public class VkDeckPanel extends DeckPanel implements IVkPanel, HasVkWidgets, HasVkAnimation, HasVkSwitchNumberedWidget {
	final public static String NAME = "Deck Panel";
	private IVkWidget vkParent;
	@Override
	public int getCurrentlyShowingWidget() {
		return getVisibleWidget();
	}
	@Override
	public void add(Widget w){
		super.add(w);
		super.showWidget(super.getWidgetCount() - 1);
		if(w instanceof IVkWidget)
			((IVkWidget)w).setVkParent(this);
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
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
	public int getVisibleWidget() {
	    return super.getVisibleWidget();
	}
	@Override
	@Export
	public boolean isAnimationEnabled()
	{
		return super.isAnimationEnabled();
	}
	@Override
	@Export
	public void setAnimationEnabled(boolean enable) {
	    super.setAnimationEnabled(enable);
	}
	@Override
	@Export
	public void showWidget(int index)
	{
		super.showWidget(index);
	}
	@Override
	@Export
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
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
	public List<? extends Widget> getToolbarWidgets() {
		List<Label> pages = new ArrayList<Label>();
		for(int i = 0, len = getWidgetCount(); i < len; i++) {
			Label page = new Label("P" + i);
			pages.add(page);
			final int widgetIndex = i;
			page.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					showWidget(widgetIndex);
				}});
		}
		return pages;
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
