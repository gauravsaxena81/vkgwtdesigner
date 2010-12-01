package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkInlineLabel;

public class VkInlineLabelEngine extends VkAbstractWidgetEngine<VkInlineLabel> {
	@Override
	public VkInlineLabel getWidget() {
		VkInlineLabel widget = new VkInlineLabel();
		init(widget);
		return widget;
	}

}
