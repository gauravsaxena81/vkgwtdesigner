package com.vk.gwt.designer.client.widgets;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.engine.VkAbsolutePanelEngine;

public class VkGrid extends Grid implements IVkWidget, HasVkClickHandler{
	public static final String NAME = "Grid";
	private HandlerRegistration clickHandlerRegistration;
	private String clickJs = "";
	@SuppressWarnings("unused")//used in native function
	private boolean startSelection = false;
	@SuppressWarnings("unused")//used in native function
	private boolean firstSelection = false;
	private boolean isSelectionEnabled;
	private VkFlexTableColumnFormatter columnFormatter;
	
	class VkFlexTableColumnFormatter extends ColumnFormatter
	{
		public com.google.gwt.user.client.Element getElement(int col){
			// no need to ensure that this <col> exists because in make cell this is made 
			return (com.google.gwt.user.client.Element)DOM.getChild(columnGroup, col);
		}
	}
	
	class VkFlexTableAbsolutePanel extends VkAbsolutePanel{
		final public static String NAME = "FlexTable Panel";
		public void setWidth(String width)
		{
			super.setWidth("100%");
			if(width.endsWith("px"))
			{
				double diff = Double.parseDouble(width.replaceAll("px", "")) - getOffsetWidth();
				int currentCol = Integer.parseInt(DOM.getElementAttribute((com.google.gwt.user.client.Element) getElement().getParentElement(), "col"));
				VkGrid.this.columnFormatter.setWidth(currentCol
					, Double.parseDouble(DOM.getElementAttribute(VkGrid.this.columnFormatter.getElement(currentCol)
						, "width").replaceAll("px", "")) + diff + "px");
			}
		}
		public void setHeight(String height)
		{
			super.setHeight("100%");
			if(height.endsWith("px"))
			{
				double diff = Double.parseDouble(height.replaceAll("px", "")) - getOffsetHeight();
				int currentRow = getRow();
				DOM.setElementAttribute(VkGrid.this.getRowFormatter().getElement(currentRow), "height"
					, Double.parseDouble(DOM.getElementAttribute(VkGrid.this.getRowFormatter().getElement(currentRow)
						, "height").replaceAll("px", "")) + diff + "px");
			}
		}
		public void onLoad()
		{
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
		public String getWidgetName()
		{
			return VkFlexTableAbsolutePanel.NAME;
		}
		@Override
		public boolean showMenu() {
			return !isSelectionEnabled;
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
		public List<String> getOperationsList(Widget invokingWidget)
		{
			List<String> operationsList = VkDesignerUtil.getEngine().getOperationsList(invokingWidget);
			operationsList.remove(IEngine.REMOVE);
			operationsList.remove(IEngine.CUT);
			return operationsList;
		}
	}
	
	public VkGrid()
	{
		if(!VkDesignerUtil.getEngineMap().containsKey(VkFlexTableAbsolutePanel.NAME))
			VkDesignerUtil.getEngineMap().put(VkFlexTableAbsolutePanel.NAME, new VkFlexTableAbsolutePanelEngine());
		if(VkDesignerUtil.isDesignerMode)
			showAddTextAttributeDialog();
		super.setHeight("100px");
		super.setWidth("100px");
		DOM.setStyleAttribute(getElement(), "tableLayout", "fixed");
	}
	@Override
	public void onLoad()
	{
		setCellPadding(0);
		setCellSpacing(0);
		DOM.setElementAttribute(getElement(), "cellspacing", "0");
		this.columnFormatter = new VkFlexTableColumnFormatter();
		setColumnFormatter(columnFormatter);
	}
	@Override
	public void setWidth(String width)
	{
		if(getRowCount() != 0)
		{
			double percentage = (double)Integer.parseInt(width.replace("px", "")) / (double)getOffsetWidth();
			int columns = getCellCount(0);
			for(int i = 0; i < columns; i++)
				columnFormatter.setWidth(i, percentage * Double.parseDouble(DOM.getElementAttribute(VkGrid.this.columnFormatter.getElement(i)
						, "width").replaceAll("px", "")) + "px");
		}
	}
	@Override
	public void setHeight(final String height)
	{
		int rows = getRowCount();
		if(rows != 0)
		{
			double percentage = (double)Integer.parseInt(height.replace("px", "")) / (double)getOffsetHeight();
			for(int i = 0; i < rows; i++)
				DOM.setElementAttribute(getRowFormatter().getElement(i), "height"
					, percentage * Double.parseDouble(DOM.getElementAttribute(getRowFormatter().getElement(i)
							, "height").replaceAll("px", "")) + "px");
		}
	}
	private void showAddTextAttributeDialog() {
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
					int colCount = Integer.parseInt(columnsTextBox.getText().trim());
					VkGrid.this.resize(rowCount, colCount);
					for(int i = 0; i < rowCount; i++)
						for(int j = 0; j < colCount; j++)
							makeCell(i, j);
					origDialog.hide();
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
				origDialog.hide();
			}
		});
		origDialog.center();
	}
	public void makeCell(final int row, final int col)
	{
		VkFlexTableAbsolutePanel l2 = new VkFlexTableAbsolutePanel();
		DOM.setStyleAttribute(l2.getElement(), "border", "solid 1px gray");
		VkDesignerUtil.getEngine().prepareWidget(l2, VkDesignerUtil.getEngineMap().get(VkAbsolutePanel.NAME));
		boolean isVkDesignerMode = VkDesignerUtil.isDesignerMode;
		VkDesignerUtil.isDesignerMode = false;//important as call routes to inserRow here instead of super's
		setWidget(row, col, l2);
		VkDesignerUtil.isDesignerMode = isVkDesignerMode;
		DOM.setElementAttribute(getCellFormatter().getElement(row, col), "col", Integer.toString(col));
		DOM.setElementAttribute(VkGrid.this.getRowFormatter().getElement(row), "height", "40px");
		DOM.setStyleAttribute(VkGrid.this.getCellFormatter().getElement(row, col), "position", "relative");
		columnFormatter.setWidth(col, "80px");
		prepareForSelection(getCellFormatter(), row, col);
	}
	private native void prepareForSelection(CellFormatter flexCellFormatter, int i, int j) /*-{
		var element = flexCellFormatter.@com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter::getElement(II)(i, j);
		var t = this;
		t.@com.vk.gwt.designer.client.widgets.VkGrid::getElement()().onclick = function(e){
			e = e || $wnd.event;
			if(e.button == 0 && t.@com.vk.gwt.designer.client.widgets.VkGrid::isSelectionEnabled)
			{
				if(!t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection)
				{
					t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection = true;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::clearAllStyles()();
					var source = e.srcElement || e.target;
					while(source.tagName != 'TD')
						source = source.parentNode;
					source.className = 'vkflextable-cell-selected first';
				}
				else
				{
					t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection = false;
					t.@com.vk.gwt.designer.client.widgets.VkGrid::firstSelection = false;
				}
			}
			if(e.cancelBubble)
				e.cancelBubble = true;
			else
				e.stopPropagation();
		}
		element.onmouseover = function(){
			if(t.@com.vk.gwt.designer.client.widgets.VkGrid::startSelection)
			{
				t.@com.vk.gwt.designer.client.widgets.VkGrid::clearSelectedCells()();
				if(element.className.indexOf('first') == -1)
					element.className = 'vkflextable-cell-selected';
				t.@com.vk.gwt.designer.client.widgets.VkGrid::selectAll()();
			}
		}
	}-*/;
	@SuppressWarnings("unused")//used in native function
	private void clearSelectedCells()
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
	private void clearAllStyles()
	{
		int rowCount = getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = getCellCount(i);
			for(int j = 0; j < colCount; j++)
				getCellFormatter().setStyleName(i, j, "");
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
					row1 = i;
					col1 = Integer.parseInt(DOM.getElementAttribute(getCellFormatter().getElement(i, j), "col"));
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
		clickHandlerRegistration = null;
		clickJs = js.trim();
		if(!clickJs.isEmpty())
		{
			clickHandlerRegistration = addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					Cell cell = getCellForEvent(event);
					setUpCellCallingEvent(cell.getRowIndex(), cell.getCellIndex());
					VkDesignerUtil.executeEvent(clickJs, event, false);
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
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
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
	public void insertCell(int beforeRow, int beforeColumn)
	{
		super.insertCell(beforeRow, beforeColumn);
		if(VkDesignerUtil.isDesignerMode)
			makeCell(beforeRow, beforeColumn);
	}
	@Override
	@Export
	public int insertRow(int beforeRow) {
	    int rowNum =  super.insertRow(beforeRow);
	    if(VkDesignerUtil.isDesignerMode)
	    {
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
	@Override
	@Export
	public void removeCell(int row, int col) {
		 super.removeCell(row, col);
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
		VkDesignerUtil.getEngine().addWidget(VkDesignerUtil.getEngine().getWidget(VkHTML.NAME), panel);
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
		VkDesignerUtil.getEngine().addWidget(VkDesignerUtil.getEngine().getWidget(VkLabel.NAME), panel);
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