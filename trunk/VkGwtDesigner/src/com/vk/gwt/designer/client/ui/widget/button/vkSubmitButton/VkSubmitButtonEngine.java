package com.vk.gwt.designer.client.ui.widget.button.vkSubmitButton;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkSubmitButtonEngine extends VkAbstractWidgetEngine<VkSubmitButton> {
	@Override
	public VkSubmitButton getWidget() {
		VkSubmitButton widget = new VkSubmitButton();
		init(widget);
		return widget;
	}
}
