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
		DOM.setStyleAttribute(toolBarPanel.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		resizePanel.add(resizeImage);
		resizePanel.setAutoHideEnabled(false);
		DOM.setStyleAttribute(resizePanel.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		moveImage.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				 VkStateHelper.getInstance().getMenu().prepareMenu((IVkWidget) popUpAssociateWidget);
				if(event.getNativeButton() == NativeEvent.BUTTON_LEFT && ((IVkWidget)popUpAssociateWidget).isMovable() && popUpAssociateWidget.getParent() instanceof AbsolutePanel) {
					MoveHelper.getInstance().makeMovable(popUpAssociateWidget);
					toolBarPanel.hide();
					resizePanel.hide();
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
		List<Widget> toolbarWidgets = ((IVkWidget)widget).getToolbarWidgets();
		if(toolbarWidgets != null)
			for(int i = 0, len = toolbarWidgets.size(); i < len; i++)
				toolBarHp.add(toolbarWidgets.get(i));
		int widgetAbsoluteLeft = widget.getAbsoluteLeft();
		int menuTop = widget.getAbsoluteTop() - VkStateHelper.getInstance().getMenu().getOffsetHeight();
		toolBarPanel.show();
		toolBarPanel.setPopupPosition(widgetAbsoluteLeft,  menuTop + 3);
		resizePanel.show();
		resizePanel.setPopupPosition(widgetAbsoluteLeft + widget.getOffsetWidth(), menuTop + widget.getOffsetHeight() + resizePanel.getOffsetHeight() + 3);
	}
}
