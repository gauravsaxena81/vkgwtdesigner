package com.vk.gwt.designer.client.ui.panel.vkFlowPanel;

import com.google.gwt.user.client.ui.FlowPanel;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFlowPanelEngine extends VkAbstractWidgetEngine<FlowPanel> {
	@Override
	public FlowPanel getWidget() {
		VkFlowPanel widget = new VkFlowPanel();
		init(widget);
		return widget;
	}
}
