package com.vk.gwt.designer.client.ui.widget.vkAnchor;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkAnchorEngine extends VkAbstractWidgetEngine<VkAnchor> {
	@Override
	public VkAnchor getWidget() {
		VkAnchor widget = new VkAnchor();
		init(widget);
		DOM.setStyleAttribute(widget.getElement(), "display", "inline-block");
		return widget;
	}
}
