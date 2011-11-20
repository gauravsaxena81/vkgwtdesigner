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

import java.util.List;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class ToolbarHelper {
	private static ToolbarHelper toolbarHelper = new ToolbarHelper();
	private PopupPanel toolBarPanel;
	private PopupPanel resizePanel;
	private Widget popUpAssociateWidget;
	private static HTML moveImage;
	private static HTML resizeImage;
	
	private ToolbarHelper(){
		toolBarPanel = new PopupPanel(true);
		resizePanel = new PopupPanel(true);
		moveImage = new HTML("<img src='images/cursor_move.png' height=18 width=18>");
		resizeImage = new HTML("<img src='images/cursor_resize.png' height=16 width=16>");
		
		HorizontalPanel moveHp = new HorizontalPanel();
		toolBarPanel.setWidget(moveHp);
		toolBarPanel.setAutoHideEnabled(false);
		DOM.setStyleAttribute(toolBarPanel.getElement(), "zIndex", Integer.toString(1));
		resizePanel.add(resizeImage);
		resizePanel.setAutoHideEnabled(false);
		DOM.setStyleAttribute(resizePanel.getElement(), "zIndex", Integer.toString(1));
		moveImage.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkStateHelper.getInstance().getMenu().prepareMenu((IVkWidget) popUpAssociateWidget);
				if(popUpAssociateWidget instanceof TextBoxBase)
					((TextBoxBase)popUpAssociateWidget).setFocus(false);
				if(event.getNativeButton() == NativeEvent.BUTTON_LEFT && ((IVkWidget)popUpAssociateWidget).isMovable() && popUpAssociateWidget.getParent() instanceof AbsolutePanel) {
					MoveHelper.getInstance().makeMovable((IVkWidget) popUpAssociateWidget);
					event.preventDefault();
					event.stopPropagation();
				}
			}});
		resizeImage.addMouseDownHandler(new MouseDownHandler(){
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkStateHelper.getInstance().getMenu().prepareMenu((IVkWidget) popUpAssociateWidget);
				if (event.getNativeButton() == NativeEvent.BUTTON_LEFT && ((IVkWidget)popUpAssociateWidget).isResizable()) {
					ResizeHelper.getInstance().resize(popUpAssociateWidget);
					event.preventDefault();
					event.stopPropagation();
				}
			}});
		toolBarPanel.setStyleName("none");
		resizePanel.setStyleName("none");
		init();
	}
	private void init() {
		popUpAssociateWidget = null;
	}
	public static ToolbarHelper getInstance(){
		return toolbarHelper;
	}
	public void hideToolbar() {
		toolBarPanel.hide();
		resizePanel.hide();
	}
	public void showToolbar(Widget widget) {
		popUpAssociateWidget = widget;
		moveImage.setVisible(((IVkWidget)popUpAssociateWidget).isMovable() && ((IVkWidget)popUpAssociateWidget).isMovable() && popUpAssociateWidget.getParent() instanceof AbsolutePanel);
		resizeImage.setVisible(((IVkWidget)popUpAssociateWidget).isResizable());
		HorizontalPanel toolBarHp = (HorizontalPanel) toolBarPanel.getWidget();
		toolBarHp.clear();
		toolBarHp.add(moveImage);
		List<? extends Widget> toolbarWidgets = ((IVkWidget)widget).getToolbarWidgets();
		if(toolbarWidgets != null)
			for(int i = 0, len = toolbarWidgets.size(); i < len; i++)
				toolBarHp.add(toolbarWidgets.get(i));
		int widgetAbsoluteLeft = widget.getAbsoluteLeft();
		toolBarPanel.show();
		int menuTop = widget.getAbsoluteTop() - toolBarPanel.getOffsetHeight();
		toolBarPanel.setPopupPosition(widgetAbsoluteLeft,  menuTop + 3);
		resizePanel.show();
		resizePanel.setPopupPosition(widgetAbsoluteLeft + widget.getOffsetWidth(), menuTop + widget.getOffsetHeight() + resizePanel.getOffsetHeight() + 3);
	}
}
