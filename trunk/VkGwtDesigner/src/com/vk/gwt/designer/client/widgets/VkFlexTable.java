package com.vk.gwt.designer.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkFlexTable extends FlexTable implements HasVkClickHandler{
	public static final String NAME = "Flex Table";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs;
	
	public VkFlexTable()
	{
		showAddTextAttributeDialog();
		setBorderWidth(1);
	}
	private void showAddTextAttributeDialog() {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		dialog.add(new Label("Give number of rows and columns"));
		final TextBox rowsTextBox = new TextBox();
		dialog.add(rowsTextBox);
		rowsTextBox.setWidth("50px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				rowsTextBox.setFocus(true);
			}
		};
		t.schedule(100);
		
		final TextBox columnsTextBox = new TextBox();
		columnsTextBox.setWidth("50px");
		dialog.add(columnsTextBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try{
					int rowCount = Integer.parseInt(rowsTextBox.getText());
					int columnCount = Integer.parseInt(columnsTextBox.getText());
					for(int i = 0; i < rowCount; i++)
					{
						for(int j = 0; j < columnCount; j++)
						{
							Label l = new Label("&nbsp;");
							VkFlexTable.this.setWidget(i, j, l);
							VkFlexTable.this.getFlexCellFormatter().setWidth(i, j, "5px");
							VkFlexTable.this.getFlexCellFormatter().setHeight(i, j, "5px");
						}
					}
					dialog.removeFromParent();
				}catch(NumberFormatException e)
				{
					Window.alert("row and column number cannot be non-numeric");
				}
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
	@Override
	public void addClickHandler(String js) {
		if(clickHandlerRegistration != null)
			clickHandlerRegistration.removeHandler();
		clickJs = js;
		clickHandlerRegistration = addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VkDesignerUtil.executeEvent(clickJs, event);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
		else
			return "";
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void addCell(int row)
	{
		super.addCell(row);
	}
	@Override
	@Export
	public int getCellCount(int row){
		return super.getCellCount(row);
	}
	@Override
	@Export
	 public int getRowCount() {
		return super.getRowCount();
	}
	
	@Override
	@Export
	public void insertCell(int beforeRow, int beforeColumn)
	{
		super.insertCell(beforeRow, beforeColumn);
	}
	@Override
	@Export
	public int insertRow(int beforeRow) {
	    return super.insertRow(beforeRow);
	}
	public void removeAllRows() {
		super.removeAllRows();
	}
	@Override
	@Export
	public void removeCell(int row, int col) {
		 super.removeCell(row, col);
	}
	@Override
	@Export
	public void removeCells(int row, int column, int num)
	{
		super.removeCells(row, column, num);
	}
	@Export
	public int getColSpan(int row, int column)
	{
		return super.getFlexCellFormatter().getColSpan(row, column);
	}
	@Export
	public void setColSpan(int row, int column, int colspan)
	{
		super.getFlexCellFormatter().setColSpan(row, column, colspan);
	}
	@Export
	public int getRowSpan(int row, int column)
	{
		return super.getFlexCellFormatter().getRowSpan(row, column);
	}
	@Export
	public void setRowSpan(int row, int column, int rowspan)
	{
		super.getFlexCellFormatter().setRowSpan(row, column, rowspan);
	}
	@Override
	@Export
	public void setBorderWidth(int width){
		super.setBorderWidth(width);
	}
	@Override
	@Export
	public void setCellPadding(int padding){
		super.setCellPadding(padding);
	}
	@Override
	@Export
	public int getCellPadding(){
		return super.getCellPadding();
	}
	@Override
	@Export
	public void setCellSpacing(int spacing){
		super.setCellPadding(spacing);
	}
	@Override
	@Export
	public int getCellSpacing(){
		return super.getCellSpacing();
	}
}
