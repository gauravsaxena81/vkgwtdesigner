package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkHorizontalPanelEngine extends VkAbstractWidgetEngine<HorizontalPanel> {
	public static final String NAME = "Horizontal Panel";
	@Override
	public HorizontalPanel getWidget() {
		VkHorizontalPanel widget = new VkHorizontalPanel();
		init(widget);
		return widget;
	}
}
