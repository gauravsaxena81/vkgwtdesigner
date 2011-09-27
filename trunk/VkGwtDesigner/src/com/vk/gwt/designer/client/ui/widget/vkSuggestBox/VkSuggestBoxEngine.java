package com.vk.gwt.designer.client.ui.widget.vkSuggestBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IEventRegister;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkSuggestBoxEngine extends VkAbstractWidgetEngine<VkSuggestBox> {
	private static final String ADD_SUGGESTION = "Add Suggestion";
	private static final String REMOVE_SUGGESTION = "Remove Suggestion";
	private static final String EDIT_SUGGESTION = "Edit Suggestion";
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
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please edit the suggestion", tb
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
						oracle.add(tb.getText());
						widget.getSuggestions().add(tb.getText());
					}
				});
		}
		else if(attributeName.equals(REMOVE_SUGGESTION))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("200px");
			int i = 0;
			for (Iterator<String> iterator = widget.getSuggestions().iterator(); iterator.hasNext();)
				listBox.addItem(iterator.next(),Integer.toString(i++));
			VkDesignerUtil.showAddListDialog("Pick a suggestion to delete", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
						oracle.clear();
						widget.getSuggestions().remove(listBox.getSelectedIndex());
						for (Iterator<String> iterator = widget.getSuggestions().iterator(); iterator.hasNext();)
							oracle.add(iterator.next());
					}
				});
		}
		else if(attributeName.equals(EDIT_SUGGESTION))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("200px");
			int i = 0;
			for (Iterator<String> iterator = widget.getSuggestions().iterator(); iterator.hasNext();)
				listBox.addItem(iterator.next(),Integer.toString(i++));
			VkDesignerUtil.showAddListDialog("Pick a suggestion to delete", listBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						final TextBox tb = new TextBox();
						tb.setText(widget.getSuggestions().get(listBox.getSelectedIndex()));
						tb.setWidth("300px");
						VkDesignerUtil.showAddTextAttributeDialog("Please edit the suggestion", tb
							, new IEventRegister() {
								@Override
								public void registerEvent(String js) {
									MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)widget.getSuggestOracle();
									oracle.clear();
									widget.getSuggestions().remove(listBox.getSelectedIndex());
									widget.getSuggestions().add(listBox.getSelectedIndex(), tb.getText());
									for (Iterator<String> iterator = widget.getSuggestions().iterator(); iterator.hasNext();)
										oracle.add(iterator.next());
								}
						});
					}
			});
		}
		VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		
		List<String> list = new ArrayList<String>();
		list.add(ADD_SUGGESTION);
		list.add(EDIT_SUGGESTION);
		list.add(REMOVE_SUGGESTION);
		list.addAll(VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		VkSuggestBox suggestBox = (VkSuggestBox)widget;
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",suggestions:[");
		for(int i = 0; i < suggestBox.getSuggestions().size(); i++)
			buffer.append("'").append(suggestBox.getSuggestions().get(i)).append("',");
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkSuggestBox suggestBox = (VkSuggestBox)parent;
		MultiWordSuggestOracle oracle = (MultiWordSuggestOracle)suggestBox.getSuggestOracle();
		JSONArray suggestionArray = jsonObj.get("suggestions").isArray();
		suggestBox.getSuggestions().clear();
		addAttributes(jsonObj, parent);
		for(int i = 0; i < suggestionArray.size(); i++)
		{
			String suggestion = suggestionArray.get(i).isString().stringValue();
			oracle.add(suggestion);
			suggestBox.getSuggestions().add(suggestion);
		}
	}
}
