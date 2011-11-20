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

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;

public class VkMainDrawingPanel extends VkAbsolutePanel implements IVkPanel, HasVkLoadHandler, HasVkWidgets{
	public static String NAME = "Drawing Panel";
	private String loadHandlerJs = "";
	private static VkMainDrawingPanel drawingPanel;
	private VkMainDrawingPanel(){
		getElement().setId("drawingPanel");
		setPixelSize(Window.getClientWidth() - 20, Window.getClientHeight() - 15);
		DOM.setStyleAttribute(getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(getElement(), "position", "relative");
		if(VkStateHelper.getInstance().isDesignerMode()) {
			InitializeHelper.getInstance().initDesignerEvents(this, WidgetEngineMapping.getInstance().getEngineMap().get(VkMainDrawingPanel.NAME));
			VkStateHelper.getInstance().getMenu().prepareMenu(this);
		}
	}
	
	public static VkMainDrawingPanel getInstance() {
		if(drawingPanel == null)
			drawingPanel = new VkMainDrawingPanel();//not instantiated during declaration declared because there is cyclic instantiation problem between WidgetEngineMapping and VkMainDrawingPanel 
		return drawingPanel;
	}
	
	@Override
	public void clone(Widget targetWidget) {}

	@Override
	public String getWidgetName() {
		return NAME;
	}

	@Override
	public void addLoadHandler(String js) {
		loadHandlerJs = js;
	}

	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkLoadHandler.NAME))
			return loadHandlerJs;
		else
			return "";
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return false;
	}
	@Override
	public boolean isResizable() {
		return false;
	}

	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
}
