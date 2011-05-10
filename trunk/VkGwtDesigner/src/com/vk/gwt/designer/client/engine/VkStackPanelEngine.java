package com.vk.gwt.designer.client.engine;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkStackPanelEngine extends VkAbstractWidgetEngine<VkStackPanel> {
	@Override
	public VkStackPanel getWidget() {
		VkStackPanel widget = new VkStackPanel();
		init(widget);
		return widget;
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IPanel)
		{
			Iterator<Widget> widgetList = ((IPanel)widget).iterator();
			int widgetIndex = 0;
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{headerHtml:'").append(((VkStackPanel)widget).getHTML(widgetIndex++).replace("'", "\\'").replace("\"", "\\\""))
					.append("',child:");
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
	protected void serializeAttributes(StringBuffer buffer, Widget widgetSource)
	{
		if(!widgetSource.getStyleName().isEmpty())
			buffer.append(",className:'" + widgetSource.getStyleName() + "'");
		if(!widgetSource.getTitle().isEmpty())
			buffer.append(",title:'" + widgetSource.getTitle() + "'");
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			VkDesignerUtil.getEngine().addWidget(widget, ((IPanel)parent));
			((VkStackPanel)parent).setHeaderHtml(((VkStackPanel)parent).getWidgetCount() - 1, childObj.get("headerHtml").isString().stringValue());
			addAttributes(childWidgetObj, widget);
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONValue attributeJsObj = childObj.get("style");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "style", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("title");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "title", attributeStringObj.stringValue()); 
		attributeJsObj = childObj.get("className");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "className", attributeStringObj.stringValue());
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		int widgetCount = ((VkStackPanel)widgetSource).getWidgetCount();
		for(int i = 0 ; i < widgetCount; i++)
			((VkStackPanel)widgetTarget).setHeaderHtml(i, ((VkStackPanel)widgetSource).getHTML(i));
	}
}
