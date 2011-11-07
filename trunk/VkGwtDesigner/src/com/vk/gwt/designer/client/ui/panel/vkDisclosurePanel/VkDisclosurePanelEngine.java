package com.vk.gwt.designer.client.ui.panel.vkDisclosurePanel;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;
import com.vk.gwt.designer.client.designer.WidgetEngineMapping;

public class VkDisclosurePanelEngine extends VkAbstractWidgetEngine<VkDisclosurePanel>{
	@Override
	public VkDisclosurePanel getWidget() {
		VkDisclosurePanel widget = new VkDisclosurePanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		return optionList;
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",open:").append(((VkDisclosurePanel)widget).isOpen());
		buffer.append(",children:[");
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> widgetList = ((IVkPanel)widget).iterator();
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				if(child instanceof IVkWidget)
					buffer.append(WidgetEngineMapping.getInstance().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		super.buildWidget(jsonObj, parent);
		JSONValue attributeJsObj = jsonObj.get("open");
		JSONBoolean attributeBooleanObj;
		if(attributeJsObj != null && (attributeBooleanObj = attributeJsObj.isBoolean()) != null)
			((VkDisclosurePanel)parent).setOpen(attributeBooleanObj.booleanValue());
	}
}