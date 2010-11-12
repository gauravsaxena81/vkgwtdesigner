package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.ui.FlowPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkFlowPanel extends FlowPanel implements IPanel, HasVkWidgets{
	public static final String NAME = "Flow Panel";
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
