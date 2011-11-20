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
package com.vk.gwt.designer.client.designer;

import java.util.Iterator;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class VkMainDrawingPanelEngine extends VkAbstractWidgetEngine<VkMainDrawingPanel> {
	@Override
	public VkMainDrawingPanel getWidget() {
		VkMainDrawingPanel drawingPanel = VkMainDrawingPanel.getInstance();
		return drawingPanel;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		addAttributes(jsonObj, parent);
		super.buildWidget(jsonObj, parent);
	}
	@Override
	public String serialize(IVkWidget vkMainDrawingPanel) {
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(vkMainDrawingPanel.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) vkMainDrawingPanel)).append("'");
		serializeAttributes(buffer, (Widget) vkMainDrawingPanel);
		buffer.append(",children:[");
		Iterator<Widget> widgetList = ((IVkPanel)vkMainDrawingPanel).iterator();
		while(widgetList.hasNext())
			serializeChild(widgetList.next(), buffer);
		addPopUpWidgets((Widget) vkMainDrawingPanel, buffer);
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	private void addPopUpWidgets(Widget vkMainDrawingPanel, StringBuffer buffer) {
		Panel parent = (Panel) vkMainDrawingPanel.getParent();
		Iterator<Widget> popUpWidgets = parent.iterator();
		while(popUpWidgets.hasNext()) {
			Widget popup = popUpWidgets.next();
			if(popup != vkMainDrawingPanel)
				serializeChild(popup, buffer);
		}
	}
	private void serializeChild(Widget child, StringBuffer buffer) {
		if(child instanceof IVkWidget)
			buffer.append(WidgetEngineMapping.getInstance().getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");		
	}
}
