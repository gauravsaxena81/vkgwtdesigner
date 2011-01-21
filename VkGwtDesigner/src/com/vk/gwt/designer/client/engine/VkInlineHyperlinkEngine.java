package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkInlineHyperlink;

public class VkInlineHyperlinkEngine extends VkAbstractWidgetEngine<VkInlineHyperlink> {
	@Override
	public VkInlineHyperlink getWidget() {
		VkInlineHyperlink widget = new VkInlineHyperlink();
		init(widget);
		return widget;
	}
}
