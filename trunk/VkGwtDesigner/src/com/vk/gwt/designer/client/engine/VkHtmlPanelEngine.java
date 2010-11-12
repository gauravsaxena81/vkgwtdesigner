package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkHtmlPanelEngine extends VkAbstractWidgetEngine<VkHtmlPanel> {
	@Override
	public VkHtmlPanel getWidget() {
		VkHtmlPanel widget = new VkHtmlPanel();
		init(widget);
		return widget;
	}

}
