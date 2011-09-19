package com.vk.gwt.designer.client.ui.panel.vkScrollPanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkScrollPanelEngine extends VkAbstractWidgetEngine<VkScrollPanel> {
	@Override
	public VkScrollPanel getWidget() {
		VkScrollPanel widget = new VkScrollPanel();
		init(widget);
		return widget;
	}
}
