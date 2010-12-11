package com.vk.gwt.designer.client.Panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

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
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as start line widget of Horizontal Split Panel");
		}
		else if(horizontalSplitPanel.getEndOfLineWidget() == null)
		{
			horizontalSplitPanel.setEndOfLineWidget(widget);
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as end line widget of Horizontal Split Panel");
		}
		else
			Window.alert("Horizontal Split Panel can add only two widgets");
	}
	@Override
	public Iterator<Widget> iterator() 
	{
		List<Widget> list = new ArrayList<Widget>();
		if(horizontalSplitPanel.getStartOfLineWidget() != null)
			list.add(horizontalSplitPanel.getStartOfLineWidget());
		if(horizontalSplitPanel.getEndOfLineWidget() != null)
			list.add(horizontalSplitPanel.getEndOfLineWidget());
		if(list.size() == 0)
			return super.iterator();//when widget is initialized, panel housekeeping needs the iterator of VerticalPanel
		else
			return list.iterator();//used by the serializer
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
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {}
	/**************************Export attribute Methods********************************/
	@Export
	public void setSplitPosition(String pos) {
		horizontalSplitPanel.setSplitPosition(pos);
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
