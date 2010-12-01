package com.vk.gwt.designer.client.widgets;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkGrid extends Grid implements IVkWidget, HasVkClickHandler{
	public static final String NAME = "Grid";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs;
	@SuppressWarnings("unused")//used in native function
	private boolean startSelection = false;
	@SuppressWarnings("unused")//used in native function
	private boolean firstSelection = false;
	private boolean isSelectionEnabled;
	public VkGrid()
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
					VkGrid.super.resize(rowCount, columnCount);
					for(int i = 0; i < rowCount; i++)
					{
						for(int j = 0; j < columnCount; j++)
							makeCell(i, j, rowCount);
						VkGrid.this.getCellFormatter().setWidth(i,getCellCount(i) - 1,"*");
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
	public void makeCell(int row, int col, int rowCount)
	{
		VkVerticalPanel l = new VkVerticalPanel();
		VkAbsolutePanel l2 = (VkAbsolutePanel) VkDesignerUtil.getEngine().getWidget(VkAbsolutePanel.NAME);
		VkDesignerUtil.addWidget(l2, l);
		VkGrid.this.setWidget(row, col, l2);
		VkGrid.this.getCellFormatter().setWidth(row,col,"1px");
		if(row < rowCount - 1)
			VkGrid.this.getCellFormatter().setHeight(row,col,"1px");
		else
			VkGrid.this.getCellFormatter().setHeight(row,col,"*");
		l2.setSize("40px", "20px");
		DOM.setElementAttribute(getCellFormatter().getElement(row, col), "col", col + "");
		prepareForSelection(getCellFormatter(), row, col);
	}
	private native void prepareForSelection(CellFormatter cellFormatter, int i, int j) /*-{
		var element = cellFormatter.@com.google.gwt.user.client.ui.HTMLTable.CellFormatter::getElement(II)(i, j);
		var t = this;
		t.@com.vk.gwt.designer.client.widgets.VkGrid::getElement()().onclick = function(e){
			if(!!e)
				e = $wnd.event;
			if(e.button == 0 && t.@com.vk.gwt.designer.client.widgets.VkGrid::isSelectionEnabled)
			{
				if(!t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection)
				{
					t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::clearAllStyles()();
				}
				else
				{
					t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection = false;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection = false;
				}
			}
		}
		element.onmouseout = function(){
			if(t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection)
			{
				t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection = false;
				element.className = 'vkflextable-cell-selected first';
			}
		}
		element.onmouseover = function(){
			if(t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection)
			{
				t.@com.vk.gwt.designer.client.widgets.VkGrid::clearAllStyles()();
				if(element.className.indexOf('first') == -1)
					element.className = 'vkflextable-cell-selected';
				t.@com.vk.gwt.designer.client.widgets.VkGrid::selectAll()();
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
				if(getCellFormatter().getStyleName(i, j).indexOf("first") == -1)
					getCellFormatter().removeStyleName(i, j, "vkflextable-cell-selected");
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
				if(getCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					int row1 = i;
					int col1 = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
					if(row1 > maxRow)
						maxRow = row1;
					if(col1 > maxCol)
						maxCol = col1;
					row1 = i;// - (getCellFormatter().getRowSpan(i, j) - 1);
					col1 = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
//						-(getCellFormatter().getColSpan(i, j) - 1);
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
		clickJs = js;
		clickHandlerRegistration = addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VkDesignerUtil.executeEvent(clickJs, event);
				Cell cell = getCellForEvent(event);
				setUpCellCallingEvent(cell.getRowIndex(), cell.getCellIndex());
			}			
		});
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
	/*public void setCellHorizontalAlignment(int row, int col, String horizontalAlignment)
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
		getCellFormatter().setHorizontalAlignment(row, col, hAlign);
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
		getCellFormatter().setVerticalAlignment(row, col, vAlign);
	}
	public void setCellWordWrap(int row, int col, boolean wordWrap) {
		super.getCellFormatter().setWordWrap(row, col, wordWrap);
	}*/
	@Override
	public String getWidgetName() {
		return NAME;
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
	public void addColumn()
	{
		int cols = getColumnCount(); 
		super.resizeColumns(cols + 1);
		int rows = getRowCount();
		for(int i = 0; i < rows; i++)
			makeCell(i, cols, rows);
	}
	@Override
	@Export
	public int insertRow(int beforeRow) {
	    int rowNum =  super.insertRow(beforeRow);
	    int cellCount = getCellCount(rowNum);
	    int rowCount = getRowCount();
	    for(int i = 0; i < cellCount; i++)
	    	makeCell(rowNum, i, rowCount);
	    return rowNum;
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
		VkDesignerUtil.addWidget(VkDesignerUtil.getEngine().getWidget(VkHTML.NAME), panel);
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
		VkDesignerUtil.addWidget(VkDesignerUtil.getEngine().getWidget(VkLabel.NAME), panel);
	}
	@Override
	@Export
	public int getColumnCount()
	{
		return super.getColumnCount();
	}
	@Override
	@Export
	public void resizeRows(int rows)
	{
		int initialRows = getRowCount();
		super.resizeRows(rows);
		int finalRows = getRowCount();
		int colCount = getColumnCount();
		for(int i = initialRows; i < finalRows; i++)
			for(int j = 0; j < colCount; j++)
				makeCell(i, j, finalRows);
	}
	@Override
	@Export
	public void resizeColumns(int columns)
	{
		int initialColumns = getColumnCount();
		super.resizeColumns(columns);
		int finalColumnCount = getColumnCount();
		int rows = getRowCount();
		for(int i = 0; i < rows; i++)
			for(int j = initialColumns; j < finalColumnCount; j++)
				makeCell(i, j, rows);
	}
	@Override
	@Export
	public void resize(int rows, int columns)
	{
		resizeRows(rows);
		resizeColumns(columns);
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
}
