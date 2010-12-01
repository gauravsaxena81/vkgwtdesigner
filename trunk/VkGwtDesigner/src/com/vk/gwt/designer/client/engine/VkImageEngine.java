package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkImage;

public class VkImageEngine extends VkAbstractWidgetEngine<VkImage> {
	@Override
	public VkImage getWidget() {
		VkImage widget = new VkImage();
		init(widget);
		return widget;
	}
}
