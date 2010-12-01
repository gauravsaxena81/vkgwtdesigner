package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkDeckPanelEngine extends VkAbstractWidgetEngine<VkDeckPanel>{
	@Override
	public VkDeckPanel getWidget() {
		VkDeckPanel widget = new VkDeckPanel();
		init(widget);
		return widget;
	}
}
