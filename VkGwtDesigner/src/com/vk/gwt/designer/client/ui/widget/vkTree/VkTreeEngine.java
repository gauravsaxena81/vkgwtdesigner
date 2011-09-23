package com.vk.gwt.designer.client.ui.widget.vkTree;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IEventRegister;

public class VkTreeEngine extends VkAbstractWidgetEngine<VkTree> {
	private static final String ADD_ITEM = "Add Item";
	private static final String EDIT_ITEM = "Edit Selected Item";
	private static final String REMOVE_ITEM = "Remove Selecetd Item";
	@Override
	public VkTree getWidget() {
		VkTree widget = new VkTree();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkTree widget = (VkTree)invokingWidget;
		if(attributeName.equals(ADD_ITEM))
		{
			TextBox textBox = new TextBox();
			textBox.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide item HTML", textBox, new IEventRegister() {
				@Override
				public void registerEvent(String html) {
					if(widget.getSelectedItem() == null)
						widget.addItem(new TreeItem(html));
					else
						widget.getSelectedItem().addItem(new TreeItem(html));
				}
			});
		}	
		else if(attributeName.equals(EDIT_ITEM))
		{
			TextBox textBox = new TextBox();
			textBox.setWidth("300px");
			textBox.setText(widget.getSelectedItem().getText());
			VkDesignerUtil.showAddTextAttributeDialog("Please edit item HTML", textBox, new IEventRegister() {
				@Override
				public void registerEvent(String html) {
					if(widget.getSelectedItem() != null)
						widget.getSelectedItem().setHTML(html);
					else
						Window.alert("An item needs to be selected for this operation");
				}
			});
		}
		else if(attributeName.equals(REMOVE_ITEM))
		{
			if(widget.getSelectedItem() != null)
				widget.getSelectedItem().remove();
			else
				Window.alert("An item needs to be selected for this operation");
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		list.add(3, ADD_ITEM);
		list.add(4,EDIT_ITEM);
		list.add(5, REMOVE_ITEM);
		return list;
	}
	@Override//overriding deep clone because addition of widgets has to be done in a different way
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		//VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		copyAttributes(sourceWidget, targetWidget);
		VkTree vkTreeSource = (VkTree)sourceWidget;
		VkTree vkTreeTarget = (VkTree)targetWidget;
		int treeItemNum = vkTreeSource.getItemCount();
		for(int i = 0; i < treeItemNum; i++)
		{
			TreeItem treeItem = vkTreeSource.getItem(i);
			TreeItem copiedItem;
			if(treeItem.getWidget() == null)
				copiedItem = new TreeItem(treeItem.getHTML());
			else
				copiedItem = new TreeItem((VkDesignerUtil.getEngineMap().get(((IVkWidget)treeItem.getWidget()).getWidgetName())).deepClone(treeItem.getWidget()
						, VkDesignerUtil.getEngine().getWidget(((IVkWidget)treeItem.getWidget()).getWidgetName())));
			vkTreeTarget.addItem(copiedItem);
			deepClone(vkTreeSource.getItem(i), copiedItem);
		}
		return targetWidget;
	}

	private void deepClone(TreeItem sourceItem, TreeItem targetItem) {
		int treeItemNum = sourceItem.getChildCount();
		for(int i = 0; i < treeItemNum; i++)
		{
			TreeItem treeItem = sourceItem.getChild(i);
			TreeItem copiedItem;
			if(treeItem.getWidget() == null)
				copiedItem = new TreeItem(treeItem.getHTML());
			else
				copiedItem = new TreeItem(VkDesignerUtil.getEngineMap().get(((IVkWidget)treeItem.getWidget()).getWidgetName()).deepClone(treeItem.getWidget()
						, VkDesignerUtil.getEngine().getWidget(((IVkWidget)treeItem.getWidget()).getWidgetName())));
			targetItem.addItem(copiedItem);
			deepClone(sourceItem.getChild(i), copiedItem);
		}
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkTree tree =  (VkTree)widget;
		buffer.append(",items:[");
		for(int i = 0; i < tree.getItemCount(); i++)
		{
			TreeItem child = tree.getItem(i);
			if(child.getWidget() == null)
				buffer.append("{html:'").append(child.getHTML()).append("'");
			else
				buffer.append("{widget:").append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child.getWidget()).getWidgetName())
						.serialize((IVkWidget) child.getWidget()));
			buffer.append(",selected:").append(child.isSelected());
			buffer.append(",open:").append(child.getState());
			if(child.getChildCount() > 0)
				buffer.append(serialize(child));
			buffer.append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	private StringBuffer serialize(TreeItem item) {
		StringBuffer buffer = new StringBuffer(",items:[");
		for(int i = 0, len = item.getChildCount(); i < len; i++)
		{
			TreeItem child = item.getChild(i);
			if(child.getWidget() == null)
				buffer.append("{html:'").append(child.getHTML()).append("'");
			else
				buffer.append("{widget:").append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child.getWidget()).getWidgetName())
						.serialize((IVkWidget) child.getWidget()));
			buffer.append(",selected:").append(child.isSelected());
			buffer.append(",open:").append(child.getState());
			if(child.getChildCount() > 0)
				buffer.append(serialize(child));
			buffer.append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		return buffer;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkTree tree =  (VkTree)parent;
		JSONArray items = jsonObj.get("items").isArray();
		addAttributes(jsonObj, parent);
		for(int i = 0; i < items.size(); i++)
		{
			JSONObject item = items.get(i).isObject();
			JSONValue html = item.get("html");
			TreeItem child;
			if(html != null)
				tree.addItem(child = new TreeItem(html.isString().stringValue()));
			else
				child = addWidgetAsTreeitem(item, tree);
			child.setSelected(item.get("selected").isBoolean().booleanValue());
			if(item.get("items") != null)
				buildItemWidget(item, tree.getItem(tree.getItemCount() - 1));
			child.setState(item.get("open").isBoolean().booleanValue());
		}
	}
	private TreeItem addWidgetAsTreeitem(JSONObject item, VkTree tree) {
		JSONObject jsonObj = item.get("widget").isObject();
		JSONString widgetName = jsonObj.get("widgetName").isString();
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
		TreeItem child = tree.addItem(widget);
		addAttributes(jsonObj, widget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(jsonObj, widget);
		return child;
	}
	private void buildItemWidget(JSONObject itemJson, TreeItem parent) {
		TreeItem treeItem = parent;
		JSONArray items = itemJson.get("items").isArray();
		for(int i = 0; i < items.size(); i++)
		{
			JSONObject item = items.get(i).isObject();
			JSONValue html = item.get("html");
			TreeItem child;
			if(html != null)
				treeItem.addItem(child = new TreeItem(html.isString().stringValue()));
			else
				child = addWidgetAsTreeitem(item, treeItem);
			child.setSelected(item.get("selected").isBoolean().booleanValue());
			if(item.get("items") != null)
				buildItemWidget(item, treeItem.getChild(treeItem.getChildCount() - 1));
			child.setState(item.get("open").isBoolean().booleanValue(), false);
		}
	}
	private TreeItem addWidgetAsTreeitem(JSONObject item, TreeItem treeItem) {
		JSONObject jsonObj = item.get("widget").isObject();
		JSONString widgetName = jsonObj.get("widgetName").isString();
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
		TreeItem child = treeItem.addItem(widget);
		addAttributes(jsonObj, widget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(jsonObj, widget);
		return child;
	}
}