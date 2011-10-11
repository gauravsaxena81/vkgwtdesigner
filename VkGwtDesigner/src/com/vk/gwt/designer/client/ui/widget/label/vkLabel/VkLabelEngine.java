package com.vk.gwt.designer.client.ui.widget.label.vkLabel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkLabelEngine extends VkAbstractWidgetEngine<VkLabel> {
	@Override
	public VkLabel getWidget() {
		VkLabel widget = new VkLabel();
		init(widget);
		return widget;
	}
}
