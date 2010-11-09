package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkCaptionPanelEngine extends VkAbstractWidgetEngine<VkCaptionPanel>{
	@Override
	public VkCaptionPanel getWidget() {
		VkCaptionPanel widget = new VkCaptionPanel();
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
