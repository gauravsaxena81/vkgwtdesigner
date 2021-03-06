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
package com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoOpen;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkMenuBarHorizontal extends MenuBar implements IVkWidget, HasVkCloseHandler, HasVkAnimation, HasVkAutoOpen, HasVkWidgets, IVkPanel{
	public static final String NAME = "Menu Bar Horizontal";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	private HashMap<Integer, String> commandJs = new HashMap<Integer, String>();
	private List<Integer> seperatorIndices = new ArrayList<Integer>();
	private HashMap<Integer, Widget> widgets = new HashMap<Integer, Widget>();
	private IVkWidget vkParent;
	
	public VkMenuBarHorizontal(){
		this(false);
	}
	
	protected VkMenuBarHorizontal(boolean b) {
		super(b);
	}
	public MenuItem getMenuItem(int index) {
		return super.getItems().get(index);
	}
	@Override
	public void add(Widget widget) {
		addWidgetToCollection(widget);
		final MenuItem menuItem = new MenuItem("Widget", (Command) null){
			@Override
			public void setSelectionStyle(boolean selected)
			{
				super.setSelectionStyle(selected);
				if(VkMenuBarHorizontal.this.getAutoOpen() && getCommand() != null)
					getCommand().execute();
			}
		};
		this.addItem(menuItem);
		final PopupPanel popupPanel = new PopupPanel() {
			public void hide(boolean autoClosed) {
				if(autoClosed) {
					new Timer(){
						@Override
						public void run() {
							lateHide();
						}}.schedule(100);
				} else
					super.hide(autoClosed);
			}
			private void lateHide() {
				if(!VkStateHelper.getInstance().getResizeHelper().isResizing())
					super.hide(true);
			}
		};
		popupPanel.add(widget);
		if(widget instanceof IVkWidget)
			((IVkWidget)widget).setVkParent(this);
		popupPanel.setAutoHideEnabled(true);
		popupPanel.addAutoHidePartner(menuItem.getElement());
		menuItem.setCommand(new Command(){
			@Override
			public void execute() {
				if(popupPanel.isShowing())
					popupPanel.hide();
				else {
					popupPanel.showRelativeTo(menuItem);
					DOM.setStyleAttribute(popupPanel.getElement(), "top", VkDesignerUtil.getOffsetTop(popupPanel.getElement()) + VkMenuBarHorizontal.this.getOffsetHeight() - menuItem.getOffsetHeight() + "");
					popupPanel.show();
				}
			}});
		
	}
	@Override
	public Iterator<Widget> iterator() {
		return widgets.values().iterator();
	}
	public int getItemCount() {
		return super.getItems().size();
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeRegistration != null)
			closeRegistration.removeHandler();
		closeRegistration = null;
		closeJs = js.trim();
		if(!closeJs.isEmpty()) {
			closeRegistration = addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(CloseEvent<PopupPanel> event) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("autoClosed",Boolean.toString(event.isAutoClosed()));
					VkStateHelper.getInstance().getEventHelper().executeEvent(closeJs, map);
				}
			});
		}
	}

	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkCloseHandler.NAME))
			return closeJs;
		else
			return "";
	}
	/*@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {
	      	case Event.ONCLICK:{
	      		if(getSelectedItem() != null) {
		      		//submenus are always VkMenuBarVertical type
		    	  	VkMenuBarVertical menu = ((VkMenuBarVertical)getSelectedItem().getSubMenu());
		    	  	if(menu != null) {
			    	  	menu.setTop(menu.getAbsoluteTop());
			    	  	menu.setLeft(menu.getAbsoluteLeft());
		    	  	}
	      		}
	      	}
		}
	}*/
	@Override
	public String getWidgetName() {
		return NAME;
	}
	protected void addWidgetToCollection(Widget widget) {
		widgets.put(getItemCount(),widget);
	}
	@Override
	protected List<MenuItem> getItems() {
	    return super.getItems();
	}
	public HashMap<Integer, String> getCommandJs() {
		return commandJs;
	}
	public List<Integer> getSeperatorIndices() {
		return seperatorIndices;
	}
	@SuppressWarnings("unchecked")
	@Override
	public void clone(Widget targetWidget) {
		((VkMenuBarHorizontal)targetWidget).commandJs = (HashMap<Integer, String>) commandJs.clone();
		((VkMenuBarHorizontal)targetWidget).seperatorIndices = new ArrayList<Integer>();
		((VkMenuBarHorizontal)targetWidget).seperatorIndices.addAll(seperatorIndices);
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	public HashMap<Integer, Widget> getWidgets() {
		return widgets;
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
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
	@Override
	public boolean isAutoOpen() {
		return super.getAutoOpen();
	}
	/**************************Export attribute Methods********************************/
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
}
