package com.vk.gwt.designer.client.ui.widget.vkHidden;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkHiddenEngine extends VkAbstractWidgetEngine<VkHidden> {

	@Override
	public VkHidden getWidget() {
		VkHidden widget = new VkHidden();
		init(widget);
		return widget;
	}

}
