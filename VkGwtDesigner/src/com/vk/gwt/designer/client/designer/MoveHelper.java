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
package com.vk.gwt.designer.client.designer;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class MoveHelper implements IMoveHelper{
	
	public MoveHelper(){}
	@Override
	public void makeMovable(final IVkWidget widget) {
		if(widget.isMovable()) {
			final Widget invokingWidget = (Widget) widget;
			final HTML draggingWidget = new HTML("&nbsp;");
			RootPanel.get().add(draggingWidget);
			DOM.setStyleAttribute(draggingWidget.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
			DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
			draggingWidget.getElement().getStyle().setOpacity(0.2);
			DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
			
			draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
			DOM.setStyleAttribute(draggingWidget.getElement(), "top", invokingWidget.getElement().getAbsoluteTop() + "px");
			DOM.setStyleAttribute(draggingWidget.getElement(), "left", invokingWidget.getAbsoluteLeft() + "px");
			DOM.setCapture(draggingWidget.getElement());
			VkStateHelper.getInstance().getSnapHelper().setIgnoreWidget(invokingWidget);
			//setCapture(draggingWidget);
			draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					DOM.setStyleAttribute(draggingWidget.getElement(), "top", VkStateHelper.getInstance().getSnapHelper().getSnappedTop(event.getClientY() +  RootPanel.getBodyElement().getScrollTop(), draggingWidget.getOffsetHeight()) + "px");
					DOM.setStyleAttribute(draggingWidget.getElement(), "left", VkStateHelper.getInstance().getSnapHelper().getSnappedLeft(event.getClientX(), draggingWidget.getOffsetWidth()) + "px");
					event.preventDefault();
				}
			});
			draggingWidget.addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					final int initialTop = VkDesignerUtil.getOffsetTop(invokingWidget.getElement());
					final int initialLeft = VkDesignerUtil.getOffsetLeft(invokingWidget.getElement());
					final int finalTop = draggingWidget.getAbsoluteTop() - invokingWidget.getElement().getOffsetParent().getAbsoluteTop()
					- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-top-width") 
					/*- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-bottom-width")*/;
					final int finalLeft = VkDesignerUtil.getOffsetLeft(draggingWidget.getElement()) - invokingWidget.getElement().getOffsetParent().getAbsoluteLeft()
					- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-left-width") 
					/*- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-right-width")*/;
					VkStateHelper.getInstance().getSnapHelper().setIgnoreWidget(null);
					if(finalTop != initialTop || finalLeft != initialLeft) {//-1 is hack for FF
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(invokingWidget.getElement(), "top", finalTop + "px");
								DOM.setStyleAttribute(invokingWidget.getElement(), "left", finalLeft + "px");
								VkStateHelper.getInstance().getToolbarHelper().showToolbar(invokingWidget);
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(invokingWidget.getElement(), "top", initialTop + "px"); 
										DOM.setStyleAttribute(invokingWidget.getElement(), "left", initialLeft + "px");
									}});
					}
					DOM.releaseCapture(draggingWidget.getElement());
					//VkDesignerUtil.releaseCapture(draggingWidget);
					draggingWidget.removeFromParent();
					if(finalLeft - initialLeft == 1 && finalTop  - initialTop == 1) {//draggingwidget is 1 pixel off in position
						invokingWidget.fireEvent(event);
						invokingWidget.onBrowserEvent((Event) event.getNativeEvent());
					}
				}
			});
		}
	}
	@Override
	public void moveWidgetLeft(IVkWidget widget){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "left", VkDesignerUtil.getOffsetLeft(((UIObject) widget).getElement()) - 1 + "px");
			VkStateHelper.getInstance().getToolbarHelper().showToolbar((Widget) widget);
		}
	}
	@Override
	public void moveWidgetDown(IVkWidget widget){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "top", VkDesignerUtil.getOffsetTop(((UIObject) widget).getElement()) + 1 + "px");
			VkStateHelper.getInstance().getToolbarHelper().showToolbar((Widget) widget);
		}
	}
	@Override
	public void moveWidgetRight(IVkWidget widget){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "left", VkDesignerUtil.getOffsetLeft(((UIObject) widget).getElement()) + 1 + "px");
			VkStateHelper.getInstance().getToolbarHelper().showToolbar((Widget) widget);
		}
	}
	@Override
	public void moveWidgetUp(IVkWidget widget){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "top", VkDesignerUtil.getOffsetTop(((UIObject) widget).getElement()) - 1 + "px");
			VkStateHelper.getInstance().getToolbarHelper().showToolbar((Widget) widget);
		}
	}
}
