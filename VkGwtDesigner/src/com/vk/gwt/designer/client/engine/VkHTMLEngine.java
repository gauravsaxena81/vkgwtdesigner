package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkHTML;

public class VkHTMLEngine extends VkAbstractWidgetEngine<VkHTML> {
	@Override
	public VkHTML getWidget() {
		return new VkHTML();
	}
}
