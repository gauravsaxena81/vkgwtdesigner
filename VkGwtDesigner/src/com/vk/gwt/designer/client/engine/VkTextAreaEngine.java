package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkTextArea;

public class VkTextAreaEngine extends VkAbstractWidgetEngine<VkTextArea> {

	@Override
	public VkTextArea getWidget() {
		VkTextArea widget = new VkTextArea();
		init(widget);
		return widget;
	}
}
