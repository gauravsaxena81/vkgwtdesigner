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
 */package com.vk.gwt.designer.client.designer;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;

public class InitializeHelper implements IIntializeHelper{
	private static InitializeHelper initializeHelper = new InitializeHelper();
	
	private InitializeHelper(){}
	
	public static InitializeHelper getInstance(){
		return initializeHelper;
	}
	@Override
	public void initDesignerEvents(Widget widget){
		allWidgetEvents(widget);
		automaticTextAddition(widget);
	};
	private void automaticTextAddition(final Widget widget) {
		if(widget instanceof HasVkText) {
			if(widget instanceof HasDoubleClickHandlers) {
				((HasDoubleClickHandlers)widget).addDoubleClickHandler(new DoubleClickHandler() {
					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						addTextBox(widget);
						event.stopPropagation();
					}
				});
			} else {
				widget.addDomHandler(new DoubleClickHandler() {
					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						addTextBox(widget);
						event.stopPropagation();
					}
				}, DoubleClickEvent.getType());
			}
		} else {
			if(widget instanceof HasDoubleClickHandlers) {
				((HasDoubleClickHandlers)widget).addDoubleClickHandler(new DoubleClickHandler() {
					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						event.stopPropagation();
					}
				});
			} else {
				widget.addDomHandler(new DoubleClickHandler() {
					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						IWidgetEngine<? extends IVkWidget> iWidgetEngine = VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)widget).getWidgetName());
						iWidgetEngine.applyAttribute(iWidgetEngine.getAttributesList(widget).get(0), widget);
						event.stopPropagation();
					}
				}, DoubleClickEvent.getType());
			}
		}
	}
	private native void addNativeDoubleClickHandler(Widget widget) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		function dblClickHandler(){
			this.@com.vk.gwt.designer.client.designer.InitializeHelper::addTextBox(Lcom/google/gwt/user/client/ui/Widget;)(widget);
		}
		if(element.addEventListener)
			element.addEventListener("dblclick", dblClickHandler, false);
		else if(element.attachEvent)
			element.attachEvent("ondblclick", dblClickHandler);
	}-*/;
	private void addTextBox(final Widget widget) {
		final TextBox tb = new TextBox();
		tb.setText(((HasVkText) widget).getText());
		tb.setPixelSize(widget.getOffsetWidth(), Math.max(30, widget.getOffsetHeight()));
		DOM.setStyleAttribute(tb.getElement(), "top", widget.getElement().getAbsoluteTop() + "px");
		DOM.setStyleAttribute(tb.getElement(), "left", widget.getElement().getAbsoluteLeft() + "px");
		DOM.setStyleAttribute(tb.getElement(), "position", "absolute");
		DOM.setStyleAttribute(tb.getElement(), "zIndex", "1");
		RootPanel.get().add(tb);
		tb.setFocus(true);
		tb.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				applyText(tb.getText(), (HasVkText) widget);
				tb.removeFromParent();
			}
		});
		tb.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				switch(event.getNativeKeyCode()){
					case KeyCodes.KEY_ENTER :
						applyText(tb.getText(), (HasVkText) widget);
						tb.removeFromParent();
					break;
					case KeyCodes.KEY_ESCAPE:
						tb.removeFromParent();
					break;
					case KeyCodes.KEY_DELETE:
						event.stopPropagation();
					break;
				}
			}
		});
	}
	private void applyText(final String text, final HasVkText widget){
		final String prior = widget.getText();
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				widget.setText(text);
			}}, new Command(){
			@Override
			public void execute() {
				widget.setText(prior);
			}});
	}
	private native void allWidgetEvents(Widget widget) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		setTimeout(createMouseDownEvent, 200);//widgets(like images) may take time to load, thus event is not attached successfully if not waited for it
		var localInitializeHelper = this;;
		function isChild(target, parent) {
			while(target != null)
			{
				if(target == parent)
					return true;
				else
					target = target.parentNode;
			}
			return false;
		}
		function createMouseDownEvent()	{
			element.isWidget = 'true';
			if(element.addEventListener) {
				element.addEventListener("mousedown", mouseDownHandler, false);
				//element.addEventListener("mouseover", mouseOverHandler, false);
			}
			else if(element.attachEvent) {
				element.attachEvent("onmousedown", mouseDownHandler);
				//element.attachEvent("onmouseover", mouseOverHandler);
			}
			function mouseOverHandler(ev) {
				ev = ev || $wnd.event;
				if(element.id != 'drawingPanel' && getTarget(ev.target || ev.srcElement) == element) {
					//ev = ev || $wnd.event;
					//if(ev.stopPropagation)
					//	ev.stopPropagation();
					//else
					//	ev.cancelBubble = true;
				}
			}
			function getTarget(target){
				while(typeof target.isWidget == 'undefined')
					target = target.parentNode;
				return target;
			}
			function mouseDownHandler(ev) {
				ev = ev || $wnd.event;
				if(element.id != '' && getTarget(ev.target || ev.srcElement) == element) {
					localInitializeHelper.@com.vk.gwt.designer.client.designer.InitializeHelper::handleMouseDown(Lcom/google/gwt/user/client/ui/Widget;)(widget);
				}
			}
		}
	}-*/;
	private void handleMouseDown(Widget widget) {
		VkStateHelper.getInstance().getToolbarHelper().showToolbar(widget);
		VkStateHelper.getInstance().getMenu().prepareMenu((IVkWidget) widget);
	}
}
