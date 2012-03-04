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
package com.vk.gwt.designer.client.ui.widget.vkDateBox;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.google.gwt.event.logical.shared.HighlightEvent;
import com.google.gwt.event.logical.shared.HighlightHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
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
import com.vk.gwt.designer.client.api.attributes.HasVkHighlightHandlers;
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
import com.vk.gwt.designer.client.api.attributes.HasVkShowRangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.attributes.HasVkValueChangeHandler;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDateBox extends DateBox implements IVkWidget, HasVkText, HasVkAllKeyHandlers, HasVkAllMouseHandlers, HasVkFocusHandler, HasVkBlurHandler
, HasVkChangeHandler, HasVkValueChangeHandler, HasVkHighlightHandlers, HasVkShowRangeHandler, HasVkDirection, HasVkAccessKey, HasVkTabIndex, HasVkEnabled
, HasVkMaxLength, HasVkName, HasVkValue<Date>{

	public static final String NAME = "Date Box";
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
	private HandlerRegistration valueChangeHandlerRegistration;
	private HandlerRegistration highlightHandlerRegistration;
	private HandlerRegistration showRangeHandlerRegistration;
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
	private String valueChangeJs = "";
	private String highlightJs = "";
	private String showRangeJs = "";
	private char accessKey;
	private String pattern = "";
	private boolean isEnabled = true;
	private IVkWidget vkParent;
	
	public VkDateBox()
	{
		if(VkStateHelper.getInstance().isDesignerMode()){
			super.getTextBox().addKeyDownHandler(new KeyDownHandler(){
				@Override
				public void onKeyDown(KeyDownEvent event) {
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
			clickHandlerRegistration = super.getTextBox().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(clickJs, event, true);
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
			doubleClickHandlerRegistration = super.getTextBox().addDoubleClickHandler(new DoubleClickHandler() {
				@Override
				public void onDoubleClick(DoubleClickEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(doubleClickJs, event, true);
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
			mouseDownHandlerRegistration = super.getTextBox().addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseDownJs, event, true);
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
			mouseUpHandlerRegistration = super.getTextBox().addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseUpJs, event, true);
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
			mouseMoveHandlerRegistration = super.getTextBox().addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseMoveJs, event, true);
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
			mouseOverHandlerRegistration = super.getTextBox().addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseOverJs, event, true);
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
			mouseOutHandlerRegistration = super.getTextBox().addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseOutJs, event, true);
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
			mouseWheelHandlerRegistration = super.getTextBox().addMouseWheelHandler(new MouseWheelHandler() {
				@Override
				public void onMouseWheel(MouseWheelEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(mouseWheelJs, event, true);
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
			keyDownHandlerRegistration = super.getTextBox().addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(keyDownJs, event, true);
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
			keyUpHandlerRegistration = super.getTextBox().addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(keyUpJs, event, true);
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
			keyPressHandlerRegistration = super.getTextBox().addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(keyPressJs, event, true);
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
			focusHandlerRegistration = super.getTextBox().addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(focusJs, event, true);
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
			blurHandlerRegistration = super.getTextBox().addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(blurJs, event, true);
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
			changeHandlerRegistration = super.getTextBox().addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					VkStateHelper.getInstance().getEventHelper().executeEvent(changeJs, event, true);
				}
			});
		}
	}
	@Override
	public void addValueChangeHandler(final String js) {
		if(valueChangeHandlerRegistration != null)
			valueChangeHandlerRegistration.removeHandler();
		valueChangeHandlerRegistration = null;
		valueChangeJs = js.trim();
		if(!valueChangeJs.isEmpty())
		{
			valueChangeHandlerRegistration = addValueChangeHandler(new ValueChangeHandler<Date>() {
				@Override
				public void onValueChange(ValueChangeEvent<Date> event) {
					Map<String, String> eventProperties = new HashMap<String, String>();
					eventProperties.put("date", Long.toString(event.getValue().getTime()));
					VkStateHelper.getInstance().getEventHelper().executeEvent(valueChangeJs, eventProperties);
				}
			});
		}
	}
	@Override
	public void addHighlightHandler(String js) {
		if(highlightHandlerRegistration != null)
			highlightHandlerRegistration.removeHandler();
		highlightHandlerRegistration = null;
		highlightJs = js.trim();
		if(!highlightJs.isEmpty())
		{
			highlightHandlerRegistration = getDatePicker().addHighlightHandler(new HighlightHandler<Date>() {
				@Override
				public void onHighlight(HighlightEvent<Date> event) {
					Map<String, String> eventProperties = new HashMap<String, String>();
					eventProperties.put("date", Long.toString(event.getHighlighted().getTime()));
					VkStateHelper.getInstance().getEventHelper().executeEvent(highlightJs, eventProperties);
				}
			});
		}
	}
	@Override
	public void addShowRangeHandler(String js) {
		if(showRangeHandlerRegistration != null)
			showRangeHandlerRegistration.removeHandler();
		showRangeHandlerRegistration = null;
		showRangeJs = js.trim();
		if(!showRangeJs.isEmpty())
		{
			showRangeHandlerRegistration = getDatePicker().addShowRangeHandler(new ShowRangeHandler<Date>() {
				@Override
				public void onShowRange(ShowRangeEvent<Date> event) {
					Map<String, String> eventProperties = new HashMap<String, String>();
					eventProperties.put("startDate", Long.toString(event.getStart().getTime()));
					eventProperties.put("endDate", Long.toString(event.getEnd().getTime()));
					VkStateHelper.getInstance().getEventHelper().executeEvent(showRangeJs, eventProperties);
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
		else if(eventName.equals(HasVkValueChangeHandler.NAME))
			return valueChangeJs;
		else if(eventName.equals(HasVkHighlightHandlers.NAME))
			return highlightJs;
		else if(eventName.equals(HasVkShowRangeHandler.NAME))
			return showRangeJs;
		else return "";
	}
	public void setDirection(String direction)
	{
		if(direction.equals(HasDirection.Direction.LTR.toString()))
			super.getTextBox().setDirection(HasDirection.Direction.LTR);
		else if(direction.equals(HasDirection.Direction.RTL.toString()))
			super.getTextBox().setDirection(HasDirection.Direction.RTL);
		else if(direction.equals(HasDirection.Direction.DEFAULT.toString()))
			super.getTextBox().setDirection(HasDirection.Direction.DEFAULT);
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
	public Format getFormat()
	{
		return super.getFormat();
	}
	@Override
	public void setFormat(Format format)
	{
		super.setFormat(format);
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	@Override
	public void clone(Widget targetWidget) {
		((VkDateBox)targetWidget).pattern = pattern;
		((VkDateBox)targetWidget).setText(getText());
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public void removeFromParent(){
		super.hideDatePicker();
		super.removeFromParent();
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
	@Export
	public int getSelectionLength() {
		return super.getTextBox().getSelectionLength();
	}
	@Export
	public String getSelectedText() {
		return super.getTextBox().getSelectedText();
	}
	@Export
	public void selectAll() {
		super.getTextBox().selectAll();
	}
	@Export
	public void setSelectionRange(int pos, int length) {
		super.getTextBox().setSelectionRange(pos, length);
	}
	@Export
	public boolean isReadOnly() {
		return super.getTextBox().isReadOnly();
	}
	@Export
	public void setReadOnly(boolean readOnly) {
		super.getTextBox().setReadOnly(readOnly); 
	}
	@Override
	@Export
	public void setText(String text)
	{
		super.getTextBox().setText(text);
	}
	@Override
	@Export
	public String getText()
	{
		return super.getTextBox().getText();
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
		return super.getTextBox().getMaxLength();
	}
	@Override
	@Export
	public void setMaxLength(int maxLength)
	{
		super.getTextBox().setMaxLength(maxLength);
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
		super.getTextBox().setName(name);
	}
	@Override
	@Export
	public String getName()
	{
		return super.getTextBox().getName();
	}
	@Export
	public String getDirectionString()
	{
		return super.getTextBox().getDirection().toString();
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
	public void setValue(String value) {
		Date date = getFormat().parse(this, value, false);
		if(date == null)
			throw new IllegalArgumentException("Date parse error");
		else
			super.setValue(date);
	}
	@Override
	@Export
	public void setValue(Date value) {
		super.setValue(value);
	}
	@SuppressWarnings("serial")
	@Override
	@Export
	public Date getValue() {
		return new Date(){
			@Override
			public String toString()
			{
				return VkDateBox.super.getFormat().format(VkDateBox.this, VkDateBox.super.getValue());
			}
		};
	}
	@Override
	@Export
	public void hideDatePicker() {
		super.hideDatePicker();
	}
	@Override
	@Export
	public boolean isDatePickerShowing() {
		return super.isDatePickerShowing();
	}
	@Override
	@Export
	public void showDatePicker() {
		super.showDatePicker();
	}

	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
