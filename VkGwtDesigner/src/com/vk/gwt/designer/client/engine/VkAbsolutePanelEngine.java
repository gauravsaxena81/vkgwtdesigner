package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.api.engine.AbstractPanelEngine;

public class VkAbsolutePanelEngine extends AbstractPanelEngine<VkAbsolutePanel> {
	@Override
	public VkAbsolutePanel getWidget() {
		
		VkAbsolutePanel widget = new VkAbsolutePanel();
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
