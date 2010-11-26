package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.util.VkEventTextArea;
import com.vk.gwt.designer.client.widgets.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;

public class VkMenuBarHorizontalEngine extends VkAbstractWidgetEngine<VkMenuBarHorizontal> {
	private static final String AUTO_OPEN = "Set Auto Open";
	private static final String ADD_SEPERATOR = "Add Separator";
	private static final String ADD_ITEM = "Add Item";
	private static final String ADD_MENU = "Add Menu";
	private static final String REMOVE_ITEM = "Remove Item";
	private static final String EDIT_ITEM = "Edit Item";
	private List<String> commandJs = new ArrayList<String>();
	@Override
	public VkMenuBarHorizontal getWidget() {
		VkMenuBarHorizontal widget = new VkMenuBarHorizontal();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkMenuBarHorizontal menuBar = (VkMenuBarHorizontal)invokingWidget;
		if(attributeName.equals(AUTO_OPEN))
		{
			final ListBox listBox = new ListBox();
			listBox.addItem("True","0");
			listBox.addItem("False","1");
			listBox.setSelectedIndex(menuBar.getAutoOpen()? 0 : 1);
			VkDesignerUtil.getEngine().showAddListDialog("Pick 'open on hover' setting", listBox
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						if(listBox.getSelectedIndex() == 0)
							menuBar.setAutoOpen(true);
						else
							menuBar.setAutoOpen(false);
					}
				});
		}
		else if(attributeName.equals(ADD_SEPERATOR))
			menuBar.addSeparator();
		else if(attributeName.equals(ADD_ITEM))
			showAddItemAttributeDialog(menuBar);
		else if(attributeName.equals(REMOVE_ITEM))
		{
			final ListBox listBox = new ListBox();
			int itemCount = menuBar.getItemCount();
			if(itemCount == 0)
			{
				Window.alert("No Items found to remove");
				return;
			}
			for(int i = 0; i < itemCount; i++)
				listBox.addItem(menuBar.getMenuItem(i).getText(), Integer.toString(i));
			VkDesignerUtil.getEngine().showAddListDialog("Choose the item to edit", listBox, new IEventRegister() {
				@Override
				public void registerEvent(String number) {
					menuBar.removeItem(menuBar.getMenuItem(listBox.getSelectedIndex()));
				}
			});
		}
		else if(attributeName.equals(EDIT_ITEM))
		{
			final ListBox listBox = new ListBox();
			int itemCount = menuBar.getItemCount();
			if(itemCount == 0)
			{
				Window.alert("No Items found to edit");
				return;
			}
			for(int i = 0; i < itemCount; i++)
				listBox.addItem(menuBar.getMenuItem(i).getText(), Integer.toString(i));
			VkDesignerUtil.getEngine().showAddListDialog("Choose the item to edit", listBox, new IEventRegister() {
				@Override
				public void registerEvent(String number) {
					int index = listBox.getSelectedIndex();
					showEditItemAttributeDialog(menuBar, index);
				}
			});
		}
		else if(attributeName.equals(ADD_MENU))
		{
			final TextBox nameTb = new TextBox();
			nameTb.setWidth("80px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide name of sub-menu", nameTb, new IEventRegister() {
				
				@Override
				public void registerEvent(String js) {
					final VkMenuBarVertical widget = (VkMenuBarVertical)VkDesignerUtil.getEngine().getWidget(VkMenuBarVertical.NAME);
					menuBar.addItem(new MenuItem(nameTb.getText(), widget));
				}
			});
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(AUTO_OPEN);
		list.add(ADD_SEPERATOR);
		list.add(ADD_ITEM);
		list.add(EDIT_ITEM);
		list.add(REMOVE_ITEM);
		list.add(ADD_MENU);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	private void showEditItemAttributeDialog(final VkMenuBarHorizontal menuBar, final int index) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		dialog.add(new Label("Provide html for item name and JS to execute on its click"));
		HorizontalPanel nameHp = new HorizontalPanel();
		nameHp.setWidth("100%");
		dialog.add(nameHp);
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		nameHp.add(new Label("Name HTML:"));
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		nameHp.setCellWidth(nameHp.getWidget(0), "35%");
		final TextArea nameTextArea = new TextArea();
		nameTextArea.setText(menuBar.getMenuItem(index).getHTML());
		nameHp.add(nameTextArea);
		nameTextArea.setSize("150px", "40px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				nameTextArea.setFocus(true);
			}
		};
		t.schedule(100);
		HorizontalPanel jsHp = new HorizontalPanel();
		jsHp.setWidth("100%");
		dialog.add(jsHp);
		jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		jsHp.add(new Label("Command Js:"));
		jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		jsHp.setCellWidth(jsHp.getWidget(0), "35%");
		final TextArea jsTextArea = new VkEventTextArea();
		jsTextArea.setSize("150px","40px");
		jsHp.add(jsTextArea);
		jsTextArea.setText(commandJs.get(index));
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				commandJs.remove(index);
				commandJs.add(index, jsTextArea.getText());
				MenuItem item = menuBar.getMenuItem(index);
				item.setHTML(nameTextArea.getText());
				item.setCommand(new Command() {
					@Override
					public void execute() {
						VkDesignerUtil.executeEvent(jsTextArea.getText(), (Map<String, String>)null);
					}
				});
				dialog.removeFromParent();
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
	private void showAddItemAttributeDialog(final VkMenuBarHorizontal menuBar) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		dialog.add(new Label("Provide html for item name and JS to execute on its click"));
		HorizontalPanel nameHp = new HorizontalPanel();
		nameHp.setWidth("100%");
		dialog.add(nameHp);
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		nameHp.add(new Label("Name HTML:"));
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		nameHp.setCellWidth(nameHp.getWidget(0), "35%");
		final TextArea nameTextArea = new TextArea();
		nameHp.add(nameTextArea);
		nameTextArea.setSize("150px", "40px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				nameTextArea.setFocus(true);
			}
		};
		t.schedule(100);
		HorizontalPanel jsHp = new HorizontalPanel();
		jsHp.setWidth("100%");
		dialog.add(jsHp);
		jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		jsHp.add(new Label("Command Js:"));
		jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		jsHp.setCellWidth(jsHp.getWidget(0), "35%");
		final TextArea jsTextArea = new VkEventTextArea();
		jsTextArea.setSize("150px","40px");
		jsHp.add(jsTextArea);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				commandJs.add(jsTextArea.getText());
				menuBar.addItem(new MenuItem(nameTextArea.getText(), new Command() {
					@Override
					public void execute() {
						VkDesignerUtil.executeEvent(jsTextArea.getText(), (Map<String, String>)null);
					}
				}));
				dialog.removeFromParent();
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
