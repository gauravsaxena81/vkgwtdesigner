package com.vk.gwt.designer.client.Panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkVerticalSplitPanel extends SimplePanel implements IPanel, HasVkWidgets, HasVkImageUrl {
	public static final String NAME = "Vertical Split Panel";
	private VerticalSplitPanel verticalSplitPanel;
	public VkVerticalSplitPanel()
	{
		verticalSplitPanel = new VerticalSplitPanel();
		setWidget(verticalSplitPanel);
	}
	@Override
	public void setHeight(String height)
	{
		super.setHeight(height);
		verticalSplitPanel.setHeight(height);
	}
	@Override
	public void add(Widget widget)
	{
		if(verticalSplitPanel.getTopWidget() == null)
		{
			verticalSplitPanel.setTopWidget(widget);
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as top widget of Vertical Split Panel");
		}
		else if(verticalSplitPanel.getBottomWidget() == null)
		{
			verticalSplitPanel.setBottomWidget(widget);
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as bottom widget of Vertical Split Panel");
		}
		else
			Window.alert("Vertical Split Panel can add only two widgets");
	}
	@Override
	public Iterator<Widget> iterator() 
	{
		List<Widget> list = new ArrayList<Widget>();
		if(verticalSplitPanel.getTopWidget() != null)
			list.add(verticalSplitPanel.getTopWidget());
		if(verticalSplitPanel.getBottomWidget() != null)
			list.add(verticalSplitPanel.getBottomWidget());
		if(list.size() == 0)
			return super.iterator();//when widget is initialized, panel housekeeping needs the iterator of VerticalPanel
		else
			return list.iterator();//used by the serializer
	}
	@Override
	public void setImageUrl(String url) {
		DOM.setStyleAttribute((Element) verticalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement()
				.getElementsByTagName("IMG").getItem(0)	, "backgroundImage", "url(\"" + url + "\")");
	}
	@Override
	public String getImageUrl() {
		return DOM.getStyleAttribute((Element) verticalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getNextSiblingElement()
				.getElementsByTagName("IMG").getItem(0), "backgroundImage").replace("url(\"","").replace("\")","");
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	/**************************Export attribute Methods********************************/
	@Export
	public void setSplitPosition(String pos) {
		verticalSplitPanel.setSplitPosition(pos);
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