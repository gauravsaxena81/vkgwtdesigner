package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkAnchor;

public class VkAnchorEngine extends VkAbstractWidgetEngine<VkAnchor> {
	@Override
	public VkAnchor getWidget() {
		VkAnchor widget = new VkAnchor();
		init(widget);
		return widget;
	}
}
