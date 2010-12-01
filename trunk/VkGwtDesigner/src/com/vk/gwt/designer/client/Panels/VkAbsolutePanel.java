package com.vk.gwt.designer.client.Panels;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

public class VkAbsolutePanel extends AbsolutePanel implements IPanel,HasVkWidgets
{
	final public static String NAME = "Absolute Panel";
	@Override
	public String getWidgetName() {
		return NAME;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Export
	public Element getElement(int index)
	{
		return super.getWidget(index).getElement();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
}
