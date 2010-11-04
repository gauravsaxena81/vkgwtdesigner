package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkMenu;

public class VkAbsolutePanel extends AbsolutePanel implements HasVkWidgets
{
	final public static String NAME = "Absolute Panel";
	public VkAbsolutePanel()
	{
		VkMenu designerMenu = new VkMenu(this, VkDesignerUtil.getEngineMap().get(NAME));
		this.add(designerMenu);
		VkDesignerUtil.addPressAndHoldEvent(this.getElement(), designerMenu.getElement());
	}
}
