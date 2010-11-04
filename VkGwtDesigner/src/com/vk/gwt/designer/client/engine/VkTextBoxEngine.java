package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.AbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkTextBox;

public class VkTextBoxEngine extends AbstractWidgetEngine<VkTextBox> {
	@Override
	public VkTextBox getWidget() {
		return new VkTextBox();
	}
}