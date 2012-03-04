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
package com.vk.gwt.designer.client.ui.panel.vkFormPanel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkFormEncoding;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitCompleteHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.VkStateHelper;

/**
 * @author gaurav.saxena
 *
 */
public class VkFormPanel extends FormPanel implements HasVkWidgets, IVkPanel, HasVkSubmitHandler, HasVkSubmitCompleteHandler, HasVkTarget, HasVkFormMethod, 
HasVkUrl, HasVkFormEncoding{
	public static final String NAME = "Form Panel";
	private HandlerRegistration submitCompleteHandler;
	private String submitCompleteJs = "";
	private String submitJs = "";
	private IVkWidget vkParent;
	@Override
	public void add(Widget widget)
	{
		if(getWidget() != null)
			Window.alert("Form Panel can contain only one widget");
		else {
			super.add(widget);
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
		}
	}
	@Override
	public void addSubmitCompleteHandler(String js) {
		if(submitCompleteHandler != null)
			submitCompleteHandler.removeHandler();
		submitCompleteHandler = null;
		submitCompleteJs = js.trim();
		if(!submitCompleteJs.isEmpty())
		{
			submitCompleteHandler = addSubmitCompleteHandler(new SubmitCompleteHandler() {
				@Override
				public void onSubmitComplete(SubmitCompleteEvent event) {
					Map<String, String> eventproperties = new HashMap<String, String>();
					eventproperties.put("resultHtml", event.getResults());
					VkStateHelper.getInstance().getEventHelper().executeEvent(submitCompleteJs, eventproperties);
				}
			});
		}
	}
	@Override
	public void addSubmitHandler(String js) {
		submitJs = js;
		addOnSubmitFunction(EventHelper.formatJs(submitJs));
	}
	private native void addOnSubmitFunction(String formatJs) /*-{
		this.@com.vk.gwt.designer.client.ui.panel.vkFormPanel.VkFormPanel::getElement()().onsubmit = function(){
			//find the function name and appends it at the end so that eval can return the function. E.g. function x(){alert('1')} will become
			//function x(){alert('1')} x;
			var z = eval(formatJs + formatJs.match(/function\s+[^\(\)]+\(\)/)[0].replace('function','').replace('()','') + ';');
			return z();
		}
		
	}-*/;
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkSubmitHandler.NAME))
			return submitJs;
		else if (eventName.equals(HasVkSubmitCompleteHandler.NAME))
			return submitCompleteJs;
		else
			return "";
	}
	@Override
	public void setTarget(String target) {
		DOM.setElementAttribute(getElement(), "target", target);
	}
	@Override
	public void setUrl(String url) {
		setAction(url);
	}
	@Override
	public String getUrl() {
		return getAction();
	}
	@Override
	public void setMethod(String method)
	{
		super.setMethod(method);
	}
	@Override
	public String getMethod()
	{
		return super.getMethod();
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
	public String getAction()
	{
		return super.getAction();
	}
	@Override
	@Export
	public String getTarget()
	{
		return super.getTarget();
	}
	@Override
	@Export
	public void setAction(String url)
	{
		super.setAction(url);
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
