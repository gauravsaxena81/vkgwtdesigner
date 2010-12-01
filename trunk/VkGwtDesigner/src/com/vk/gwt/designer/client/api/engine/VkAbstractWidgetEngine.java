package com.vk.gwt.designer.client.api.engine;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAlternateText;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoOpen;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkFormEncoding;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkHistoryToken;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxMultiple;
import com.vk.gwt.designer.client.api.attributes.HasVkMaxLength;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollBarShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderText;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
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
	protected void init(Widget widget) {
		widget.setPixelSize(100, 20);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
	}
	@Override
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
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
		if(widgetSource instanceof HasVkAutoOpen)
			((HasVkAutoOpen)widgetTarget).setAutoOpen(((HasVkAutoOpen)widgetSource).getAutoOpen());
		if(widgetSource instanceof HasVkTabHeaderText)
			((HasVkTabHeaderText)widgetTarget).setTabText(((HasVkTabHeaderText)widgetSource).getTabText());
		if(widgetSource instanceof HasVkTabHeaderHtml)
			((HasVkTabHeaderHtml)widgetTarget).setTabHTML(((HasVkTabHeaderHtml)widgetSource).getTabHTML());
			
	}
}
