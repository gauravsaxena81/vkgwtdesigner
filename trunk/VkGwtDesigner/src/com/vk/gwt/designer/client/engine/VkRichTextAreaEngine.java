package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkRichTextArea;

public class VkRichTextAreaEngine extends VkAbstractWidgetEngine<VkRichTextArea> {
	@Override
	public VkRichTextArea getWidget() {
		VkRichTextArea widget = new VkRichTextArea();
		init(widget);
		return widget;
	}
}
