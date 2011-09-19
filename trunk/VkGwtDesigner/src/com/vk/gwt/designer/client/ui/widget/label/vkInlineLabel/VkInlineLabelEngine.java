package com.vk.gwt.designer.client.ui.widget.label.vkInlineLabel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkInlineLabelEngine extends VkAbstractWidgetEngine<VkInlineLabel> {
	@Override
	public VkInlineLabel getWidget() {
		VkInlineLabel widget = new VkInlineLabel();
		init(widget);
		return widget;
	}

}
