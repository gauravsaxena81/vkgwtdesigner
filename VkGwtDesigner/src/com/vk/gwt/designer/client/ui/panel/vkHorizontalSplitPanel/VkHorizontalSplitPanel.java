/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.gwt.designer.client.ui.panel.vkHorizontalSplitPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkHorizontalSplitPanel extends Composite implements IVkPanel, HasVkWidgets, HasVkImageUrl{
	public static final String NAME = "Horizontal Split Panel";
	private HorizontalSplitPanel horizontalSplitPanel;
	private IVkWidget vkParent;
	//TODO if mousedown event of VkDesignerUtil is not being used then there is no need for this complex structure of widgets
	public VkHorizontalSplitPanel()
	{
		/*SimplePanel spParent = new SimplePanel();
		SimplePanel sp = new SimplePanel(){
			@Override
			  public void onBrowserEvent(Event event) {
			    switch (DOM.eventGetType(event)) {
			    	case Event.ONMOUSEDOWN:
			    		if(event.getButton() == 0 || event.getButton() == 1)
			    			event.stopPropagation();
			    	break;
			    }
			    super.onBrowserEvent(event);
			}
		};
		spParent.setWidget(sp);
		sp.sinkEvents(Event.ONMOUSEDOWN);*/
		horizontalSplitPanel = new HorizontalSplitPanel();
		initWidget(horizontalSplitPanel);
		
		//sp.add(horizontalSplitPanel);
	}
	@Override
	public void add(Widget widget)
	{
		if(horizontalSplitPanel.getStartOfLineWidget() == null) {
			horizontalSplitPanel.setStartOfLineWidget(widget);
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as start line widget of Horizontal Split Panel");
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
		} else if(horizontalSplitPanel.getEndOfLineWidget() == null) {
			horizontalSplitPanel.setEndOfLineWidget(widget);
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as end line widget of Horizontal Split Panel");
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
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
		/*if(list.size() == 0)
			return super.iterator();//when widget is initialized, panel housekeeping needs the iterator of VerticalPanel
		else*/
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
	public void clone(Widget targetWidget) {
		((VkHorizontalSplitPanel)targetWidget).horizontalSplitPanel.setSplitPosition(splitterPosition() + "px");
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
	}
	/**************************Export attribute Methods********************************/
	@Export
	public int splitterPosition(){
		return horizontalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getOffsetWidth();
	}
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
	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
