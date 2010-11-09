package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface IEngine {
	public List<String> getAttributesList(Widget invokingWidget);
	public void applyAttribute(String attributeName, Widget invokingWidget);
	public List<String> getWidgetsList(Widget invokingWidget);
}
