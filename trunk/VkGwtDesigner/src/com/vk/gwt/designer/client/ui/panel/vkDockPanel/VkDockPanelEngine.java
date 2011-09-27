package com.vk.gwt.designer.client.ui.panel.vkDockPanel;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAlternateText;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoOpen;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFormEncoding;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkHighlightHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkHistoryToken;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkInitializeHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxMultiple;
import com.vk.gwt.designer.client.api.attributes.HasVkMaxLength;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkOpenHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollBarShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkShowRangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitCompleteHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderText;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkValueChangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDockPanelEngine extends VkAbstractWidgetEngine<VkDockPanel>{

	@Override
	public VkDockPanel getWidget() {
		VkDockPanel widget = new VkDockPanel();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(HasVkHorizontalAlignment.NAME))
			((VkDockPanel)invokingWidget).setHorizontalAlignment(attributeName);
		else if(attributeName.equals(HasVkVerticalAlignment.NAME))
			((VkDockPanel)invokingWidget).setVerticalAlignment(attributeName);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> childList = ((IVkPanel)widget).iterator();
			while(childList.hasNext())
			{
				Widget child = childList.next();
				buffer.append("{direction:").append(getDirectionConstantNumber(((VkDockPanel)widget).getWidgetDirection(child))).append(",");
				String align = DOM.getElementAttribute((Element) child.getElement().getParentElement(), "align");
				if(!align.trim().isEmpty())
					buffer.append("horizontalAlignment:").append("'").append(align).append("',");
				String verticalAlign = DOM.getStyleAttribute((Element) child.getElement().getParentElement(), "verticalAlign");
				if(!verticalAlign.trim().isEmpty())
					buffer.append("verticalAlignment:").append("'").append(verticalAlign).append("',");
				buffer.append("child:");
				if(child instanceof IVkWidget)
					buffer.append(VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	private int getDirectionConstantNumber(DockLayoutConstant widgetDirection) {
		if(widgetDirection.equals(DockPanel.CENTER))
			return 0;
		else if(widgetDirection.equals(DockPanel.EAST))
			return 1;
		else if(widgetDirection.equals(DockPanel.LINE_END))
			return 2;
		else if(widgetDirection.equals(DockPanel.LINE_START))
			return 3;
		else if(widgetDirection.equals(DockPanel.NORTH))
			return 4;
		else if(widgetDirection.equals(DockPanel.SOUTH))
			return 5;
		else if(widgetDirection.equals(DockPanel.WEST))
			return 6;
		else
		{
			Window.alert("Serialization failed.");
			throw new IllegalArgumentException("Serialization failed.");
		}
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		addAttributes(jsonObj, parent);
		VkDockPanel dockPanel = (VkDockPanel)parent;
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			dockPanel.add(widget, getDirectionConstant((int)childObj.get("direction").isNumber().doubleValue()));
			dockPanel.setCellHorizontalAlignment(dockPanel.getWidgetCount() - 1, childObj.get("horizontalAlignment").isString().stringValue());
			dockPanel.setCellVerticalAlignment(dockPanel.getWidgetCount() - 1, childObj.get("verticalAlignment").isString().stringValue());
			//addAttributes(childWidgetObj, widget);
			VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
	}
	private DockLayoutConstant getDirectionConstant(int value) {
		if(value == 0)
			return DockPanel.CENTER;
		else if(value == 1)
			return DockPanel.EAST;
		else if(value == 2)
			return DockPanel.LINE_END;
		else if(value == 3)
			return DockPanel.LINE_START;
		else if(value == 4)
			return DockPanel.NORTH;
		else if(value == 5)
			return DockPanel.SOUTH;
		else if(value == 6)
			return DockPanel.WEST;
		else
		{
			Window.alert("JSON String is not well-formed. Application cannot be built.");
			throw new IllegalArgumentException("JSON String is not well-formed. Application cannot be built.");
		}
	}
	//TODO reduce the number of tests
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONNumber attributeNumberObj;
		JSONBoolean attributeBooleanObj;
		JSONValue attributeJsObj = childObj.get("style");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "style", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("title");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "title", attributeStringObj.stringValue()); 
		attributeJsObj = childObj.get("className");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "className", attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkText.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkText)widget).setText(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkAccessKey.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkAccessKey)widget).setAccessKey(attributeStringObj.stringValue().charAt(0));
		attributeJsObj = childObj.get(HasVkTabIndex.NAME);
		if(attributeJsObj != null && (attributeNumberObj = attributeJsObj.isNumber()) != null)
			((HasVkTabIndex)widget).setTabIndex((int)attributeNumberObj.isNumber().doubleValue());
		attributeJsObj = childObj.get(HasVkHtml.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkHtml)widget).setHTML(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkEnabled.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkEnabled)widget).setEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkWordWrap.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkWordWrap)widget).setWordWrap(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkDirection.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkDirection)widget).setDirection(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMaxLength.NAME);
		if(attributeJsObj != null && (attributeNumberObj = attributeJsObj.isNumber()) != null)
			((HasVkMaxLength)widget).setMaxLength((int)attributeNumberObj.doubleValue());
		/*attributeJsObj = childObj.get(HasVkHorizontalAlignment.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkHorizontalAlignment)widget).setHorizontalAlignment(attributeStringObj.stringValue());*/
		attributeJsObj = childObj.get(HasVkAnimation.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAnimation)widget).setAnimationEnabled(attributeBooleanObj.booleanValue());
		/*attributeJsObj = childObj.get(HasVkVerticalAlignment.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkVerticalAlignment)widget).setVerticalAlignment(attributeStringObj.stringValue());*/
		attributeJsObj = childObj.get(HasVkTarget.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkTarget)widget).setTarget(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkUrl.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkUrl)widget).setUrl(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkFormMethod.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkFormMethod)widget).setMethod(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkImageUrl.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkImageUrl)widget).setImageUrl(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkScrollBarShowing.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkScrollBarShowing)widget).setAlwaysShowScrollBars(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkSwitchNumberedWidget.NAME);
		/*if(attributeJsObj != null && (attributeNumberObj = attributeJsObj.isNumber()) != null)
			((HasVkSwitchNumberedWidget)widget).showWidget((int)attributeNumberObj.doubleValue());*/
		attributeJsObj = childObj.get(HasVkName.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkName)widget).setName(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkCaptionText.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkCaptionText)widget).setCaptionText(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkCaptionHtml.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkCaptionHtml)widget).setCaptionHtml(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkAutoHide.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAutoHide)widget).setAutoHideEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkGlass.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkGlass)widget).setGlassEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkGlassStyle.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkGlassStyle)widget).setGlassStyleName(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkModal.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkModal)widget).setModal(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkFormEncoding.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkFormEncoding)widget).setEncoding(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkHistoryToken.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkHistoryToken)widget).setTargetHistoryToken(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkAlternateText.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkAlternateText)widget).setAlt(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkListBoxMultiple.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkListBoxMultiple)widget).setMultipleEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkAutoOpen.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAutoOpen)widget).setAutoOpen(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkInitiallyShowing.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkInitiallyShowing)widget).setInitiallyShowing(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkTabHeaderText.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkTabHeaderText)widget).setTabText(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkTabHeaderHtml.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkTabHeaderHtml)widget).setTabHTML(attributeStringObj.stringValue());

		attributeJsObj = childObj.get(HasVkBlurHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkBlurHandler)widget).addBlurHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkFocusHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkFocusHandler)widget).addFocusHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkKeyDownHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkKeyDownHandler)widget).addKeyDownHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkKeyPressHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkKeyPressHandler)widget).addKeyPressHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkKeyUpHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkKeyUpHandler)widget).addKeyUpHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkClickHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkClickHandler)widget).addClickHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseDownHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseDownHandler)widget).addMouseDownHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseMoveHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseMoveHandler)widget).addMouseMoveHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseUpHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseUpHandler)widget).addMouseUpHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseOutHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseOutHandler)widget).addMouseOutHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseOverHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseOverHandler)widget).addMouseOverHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkMouseWheelHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkMouseWheelHandler)widget).addMouseWheelHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkCloseHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkCloseHandler)widget).addCloseHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkOpenHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkOpenHandler)widget).addOpenHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSubmitHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSubmitHandler)widget).addSubmitHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSubmitCompleteHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSubmitCompleteHandler)widget).addSubmitCompleteHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkScrollHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkScrollHandler)widget).addScrollHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkBeforeSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkBeforeSelectionHandler)widget).addBeforeSelectionHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkSelectionHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkSelectionHandler)widget).addSelectionHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkInitializeHandlers.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkInitializeHandlers)widget).addInitializeHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkValueChangeHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkValueChangeHandler)widget).addValueChangeHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkHighlightHandlers.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkHighlightHandlers)widget).addHighlightHandler(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkShowRangeHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkShowRangeHandler)widget).addShowRangeHandler(attributeStringObj.stringValue());
	}
}
