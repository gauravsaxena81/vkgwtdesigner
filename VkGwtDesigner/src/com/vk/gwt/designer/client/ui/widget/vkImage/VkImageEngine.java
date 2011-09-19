package com.vk.gwt.designer.client.ui.widget.vkImage;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkImageEngine extends VkAbstractWidgetEngine<VkImage> {
	@Override
	public VkImage getWidget() {
		VkImage widget = new VkImage();
		init(widget);
		return widget;
	}
}
