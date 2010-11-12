package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkStackPanelEngine extends VkAbstractWidgetEngine<VkStackPanel> {

	@Override
	public VkStackPanel getWidget() {
		VkStackPanel widget = new VkStackPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.remove(1);
		optionList.remove(1);
		optionList.add("Add Current Header Text");
		optionList.add("Add Current Header HTML");
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals("Add Current Header Text"))
			attributeName = "Add Text";
		if(attributeName.equals("Add Current Header HTML"))
			attributeName = "Add HTML";
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
}
