package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.ui.Frame;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkFrame;

public class VkFrameEngine extends VkAbstractWidgetEngine<Frame> {
	@Override
	public Frame getWidget() {
		VkFrame widget = new VkFrame();
		init(widget);
		return widget;
	}
}
