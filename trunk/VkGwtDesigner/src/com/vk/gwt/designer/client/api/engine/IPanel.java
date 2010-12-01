package com.vk.gwt.designer.client.api.engine;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;

public interface IPanel extends IVkWidget{
	public void add(Widget widget);
	public Iterator<Widget> iterator();
}
