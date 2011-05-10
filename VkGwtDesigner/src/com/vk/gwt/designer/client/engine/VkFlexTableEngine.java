package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkFlexTable;

public class VkFlexTableEngine extends VkAbstractWidgetEngine<VkFlexTable> {

	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	private static final String REMOVE_ROW = "Remove Selected Row";
	private static final String REMOVE_COLUMN = "Remove Selected Column";
	private static final String CLEAR_SELECTION = "Clear And Stop Cell Selection";
	private static final String START_SELECTION = "Start Cell Selection";
	private static final String ADD_SPAN = "Group Cells";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	@Override
	public VkFlexTable getWidget() {
		VkFlexTable widget = new VkFlexTable();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		list.add(ADD_NEW_ROW);
		list.add(ADD_NEW_COLUMN);
		list.add(REMOVE_ROW);
		list.add(REMOVE_COLUMN);
		list.add(CLEAR_SELECTION);
		list.add(START_SELECTION);
		list.add(ADD_SPAN);
		list.add(ADD_CELLSPACING);
		list.add(ADD_CELLPADDING);
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkFlexTable table = (VkFlexTable)invokingWidget;
		if(attributeName.equals(ADD_NEW_ROW))
			addNewRow(table);
		else if(attributeName.equals(ADD_NEW_COLUMN))
			addNewColumn(table);
		else if(attributeName.equals(REMOVE_COLUMN))
			removeSelectedColumns(table);
		else if(attributeName.equals(REMOVE_ROW))
			removeSelectedRows(table);
		else if(attributeName.equals(CLEAR_SELECTION))
			clearAndStopCellSelection(table);
		else if(attributeName.equals(START_SELECTION))
			startCellSelection(table);
		else if (attributeName.equals(ADD_SPAN))
			addSpan(table);
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void removeSelectedColumns(VkFlexTable table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					int loopLimit = table.getColSpan(i, j) + j;
					for(int l = j; l < loopLimit; l++)
					{
						removeCol(table, i, l);
						l--;
						loopLimit--;
						colCount--;
						table.setInitialColumnCount(table.getInitialColumnCount() - 1);
					}
					j--;
				}
			}
		}
		clearAndStopCellSelection(table);
	}
	private void removeCol(VkFlexTable table, int startRow, int startCol) {
		int origCol = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(startRow, startCol)
				, "col"));
		int totalRowCount = table.getRowCount();
		for(int i = 0; i < totalRowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				int col = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
				if(col <= origCol && col + table.getColSpan(i, j) - 1 >= origCol)
				{
					if(table.getColSpan(i, j) == 1)
					{
						table.removeCell(i, j--);
						colCount--;
					}
					else //if(col + table.getColSpan(i, j) >= Integer.parseInt(origCol))
						table.setColSpan(i, j, table.getColSpan(i, j) - 1);
				}
			}
		}
		for(int i = 0; i < totalRowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				int col = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
				if(origCol < col)
					DOM.setElementAttribute(table.getFlexCellFormatter().getElement(i, j)
						, "col", Integer.toString(col - 1));
			}
		}
		table.getColumnFormatter().getElement(startCol).removeFromParent();
	}
	private void removeSelectedRows(VkFlexTable table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					int loopLimit = table.getRowSpan(i,j) + i;
					for(int k = i; k < loopLimit; k++)
					{
						removeRow(table, k);
						rowCount--;
						k--;
						loopLimit--;
					}
					i--;
					break;
				}
			}
		}
		clearAndStopCellSelection(table);
	}
	private void removeRow(VkFlexTable table, int row) {
		int colCount = table.getCellCount(row);
		for(int i = 0; i < colCount; i++)
		{
			if(table.getRowSpan(row, i) == 1)
				table.removeCell(row, i);
			else
				//table.setRowSpan(row, i, table.getRowSpan(row, i) - 1);
			{
				table.setRowSpan(row, i, table.getRowSpan(row, i) - 1);
				int col = Integer.parseInt(
					DOM.getElementAttribute(table.getFlexCellFormatter().getElement(row, i), "col"));
				Element td = table.getCellElement(row, i);
				td.removeFromParent();
				int columnCount = table.getCellCount(i + 1);
				com.google.gwt.user.client.Element refNode = null;
				for(int j = 0; j < columnCount; j++)
				{
					if(Integer.parseInt(
						DOM.getElementAttribute(table.getFlexCellFormatter().getElement(row + 1, j), "col")) 
						> col)
					{
						refNode = table.getFlexCellFormatter().getElement(row + 1, j);
						break;
					}
				}
				//if(i - 1 > 0)
					//table.getRowFormatter().getElement(row + 1).insertAfter(td, refNode);
				//else
					table.getRowFormatter().getElement(row + 1).insertBefore(td, refNode);
			}
			i--;
			colCount--;
		}
		table.removeRow(row);
		for(int i = 0; i < row; i++)
		{
			int columnCount = table.getCellCount(i);
			for(int j = 0; j < columnCount; j++)
				if(i + table.getRowSpan(i, j) - 1 >= row)
					table.setRowSpan(i, j, table.getRowSpan(i, j) - 1);
		}
	}
	private void addNewColumn(VkFlexTable table) {
		int rowCount = table.getInitialRowCount();
		table.setInitialColumnCount(table.getInitialColumnCount() + 1);
		for(int i = 0; i < rowCount; i++)
			table.addCell(i);
	}
	private void addNewRow(VkFlexTable table) {
		int rowCount = table.getRowCount();
		table.insertRow(rowCount);
		table.setInitialRowCount(table.getInitialRowCount() + 1);
	}
	private void addSpan(final VkFlexTable table) {
		boolean isFirstCellSpared = false;
		int lastCellRow = 0;
		int lastCellCol = 0;
		int firstCellRow = -1;
		int firstCellCol = -1;
		int rowCount = table.getRowCount();
		int firstSelectionRow = -1;
		boolean selectedCellFound = false;
		for(int i = 0, l = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0, k = 0;j < colCount; j++, k++)
			{
				if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					selectedCellFound = true;
					lastCellRow = i;
					if(firstSelectionRow == -1)
						firstSelectionRow = i;
					if(firstSelectionRow == i)
						lastCellCol += table.getFlexCellFormatter().getColSpan(i, j);

					if(isFirstCellSpared)
					{
						table.removeCell(i, j);//do not delete upper left most cell
						colCount--;
						j--;
					}
					else
					{
						isFirstCellSpared = true;
						firstCellRow = i;
						firstCellCol = j;
					}
				}
			}
			if(selectedCellFound)
				l++;
		}
		if(!selectedCellFound)
			Window.alert("Please select cells before applying cell operations");
		table.getFlexCellFormatter().setRowSpan(firstCellRow, firstCellCol, lastCellRow - firstCellRow + 1);
		table.getFlexCellFormatter().setColSpan(firstCellRow, firstCellCol, lastCellCol);
		clearAndStopCellSelection(table);		
	}
	private void addCellPadding(final VkFlexTable table) {
		TextBox tb = new TextBox();
		tb.setWidth("100px");
		VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add Cell Spacing", tb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					table.setCellPadding(Integer.parseInt(text.trim()));
				}catch(NumberFormatException e)
				{
					Window.alert("Cell Padding cannot be non-numeric");
				}
			}
		});
	}
	private void addCellSpacing(final VkFlexTable table) {
		TextBox tb = new TextBox();
		tb.setWidth("100px");
		VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add Cell Spacing", tb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					table.setCellSpacing(Integer.parseInt(text.trim()));
				}catch(NumberFormatException e)
				{
					Window.alert("Cell Spacing cannot be non-numeric");
				}
			}
		});
	}
	private void startCellSelection(VkFlexTable table) {
		table.setSelectionEnabled(true);
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				table.getFlexCellFormatter().setStyleName(i, j, "");
		}
		Window.alert("Cell Selection is enabled. Click on a cell to start cell selecting and click again to stop");
	}
	private void clearAndStopCellSelection(VkFlexTable table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				table.getFlexCellFormatter().setStyleName(i, j, "");
		}
		table.setSelectionEnabled(false);
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style"))
			.append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkFlexTable flexTable =  (VkFlexTable)widget;
		buffer.append(",widths:[").append(getColumnWidths(flexTable)).append("]")
		.append(",heights:[").append(getRowHeights(flexTable)).append("]");
		buffer.append(",cells:[");
		int rowCount = flexTable.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = flexTable.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				buffer.append("{row:").append(i).append(",col:").append(j).append(",rowSpan:")
					.append(flexTable.getRowSpan(i, j))
				.append(",colSpan:").append(flexTable.getColSpan(i, j))
				.append(",child:").append(VkDesignerUtil.getEngineMap()
						.get(((IVkWidget)flexTable.getWidget(i,j)).getWidgetName())
							.serialize((IVkWidget) flexTable.getWidget(i,j)))
				.append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	private StringBuffer getRowHeights(VkFlexTable flexTable) {
		int rowCount = flexTable.getRowCount();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < rowCount; i++)
			buf.append(DOM.getElementAttribute(flexTable.getRowFormatter().getElement(i), "height"));
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	private StringBuffer getColumnWidths(VkFlexTable flexTable) {
		int numberOfCols = 0;
		int rowCount = flexTable.getRowCount();
		for(int i = 0; i < rowCount; i++)
			numberOfCols = Math.max(numberOfCols, flexTable.getCellCount(i));
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < numberOfCols; i++)
			buf.append(DOM.getElementAttribute(flexTable.getColumnFormatter().getElement(i), "width"))
				.append(",");
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkFlexTable flexTable =  (VkFlexTable)parent;
		JSONArray cellsArray = jsonObj.get("cells").isArray();
		for(int i = 0, colNum = 0; i < cellsArray.size(); i++)
		{
			int row = (int)cellsArray.get(i).isObject().get("row").isNumber().doubleValue();
			int col = (int)cellsArray.get(i).isObject().get("col").isNumber().doubleValue();
			if(row >= flexTable.getRowCount())
				flexTable.insertRow(flexTable.getRowCount());
			int colSpan = (int)cellsArray.get(i).isObject().get("colSpan").isNumber().doubleValue();
			flexTable.makeCell(row, col, flexTable.getRowCount(), colNum);
			colNum += colSpan;
			Widget widget = flexTable.getWidget(row, flexTable.getCellCount(row) - 1);
			flexTable.setRowSpan(row, col, (int)cellsArray.get(i).isObject().get("rowSpan").isNumber()
					.doubleValue());
			flexTable.setColSpan(row, col, colSpan);
			addAttributes(cellsArray.get(i).isObject().get("child").isObject(), widget);
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName())
				.buildWidget(cellsArray.get(i).isObject().get("child").isObject(), widget);
		}
		JSONArray widthArray = jsonObj.get("widths").isArray();
		for(int i = 0; i < widthArray.size(); i++)
			flexTable.getColumnFormatter().setWidth(i, widthArray.get(i).isNumber().doubleValue() + "px");
		JSONArray heightArray = jsonObj.get("heights").isArray();
		for(int i = 0; i < heightArray.size(); i++)
			DOM.setElementAttribute(flexTable.getRowFormatter().getElement(i), "height"
					, heightArray.get(i).isNumber().doubleValue() + "px");
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		VkFlexTable sourceTable = (VkFlexTable)widgetSource;
		VkFlexTable targetTable = (VkFlexTable)widgetTarget;
		int rowCount = sourceTable.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int columnCount = sourceTable.getCellCount(i);
			for(int j = 0, colNum = 0; j < columnCount; j++)
			{
				targetTable.makeCell(i, j , rowCount, colNum);
				int colspan = sourceTable.getColSpan(i, j);
				colNum += colspan;
				targetTable.setRowSpan(i, j, sourceTable.getRowSpan(i, j));
				targetTable.setColSpan(i, j, colspan);
			}
			DOM.setElementAttribute(targetTable.getRowFormatter().getElement(i), "height"
					, DOM.getElementAttribute(sourceTable.getRowFormatter().getElement(i), "height"));
		}
		int numberOfCols = 0;
		for(int i = 0; i < rowCount; i++)
			numberOfCols = Math.max(numberOfCols, sourceTable.getCellCount(i));
		for(int i = 0; i < numberOfCols; i++)
			targetTable.getColumnFormatter().setWidth(i
					, DOM.getElementAttribute(sourceTable.getColumnFormatter().getElement(i), "width"));
	}
}
