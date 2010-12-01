package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkSubmitButton;

public class VkSubmitButtonEngine extends VkAbstractWidgetEngine<VkSubmitButton> {
	@Override
	public VkSubmitButton getWidget() {
		VkSubmitButton widget = new VkSubmitButton();
		init(widget);
		return widget;
	}
}
