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
package com.vk.gwt.designer.client.ui.panel.vkPopUpPanel;

import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkPopUpPanelEngine extends VkAbstractWidgetEngine<VkPopUpPanel> {
	//private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkPopUpPanel getWidget() {
		VkPopUpPanel widget = new VkPopUpPanel();
		widget.show();
		widget.setModal(false);
		init(widget);
		return widget;
	}
	/*@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkPopUpPanel widget = (VkPopUpPanel)invokingWidget;
		if(attributeName.equals(SET_POSITION))	{
			widget.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
			Window.alert("Position of Panel is saved. Panel will appear at this position when it shows.");
		} else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}*/
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		//list.add(0, SET_POSITION);
		return list;
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget) {
		int top = VkDesignerUtil.getPixelValue(widgetSource.getElement(), "top");
		int left = VkDesignerUtil.getPixelValue(widgetSource.getElement(), "left");
		((VkPopUpPanel)widgetTarget).setPopupPosition(left, top);
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		super.addAttributes(childObj, widget);
		int top = VkDesignerUtil.getPixelValue(widget.getElement(), "top");
		int left = VkDesignerUtil.getPixelValue(widget.getElement(), "left");
		((VkPopUpPanel)widget).setPopupPosition(left, top);
		if(((HasVkInitiallyShowing)widget).isInitiallyShowing())
			((VkPopUpPanel)widget).show();
		else
			((VkPopUpPanel)widget).hide();
	}
}