package com.vk.gwt.designer.client.widgets;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAllKeyHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDoubleClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkPushButton extends PushButton implements IVkWidget, HasVkEnabled, HasVkAccessKey, HasVkTabIndex, HasVkAllMouseHandlers
, HasVkAllKeyHandlers, HasVkFocusHandler, HasVkBlurHandler{
	final public static String NAME = "Push Button";

	private HandlerRegistration clickHandlerRegistration;
	private HandlerRegistration mouseDownHandlerRegistration;
	private HandlerRegistration doubleClickHandlerRegistration;
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
	private String doubleClickJs = "";
	private String imageUpSrc;
	private String imageDownSrc;
	private String imageUpHoveringSrc;
	private String imageUpDisabledSrc;
	private String imageDownDisabledSrc;
	private String imageDownHoveringSrc;
	private char accessKey;
	private boolean allowHoldDown = false;
	private boolean isEnabled = true;
	
	public VkPushButton()
	{
		super("Up","Down");
	}
	
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
		else if(eventName.equals(HasVkMouseDownHandler.NAME))
			return mouseDownJs;
		else if(eventName.equals(HasVkDoubleClickHandler.NAME))
			return doubleClickJs;
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
					VkDesignerUtil.executeEvent(clickJs, event, true);
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
					VkDesignerUtil.executeEvent(doubleClickJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseDownJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseUpJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseMoveJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseOverJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseOutJs, event, true);
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
					VkDesignerUtil.executeEvent(mouseWheelJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyDownHandler(String js) {
		if(keyDownHandlerRegistration != null)
			keyDownHandlerRegistration.removeHandler();
		keyDownHandlerRegistration = null;
		keyDownJs = js.trim();
		if(!keyDownJs.isEmpty())
		{
			keyDownHandlerRegistration = addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					VkDesignerUtil.executeEvent(keyDownJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyUpHandler(String js) {
		if(keyUpHandlerRegistration != null)
			keyUpHandlerRegistration.removeHandler();
		keyUpHandlerRegistration = null;
		keyUpJs = js.trim();
		if(!keyUpJs.isEmpty())
		{
			keyUpHandlerRegistration = addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					VkDesignerUtil.executeEvent(keyUpJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyPressHandler(String js) {
		if(keyPressHandlerRegistration != null)
			keyPressHandlerRegistration.removeHandler();
		keyPressHandlerRegistration = null;
		keyPressJs = js.trim();
		if(!keyPressJs.isEmpty())
		{
			keyPressHandlerRegistration = addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					VkDesignerUtil.executeEvent(keyPressJs, event, true);
				}
			});
		}
	}
	@Override
	public void addFocusHandler(String js) {
		if(focusHandlerRegistration != null)
			focusHandlerRegistration.removeHandler();
		focusHandlerRegistration = null;
		focusJs = js.trim();
		if(!focusJs.isEmpty())
		{
			focusHandlerRegistration = addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					VkDesignerUtil.executeEvent(focusJs, event, true);
				}
			});
		}
	}
	@Override
	public void addBlurHandler(String js) {
		if(blurHandlerRegistration != null)
			blurHandlerRegistration.removeHandler();
		blurHandlerRegistration = null;
		blurJs = js.trim();
		if(!blurJs.isEmpty())
		{
			blurHandlerRegistration = addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					VkDesignerUtil.executeEvent(blurJs, event, true);
				}
			});
		}
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
	public void setTabIndex(int tabIndex)
	{
		super.setTabIndex(tabIndex);
	}
	@Override
	public int getTabIndex()
	{
		return super.getTabIndex();
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	public String getImageUpSrc() {
		return imageUpSrc;
	}
	public void setImageUpSrc(String imageUpSrc) {
		this.imageUpSrc = imageUpSrc;
	}
	public String getImageDownSrc() {
		return imageDownSrc;
	}
	public void setImageDownSrc(String imageDownSrc) {
		this.imageDownSrc = imageDownSrc;
	}
	public String getImageUpHoveringSrc() {
		return imageUpHoveringSrc;
	}
	public void setImageUpHoveringSrc(String imageUpHoveringSrc) {
		this.imageUpHoveringSrc = imageUpHoveringSrc;
	}
	public String getImageUpDisabledSrc() {
		return imageUpDisabledSrc;
	}
	public void setImageUpDisabledSrc(String imageUpDisabledSrc) {
		this.imageUpDisabledSrc = imageUpDisabledSrc;
	}
	public String getImageDownDisabledSrc() {
		return imageDownDisabledSrc;
	}
	public void setImageDownDisabledSrc(String imageDownDisabledSrc) {
		this.imageDownDisabledSrc = imageDownDisabledSrc;
	}
	public String getImageDownHoveringSrc() {
		return imageDownHoveringSrc;
	}
	public void setImageDownHoveringSrc(String imageDownHoveringSrc) {
		this.imageDownHoveringSrc = imageDownHoveringSrc;
	}
	@Override
	public void clone(Widget targetWidget) {
		VkPushButton targetButton = (VkPushButton)targetWidget;
		targetButton.imageDownDisabledSrc = imageDownDisabledSrc;
		targetButton.imageDownHoveringSrc= imageDownHoveringSrc;
		targetButton.imageDownSrc = imageDownSrc;
		targetButton.imageUpDisabledSrc = imageUpDisabledSrc;
		targetButton.imageUpSrc = imageUpSrc;
		targetButton.imageUpHoveringSrc = imageUpHoveringSrc;
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	  protected void onClick() {
		if(allowHoldDown)
		{
			boolean down = isDown();
			super.onClick();
			if(down)
				setDown(false);
			else
				setDown(true);
		}
		else
			super.onClick();
	  }
	@Override
	protected void onClickStart() {
		if(!allowHoldDown)
			super.onClickStart();
	}
	public boolean isAllowHoldDown() {
		return allowHoldDown;
	}

	public void setAllowHoldDown(boolean allowHoldDown) {
		this.allowHoldDown = allowHoldDown;
	}
	@Override
	public void setDown(boolean down){
		super.setDown(down);
	}
	@Override
	public boolean isDown(){
		return super.isDown();
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
	@Export
	public void setVkPushButtonEabled(boolean enabled)
	{
		if(!VkDesignerUtil.isDesignerMode)
			super.setEnabled(enabled);
		else if(!enabled)
			Window.alert("Widget has been disabled and will appear so in preview \n but in designer mode i.e. now, it will appear enabled ");
		isEnabled = enabled;
	}
	@Export
	public boolean isVkPushButtonEnabled()
	{
		return isEnabled;
	}
	@Override
	@Export
	public void setFocus(boolean focused)
	{
		super.setFocus(focused);
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
	@Export
	public String getDownText()
	{
		return getDownFace().getText();
	}
	@Export
	public String getDownHTML()
	{
		return getDownFace().getHTML();
	}
	@Export
	public String getDownImageSrc()
	{
		return imageDownSrc;
	}
	@Export
	public String getDownHoveringText()
	{
		return getDownHoveringFace().getText();
	}
	@Export
	public String getDownHoveringHTML()
	{
		return getDownHoveringFace().getHTML();
	}
	@Export
	public String getDownHoveringImageSrc()
	{
		return imageDownHoveringSrc;
	}
	@Export
	public String getDownDisabledText()
	{
		return getDownFace().getText();
	}
	@Export
	public String getDownDisabledHTML()
	{
		return getDownFace().getHTML();
	}
	@Export
	public String getDownDisabledImageSrc()
	{
		return imageDownDisabledSrc;
	}
	@Export
	public void setUpText(String text)
	{
		getUpFace().setText(text);
	}
	@Export
	public void setUpHTML(String html)
	{
		getUpFace().setHTML(html);
	}
	@Export
	public void setUpImageSrc(String src)
	{
		getUpFace().setImage(new Image(src));
	}
	@Export
	public void setUpHoveringText(String text)
	{
		getUpHoveringFace().setText(text);
	}
	@Export
	public void setUpHoveringHTML(String html)
	{
		getUpHoveringFace().setHTML(html);
	}
	@Export
	public void setUpHoveringImageSrc(String src)
	{
		getUpHoveringFace().setImage(new Image(src));
	}
	@Export
	public void setUpDisabledText(String text)
	{
		getUpFace().setText(text);
	}
	@Export
	public void setUpDisabledHTML(String html)
	{
		getUpFace().setHTML(html);
	}
	@Export
	public void setUpDisabledImageSrc(String src)
	{
		getUpFace().setImage(new Image(src));
	}
	@Export
	public void setDownText(String text)
	{
		getDownFace().setText(text);
	}
	@Export
	public void setDownHTML(String html)
	{
		getDownFace().setHTML(html);
	}
	@Export
	public void setDownImageSrc(String src)
	{
		getDownFace().setImage(new Image(src));
	}
	@Export
	public void setDownHoveringText(String text)
	{
		getDownHoveringFace().setText(text);
	}
	@Export
	public void setDownHoveringHTML(String html)
	{
		getDownHoveringFace().setHTML(html);
	}
	@Export
	public void setDownHoveringImageSrc(String src)
	{
		getDownHoveringFace().setImage(new Image(src));
	}
	@Export
	public void setDownDisabledText(String text)
	{
		getDownFace().setText(text);
	}
	@Export
	public void setDownDisabledHTML(String html)
	{
		getDownFace().setHTML(html);
	}
	@Export
	public void setDownDisabledImageSrc(String src)
	{
		getDownFace().setImage(new Image(src));
	}
}
