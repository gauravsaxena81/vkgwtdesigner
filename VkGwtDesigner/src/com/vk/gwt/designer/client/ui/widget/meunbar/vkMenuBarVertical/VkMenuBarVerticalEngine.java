package com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical;

import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal.VkMenuBarHorizontalEngine;

public class VkMenuBarVerticalEngine extends VkMenuBarHorizontalEngine {
	@Override
	public VkMenuBarVertical getWidget() {
		VkMenuBarVertical widget = new VkMenuBarVertical();
		init(widget);
		return widget;
	}
}
