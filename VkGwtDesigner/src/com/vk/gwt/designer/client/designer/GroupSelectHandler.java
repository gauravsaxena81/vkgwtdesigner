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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;

public class GroupSelectHandler {
	private static GroupSelectHandler groupHandler = new GroupSelectHandler();
	private PopupPanel groupingBoundsWidget;
	private List<Widget> selectedWidgets;
	
	private GroupSelectHandler(){
		groupingBoundsWidget = new PopupPanel();
		groupingBoundsWidget.setWidget(new HTML());
		groupingBoundsWidget.setStyleName("none");
		groupingBoundsWidget.getWidget().setStyleName("vk-grouping-widget");
		groupingBoundsWidget.hide();
		selectedWidgets = new ArrayList<Widget>();
	}
	
	public static GroupSelectHandler getInstance(){
		return groupHandler;
	}
	public void addGroupSelectionHandler(final VkAbsolutePanel panel){
		panel.addDomHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkDesignerUtil.setCapture(panel);
				groupingBoundsWidget.setPopupPosition(event.getClientX() - panel.getAbsoluteLeft() + Window.getScrollLeft(), event.getClientY() - panel.getAbsoluteTop() + Window.getScrollTop());
				groupingBoundsWidget.show();
				selectedWidgets.clear();
			}}, MouseDownEvent.getType());
		panel.addDomHandler(new MouseMoveHandler(){
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				groupingBoundsWidget.setHeight(event.getClientY() - groupingBoundsWidget.getAbsoluteTop() + "px");
				groupingBoundsWidget.setWidth(event.getClientX() - groupingBoundsWidget.getAbsoluteLeft() + "px");
			}}, MouseMoveEvent.getType());
		panel.addDomHandler(new MouseUpHandler(){
			@Override
			public void onMouseUp(MouseUpEvent event) {
				VkDesignerUtil.releaseCapture(panel);
				selectWidgets(panel, groupingBoundsWidget.getAbsoluteTop(), groupingBoundsWidget.getAbsoluteLeft()
				, groupingBoundsWidget.getAbsoluteTop() + groupingBoundsWidget.getOffsetHeight(), groupingBoundsWidget.getAbsoluteLeft() + groupingBoundsWidget.getOffsetWidth());
				groupingBoundsWidget.hide();
			}}, MouseUpEvent.getType());
	}
	private void selectWidgets(AbsolutePanel panel, int top, int left, int bottom, int right) {
		IVkPanel iVkPanel = (IVkPanel)panel;
		for(Widget i : iVkPanel){
			if(isWidgetBound(i, top, left, bottom, right))
				selectedWidgets.add(i);
		}
	}
	private boolean isWidgetBound(Widget widget, int top, int left, int bottom, int right) {
		int widgetTop = widget.getAbsoluteTop() + Window.getScrollTop();
		int widgetBottom = widgetTop + widget.getOffsetHeight();
		int widgetLeft = widget.getAbsoluteLeft() + Window.getScrollLeft();
		int widgetRight = widgetLeft + widget.getOffsetWidth();
		if(widgetTop > top && widgetBottom < bottom && widgetLeft > left && widgetRight < right)
			return true;
		else
			return false;
	}

	public List<Widget> getSelectedWidgets() {
		return selectedWidgets;
	}
}
