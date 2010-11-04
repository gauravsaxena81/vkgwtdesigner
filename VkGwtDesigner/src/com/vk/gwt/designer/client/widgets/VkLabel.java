package com.vk.gwt.designer.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.user.client.ui.Label;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkLabel extends Label implements HasVkText, HasVkAllMouseHandlers, HasVkWordWrap{
	final public static String NAME = "Button";
	private HandlerRegistration clickHandlerRegistration;
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
	
	public VkLabel() {
		super();
	}
	@Override
	public void addText(String text) {
		setText(text);
	}
	@Override
	public void addWordWrap(boolean wordWrap) 
	{
		setWordWrap(wordWrap);
	}
	@Override
	public void addClickHandler(final String js) {
		if(clickHandlerRegistration != null)
			clickHandlerRegistration.removeHandler();
		clickJs = js;
		clickHandlerRegistration = addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VkDesignerUtil.executeEvent(clickJs, event);
			}
		});
	}
	@Override
	public String getPriorJs(HasVkEventHandler widget) {
		if(widget instanceof HasVkClickHandler)
			return clickJs;
		else
			return "";
	}
	@Override
	public void addMouseDownHandler(String js) {
		if(mouseDownHandlerRegistration != null)
			mouseDownHandlerRegistration.removeHandler();
		mouseDownJs = js;
		mouseDownHandlerRegistration = addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkDesignerUtil.executeEvent(mouseDownJs, event);
			}
		});
	}
	@Override
	public void addMouseUpHandler(String js) {
		if(mouseUpHandlerRegistration != null)
			mouseUpHandlerRegistration.removeHandler();
		mouseUpJs = js;
		mouseUpHandlerRegistration = addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				VkDesignerUtil.executeEvent(mouseUpJs, event);
			}
		});
	}
	@Override
	public void addMouseMoveHandler(String js) {
		if(mouseMoveHandlerRegistration != null)
			mouseMoveHandlerRegistration.removeHandler();
		mouseMoveJs = js;
		mouseMoveHandlerRegistration = addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				VkDesignerUtil.executeEvent(mouseMoveJs, event);
			}
		});
	}
	@Override
	public void addMouseOverHandler(String js) {
		if(mouseOverHandlerRegistration != null)
			mouseOverHandlerRegistration.removeHandler();
		mouseOverJs = js;
		mouseOverHandlerRegistration = addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				VkDesignerUtil.executeEvent(mouseOverJs, event);
			}
		});
	}
	@Override
	public void addMouseOutHandler(String js) {
		if(mouseOutHandlerRegistration != null)
			mouseOutHandlerRegistration.removeHandler();
		mouseOutJs = js;
		mouseOutHandlerRegistration = addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				VkDesignerUtil.executeEvent(mouseOutJs, event);
			}
		});
	}
	@Override
	public void addMouseWheelHandler(String js) {
		if(mouseWheelHandlerRegistration != null)
			mouseWheelHandlerRegistration.removeHandler();
		mouseWheelJs = js;
		mouseWheelHandlerRegistration = addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				VkDesignerUtil.executeEvent(mouseWheelJs, event);
			}
		});
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
	public void setWordWrap(boolean wrap)
	{
		super.setWordWrap(wrap);
	}
	@Override
	@Export
	public boolean getWordWrap()
	{
		return super.getWordWrap();
	}
}
