package com.vk.gwt.designer.client.ui.panel.vkSimplePanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkSimplePanelEngine extends VkAbstractWidgetEngine<VkSimplePanel> {
	@Override
	public VkSimplePanel getWidget() {
		VkSimplePanel widget = new VkSimplePanel();
		init(widget);
		return widget;
	}
	
}
