package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkInlineHTML;

public class VkInlineHTMLEngine extends VkAbstractWidgetEngine<VkInlineHTML> {
	@Override
	public VkInlineHTML getWidget() {
		VkInlineHTML widget = new VkInlineHTML();
		init(widget);
		return widget;
	}
}
