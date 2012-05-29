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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Element;
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

public class VkFlexTableEngine extends VkAbstractWidgetEngine<VkFlexTable> {

	private static final String ADD_NEW_ROW = "Insert Row";
	private static final String ADD_NEW_COLUMN = "Insert Column";
	private static final String REMOVE_ROW = "Remove Selected Row";
	private static final String REMOVE_COLUMN = "Remove Selected Column";
	private static final String ADD_SPAN = "Group Cells";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	private static final String SPLIT_CELLS = "Split Cells";
	@Override
	public VkFlexTable getWidget() {
		VkFlexTable widget = new VkFlexTable();
		init(widget);
		DOM.setStyleAttribute(widget.getElement(), "width", "100px");
		DOM.setStyleAttribute(widget.getElement(), "height", "20px");
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget) {
		List<String> list = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		list.add(ADD_NEW_ROW);
		list.add(ADD_NEW_COLUMN);
		list.add(REMOVE_ROW);
		list.add(REMOVE_COLUMN);
		list.add(ADD_SPAN);
		list.add(ADD_CELLSPACING);
		list.add(ADD_CELLPADDING);
		list.add(SPLIT_CELLS);
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
		else if (attributeName.equals(ADD_SPAN))
			addSpan(table);
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		else if (attributeName.equals(SPLIT_CELLS))
			splitCells(table);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void splitCells(final VkFlexTable table) {
		final VkFlexTable oldTable = new VkFlexTable();
		this.deepClone(table, oldTable);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				A: for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
					for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++) {
						if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1) {
							int rowSpan = table.getRowSpan(i, j);
							int colSpan = table.getColSpan(i, j);
							table.removeCell(i, j);
							for(int k = i, rows = i + rowSpan; k < rows; k++)
								for(int l = j, cols = j + colSpan; l < cols; l++)
									table.insertCell(k, l);
							break A;
						}
					}
				}
			}}, new Command(){
					@Override
					public void execute() {
						restoreOldTable(table, oldTable);
					}});
	}
	private void restoreOldTable(VkFlexTable table, VkFlexTable oldTable) {
		table.clear(true);
		table.removeAllRows();
		for(int i = 0, rowCount = oldTable.getRowCount(); i < rowCount; i++) {
			table.insertRow(i);
			for(int j = 0, colCount = oldTable.getCellCount(i), maxCols = table.getCellCount(i); j < colCount; j++) {
				if(j == maxCols)
					table.insertCell(i, j);
				if(oldTable.getWidget(i, j) != null) {
					Widget widget = oldTable.getWidget(i, j);
					table.setWidget(i, j, widget);
				}
				table.getFlexCellFormatter().setRowSpan(i, j, oldTable.getFlexCellFormatter().getRowSpan(i, j));
				table.getFlexCellFormatter().setColSpan(i, j, oldTable.getFlexCellFormatter().getColSpan(i, j));
			}
		}
		VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
	}
	private void removeSelectedColumns(final VkFlexTable table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		Collections.sort(cells, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[1] - o2[1];
			}
		});
		for(Iterator<int[]> i = cells.iterator(); i.hasNext();) {
			int[] next = i.next();
			while(i.hasNext() && i.next()[1] == next[1])
				i.remove();
		}
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			final VkFlexTable oldTable =(VkFlexTable) this.deepClone(table, new VkFlexTable());
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					int cellsExecuted = 0;
					A: for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
						for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++) {
							for(int[] k : cells) {
								if(Arrays.equals(k, new int[]{i, j})) {
									int loopLimit = table.getColSpan(i, j) + j;
									for(int l = j; l < loopLimit; l++) {
										removeCol(table, i, l);
										cellsExecuted++;
										l--;
										loopLimit--;
										colCount--;
										table.setInitialColumnCount(table.getInitialColumnCount() - 1);
										if(cellsExecuted == cells.size())
											break A;
									}
									j--;
									break;
								}
							}
						}
					}
				}}, new Command(){
						@Override
						public void execute() {
							restoreOldTable(table, oldTable);
						}});
		} 
	}
	private ArrayList<int[]> getSelectedCells(VkFlexTable table) {
		ArrayList<int[]> cells = new ArrayList<int[]>();
		for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++)
			for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++)
				if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1)
					cells.add(new int[]{i, j});
		return cells;
	}
	private void removeCol(VkFlexTable table, int startRow, int startCol) {
		int origCol = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(startRow, startCol), "col"));
		int totalRowCount = table.getRowCount();
		for(int i = 0; i < totalRowCount; i++) {
			for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++)	{
				int col = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
				if(col <= origCol && col + table.getColSpan(i, j) - 1 >= origCol) {
					if(table.getColSpan(i, j) == 1)	{
						table.removeCell(i, j--);
						colCount--;
					} else
						table.setColSpan(i, j, table.getColSpan(i, j) - 1);
				}
			}
		}
		for(int i = 0; i < totalRowCount; i++) {
			int colCount = table.getCellCount(i);
			for(int j = 0; j < colCount; j++) {
				int col = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
				if(origCol < col)
					DOM.setElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col", Integer.toString(col - 1));
			}
		}
		table.getColumnFormatter().getElement(startCol).removeFromParent();
	}
	private void removeSelectedRows(final VkFlexTable table) {
		final ArrayList<int[]> cells = getSelectedCells(table);
		Collections.sort(cells, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				return o1[0] - o2[0];
			}
		});
		for(Iterator<int[]> i = cells.iterator(); i.hasNext();) {
			int[] next = i.next();
			while(i.hasNext() && i.next()[0] == next[0])
				i.remove();
		}
		if(cells.isEmpty()) 
			Window.alert("Please select cells before applying cell operations");
		else {
			final VkFlexTable oldTable = new VkFlexTable();
			this.deepClone(table, oldTable);
			UndoHelper.getInstance().doCommand(new Command(){
				@Override
				public void execute() {
					int cellsExecuted = 0;
					A: for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++) {
						for(int j = 0, colCount = table.getCellCount(i); j < colCount; j++) {
							for(int[] k : cells) {
								if(Arrays.equals(k, new int[]{i, j})) {
									for(int l = i, loopLimit = table.getRowSpan(i,j) + i; l < loopLimit; l++) {
										removeRow(table, l);
										cellsExecuted++;
										rowCount--;
										l--;
										loopLimit--;
										if(cellsExecuted == cells.size())
											break A;
									}
									i--;
									break;
								}
							}
						}
					}
					table.clearCellSelection();
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
				}}, new Command(){
				@Override
				public void execute() {
					restoreOldTable(table, oldTable);
				}});
		}
	}
	private void removeRow(VkFlexTable table, int row) {
		int colCount = table.getCellCount(row);
		for(int i = 0; i < colCount; i++) {
			if(table.getRowSpan(row, i) == 1)
				table.removeCell(row, i);
			else {
				table.setRowSpan(row, i, table.getRowSpan(row, i) - 1);
				int col = Integer.parseInt(
					DOM.getElementAttribute(table.getFlexCellFormatter().getElement(row, i), "col"));
				Element td = table.getCellElement(row, i);
				td.removeFromParent();
				com.google.gwt.user.client.Element refNode = null;
				for(int j = 0, columnCount = table.getCellCount(i + 1); j < columnCount; j++) {
					if(Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(row + 1, j), "col")) > col) {
						refNode = table.getFlexCellFormatter().getElement(row + 1, j);
						break;
					}
				}
				table.getRowFormatter().getElement(row + 1).insertBefore(td, refNode);
			}
			i--;
			colCount--;
		}
		table.removeRow(row);
		for(int i = 0; i < row; i++) {
			int columnCount = table.getCellCount(i);
			for(int j = 0; j < columnCount; j++)
				if(i + table.getRowSpan(i, j) - 1 >= row)
					table.setRowSpan(i, j, table.getRowSpan(i, j) - 1);
		}
	}
	private void addNewColumn(final VkFlexTable table) {
		final VkFlexTable oldTable = new VkFlexTable();
		this.deepClone(table, oldTable);
		final ArrayList<int[]> cells = getSelectedCells(table);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				if(cells.isEmpty()) 
					for(int i = 0, rowCount = table.getRowCount(); i < rowCount; i++)
						table.insertCell(i, table.getCellCount(i));
				 else {
					insertColumn(Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(cells.get(0)[0], cells.get(0)[1]), "col")) + table.getColSpan(cells.get(0)[0], cells.get(0)[1]), table);
					table.clearCellSelection();
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
				}
			}}, new Command(){
			@Override
			public void execute() {
				restoreOldTable(table, oldTable);
			}});
	}
	private void insertColumn(int actualColumnNumber, VkFlexTable table) {
		table.setInitialColumnCount(table.getInitialColumnCount() + 1);
		if(actualColumnNumber == table.getInitialColumnCount() - 1){//adding cells in the last column
			for(int i = 0, rows = table.getInitialRowCount(); i < rows; i++)
				table.insertCell(i, table.getCellCount(i));
		} else { 
			for(int i = 0, actualRowCount = table.getRowCount(); i < actualRowCount; i++) {
				for(int j = 0, cols = table.getCellCount(i); j < cols; j++) {
					int actualColCount = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
					if(actualColCount >= actualColumnNumber) {//adding cells in the middle of the table but the added column doesn't form a part of a span
						table.insertCell(i, j);
						for(int k = j + 1; k < cols + 1; k++)
							DOM.setElementAttribute(table.getFlexCellFormatter().getElement(i, k), "col"
							, Integer.toString(Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, k), "col")) + 1));
						break;
					} else if(actualColCount + table.getColSpan(i, j) > actualColumnNumber) {//adding cells in the middle of the table but the added column forms a part of a span
						table.setColSpan(i, j, table.getColSpan(i, j) + 1);
						for(int r = i, rows = table.getRowSpan(i, j) + i; r < rows; r++)
							for(int c = 0, cols1 = table.getCellCount(r), newColumnNumber = 0; c < cols1; c++)
								if((newColumnNumber = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(r, c), "col"))) > actualColumnNumber)
									DOM.setElementAttribute(table.getFlexCellFormatter().getElement(r, c), "col", Integer.toString(newColumnNumber + 1));
						i += table.getRowSpan(i, j);
						//break;
					}
				}
			}
		}
		for(int i = actualColumnNumber + 1, len = table.getInitialColumnCount(); i < len; i++)
			table.getColumnFormatter().setWidth(i, DOM.getElementAttribute(table.getColumnFormatter().getElement(i - 1), "width"));
		table.getColumnFormatter().setWidth(actualColumnNumber, "80px");
	}
	private void addNewRow(final VkFlexTable table) {
		final VkFlexTable oldTable = new VkFlexTable();
		this.deepClone(table, oldTable);
		final ArrayList<int[]> cells = getSelectedCells(table);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				if(cells.isEmpty()) 
					table.insertRow(table.getRowCount());
				 else {
					table.insertRow(cells.get(0)[0] + Math.max(1, table.getRowSpan(cells.get(0)[0], cells.get(0)[1])));
					table.setInitialRowCount(table.getInitialRowCount() + 1);
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(table);
				}
				table.clearCellSelection();
			}}, new Command(){
			@Override
			public void execute() {
				restoreOldTable(table, oldTable);
			}});
	}
	private void addSpan(final VkFlexTable table) {
		final VkFlexTable oldTable = new VkFlexTable();
		this.deepClone(table, oldTable);
		final ArrayList<int[]> cells = getSelectedCells(table);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				int rowSpan = 0;
				int colspan = 0;
				int firstSelectionCol = -1;
				/*int firstSelectionRow = -1;
				boolean isFirstCellSpared = false;*/
				int firstCellRow = -1;
				int firstCellCol = -1;
				/*int rowCount = table.getRowCount();
				boolean selectedCellFound = false;*/
				
				if(cells.isEmpty()) {
					Window.alert("Please select cells before applying cell operations");
					return;
				}
				//firstSelectionRow = cells.get(0)[0];
				for(int[] i : cells) {
					if(i[0] == cells.get(0)[0])
						colspan += table.getColSpan(i[0], i[1]);
					else
						break;
				}
				firstSelectionCol = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(cells.get(0)[0], cells.get(0)[1]), "col"));
				for(int[] i : cells) {
					if(firstSelectionCol == Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i[0], i[1]), "col")))
						rowSpan += table.getRowSpan(i[0], i[1]);
				}
				firstCellRow = cells.get(0)[0];
				firstCellCol = cells.get(0)[1];
				Collections.sort(cells, new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {
						if(o1[0] == o2[0])
							return o2[1] - o1[1];
						else
							return o2[0] - o1[0];
					}
				});
				cells.remove(cells.size() - 1);
				for(int[] i : cells)
					table.removeCell(i[0], i[1]);
				/*for(int i = 0, l = 0; i < rowCount; i++) {
					for(int j = 0, k = 0, colCount = table.getCellCount(i);j < colCount; j++, k++) {
						if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1) {
							selectedCellFound = true;
							if(firstSelectionRow == -1)
								firstSelectionRow = i;
							if(firstSelectionRow == i)
								colspan += table.getColSpan(i, j);
							if(firstSelectionCol == -1)
								firstSelectionCol = Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col"));
							if(firstSelectionCol == Integer.parseInt(DOM.getElementAttribute(table.getFlexCellFormatter().getElement(i, j), "col")))
								rowSpan += table.getRowSpan(i, j);
							if(isFirstCellSpared) {
								table.removeCell(i, j);//do not delete upper left most cell
								colCount--;
								j--;
							} else {
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
					Window.alert("Please select cells before applying cell operations");*/
				table.getFlexCellFormatter().setRowSpan(firstCellRow, firstCellCol, rowSpan);
				table.getFlexCellFormatter().setColSpan(firstCellRow, firstCellCol, colspan);
				table.clearCellSelection();		
			}
			
		}, new Command(){
			@Override
			public void execute() {
				restoreOldTable(table, oldTable);
			}});
	}
	private void addCellPadding(final VkFlexTable table) {
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
	private void addCellSpacing(final VkFlexTable table) {
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
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget))
			.append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkFlexTable flexTable =  (VkFlexTable)widget;
		buffer.append(",widths:[").append(getColumnWidths(flexTable)).append("]")
		.append(",heights:[").append(getRowHeights(flexTable)).append("]");
		buffer.append(",cells:[");
		int rowCount = flexTable.getRowCount();
		for(int i = 0; i < rowCount; i++) {
			int colCount = flexTable.getCellCount(i);
			for(int j = 0; j < colCount; j++)
				buffer.append("{row:").append(i).append(",col:").append(j).append(",rowSpan:")
					.append(flexTable.getRowSpan(i, j))
				.append(",colSpan:").append(flexTable.getColSpan(i, j))
				.append(",child:").append(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap()
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
			buf.append(DOM.getElementAttribute(flexTable.getRowFormatter().getElement(i), "height").replaceAll("px", ""))
				.append(",");
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	private StringBuffer getColumnWidths(VkFlexTable flexTable) {
		int numberOfCols = 0;
		//int rowCount = flexTable.getRowCount();
		//for(int i = 0; i < rowCount; i++)
			numberOfCols = flexTable.getInitialColumnCount();
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < numberOfCols; i++)
			buf.append(DOM.getElementAttribute(flexTable.getColumnFormatter().getElement(i), "width").replaceAll("px", "")).append(",");
		if(buf.length() > 0)
			buf.deleteCharAt(buf.length() - 1);
		return buf;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkFlexTable flexTable =  (VkFlexTable)parent;
		addAttributes(jsonObj, parent);
		JSONArray cellsArray = jsonObj.get("cells").isArray();
		int lastRow = 0;
		for(int i = 0, colNum = 0; i < cellsArray.size(); i++) {
			int row = (int)cellsArray.get(i).isObject().get("row").isNumber().doubleValue();
			int col = (int)cellsArray.get(i).isObject().get("col").isNumber().doubleValue();
			if(row != lastRow) {
				colNum = 0;
				lastRow = row;
			}
			if(row >= flexTable.getRowCount())
				flexTable.insertRow(flexTable.getRowCount());
			int colSpan = (int)cellsArray.get(i).isObject().get("colSpan").isNumber().doubleValue();
			flexTable.makeCell(row, col, colNum);
			colNum += colSpan;
			Widget widget = flexTable.getWidget(row, flexTable.getCellCount(row) - 1);
			flexTable.setRowSpan(row, col, (int)cellsArray.get(i).isObject().get("rowSpan").isNumber()
					.doubleValue());
			flexTable.setColSpan(row, col, colSpan);
			//addAttributes(cellsArray.get(i).isObject().get("child").isObject(), widget);
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(cellsArray.get(i).isObject().get("child").isObject(), widget);
		}
		JSONArray widthArray = jsonObj.get("widths").isArray();
		for(int i = 0, len = widthArray.size(); i < len; i++)
			if(widthArray.get(i) != null)//could be null in case all columns at this index are part of one or the other colspans
				flexTable.getColumnFormatter().setWidth(i, widthArray.get(i).isNumber().doubleValue() + "px");
		JSONArray heightArray = jsonObj.get("heights").isArray();
		for(int i = 0; i < heightArray.size(); i++)
			DOM.setElementAttribute(flexTable.getRowFormatter().getElement(i), "height", heightArray.get(i).isNumber().doubleValue() + "px");
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget){
		super.copyAttributes(widgetSource, widgetTarget);
		VkFlexTable sourceTable = (VkFlexTable)widgetSource;
		VkFlexTable targetTable = (VkFlexTable)widgetTarget;
		int rowCount = sourceTable.getRowCount();
		VkStateHelper.getInstance().setDesignerMode(true);
		for(int i = 0; i < rowCount; i++) {
			for(int j = 0, colNum = 0, columnCount = sourceTable.getCellCount(i); j < columnCount; j++) {
				targetTable.makeCell(i, j , colNum);
				Panel sourcePanel = (Panel) sourceTable.getWidget(i, j);
				Panel targetPanel = (Panel) targetTable.getWidget(i, j);
				for(Widget k : sourcePanel) {
					IVkWidget currentWidget = ((IVkWidget) k);
					Widget newWidget = VkStateHelper.getInstance().getEngine().getWidget(currentWidget.getWidgetName());
					VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(currentWidget.getWidgetName()).deepClone((Widget) currentWidget, newWidget);
					targetPanel.add(newWidget);
				}
				int colspan = sourceTable.getColSpan(i, j);
				colNum += colspan;
				targetTable.setRowSpan(i, j, sourceTable.getRowSpan(i, j));
				targetTable.setColSpan(i, j, colspan);
			}
			DOM.setElementAttribute(targetTable.getRowFormatter().getElement(i), "height", DOM.getElementAttribute(sourceTable.getRowFormatter().getElement(i), "height"));
		}
		VkStateHelper.getInstance().setDesignerMode(false);//because it would be false when this would be called from VkAbstractWidgetEngine#deepClone
		int numberOfCols = 0;
		for(int i = 0; i < rowCount; i++)
			numberOfCols = Math.max(numberOfCols, sourceTable.getCellCount(i));
		for(int i = 0; i < numberOfCols; i++)
			targetTable.getColumnFormatter().setWidth(i, DOM.getElementAttribute(sourceTable.getColumnFormatter().getElement(i), "width"));
	}
}