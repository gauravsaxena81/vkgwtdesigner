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
package com.vk.gwt.designer.client.ui.widget.table.vkGrid;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanelEngine;
import com.vk.gwt.designer.client.ui.widget.label.vkHtml.VkHTML;
import com.vk.gwt.designer.client.ui.widget.label.vkLabel.VkLabel;
import com.vk.gwt.designer.client.ui.widget.table.IVkTable;

public class VkGrid extends Grid implements IVkWidget, HasVkClickHandler, IVkTable{
	public static final String NAME = "Grid";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs = "";
	private boolean startSelection = false;
	private boolean firstSelection = false;
	private VkGridColumnFormatter columnFormatter;
	private IVkWidget vkParent;
	
	class VkGridColumnFormatter extends ColumnFormatter
	{
		public com.google.gwt.user.client.Element getElement(int col){
			// no need to ensure that this <col> exists because in make cell this is made 
			return (com.google.gwt.user.client.Element)DOM.getChild(columnGroup, col);
		}
	}
	
	class VkGridAbsolutePanel extends VkAbsolutePanel{
		final public static String NAME = "FlexTable Panel";
		private IVkWidget vkParent;
		public void setWidth(String width) {
			super.setWidth("100%");
			if(width.endsWith("px")) {
				//double diff = Double.parseDouble(width.replaceAll("px", "")) - getOffsetWidth();
				int currentCol = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getElement().getParentElement(), "col"));
				VkGrid.this.columnFormatter.setWidth(currentCol
					, width/*Double.parseDouble(DOM.getElementAttribute(VkGrid.this.columnFormatter.getElement(currentCol)
						, "width").replaceAll("px", "")) + diff + "px"*/);
			}
		}
		public void setHeight(String height) {
			super.setHeight("100%");
			if(height.endsWith("px")) {
				//double diff = Double.parseDouble(height.replaceAll("px", "")) - getOffsetHeight();
				int currentRow = getRow();
				DOM.setElementAttribute(VkGrid.this.getRowFormatter().getElement(currentRow), "height"
					, height/*Double.parseDouble(DOM.getElementAttribute(VkGrid.this.getRowFormatter().getElement(currentRow)
						, "height").replaceAll("px", "")) + diff + "px"*/);
			}
		}
		public void onLoad() {
			super.onLoad();
			super.setSize("100%", "100%");
		}
		private int getRow() {
			int rows = VkGrid.this.getRowCount();
			for(int i = 0; i < rows; i++)
			{
				int cols = VkGrid.this.getCellCount(i);
				for(int j = 0; j < cols; j++)
					if(VkGrid.this.getWidget(i, j).equals(this))
						return i;
			}
			return -1;
		}
		@Override
		public String getWidgetName() {
			return VkGridAbsolutePanel.NAME;
		}
		@Override
		public boolean showMenu() {
			return true;
		}
		@Override
		public IVkWidget getVkParent() {
			return this.vkParent;
		}
		@Override
		public void setVkParent(IVkWidget panel) {
			this.vkParent = panel;
		}
	};
	
	class VkGridAbsolutePanelEngine extends VkAbsolutePanelEngine{
		@Override
		public VkGridAbsolutePanel getWidget() {
			VkGridAbsolutePanel widget = new VkGridAbsolutePanel();
			init(widget);
			return widget;
		}
		@Override
		public List<String> getOperationsList(Widget invokingWidget)
		{
			List<String> operationsList = VkStateHelper.getInstance().getEngine().getOperationsList(invokingWidget);
			operationsList.remove(IEngine.DELETE);
			operationsList.remove(IEngine.CUT);
			return operationsList;
		}
	}
	
	public VkGrid() {
		if(!VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().containsKey(VkGridAbsolutePanel.NAME))
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().put(VkGridAbsolutePanel.NAME, new VkGridAbsolutePanelEngine());
		/*if(VkStateHelper.getInstance().isDesignerMode())
			showAddTextAttributeDialog();*/
		this.columnFormatter = new VkGridColumnFormatter();
		setColumnFormatter(columnFormatter);
		DOM.setStyleAttribute(getElement(), "tableLayout", "fixed");
		if(VkStateHelper.getInstance().isDesignerMode()) {
			this.addDomHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					if(event.getNativeButton() == Event.BUTTON_LEFT){
						startSelection = true;
						firstSelection = true;
						clearAllStyles();
						Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
						if(td != null) {
						    /*int row = TableRowElement.as(td.getParentElement()).getSectionRowIndex();
						    int column = TableCellElement.as(td).getCellIndex();
							VkFlexTable.this.getFlexCellFormatter().setStyleName(cell.getRowIndex(), cell.getCellIndex(), "vkflextable-cell-selected first");*/
							td.setClassName("vkflextable-cell-selected first");
						}
					}
				}
			}, MouseDownEvent.getType());
			this.addDomHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					if(event.getNativeButton() == Event.BUTTON_LEFT){
						startSelection = false;
						firstSelection = false;
						VkDesignerUtil.clearSelection();
					}
				}
			}, MouseUpEvent.getType());
		}
	}
	@Override
	public void onLoad()
	{
		setCellPadding(0);
		setCellSpacing(0);
		DOM.setElementAttribute(getElement(), "cellspacing", "0");
	}
	@Override
	public void setWidth(String width) {
		if(getRowCount() != 0)	{
			double percentage = (double)(Integer.parseInt(width.replace("px", "")) + VkDesignerUtil.getDecorationsWidth(this.getElement()))/ (double)getOffsetWidth();
			int columns = getCellCount(0);
			for(int i = 0; i < columns; i++)
				columnFormatter.setWidth(i
				, percentage * Double.parseDouble(DOM.getElementAttribute(VkGrid.this.columnFormatter.getElement(i), "width").replaceAll("px", "")) + "px");
		}
	}
	@Override
	public void setHeight(final String height) {
		int rows = getRowCount();
		if(rows != 0) {
			double percentage =  (double)(Integer.parseInt(height.replace("px", "")) + VkDesignerUtil.getDecorationsHeight(this.getElement())) / (double)getOffsetHeight();
			for(int i = 0; i < rows; i++) {
				DOM.setElementAttribute(getRowFormatter().getElement(i), "height"
				, percentage * Double.parseDouble(DOM.getElementAttribute(getRowFormatter().getElement(i), "height").replaceAll("px", "")) + "px");
				for(int j = 0, cols = getCellCount(i); j < cols; j++) {
					DOM.setStyleAttribute(getCellFormatter().getElement(i, j), "height", "");
					//DOM.setStyleAttribute(getCellFormatter().getElement(i, j), "height", "inherit");//IE otherwise doesn't resize cells-TODO not working in IE
				}
			}
		}
	}
	/*private void showAddTextAttributeDialog() {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Provide number of rows and columns");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		HorizontalPanel rowHp = new HorizontalPanel();
		rowHp.setWidth("100%");
		dialog.add(rowHp);
		rowHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		rowHp.add(new Label("Row: "));
		rowHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		rowHp.setCellWidth(rowHp.getWidget(0), "50%");
		final TextBox rowsTextBox = new TextBox();
		rowHp.add(rowsTextBox);
		rowsTextBox.setWidth("300px");
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				rowsTextBox.setFocus(true);
			}
		}.schedule(100);
		HorizontalPanel colHp = new HorizontalPanel();
		colHp.setWidth("100%");
		dialog.add(colHp);
		colHp.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		colHp.add(new Label("Column: "));
		colHp.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		colHp.setCellWidth(colHp.getWidget(0), "50%");
		final TextBox columnsTextBox = new TextBox();
		columnsTextBox.setWidth("300px");
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
					int colCount = Integer.parseInt(columnsTextBox.getText().trim());
					defineTable(rowCount, colCount);
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(VkGrid.this);
					origDialog.hide();
				} catch(NumberFormatException e) {
					Window.alert("row and column number cannot be non-numeric");
				}
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
	}*/
	public void makeCell(final int row, final int col)
	{
		VkGridAbsolutePanel l2 = new VkGridAbsolutePanel();
		l2.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(startSelection){
					clearSelectedCells();
					Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
					if(td != null) {
						if(td.getClassName().indexOf("first") == -1)
							td.setClassName("vkflextable-cell-selected");
						selectAll();
					}
				}
			}
		}, MouseOverEvent.getType());
		DOM.setStyleAttribute(l2.getElement(), "overflow", "");
		VkStateHelper.getInstance().getEngine().prepareWidget(l2);
		boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
		VkStateHelper.getInstance().setDesignerMode(false);//important as call routes to inserRow here instead of super's
		setWidget(row, col, l2);
		DOM.setStyleAttribute(l2.getElement(), "border", "solid 1px gray");
		l2.setVkParent(this);
		VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
		//DOM.setStyleAttribute(getCellFormatter().getElement(row, col), "height", "inherit");
		DOM.setElementAttribute(getCellFormatter().getElement(row, col), "col", Integer.toString(col));
		DOM.setElementAttribute(VkGrid.this.getRowFormatter().getElement(row), "height", "40px");
		DOM.setStyleAttribute(VkGrid.this.getCellFormatter().getElement(row, col), "position", "relative");
		columnFormatter.setWidth(col, "80px");
	}
	private void clearSelectedCells() {
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++) {
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
				if(getCellFormatter().getStyleName(i, j).indexOf("first") == -1)
					getCellFormatter().removeStyleName(i, j, "vkflextable-cell-selected");
		}
	}
	private void clearAllStyles() {
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++) {
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
				getCellFormatter().setStyleName(i, j, "");
		}
	}
	private void selectAll() {
		int maxCol = -1;
		int maxRow = -1;
		int minRow = -1; 
		int minCol = -1;
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++) {
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++) {
				if(getCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)	{
					int row1 = i;
					int col1 = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
					if(row1 > maxRow)
						maxRow = row1;
					if(col1 > maxCol)
						maxCol = col1;
					row1 = i;
					col1 = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
					if(minRow == -1 || row1 < minRow)
						minRow = row1;
					if(minCol == -1 || col1 < minCol)
						minCol = col1;
				}
			}
		}
		for(int i = 0; i < rowCount; i++) {
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++) {
				int columnNumber = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
				if(i >= minRow && i <= maxRow && columnNumber >= minCol && columnNumber <= maxCol && getCellFormatter().getStyleName(i, j).indexOf("first") == -1)
						getCellFormatter().setStyleName(i, j, "vkflextable-cell-selected");
			}
		}
	}
	@Override
	public void addClickHandler(String js) {
		if(clickHandlerRegistration != null)
			clickHandlerRegistration.removeHandler();
		clickHandlerRegistration = null;
		clickJs = js.trim();
		if(!clickJs.isEmpty()) {
			clickHandlerRegistration = addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Cell cell = getCellForEvent(event);
					setUpCellCallingEvent(cell.getRowIndex(), cell.getCellIndex());
					VkStateHelper.getInstance().getEventHelper().executeEvent(clickJs, event, false);
				}			
			});
		}
	}
	/**
	 * Sets up cell indices on which cell event was called in the vkEvent global variable.
	 * @param rowIndex - index of the row containing the cell
	 * @param cellIndex - index of cell in the parent row
	 */
	private native void setUpCellCallingEvent(int rowIndex, int cellIndex) /*-{
		$wnd.vkEvent = {};
		$wnd.vkEvent.row = rowIndex;
		$wnd.vkEvent.col = cellIndex;
	}-*/;
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
		else
			return "";
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
	/*
	public void setCellWordWrap(int row, int col, boolean wordWrap) {
		super.getCellFormatter().setWordWrap(row, col, wordWrap);
	}*/
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
	@Override
	public void defineTable(int rows, int cols) {
		VkGrid.this.resize(rows, cols);
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				makeCell(i, j);
	}
	/**************************Export attribute Methods********************************/
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
	@Export
	public void insertColumn(int beforeColumn)
	{
		for(int i = 0, rows = getRowCount(); i < rows; i++)
			super.insertCell(i, beforeColumn);
		super.numColumns++;
		if(VkStateHelper.getInstance().isDesignerMode())
			for(int i = 0, rows = getRowCount(); i < rows; i++)
				makeCell(i, beforeColumn);
	}
	@Override
	@Export
	public int insertRow(int beforeRow) {
	    int rowNum =  super.insertRow(beforeRow);
	    if(VkStateHelper.getInstance().isDesignerMode()) {
			int columnCount = getColumnCount();
		    for(int i = 0; i < columnCount; i++)
		    	makeCell(rowNum, i);
	    }
	    return rowNum;
	}
	@Override
	@Export
	public void removeRow(int row) {
		super.removeRow(row);
	}
	@Export
	public void removeAllRows() {
		super.resizeRows(0);
	}
	@Export
	public void removeColumn(int col) {
		getColumnFormatter().getElement(col).removeFromParent();
		 for(int i = 0, rows = getRowCount(); i < rows; i++)
			 super.removeCell(i, col);
		 super.numColumns--;
	}
	@Export
	public void setVisible(int row, int column, boolean isVisible)
	{
		checkCellBounds(row, column);
		super.getCellFormatter().setVisible(row, column, isVisible);
	}
	@Export
	public boolean isVisible(int row, int column)
	{
		return super.getCellFormatter().isVisible(row, column);
	}
	@Export
	public String getCellStyleName(int row, int col)
	{
		return getCellFormatter().getStyleName(row, col);
	}
	@Export
	public void addCellStyleName(int row, int col, String styleName)
	{
		checkCellBounds(row, col);
		getCellFormatter().addStyleName(row, col, styleName);
	}
	@Export
	public void removeCellStyleName(int row, int col, String styleName)
	{
		getCellFormatter().removeStyleName(row, col, styleName);
	}
	@Export
	public void setCellStyleName(int row, int col, String styleName)
	{
		checkCellBounds(row, col);
		getCellFormatter().setStyleName(row, col, styleName);
	}
	@Export
	public void setRowVisible(int row, boolean visible)
	{
		checkRowBounds(row);
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
		checkRowBounds(row);
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
	@Export 
	public Element getCellElement(int row, int col)
	{
		return getCellFormatter().getElement(row, col);
	}
	@Override
	@Export
	public boolean clearCell(int row, int col)
	{
		checkCellBounds(row, col);
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		boolean isWidgetPresent;
		if(panel.getWidgetCount() > 0)
			isWidgetPresent = true;
		else
			isWidgetPresent = false;
		panel.clear();
		return isWidgetPresent;
	}
	@Override
	@Export
	public String getHTML(int row, int col)
	{
		return super.getHTML(row, col);
	}
	@Override
	@Export
	public void setHTML(int row, int col, String html)
	{
		checkCellBounds(row, col);
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		panel.clear();
		DOM.setInnerHTML(panel.getElement(), "");
		VkStateHelper.getInstance().getEngine().addWidget(VkStateHelper.getInstance().getEngine().getWidget(VkHTML.NAME), panel);
	}
	@Override
	@Export
	public boolean isCellPresent(int row, int col)
	{
		return super.isCellPresent(row, col);
	}
	@Override
	@Export
	public String getText(int row, int col)
	{
		return DOM.getInnerText(getWidget(row, col).getElement());
	}
	@Override
	@Export
	public void setText(int row, int col, String text)
	{
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		panel.clear();
		VkStateHelper.getInstance().getEngine().addWidget(VkStateHelper.getInstance().getEngine().getWidget(VkLabel.NAME), panel);
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
		// TODO Auto-generated method stub
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
	@Override
	public void setWidget(int row, int col, Widget widget){
		if(row >= 0 && row < getRowCount() && col >=0 && col < getCellCount(row) && widget instanceof IVkWidget){
			super.setWidget(row, col, widget);
			widget.addDomHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(startSelection){
						clearSelectedCells();
						Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
						if(td != null) {
							if(td.getClassName().indexOf("first") == -1)
								td.setClassName("vkflextable-cell-selected");
							selectAll();
						}
					}
				}
			}, MouseOverEvent.getType());
			((IVkWidget)widget).setVkParent(this);
			DOM.setStyleAttribute(widget.getElement(), "overflow", "");
		}
	}
}
