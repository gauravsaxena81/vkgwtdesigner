package com.vk.gwt.designer.client.ui.widget.button.vkButton;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkButtonEngine extends VkAbstractWidgetEngine<VkButton> {

	@Override
	public VkButton getWidget() {
		VkButton widget = new VkButton();
		init(widget);
		return widget;
	}
	protected void init(Widget widget) {
		widget.setPixelSize(100, 20);
	}
}
