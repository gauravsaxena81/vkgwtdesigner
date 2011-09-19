package com.vk.gwt.designer.client.ui.widget.vkFrame;

import com.google.gwt.user.client.ui.Frame;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFrameEngine extends VkAbstractWidgetEngine<Frame> {
	@Override
	public Frame getWidget() {
		VkFrame widget = new VkFrame();
		init(widget);
		return widget;
	}
}
