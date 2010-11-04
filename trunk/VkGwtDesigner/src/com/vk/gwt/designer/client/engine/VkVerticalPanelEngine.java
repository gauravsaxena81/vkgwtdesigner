package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.engine.AbstractPanelEngine;
import com.vk.gwt.designer.client.api.engine.IPanelEngine;

public class VkVerticalPanelEngine extends AbstractPanelEngine<VkVerticalPanel> implements IPanelEngine<VkVerticalPanel> {
	@Override
	public VkVerticalPanel getWidget() {
		VkVerticalPanel widget = new VkVerticalPanel();
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
