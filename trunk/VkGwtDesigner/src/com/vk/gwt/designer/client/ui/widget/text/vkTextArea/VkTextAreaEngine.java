package com.vk.gwt.designer.client.ui.widget.text.vkTextArea;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkTextAreaEngine extends VkAbstractWidgetEngine<VkTextArea> {

	@Override
	public VkTextArea getWidget() {
		VkTextArea widget = new VkTextArea();
		init(widget);
		return widget;
	}
}
