package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkCheckbox;

public class VkCheckboxEngine extends VkAbstractWidgetEngine<VkCheckbox> {

	@Override
	public VkCheckbox getWidget() {
		return new VkCheckbox();
	}
}
