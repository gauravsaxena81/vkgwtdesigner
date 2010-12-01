package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkDecoratedStackPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkDecoratedStackPanelEngine extends VkAbstractWidgetEngine<VkDecoratedStackPanel> {

	@Override
	public VkDecoratedStackPanel getWidget() {
		VkDecoratedStackPanel widget = new VkDecoratedStackPanel();
		init(widget);
		return widget;
	}

}
