package com.vk.gwt.designer.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAllKeyHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkAnchor extends Anchor implements IVkWidget, HasVkAllKeyHandlers, HasVkAllMouseHandlers, HasVkFocusHandler, HasVkBlurHandler
, HasVkHorizontalAlignment,HasVkName, HasVkHtml, HasVkText, HasVkWordWrap, HasVkDirection, HasVkAccessKey, HasVkTabIndex, HasVkUrl, HasVkTarget{
	public static final String NAME = "Anchor";
	private HandlerRegistration clickHandlerRegistration;
	private HandlerRegistration mouseDownHandlerRegistration;
	private HandlerRegistration mouseUpHandlerRegistration;
	private HandlerRegistration mouseMoveHandlerRegistration;
	private HandlerRegistration mouseOutHandlerRegistration;
	private HandlerRegistration mouseOverHandlerRegistration;
	private HandlerRegistration mouseWheelHandlerRegistration;
	private HandlerRegistration keyUpHandlerRegistration;
	private HandlerRegistration keyDownHandlerRegistration;
	private HandlerRegistration keyPressHandlerRegistration;
	private HandlerRegistration focusHandlerRegistration;
	private HandlerRegistration blurHandlerRegistration;
	private String mouseDownJs = "";
	private String mouseUpJs = "";
	private String mouseMoveJs = "";
	private String mouseOutJs = "";
	private String mouseOverJs = "";
	private String mouseWheelJs = "";
	private String keyDownJs = "";
	private String keyUpJs = "";
	private String keyPressJs = "";
	private String focusJs = "";
	private String blurJs = "";
	private String clickJs = "";
	private char accessKey;

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
	@Override
	public void addKeyDownHandler(String js) {
		if(keyDownHandlerRegistration != null)
			keyDownHandlerRegistration.removeHandler();
		keyDownJs = js;
		keyDownHandlerRegistration = addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				VkDesignerUtil.executeEvent(keyDownJs, event);
			}
		});
	}
	@Override
	public void addKeyUpHandler(String js) {
		if(keyUpHandlerRegistration != null)
			keyUpHandlerRegistration.removeHandler();
		keyUpJs = js;
		keyUpHandlerRegistration = addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				VkDesignerUtil.executeEvent(keyUpJs, event);
			}
		});
	}
	@Override
	public void addKeyPressHandler(String js) {
		if(keyPressHandlerRegistration != null)
			keyPressHandlerRegistration.removeHandler();
		keyPressJs = js;
		keyPressHandlerRegistration = addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				VkDesignerUtil.executeEvent(keyPressJs, event);
			}
		});
	}
	@Override
	public void addFocusHandler(String js) {
		if(focusHandlerRegistration != null)
			focusHandlerRegistration.removeHandler();
		focusJs = js;
		focusHandlerRegistration = addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				VkDesignerUtil.executeEvent(focusJs, event);
			}
		});
	}
	@Override
	public void addBlurHandler(String js) {
		if(blurHandlerRegistration != null)
			blurHandlerRegistration.removeHandler();
		blurJs = js;
		blurHandlerRegistration = addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				VkDesignerUtil.executeEvent(blurJs, event);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
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
		else if(eventName.equals(HasVkKeyUpHandler.NAME))
			return keyUpJs;
		else if(eventName.equals(HasVkKeyDownHandler.NAME))
			return keyDownJs;
		else if(eventName.equals(HasVkKeyPressHandler.NAME))
			return keyPressJs;
		else if(eventName.equals(HasVkFocusHandler.NAME))
			return focusJs;
		else if(eventName.equals(HasVkBlurHandler.NAME))
			return blurJs;
		else return "";
	}
	public void setDirection(String direction)
	{
		if(direction.equals(HasDirection.Direction.LTR.toString()))
			setDirection(HasDirection.Direction.LTR);
		else if(direction.equals(HasDirection.Direction.RTL.toString()))
			setDirection(HasDirection.Direction.RTL);
		else if(direction.equals(HasDirection.Direction.DEFAULT.toString()))
			setDirection(HasDirection.Direction.DEFAULT);
		else 
			Window.alert("direction can only take one of the following values: " + HasDirection.Direction.LTR.toString() + "," 
				+ HasDirection.Direction.RTL.toString() + "," +	HasDirection.Direction.DEFAULT.toString());
	}
	@Override
	public char getAccessKey()
	{
		return accessKey;
	}
	@Override
	public void setAccessKey(char key)
	{
		accessKey = key;
		super.setAccessKey(key);
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public String getHorizontalAlignmentString()
	{
		if(getHorizontalAlignment() != null)
			return getHorizontalAlignment().getTextAlignString();
		else
			return "left";
	}
	@Override
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
	public String getDirectionString()
	{
		return getDirection().toString();
	}
	@Override
	public void clone(Widget targetWidget) {}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getUrl() {
		return super.getHref();
	}
	@Override
	@Export
	public void setUrl(String url) {
		super.setHref(url);
	}
	@Override
	@Export
	public void setFocus(boolean focused) {
		super.setFocus(focused);
	}
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
	public void setHTML(String html)
	{
		super.setHTML(html);
	}
	@Override
	@Export
	public String getHTML()
	{
		return super.getHTML();
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
}
