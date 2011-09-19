package com.vk.gwt.designer.client.ui.widget.text.vkPasswordTextBox;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkPasswordTextBoxEngine extends VkAbstractWidgetEngine<VkPasswordTextBox> {
	@Override
	public VkPasswordTextBox getWidget() {
		VkPasswordTextBox widget = new VkPasswordTextBox();
		init(widget);
		return widget;
	}
}
