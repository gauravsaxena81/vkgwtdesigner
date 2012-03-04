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
package com.vk.gwt.designer.client.ui.panel.vkPopUpPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkPopUpPanel extends PopupPanel implements HasVkWidgets, IVkPanel, HasVkCloseHandler, HasVkAnimation, HasVkAutoHide, HasVkGlass, HasVkGlassStyle,
HasVkModal, HasVkInitiallyShowing{
	public static final String NAME = "Popup Panel(added to Page)";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	private boolean isShowing = true;
	private IVkWidget vkParent;
	@Override
	public void add(Widget w) {
		if(getWidget() != null)
			Window.alert("VkPopUpPanel can add only one widget");
		else {
			super.add(w);
			if(w instanceof IVkWidget)
				((IVkWidget)w).setVkParent(this);
		}
	}
	@Override
	public void setWidth(String width) {
		super.setWidth(width);
		DOM.setStyleAttribute(getElement(), "width", width);
	}
	@Override
	public void setHeight(String height) {
		super.setHeight(height);
		DOM.setStyleAttribute(getElement(), "height", height);
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeRegistration != null)
			closeRegistration.removeHandler();
		closeRegistration = null;
		closeJs = js.trim();
		if(!closeJs.isEmpty())
		{
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
	public void setInitiallyShowing(boolean showing) {
		isShowing = showing;
	}
	@Override
	public boolean isInitiallyShowing() {
		return isShowing;
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
	public void center()
	{
		RootPanel.get().remove(this);
		super.center();
		DOM.setStyleAttribute(getElement(), "display", "");
	}
	@Override
	@Export
	public void hide() {
		super.hide();
		RootPanel.get().add(this);
		DOM.setStyleAttribute(getElement(), "display", "none");
	}
	@Override
	@Export
	public boolean isShowing() {
		return super.isShowing();
	}
	@Override
	@Export
	public void setPopupPosition(int left, int top) {
		super.setPopupPosition(left, top);
	}
	@Override
	@Export
	public void show()
	{
		RootPanel.get().remove(this);
		super.show();
		DOM.setStyleAttribute(getElement(), "display", "");
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
