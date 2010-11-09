package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.ui.Label;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkLabel;

public class VkLabelEngine extends VkAbstractWidgetEngine<Label> {

	@Override
	public Label getWidget() {
		return new VkLabel();
	}

}
