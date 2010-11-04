package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.AbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkButton;

public class VkButtonEngine extends AbstractWidgetEngine<VkButton> {

	@Override
	public VkButton getWidget() {
		return new VkButton();
	}	
}
