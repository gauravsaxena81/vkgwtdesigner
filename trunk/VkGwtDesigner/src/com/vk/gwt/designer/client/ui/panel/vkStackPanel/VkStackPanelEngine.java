/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.gwt.designer.client.ui.panel.vkStackPanel;

import java.util.Iterator;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

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
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",children:[");
		if(widget instanceof IVkPanel)
		{
			Iterator<Widget> widgetList = ((IVkPanel)widget).iterator();
			int widgetIndex = 0;
			while(widgetList.hasNext())
			{
				Widget child = widgetList.next();
				buffer.append("{headerHtml:'").append(((VkStackPanel)widget).getHTML(widgetIndex++).replace("'", "\\'").replace("\"", "\\\"")).append("',child:");
				if(child instanceof IVkWidget)
					buffer.append(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append("},");
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
		for(int i = 0; i < childrenArray.size(); i++)
		{
			JSONObject childObj = childrenArray.get(i).isObject();
			JSONObject childWidgetObj = childObj.get("child").isObject();
			JSONString widgetName = childWidgetObj.get("widgetName").isString();
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			VkStateHelper.getInstance().getEngine().addWidget(widget, ((IVkPanel)parent));
			((VkStackPanel)parent).setHeaderHtml(((VkStackPanel)parent).getWidgetCount() - 1, childObj.get("headerHtml").isString().stringValue());
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childWidgetObj, widget);
		}
		addAttributes(jsonObj, parent);
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
		JSONNumber attributeNumberObj;
		attributeJsObj = childObj.get(HasVkSwitchNumberedWidget.NAME);
		if(attributeJsObj != null && (attributeNumberObj = attributeJsObj.isNumber()) != null)
			((HasVkSwitchNumberedWidget)widget).showWidget((int)attributeNumberObj.doubleValue());
	}
	@Override
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
		if(sourceWidget instanceof IVkPanel && targetWidget instanceof IVkPanel)
		{
			Iterator<Widget> widgets = ((IVkPanel)sourceWidget).iterator();
			while(widgets.hasNext())
			{
				Widget currentWidget = widgets.next();
				Widget newWidget = VkStateHelper.getInstance().getEngine().getWidget(((IVkWidget)currentWidget).getWidgetName());
				VkStateHelper.getInstance().setDesignerMode(false);
				if(currentWidget instanceof IVkWidget)
					VkStateHelper.getInstance().getEngine().addWidget(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget) currentWidget).getWidgetName()).deepClone(currentWidget, newWidget), (IVkPanel)targetWidget);
				VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
			}
		}
		VkStateHelper.getInstance().setDesignerMode(false);
		((IVkWidget)sourceWidget).clone(targetWidget);
		//VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		copyAttributes(sourceWidget, targetWidget);
		VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
		return targetWidget;
	}
}
