package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkResetButton;

public class VkResetButtonEngine extends VkAbstractWidgetEngine<VkResetButton> {
	@Override
	public VkResetButton getWidget() {
		VkResetButton widget = new VkResetButton();
		init(widget);
		return widget;
	}
}
