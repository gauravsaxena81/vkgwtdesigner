package com.vk.gwt.designer.client.widgets;

import com.google.gwt.user.client.ui.MenuBar;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkMenuBarVertical extends VkMenuBarHorizontal{
	public static final String NAME = "Menu Bar Vertical";
	private int left;
	private int top;
	
	public VkMenuBarVertical()
	{
		super(true);
	}
	public int getLeft() {
		return left;
	}
	public void setLeft(int left) {
		this.left = left;
	}
	public int getTop() {
		return top;
	}
	public void setTop(int top) {
		this.top = top;
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public boolean isResizable() {
		return true; 	
	}
	@Override
	public boolean isMovable() {
		if(getParent() instanceof MenuBar)
			return false;
		else
			return true;
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
