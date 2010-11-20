package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

public class VkSimplePanel extends SimplePanel implements HasVkWidgets, IPanel {
	public static final String NAME = "Simple Panel";

	@Override
	public void add(Widget w) {
		if(getWidget() != null)
			Window.alert("VkSimplePanel can add only one widget");
		else
			super.add(w);
	}
}
