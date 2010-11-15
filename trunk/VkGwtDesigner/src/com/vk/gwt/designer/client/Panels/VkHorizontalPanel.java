package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkHorizontalPanel extends HorizontalPanel implements HasVkWidgets, IPanel, HasVkVerticalAlignment, HasVkHorizontalAlignment {
	public static final String NAME = "Horizontal Panel";
	@Override
	protected void add(Widget child, Element container) 
	{
		//So that TD resizes with the widget inside
		if(getWidgetCount() > 0)
			DOM.setElementAttribute((Element) getWidget(getWidgetCount() - 1).getElement().getParentElement(), "width", "1px");
		super.add(child, container);
		DOM.setElementAttribute(container, "width", "*");
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
	@Export
	public String getHorizontalAlignmentString()
	{
		return getHorizontalAlignment().getTextAlignString();
	}
	@Export
	public void setHorizontalAlignment(String horizontalAlignment) {
		if(horizontalAlignment.equals(HorizontalPanel.ALIGN_CENTER.getTextAlignString()))
			setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		else if(horizontalAlignment.equals(HorizontalPanel.ALIGN_LEFT.getTextAlignString()))
			setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		else if(horizontalAlignment.equals(HorizontalPanel.ALIGN_RIGHT.getTextAlignString()))
			setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		else 
			Window.alert("direction can only take one of the following values: " + HorizontalPanel.ALIGN_CENTER.getTextAlignString() + "," 
				+ HorizontalPanel.ALIGN_LEFT.getTextAlignString() + "," +	HorizontalPanel.ALIGN_RIGHT.getTextAlignString());
	}
	@Export
	public String getVerticalAlignmentString()
	{
		return getVerticalAlignment().getVerticalAlignString();
	}
	@Override
	@Export
	public void setVerticalAlignment(String verticalAlignment) {
		if(verticalAlignment.equals(HorizontalPanel.ALIGN_MIDDLE.getVerticalAlignString()))
			setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		else if(verticalAlignment.equals(HorizontalPanel.ALIGN_TOP.getVerticalAlignString()))
			setVerticalAlignment(HorizontalPanel.ALIGN_TOP);
		else if(verticalAlignment.equals(HorizontalPanel.ALIGN_BOTTOM.getVerticalAlignString()))
			setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
		else 
			Window.alert("direction can only take one of the following values: " + HorizontalPanel.ALIGN_MIDDLE.getVerticalAlignString() + "," 
				+ HorizontalPanel.ALIGN_TOP.getVerticalAlignString() + "," +	HorizontalPanel.ALIGN_BOTTOM.getVerticalAlignString());
	}
}