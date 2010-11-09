package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;

public class VkVerticalPanelEngine extends VkAbstractWidgetEngine<VkVerticalPanel>{
	@Override
	public VkVerticalPanel getWidget() {
		VkVerticalPanel widget = new VkVerticalPanel();
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
		return widget;
	}
}
