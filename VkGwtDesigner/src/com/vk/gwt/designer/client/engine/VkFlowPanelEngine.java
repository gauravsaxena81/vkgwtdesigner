package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.ui.FlowPanel;
import com.vk.gwt.designer.client.Panels.VkFlowPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkFlowPanelEngine extends VkAbstractWidgetEngine<FlowPanel> {
	@Override
	public FlowPanel getWidget() {
		VkFlowPanel widget = new VkFlowPanel();
		init(widget);
		return widget;
	}
}
