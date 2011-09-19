package com.vk.gwt.designer.client.ui.panel.vkCaptionPanel;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkCaptionPanelEngine extends VkAbstractWidgetEngine<VkCaptionPanel>{
	@Override
	public VkCaptionPanel getWidget() {
		VkCaptionPanel widget = new VkCaptionPanel();
		init(widget);
		return widget;
	}
}
