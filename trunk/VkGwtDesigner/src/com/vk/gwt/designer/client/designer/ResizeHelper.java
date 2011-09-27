package com.vk.gwt.designer.client.designer;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical.VkMenuBarVertical;
import com.vk.gwt.designer.client.ui.widget.vkFrame.VkFrame;

class ResizeHelper {
	private static ResizeHelper resizeHelper = new ResizeHelper();
	
	private ResizeHelper(){}
	
	public static ResizeHelper getInstance(){
		return resizeHelper;
	}
	void resize(final Widget invokingWidget){
		final HTML draggingWidget = new HTML("&nbsp;");
		draggingWidget.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				event.stopPropagation();
			}
		});
		DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
		DOM.setStyleAttribute(draggingWidget.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		draggingWidget.getElement().getStyle().setOpacity(0.2);
		RootPanel.get().add(draggingWidget);
		DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
		draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
		boolean isAttached = invokingWidget.isAttached();
		boolean isPopUpMenuBar = invokingWidget instanceof VkMenuBarVertical;
		//when menubars are added as submenus then on pressing resize they vanish which leads to top and left being evaluated to 0
		final int top = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getTop() : 
			(invokingWidget.getElement().getAbsoluteTop()/* - VkMainDrawingPanel.getInstance().getElement().getOffsetTop()*/);
		final int left = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getLeft() : invokingWidget.getElement().getAbsoluteLeft();
		DOM.setStyleAttribute(draggingWidget.getElement(), "top", top + "px");
		DOM.setStyleAttribute(draggingWidget.getElement(), "left", left + "px");
		//DOM.setCapture(draggingWidget.getElement());
		VkDesignerUtil.setCapture(draggingWidget);
		if(invokingWidget instanceof Frame)
			invokingWidget.setVisible(false);
		draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				DOM.setStyleAttribute(draggingWidget.getElement(), "width", SnapHelper.getInstance().getSnappedWidth(left, event.getClientX() + Window.getScrollLeft() - left) + "px");
				DOM.setStyleAttribute(draggingWidget.getElement(), "height", SnapHelper.getInstance().getSnappedHeight(top, event.getClientY() + Window.getScrollTop() - top) + "px");
			}
		});
		draggingWidget.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				if(invokingWidget instanceof VkFrame)
					invokingWidget.setVisible(true);
				//DOM.releaseCapture(draggingWidget.getElement());
				final Widget widget = invokingWidget;
				VkDesignerUtil.releaseCapture(draggingWidget);
				final int initialHeight = invokingWidget.getOffsetHeight() - (int)VkDesignerUtil.getDecorationsWidth(widget.getElement());
				final int initialWidth = invokingWidget.getOffsetWidth() - (int)VkDesignerUtil.getDecorationsHeight(widget.getElement());
				final int finalWidth = draggingWidget.getOffsetWidth()  - (int)VkDesignerUtil.getDecorationsWidth(widget.getElement());
				final int finalHeight = draggingWidget.getOffsetHeight()  - (int)VkDesignerUtil.getDecorationsHeight(widget.getElement());
				draggingWidget.removeFromParent();
				if(finalWidth > 0)
					widget.setWidth(finalWidth + "px");
				if(finalHeight > 0)
					widget.setHeight(finalHeight + "px");
				ToolbarHelper.getInstance().showToolbar(invokingWidget);
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(finalWidth > 0)
							widget.setWidth(finalWidth + "px");
						if(finalHeight > 0)
							widget.setHeight(finalHeight + "px");
					}
				}, new Command(){
					@Override
					public void execute() {
						widget.setWidth(initialWidth + "px");
						widget.setHeight(initialHeight + "px");
					}});
			}
		});
	}
}