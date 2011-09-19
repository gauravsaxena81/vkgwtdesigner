package com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkAbsolutePanelEngine extends VkAbstractWidgetEngine<VkAbsolutePanel> {
	@Override
	public VkAbsolutePanel getWidget() {
		
		VkAbsolutePanel widget = new VkAbsolutePanel();
		init(widget);
		return widget;
	}
}
