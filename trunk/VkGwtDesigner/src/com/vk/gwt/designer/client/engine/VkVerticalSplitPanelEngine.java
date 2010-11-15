package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkVerticalSplitPanelEngine extends VkAbstractWidgetEngine<VkVerticalSplitPanel> {
	@Override
	public VkVerticalSplitPanel getWidget() {
		VkVerticalSplitPanel widget = new VkVerticalSplitPanel();
		init(widget);
		return widget;
	}
}
