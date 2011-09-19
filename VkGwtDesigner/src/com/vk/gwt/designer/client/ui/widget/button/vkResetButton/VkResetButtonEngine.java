package com.vk.gwt.designer.client.ui.widget.button.vkResetButton;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkResetButtonEngine extends VkAbstractWidgetEngine<VkResetButton> {
	@Override
	public VkResetButton getWidget() {
		VkResetButton widget = new VkResetButton();
		init(widget);
		return widget;
	}
}
