package com.vk.gwt.designer.client.ui.widget.vkAnchor;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkAnchorEngine extends VkAbstractWidgetEngine<VkAnchor> {
	@Override
	public VkAnchor getWidget() {
		VkAnchor widget = new VkAnchor();
		init(widget);
		return widget;
	}
}
