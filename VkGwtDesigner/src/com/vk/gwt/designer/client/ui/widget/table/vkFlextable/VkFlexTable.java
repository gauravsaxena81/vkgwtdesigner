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
package com.vk.gwt.designer.client.ui.widget.table.vkFlextable;

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
import com.google.gwt.user.client.ui.FlexTable;
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

public class VkFlexTable extends FlexTable implements IVkWidget, HasVkClickHandler, IVkTable{
	public static final String NAME = "Flex Table";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs = "";
	private boolean startSelection = false;
	private boolean firstSelection = false;
	private boolean isSelectionEnabled = true;
	private VkFlexTableColumnFormatter columnFormatter;
	private int initialRowCount;
	private int initialColumnCount;
	private IVkWidget vkParent;
	
	class VkFlexTableColumnFormatter extends ColumnFormatter {
		public com.google.gwt.user.client.Element getElement(int col) {
			// no need to ensure that this <col> exists because in makeCell this is made 
			return (com.google.gwt.user.client.Element)DOM.getChild(columnGroup, col);
		}
	}
	
	class VkFlexTableAbsolutePanel extends VkAbsolutePanel {
		final public static String NAME = "FlexTable Panel";
		private IVkWidget vkParent;
		public void setWidth(String width) {
			super.setWidth("100%");
			if(width.endsWith("px")) {
				int intWidth = (int) Double.parseDouble(width.replaceAll("px", ""));
				int currentCol = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getElement().getParentElement(), "col"));
				int denominator = 0;
				int maxCols = currentCol + Math.max(1, DOM.getElementPropertyInt((com.google.gwt.user.client.Element) getElement().getParentElement(), "colSpan"));
				for(int i = currentCol; i < maxCols; i++)
					denominator += DOM.getElementPropertyInt(VkFlexTable.this.columnFormatter.getElement(i), "width");
				for(int i = currentCol; i < maxCols; i++)
					VkFlexTable.this.columnFormatter.setWidth(i
					, intWidth * DOM.getElementPropertyInt(VkFlexTable.this.columnFormatter.getElement(i), "width") / denominator - VkDesignerUtil.getDecorationsHeight(getElement()) + "px");
			}
		}
		public void setHeight(String height) {
			super.setHeight("100%");
			if(height.endsWith("px")) {
				int intHeight = (int) Double.parseDouble(height.replaceAll("px", ""));
				int currentRow = getRow();
				int denominator = 0;
				int maxRows = currentRow + Math.max(1, DOM.getElementPropertyInt((com.google.gwt.user.client.Element) getElement().getParentElement(), "rowSpan"));
				for(int i = currentRow; i < maxRows; i++)
					denominator += Double.parseDouble(DOM.getElementAttribute(VkFlexTable.this.getRowFormatter().getElement(i), "height").replaceAll("px", ""));
				for(int i = currentRow; i < maxRows; i++)
					DOM.setElementAttribute(VkFlexTable.this.getRowFormatter().getElement(i), "height"
					, intHeight * (int)Double.parseDouble(DOM.getElementAttribute(VkFlexTable.this.getRowFormatter().getElement(i), "height").replaceAll("px", "")) / denominator 
					- VkDesignerUtil.getDecorationsHeight(getElement())+ "px");
			}
		}
		public void onLoad() {
			super.onLoad();
			super.setSize("100%", "100%");
		}
		private int getRow() {
			for(int i = 0, rows = VkFlexTable.this.getRowCount(); i < rows; i++)
				for(int j = 0, cols = VkFlexTable.this.getCellCount(i); j < cols; j++)
					if(VkFlexTable.this.getWidget(i, j).equals(this))
						return i;
			return -1;
		}
		@Override
		public String getWidgetName() {
			return VkFlexTableAbsolutePanel.NAME;
		}
		@Override
		public boolean showMenu() {
			return true;
		}
		@Override
		public boolean isMovable() {
			return false;
		}
		@Override
		public boolean isResizable() {
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
	
	class VkFlexTableAbsolutePanelEngine extends VkAbsolutePanelEngine{
		@Override
		public VkFlexTableAbsolutePanel getWidget() {
			VkFlexTableAbsolutePanel widget = new VkFlexTableAbsolutePanel();
			init(widget);
			return widget;
		}
		@Override
		public List<String> getOperationsList(Widget invokingWidget) {
			List<String> operationsList = VkStateHelper.getInstance().getEngine().getOperationsList(invokingWidget);
			operationsList.remove(IEngine.DELETE);
			operationsList.remove(IEngine.CUT);
			return operationsList;
		}
	}
	
	public VkFlexTable() {
		if(!VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().containsKey(VkFlexTableAbsolutePanel.NAME))
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().put(VkFlexTableAbsolutePanel.NAME, new VkFlexTableAbsolutePanelEngine());
		this.columnFormatter = new VkFlexTableColumnFormatter();
		setColumnFormatter(columnFormatter);
		DOM.setStyleAttribute(getElement(), "tableLayout", "fixed");
		if(VkStateHelper.getInstance().isDesignerMode()) {
			this.addDomHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					if(event.getNativeButton() == Event.BUTTON_LEFT && isSelectionEnabled){
						startSelection = true;
						firstSelection = true;
						Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
						if(td != null) {
							clearCellSelection();
							if(td.getClassName().indexOf("vkflextable-cell-selected") == - 1)
								td.setClassName("vkflextable-cell-selected first");
							event.stopPropagation();
						}
					}
				}
			}, MouseDownEvent.getType());
			this.addDomHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					if(event.getNativeButton() == Event.BUTTON_LEFT && isSelectionEnabled){
						startSelection = false;
						firstSelection = false;
					}
					VkDesignerUtil.clearSelection();
				}
			}, MouseUpEvent.getType());
		}
	}
	public void clearCellSelection() {
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0, colCount = getCellCount(i); j < colCount; j++)
				getFlexCellFormatter().setStyleName(i, j, "");
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
		if(getRowCount() != 0) {
			double percentage = (double)(Integer.parseInt(width.replace("px", "")) + VkDesignerUtil.getDecorationsWidth(this.getElement())) / (double)getOffsetWidth();
			int columns = getCellCount(0);
			int colCount = 0;
			for(int i = 0; i < columns; i++)
				colCount += getFlexCellFormatter().getColSpan(0, i);
			for(int i = 0; i < colCount; i++)
				columnFormatter.setWidth(i
				, percentage * Double.parseDouble(DOM.getElementAttribute(VkFlexTable.this.columnFormatter.getElement(i), "width").replaceAll("px", "")) + "px");
		}
	}
	@Override
	public void setHeight(final String height) {
		int rows = getRowCount();
		if(rows != 0) {
			double percentage = (double)(Integer.parseInt(height.replace("px", "")) + VkDesignerUtil.getDecorationsHeight(this.getElement())) / (double)getOffsetHeight();
			for(int i = 0; i < rows; i++) {
				DOM.setElementAttribute(getRowFormatter().getElement(i), "height"
				, percentage * Double.parseDouble(DOM.getElementAttribute(getRowFormatter().getElement(i), "height").replaceAll("px", "")) + "px");
				for(int j = 0, cols = getCellCount(i); j < cols; j++) {
					DOM.setStyleAttribute(getFlexCellFormatter().getElement(i, j), "height", "");
					//DOM.setStyleAttribute(getFlexCellFormatter().getElement(i, j), "height", "inherit");//IE otherwise doesn't resize cells//TODO not working in IE
				}
			}
		}
	}
	public void makeCell(final int row, final int col, int actualCol) {
		VkFlexTableAbsolutePanel l2 = new VkFlexTableAbsolutePanel();
		if(VkStateHelper.getInstance().isDesignerMode()) {
			l2.addDomHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(startSelection){
						clearSelectedCellsExceptFirst();
						Element td = getEventTargetCell(Event.as(event.getNativeEvent()));
						if(td != null) {
							if(td.getClassName().indexOf("first") == -1)
								td.setClassName("vkflextable-cell-selected");
							selectAll();
						}
					}
				}
			}, MouseOverEvent.getType());
		}
		DOM.setStyleAttribute(l2.getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(l2.getElement(), "overflow", "");//otherwise mouse over is not called when mouse button is pressed 
		VkStateHelper.getInstance().getEngine().prepareWidget(l2);
		boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
		VkStateHelper.getInstance().setDesignerMode(false);//important as call routes to inserRow here instead of super's
		super.setWidget(row, col, l2);
		l2.setVkParent(this);
		VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
		//DOM.setStyleAttribute(getFlexCellFormatter().getElement(row, col), "height", "inherit");
		DOM.setElementAttribute(getFlexCellFormatter().getElement(row, col), "col", Integer.toString(actualCol));
		DOM.setElementAttribute(VkFlexTable.this.getRowFormatter().getElement(row), "height", "40px");
		DOM.setStyleAttribute(VkFlexTable.this.getFlexCellFormatter().getElement(row, col), "position", "relative");
		columnFormatter.setWidth(actualCol, "80px");
		initialColumnCount = Math.max(initialColumnCount, actualCol + 1);
	}
	private void clearSelectedCellsExceptFirst() {
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++) {
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
				if(getFlexCellFormatter().getStyleName(i, j).indexOf("first") == -1)
					getFlexCellFormatter().removeStyleName(i, j, "vkflextable-cell-selected");
		}
	}
	private void selectAll() {
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
					row1 = i;
					col1 = Integer.parseInt(DOM.getElementAttribute(getFlexCellFormatter().getElement(i, j), "col"));
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
		clickHandlerRegistration = null;
		clickJs = js.trim();
		if(!clickJs.isEmpty())
		{
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
	public void addCell(int row)
	{
		super.addCell(row);
		makeCell(row, getCellCount(row) - 1, initialColumnCount - 1);
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
	public void insertCell(int beforeRow, int beforeColumn) {
		super.insertCell(beforeRow, beforeColumn);
		makeCell(beforeRow, beforeColumn, actualColumnNumber(beforeRow, beforeColumn));
	}
	@Override
	public void setWidget(int row, int col, Widget widget){
		if(row >= 0 && row < getRowCount() && col >=0 && col < getCellCount(row) && widget instanceof IVkWidget){
			super.setWidget(row, col, widget);
			widget.addDomHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					if(startSelection){
						clearSelectedCellsExceptFirst();
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
			DOM.setStyleAttribute(widget.getElement(), "border", "solid 1px gray");
			DOM.setStyleAttribute(widget.getElement(), "overflow", "");
		}
	}
	private int actualColumnNumber(int beforeRow, int beforeColumn) {
		if(beforeColumn == 0)
			return 0;
		else if(beforeColumn > getInitialColumnCount())
			return getInitialColumnCount();
		int actualColumnNumber = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getCellElement(beforeRow, beforeColumn - 1), "col"));
		for(int i = 0; i < beforeRow; i++)
			for(int j = 0, cols = getCellCount(i); j < cols; j++)
				if(Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getCellElement(i, j), "col")) > actualColumnNumber)
					if(i + Math.max(getRowSpan(i, j), 1) - 1 >= beforeRow)
						actualColumnNumber += getColSpan(i, j);
					else
						break;
		actualColumnNumber += Math.max(getColSpan(beforeRow, beforeColumn - 1), 1);
		return actualColumnNumber;
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	@Override
	public void defineTable(int rows, int cols) {
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				makeCell(i, j , j);
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
	@Override
	@Export
	public int insertRow(int beforeRow) {
	    int rowNum =  super.insertRow(beforeRow);
	    if(VkStateHelper.getInstance().isDesignerMode()) {
	    	int lastActualColumnNumber = 0;
		    for(int i = 0, colsAdded = 0; i < beforeRow; i++) {
		    	for(int j = 0, cols = getCellCount(i); j < cols; j++) {
		    		if( i + getRowSpan(i, j) - 1 >= beforeRow) {
		    			setRowSpan(i, j, getRowSpan(i, j) + 1);
		    			for(int actualCols = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getCellElement(i, j), "col")); lastActualColumnNumber < actualCols; 
		    			colsAdded++, lastActualColumnNumber++)
		    				makeCell(beforeRow, colsAdded, lastActualColumnNumber);
		    			lastActualColumnNumber = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getCellElement(i, j), "col")) + getColSpan(i, j);
		    		}
		    	}
		    }
		    for(int i = getCellCount(rowNum); lastActualColumnNumber < initialColumnCount; i++, lastActualColumnNumber++)
		    	makeCell(rowNum, i, lastActualColumnNumber);
	    }
	    initialRowCount++;
	    return rowNum;
	}
	@Override
	@Export
	public void removeRow(int row) {
		super.removeRow(row);
		initialRowCount--;
	}
	@Override
	@Export
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
		checkCellBounds(row, column);
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
		checkCellBounds(row, column);
		super.getFlexCellFormatter().setRowSpan(row, column, rowspan);
	}
	@Export
	public void setVisible(int row, int column, boolean isVisible)
	{
		checkCellBounds(row, column);
		super.getFlexCellFormatter().setVisible(row, column, isVisible);
	}
	@Export
	public boolean isVisible(int row, int column)
	{
		return super.getFlexCellFormatter().isVisible(row, column);
	}
	@Export
	public String getCellStyleName(int row, int col)
	{
		return getFlexCellFormatter().getStyleName(row, col);
	}
	@Export
	public void addCellStyleName(int row, int col, String styleName)
	{
		checkCellBounds(row, col);
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
		checkCellBounds(row, col);
		getFlexCellFormatter().setStyleName(row, col, styleName);
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
		return getFlexCellFormatter().getElement(row, col);
	}
	@Override
	@Export
	public boolean clearCell(int row, int col) {
		checkCellBounds(row, col);
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		boolean isWidgetPresent = panel.getWidgetCount() > 0;
		panel.clear();
		return isWidgetPresent;
	}
	@Override
	@Export
	public String getHTML(int row, int col) {
		return super.getHTML(row, col);
	}
	@Override
	@Export
	public void setHTML(int row, int col, String html) {
		checkCellBounds(row, col);
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		panel.clear();
		VkHTML htmlWidget = (VkHTML)VkStateHelper.getInstance().getEngine().getWidget(VkHTML.NAME);
		htmlWidget.setHTML(html);
		VkStateHelper.getInstance().getEngine().addWidget(htmlWidget, panel);
	}
	@Override
	@Export
	public boolean isCellPresent(int row, int col) {
		return super.isCellPresent(row, col);
	}
	@Override
	@Export
	public String getText(int row, int col)	{
		return DOM.getInnerText(getWidget(row, col).getElement());
	}
	@Override
	@Export
	public void setText(int row, int col, String text) {
		VkAbsolutePanel panel = (VkAbsolutePanel)getWidget(row, col);
		panel.clear();
		VkLabel label = (VkLabel)VkStateHelper.getInstance().getEngine().getWidget(VkLabel.NAME);
		label.setText(text);
		VkStateHelper.getInstance().getEngine().addWidget(label, panel);
	}
	@Override
	@Export
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible() {
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className) {
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className) {
		super.removeStyleName(className);
	}
	public int getInitialRowCount() {
		return initialRowCount;
	}
	public int getInitialColumnCount() {
		return initialColumnCount;
	}
	public void setInitialRowCount(int initialRowCount) {
		this.initialRowCount = initialRowCount;
	}
	public void setInitialColumnCount(int initialColumnCount) {
		this.initialColumnCount = initialColumnCount;
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
