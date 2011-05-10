package com.vk.gwt.designer.client.api.widgets;

import com.google.gwt.user.client.ui.Widget;

public interface IVkWidget {
	public String getWidgetName();
	public void clone(Widget targetWidget);
	public boolean showMenu();
}
