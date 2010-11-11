package com.vk.gwt.designer.client.engine;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkDockPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkDockPanelEngine extends VkAbstractWidgetEngine<VkDockPanel>{

	@Override
	public VkDockPanel getWidget() {
		VkDockPanel widget = new VkDockPanel();
		init(widget);
		DOM.setElementProperty(widget.getElement(), "border", "1");
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(HasVkHorizontalAlignment.NAME))
			((VkDockPanel)invokingWidget).addHorizontalAlignment(attributeName);
		else if(attributeName.equals(HasVkVerticalAlignment.NAME))
			((VkDockPanel)invokingWidget).addVerticalAligment(attributeName);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
}
