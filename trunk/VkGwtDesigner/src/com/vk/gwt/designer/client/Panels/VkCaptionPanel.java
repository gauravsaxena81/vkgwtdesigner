package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkCaptionPanel extends CaptionPanel implements IPanel,HasVkWidgets, HasVkText, HasVkHtml{
	public static final String NAME = "Caption Panel";
	@Override
	public void addText(String text) {
		setCaptionText(text);
	}
	@Override
	public void addHtml(String html) {
		setCaptionHTML(html);
	}
	@Override
	public void add(Widget w)
	{
		if(getContentWidget() != null)
			Window.alert("Caption Panel can add only one Widget");
		else
			super.add(w);
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void setCaptionText(String text)
	{
		super.setCaptionText(text);
	}
	@Override
	@Export
	public String getCaptionText()
	{
		return super.getCaptionText();
	}
	@Override
	@Export
	public void setCaptionHTML(String html)
	{
		super.setCaptionHTML(html);
	}
	@Override
	@Export
	public String getCaptionHTML()
	{
		return super.getCaptionHTML();
	}
}
