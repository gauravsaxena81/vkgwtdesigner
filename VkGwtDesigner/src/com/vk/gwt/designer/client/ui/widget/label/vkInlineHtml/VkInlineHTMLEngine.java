package com.vk.gwt.designer.client.ui.widget.label.vkInlineHtml;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkInlineHTMLEngine extends VkAbstractWidgetEngine<VkInlineHTML> {
	@Override
	public VkInlineHTML getWidget() {
		VkInlineHTML widget = new VkInlineHTML();
		init(widget);
		return widget;
	}
}
