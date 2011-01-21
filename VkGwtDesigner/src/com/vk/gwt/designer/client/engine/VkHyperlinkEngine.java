package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkHyperlink;

public class VkHyperlinkEngine extends VkAbstractWidgetEngine<VkHyperlink> {
	@Override
	public VkHyperlink getWidget() {
		VkHyperlink widget = new VkHyperlink();
		init(widget);
		return widget;
	}
}
