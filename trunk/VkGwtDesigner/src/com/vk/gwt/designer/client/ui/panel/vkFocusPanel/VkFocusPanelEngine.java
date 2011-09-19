package com.vk.gwt.designer.client.ui.panel.vkFocusPanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFocusPanelEngine extends VkAbstractWidgetEngine<VkFocusPanel> {
	@Override
	public VkFocusPanel getWidget() {
		VkFocusPanel widget = new VkFocusPanel();
		init(widget);
		return widget;
	}
}
