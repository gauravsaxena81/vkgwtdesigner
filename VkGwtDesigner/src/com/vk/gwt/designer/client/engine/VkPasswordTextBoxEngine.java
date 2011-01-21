package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkPasswordTextBox;

public class VkPasswordTextBoxEngine extends VkAbstractWidgetEngine<VkPasswordTextBox> {
	@Override
	public VkPasswordTextBox getWidget() {
		VkPasswordTextBox widget = new VkPasswordTextBox();
		init(widget);
		return widget;
	}
}
