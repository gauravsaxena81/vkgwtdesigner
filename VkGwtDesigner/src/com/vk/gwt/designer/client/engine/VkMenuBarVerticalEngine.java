package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;

public class VkMenuBarVerticalEngine extends VkMenuBarHorizontalEngine {
	@Override
	public VkMenuBarVertical getWidget() {
		VkMenuBarVertical widget = new VkMenuBarVertical();
		init(widget);
		return widget;
	}
}
