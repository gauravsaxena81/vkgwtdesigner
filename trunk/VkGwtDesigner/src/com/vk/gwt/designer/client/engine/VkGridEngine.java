package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkGrid;

public class VkGridEngine extends VkAbstractWidgetEngine<Grid> {

	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	private static final String REMOVE_ROW = "Remove Selected Row";
	private static final String REMOVE_COLUMN = "Remove Selected Column";
	private static final String CLEAR_SELECTION = "Clear And Stop Cell Selection";
	private static final String START_SELECTION = "Start Cell Selection";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	@Override
	public Grid getWidget() {
		VkGrid widget = new VkGrid();
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
		list.add(ADD_CELLSPACING);
		list.add(ADD_CELLPADDING);
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkGrid table = (VkGrid)invokingWidget;
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
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void removeSelectedColumns(VkGrid table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(table.getCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					for(int l = 0; l < rowCount; l++)
						table.removeCell(l, j);
					table.getColumnFormatter().getElement(j).removeFromParent();
					j--;
					colCount--;
				}
			}
		}
		clearAndStopCellSelection(table);
	}
	private void removeSelectedRows(VkGrid table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
			{
				if(table.getCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
				{
					table.removeRow(i);
					i--;
					rowCount--;
					break;
				}
			}
		}
		clearAndStopCellSelection(table);
	}
	
	private void addNewColumn(VkGrid table) {
		int rowCount = table.getRowCount();
		int cols = table.getColumnCount();
		boolean designerMode = VkDesignerUtil.isDesignerMode;
		VkDesignerUtil.isDesignerMode = false;
		table.resizeColumns(cols + 1);
		VkDesignerUtil.isDesignerMode = designerMode;
		for(int i = 0; i < rowCount; i++)
			table.makeCell(i, cols);
	}
	private void addNewRow(VkGrid table) {
		int rowCount = table.getRowCount();
		table.insertRow(rowCount);
	}
	private void addCellPadding(final VkGrid table) {
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
					addCellPadding(table);
				}
			}
		});
	}
	private void addCellSpacing(final VkGrid table) {
		TextBox tb = new TextBox();
		tb.setWidth("300px");
		VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add Cell Spacing", tb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					table.setCellSpacing(Integer.parseInt(text.trim()));
				}catch(NumberFormatException e)
				{
					Window.alert("Cell Spacing cannot be non-numeric");
					addCellSpacing(table);
				}
			}
		});
	}
	private void startCellSelection(VkGrid table) {
		table.setSelectionEnabled(true);
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				table.getCellFormatter().setStyleName(i, j, "");
		}
		Window.alert("Cell Selection is enabled. Click on a cell to start cell selecting and click again to stop");
	}
	private void clearAndStopCellSelection(VkGrid table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				table.getCellFormatter().setStyleName(i, j, "");
		}
		table.setSelectionEnabled(false);
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget))
			.append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkGrid table =  (VkGrid)widget;
		buffer.append(",widths:[").append(getColumnWidths(table)).append("]")
		.append(",heights:[").append(getRowHeights(table)).append("]");
		buffer.append(",rows:").append(table.getRowCount()).append(",cols:").append(table.getColumnCount())
		.append(",cells:[");
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				buffer.append("{row:").append(i).append(",col:").append(j)
				.append(",child:").append(VkDesignerUtil.getEngineMap()
						.get(((IVkWidget)table.getWidget(i,j)).getWidgetName())
							.serialize((IVkWidget) table.getWidget(i,j)))
				.append("},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	private StringBuffer getRowHeights(VkGrid flexTable) {
		int rowCount = flexTable.getRowCount();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < rowCount; i++)
			buf.append(DOM.getElementAttribute(flexTable.getRowFormatter().getElement(i), "height").replaceAll("px", ""))
				.append(",");
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	private StringBuffer getColumnWidths(VkGrid table) {
		int numberOfCols = table.getColumnCount();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < numberOfCols; i++)
			buf.append(DOM.getElementAttribute(table.getColumnFormatter().getElement(i), "width").replaceAll("px", ""))
				.append(",");
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkGrid table =  (VkGrid)parent;
		addAttributes(jsonObj, parent);
		table.resize((int)jsonObj.get("rows").isNumber().doubleValue()
				, (int)jsonObj.get("cols").isNumber().doubleValue());
		JSONArray cellsArray = jsonObj.get("cells").isArray();
		for(int i = 0; i < cellsArray.size(); i++)
		{
			int row = (int)cellsArray.get(i).isObject().get("row").isNumber().doubleValue();
			int col = (int)cellsArray.get(i).isObject().get("col").isNumber().doubleValue();
			table.makeCell(row, col);
			Widget widget = table.getWidget(row, col);
			JSONObject child = cellsArray.get(i).isObject().get("child").isObject();
			//addAttributes(child, widget);
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(child, widget);
		}
		JSONArray widthArray = jsonObj.get("widths").isArray();
		for(int i = 0; i < widthArray.size(); i++)
			table.getColumnFormatter().setWidth(i, widthArray.get(i).isNumber().doubleValue() + "px");
		JSONArray heightArray = jsonObj.get("heights").isArray();
		for(int i = 0; i < heightArray.size(); i++)
			DOM.setElementAttribute(table.getRowFormatter().getElement(i), "height"
					, heightArray.get(i).isNumber().doubleValue() + "px");
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		VkGrid sourceTable = (VkGrid)widgetSource;
		VkGrid targetTable = (VkGrid)widgetTarget;
		int rowCount = sourceTable.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int columnCount = sourceTable.getCellCount(i);
			for(int j = 0, colNum = 0; j < columnCount; j++)
			{
				targetTable.makeCell(i, j);
				colNum++;
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
