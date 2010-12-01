package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkStackPanelEngine extends VkAbstractWidgetEngine<VkStackPanel> {
	@Override
	public VkStackPanel getWidget() {
		VkStackPanel widget = new VkStackPanel();
		init(widget);
		return widget;
	}
}
