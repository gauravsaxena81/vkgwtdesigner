package com.vk.gwt.designer.client.ui.widget.vkCheckbox;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkCheckboxEngine extends VkAbstractWidgetEngine<VkCheckbox> {

	@Override
	public VkCheckbox getWidget() {
		VkCheckbox widget = new VkCheckbox();
		init(widget);
		return widget;
	}
}
