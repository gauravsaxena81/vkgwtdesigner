package com.vk.gwt.designer.client.ui.widget.label.vkLabel;

import com.google.gwt.user.client.ui.Label;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkLabelEngine extends VkAbstractWidgetEngine<Label> {
	@Override
	public Label getWidget() {
		VkLabel widget = new VkLabel();
		init(widget);
		return widget;
	}
}
