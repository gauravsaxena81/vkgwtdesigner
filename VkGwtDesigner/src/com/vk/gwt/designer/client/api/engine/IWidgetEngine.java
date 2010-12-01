package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface IWidgetEngine<T extends Widget> {
	 public T getWidget();
	 public void applyAttribute(String attributeName, Widget invokingWidget);
	 public List<String> getAttributesList(Widget invokingWidget);
	 public void copyAttributes(Widget widgetSource, Widget widgetTarget);
	 public Widget deepClone(Widget currentWidget, Widget widget);
}
