package com.vk.gwt.designer.client.widgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkFlexTable extends FlexTable implements HasVkClickHandler{
	public static final String NAME = "Flex Table";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs;
	@SuppressWarnings("unused")//used in native function
	private boolean startSelection = false;
	@SuppressWarnings("unused")//used in native function
	private boolean firstSelection = false;
	private boolean isSelectionEnabled;
	public VkFlexTable()
	{
		showAddTextAttributeDialog();
	}
	@Override
	public void onLoad()
	{
		setCellPadding(0);
		setCellSpacing(0);
		DOM.setElementAttribute(getElement(), "cellspacing", "0");
	}
	@Override
	public void setWidth(String width)
	{
		double ratio = (double)Integer.parseInt(width.replace("px", "")) / (double)getOffsetWidth() ;
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount ; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				Widget widget = getWidget(i, j);
				widget.setWidth(widget.getOffsetWidth() * ratio + "px");
			}
		}
		super.setWidth(width);
	}
	@Override
	public void setHeight(String height)
	{
		double ratio = (double)Integer.parseInt(height.replace("px", "")) / (double)getOffsetHeight() ;
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount ; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				Widget widget = getWidget(i, j);
				widget.setHeight(widget.getOffsetHeight() * ratio + "px");
			}
		}
		super.setHeight(height);
	}
	private void showAddTextAttributeDialog() {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		dialog.add(new Label("Provide number of rows and columns"));
		HorizontalPanel rowHp = new HorizontalPanel();
		rowHp.setWidth("100%");
		dialog.add(rowHp);
		rowHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		rowHp.add(new Label("Row: "));
		rowHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		rowHp.setCellWidth(rowHp.getWidget(0), "50%");
		final TextBox rowsTextBox = new TextBox();
		rowHp.add(rowsTextBox);
		rowsTextBox.setWidth("50px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				rowsTextBox.setFocus(true);
			}
		};
		t.schedule(100);
		HorizontalPanel colHp = new HorizontalPanel();
		colHp.setWidth("100%");
		dialog.add(colHp);
		colHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		colHp.add(new Label("Column: "));
		colHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		colHp.setCellWidth(colHp.getWidget(0), "50%");
		final TextBox columnsTextBox = new TextBox();
		columnsTextBox.setWidth("50px");
		colHp.add(columnsTextBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try{
					int rowCount = Integer.parseInt(rowsTextBox.getText().trim());
					int columnCount = Integer.parseInt(columnsTextBox.getText().trim());
					for(int i = 0; i < rowCount; i++)
					{
						for(int j = 0; j < columnCount; j++)
						{
							VkVerticalPanel l = new VkVerticalPanel();
							VkAbsolutePanel l2 = (VkAbsolutePanel) VkDesignerUtil.getEngine().getWidget(VkAbsolutePanel.NAME);
							VkDesignerUtil.addWidget(l2, l);
							VkFlexTable.this.setWidget(i, j, l2);
							VkFlexTable.this.getFlexCellFormatter().setWidth(i,j,"1px");
							if(i < rowCount - 1)
								VkFlexTable.this.getFlexCellFormatter().setHeight(i,j,"1px");
							else
								VkFlexTable.this.getFlexCellFormatter().setHeight(i,j,"*");
							l2.setSize("40px", "20px");
							DOM.setElementAttribute(getFlexCellFormatter().getElement(i, j), "col", j + "");
							prepareForSelection(getFlexCellFormatter(), i, j);
						}
						VkFlexTable.this.getFlexCellFormatter().setWidth(i,getCellCount(i) - 1,"*");
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
	private native void prepareForSelection(FlexCellFormatter flexCellFormatter, int i, int j) /*-{
		var element = flexCellFormatter.@com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter::getElement(II)(i, j);
		var t = this;
		t.@com.vk.gwt.designer.client.widgets.VkFlexTable::getElement()().onclick = function(e){
			if(e.button == 0 && t.@com.vk.gwt.designer.client.widgets.VkFlexTable::isSelectionEnabled)
			{
				if(!t.@com.vk.gwt.designer.client.widgets.VkFlexTable::startSelection)
				{
					t.@com.vk.gwt.designer.client.widgets.VkFlexTable::startSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkFlexTable::firstSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkFlexTable::clearAllStyles()();
				}
				else
				{
					t.@com.vk.gwt.designer.client.widgets.VkFlexTable::startSelection = false;
					t.@com.vk.gwt.designer.client.widgets.VkFlexTable::firstSelection = false;
				}
			}
		}
		element.onmouseout = function(){
			if(t.@com.vk.gwt.designer.client.widgets.VkFlexTable::firstSelection)
			{
				t.@com.vk.gwt.designer.client.widgets.VkFlexTable::firstSelection = false;
				element.className = 'vkflextable-cell-selected first';
			}
		}
		element.onmouseover = function(){
			if(t.@com.vk.gwt.designer.client.widgets.VkFlexTable::startSelection)
			{
				t.@com.vk.gwt.designer.client.widgets.VkFlexTable::clearAllStyles()();
				if(element.className.indexOf('first') == -1)
					element.className = 'vkflextable-cell-selected';
				t.@com.vk.gwt.designer.client.widgets.VkFlexTable::selectAll()();
			}
		}
	}-*/;
	@SuppressWarnings("unused")//used in native function
	private void clearAllStyles()
	{
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
				if(getFlexCellFormatter().getStyleName(i, j).indexOf("first") == -1)
					getFlexCellFormatter().removeStyleName(i, j, "vkflextable-cell-selected");
		}
	}
	@SuppressWarnings("unused")//used in native function
	private void selectAll()
	{
		int maxCol = -1;
		int maxRow = -1;
		int minRow = -1; 
		int minCol = -1;
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					int row1 = i + getFlexCellFormatter().getRowSpan(i, j) - 1;
					int col1 = Integer.parseInt(DOM.getElementAttribute(getFlexCellFormatter().getElement(i, j), "col")) 
						+ getFlexCellFormatter().getColSpan(i, j) - 1;
					if(row1 > maxRow)
						maxRow = row1;
					if(col1 > maxCol)
						maxCol = col1;
					row1 = i;// - (getFlexCellFormatter().getRowSpan(i, j) - 1);
					col1 = Integer.parseInt(DOM.getElementAttribute(getFlexCellFormatter().getElement(i, j), "col"));
//						-(getFlexCellFormatter().getColSpan(i, j) - 1);
					if(minRow == -1 || row1 < minRow)
						minRow = row1;
					if(minCol == -1 || col1 < minCol)
						minCol = col1;
				}
			}
		}
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(getFlexCellFormatter().getColSpan(i, j) > 1)
				{
					int col1 = Integer.parseInt(DOM.getElementAttribute(getFlexCellFormatter().getElement(i, j), "col"));
					int row1 = i + getFlexCellFormatter().getRowSpan(i, j) - 1;
					if(i >= minRow && i <= maxRow && col1 >= minCol && col1 <= maxCol)
					{
						if(row1 > maxRow)
							maxRow = row1;
						if(col1 + (getFlexCellFormatter().getColSpan(i, j) - 1) > maxCol)
							maxCol = col1 + (getFlexCellFormatter().getColSpan(i, j) - 1);
					}
					else
					{
						int colRealWidth = col1 + (getFlexCellFormatter().getColSpan(i, j) - 1);
						if(i >= minRow && i <= maxRow && colRealWidth >= minCol && colRealWidth <= maxCol && col1 < minCol)
							minCol = col1;
						if(col1 >= minCol && col1 <= maxCol && row1 >= minRow && row1 <= maxRow && i < minRow)
							minRow = i;
					}
					
				}
			}
		}
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				int columnNumber = Integer.parseInt(DOM.getElementAttribute(getFlexCellFormatter().getElement(i, j), "col"));
				if(i >= minRow && i <= maxRow && columnNumber >= minCol && columnNumber <= maxCol && getFlexCellFormatter().getStyleName(i, j).indexOf("first") == -1)
						getFlexCellFormatter().setStyleName(i, j, "vkflextable-cell-selected");
			}
		}
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
	public boolean isSelectionEnabled() {
		return isSelectionEnabled;
	}
	public void setSelectionEnabled(boolean isSelectionEnabled) {
		this.isSelectionEnabled = isSelectionEnabled;
	}
	@Override
	public void setBorderWidth(int width){
		super.setBorderWidth(width);
	}
	@Override
	public void setCellPadding(int padding){
		super.setCellPadding(padding);
	}
	@Override
	public int getCellPadding(){
		return super.getCellPadding();
	}
	@Override
	public void setCellSpacing(int spacing){
		super.setCellPadding(spacing);
	}
	@Override
	public int getCellSpacing(){
		return super.getCellSpacing();
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
	@Export
	public String getCellStyleName(int row, int col)
	{
		return getFlexCellFormatter().getStyleName(row, col);
	}
	@Export
	public void addCellStyleName(int row, int col, String styleName)
	{
		getFlexCellFormatter().addStyleName(row, col, styleName);
	}
	@Export
	public void removeCellStyleName(int row, int col, String styleName)
	{
		getFlexCellFormatter().removeStyleName(row, col, styleName);
	}
	@Export
	public void setCellStyleName(int row, int col, String styleName)
	{
		getFlexCellFormatter().setStyleName(row, col, styleName);
	}
	@Export
	public void setRowVisible(int row, boolean visible)
	{
		getRowFormatter().setVisible(row, visible);
	}
	@Export
	public boolean isRowVisible(int row)
	{
		return getRowFormatter().isVisible(row);
	}
	@Export
	public void addRowStyleName(int row, String styleName)
	{
		getRowFormatter().addStyleName(row, styleName);
	}
	@Export
	public void removeRowStyleName(int row, String styleName)
	{
		getRowFormatter().removeStyleName(row, styleName);
	}
	@Export
	public void getRowStyleName(int row)
	{
		getRowFormatter().getStyleName(row);
	}
	/*@Export
	public void setCellHorizontalAlignment(int row, int col, String horizontalAlignment)
	{
		HorizontalAlignmentConstant hAlign;
		if(horizontalAlignment.equals(HasHorizontalAlignment.ALIGN_CENTER.getTextAlignString()))
			hAlign = HasHorizontalAlignment.ALIGN_CENTER;
		else if(horizontalAlignment.equals(HasHorizontalAlignment.ALIGN_LEFT.getTextAlignString()))
			hAlign = HasHorizontalAlignment.ALIGN_LEFT;
		else if(horizontalAlignment.equals(HasHorizontalAlignment.ALIGN_RIGHT.getTextAlignString()))
			hAlign = HasHorizontalAlignment.ALIGN_RIGHT;
		else 
		{
			Window.alert("horizontal direction can only take one of the following values: " + HasHorizontalAlignment.ALIGN_CENTER.getTextAlignString() + "," 
				+ HasHorizontalAlignment.ALIGN_LEFT.getTextAlignString() + "," +	HasHorizontalAlignment.ALIGN_RIGHT.getTextAlignString());
			return;
		}
		getFlexCellFormatter().setHorizontalAlignment(row, col, hAlign);
	}
	public void setCellVerticalAlignment(int row, int col, String verticalAlignment)
	{
		VerticalAlignmentConstant vAlign;
		if(verticalAlignment.equals(HasVerticalAlignment.ALIGN_BOTTOM.getVerticalAlignString()))
			vAlign = HasVerticalAlignment.ALIGN_BOTTOM;
		else if(verticalAlignment.equals(HasVerticalAlignment.ALIGN_MIDDLE.getVerticalAlignString()))
			vAlign = HasVerticalAlignment.ALIGN_MIDDLE;
		else if(verticalAlignment.equals(HasVerticalAlignment.ALIGN_TOP.getVerticalAlignString()))
			vAlign = HasVerticalAlignment.ALIGN_TOP;
		else 
		{
			Window.alert("vertical direction can only take one of the following values: " + HasVerticalAlignment.ALIGN_BOTTOM.getVerticalAlignString() + "," 
				+ HasVerticalAlignment.ALIGN_MIDDLE.getVerticalAlignString() + "," +	HasVerticalAlignment.ALIGN_TOP.getVerticalAlignString());
			return;
		}
		getFlexCellFormatter().setVerticalAlignment(row, col, vAlign);
	}*/
}
