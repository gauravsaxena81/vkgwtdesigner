package com.vk.gwt.designer.client.widgets;

import com.google.gwt.user.client.ui.Frame;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.generator.client.Export;

public class VkFrame extends Frame implements HasVkUrl{

	public static final String NAME = "Frame";

	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getUrl() {
		return super.getUrl();
	}
	@Override
	@Export
	public void setUrl(String url) {
		super.setUrl(url);
	}
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
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
