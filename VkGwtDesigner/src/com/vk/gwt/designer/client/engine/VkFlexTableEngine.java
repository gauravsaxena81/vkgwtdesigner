package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HasHorizontalAlignment.HorizontalAlignmentConstant;
import com.google.gwt.user.client.ui.HasVerticalAlignment.VerticalAlignmentConstant;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkFlexTable;

public class VkFlexTableEngine extends VkAbstractWidgetEngine<VkFlexTable> {

	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	private static final String CLEAR_SELECTION = "Clear And Stop Cell Selection";
	private static final String START_SELECTION = "Start Cell Selection";
	private static final String ADD_SPAN = "Group Cells";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	private static final String SET_CELL_HORIZONTAL_ALIGNMENT = "Set Cell Horizontal Alignment";
	private static final String SET_CELL_VERTICAL_ALIGNMENT = "Set Cell Vertical Alignment";
	private static final String SET_CELL_WORDWRAP = "Set Cell Word Wrap";
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
		list.add(CLEAR_SELECTION);
		list.add(START_SELECTION);
		list.add(ADD_SPAN);
		list.add(ADD_CELLSPACING);
		list.add(ADD_CELLPADDING);
		list.add(SET_CELL_HORIZONTAL_ALIGNMENT);
		list.add(SET_CELL_VERTICAL_ALIGNMENT);
		list.add(SET_CELL_WORDWRAP);
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkFlexTable table = (VkFlexTable)invokingWidget;
		if(attributeName.equals(ADD_NEW_ROW))
			addNewRow(table);
		else if(attributeName.equals(ADD_NEW_COLUMN))
			addNewColumn(table);
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
		else if (attributeName.equals(SET_CELL_VERTICAL_ALIGNMENT))
			setCellVerticalAlignment(table);
		else if (attributeName.equals(SET_CELL_HORIZONTAL_ALIGNMENT))
			setCellHorizontalAlignment(table);
		else if (attributeName.equals(SET_CELL_WORDWRAP))
			setCellWordWrap(table);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void setCellWordWrap(final VkFlexTable table) {
		final ListBox listBox = new ListBox();
		listBox.setWidth("100px");
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		VkDesignerUtil.getEngine().showAddListDialog("Please choose true to make text wrap", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				int rowCount = table.getRowCount();
				boolean selectedCellFound = false;
				for(int i = 0; i < rowCount; i++)
				{
					int colCount = table.getCellCount(i);
					for(int j = 0; j < colCount; j++)
						if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1 && (selectedCellFound = true))
							table.getFlexCellFormatter().setWordWrap(i, j, Boolean.valueOf(listBox.getValue(listBox.getSelectedIndex())));
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
		
	}
	private void setCellHorizontalAlignment(final VkFlexTable table) {
		final ListBox listBox = new ListBox();
		listBox.setWidth("100px");
		listBox.addItem("Left", "left");
		listBox.addItem("Center", "center");
		listBox.addItem("Right", "right");
		VkDesignerUtil.getEngine().showAddListDialog("Add Cell Spacing", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				HorizontalAlignmentConstant hAlign = null;
				if(text.equals(HasHorizontalAlignment.ALIGN_CENTER.getTextAlignString()))
					hAlign = HasHorizontalAlignment.ALIGN_CENTER;
				else if(text.equals(HasHorizontalAlignment.ALIGN_LEFT.getTextAlignString()))
					hAlign = HasHorizontalAlignment.ALIGN_LEFT;
				else if(text.equals(HasHorizontalAlignment.ALIGN_RIGHT.getTextAlignString()))
					hAlign = HasHorizontalAlignment.ALIGN_RIGHT;
				
				int rowCount = table.getRowCount();
				boolean selectedCellFound = false;
				for(int i = 0; i < rowCount; i++)
				{
					int colCount = table.getCellCount(i);
					for(int j = 0; j < colCount; j++)
						if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1 && (selectedCellFound = true))
							table.getFlexCellFormatter().setHorizontalAlignment(i, j, hAlign);
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
	}
	private void setCellVerticalAlignment(final VkFlexTable table) {
		final ListBox listBox = new ListBox();
		listBox.setWidth("100px");
		listBox.addItem("Top", "top");
		listBox.addItem("Middle", "middle");
		listBox.addItem("Bottom", "bottom");
		VkDesignerUtil.getEngine().showAddListDialog("Add Cell Spacing", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				VerticalAlignmentConstant vAlign = null;
				if(text.equals(HasVerticalAlignment.ALIGN_BOTTOM.getVerticalAlignString()))
					vAlign = HasVerticalAlignment.ALIGN_BOTTOM;
				else if(text.equals(HasVerticalAlignment.ALIGN_MIDDLE.getVerticalAlignString()))
					vAlign = HasVerticalAlignment.ALIGN_MIDDLE;
				else if(text.equals(HasVerticalAlignment.ALIGN_TOP.getVerticalAlignString()))
					vAlign = HasVerticalAlignment.ALIGN_TOP;
				int rowCount = table.getRowCount();
				boolean selectedCellFound = false;
				for(int i = 0; i < rowCount; i++)
				{
					int colCount = table.getCellCount(i);
					for(int j = 0; j < colCount; j++)
						if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1 && (selectedCellFound = true))
							table.getFlexCellFormatter().setVerticalAlignment(i, j, vAlign);
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
	}
	private void addNewColumn(VkFlexTable table) {
		int rowCount = table.getRowCount();
		for(int i = 0; i < rowCount; i++)
			table.addCell(i);
	}
	private void addNewRow(VkFlexTable table) {
		VkVerticalPanel l = new VkVerticalPanel();
		VkAbsolutePanel l2 = (VkAbsolutePanel) VkDesignerUtil.getEngine().getWidget(VkAbsolutePanel.NAME);
		VkDesignerUtil.addWidget(l2, l);
		l2.setSize("90%", "90%");
		int rowCount = table.getRowCount();
		table.setWidget(rowCount, 0, l);
		table.getFlexCellFormatter().setWidth(rowCount, 0,"1px");
		table.getFlexCellFormatter().setHeight(rowCount - 1, 0,"1px");
		table.getFlexCellFormatter().setHeight(rowCount,0,"*");
	}
	private void addSpan(final VkFlexTable table) {
		boolean isFirstCellSpared = false;
		int lastCellRow = 0;
		int lastCellCol = 0;
		int firstCellRow = -1;
		int firstCellCol = -1;
		int rowCount = table.getRowCount();
		int initialTdHeight = 0;
		int initialTdWidth = 0;
		int firstSelectionRow = -1;
		boolean selectedCellFound = false;
		for(int i = 0; i < rowCount; i++)
		{
			int colCount = table.getCellCount(i);
			for(int j = 0, k = 0;j < colCount; j++, k++)
			{
				if(table.getFlexCellFormatter().getStyleName(i, j).indexOf("vkflextable-cell-selected") > -1 && (selectedCellFound = true))
				{
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
						initialTdHeight = table.getFlexCellFormatter().getElement(i, j).getOffsetHeight();
						initialTdWidth = table.getFlexCellFormatter().getElement(i, j).getOffsetWidth();
					}
				}
			}
			if(!selectedCellFound)
				Window.alert("Please select cells before applying cell operations");
		}
		final int cellCol = firstCellCol;
		final int cellRow = firstCellRow;
		final int cellHeight = initialTdHeight;
		final int cellWidth = initialTdWidth;
		Timer t = new Timer(){
			@Override
			public void run() {
				Element cell = table.getFlexCellFormatter().getElement(cellRow, cellCol);
				Widget widget = table.getWidget(cellRow, cellCol);
				widget.setPixelSize((int)(widget.getOffsetWidth() * ((double)cell.getOffsetWidth() / (double)cellWidth))
						, (int)(widget.getOffsetHeight() * ((double)cell.getOffsetHeight() / (double)cellHeight)));
			}
		};
		t.schedule(50);
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
}
