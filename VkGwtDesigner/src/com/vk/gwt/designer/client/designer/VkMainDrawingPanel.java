package com.vk.gwt.designer.client.designer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

class VkMainDrawingPanel extends AbsolutePanel implements IPanel, HasVkLoadHandler, HasVkWidgets{
	public static String NAME = "Drawing Panel";
	private String loadHandlerJs = "";
	@Override
	public void clone(Widget targetWidget) {}

	@Override
	public String getWidgetName() {
		return NAME;
	}

	@Override
	public void addLoadHandler(String js) {
		loadHandlerJs = js;
	}

	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkLoadHandler.NAME))
			return loadHandlerJs;
		else
			return "";
	}
	@Override
	public Iterator<Widget> iterator()
	{
		List<Widget> list = new ArrayList<Widget>();
		Iterator<Widget> widgetIterator = super.iterator();
		while(widgetIterator.hasNext())
			list.add(widgetIterator.next());
		Panel parent = (Panel) getParent();
		Iterator<Widget> popUpWidgets = parent.iterator();
		while(popUpWidgets.hasNext())
		{
			Widget popup = popUpWidgets.next();
			if(popup != this)
				list.add(popup);
		}
		return list.iterator();
	}
	@Override
	public boolean showMenu() {
		return true;
	}
}
