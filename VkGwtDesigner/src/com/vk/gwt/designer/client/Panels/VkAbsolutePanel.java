package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkAbsolutePanel extends AbsolutePanel implements IPanel,HasVkWidgets
{
	final public static String NAME = "Absolute Panel";
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
