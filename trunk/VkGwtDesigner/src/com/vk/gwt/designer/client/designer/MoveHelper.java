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
import com.google.gwt.user.client.ui.Widget;

public class MoveHelper {
private static MoveHelper moveHelper = new MoveHelper();
	
	private MoveHelper(){}
	
	public static MoveHelper getInstance(){
		return moveHelper;
	}
	
	void makeMovable(final Widget invokingWidget) {
		final HTML draggingWidget = new HTML("&nbsp;");
		RootPanel.get().add(draggingWidget);
		DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
		draggingWidget.getElement().getStyle().setOpacity(0.2);
		DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
		draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
		DOM.setStyleAttribute(draggingWidget.getElement(), "top", invokingWidget.getAbsoluteTop() + "px");
		DOM.setStyleAttribute(draggingWidget.getElement(), "left", invokingWidget.getAbsoluteLeft() + "px");
		DOM.setCapture(draggingWidget.getElement());
		SnapHelper.getInstance().setIgnoreWidget(invokingWidget);
		//setCapture(draggingWidget);
		draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				DOM.setStyleAttribute(draggingWidget.getElement(), "top", SnapHelper.getInstance().getSnappedTop(event.getClientY() +  RootPanel.getBodyElement().getScrollTop()) + "px");
				DOM.setStyleAttribute(draggingWidget.getElement(), "left", SnapHelper.getInstance().getSnappedLeft(event.getClientX()) + "px");
				event.preventDefault();
			}
		});
		draggingWidget.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				final int initialTop = invokingWidget.getElement().getOffsetTop();
				final int initialLeft = invokingWidget.getElement().getOffsetLeft();
				final int finalTop = draggingWidget.getElement().getAbsoluteTop() - invokingWidget.getElement().getOffsetParent().getAbsoluteTop() 
				- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-top-width") 
				/*- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-bottom-width")*/;
				final int finalLeft = draggingWidget.getElement().getOffsetLeft() - invokingWidget.getElement().getOffsetParent().getAbsoluteLeft()
				- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-left-width") 
				/*- VkDesignerUtil.getPixelValue((Element) invokingWidget.getElement().getOffsetParent(), "border-right-width")*/;
				SnapHelper.getInstance().setIgnoreWidget(null);
				if(finalTop != initialTop - 1 || finalLeft != initialLeft - 1) {//-1 is hack for FF
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(invokingWidget.getElement(), "top", finalTop + "px");
							DOM.setStyleAttribute(invokingWidget.getElement(), "left", finalLeft + "px");
							ToolbarHelper.getInstance().showToolbar(invokingWidget);
						}}, new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(invokingWidget.getElement(), "top", initialTop + "px"); 
									DOM.setStyleAttribute(invokingWidget.getElement(), "left", initialLeft + "px");
									ToolbarHelper.getInstance().showToolbar(invokingWidget);
								}});
				} else
					ToolbarHelper.getInstance().showToolbar(invokingWidget);
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
