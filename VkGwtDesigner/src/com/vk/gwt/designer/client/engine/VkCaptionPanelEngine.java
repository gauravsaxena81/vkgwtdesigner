package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkCaptionPanelEngine extends VkAbstractWidgetEngine<VkCaptionPanel>{
	@Override
	public VkCaptionPanel getWidget() {
		VkCaptionPanel widget = new VkCaptionPanel();
		init(widget);
		return widget;
	}
}
