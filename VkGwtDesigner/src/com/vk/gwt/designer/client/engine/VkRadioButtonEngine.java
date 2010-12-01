package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkRadioButton;

public class VkRadioButtonEngine extends VkAbstractWidgetEngine<VkRadioButton> {
	@Override
	public VkRadioButton getWidget() {
		VkRadioButton widget = new VkRadioButton();
		init(widget);
		return widget;
	}
}
