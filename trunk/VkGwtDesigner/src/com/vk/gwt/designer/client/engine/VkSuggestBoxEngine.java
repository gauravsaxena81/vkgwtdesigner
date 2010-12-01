package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkSuggestBox;

public class VkSuggestBoxEngine extends VkAbstractWidgetEngine<VkSuggestBox> {
	private static final String ADD_SUGGESTION = "Add Suggestion";
	private static final String REMOVE_SUGGESTION = "Remove Suggestion";
	private static final String EDIT_SUGGESTION = "Edit Suggestion";
	private List<String> suggestions = new ArrayList<String>();
	@Override
	public VkSuggestBox getWidget() {
		VkSuggestBox widget = new VkSuggestBox();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkSuggestBox widget = (VkSuggestBox)invokingWidget;
		if(attributeName.equals(ADD_SUGGESTION))
		{
			final TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please edit the suggestion", tb
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
						oracle.add(tb.getText());
						suggestions.add(tb.getText());
					}
				});
		}
		else if(attributeName.equals(REMOVE_SUGGESTION))
		{
			final ListBox listBox = new ListBox();
			int i = 0;
			for (Iterator<String> iterator = suggestions.iterator(); iterator.hasNext();)
				listBox.addItem(iterator.next(),Integer.toString(i++));
			VkDesignerUtil.getEngine().showAddListDialog("Pick a suggestion to delete", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
						oracle.clear();
						suggestions.remove(listBox.getSelectedIndex());
						for (Iterator<String> iterator = suggestions.iterator(); iterator.hasNext();)
							oracle.add(iterator.next());
					}
				});
		}
		else if(attributeName.equals(EDIT_SUGGESTION))
		{
			final ListBox listBox = new ListBox();
			int i = 0;
			for (Iterator<String> iterator = suggestions.iterator(); iterator.hasNext();)
				listBox.addItem(iterator.next(),Integer.toString(i++));
			VkDesignerUtil.getEngine().showAddListDialog("Pick a suggestion to delete", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						final TextBox tb = new TextBox();
						tb.setText(suggestions.get(listBox.getSelectedIndex()));
						tb.setWidth("100px");
						VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please edit the suggestion", tb
							, new IEventRegister() {
								@Override
								public void registerEvent(String js) {
									MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
									oracle.clear();
									suggestions.remove(listBox.getSelectedIndex());
									suggestions.add(listBox.getSelectedIndex(), tb.getText());
									for (Iterator<String> iterator = suggestions.iterator(); iterator.hasNext();)
										oracle.add(iterator.next());
								}
						});
					}
			});
		}
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		
		List<String> list = new ArrayList<String>();
		list.add(ADD_SUGGESTION);
		list.add(EDIT_SUGGESTION);
		list.add(REMOVE_SUGGESTION);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	public List<String> getSuggestions() {
		return suggestions;
	}
}
