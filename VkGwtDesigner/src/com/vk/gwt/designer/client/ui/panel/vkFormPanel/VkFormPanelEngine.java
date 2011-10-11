package com.vk.gwt.designer.client.ui.panel.vkFormPanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFormPanelEngine extends VkAbstractWidgetEngine<VkFormPanel> {
	@Override
	public VkFormPanel getWidget() {
		VkFormPanel widget = new VkFormPanel();
		init(widget);
		return widget;
	}
}
