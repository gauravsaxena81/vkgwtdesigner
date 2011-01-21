package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkTree;

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
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide item HTML", new TextBox(), new IEventRegister() {
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
			textBox.setText(widget.getSelectedItem().getText());
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please edit item HTML", textBox, new IEventRegister() {
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
		List<String> list = new ArrayList<String>();
		list.add(ADD_ITEM);
		list.add(EDIT_ITEM);
		list.add(REMOVE_ITEM);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override//overriding deep clone because addition of widgets has to be done in a different way
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
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
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkTree tree =  (VkTree)widget;
		buffer.append(",items:[");
		for(int i = 0; i < tree.getItemCount(); i++)
		{
			if(tree.getItem(i).getWidget() == null)
				buffer.append("{html:'").append(tree.getItem(i).getHTML()).append("'");
			else
				buffer.append("{widget:").append(VkDesignerUtil.getEngineMap().get(((IVkWidget)tree.getItem(i).getWidget()).getWidgetName())
						.serialize((IVkWidget) tree.getItem(i).getWidget()));
			if(tree.getItem(i).getChildCount() > 0)
				buffer.append(serialize(tree.getItem(i)));
			buffer.append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	private StringBuffer serialize(TreeItem item) {
		StringBuffer buffer = new StringBuffer(",items:[");
		for(int i = 0; i < item.getChildCount(); i++)
		{
			if(item.getChild(i).getWidget() == null)
				buffer.append("{html:'").append(item.getChild(i).getHTML()).append("'");
			else
				buffer.append("{widget:").append(VkDesignerUtil.getEngineMap().get(((IVkWidget)item.getChild(i).getWidget()).getWidgetName())
						.serialize((IVkWidget) item.getChild(i).getWidget()));
			if(item.getChild(i).getChildCount() > 0)
				buffer.append(serialize(item.getChild(i)));
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
		for(int i = 0; i < items.size(); i++)
		{
			JSONObject item = items.get(i).isObject();
			JSONValue html = item.get("html");
			TreeItem treeItem = new TreeItem();
			tree.addItem(treeItem);
			if(html != null)
				treeItem.setHTML(html.isString().stringValue());
			else
				//VkDesignerUtil.getEngineMap().get(item.get("widget").isObject().get("widgetName").isString()).buildWidget(item.get("widget").isObject(), tree);
				addWidgetAsTreeitem(item, tree);
			if(item.get("items") != null)
				buildItemWidget(item, tree.getItem(tree.getItemCount() - 1));
		}
	}
	private void addWidgetAsTreeitem(JSONObject item, VkTree tree) {
		JSONObject jsonObj = item.get("widget").isObject();
		JSONString widgetName = jsonObj.get("widgetName").isString();
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
		tree.addItem(widget);
		addAttributes(jsonObj, widget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(jsonObj, widget);
	}
	private void buildItemWidget(JSONObject itemJson, TreeItem parent) {
		TreeItem treeItem = parent;
		JSONArray items = itemJson.get("items").isArray();
		for(int i = 0; i < items.size(); i++)
		{
			JSONObject item = items.get(i).isObject();
			JSONValue html = item.get("html");
			if(html != null)
				treeItem.addItem(new TreeItem(html.isString().stringValue()));
			else
				addWidgetAsTreeitem(item, treeItem);
			if(item.get("items") != null)
				buildItemWidget(item, treeItem.getChild(treeItem.getChildCount() - 1));
		}
	}
	private void addWidgetAsTreeitem(JSONObject item, TreeItem treeItem) {
		JSONObject jsonObj = item.get("widget").isObject();
		JSONString widgetName = jsonObj.get("widgetName").isString();
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
		treeItem.addItem(widget);
		addAttributes(jsonObj, widget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(jsonObj, widget);
	}
}
