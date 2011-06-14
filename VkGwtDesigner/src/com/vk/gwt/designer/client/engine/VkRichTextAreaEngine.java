package com.vk.gwt.designer.client.engine;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkRichTextArea;

public class VkRichTextAreaEngine extends VkAbstractWidgetEngine<VkRichTextArea> {
	@Override
	public VkRichTextArea getWidget() {
		VkRichTextArea widget = new VkRichTextArea();
		init(widget);
		return widget;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent)
	{
		super.buildWidget(jsonObj, parent);
		VkRichTextArea richTextArea = (VkRichTextArea)parent;
		richTextArea.setHeight(richTextArea.getOffsetHeight() + "px");
	}
}
