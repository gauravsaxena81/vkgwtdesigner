package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkButton;

public class VkButtonEngine extends VkAbstractWidgetEngine<VkButton> {

	@Override
	public VkButton getWidget() {
		VkButton widget = new VkButton();
		init(widget);
		return widget;
	}	
}
