package com.vk.gwt.designer.client.ui.widget.text.vkTextBox;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
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
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAllKeyHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkChangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkDoubleClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMaxLength;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkTextBox extends TextBox implements IVkWidget, HasVkText, HasVkAllKeyHandlers, HasVkAllMouseHandlers, HasVkFocusHandler, HasVkBlurHandler
, HasVkChangeHandler, HasVkDirection, HasVkAccessKey, HasVkTabIndex, HasVkEnabled, HasVkMaxLength, HasVkName, HasVkValue<String>{

	public static final String NAME = "Textbox";
	private HandlerRegistration clickHandlerRegistration;
	private HandlerRegistration doubleClickHandlerRegistration;
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
	private HandlerRegistration changeHandlerRegistration;
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
	private String changeJs = "";
	private char accessKey;
	private boolean isEnabled = true;
	
	public VkTextBox(){
		if(VkStateHelper.getInstance().isDesignerMode()){
			super.addKeyDownHandler(new KeyDownHandler(){
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if(event.getNativeKeyCode() == KeyCodes.KEY_DELETE)
						event.stopPropagation();
				}});
		}
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
		mouseOutJs = js.trim();
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
					EventHelper.getInstance().executeEvent(keyDownJs, event, true);
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
					EventHelper.getInstance().executeEvent(keyUpJs, event, true);
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
					EventHelper.getInstance().executeEvent(keyPressJs, event, true);
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
					EventHelper.getInstance().executeEvent(focusJs, event, true);
				}
			});
		}
	}
	@Override
	public void addBlurHandler(String js) {
		if(blurHandlerRegistration != null)
			blurHandlerRegistration.removeHandler();
		blurHandlerRegistration = null;
		blurJs = js;
		if(!js.trim().isEmpty())
		{
			blurHandlerRegistration = addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					EventHelper.getInstance().executeEvent(blurJs, event, true);
				}
			});
		}
	}
	@Override
	public void addChangeHandler(String js) {
		if(changeHandlerRegistration != null)
			changeHandlerRegistration.removeHandler();
		changeHandlerRegistration = null;
		changeJs  = js.trim();
		if(!changeJs.isEmpty())
		{
			changeHandlerRegistration = addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					EventHelper.getInstance().executeEvent(changeJs, event, true);
				}
			});
		}
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
		else if(eventName.equals(HasVkChangeHandler.NAME))
			return changeJs;
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
	public int getCursorPos() {
		return super.getCursorPos();
	}
	@Override
	@Export
	public int getSelectionLength() {
		return super.getSelectionLength();
	}
	@Override
	@Export
	public String getSelectedText() {
		return super.getSelectedText();
	}
	@Override
	@Export
	public void selectAll() {
		super.selectAll();
	}
	@Override
	@Export
	public void setSelectionRange(int pos, int length) {
		super.setSelectionRange(pos, length);
	}
	@Override
	@Export
	public boolean isReadOnly() {
		return super.isReadOnly();
	}
	@Override
	@Export
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly); 
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
	public void setEnabled(boolean enabled)
	{
		if(!VkStateHelper.getInstance().isDesignerMode())
			super.setEnabled(enabled);
		else if(!enabled)
			Window.alert("Widget has been disabled and will appear so in preview \n but in designer mode i.e. now, it will appear enabled ");
		isEnabled = enabled;
	}
	@Override
	@Export
	public boolean isEnabled()
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
	public int getMaxLength()
	{
		return super.getMaxLength();
	}
	@Override
	@Export
	public void setMaxLength(int maxLength)
	{
		super.setMaxLength(maxLength);
	}
	@Override
	@Export
	public void removeMaxLength() {
		DOM.removeElementAttribute(getElement(), "maxLength");
	}
	@Override
	@Export
	public void setName(String name)
	{
		super.setName(name);
	}
	@Override
	@Export
	public String getName()
	{
		return super.getName();
	}
	@Export
	public String getDirectionString()
	{
		return getDirection().toString();
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
	//Not providing exportable method for visibleLength because it only sets the width of the textbox which anyway can be set by designer

	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}
