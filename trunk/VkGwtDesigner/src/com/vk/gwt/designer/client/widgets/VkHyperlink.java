package com.vk.gwt.designer.client.widgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Hyperlink;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.generator.client.Export;

public class VkHyperlink extends Hyperlink implements HasVkHtml, HasVkText{
	public static String NAME = "Hyperlink";
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getTargetHistoryToken() {
	    return super.getTargetHistoryToken();
	}
	@Override
	@Export
	public void setTargetHistoryToken(String targetHistoryToken)
	{
		if(targetHistoryToken != null)
			super.setTargetHistoryToken(targetHistoryToken);
		else
			Window.alert("Cannot set null history token");
	}
	@Override
	@Export
	public void setText(String text)
	{
		super.setText(text);
	}
	@Override
	@Export
	public String getText()
	{
		return super.getText();
	}
	@Override
	@Export
	public void setHTML(String html)
	{
		super.setHTML(html);
	}
	@Override
	@Export
	public String getHTML()
	{
		return super.getHTML();
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
