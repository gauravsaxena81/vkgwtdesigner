package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;

public interface IWidgetEngine<T extends Widget> {
	 public T getWidget();
	 public void applyAttribute(String attributeName, Widget invokingWidget);
	 public List<String> getAttributesList(Widget invokingWidget);
	 public List<String> getOperationsList(Widget invokingWidget);
	 public void copyAttributes(Widget widgetSource, Widget widgetTarget);
	 public Widget deepClone(Widget currentWidget, Widget widget);
	 public String serialize(IVkWidget widget);
	 public void deserialize(IVkWidget invokingWidget, String jsonString);
	 public void buildWidget(JSONObject childObj, Widget parent);
}
