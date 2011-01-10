package com.vk.gwt.designer.client.engine;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkHtmlPanelEngine extends VkAbstractWidgetEngine<VkHtmlPanel> {
	@Override
	public VkHtmlPanel getWidget() {
		VkHtmlPanel widget = new VkHtmlPanel();
		init(widget);
		return widget;
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		buffer.append(",widgetId:'").append(widget.hashCode()).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IPanel)
		{
			Iterator<Widget> widgetList = ((IPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{parentId:'").append(child.getElement().getParentElement().getId()).append("',child:");
				if(child instanceof IVkWidget)
					buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
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
		((Widget)parent).getElement().getFirstChildElement().setId(jsonObj.get("widgetId").isString().stringValue());
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			((VkHtmlPanel)parent).add(widget, childObj.get("parentId").isString().stringValue());
			addAttributes(childWidgetObj, widget);
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
	}
}
