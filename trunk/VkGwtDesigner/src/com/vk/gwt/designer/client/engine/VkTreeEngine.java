package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

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
	@Override
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
}
