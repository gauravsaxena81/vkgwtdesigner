package com.vk.gwt.designer.client.ui.widget.label.vkHtml;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkHTMLEngine extends VkAbstractWidgetEngine<VkHTML> {
	@Override
	public VkHTML getWidget() {
		VkHTML widget = new VkHTML();
		init(widget);
		return widget;
	}
}
