package com.vk.gwt.designer.client.ui.panel.vkHtmlPanel;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

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
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		buffer.append(",widgetId:'").append(widget.hashCode()).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> widgetList = ((IVkPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{parentId:'").append(child.getElement().getParentElement().getId()).append("',child:");
				if(child instanceof IVkWidget)
					buffer.append(VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
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
		((Widget)parent).getElement().getFirstChildElement().setId(jsonObj.get("widgetId").isString().stringValue());
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			((VkHtmlPanel)parent).add(widget, childObj.get("parentId").isString().stringValue());
			//addAttributes(childWidgetObj, widget);
			VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
	}
}
