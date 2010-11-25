package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;

public class VkFormPanelEngine extends VkAbstractWidgetEngine<FormPanel> {
	private static final String SET_ENCODING = "Set Encoding";
	@Override
	public FormPanel getWidget() {
		VkFormPanel widget = new VkFormPanel();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(SET_ENCODING))
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please give form encoding", new TextBox()
			, new IEventRegister(){
				@Override
				public void registerEvent(String encodingType) {
					((VkFormPanel)invokingWidget).setEncoding(encodingType);
				}});
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		list.add(SET_ENCODING);
		return list; 
	}
}
