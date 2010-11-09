package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkTextBox;

public class VkTextBoxEngine extends VkAbstractWidgetEngine<VkTextBox> {
	@Override
	public VkTextBox getWidget() {
		return new VkTextBox();
	}
}