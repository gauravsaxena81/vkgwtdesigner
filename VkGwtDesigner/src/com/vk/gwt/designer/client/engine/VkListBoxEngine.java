package com.vk.gwt.designer.client.engine;

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
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.widgets.VkListBox;

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
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(ADD_ITEM))
			showAddItemDialog((VkListBox)invokingWidget);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void showAddItemDialog(final VkListBox invokingWidget) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		dialog.add(new Label("Provide Text and value of item"));
		HorizontalPanel textHp = new HorizontalPanel();
		textHp.setWidth("100%");
		dialog.add(textHp);
		textHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		textHp.add(new Label("Text: "));
		textHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		textHp.setCellWidth(textHp.getWidget(0), "50%");
		final TextBox textTextBox = new TextBox();
		textHp.add(textTextBox);
		textTextBox.setWidth("50px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				textTextBox.setFocus(true);
			}
		};
		t.schedule(100);
		HorizontalPanel valueHp = new HorizontalPanel();
		valueHp.setWidth("100%");
		dialog.add(valueHp);
		valueHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		valueHp.add(new Label("Value: "));
		valueHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		valueHp.setCellWidth(valueHp.getWidget(0), "50%");
		final TextBox valueTextBox = new TextBox();
		valueTextBox.setWidth("50px");
		valueHp.add(valueTextBox);
		
		HorizontalPanel indexHp = new HorizontalPanel();
		indexHp.setWidth("100%");
		dialog.add(indexHp);
		indexHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		indexHp.add(new Label("Index: "));
		indexHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		indexHp.setCellWidth(indexHp.getWidget(0), "50%");
		final TextBox indexTextBox = new TextBox();
		indexTextBox.setWidth("50px");
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
					dialog.removeFromParent();
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
				dialog.removeFromParent();
			}
		});
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
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
