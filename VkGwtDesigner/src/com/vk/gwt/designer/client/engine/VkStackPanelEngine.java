package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkStackPanelEngine extends VkAbstractWidgetEngine<VkStackPanel> {
	private final String ADD_TEXT = "Add Current Header Text";
	private final String ADD_HTML = "Add Current Header HTML";
	
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
		optionList.remove(HasVkText.NAME);
		optionList.remove(HasVkHtml.NAME);
		optionList.remove(HasVkEnabled.NAME);
		optionList.add(ADD_TEXT);
		optionList.add(ADD_HTML);
		
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(ADD_TEXT))
			attributeName = HasVkText.NAME; //Vk Engine needs exact attribute text to apply attribute
		else if(attributeName.equals(ADD_HTML))
			attributeName = HasVkHtml.NAME;
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
}
