package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkListBox;

public class VkListBoxEngine extends VkAbstractWidgetEngine<VkListBox> {
	private final static String RENDER_MODE = "Change Render Mode";
	private final static String SET_MULTIPLE = "Set Up Multiple Select";
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
		list.add(RENDER_MODE);
		list.add(SET_MULTIPLE);
		list.add(ADD_ITEM);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, final Widget invokingWidget) {
		if(attributeName.equals(RENDER_MODE))
		{
			final ListBox listBox = new ListBox();
			listBox.addItem("Drop Down","0");
			listBox.addItem("List","1");
			VkDesignerUtil.getEngine().showAddListDialog("Pick a render mode", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						VkListBox widget = (VkListBox)invokingWidget;
						if(listBox.getSelectedIndex() == 0)
							widget.setVisibleItemCount(1);
						else
							widget.setVisibleItemCount(2);
					}
				});
		}
		else if(attributeName.equals(SET_MULTIPLE))
		{
			final ListBox listBox = new ListBox();
			listBox.addItem("true","true");
			listBox.addItem("false","false");
			VkDesignerUtil.getEngine().showAddListDialog("Pick a render mode", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						if(listBox.getSelectedIndex() == 0)
							DOM.setElementAttribute(invokingWidget.getElement(), "multiple", "multiple");
						else
							DOM.removeElementAttribute(invokingWidget.getElement(), "multiple");
					}
				});
		}
		else if(attributeName.equals(ADD_ITEM))
		{
			showAddItemDialog((VkListBox)invokingWidget);
		}
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
					Window.alert("row and column number cannot be non-numeric");
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
}
