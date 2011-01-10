package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkScrollPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkScrollPanelEngine extends VkAbstractWidgetEngine<VkScrollPanel> {
	@Override
	public VkScrollPanel getWidget() {
		VkScrollPanel widget = new VkScrollPanel();
		init(widget);
		return widget;
	}
}
