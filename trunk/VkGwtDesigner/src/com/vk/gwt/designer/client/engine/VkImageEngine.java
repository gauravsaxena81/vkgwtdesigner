package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkImage;

public class VkImageEngine extends VkAbstractWidgetEngine<VkImage> {
	private static final String ADD_ALT = "Add Alternate Text";
	@Override
	public VkImage getWidget() {
		VkImage widget = new VkImage();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(ADD_ALT))
		{
			TextBox textbox = new TextBox();
			textbox.setValue(((VkImage)(invokingWidget)).getAlt());
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add alternate text", textbox 
			, new IEventRegister() {
				@Override
				public void registerEvent(String text) {
					((VkImage)(invokingWidget)).setAlt(text);
				}
			});
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		list.add(ADD_ALT);
		return list;
	}
}
