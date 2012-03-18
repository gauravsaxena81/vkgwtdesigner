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
package com.vk.gwt.designer.client.designer;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PopupPanel.PositionCallback;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.ui.widget.table.IVkTable;

public class TableSizeChooserMenuItem extends MenuItem {
	private PopupPanel popupPanel;
	private Timer hideTimer;
	
	public interface IAddTableCommand{
		public void addTable(Widget table);
	}
	
	public TableSizeChooserMenuItem(String widgetName, IAddTableCommand command){
		super(widgetName, true, new MenuBar());
		popupPanel = new PopupPanel(true);
		getSubMenu().addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				hide();
			}
		});
		hideTimer = new Timer(){
			@Override
			public void run() {
				popupPanel.hide();
			}};
		popupPanel.setWidget(makeTableChooser(getText(), command));
		DOM.setStyleAttribute(popupPanel.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		popupPanel.setStyleName("vkgwtdesigner-vertical-menu");
	}
	private Grid makeTableChooser(final String widgetName, final IAddTableCommand command) {
		final Grid defineTableGrid = new Grid(8, 8); 
		for(int i = 0, rows = defineTableGrid.getRowCount(); i < rows; i++){
			for(int j = 0, cols = defineTableGrid.getColumnCount(); j < cols; j++){
				final Label label = new Label();
				defineTableGrid.setWidget(i, j, label);
				DOM.setStyleAttribute(label.getElement(), "borderTop", "solid 1px black");
				DOM.setStyleAttribute(label.getElement(), "borderLeft", "solid 1px black");
				label.setPixelSize(10, 10);
				label.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						int row = -1;
						int col = -1;
						A: for(int i = 0, rows = defineTableGrid.getRowCount(); i < rows; i++) {
							for(int j = 0, cols = defineTableGrid.getColumnCount(); j < cols; j++) {
								if(defineTableGrid.getWidget(i, j).equals(label)) {
									row = i;
									col = j;
									break A;
								}
							}
						}
						for(int i = 0, rows = defineTableGrid.getRowCount(); i < rows; i++) {
							for(int j = 0, cols = defineTableGrid.getColumnCount(); j < cols; j++){
								if(i <= row && j <= col)
									DOM.setStyleAttribute(defineTableGrid.getWidget(i, j).getElement(), "backgroundColor", "white");
								else
									DOM.setStyleAttribute(defineTableGrid.getWidget(i, j).getElement(), "backgroundColor", "transparent");
							}
						}
					}
				});
				label.addMouseDownHandler(new MouseDownHandler(){
					@Override
					public void onMouseDown(MouseDownEvent event) {
						A: for(int i = 0, rows = defineTableGrid.getRowCount(); i < rows; i++) {
							for(int j = 0, cols = defineTableGrid.getColumnCount(); j < cols; j++) {
								if(defineTableGrid.getWidget(i, j).equals(label)) {
									Widget table = VkStateHelper.getInstance().getEngine().getWidget(widgetName);
									if(table instanceof IVkTable) {
										((IVkTable) table).defineTable(i + 1, j + 1);
										command.addTable(table);
										hide();
									}
									break A;
								}
							}
						}
					}});
			}
		}
		return defineTableGrid;
	}
	public void show(){
		popupPanel.setPopupPositionAndShow(new PositionCallback() {
			@Override
			public void setPosition(int offsetWidth, int offsetHeight) {
				popupPanel.setPopupPosition(TableSizeChooserMenuItem.this.getAbsoluteLeft() + TableSizeChooserMenuItem.this.getParentMenu().getOffsetWidth()
						, TableSizeChooserMenuItem.this.getAbsoluteTop());
			}
		});
	}
	public void hide(){
		hideTimer.schedule(1);
	}
}
