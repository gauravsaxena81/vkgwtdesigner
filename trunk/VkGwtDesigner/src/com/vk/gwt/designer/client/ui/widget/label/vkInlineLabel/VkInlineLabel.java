package com.vk.gwt.designer.client.ui.widget.label.vkInlineLabel;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkDoubleClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.EventHelper;

public class VkInlineLabel extends InlineLabel implements IVkWidget, HasVkText, HasVkAllMouseHandlers
, HasVkWordWrap, HasVkDirection{
	final public static String NAME = "Inline Label";
	private HandlerRegistration clickHandlerRegistration;
	private HandlerRegistration doubleClickHandlerRegistration;
	private HandlerRegistration mouseDownHandlerRegistration;
	private HandlerRegistration mouseUpHandlerRegistration;
	private HandlerRegistration mouseMoveHandlerRegistration;
	private HandlerRegistration mouseOutHandlerRegistration;
	private HandlerRegistration mouseOverHandlerRegistration;
	private HandlerRegistration mouseWheelHandlerRegistration;
	private String mouseDownJs = "";
	private String mouseUpJs = "";
	private String mouseMoveJs = "";
	private String mouseOutJs = "";
	private String mouseOverJs = "";
	private String mouseWheelJs = "";
	private String clickJs = "";
	private String doubleClickJs = "";
	
	public VkInlineLabel() {
		super.setDirectionEstimator(true);
		DOM.setStyleAttribute(getElement(), "border", "solid 1px gray");
	}
	
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
		else if(eventName.equals(HasVkDoubleClickHandler.NAME))
			return doubleClickJs;
		else if(eventName.equals(HasVkMouseDownHandler.NAME))
			return mouseDownJs;
		else if(eventName.equals(HasVkMouseUpHandler.NAME))
			return mouseUpJs;
		else if(eventName.equals(HasVkMouseOverHandler.NAME))
			return mouseOverJs;
		else if(eventName.equals(HasVkMouseOutHandler.NAME))
			return mouseOutJs;
		else if(eventName.equals(HasVkMouseWheelHandler.NAME))
			return mouseWheelJs;
		else if(eventName.equals(HasVkMouseMoveHandler.NAME))
			return mouseMoveJs;
		else return "";
	}
	@Override
	public void addClickHandler(final String js) {
		if(clickHandlerRegistration != null)
			clickHandlerRegistration.removeHandler();
		clickHandlerRegistration = null;
		clickJs = js.trim();
		if(!clickJs.isEmpty())
		{
			clickHandlerRegistration = addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					EventHelper.getInstance().executeEvent(clickJs, event, true);
				}
			});
		}
	}
	@Override
	public void addDoubleClickHandler(final String js) {
		if(doubleClickHandlerRegistration != null)
			doubleClickHandlerRegistration.removeHandler();
		doubleClickHandlerRegistration = null;
		doubleClickJs  = js.trim();
		if(!doubleClickJs.isEmpty())
		{
			doubleClickHandlerRegistration = addDoubleClickHandler(new DoubleClickHandler() {
				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					EventHelper.getInstance().executeEvent(doubleClickJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseDownHandler(String js) {
		if(mouseDownHandlerRegistration != null)
			mouseDownHandlerRegistration.removeHandler();
		mouseDownHandlerRegistration = null;
		mouseDownJs = js.trim();
		if(!mouseDownJs.isEmpty())
		{
			mouseDownHandlerRegistration = addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					EventHelper.getInstance().executeEvent(mouseDownJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseUpHandler(String js) {
		if(mouseUpHandlerRegistration != null)
			mouseUpHandlerRegistration.removeHandler();
		mouseUpHandlerRegistration = null;
		mouseUpJs = js.trim();
		if(!mouseUpJs.isEmpty())
		{
			mouseUpHandlerRegistration = addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					EventHelper.getInstance().executeEvent(mouseUpJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseMoveHandler(String js) {
		if(mouseMoveHandlerRegistration != null)
			mouseMoveHandlerRegistration.removeHandler();
		mouseMoveHandlerRegistration = null;
		mouseMoveJs = js.trim();
		if(!mouseMoveJs.isEmpty())
		{
			mouseMoveHandlerRegistration = addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					EventHelper.getInstance().executeEvent(mouseMoveJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseOverHandler(String js) {
		if(mouseOverHandlerRegistration != null)
			mouseOverHandlerRegistration.removeHandler();
		mouseOverHandlerRegistration = null;
		mouseOverJs = js.trim();
		if(!mouseOverJs.isEmpty())
		{
			mouseOverHandlerRegistration = addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					EventHelper.getInstance().executeEvent(mouseOverJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseOutHandler(String js) {
		if(mouseOutHandlerRegistration != null)
			mouseOutHandlerRegistration.removeHandler();
		mouseOutHandlerRegistration = null;
		mouseOutJs = js;
		if(!mouseOutJs.isEmpty())
		{
			mouseOutHandlerRegistration = addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					EventHelper.getInstance().executeEvent(mouseOutJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseWheelHandler(String js) {
		if(mouseWheelHandlerRegistration != null)
			mouseWheelHandlerRegistration.removeHandler();
		mouseWheelHandlerRegistration = null;
		mouseWheelJs = js.trim();
		if(!mouseWheelJs.isEmpty())
		{
			mouseWheelHandlerRegistration = addMouseWheelHandler(new MouseWheelHandler() {
				@Override
				public void onMouseWheel(MouseWheelEvent event) {
					EventHelper.getInstance().executeEvent(mouseWheelJs, event, true);
				}
			});
		}
	}
	public String getDirectionString()
	{
		return getTextDirection().toString();
	}
	public void setDirection(String direction)
	{
		if(direction.equals(HasDirection.Direction.LTR.toString()))
			setText(getText(), HasDirection.Direction.LTR);
		else if(direction.equals(HasDirection.Direction.RTL.toString()))
			setText(getText(), HasDirection.Direction.RTL);
		else if(direction.equals(HasDirection.Direction.DEFAULT.toString()))
			setText(getText(), HasDirection.Direction.DEFAULT);
		else 
			Window.alert("direction can only take one of the following values: " + HasDirection.Direction.LTR.toString() + "," 
				+ HasDirection.Direction.RTL.toString() + "," +	HasDirection.Direction.DEFAULT.toString());
	}
	public void setHorizontalAlignment(String horizontalAlignment) {
		if(horizontalAlignment.equals(Label.ALIGN_CENTER.getTextAlignString()))
			setHorizontalAlignment(Label.ALIGN_CENTER);
		else if(horizontalAlignment.equals(Label.ALIGN_LEFT.getTextAlignString()))
			setHorizontalAlignment(Label.ALIGN_LEFT);
		else if(horizontalAlignment.equals(Label.ALIGN_RIGHT.getTextAlignString()))
			setHorizontalAlignment(Label.ALIGN_RIGHT);
		else
			Window.alert("direction can only take one of the following values: " + Label.ALIGN_CENTER.getTextAlignString() + "," 
				+ Label.ALIGN_LEFT.getTextAlignString() + "," +	Label.ALIGN_RIGHT.getTextAlignString());
	}
	@Override
	public void setWordWrap(boolean wrap)
	{
		super.setWordWrap(wrap);
	}
	@Override
	public boolean getWordWrap()
	{
		return super.getWordWrap();
	}
	public String getHorizontalAlignmentString()
	{
		if(getHorizontalAlignment() != null)
			return getHorizontalAlignment().getTextAlignString();
		else
			return "left";
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
	public void setText(String text)
	{
		super.setText(text);
	}
	@Override
	@Export
	public String getText()
	{
		return super.getText();
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
		// TODO Auto-generated method stub
		return null;
	}
}
