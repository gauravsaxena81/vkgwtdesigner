package com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

public class VkAbsolutePanel extends AbsolutePanel implements IVkPanel,HasVkWidgets
{
	final public static String NAME = "Absolute Panel";
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public Iterator<Widget> iterator()
	{
		List<Widget> list = new ArrayList<Widget>();
		Iterator<Widget> widgetIterator = super.iterator();
		while(widgetIterator.hasNext())
			list.add(widgetIterator.next());
		return list.iterator();
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
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
	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}