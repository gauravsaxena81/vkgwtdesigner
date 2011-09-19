package com.vk.gwt.designer.client.ui.widget.vkDateBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.CustomSuggestionAutoCompleterTextBox;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.ICustomSuggestion;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IEventRegister;

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
        public String getDisplayString(int position, String currentTextValue) {
                return html;
        }

        @Override
        public String getValueString() {
                return filterKey;
        }
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDateBox widget = (VkDateBox)invokingWidget;
		if(attributeName.equals(SET_FORMAT))
		{
			List<ICustomSuggestion> suggestions = new ArrayList<ICustomSuggestion>();
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).getPattern()
				, DateTimeFormat.getFormat(PredefinedFormat.DATE_FULL).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_FULL).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.TIME_FULL).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.TIME_FULL).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.TIME_FULL).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_LONG).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_LONG).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_LONG).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.TIME_LONG).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.TIME_LONG).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.TIME_LONG).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_MEDIUM).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_MEDIUM).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.TIME_MEDIUM).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.DATE_TIME_SHORT).format(new Date()) +")"));
			suggestions.add(new CustomSuggestionImpl(DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT).getPattern(), 
				DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT).getPattern() 
				+ "(" + DateTimeFormat.getFormat(PredefinedFormat.TIME_SHORT).format(new Date()) +")"));
			
			CustomSuggestionAutoCompleterTextBox textBox = new CustomSuggestionAutoCompleterTextBox(suggestions);
			//AutoCompleterTextBox textBox = new AutoCompleterTextBox(suggestions);
			textBox.setText(widget.getPattern());
			textBox.setSuggestionWidth(500);
			VkDesignerUtil.showAddAutoCompleteTextDialog("Please select the date format", textBox
				, new IEventRegister() {
					@Override
					public void registerEvent(String pattern) {
						String prevDate = widget.getText();
						widget.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(pattern)));
						widget.setPattern(pattern);
						widget.setValue(prevDate);
					}
				});
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		list.add(3, SET_FORMAT);
		return list;
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",dateFormat:'").append(((VkDateBox)widget).getPattern()).append("'");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkDateBox dateBox = (VkDateBox)parent;
		dateBox.setPattern(jsonObj.get("dateFormat").isString().stringValue());
		String pattern = dateBox.getPattern();
		if(!pattern.isEmpty())
			dateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(pattern)));
		addAttributes(jsonObj, parent);
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		((VkDateBox)widgetTarget).setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat(((VkDateBox)widgetSource).getPattern())));
	}
}
