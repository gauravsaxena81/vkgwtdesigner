package com.vk.gwt.designer.client.widgets;

import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.generator.client.Export;

public class VkHidden extends SimplePanel implements HasVkName, HasVkValue<String>{
	public static final String NAME = "Hidden";
	private Hidden hidden = new Hidden();

	@Override
	@Export
	public String getName() {
		return hidden.getName();
	}

	@Override
	@Export
	public void setName(String name) {
		hidden.setName(name);
	}
	@Override
	@Export
	public String getValue() {
		return hidden.getValue();
	}
	@Override
	@Export
	public void setValue(String value) {
		hidden.setValue(value);
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
}
