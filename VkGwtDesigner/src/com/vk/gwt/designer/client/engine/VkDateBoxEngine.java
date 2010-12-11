package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.CustomSuggestionAutoCompleterTextBox;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.ICustomSuggestion;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
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
	private class CustomSuggestionImpl implements ICustomSuggestion {
        private String filterKey;
        private String  html;
        public CustomSuggestionImpl(String filterKey, String html) {
                super();
                this.filterKey = filterKey;
                this.html = html;
        }

        @Override
        public String asHTML(int position, String currentTextValue) {
                return html;
        }

        @Override
        public String getFilterKey() {
                return filterKey;
        }
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDateBox widget = (VkDateBox)invokingWidget;
		if(attributeName.equals(SET_FORMAT))
		{
			List<ICustomSuggestion> suggestions = new ArrayList<ICustomSuggestion>();
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFullDateFormat().getPattern()
					, DateTimeFormat.getFullDateFormat().getPattern() + "(" + DateTimeFormat.getFullDateFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFullDateTimeFormat().getPattern(), 
					DateTimeFormat.getFullDateTimeFormat().getPattern() + "(" + DateTimeFormat.getFullDateTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFullTimeFormat().getPattern(), 
					DateTimeFormat.getFullTimeFormat().getPattern() + "(" + DateTimeFormat.getFullTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getLongDateFormat().getPattern(), 
					DateTimeFormat.getLongDateFormat().getPattern() + "(" + DateTimeFormat.getLongDateFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getLongDateTimeFormat().getPattern(), 
					DateTimeFormat.getLongDateTimeFormat().getPattern() + "(" + DateTimeFormat.getLongDateTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getLongTimeFormat().getPattern(), 
					DateTimeFormat.getLongTimeFormat().getPattern() + "(" + DateTimeFormat.getLongTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getMediumDateFormat().getPattern(), 
					DateTimeFormat.getMediumDateFormat().getPattern() + "(" + DateTimeFormat.getMediumDateFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getMediumDateTimeFormat().getPattern(), 
					DateTimeFormat.getMediumDateTimeFormat().getPattern() + "(" + DateTimeFormat.getMediumDateTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getMediumTimeFormat().getPattern(), 
					DateTimeFormat.getMediumTimeFormat().getPattern() + "(" + DateTimeFormat.getMediumTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getShortDateFormat().getPattern(), 
					DateTimeFormat.getShortDateFormat().getPattern() + "(" + DateTimeFormat.getShortDateFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getShortDateTimeFormat().getPattern(), 
					DateTimeFormat.getShortDateTimeFormat().getPattern() + "(" + DateTimeFormat.getShortDateTimeFormat().format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getShortTimeFormat().getPattern(), 
					DateTimeFormat.getShortTimeFormat().getPattern() + "(" + DateTimeFormat.getShortTimeFormat().format(new Date()) +")"));
			
			CustomSuggestionAutoCompleterTextBox textBox = new CustomSuggestionAutoCompleterTextBox(suggestions);
			//AutoCompleterTextBox textBox = new AutoCompleterTextBox(suggestions);
			textBox.setText(widget.getPattern());
			textBox.setSuggestionWidth(500);
			VkDesignerUtil.getEngine().showAddAutoCompleteTextDialog("Please select the date format", textBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String pattern) {
						widget.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(pattern)));
						widget.setPattern(pattern);
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
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",dateFormat:'").append(((VkDateBox)widget).getPattern()).append("'");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkDateBox dateBox = (VkDateBox)parent;
		dateBox.setPattern(jsonObj.get("dateFormat").isString().stringValue());
		dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(dateBox.getPattern())));
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		((VkDateBox)widgetTarget).setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(((VkDateBox)widgetSource).getPattern())));
	}
}
