package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkHyperlink;

public class VkHyperlinkEngine extends VkAbstractWidgetEngine<VkHyperlink> {
	private static final String ADD_HISTORY_TOKEN = "Add History Token";
	@Override
	public VkHyperlink getWidget() {
		VkHyperlink widget = new VkHyperlink();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(ADD_HISTORY_TOKEN))
		{
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide history token"
					, new TextBox(), new IEventRegister() {
						@Override
						public void registerEvent(String historyToken) {
							((VkHyperlink)invokingWidget).setTargetHistoryToken(historyToken);
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
		list.add(ADD_HISTORY_TOKEN);
		return list;
	}
}
