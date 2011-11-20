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
package com.vk.gwt.designer.client.ui.panel.vkDeckPanel;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IMultipleWidgetDialogCallback;
import com.vk.gwt.designer.client.designer.VkMainDrawingPanel;
import com.vk.gwt.designer.client.designer.VkStateHelper;
import com.vk.gwt.designer.client.designer.WidgetEngineMapping;
import com.vk.gwt.designer.client.ui.widget.button.vkButton.VkButton;
import com.vk.gwt.designer.client.ui.widget.label.vkLabel.VkLabel;
import com.vk.gwt.designer.client.ui.widget.vkImage.VkImage;

public class VkDeckPanelEngine extends VkAbstractWidgetEngine<VkDeckPanel>{
	private final static String ADD_ASSOCIATED_WIDGET = "Add associated widget";
	@Override
	public VkDeckPanel getWidget() {
		VkDeckPanel widget = new VkDeckPanel();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget){
		if(attributeName.equals(ADD_ASSOCIATED_WIDGET)){
			final ListBox widgetListBox = new ListBox();
			widgetListBox.addItem(VkLabel.NAME);
			widgetListBox.addItem(VkButton.NAME);
			widgetListBox.addItem(VkImage.NAME);
			VkDeckPanel deck =(VkDeckPanel)invokingWidget;
			final ListBox pageListBox = new ListBox();
			DOM.setStyleAttribute(pageListBox.getElement(), "marginTop", "10px");
			for(int i = 0, len = deck.getWidgetCount(); i < len; i++)
				pageListBox.addItem("Page " + i);
			
			VkDesignerUtil.showAddWidgetsDialog("Choose associated widget", new IMultipleWidgetDialogCallback() {
				@Override
				public void save() {
					Widget widget = VkStateHelper.getInstance().getEngine().addWidgetByName(VkMainDrawingPanel.getInstance()
					, widgetListBox.getItemText(widgetListBox.getSelectedIndex()));
					if(widget instanceof HasVkText)
						((HasVkText) widget).setText(pageListBox.getItemText(pageListBox.getSelectedIndex()));
					else
						widget.getElement().setTitle(pageListBox.getItemText(pageListBox.getSelectedIndex()));
					((HasVkClickHandler)widget).addClickHandler("&(" + invokingWidget.getElement().getId() + ").showWidget(" + pageListBox.getSelectedIndex() + ");");
				}
			}, widgetListBox, pageListBox);
		} else
			super.applyAttribute(attributeName, invokingWidget);
	}
	public List<String> getAttributesList(Widget invokingWidget){
		List<String> attributesList = super.getAttributesList(invokingWidget);
		attributesList.add(ADD_ASSOCIATED_WIDGET);
		return attributesList;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		for(int i = 0; i < childrenArray.size(); i++) {
			JSONObject childObj = childrenArray.get(i).isObject();
			if(childObj == null)
				return;
			JSONString widgetName = childObj.get("widgetName").isString();
			Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName.stringValue());
			VkStateHelper.getInstance().getEngine().addWidget(widget, ((IVkPanel)parent));
			WidgetEngineMapping.getInstance().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
		}
		addAttributes(jsonObj, parent);
	}
}
