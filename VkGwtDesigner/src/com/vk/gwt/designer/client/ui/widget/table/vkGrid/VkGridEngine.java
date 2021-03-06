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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.UndoHelper;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkGridEngine extends VkAbstractWidgetEngine<VkGrid> {

	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	private static final String REMOVE_ROW = "Remove Selected Row";
	private static final String REMOVE_COLUMN = "Remove Selected Column";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	@Override
	public VkGrid getWidget() {
		VkGrid widget = new VkGrid();
		init(widget);
		DOM.setStyleAttribute(widget.getElement(), "width", "100px");
		DOM.setStyleAttribute(widget.getElement(), "height", "20px");
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		list.add(ADD_NEW_ROW);
		list.add(ADD_NEW_COLUMN);
		list.add(REMOVE_ROW);
		list.add(REMOVE_COLUMN);
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
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void removeSelectedColumns(final VkGrid table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		Collections.sort(cells, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o2[1] - o1[1];
			}
		});
		for(Iterator<int[]> i = cells.iterator(); i.hasNext();) {
			int[] last = i.next();
			while(i.hasNext()) {
				int[] temp = i.next();				
				if(temp[1] == last[1])
					i.remove();
				else
					last = temp;
			}
			break;
		}
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			VkGrid vkGrid = new VkGrid();
			vkGrid.resize(table.getRowCount(), table.getColumnCount());
			final VkGrid oldTable =(VkGrid) this.deepClone(table, vkGrid);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					for(int[] i : cells)
						table.removeColumn(i[1]);
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
				}}, new Command(){
				@Override
				public void execute() {
					restoreOldTable(table, oldTable);
				}});
		}
		clearAndStopCellSelection(table);
	}
	private void restoreOldTable(VkGrid table, VkGrid oldTable) {
		table.resize(0, 0);
		for(int i = 0, rowCount = oldTable.getRowCount(); i < rowCount; i++)
			table.insertRow(i);
		for(int i = 0, colCount = oldTable.getColumnCount(); i < colCount; i++)
			table.insertColumn(i);
		for(int i = 0, rowCount = oldTable.getRowCount(); i < rowCount; i++) {
			for(int j = 0, colCount = oldTable.getCellCount(i); j < colCount; j++) {
				if(oldTable.getWidget(i, j) != null) {
					Widget widget = oldTable.getWidget(i, j);
					table.setWidget(i, j, widget);
				}
			}
		}
		VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
	}
	private ArrayList<int[]> getSelectedCells(VkGrid table) {
		ArrayList<int[]> cells = new ArrayList<int[]>();
		for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++)
			for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++)
				if(table.getCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
					cells.add(new int[]{i, j});
		return cells;
	}
	private void removeSelectedRows(final VkGrid table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		Collections.sort(cells, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o2[0] - o1[0];
			}
		});
		for(Iterator<int[]> i = cells.iterator(); i.hasNext();) {
			int[] last = i.next();
			while(i.hasNext()) {
				int[] temp = i.next();				
				if(temp[0] == last[0])
					i.remove();
				else
					last = temp;
			}
			break;
		}
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			VkGrid vkGrid = new VkGrid();
			vkGrid.resize(table.getRowCount(), table.getColumnCount());
			final VkGrid oldTable =(VkGrid) this.deepClone(table, vkGrid);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					for(int[] i : cells)
						table.removeRow(i[0]);
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
				}}, new Command(){
				@Override
				public void execute() {
					restoreOldTable(table, oldTable);
				}});
		}
		clearAndStopCellSelection(table);
	}
	
	private void addNewColumn(final VkGrid table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			VkGrid vkGrid = new VkGrid();
			vkGrid.resize(table.getRowCount(), table.getColumnCount());
			final VkGrid oldTable =(VkGrid) this.deepClone(table, vkGrid);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					int columnNumber = 0;
					table.resizeColumns(table.getColumnCount() + 1);
					columnNumber = cells.get(0)[1];
					table.removeColumn(table.getColumnCount() - 1);
					table.insertColumn(columnNumber + 1);
					for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++)
						for(int j = 0, colCount = table.getColumnCount(); j < colCount; j++)
							DOM.setElementAttribute(table.getCellFormatter().getElement(i, j), "col", Integer.toString(j));
					
					for(int i = columnNumber + 2, len = table.getColumnCount(); i < len; i++)
						table.getColumnFormatter().setWidth(i, DOM.getElementAttribute(table.getColumnFormatter().getElement(i - 1), "width"));
				}}, new Command(){
				@Override
				public void execute() {
					restoreOldTable(table, oldTable);
				}});
			clearAndStopCellSelection(table);
			VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
		}
	}
	private void addNewRow(final VkGrid table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			VkGrid vkGrid = new VkGrid();
			vkGrid.resize(table.getRowCount(), table.getColumnCount());
			final VkGrid oldTable =(VkGrid) this.deepClone(table, vkGrid);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					table.insertRow(cells.get(0)[0] + 1);
				}}, new Command(){
				@Override
				public void execute() {
					restoreOldTable(table, oldTable);
				}});
			clearAndStopCellSelection(table);
			VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
		}
	}
	private void addCellPadding(final VkGrid table) {
		TextBox tb = new TextBox();
		tb.setWidth("300px");
		VkDesignerUtil.showAddTextAttributeDialog("Add Cell Spacing", tb, new IDialogCallback() {
			@Override
			public void save(String text) {
				int padding = 0;
				final int priorPadding = table.getCellPadding();
				try{
					padding = Integer.parseInt(text.trim());
					final int finalPadding = padding;
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							table.setCellPadding(finalPadding);
							VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
						}}, new Command(){
						@Override
						public void execute() {
							table.setCellPadding(priorPadding);
							VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
						}});
				} catch(NumberFormatException e) {
					Window.alert("Cell Padding cannot be non-numeric");
				}
			}
		});
	}
	private void addCellSpacing(final VkGrid table) {
		TextBox tb = new TextBox();
		tb.setWidth("300px");
		VkDesignerUtil.showAddTextAttributeDialog("Add Cell Spacing", tb, new IDialogCallback() {
			@Override
			public void save(String text) {
				int spacing = 0;
				final int priorSpacing = table.getCellSpacing();
				try{
					spacing = Integer.parseInt(text.trim());
					final int finalSpacing = spacing;
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							table.setCellSpacing(finalSpacing);
							VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
						}}, new Command(){
						@Override
						public void execute() {
							table.setCellSpacing(priorSpacing);
							VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
						}});
				} catch(NumberFormatException e)	{
					Window.alert("Cell Spacing cannot be non-numeric");
				}
			}
		});
	}
	private void clearAndStopCellSelection(VkGrid table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				table.getCellFormatter().setStyleName(i, j, "");
		}
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
				.append(",child:").append(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap()
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
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(child, widget);
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
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		((VkGrid)targetWidget).resize(((VkGrid)sourceWidget).getRowCount(), ((VkGrid)sourceWidget).getColumnCount());
		return super.deepClone(sourceWidget, targetWidget);
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		VkGrid sourceTable = (VkGrid)widgetSource;
		VkGrid targetTable = (VkGrid)widgetTarget;
		int rowCount = sourceTable.getRowCount();
		VkStateHelper.getInstance().setDesignerMode(true);
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0, colNum = 0, columnCount = sourceTable.getCellCount(i); j < columnCount; j++, colNum++) {
				targetTable.makeCell(i, j);
				Panel sourcePanel = (Panel) sourceTable.getWidget(i, j);
				Panel targetPanel = (Panel) targetTable.getWidget(i, j);
				for(Widget k : sourcePanel) {
					IVkWidget currentWidget = ((IVkWidget) k);
					Widget newWidget = VkStateHelper.getInstance().getEngine().getWidget(currentWidget.getWidgetName());
					VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(currentWidget.getWidgetName()).deepClone((Widget) currentWidget, newWidget);
					targetPanel.add(newWidget);
				}
			}
			DOM.setElementAttribute(targetTable.getRowFormatter().getElement(i), "height", DOM.getElementAttribute(sourceTable.getRowFormatter().getElement(i), "height"));
		}
		VkStateHelper.getInstance().setDesignerMode(false);//because it would be false when this would be called from VkAbstractWidgetEngine#deepClone
		for(int i = 0, colCount = sourceTable.getColumnCount(); i < colCount; i++)
			targetTable.getColumnFormatter().setWidth(i, DOM.getElementAttribute(sourceTable.getColumnFormatter().getElement(i), "width"));
	}
}