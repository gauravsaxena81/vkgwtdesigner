package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public abstract class VkAbstractWidgetEngine<T extends Widget> implements IWidgetEngine<T> {
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		return VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
	}
	protected void init(Widget widget) {
		widget.setPixelSize(100, 100);
		DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(widget.getElement(), "padding", "5px");
	}
}
