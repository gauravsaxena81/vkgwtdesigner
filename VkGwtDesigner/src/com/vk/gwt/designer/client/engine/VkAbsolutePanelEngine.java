package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkAbsolutePanelEngine extends VkAbstractWidgetEngine<VkAbsolutePanel> {
	final public static String NAME = "Absolute Panel";
	@Override
	public VkAbsolutePanel getWidget() {
		
		VkAbsolutePanel widget = new VkAbsolutePanel();
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
