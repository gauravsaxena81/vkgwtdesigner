package com.vk.gwt.designer.client.api.component;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

public interface IVkPanel extends IVkWidget{
	public void add(Widget widget);
	public Iterator<Widget> iterator();
}
