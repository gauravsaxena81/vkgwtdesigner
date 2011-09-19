package com.vk.gwt.designer.client.ui.widget.text.vkTextBox;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkTextBoxEngine extends VkAbstractWidgetEngine<VkTextBox> {
	@Override
	public VkTextBox getWidget() {
		VkTextBox textBox = new VkTextBox();
		init(textBox);
		return textBox;
	}
}