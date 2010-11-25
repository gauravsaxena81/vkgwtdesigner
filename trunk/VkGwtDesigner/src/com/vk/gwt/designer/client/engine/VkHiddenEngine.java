package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkHidden;

public class VkHiddenEngine extends VkAbstractWidgetEngine<VkHidden> {

	@Override
	public VkHidden getWidget() {
		VkHidden widget = new VkHidden();
		init(widget);
		return widget;
	}

}
