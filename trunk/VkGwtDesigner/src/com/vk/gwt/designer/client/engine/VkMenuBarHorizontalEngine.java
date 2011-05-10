package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEventTextArea;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;

public class VkMenuBarHorizontalEngine extends VkAbstractWidgetEngine<VkMenuBarHorizontal> {
	private static final String ADD_SEPERATOR = "Add Separator";
	private static final String ADD_ITEM = "Add Item";
	private static final String ADD_MENU = "Add Menu";
	private static final String REMOVE_ITEM = "Remove Item";
	private static final String EDIT_ITEM = "Edit Item";
	@Override
	public VkMenuBarHorizontal getWidget() {
		VkMenuBarHorizontal widget = new VkMenuBarHorizontal();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkMenuBarHorizontal menuBar = (VkMenuBarHorizontal)invokingWidget;
		if(attributeName.equals(ADD_SEPERATOR))
			addSeparator(menuBar);
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
	private void addSeparator(VkMenuBarHorizontal menuBar) {
		menuBar.getSeperatorIndices().add(menuBar.getSeparatorIndex(menuBar.addSeparator()));
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(ADD_SEPERATOR);
		list.add(ADD_ITEM);
		list.add(EDIT_ITEM);
		list.add(REMOVE_ITEM);
		list.add(ADD_MENU);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	private void showEditItemAttributeDialog(final VkMenuBarHorizontal menuBar, final int index) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Provide html for item name and JS to execute on its click");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
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
		nameTextArea.setSize("250px", "40px");
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				nameTextArea.setFocus(true);
			}
		}.schedule(100);
		final VkEventTextArea jsTextArea = new VkEventTextArea();
		if(menuBar.getCommandJs().containsKey(index))
		{
			HorizontalPanel jsHp = new HorizontalPanel();
			jsHp.setWidth("100%");
			dialog.add(jsHp);
			jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
			jsHp.add(new Label("Command Js:"));
			jsHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
			jsHp.setCellWidth(jsHp.getWidget(0), "35%");
			jsTextArea.setSize("250px","80px");
			jsHp.add(jsTextArea);
			jsTextArea.setText(menuBar.getCommandJs().get(index));
		}
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
				MenuItem item = menuBar.getMenuItem(index);
				item.setHTML(nameTextArea.getText());
				if(menuBar.getCommandJs().containsKey(index))
				{
					menuBar.getCommandJs().put(index, jsTextArea.getText());
					item.setCommand(new Command() {
						@Override
						public void execute() {
							VkDesignerUtil.executeEvent(jsTextArea.getText(), (Map<String, String>)null);
						}
					});
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
	private void showAddItemAttributeDialog(final VkMenuBarHorizontal menuBar) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Provide html for item name and JS to execute on its click");
		dialog.setWidth("100%");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		
		HorizontalPanel nameHp = new HorizontalPanel();
		nameHp.setWidth("100%");
		dialog.add(nameHp);
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		nameHp.add(new Label("Name HTML:"));
		nameHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		nameHp.setCellWidth(nameHp.getWidget(0), "35%");
		final TextArea nameTextArea = new TextArea();
		nameHp.add(nameTextArea);
		nameTextArea.setSize("250px", "40px");
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
		final VkEventTextArea jsTextArea = new VkEventTextArea();
		jsTextArea.setSize("250px","80px");
		jsHp.add(jsTextArea);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				menuBar.getCommandJs().put(menuBar.getItemCount(), jsTextArea.getText());
				addMenuItem(menuBar, nameTextArea.getText(), jsTextArea.getText());
				origDialog.hide();
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
	private void addMenuItem(VkMenuBarHorizontal menuBar, String name, final String js) {
		menuBar.addItem(new MenuItem(name, new Command() {
			@Override
			public void execute() {
				VkDesignerUtil.executeEvent(js, (Map<String, String>)null);
			}
		}));
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkMenuBarHorizontal menuBar =  (VkMenuBarHorizontal)widget;
		buffer.append(",items:[");
		for(int i = 0, allItems = 0, k = 0; i < menuBar.getItemCount(); i++, allItems++)
		{
			buffer.append("{html:'").append(menuBar.getItems().get(i).getHTML()).append("'");
			if(menuBar.getItems().get(i).getSubMenu()== null)
			{
				if(menuBar.getCommandJs().containsKey(i))
					buffer.append(",js:'").append(menuBar.getCommandJs().get(i).replace('\'', '"')).append("'");
				else
					buffer.append(",child:")
						.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)menuBar.getWidgets().get(i)).getWidgetName()).serialize((IVkWidget) menuBar.getWidgets().get(i)));
			}
			else
				buffer.append(",menu:").append(serialize((IVkWidget) menuBar.getItems().get(i).getSubMenu()));
			buffer.append("},");
			if(k < menuBar.getSeperatorIndices().size() && menuBar.getSeperatorIndices().get(k) == allItems + 1)
			{
				buffer.append("{separator:''},");
				allItems++;
				k++;
			}
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkMenuBarHorizontal tree =  (VkMenuBarHorizontal)parent;
		JSONArray items = jsonObj.get("items").isArray();
		for(int i = 0; i < items.size(); i++)
		{
			JSONObject item = items.get(i).isObject();
			JSONValue js = item.get("js");
			if(js != null)
				((VkMenuBarHorizontalEngine)VkDesignerUtil.getEngineMap().get(((IVkWidget)tree).getWidgetName()))
					.addMenuItem(tree, item.get("html").isString().stringValue(), item.get("js").isString().stringValue());
			else if(item.containsKey("child"))
			{
				JSONObject childObj = item.get("child").isObject();
				JSONString widgetName = childObj.get("widgetName").isString();
				Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
				VkDesignerUtil.getEngine().addWidget(widget, ((IPanel)tree));
				VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
				addAttributes(childObj, widget);
			}
			else if(item.get("separator") == null)
			{
				VkMenuBarHorizontal subTree = (VkMenuBarHorizontal)VkDesignerUtil.getEngine().getWidget(VkMenuBarVertical.NAME);//all submenus are vertical
				addAttributes(item.get("menu").isObject(), subTree);
				tree.addItem(new MenuItem(item.get("html").isString().stringValue(), subTree));
				VkDesignerUtil.getEngineMap().get(((IVkWidget)tree).getWidgetName()).buildWidget(item.get("menu").isObject(), subTree);
			}
			else
				addSeparator(tree);
		}
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget)
	{
		super.copyAttributes(widgetSource, widgetTarget);
		VkMenuBarHorizontal sourceMenuBar = (VkMenuBarHorizontal)widgetSource;
		VkMenuBarHorizontal targetMenuBar = (VkMenuBarHorizontal)widgetTarget;
		for(int i = 0, allItems = 0, k = 0; i < sourceMenuBar.getItemCount(); i++, allItems++)
		{
			if(sourceMenuBar.getItems().get(i).getSubMenu()== null)
				addMenuItem(targetMenuBar, sourceMenuBar.getItems().get(i).getHTML(), 
					sourceMenuBar.getCommandJs().get(sourceMenuBar.getItems().indexOf(sourceMenuBar.getItems().get(i))));
			else
			{
				VkMenuBarHorizontal widget = (VkMenuBarHorizontal)VkDesignerUtil.getEngine().getWidget(VkMenuBarVertical.NAME);
				targetMenuBar.addItem(new MenuItem(sourceMenuBar.getItems().get(i).getHTML()
					, (VkMenuBarVertical) VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).deepClone(sourceMenuBar.getItems().get(i).getSubMenu(), widget)));
			}
			if(k < sourceMenuBar.getSeperatorIndices().size() && sourceMenuBar.getSeperatorIndices().get(k) == allItems + 1)
				addSeparator(targetMenuBar);
		}
	}
}
