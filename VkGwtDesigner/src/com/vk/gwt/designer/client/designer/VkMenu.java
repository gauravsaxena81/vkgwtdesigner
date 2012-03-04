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

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.designer.TableSizeChooserMenuItem.IAddTableCommand;
import com.vk.gwt.designer.client.ui.widget.table.vkFlextable.VkFlexTable;
import com.vk.gwt.designer.client.ui.widget.table.vkGrid.VkGrid;

public class VkMenu extends Composite implements IVkMenu{
	private FlowPanel menuBarPanel = new FlowPanel();
	private MenuBar menuBar;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private IWidgetEngine<? extends Widget> widgetEngine;
	private TabPanel styleTabPanel;
	private Command copyCommand;
	private Command undoCommand;
	private Command redoCommand;
	private Command cutCommand;
	private Command copyStyleCommand;
	private Command saveCommand;
	private Command loadCommand;
	private MenuItem undoItem;
	private MenuItem redoItem;
	private Widget lastSelectedWidget;
	private Command clearCommand;
	private MenuBar ancestorMeuBar;
	private Command downLoadCommand;
	private Command openCommand;
	private Command pasteCommand;
	private Command pasteStyleCommand;
	private Command removeCommand;
	private Command moveCommand;
	private Command moveRightCommand;
	private Command moveLeftCommand;
	private Command moveUpCommand;
	private Command resizeCommand;
	private Command resizeRightCommand;
	private Command resizeLeftCommand;
	private Command resizeUpCommand;
	private Command resizeDownCommand;
	
	
	public VkMenu() {
		this(false);
	}
	private VkMenu(boolean isMenu) {
		menuBarPanel.clear();
		initWidget(menuBarPanel);
		menuBar = new MenuBar(isMenu);
		menuBarPanel.add(menuBar);
		menuBar.setAnimationEnabled(true);
		menuBar.sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
		menuBar.setStyleName("vkgwtdesigner-vertical-menu");
		DOM.setStyleAttribute(menuBar.getElement(), "padding", "3px 0 3px 0");
		//menuBar.getElement().getStyle().setFloat(Float.LEFT);//not working if done as DOM.setStyleAttribute
		menuBarPanel.add(StyleToolbar.getInstance());
		ancestorMeuBar = new MenuBar();
		ancestorMeuBar.setHeight("16px");
		menuBarPanel.add(ancestorMeuBar);
		ancestorMeuBar.setStyleName("vkgwtdesigner-vertical-menu");
	}
	public void prepareMenu(IVkWidget invokingWidget) {
		if(invokingWidget.showMenu()) {
			initializeMenu(invokingWidget);
			menuBar.clearItems();
			menuBar.addItem("File", getFileItems());
			menuBar.addItem("Edit", getEditItems(widgetEngine.getOperationsList((Widget) invokingWidget)));
			List<String> widgetsList = VkStateHelper.getInstance().getEngine().getWidgetsList((Widget) invokingWidget);
			MenuBar widgetsMenu = getWidgetsList(widgetsList);
			menuBar.addItem("Widgets", widgetsMenu).setEnabled(widgetsList != null && !widgetsList.isEmpty());
			List<String> panelsList = VkStateHelper.getInstance().getEngine().getPanelsList(invokingWidget);
			MenuBar panelsMenu = getPanelsList(panelsList);
			menuBar.addItem("Panels", panelsMenu).setEnabled(panelsList != null && !panelsList.isEmpty());
			List<String> attributeList = VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(invokingWidget.getWidgetName()).getAttributesList((Widget) invokingWidget);
			if(attributeList != null)
				menuBar.addItem("Attributes", getAttributesMenu(attributeList));
			else
				menuBar.addItem("Attributes", getAttributesMenu(new ArrayList<String>())).setEnabled(false);
			StyleToolbar.getInstance().setWidget((Widget) invokingWidget);
			VkStateHelper.getInstance().getKeyBoardHelper().setWidget(invokingWidget);
			refreshUndoRedo();
			menuBar.closeAllChildren(false);
			addAncestorMenu();
		}
	}
	private void addAncestorMenu() {
		ancestorMeuBar.clearItems();
		IVkWidget node = ((IVkWidget) invokingWidget);
		while (node instanceof IVkWidget) {
			final Widget currentWidget = (Widget) node;
			ancestorMeuBar.addItem(((IVkWidget)node).getWidgetName() + (node.getVkParent() != null ? " >" : ""), new Command(){
				@Override
				public void execute() {
					prepareMenu((IVkWidget)currentWidget);
					VkStateHelper.getInstance().getToolbarHelper().showToolbar(currentWidget);
				}});
			node = ((IVkWidget)node).getVkParent();
		}
	}
	private void initializeMenu(IVkWidget widget) {
		if(this.invokingWidget != null && VkStateHelper.getInstance().isDesignerMode()) {
			this.invokingWidget.removeStyleName("vk-selectedWidget");
			((Widget) invokingWidget).getElement().getStyle().clearZIndex();
		}
		lastSelectedWidget = invokingWidget; 
		invokingWidget = (Widget)widget; 
		if(VkStateHelper.getInstance().isDesignerMode() && !((Widget) invokingWidget).getElement().getId().equals("drawingPanel"))	{
			this.invokingWidget.addStyleName("vk-selectedWidget");
			DOM.setStyleAttribute(invokingWidget.getElement(), "zIndex", "1");
		}
		this.widgetEngine = VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName());
		refreshStylePanelValues();
	}
	public final boolean isLastSelectedWidget(Widget w) {
		return lastSelectedWidget == w;
	}
	private void refreshStylePanelValues() {
		if(styleTabPanel != null) {
			NodeList<Element> inputList = styleTabPanel.getElement().getElementsByTagName("INPUT");
			for(int i = 0; i < inputList.getLength(); i++) {
				String attributeName = DOM.getElementAttribute(
					(com.google.gwt.user.client.Element) inputList.getItem(i), "attributeName");
				String styleAttribute = DOM.getStyleAttribute(invokingWidget.getElement(), attributeName);
				if(!attributeName.trim().isEmpty())
					DOM.setElementProperty((com.google.gwt.user.client.Element) inputList.getItem(i), "value"
							, styleAttribute);
			}
		}
	}
	private void addPreviewMenuItem(MenuBar operationsMenu) {
		operationsMenu.addItem("Preview", new Command() {
			@Override
			public void execute() {
				VkDesignerUtil.setLoadString(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(VkMainDrawingPanel.getInstance().getWidgetName()).serialize((IVkWidget) VkMainDrawingPanel.getInstance()));
				String href = Window.Location.getHref();
				if(Window.Location.getParameterMap().size() == 0)
					href += "?isDesignerMode=false";
				else
					href += "&isDesignerMode=false";
				Window.open(href, "_blank", Window.Navigator.getUserAgent().indexOf("IE") > -1 ? "fullscreen=1" : null);
			}
		});
	}
	private void refreshUndoRedo() {
		undoItem.setEnabled(!UndoHelper.getInstance().isUndoStackEmpty());
		redoItem.setEnabled(!UndoHelper.getInstance().isRedoStackEmpty());
	}
	Command getRedoCommand() {
		return redoCommand = redoCommand != null ? redoCommand : new Command(){
			@Override
			public void execute() {
				UndoHelper.getInstance().redo();
				refreshUndoRedo();
			}};
	}
	Command getUndoCommand() {
		return undoCommand = undoCommand != null ? undoCommand : new Command(){
			@Override
			public void execute() {
				UndoHelper.getInstance().undo();
				refreshUndoRedo();
			}};
	}
	private MenuBar getAttributesMenu(List<String> attributeList) {
			MenuBar attributesMenu = new MenuBar(true){
				@Override
				public void selectItem(MenuItem item) {
					super.selectItem(item);
					selectedMenuItem = item;
				}
			};
			attributesMenu.setStyleName("vkgwtdesigner-vertical-menu");
			attributesMenu.setFocusOnHoverEnabled(false);
			Command attributeClickedCommand = new Command() {
				@Override
				public void execute() {
				//prepareMenu(widget);
					widgetEngine.applyAttribute(selectedMenuItem.getText(), invokingWidget);
				}
			};
			for (String attribute : attributeList)
				attributesMenu.addItem(attribute, attributeClickedCommand);
		return attributesMenu;
	}
	private MenuBar getWidgetsList(List<String> widgetsList) {
		MenuBar widgetsMenu = new MenuBar(true) {
			private TableSizeChooserMenuItem tableSizeChooserMenuItem;
			@Override
			public void selectItem(MenuItem item) {
				super.selectItem(item);
				selectedMenuItem = item;
			}
			private MenuItem findItem(com.google.gwt.user.client.Element hItem) {
			    for (MenuItem item : super.getItems()) {
			    	if (DOM.isOrHasChild(item.getElement(), hItem)) {
			    		return item;
			    	}
			    }
			    return null;
			}
			@Override
			public void onDetach(){
				super.onDetach();
				if(tableSizeChooserMenuItem != null)
					tableSizeChooserMenuItem.hide();
			}
			@Override
			public void onBrowserEvent(Event event) {
				MenuItem item = findItem(DOM.eventGetTarget(event));
				switch (DOM.eventGetType(event)) {
					case Event.ONMOUSEOVER: {
						if (item != null) {
							if(tableSizeChooserMenuItem != null && !item.equals(tableSizeChooserMenuItem)) {
								tableSizeChooserMenuItem.hide();
					        	super.closeAllChildren(true);
					        	tableSizeChooserMenuItem = null;
					        }
					        if (item instanceof TableSizeChooserMenuItem) {
					        	tableSizeChooserMenuItem = (TableSizeChooserMenuItem)item; 
					        	super.selectItem(item);
					        	tableSizeChooserMenuItem.show();
					        }
					        super.onBrowserEvent(event);
						}
				        break;
				    }
					default : 
						super.onBrowserEvent(event);
			    }
			}
		};
		if(widgetsList != null && widgetsList.size() > 0) {
			widgetsMenu.setStyleName("vkgwtdesigner-vertical-menu");
			widgetsMenu.setFocusOnHoverEnabled(false);
			String tables = VkGrid.NAME +  "|" + VkFlexTable.NAME;
			for (String widgetName : widgetsList) {
				if(widgetName.matches(tables))
					widgetsMenu.addItem(getTableSizeChooser(widgetName));
				else
					widgetsMenu.addItem(widgetName, getWidgetClickedCommand());
			}
		}
		return widgetsMenu;
	}
	
	private TableSizeChooserMenuItem getTableSizeChooser(String widgetName) {
		return new TableSizeChooserMenuItem(widgetName, new IAddTableCommand() {
			@Override
			public void addTable(final Widget table) {
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						VkStateHelper.getInstance().getEngine().addWidget(table, (IVkPanel)invokingWidget);
					}}, new Command(){
							@Override
							public void execute() {
								table.removeFromParent();
							}});
				new Timer(){
					@Override
					public void run() {
						VkStateHelper.getInstance().getMoveHelper().makeMovable((IVkWidget) table);
					}}.schedule(100);
			}
		});
	}
	private MenuBar getPanelsList(List<String> panelsList) {
		MenuBar panelsMenu = new MenuBar(true){
			@Override
			public void selectItem(MenuItem item) {
				super.selectItem(item);
				selectedMenuItem = item;
			}
		};
		if(panelsList != null && !panelsList.isEmpty()) {
			panelsMenu.setStyleName("vkgwtdesigner-vertical-menu");
			panelsMenu.setFocusOnHoverEnabled(false);
			for (String widgetName : panelsList)
				panelsMenu.addItem(widgetName, getWidgetClickedCommand());
		}
		return panelsMenu;
	}

	private Command getWidgetClickedCommand() {
		return new Command() {
			@Override
			public void execute() {
				VkStateHelper.getInstance().getEngine().addWidget(selectedMenuItem.getText(), (IVkPanel)invokingWidget);
			};
		};
	}
	private MenuBar getFileItems() {
		MenuBar operationsMenu = new MenuBar(true);
		DOM.setStyleAttribute(operationsMenu.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		operationsMenu.setFocusOnHoverEnabled(false);
		operationsMenu.setStyleName("vkgwtdesigner-vertical-menu");
		operationsMenu.addItem(IEngine.NEW, getClearCommand());
		operationsMenu.addItem(IEngine.OPEN, getOpenMenu());
		operationsMenu.addItem(IEngine.SAVE_AS, getSaveAsMenu());
		operationsMenu.addItem(IEngine.CLEAR, getClearCommand());
		addPreviewMenuItem(operationsMenu);
		return operationsMenu;
	}	
	private MenuBar getEditItems(List<String> operationsList) {
		MenuBar editMenu = new MenuBar(true);
		DOM.setStyleAttribute(editMenu.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		editMenu.setFocusOnHoverEnabled(false);
		editMenu.setStyleName("vkgwtdesigner-vertical-menu");
		undoItem = editMenu.addItem("Undo(Ctrl+Z)", getUndoCommand());
		redoItem = editMenu.addItem("Redo(Ctrl+Y)", getRedoCommand());
		refreshUndoRedo();
		MenuItem item = editMenu.addItem(IEngine.DELETE + "(Del)", getRemoveCommand());
		item.setEnabled(operationsList.indexOf(IEngine.DELETE) > -1);
		editMenu.addItem(IEngine.CUT + "(Ctrl+X)", getCutCommand()).setEnabled(operationsList.indexOf(IEngine.CUT) > -1);
		editMenu.addItem(IEngine.COPY + "(Ctrl+C)", getCopyCommand()).setEnabled(operationsList.indexOf(IEngine.COPY) > -1);
		editMenu.addItem(IEngine.PASTE + "(Ctrl+V)", getPasteCommand()).setEnabled(operationsList.indexOf(IEngine.PASTE) > -1 && VkStateHelper.getInstance().getClipBoardHelper().isPasteWidgetPossible());
		editMenu.addItem(IEngine.COPY_STYLE + "(Ctrl+Shift+C)", getCopyStyleCommand()).setEnabled(operationsList.indexOf(IEngine.COPY_STYLE) > -1);;
		editMenu.addItem(IEngine.PASTE_STYLE + "(Ctrl+Shift+V)", getPasteStyleCommand()).setEnabled(operationsList.indexOf(IEngine.PASTE_STYLE) > -1 
			&& VkStateHelper.getInstance().getClipBoardHelper().isPasteStylePossible());
		boolean isMoveEnabled = operationsList.indexOf(IEngine.MOVE) > -1;
		editMenu.addItem(IEngine.MOVE, getMoveCommand()).setEnabled(isMoveEnabled);
		editMenu.addItem(IEngine.MOVE + "-Right(Right arrow)", getMoveRightCommand()).setEnabled(isMoveEnabled);
		editMenu.addItem(IEngine.MOVE + "-Left(Left arrow)", getMoveLeftCommand()).setEnabled(isMoveEnabled);
		editMenu.addItem(IEngine.MOVE + "-Up(Up arrow)", getMoveUpCommand()).setEnabled(isMoveEnabled);
		editMenu.addItem(IEngine.MOVE + "-Down(Down arrow)", getMoveDownCommand()).setEnabled(isMoveEnabled);
		boolean isResizeEnabled = operationsList.indexOf(IEngine.RESIZE) > -1;		
		editMenu.addItem(IEngine.RESIZE , getResizeCommand()).setEnabled(isResizeEnabled);
		editMenu.addItem(IEngine.RESIZE + "-Right(Ctrl + Right arrow)", getResizeRightCommand()).setEnabled(isResizeEnabled);
		editMenu.addItem(IEngine.RESIZE + "-Left(Ctrl + Left arrow)", getResizeLeftCommand()).setEnabled(isResizeEnabled);
		editMenu.addItem(IEngine.RESIZE + "-Up(Ctrl + Up arrow)", getResizeUpCommand()).setEnabled(isResizeEnabled);
		editMenu.addItem(IEngine.RESIZE + "-Down(Ctrl + Down arrow)", getResizeDownCommand()).setEnabled(isResizeEnabled);
		addPreviewMenuItem(editMenu);
		return editMenu;
	}	

	private Command getMoveDownCommand() {
		return moveUpCommand = moveUpCommand != null ? moveUpCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getMoveHelper().moveWidgetDown((IVkWidget) invokingWidget);
			}};
	}
	private Command getMoveUpCommand() {
		return moveUpCommand = moveUpCommand != null ? moveUpCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getMoveHelper().moveWidgetUp((IVkWidget) invokingWidget);
			}};
	}
	private Command getMoveLeftCommand() {
		return moveLeftCommand = moveLeftCommand != null ? moveLeftCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getMoveHelper().moveWidgetLeft((IVkWidget) invokingWidget);
			}};
	}
	private Command getMoveCommand() {
		return moveCommand = moveCommand != null ? moveCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getMoveHelper().makeMovable((IVkWidget) invokingWidget);
			}};
	}
	private Command getMoveRightCommand() {
		return moveRightCommand = moveRightCommand != null ? moveRightCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getMoveHelper().moveWidgetRight((IVkWidget) invokingWidget);
			}};
	}
	private Command getOpenUsingStringCommand() {
		return loadCommand = loadCommand != null ? loadCommand : new Command(){
			@Override
			public void execute() {
				final DialogBox loadDialog = new DialogBox();
				DOM.setStyleAttribute(loadDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
				loadDialog.setText("Please paste the save string below to reproduce the application");
				VerticalPanel vp = new VerticalPanel();
				vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
				loadDialog.add(vp);
				final TextArea ta = new TextArea();
				vp.add(ta);
				ta.setPixelSize(500, 200);
				HorizontalPanel buttonsPanel = new HorizontalPanel();
				vp.add(buttonsPanel);
				buttonsPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
				Button ok = new Button("Render");
				buttonsPanel.add(ok);
				ok.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						VkStateHelper.getInstance().getLoadSaveHelper().loadApplication(ta.getText());
						loadDialog.hide();
					}
				});
				Button cancel = new Button("Cancel");
				buttonsPanel.add(cancel);
				cancel.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						loadDialog.hide();
					}
				});
				loadDialog.center();
			}
		};
	}
	private Command getDownloadCommand() {
		return downLoadCommand = downLoadCommand != null ? downLoadCommand : new Command(){
			@Override
			public void execute() {
				final FormPanel fp = new FormPanel();
				RootPanel.get().add(fp);
				fp.setAction("./vkgwtdesigner/vksync");
				fp.setMethod(FormPanel.METHOD_POST);
				fp.setEncoding(FormPanel.ENCODING_MULTIPART);
				final VerticalPanel dialog = new VerticalPanel();
				fp.setWidget(dialog);
				dialog.add(new Hidden("op", "downLoadLayoutFile"));
				dialog.add(new Hidden("downloadString", VkStateHelper.getInstance().getLoadSaveHelper().getSaveString()));
				fp.submit();
				fp.addSubmitCompleteHandler(new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						new Timer() {
							@Override
							public void run() {
								fp.removeFromParent();
							}}.schedule(5000);
					}
				});
			}
		};
	}
	private Command getOpenUsingFileCommand() {
		return openCommand = openCommand != null ? openCommand : new Command(){
			@Override
			public void execute() {
				final DialogBox origDialog = new DialogBox();
				final FormPanel fp = new FormPanel();
				fp.setAction("./vkgwtdesigner/vksync");
				fp.setMethod(FormPanel.METHOD_POST);
				fp.setEncoding(FormPanel.ENCODING_MULTIPART);
				fp.addSubmitCompleteHandler(new SubmitCompleteHandler() {
					@Override
					public void onSubmitComplete(SubmitCompleteEvent event) {
						VkStateHelper.getInstance().getLoadSaveHelper().loadApplication(event.getResults());
						origDialog.hide();
					}
				});
				origDialog.setWidget(fp);
				final VerticalPanel dialog = new VerticalPanel();
				fp.setWidget(dialog);
				origDialog.setText("Upload Layout File");
				dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
				dialog.setWidth("100%");
				DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
				FileUpload fu = new FileUpload();
				fu.setName("file");
				dialog.add(fu);
				dialog.add(new Hidden("op", "upLoadLayoutFile"));
				HorizontalPanel buttonsPanel = new HorizontalPanel();
				dialog.add(buttonsPanel);
				Button saveButton = new Button("Submit");
				buttonsPanel.add(saveButton);
				saveButton.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						fp.submit();
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
		};
	}
	private Command getClearCommand() {
		return clearCommand = clearCommand != null ? clearCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getLoadSaveHelper().clearApplication();
			}
		};
	}
	protected MenuBar getSaveAsMenu() {
		MenuBar saveMenuBar = new MenuBar(true);
		saveMenuBar.setStyleName("vkgwtdesigner-vertical-menu");
		saveMenuBar.addItem(IEngine.FILE, getDownloadCommand());
		saveMenuBar.addItem(IEngine.STRING, getSaveCommand());
		return saveMenuBar;
	}
	protected MenuBar getOpenMenu() {
		MenuBar loadMenuBar = new MenuBar(true);
		loadMenuBar.setStyleName("vkgwtdesigner-vertical-menu");
		loadMenuBar.addItem(IEngine.FILE, getOpenUsingFileCommand());
		loadMenuBar.addItem(IEngine.STRING, getOpenUsingStringCommand());
		return loadMenuBar;
	}
	private Command getSaveCommand() {
		return saveCommand = saveCommand != null ? saveCommand : new Command(){
			@Override
			public void execute() {
				final DialogBox saveDialog = new DialogBox();
				DOM.setStyleAttribute(saveDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
				saveDialog.setText("Please copy the save string below to reproduce application later");
				VerticalPanel vp = new VerticalPanel();
				vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
				saveDialog.add(vp);
				TextArea ta = new TextArea();
				vp.add(ta);
				ta.setText(VkStateHelper.getInstance().getLoadSaveHelper().getSaveString());
				ta.setPixelSize(500, 200);
				Button ok = new Button("OK");
				vp.add(ok);
				ok.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						saveDialog.hide();
					}
				});
				saveDialog.center();		
			}
		};
	}
	private Command getPasteStyleCommand() {
		return pasteStyleCommand = pasteStyleCommand != null ? pasteStyleCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getClipBoardHelper().pasteStyle((IVkWidget) invokingWidget);
			}
		};
	}
	private Command getCopyStyleCommand() {
		return copyStyleCommand = copyStyleCommand != null ? copyStyleCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getClipBoardHelper().copyStyle((IVkWidget) invokingWidget);
			}
		};
	}
	private Command getRemoveCommand() {
		return removeCommand = removeCommand != null ? removeCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getEngine().removeWidget(invokingWidget);
				prepareMenu(((IVkWidget) invokingWidget).getVkParent());
		}};
	}
	
	private Command getResizeCommand() {
		return resizeCommand = resizeCommand != null ? resizeCommand :  new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getResizeHelper().resize(invokingWidget);
			}};
	}
	private Command getResizeRightCommand() {
		return resizeRightCommand = resizeRightCommand != null ? resizeRightCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getResizeHelper().resizeWidgetRight((IVkWidget) invokingWidget);
			}};
	}
	private Command getResizeLeftCommand() {
		return resizeLeftCommand = resizeLeftCommand != null ? resizeLeftCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getResizeHelper().resizeWidgetLeft((IVkWidget)invokingWidget);
			}};
	}
	private Command getResizeUpCommand() {
		return resizeUpCommand = resizeUpCommand != null ? resizeUpCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getResizeHelper().resizeWidgetUp((IVkWidget)invokingWidget);
			}};
	}
	private Command getResizeDownCommand() {
		return resizeDownCommand = resizeDownCommand != null ? resizeDownCommand : new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getResizeHelper().resizeWidgetDown((IVkWidget)invokingWidget);
			}};
	}
	private Command getCopyCommand() {
		return copyCommand = copyCommand != null ? copyCommand :  new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getClipBoardHelper().copyWidget((IVkWidget) invokingWidget);
			}
		};
	}
	private Command getCutCommand(){
		return cutCommand = cutCommand != null ? cutCommand :  new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getClipBoardHelper().cutWidget((IVkWidget) invokingWidget);
			}
		};
	};
	private Command getPasteCommand() {
		return pasteCommand = pasteCommand != null ? pasteCommand :  new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getClipBoardHelper().pasteWidget((IVkPanel) invokingWidget);
			}
		};
	}
}