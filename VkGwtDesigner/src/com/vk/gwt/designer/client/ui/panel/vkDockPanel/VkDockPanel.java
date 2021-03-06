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
package com.vk.gwt.designer.client.ui.panel.vkDockPanel;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockPanel;
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
import com.vk.gwt.designer.client.designer.UndoHelper;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDockPanel extends DockPanel implements IVkPanel, HasVkHorizontalAlignment, HasVkVerticalAlignment,HasVkWidgets {
	public static final String NAME = "Dock Panel";
	private IVkWidget vkParent;
	@Override
	public void add(Widget w, DockLayoutConstant direction) {
		super.add(w, direction);
		//So that TD resizes with the widget inside
		for(int i = 0; i < getWidgetCount(); i++) {
			Element td = (Element) getWidget(i).getElement().getParentElement();
			if(DOM.getNextSibling(td) != null)
				DOM.setElementAttribute(td, "width", "1px");
			else
				DOM.setElementAttribute(td, "width", "*");
			Element tr = (Element) td.getParentElement();
			if(DOM.getNextSibling(tr) != null)
				DOM.setElementAttribute(td, "height", "1px");
			else
				DOM.setElementAttribute(td, "height", "*");
		}
		if(w instanceof IVkWidget)
			((IVkWidget)w).setVkParent(this);
	}
	@Override
	public void setHorizontalAlignment(String horizontalAlignment) {
		if(VkStateHelper.getInstance().isDesignerMode()) {
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Left", "left");
			listBox.addItem("Center", "center");
			listBox.addItem("Right", "right");
			listBox.setWidth("100px");
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(final int widgetIndex, final String align) {
					final String prior = DOM.getElementProperty((Element) getWidget(widgetIndex).getElement().getParentElement(), "align");
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							setCellHorizontalAlignment(widgetIndex, align);
						}}, new Command() {
						@Override
						public void execute() {
							if(prior.isEmpty())
								setCellHorizontalAlignment(widgetIndex, "left");
							else
								setCellHorizontalAlignment(widgetIndex, prior);
						}
					});
				}
			});
		}
	}
	@Override
	public void setVerticalAlignment(String verticalAlignment) {
		if(VkStateHelper.getInstance().isDesignerMode()) {
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Top", "top");
			listBox.addItem("Middle", "middle");
			listBox.addItem("Bottom", "bottom");
			listBox.setWidth("100px");
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(final int widgetIndex, final String align) {
					final String prior = DOM.getStyleAttribute((Element) getWidget(widgetIndex).getElement().getParentElement(), "verticalAlign");
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							setCellVerticalAlignment(widgetIndex, align);
						}}, new Command() {
						@Override
						public void execute() {
							if(prior.isEmpty())
								setCellVerticalAlignment(widgetIndex, "top");
							else
								setCellVerticalAlignment(widgetIndex, prior);
						}
					});
				}
			});
		}
	}
	@Override
	public void add(Widget widget) {
		if(VkStateHelper.getInstance().isDesignerMode()) {
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Center", "1");
			listBox.addItem("Line Start", "2");
			listBox.addItem("Line End", "3");
			listBox.addItem("East", "4");
			listBox.addItem("South", "5");
			listBox.addItem("North", "6");
			listBox.addItem("West", "7");
			listBox.setWidth("100px");
			showProvideLayoutDialog(listBox, widget);
		}
	}
	private void showProvideLayoutDialog(final ListBox listBox, final Widget widget) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Please Select Direction below:");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				listBox.setFocus(true);
			}
		}.schedule(100);
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String option = listBox.getValue(listBox.getSelectedIndex());
				if(option.equals("1")) {
					try{
						VkDockPanel.this.add(widget, DockPanel.CENTER);
					}catch(IllegalArgumentException e) {
						if(VkStateHelper.getInstance().isDesignerMode())
							Window.alert("Center can contain only one widget");
					}
				}
				else if(option.equals("2"))
					VkDockPanel.this.add(widget, DockPanel.LINE_START);
				else if(option.equals("3"))
					VkDockPanel.this.add(widget, DockPanel.LINE_END);
				else if(option.equals("4"))
					VkDockPanel.this.add(widget, DockPanel.EAST);
				else if(option.equals("5"))
					VkDockPanel.this.add(widget, DockPanel.SOUTH);
				else if(option.equals("6"))
					VkDockPanel.this.add(widget, DockPanel.NORTH);
				else if(option.equals("7"))
					VkDockPanel.this.add(widget, DockPanel.WEST);
				if(widget instanceof IVkWidget)
					((IVkWidget)widget).setVkParent(VkDockPanel.this);
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
	private String getDirectionString(DockLayoutConstant widgetDirection) {
		if(widgetDirection.equals(DockPanel.CENTER))
			return "Center";
		else if(widgetDirection.equals(DockPanel.EAST))
			return "East";
		else if(widgetDirection.equals(DockPanel.LINE_END))
			return "Line-End";
		else if(widgetDirection.equals(DockPanel.LINE_START))
			return "Line-Start";
		else if(widgetDirection.equals(DockPanel.NORTH))
			return "North";
		else if(widgetDirection.equals(DockPanel.SOUTH))
			return "South";
		else if(widgetDirection.equals(DockPanel.WEST))
			return "West";
		else
			return "";
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
		int index = 0;
		for (Iterator<Widget> iterator = iterator(); iterator.hasNext();) {
			Widget next = iterator.next();
			widgetIndexLb.addItem(((IVkWidget)next).getWidgetName() + "-" + getDirectionString(getWidgetDirection(next)), Integer.toString(index++));
		}
		
		widgetIndexLb.setWidth("300px");
		dialog.add(widgetIndexLb);
		dialog.add(new Label("Please choose Alignment"));
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("OK");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iAlignment.doAlignment(Integer.parseInt(widgetIndexLb.getValue(widgetIndexLb.getSelectedIndex())), listBox.getValue(listBox.getSelectedIndex()));
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
	public void setCellVerticalAlignment(int widgetIndex, String verticalAlignment) {
		if(verticalAlignment.equals(DockPanel.ALIGN_BOTTOM.getVerticalAlignString()))
			setCellVerticalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_BOTTOM);
		else if(verticalAlignment.equals(DockPanel.ALIGN_MIDDLE.getVerticalAlignString()))
			setCellVerticalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_MIDDLE);
		else if(verticalAlignment.equals(DockPanel.ALIGN_TOP.getVerticalAlignString()))
			setCellVerticalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_TOP);
		else 
			Window.alert("direction can only take one of the following values: " + DockPanel.ALIGN_MIDDLE.getVerticalAlignString() + "," 
				+ DockPanel.ALIGN_BOTTOM.getVerticalAlignString() + "," +	DockPanel.ALIGN_TOP.getVerticalAlignString());
	}
	public void setCellHorizontalAlignment(int widgetIndex, String horizontalAlignment) {
		if(horizontalAlignment.equals(DockPanel.ALIGN_CENTER.getTextAlignString()))
			setCellHorizontalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_CENTER);
		else if(horizontalAlignment.equals(DockPanel.ALIGN_LEFT.getTextAlignString()))
			setCellHorizontalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_LEFT);
		else if(horizontalAlignment.equals(DockPanel.ALIGN_RIGHT.getTextAlignString()))
			setCellHorizontalAlignment(getWidget(widgetIndex), DockPanel.ALIGN_RIGHT);
		else 
			Window.alert("direction can only take one of the following values: " + DockPanel.ALIGN_CENTER.getTextAlignString() + "," 
				+ DockPanel.ALIGN_LEFT.getTextAlignString() + "," +	DockPanel.ALIGN_RIGHT.getTextAlignString());
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public String getHorizontalAlignmentString() {
		if(getHorizontalAlignment().getTextAlignString() != null)
			return getHorizontalAlignment().getTextAlignString();
		else
			return "left";
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
	public String getVerticalAlignmentString() {
		return getVerticalAlignment().getVerticalAlignString();
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
