package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkVerticalPanel extends VerticalPanel implements IPanel,HasVkWidgets{
	public static final String NAME = "Vertical Panel";
	@Override
	protected void add(Widget child, Element container) 
	{
		if(getWidgetCount() > 0)
			DOM.setElementAttribute((Element) getWidget(getWidgetCount() - 1).getElement().getParentElement(), "height", "1px");
		super.add(child, container);
		DOM.setElementAttribute(container, "height", "*");
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
}