package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkSimplePanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkSimplePanelEngine extends VkAbstractWidgetEngine<VkSimplePanel> {
	@Override
	public VkSimplePanel getWidget() {
		VkSimplePanel widget = new VkSimplePanel();
		init(widget);
		return widget;
	}
	
}
