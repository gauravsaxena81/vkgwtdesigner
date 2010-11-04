package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public interface IPanelEngine<T extends Panel> extends IWidgetEngine<T> {
	public List<String> getWidgetsList(Widget invokingWidget);
}
