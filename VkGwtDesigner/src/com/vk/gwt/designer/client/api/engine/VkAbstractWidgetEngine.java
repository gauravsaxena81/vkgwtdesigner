package com.vk.gwt.designer.client.api.engine;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
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
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
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
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxMultiple;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxRenderMode;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
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
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.attributes.HasVkValueChangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public abstract class VkAbstractWidgetEngine<T extends Widget> implements IWidgetEngine<T> {
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		return VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
	}
	@Override
	public List<String> getOperationsList(Widget invokingWidget)
	{
		return VkDesignerUtil.getEngine().getOperationsList(invokingWidget);
	}
	protected void init(Widget widget) {
		widget.setPixelSize(100, 20);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px gray");
	}
	@Override
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		if(sourceWidget instanceof IPanel && targetWidget instanceof IPanel)
		{
			Iterator<Widget> widgets = ((IPanel)sourceWidget).iterator();
			while(widgets.hasNext())
			{
				Widget currentWidget = widgets.next();
				if(currentWidget instanceof IVkWidget)
					((IPanel)targetWidget).add(deepClone(currentWidget, VkDesignerUtil.getEngine().getWidget(((IVkWidget)currentWidget).getWidgetName())));
			}
		}
		((IVkWidget)sourceWidget).clone(targetWidget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		return targetWidget;
	}
	public void copyAttributes(Widget widgetSource, Widget widgetTarget)
	{
		DOM.setElementAttribute(widgetTarget.getElement(), "style", DOM.getElementAttribute(widgetSource.getElement(), "style"));
		widgetTarget.setStyleName(widgetSource.getStyleName());
		widgetTarget.setTitle(widgetSource.getTitle());
		if(widgetSource instanceof HasVkText)
			((HasVkText)widgetTarget).setText(((HasVkText)widgetSource).getText());
		if(widgetSource instanceof HasVkAccessKey)
			((HasVkAccessKey)widgetTarget).setAccessKey(((HasVkAccessKey)widgetSource).getAccessKey());
		if(widgetSource instanceof HasVkTabIndex)
			((HasVkTabIndex)widgetTarget).setTabIndex(((HasVkTabIndex)widgetSource).getTabIndex());
		if(widgetSource instanceof HasVkHtml)
			((HasVkHtml)widgetTarget).setHTML(((HasVkHtml)widgetSource).getHTML());
		if(widgetSource instanceof HasVkEnabled)
			((HasVkEnabled)widgetTarget).setEnabled(((HasVkEnabled)widgetSource).isEnabled());
		if(widgetSource instanceof HasVkWordWrap)
			((HasVkWordWrap)widgetTarget).setWordWrap(((HasVkWordWrap)widgetSource).getWordWrap());
		if(widgetSource instanceof HasVkDirection)
			((HasVkDirection)widgetTarget).setDirection(((HasVkDirection)widgetSource).getDirectionString());
		if(widgetSource instanceof HasVkMaxLength)
			((HasVkMaxLength)widgetTarget).setMaxLength(((HasVkMaxLength)widgetSource).getMaxLength());
		if(widgetSource instanceof HasVkHorizontalAlignment)
			((HasVkHorizontalAlignment)widgetTarget).setHorizontalAlignment(((HasVkHorizontalAlignment)widgetSource).getHorizontalAlignmentString());
		if(widgetSource instanceof HasVkAnimation)
			((HasVkAnimation)widgetTarget).setAnimationEnabled(((HasVkAnimation)widgetSource).isAnimationEnabled());
		if(widgetSource instanceof HasVkVerticalAlignment)
			((HasVkVerticalAlignment)widgetTarget).setVerticalAlignment(((HasVkVerticalAlignment)widgetSource).getVerticalAlignmentString());
		if(widgetSource instanceof HasVkTarget)
			((HasVkTarget)widgetTarget).setTarget(((HasVkTarget)widgetSource).getTarget());
		if(widgetSource instanceof HasVkUrl)
			((HasVkUrl)widgetTarget).setUrl(((HasVkUrl)widgetSource).getUrl());
		if(widgetSource instanceof HasVkFormMethod)
			((HasVkFormMethod)widgetTarget).setMethod(((HasVkFormMethod)widgetSource).getMethod());
		if(widgetSource instanceof HasVkImageUrl)
			((HasVkImageUrl)widgetTarget).setImageUrl(((HasVkImageUrl)widgetSource).getImageUrl());
		if(widgetSource instanceof HasVkScrollBarShowing)
			((HasVkScrollBarShowing)widgetTarget).setAlwaysShowScrollBars(((HasVkScrollBarShowing)widgetSource).isAlwaysShowScrollBars());
		if(widgetSource instanceof HasVkSwitchNumberedWidget)
			((HasVkSwitchNumberedWidget)widgetTarget).showWidget(((HasVkSwitchNumberedWidget)widgetSource).getCurrentlyShowingWidget());
		if(widgetSource instanceof HasVkName)
			((HasVkName)widgetTarget).setName(((HasVkName)widgetSource).getName());
		if(widgetSource instanceof HasVkCaptionText)
			((HasVkCaptionText)widgetTarget).setCaptionText(((HasVkCaptionText)widgetSource).getCaptionText());
		if(widgetSource instanceof HasVkCaptionHtml)
			((HasVkCaptionHtml)widgetTarget).setCaptionHtml(((HasVkCaptionHtml)widgetSource).getCaptionHtml());
		if(widgetSource instanceof HasVkAutoHide)
			((HasVkAutoHide)widgetTarget).setAutoHideEnabled(((HasVkAutoHide)widgetSource).isAutoHideEnabled());
		if(widgetSource instanceof HasVkGlass)
			((HasVkGlass)widgetTarget).setGlassEnabled(((HasVkGlass)widgetSource).isGlassEnabled());
		if(widgetSource instanceof HasVkGlassStyle)
			((HasVkGlassStyle)widgetTarget).setGlassStyleName(((HasVkGlassStyle)widgetSource).getGlassStyleName());
		if(widgetSource instanceof HasVkModal)
			((HasVkModal)widgetTarget).setModal(((HasVkModal)widgetSource).isModal());
		if(widgetSource instanceof HasVkFormEncoding)
			((HasVkFormEncoding)widgetTarget).setEncoding(((HasVkFormEncoding)widgetSource).getEncoding());
		if(widgetSource instanceof HasVkHistoryToken)
			((HasVkHistoryToken)widgetTarget).setTargetHistoryToken(((HasVkHistoryToken)widgetSource).getTargetHistoryToken());
		if(widgetSource instanceof HasVkAlternateText)
			((HasVkAlternateText)widgetTarget).setAlt(((HasVkAlternateText)widgetSource).getAlt());
		if(widgetSource instanceof HasVkListBoxMultiple)
			((HasVkListBoxMultiple)widgetTarget).setMultipleEnabled(((HasVkListBoxMultiple)widgetSource).isMultipleEnabled());
		if(widgetSource instanceof HasVkListBoxRenderMode)
			((HasVkListBoxRenderMode)widgetTarget).setDropDown(((HasVkListBoxRenderMode)widgetSource).isDropDown());
		if(widgetSource instanceof HasVkAutoOpen)
			((HasVkAutoOpen)widgetTarget).setAutoOpen(((HasVkAutoOpen)widgetSource).getAutoOpen());
		if(widgetSource instanceof HasVkTabHeaderText)
			((HasVkTabHeaderText)widgetTarget).setTabText(((HasVkTabHeaderText)widgetSource).getTabText());
		if(widgetSource instanceof HasVkTabHeaderHtml)
			((HasVkTabHeaderHtml)widgetTarget).setTabHTML(((HasVkTabHeaderHtml)widgetSource).getTabHTML());
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IPanel)
		{
			Iterator<Widget> widgetList = ((IPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				if(child instanceof IVkWidget)
					buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	@SuppressWarnings("unchecked")
	protected void serializeAttributes(StringBuffer buffer, Widget widgetSource)
	{
		if(!widgetSource.getStyleName().isEmpty())
			buffer.append(",className:'" + widgetSource.getStyleName() + "'");
		if(!widgetSource.getTitle().isEmpty())
			buffer.append(",title:'" + widgetSource.getTitle() + "'");
		if(!widgetSource.getElement().getId().isEmpty())
			buffer.append(",id:'" + widgetSource.getElement().getId() + "'");
		if(widgetSource instanceof HasVkText && !((HasVkText)widgetSource).getText().isEmpty())
			buffer.append(",'").append(HasVkText.NAME).append("':").append("'").append(((HasVkText)widgetSource).getText().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkAccessKey && ((HasVkAccessKey)widgetSource).getAccessKey() != 0)
			buffer.append(",'" ).append(HasVkAccessKey.NAME).append("':'").append(((HasVkAccessKey)widgetSource).getAccessKey()).append("'");
		if(widgetSource instanceof HasVkTabIndex)
			buffer.append(",'" ).append(HasVkTabIndex.NAME).append("':").append(((HasVkTabIndex)widgetSource).getTabIndex());
		if(widgetSource instanceof HasVkHtml && !((HasVkHtml)widgetSource).getHTML().isEmpty())
			buffer.append(",'" ).append(HasVkHtml.NAME).append("':").append("'").append(((HasVkHtml)widgetSource).getHTML().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkEnabled)
			buffer.append(",'" ).append(HasVkEnabled.NAME).append("':").append(((HasVkEnabled)widgetSource).isEnabled());
		if(widgetSource instanceof HasVkWordWrap)
			buffer.append(",'" ).append(HasVkWordWrap.NAME).append("':").append(((HasVkWordWrap)widgetSource).getWordWrap());
		if(widgetSource instanceof HasVkDirection && !((HasVkDirection)widgetSource).getDirectionString().isEmpty())
			buffer.append(",'" ).append(HasVkDirection.NAME).append("':").append("'").append(((HasVkDirection)widgetSource).getDirectionString()).append("'");
		if(widgetSource instanceof HasVkMaxLength)
			buffer.append(",'" ).append(HasVkMaxLength.NAME).append("':").append(((HasVkMaxLength)widgetSource).getMaxLength());
		if(widgetSource instanceof HasVkHorizontalAlignment && !((HasVkHorizontalAlignment)widgetSource).getHorizontalAlignmentString().isEmpty())
			buffer.append(",'" ).append(HasVkHorizontalAlignment.NAME).append("':").append("'")
				.append(((HasVkHorizontalAlignment)widgetSource).getHorizontalAlignmentString()).append("'");
		if(widgetSource instanceof HasVkAnimation)
			buffer.append(",'" ).append(HasVkAnimation.NAME).append("':").append(((HasVkAnimation)widgetSource).isAnimationEnabled());
		if(widgetSource instanceof HasVkVerticalAlignment && !((HasVkVerticalAlignment)widgetSource).getVerticalAlignmentString().isEmpty())
			buffer.append(",'" ).append(HasVkVerticalAlignment.NAME).append("':").append("'")
				.append(((HasVkVerticalAlignment)widgetSource).getVerticalAlignmentString()).append("'");
		if(widgetSource instanceof HasVkTarget && ((HasVkTarget)widgetSource).getTarget() != null && !((HasVkTarget)widgetSource).getTarget().isEmpty())
			buffer.append(",'" ).append(HasVkTarget.NAME).append("':").append("'").append(((HasVkTarget)widgetSource).getTarget().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkUrl && !((HasVkUrl)widgetSource).getUrl().isEmpty())
			buffer.append(",'" ).append(HasVkUrl.NAME).append("':").append("'").append(((HasVkUrl)widgetSource).getUrl().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkFormMethod && !((HasVkFormMethod)widgetSource).getMethod().isEmpty())
			buffer.append(",'" ).append(HasVkFormMethod.NAME).append("':").append("'").append(((HasVkFormMethod)widgetSource).getMethod()).append("'");
		if(widgetSource instanceof HasVkImageUrl && !((HasVkImageUrl)widgetSource).getImageUrl().isEmpty())
			buffer.append(",'" ).append(HasVkImageUrl.NAME).append("':").append("'").append(((HasVkImageUrl)widgetSource).getImageUrl().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkScrollBarShowing)
			buffer.append(",'" ).append(HasVkScrollBarShowing.NAME).append("':").append(((HasVkScrollBarShowing)widgetSource).isAlwaysShowScrollBars());
		if(widgetSource instanceof HasVkName && !((HasVkName)widgetSource).getName().isEmpty())
			buffer.append(",'" ).append(HasVkName.NAME).append("':").append("'").append(((HasVkName)widgetSource).getName().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkValue && ((HasVkValue)widgetSource).getValue() != null && !((HasVkValue)widgetSource).getValue().toString().isEmpty())
			buffer.append(",'" ).append(HasVkValue.NAME).append("':").append("'").append(((HasVkValue)widgetSource).getValue().toString().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkCaptionText && !((HasVkCaptionText)widgetSource).getCaptionText().isEmpty())
			buffer.append(",'" ).append(HasVkCaptionText.NAME).append("':").append("'").append(((HasVkCaptionText)widgetSource).getCaptionText().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkCaptionHtml && !((HasVkCaptionHtml)widgetSource).getCaptionHtml().isEmpty())
			buffer.append(",'" ).append(HasVkCaptionHtml.NAME).append("':").append("'").append(((HasVkCaptionHtml)widgetSource).getCaptionHtml().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkAutoHide)
			buffer.append(",'" ).append(HasVkAutoHide.NAME).append("':").append(((HasVkAutoHide)widgetSource).isAutoHideEnabled());
		if(widgetSource instanceof HasVkGlass)
			buffer.append(",'" ).append(HasVkGlass.NAME).append("':").append(((HasVkGlass)widgetSource).isGlassEnabled());
		if(widgetSource instanceof HasVkGlassStyle && !((HasVkGlassStyle)widgetSource).getGlassStyleName().isEmpty())
			buffer.append(",'" ).append(HasVkGlassStyle.NAME).append("':").append("'").append(((HasVkGlassStyle)widgetSource).getGlassStyleName().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkFormEncoding && !((HasVkFormEncoding)widgetSource).getEncoding().isEmpty())
			buffer.append(",'" ).append(HasVkFormEncoding.NAME).append("':").append("'").append(((HasVkFormEncoding)widgetSource).getEncoding()).append("'");
		if(widgetSource instanceof HasVkHistoryToken && !((HasVkHistoryToken)widgetSource).getTargetHistoryToken().isEmpty())
			buffer.append(",'" ).append(HasVkHistoryToken.NAME).append("':").append("'").append(((HasVkHistoryToken)widgetSource).getTargetHistoryToken().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkAlternateText && !((HasVkAlternateText)widgetSource).getAlt().isEmpty())
			buffer.append(",'" ).append(HasVkAlternateText.NAME).append("':").append("'").append(((HasVkAlternateText)widgetSource).getAlt().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkListBoxMultiple)
			buffer.append(",'" ).append(HasVkListBoxMultiple.NAME).append("':").append(((HasVkListBoxMultiple)widgetSource).isMultipleEnabled());
		if(widgetSource instanceof HasVkListBoxRenderMode)
			buffer.append(",'" ).append(HasVkListBoxRenderMode.NAME).append("':").append(((HasVkListBoxRenderMode)widgetSource).isDropDown());
		if(widgetSource instanceof HasVkAutoOpen)
			buffer.append(",'" ).append(HasVkAutoOpen.NAME).append("':").append(((HasVkAutoOpen)widgetSource).getAutoOpen());
		if(widgetSource instanceof HasVkTabHeaderText && !((HasVkTabHeaderText)widgetSource).getTabText().isEmpty())
			buffer.append(",'" ).append(HasVkTabHeaderText.NAME).append("':").append("'").append(((HasVkTabHeaderText)widgetSource).getTabText().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		if(widgetSource instanceof HasVkTabHeaderHtml && !((HasVkTabHeaderHtml)widgetSource).getTabHTML().isEmpty())
			buffer.append(",'" ).append(HasVkTabHeaderHtml.NAME).append("':").append("'").append(((HasVkTabHeaderHtml)widgetSource).getTabHTML().replace("'", "\\'").replace("\"", "\\\"")).append("'");
		
		if(widgetSource instanceof HasVkBlurHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkBlurHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkBlurHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkBlurHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkFocusHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkFocusHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkFocusHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkFocusHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkKeyDownHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyDownHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkKeyDownHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyDownHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkKeyPressHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyPressHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkKeyPressHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyPressHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkKeyUpHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyUpHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkKeyUpHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkKeyUpHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkClickHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkClickHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkClickHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkClickHandler.NAME).replace('\'', '"')).append("'");
		if(widgetSource instanceof HasVkMouseDownHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseDownHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseDownHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseDownHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkMouseMoveHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseMoveHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseMoveHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseMoveHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkMouseUpHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseUpHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseUpHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseUpHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkMouseOutHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseOutHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseOutHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseOutHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkMouseOverHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseOverHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseOverHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseOverHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkMouseWheelHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseWheelHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkMouseWheelHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkMouseWheelHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkCloseHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkCloseHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkCloseHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkCloseHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkOpenHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkOpenHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkOpenHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkOpenHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkSubmitHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkSubmitHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkSubmitHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkSubmitHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkSubmitCompleteHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkSubmitCompleteHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkSubmitCompleteHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkSubmitCompleteHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkScrollHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkScrollHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkScrollHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkScrollHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkBeforeSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkBeforeSelectionHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkBeforeSelectionHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkSelectionHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkSelectionHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkSelectionHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkInitializeHandlers && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkInitializeHandlers.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkInitializeHandlers.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkInitializeHandlers.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkValueChangeHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkValueChangeHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkValueChangeHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkValueChangeHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkHighlightHandlers && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkHighlightHandlers.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkHighlightHandlers.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkHighlightHandlers.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkShowRangeHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkShowRangeHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkShowRangeHandler.NAME).append("':").append("'").append(((HasVkEventHandler)widgetSource).getPriorJs(HasVkShowRangeHandler.NAME).replace("'", "\\'")).append("'");
		if(widgetSource instanceof HasVkLoadHandler && !((HasVkEventHandler)widgetSource).getPriorJs(HasVkLoadHandler.NAME).isEmpty())
			buffer.append(",'" ).append(HasVkLoadHandler.NAME).append("':").append("'").append(((HasVkLoadHandler)widgetSource).getPriorJs(HasVkLoadHandler.NAME).replace("'", "\\'")).append("'");
	}
	public void deserialize(IVkWidget invokingWidget, String jsonString)
	{
		if(jsonString.trim().isEmpty())
			return;
		JSONObject jsonObj = JSONParser.parseLenient(jsonString).isObject();
		if(jsonObj.isObject() == null)
		{
			Window.alert("JSON String is not well-formed. Application cannot be built.");
			return;
		}
		VkDesignerUtil.isDesignerMode = false;
		//((Panel)invokingWidget).clear(); Why do we need it, again?
		try{
			VkDesignerUtil.getEngineMap().get(invokingWidget.getWidgetName()).buildWidget(jsonObj, (Widget) invokingWidget);//cast is safe because root of DOM is drawingPanel
		}catch(Exception e)
		{
			Window.alert("JSON String is not well-formed. Application cannot be built.");	
			e.printStackTrace();
		}
		VkDesignerUtil.isDesignerMode = true;
	}
	//TODO buildWidget should return a widget and the work of addition should be performed in the parent. It is not right to pass parent object and adding
	//work is being done by the child
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			if(childObj == null)
				return;
			JSONString widgetName = childObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			VkDesignerUtil.addWidget(widget, ((IPanel)parent));
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
			addAttributes(childObj, widget);
		}
	}
	@SuppressWarnings("unchecked")
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
		attributeJsObj = childObj.get("id");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "id", attributeStringObj.stringValue()); 
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
		{
			int maxLength = (int)attributeNumberObj.doubleValue();
			if(maxLength > 0)
				((HasVkMaxLength)widget).setMaxLength(maxLength);
		}
		attributeJsObj = childObj.get(HasVkHorizontalAlignment.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkHorizontalAlignment)widget).setHorizontalAlignment(attributeStringObj.stringValue());
		attributeJsObj = childObj.get(HasVkAnimation.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAnimation)widget).setAnimationEnabled(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkVerticalAlignment.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkVerticalAlignment)widget).setVerticalAlignment(attributeStringObj.stringValue());
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
		attributeJsObj = childObj.get(HasVkValue.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkValue)widget).setValue(attributeStringObj.stringValue());
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
		attributeJsObj = childObj.get(HasVkListBoxRenderMode.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkListBoxRenderMode)widget).setDropDown(attributeBooleanObj.booleanValue());
		attributeJsObj = childObj.get(HasVkAutoOpen.NAME);
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((HasVkAutoOpen)widget).setAutoOpen(attributeBooleanObj.booleanValue());
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
		attributeJsObj = childObj.get(HasVkLoadHandler.NAME);
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			((HasVkLoadHandler)widget).addLoadHandler(attributeStringObj.stringValue());
	}
}
