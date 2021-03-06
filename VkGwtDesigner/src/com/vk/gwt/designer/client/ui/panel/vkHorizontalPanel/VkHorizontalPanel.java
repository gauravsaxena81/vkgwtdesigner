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
package com.vk.gwt.designer.client.ui.panel.vkHorizontalPanel;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkHorizontalPanel extends HorizontalPanel implements HasVkWidgets, IVkPanel, HasVkVerticalAlignment, HasVkHorizontalAlignment {
	public static final String NAME = "Horizontal Panel";
	private IVkWidget vkParent;
	@Override
	protected void add(Widget widget, Element container) 
	{
		//So that TD resizes with the widget inside
		if(getWidgetCount() > 0)
			DOM.setElementAttribute((Element) getWidget(getWidgetCount() - 1).getElement().getParentElement(), "width", "1px");
		super.add(widget, container);
		DOM.setElementAttribute(container, "width", "*");
		if(widget instanceof IVkWidget)
			((IVkWidget)widget).setVkParent(this);
	}
	@Override
	public void insert(Widget w, int beforeIndex) {
		super.insert(w, beforeIndex);
		DOM.setElementAttribute((Element)getWidget(beforeIndex).getElement().getParentElement(), "width", "1px");
	}
	public void setHorizontalAlignment(String horizontalAlignment) {
		if(VkStateHelper.getInstance().isDesignerMode())
		{
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Left", "left");
			listBox.addItem("Center", "center");
			listBox.addItem("Right", "right");
			listBox.setWidth("100px");
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(int widgetIndex, String align) {
					DOM.setElementAttribute((Element) getWidget(widgetIndex).getElement().getParentElement(), "align", align);
				}
			});
		}
	}
	@Override
	public void setVerticalAlignment(String verticalAlignment) {
		if(VkStateHelper.getInstance().isDesignerMode())
		{
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Top", "top");
			listBox.addItem("Middle", "middle");
			listBox.addItem("Bottom", "bottom");
			listBox.setWidth("100px");
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(int widgetIndex, String align) {
					DOM.setStyleAttribute((Element) getWidget(widgetIndex).getElement().getParentElement(), "verticalAlign", align);
				}
			});
		}
	}
	private void showSetCellAlignmentDialog(final ListBox listBox, final IAlignment iAlignment) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Cell Alignment Dialog");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		dialog.add(new Label("Please choose the Widget :"));
		final ListBox widgetIndexLb = new ListBox();
		for (Iterator<Widget> iterator = iterator(); iterator.hasNext();) {
			Widget next = iterator.next();
			widgetIndexLb.addItem(((IVkWidget)next).getWidgetName() + " - at index - " + getWidgetIndex(next));
		}
		
		widgetIndexLb.setWidth("300px");
		dialog.add(widgetIndexLb);
		widgetIndexLb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				iAlignment.doAlignment(Integer.parseInt(widgetIndexLb.getValue(widgetIndexLb.getSelectedIndex())), listBox.getValue(listBox.getSelectedIndex()));
			}
		});
		dialog.add(new Label("Please choose Alignment"));
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("OK");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iAlignment.doAlignment(widgetIndexLb.getSelectedIndex(), listBox.getValue(listBox.getSelectedIndex()));
				origDialog.hide();
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
			}
		});
		origDialog.center();
	}
	private interface IAlignment{
		public void doAlignment(int widgetIndex, String align);
	}
	public String getHorizontalAlignmentString()
	{
		if(getHorizontalAlignment().getTextAlignString() != null)
			return getHorizontalAlignment().getTextAlignString();
		else
			return "left";
	}
	
	public String getVerticalAlignmentString()
	{
		return getVerticalAlignment().getVerticalAlignString();
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {}
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
