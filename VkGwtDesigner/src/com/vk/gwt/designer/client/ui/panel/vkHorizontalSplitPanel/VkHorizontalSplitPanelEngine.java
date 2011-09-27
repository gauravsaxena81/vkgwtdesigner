package com.vk.gwt.designer.client.ui.panel.vkHorizontalSplitPanel;

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
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> widgetList = ((IVkPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				if(child instanceof IVkWidget)
					buffer.append(VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");
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
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			VkStateHelper.getInstance().getEngine().addWidget(widget, ((IVkPanel)parent));
			VkStateHelper.getInstance().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
		}
	}
}
