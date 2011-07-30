package com.vk.gwt.designer.client.engine;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkDeckPanelEngine extends VkAbstractWidgetEngine<VkDeckPanel>{
	@Override
	public VkDeckPanel getWidget() {
		VkDeckPanel widget = new VkDeckPanel();
		init(widget);
		return widget;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			if(childObj == null)
				return;
			JSONString widgetName = childObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			VkDesignerUtil.getEngine().addWidget(widget, ((IPanel)parent));
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
		}
		addAttributes(jsonObj, parent);
	}
}
