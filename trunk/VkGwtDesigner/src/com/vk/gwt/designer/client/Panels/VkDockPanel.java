package com.vk.gwt.designer.client.Panels;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkDockPanel extends DockPanel implements IPanel, HasVkHorizontalAlignment, HasVkVerticalAlignment,HasVkWidgets {
	public static final String NAME = "Dock Panel";
	@Override
	public void add(Widget widget, DockLayoutConstant direction){
		super.add(widget, direction);
		//So that TD resizes with the widget inside
		for(int i = 0; i < getWidgetCount(); i++)
		{
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
	}
	@Override
	public void setHorizontalAlignment(String horizontalAlignment) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Left", "left");
		listBox.addItem("Center", "center");
		listBox.addItem("Right", "right");
		listBox.setWidth("100px");
		showSetCellAlignmentDialog(listBox, new IAlignment() {
			@Override
			public void doAlignment(int widgetIndex, String align) {
				setCellHorizontalAlignment(widgetIndex, align);
			}
		});
	}
	@Override
	public void setVerticalAlignment(String verticalAlignment) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Top", "top");
		listBox.addItem("Middle", "middle");
		listBox.addItem("Bottom", "bottom");
		listBox.setWidth("100px");
		showSetCellAlignmentDialog(listBox, new IAlignment() {
			@Override
			public void doAlignment(int widgetIndex, String align) {
				setCellVerticalAlignment(widgetIndex, align);
			}
		});
	}
	@Override
	public void add(Widget widget)
	{
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
	private void showProvideLayoutDialog(final ListBox listBox, final Widget widget) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				listBox.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Please Select Direction below:"));
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String option = listBox.getValue(listBox.getSelectedIndex());
				if(option.equals("1"))
				{
					try{
						VkDockPanel.this.add(widget, DockPanel.CENTER);
					}catch(IllegalArgumentException e)
					{
						Window.alert("Adding more than one Center widgets is not allowed");
					}
				}
				if(option.equals("2"))
					VkDockPanel.this.add(widget, DockPanel.LINE_START);
				if(option.equals("3"))
					VkDockPanel.this.add(widget, DockPanel.LINE_END);
				if(option.equals("4"))
					VkDockPanel.this.add(widget, DockPanel.EAST);
				if(option.equals("5"))
					VkDockPanel.this.add(widget, DockPanel.SOUTH);
				if(option.equals("6"))
					VkDockPanel.this.add(widget, DockPanel.NORTH);
				if(option.equals("7"))
					VkDockPanel.this.add(widget, DockPanel.WEST);
				dialog.removeFromParent();
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.removeFromParent();
			}
		});
	}
	private interface IAlignment{
		public void doAlignment(int widgetIndex, String align);
	}
	public void showSetCellAlignmentDialog(final ListBox listBox, final IAlignment iAlignment) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		final TextBox widgetIndexTb = new TextBox();
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				widgetIndexTb.setFocus(true);
			}
		};
		t.schedule(100);
		Label heading = new Label("Cell Alignment Dialog");
		heading.setHorizontalAlignment(Label.ALIGN_CENTER);
		heading.setStyleName("VkDesigner-styledialog-heading");
		dialog.add(heading);
		heading.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkDesignerUtil.makeMovable(dialog);
			}
		});
		dialog.add(new Label("Please add Widget number below:"));
		dialog.add(widgetIndexTb);
		widgetIndexTb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				iAlignment.doAlignment(Integer.parseInt(widgetIndexTb.getText()), listBox.getValue(listBox.getSelectedIndex()));
			}
		});
		dialog.add(new Label("Please choose Alignment"));
		dialog.add(listBox);
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				iAlignment.doAlignment(Integer.parseInt(widgetIndexTb.getText()), listBox.getValue(listBox.getSelectedIndex()));
			}
		});
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("OK");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.removeFromParent();
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.removeFromParent();
			}
		});
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
		return getHorizontalAlignment().getTextAlignString();
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
}
