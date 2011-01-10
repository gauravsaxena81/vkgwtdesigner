package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkFocusPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkFocusPanelEngine extends VkAbstractWidgetEngine<VkFocusPanel> {
	@Override
	public VkFocusPanel getWidget() {
		VkFocusPanel widget = new VkFocusPanel();
		init(widget);
		return widget;
	}
}
