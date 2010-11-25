package com.vk.gwt.designer.client.widgets;

import com.vk.gwt.generator.client.Export;

public class VkMenuBarVertical extends VkMenuBarHorizontal{
	public static final String NAME = "Menu Bar Vertical";
	public VkMenuBarVertical()
	{
		super(true);
	}
	/**************************Export attribute Methods********************************/
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
