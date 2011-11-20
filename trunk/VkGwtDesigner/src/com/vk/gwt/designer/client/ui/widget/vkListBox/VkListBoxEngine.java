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
package com.vk.gwt.designer.client.ui.widget.vkListBox;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkListBoxEngine extends VkAbstractWidgetEngine<VkListBox> {
	private final static String ADD_ITEM = "Add Item";
	@Override
	public VkListBox getWidget() {
		VkListBox widget = new VkListBox();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(ADD_ITEM);
		list.addAll(VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(ADD_ITEM))
			showAddItemDialog((VkListBox)invokingWidget);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void showAddItemDialog(final VkListBox invokingWidget) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Provide Text and value of item");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		
		HorizontalPanel textHp = new HorizontalPanel();
		textHp.setWidth("100%");
		dialog.add(textHp);
		textHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		textHp.add(new Label("Text: "));
		textHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		textHp.setCellWidth(textHp.getWidget(0), "50%");
		final TextBox textTextBox = new TextBox();
		textHp.add(textTextBox);
		textTextBox.setWidth("300px");
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				textTextBox.setFocus(true);
			}
		}.schedule(100);
		HorizontalPanel valueHp = new HorizontalPanel();
		valueHp.setWidth("100%");
		dialog.add(valueHp);
		valueHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		valueHp.add(new Label("Value: "));
		valueHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		valueHp.setCellWidth(valueHp.getWidget(0), "50%");
		final TextBox valueTextBox = new TextBox();
		valueTextBox.setWidth("300px");
		valueHp.add(valueTextBox);
		
		HorizontalPanel indexHp = new HorizontalPanel();
		indexHp.setWidth("100%");
		dialog.add(indexHp);
		indexHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		indexHp.add(new Label("Index: "));
		indexHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		indexHp.setCellWidth(indexHp.getWidget(0), "50%");
		final TextBox indexTextBox = new TextBox();
		indexTextBox.setWidth("300px");
		indexHp.add(indexTextBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String text = textTextBox.getText();
				String value = valueTextBox.getText();
				int index;
				try{
					index = Integer.parseInt(indexTextBox.getText());
					origDialog.hide();
					invokingWidget.insertItem(text, value, index);
				}catch(NumberFormatException e)
				{
					Window.alert("index cannot be non-numeric");
				}
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
			}
		});
		origDialog.center();
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",listItems:[");
		VkListBox listBox = (VkListBox) widget;
		int itemCount = listBox.getItemCount();
		for(int i = 0; i < itemCount; i++)
		{
			buffer.append("{text:'").append(listBox.getItemText(i)).append("'");
			buffer.append(",value:'").append(listBox.getValue(i)).append("'},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray listItems = jsonObj.get("listItems").isArray();
		ListBox listBox = (ListBox) parent;
		for(int j = 0; j < listItems.size(); j++)
		{
			JSONObject listItem = listItems.get(j).isObject();
			listBox.addItem(listItem.get("text").isString().stringValue(), listItem.get("value").isString().stringValue());
		}
	}
}
