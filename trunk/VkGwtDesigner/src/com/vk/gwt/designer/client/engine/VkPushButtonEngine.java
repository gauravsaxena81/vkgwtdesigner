package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkPushButton;

public class VkPushButtonEngine extends VkAbstractWidgetEngine<VkPushButton> {
	@Override
	public VkPushButton getWidget() {
		VkPushButton widget = new VkPushButton();
		init(widget);
		return widget;
	}
}
