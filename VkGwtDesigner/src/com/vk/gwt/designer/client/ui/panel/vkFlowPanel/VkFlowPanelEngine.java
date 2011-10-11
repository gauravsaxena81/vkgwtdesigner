package com.vk.gwt.designer.client.ui.panel.vkFlowPanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFlowPanelEngine extends VkAbstractWidgetEngine<VkFlowPanel> {
	@Override
	public VkFlowPanel getWidget() {
		VkFlowPanel widget = new VkFlowPanel();
		init(widget);
		return widget;
	}
}
