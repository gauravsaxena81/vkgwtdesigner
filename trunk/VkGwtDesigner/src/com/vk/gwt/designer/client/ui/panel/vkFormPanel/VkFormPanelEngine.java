package com.vk.gwt.designer.client.ui.panel.vkFormPanel;

import com.google.gwt.user.client.ui.FormPanel;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkFormPanelEngine extends VkAbstractWidgetEngine<FormPanel> {
	@Override
	public FormPanel getWidget() {
		VkFormPanel widget = new VkFormPanel();
		init(widget);
		return widget;
	}
}
