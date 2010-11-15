package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkFlexTable;

public class VkFlexTableEngine extends VkAbstractWidgetEngine<VkFlexTable> {

	@Override
	public VkFlexTable getWidget() {
		VkFlexTable widget = new VkFlexTable();
		init(widget);
		return widget;
	}
}
