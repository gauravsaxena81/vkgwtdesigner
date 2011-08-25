package com.vk.gwt.designer.client.engine;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkHorizontalSplitPanelEngine extends VkAbstractWidgetEngine<VkHorizontalSplitPanel> {

	@Override
	public VkHorizontalSplitPanel getWidget() {
		VkHorizontalSplitPanel widget = new VkHorizontalSplitPanel();
		init(widget);
		return widget;
	}
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",splitter:").append(((VkHorizontalSplitPanel)widget).splitterPosition());
		buffer.append(",children:[");
		if(widget instanceof IPanel)
		{
			Iterator<Widget> widgetList = ((IPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				if(child instanceof IVkWidget)
					buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		addAttributes(jsonObj, parent);
		((VkHorizontalSplitPanel)parent).setSplitPosition((int)jsonObj.get("splitter").isNumber().doubleValue() + "px");
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
	}
}
