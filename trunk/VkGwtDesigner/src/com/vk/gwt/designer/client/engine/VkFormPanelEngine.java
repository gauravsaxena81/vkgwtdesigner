package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.ui.FormPanel;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkFormPanelEngine extends VkAbstractWidgetEngine<FormPanel> {
	@Override
	public FormPanel getWidget() {
		VkFormPanel widget = new VkFormPanel();
		init(widget);
		return widget;
	}
}
