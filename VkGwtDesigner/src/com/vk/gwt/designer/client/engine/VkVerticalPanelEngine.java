package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkVerticalPanelEngine extends VkAbstractWidgetEngine<VkVerticalPanel>{
	@Override
	public VkVerticalPanel getWidget() {
		VkVerticalPanel widget = new VkVerticalPanel();
		init(widget);
		return widget;
	}
}
