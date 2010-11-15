package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkHorizontalSplitPanel extends SimplePanel implements IPanel, HasVkWidgets, HasVkImageUrl{
	public static final String NAME = "Horizontal Split Panel";
	private HorizontalSplitPanel horizontalSplitPanel;
	public VkHorizontalSplitPanel()
	{
		horizontalSplitPanel = new HorizontalSplitPanel();
		setWidget(horizontalSplitPanel);
	}
	@Override
	public void add(Widget widget)
	{
		if(horizontalSplitPanel.getStartOfLineWidget() == null)
		{
			horizontalSplitPanel.setStartOfLineWidget(widget);
			Window.alert("Widget added as start line widget of Horizontal Split Panel");
		}
		else if(horizontalSplitPanel.getEndOfLineWidget() == null)
		{
			horizontalSplitPanel.setEndOfLineWidget(widget);
			Window.alert("Widget added as end line widget of Horizontal Split Panel");
		}
		else
			Window.alert("Horizontal Split Panel can add only two widgets");
	}
	@Override
	public void setImageUrl(String url) {
		DOM.setStyleAttribute((Element) horizontalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement()
				.getElementsByTagName("IMG").getItem(0)	, "backgroundImage", "url(\"" + url + "\")");
	}
	@Override
	public String getImageUrl() {
		return DOM.getStyleAttribute((Element) horizontalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement()
				.getElementsByTagName("IMG").getItem(0), "backgroundImage").replace("url(\"","").replace("\")","");
	}
	/**************************Export attribute Methods********************************/
	@Export
	public void setSplitPosition(String pos) {
		horizontalSplitPanel.setSplitPosition(pos);
	}
}
