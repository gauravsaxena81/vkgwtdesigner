package com.vk.gwt.designer.client.ui.widget.vkRadioButton;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkRadioButtonEngine extends VkAbstractWidgetEngine<VkRadioButton> {
	@Override
	public VkRadioButton getWidget() {
		VkRadioButton widget = new VkRadioButton();
		init(widget);
		return widget;
	}
}
