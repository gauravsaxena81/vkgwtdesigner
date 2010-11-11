package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkAbsolutePanelEngine extends VkAbstractWidgetEngine<VkAbsolutePanel> {
	final public static String NAME = "Absolute Panel";
	@Override
	public VkAbsolutePanel getWidget() {
		
		VkAbsolutePanel widget = new VkAbsolutePanel();
		init(widget);
		return widget;
	}
}
