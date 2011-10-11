package com.vk.gwt.designer.client.api.component;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.IsMovable;
import com.vk.gwt.designer.client.api.attributes.IsResizable;

public interface IVkWidget extends IsMovable, IsResizable{
	public String getWidgetName();
	public void clone(Widget targetWidget);
	public boolean showMenu();
	public List<Widget> getToolbarWidgets();
	public IVkWidget getVkParent();
	public void setVkParent(IVkWidget panel);
}