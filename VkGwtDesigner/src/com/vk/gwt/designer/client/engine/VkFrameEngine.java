package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Frame;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkFrame;

public class VkFrameEngine extends VkAbstractWidgetEngine<Frame> {
	@Override
	public Frame getWidget() {
		VkFrame widget = new VkFrame();
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 2px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
