package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkGrid;

public class VkGridEngine extends VkAbstractWidgetEngine<VkGrid> {
	private static final String ADD_NEW_ROW = "Add New Row";
	private static final String ADD_NEW_COLUMN = "Add New Column";
	//private static final String CLEAR_SELECTION = "Clear And Stop Cell Selection";
	//private static final String START_SELECTION = "Start Cell Selection";
	private static final String ADD_CELLSPACING = "Add Cell Spacing";
	private static final String ADD_CELLPADDING = "Add Cell Padding";
	//private static final String SET_CELL_HORIZONTAL_ALIGNMENT = "Set Cell Horizontal Alignment";
	//private static final String SET_CELL_VERTICAL_ALIGNMENT = "Set Cell Vertical Alignment";
	//private static final String SET_CELL_WORDWRAP = "Set Cell Word Wrap";
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
		//list.add(CLEAR_SELECTION);
		//list.add(START_SELECTION);
		list.add(ADD_CELLSPACING);
		list.add(ADD_CELLPADDING);
		//list.add(SET_CELL_HORIZONTAL_ALIGNMENT);
		//list.add(SET_CELL_VERTICAL_ALIGNMENT);
		//list.add(SET_CELL_WORDWRAP);
		return list;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkGrid table = (VkGrid)invokingWidget;
		if(attributeName.equals(ADD_NEW_ROW))
			addNewRow(table);
		else if(attributeName.equals(ADD_NEW_COLUMN))
			addNewColumn(table);
		/*else if(attributeName.equals(CLEAR_SELECTION))
			clearAndStopCellSelection(table);
		else if(attributeName.equals(START_SELECTION))
			startCellSelection(table);*/
		else if (attributeName.equals(ADD_CELLSPACING))
			addCellSpacing(table);
		else if (attributeName.equals(ADD_CELLPADDING))
			addCellPadding(table);
		/*else if (attributeName.equals(SET_CELL_VERTICAL_ALIGNMENT))
			setCellVerticalAlignment(table);
		else if (attributeName.equals(SET_CELL_HORIZONTAL_ALIGNMENT))
			setCellHorizontalAlignment(table);
		else if (attributeName.equals(SET_CELL_WORDWRAP))
			setCellWordWrap(table);*/
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	/*private void setCellWordWrap(final VkGrid table) {
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
						if(table.getCellFormatter().getStyleName(i, j).indexOf("VkGrid-cell-selected") > -1 && (selectedCellFound = true))
							table.setCellWordWrap(i, j, Boolean.valueOf(listBox.getValue(listBox.getSelectedIndex())));
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
	}
	private void setCellHorizontalAlignment(final VkGrid table) {
		final ListBox listBox = new ListBox();
		listBox.setWidth("100px");
		listBox.addItem("Left", "left");
		listBox.addItem("Center", "center");
		listBox.addItem("Right", "right");
		VkDesignerUtil.getEngine().showAddListDialog("Add Cell Spacing", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				int rowCount = table.getRowCount();
				boolean selectedCellFound = false;
				for(int i = 0; i < rowCount; i++)
				{
					int colCount = table.getCellCount(i);
					for(int j = 0; j < colCount; j++)
						if(table.getCellFormatter().getStyleName(i, j).indexOf("VkGrid-cell-selected") > -1 && (selectedCellFound = true))
							table.setCellHorizontalAlignment(i, j, text);
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
	}
	private void setCellVerticalAlignment(final VkGrid table) {
		final ListBox listBox = new ListBox();
		listBox.setWidth("100px");
		listBox.addItem("Top", "top");
		listBox.addItem("Middle", "middle");
		listBox.addItem("Bottom", "bottom");
		VkDesignerUtil.getEngine().showAddListDialog("Add Cell Spacing", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				int rowCount = table.getRowCount();
				boolean selectedCellFound = false;
				for(int i = 0; i < rowCount; i++)
				{
					int colCount = table.getCellCount(i);
					for(int j = 0; j < colCount; j++)
						if(table.getCellFormatter().getStyleName(i, j).indexOf("VkGrid-cell-selected") > -1 && (selectedCellFound = true))
							table.setCellVerticalAlignment(i, j, text);
				}
				if(!selectedCellFound)
					Window.alert("Please select cells before applying cell operations");
			}
		});
	}*/
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
	/*private void startCellSelection(VkGrid table) {
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
	}*/
}
