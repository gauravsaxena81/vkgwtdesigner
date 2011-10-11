package com.vk.gwt.designer.client.ui.widget.vkFrame;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFrameEngine extends VkAbstractWidgetEngine<VkFrame> {
	@Override
	public VkFrame getWidget() {
		VkFrame widget = new VkFrame();
		init(widget);
		return widget;
	}
}
