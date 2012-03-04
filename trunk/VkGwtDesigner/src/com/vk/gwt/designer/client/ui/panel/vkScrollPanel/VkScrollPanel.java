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
package com.vk.gwt.designer.client.ui.panel.vkScrollPanel;

import java.util.List;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollBarShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollHandler;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkScrollPanel extends ScrollPanel implements HasVkWidgets, IVkPanel, HasVkScrollHandler, HasVkScrollBarShowing {
	public static final String NAME = "Scroll Panel";
	private HandlerRegistration scrollHandlerRegistration;
	private String scrollJs = "";
	private IVkWidget vkParent;
	public VkScrollPanel(){
		sinkEvents(Event.ONMOUSEDOWN);
	}
	@Override
	  public void onBrowserEvent(Event event) {
	    switch (DOM.eventGetType(event)) {
	    	case Event.ONMOUSEDOWN:
	    		if(event.getButton() == 0 || event.getButton() == 1)
	    			event.stopPropagation();
	    	break;
	    }
	    super.onBrowserEvent(event);
	}
	@Override
	public void add(Widget widget)
	{
		if(getWidget() == null) {
			setWidget(widget);
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
		}
		else
			Window.alert("Scroll Panel can add only one Widget");
	}
	@Override
	public void addScrollHandler(String js) {
		if(scrollHandlerRegistration != null)
			scrollHandlerRegistration.removeHandler();
		scrollHandlerRegistration = null;
		scrollJs  = js.trim();
		if(!scrollJs.isEmpty())
		{
			scrollHandlerRegistration = addScrollHandler(new ScrollHandler() {
				@Override
				public void onScroll(ScrollEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(scrollJs, event, true);
				}
			});
		}
	}
	@Override
	public String getPriorJs(String eventName) {
		return scrollJs;
	}
	@Override
	public void setAlwaysShowScrollBars(boolean alwaysShow) {
		super.setAlwaysShowScrollBars(alwaysShow);
	}
	@Override
	public boolean isAlwaysShowScrollBars() {
		return DOM.getStyleAttribute(getElement(), "overflow").equals("scroll");
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
	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getHorizontalScrollPosition() 
	{
		return super.getHorizontalScrollPosition();
	}
	@Override
	@Export
	public int getScrollPosition() 
	{
		return super.getVerticalScrollPosition();
	}
	@Override
	@Export
	public void setHorizontalScrollPosition(int pos) 
	{
		super.setHorizontalScrollPosition(pos);
	}
	@Override
	@Export
	public void setVerticalScrollPosition(int pos) 
	{
		super.setVerticalScrollPosition(pos);
	}
	@Override
	@Export
	public void scrollToBottom()
	{
		super.scrollToBottom();
	}
	@Override
	@Export
	public void scrollToTop()
	{
		super.scrollToTop();
	}
	@Override
	@Export
	public void scrollToLeft()
	{
		super.scrollToLeft();
	}
	@Override
	@Export
	public void scrollToRight()
	{
		super.scrollToRight();
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
	public void removeStyleName(String className) {
		super.removeStyleName(className);
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
