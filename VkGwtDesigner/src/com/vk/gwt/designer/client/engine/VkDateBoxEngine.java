package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkDateBox;

public class VkDateBoxEngine extends VkAbstractWidgetEngine<VkDateBox> {
	private static final String SET_FORMAT = "Set Date Format";
	@Override
	public VkDateBox getWidget() {
		VkDateBox widget = new VkDateBox();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDateBox widget = (VkDateBox)invokingWidget;
		if(attributeName.equals(SET_FORMAT))
		{
			List<String> suggestions = new ArrayList<String>();
			suggestions.add(DateTimeFormat.getFullDateFormat().getPattern() + "(" + DateTimeFormat.getFullDateFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getFullDateTimeFormat().getPattern() + "(" + DateTimeFormat.getFullDateTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getFullTimeFormat().getPattern() + "(" + DateTimeFormat.getFullTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getLongDateFormat().getPattern() + "(" + DateTimeFormat.getLongDateFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getLongDateTimeFormat().getPattern() + "(" + DateTimeFormat.getLongDateTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getLongTimeFormat().getPattern() + "(" + DateTimeFormat.getLongTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getMediumDateFormat().getPattern() + "(" + DateTimeFormat.getMediumDateFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getMediumDateTimeFormat().getPattern() + "(" + DateTimeFormat.getMediumDateTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getMediumTimeFormat().getPattern() + "(" + DateTimeFormat.getMediumTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getShortDateFormat().getPattern() + "(" + DateTimeFormat.getShortDateFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getShortDateTimeFormat().getPattern() + "(" + DateTimeFormat.getShortDateTimeFormat().format(new Date()) +")");
			suggestions.add(DateTimeFormat.getShortTimeFormat().getPattern() + "(" + DateTimeFormat.getShortTimeFormat().format(new Date()) +")");
			
			AutoCompleterTextBox textBox = new AutoCompleterTextBox(suggestions);
			textBox.setSuggestionWidth(500);
			VkDesignerUtil.getEngine().showAddAutoCompleteTextDialog("Please select the date format", textBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String pattern) {
						widget.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(pattern)));
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
		list.add(SET_FORMAT);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
}
