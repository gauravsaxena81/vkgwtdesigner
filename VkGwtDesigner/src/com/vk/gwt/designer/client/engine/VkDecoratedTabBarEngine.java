package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkDecoratedTabBar;

public class VkDecoratedTabBarEngine extends VkAbstractWidgetEngine<VkDecoratedTabBar> {
	@Override
	public VkDecoratedTabBar getWidget() {
		VkDecoratedTabBar widget = new VkDecoratedTabBar();
		init(widget);
		return widget;
	}
}
