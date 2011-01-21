package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkHorizontalSplitPanelEngine extends VkAbstractWidgetEngine<VkHorizontalSplitPanel> {

	@Override
	public VkHorizontalSplitPanel getWidget() {
		VkHorizontalSplitPanel widget = new VkHorizontalSplitPanel();
		init(widget);
		return widget;
	}

}
