package com.vk.gwt.designer.client.engine;

import java.util.List;

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
import com.vk.gwt.designer.client.widgets.VkGrid;

public class VkGridEngine extends VkAbstractWidgetEngine<VkGrid> {
	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	@Override
	public VkGrid getWidget() {
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
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void addNewColumn(VkGrid table) {
		table.addColumn();
	}
	private void addNewRow(VkGrid table) {
		table.insertRow(table.getRowCount());
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
				}
			}
		});
	}
	private void addCellSpacing(final VkGrid table) {
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
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(DOM.getElementAttribute(((Widget)widget).getElement(), "style")).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkGrid grid =  (VkGrid)widget;
		buffer.append(",rows:").append(grid.getRowCount()).append(",cols:").append(grid.getColumnCount()).append(",cells:[");
		int rowCount = grid.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = grid.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)grid.getWidget(i,j)).getWidgetName()).serialize((IVkWidget) grid.getWidget(i,j)))
				.append(",");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkGrid grid =  (VkGrid)parent;
		JSONArray cellsArray = jsonObj.get("cells").isArray();
		int rows = (int)jsonObj.get("rows").isNumber().doubleValue();
		int cols = (int)jsonObj.get("cols").isNumber().doubleValue();
		grid.resize(rows, cols);
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < cols; j++)
			{
				grid.makeCell(i, j, grid.getRowCount());
				Widget widget = grid.getWidget(i, j);
				addAttributes(cellsArray.get(i).isObject(), widget);
				VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(cellsArray.get(i * rows + j).isObject(), widget);
			}
		}
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		VkGrid sourceTable = (VkGrid)widgetSource;
		VkGrid targetTable = (VkGrid)widgetSource;
		int rowCount = sourceTable.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int columnCount = sourceTable.getCellCount(i);
			for(int j = 0; j < columnCount; j++)
				targetTable.makeCell(i, j , rowCount);
		}
	}
}
