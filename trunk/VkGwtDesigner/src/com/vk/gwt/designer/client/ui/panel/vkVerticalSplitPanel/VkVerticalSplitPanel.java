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
package com.vk.gwt.designer.client.ui.panel.vkVerticalSplitPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkVerticalSplitPanel extends Composite implements IVkPanel, HasVkWidgets, HasVkImageUrl {
	public static final String NAME = "Vertical Split Panel";
	private VerticalSplitPanel verticalSplitPanel;
	private IVkWidget vkParent;
	public VkVerticalSplitPanel()
	{
		verticalSplitPanel = new VerticalSplitPanel();
		initWidget(verticalSplitPanel);
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
		if(verticalSplitPanel.getTopWidget() == null) {
			verticalSplitPanel.setTopWidget(widget);
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as top widget of Vertical Split Panel");
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
		}
		else if(verticalSplitPanel.getBottomWidget() == null) {
			verticalSplitPanel.setBottomWidget(widget);
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as bottom widget of Vertical Split Panel");
			if(widget instanceof IVkWidget)
				((IVkWidget)widget).setVkParent(this);
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
	@Override
	public void clone(Widget targetWidget) {
		((VkVerticalSplitPanel)targetWidget).verticalSplitPanel.setSplitPosition(splitterPosition() + "px");
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
		return verticalSplitPanel.getElement().getFirstChildElement().getFirstChildElement().getOffsetHeight();
	}
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
