package com.vk.gwt.designer.client.designer;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;

public class VkMenu extends Composite implements HasBlurHandlers{
	private static VkMenu vkMenu = new VkMenu();
	private FlowPanel menuBarPanel = new FlowPanel();
	private MenuBar menuBar;
	private Widget copyStyleWidget;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private IWidgetEngine<? extends Widget> widgetEngine;
	private IVkWidget copyWidget;
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
	private boolean executeCutsRemoveCommand;
	private Widget lastSelectedWidget;
	private Command clearCommand;
	private MenuBar ancestorMeuBar;
	
	
	private VkMenu() {
		this(false);
	}
	private VkMenu(boolean isMenu) {
		initWidget(menuBarPanel);
		menuBar = new MenuBar(isMenu);
		menuBarPanel.add(menuBar);
		menuBar.setAutoOpen(true);
		menuBar.setAnimationEnabled(true);
		menuBar.sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
		menuBar.setStyleName("vkgwtdesigner-vertical-menu");
		DOM.setStyleAttribute(menuBar.getElement(), "padding", "3px 0 3px 0");
		menuBar.getElement().getStyle().setFloat(Float.LEFT);//not working if done as DOM.setStyleAttribute
		menuBarPanel.add(StyleToolbar.getInstance());
		ancestorMeuBar = new MenuBar();
		ancestorMeuBar.setHeight("16px");
		menuBarPanel.add(ancestorMeuBar);
		ancestorMeuBar.setStyleName("vkgwtdesigner-vertical-menu");
	}
	public static VkMenu getInstance(){
		return vkMenu;
	}
	void prepareMenu(IVkWidget invokingWidget) {
		if(invokingWidget.showMenu()) {
			//setPosition();
			initializeMenu(invokingWidget);
			menuBar.clearItems();
			menuBar.addItem("Operations", getOperationsItems(widgetEngine.getOperationsList((Widget) invokingWidget)));
			List<String> widgetsList = VkStateHelper.getInstance().getEngine().getWidgetsList((Widget) invokingWidget);
			MenuBar widgetsMenu = getWidgetsList(widgetsList);
			menuBar.addItem("Widgets", widgetsMenu).setEnabled(widgetsList != null && !widgetsList.isEmpty());
			List<String> panelsList = VkStateHelper.getInstance().getEngine().getPanelsList(invokingWidget);
			MenuBar panelsMenu = getPanelsList(panelsList);
			menuBar.addItem("Panels", panelsMenu).setEnabled(panelsList != null && !panelsList.isEmpty());
			addAttributesList(invokingWidget);
			StyleToolbar.getInstance().setWidget((Widget) invokingWidget);
			undoItem = menuBar.addItem("Undo(Ctrl+Z)", getUndoCommand());
			redoItem = menuBar.addItem("Redo(Ctrl+Y)", getRedoCommand());
			refreshUndoRedo();
			menuBar.closeAllChildren(false);
			addAncestorMenu();
		}
	}
	private void addAncestorMenu() {
		ancestorMeuBar.clearItems();
		IVkWidget parent = ((IVkWidget) invokingWidget).getVkParent();
		while (parent instanceof IVkWidget) {
			final Widget currentWidget = (Widget) parent;
			ancestorMeuBar.addItem(((IVkWidget)parent).getWidgetName() + (parent.getVkParent() != null ? " >" : ""), new Command(){
				@Override
				public void execute() {
					prepareMenu((IVkWidget)currentWidget);
					ToolbarHelper.getInstance().showToolbar(currentWidget);
				}});
			parent = ((IVkWidget)parent).getVkParent();
				
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
		this.widgetEngine = WidgetEngineMapping.getInstance().getEngineMap().get(widget.getWidgetName());
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
				VkDesignerUtil.setLoadString(WidgetEngineMapping.getInstance().getEngineMap().get(VkMainDrawingPanel.getInstance().getWidgetName()).serialize((IVkWidget) VkMainDrawingPanel.getInstance()));
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
		undoItem.setVisible(!UndoHelper.getInstance().isUndoStackEmpty());
		redoItem.setVisible(!UndoHelper.getInstance().isRedoStackEmpty());
	}
	Command getRedoCommand() {
		return redoCommand = redoCommand != null ? redoCommand : new Command(){
			@Override
			public void execute() {
				redoItem.setVisible(UndoHelper.getInstance().redo());
				undoItem.setVisible(!UndoHelper.getInstance().isUndoStackEmpty());
			}};
	}
	Command getUndoCommand() {
		return undoCommand = undoCommand != null ? undoCommand : new Command(){
			@Override
			public void execute() {
				undoItem.setVisible(UndoHelper.getInstance().undo());
				redoItem.setVisible(!UndoHelper.getInstance().isRedoStackEmpty());
			}};
	}
	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return menuBar.addHandler(handler, BlurEvent.getType());
	}

	private void addAttributesList(IVkWidget widget) {
		addAttributesList(widget, menuBar, "Attributes");
	}
	
	private void addAttributesList(final IVkWidget widget, MenuBar menu, String menuName) {
		List<String> attributeList = WidgetEngineMapping.getInstance().getEngineMap().get(widget.getWidgetName()).getAttributesList((Widget) widget);
		if(attributeList != null && attributeList.size() > 0)	{
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
					prepareMenu(widget);
					widgetEngine.applyAttribute(selectedMenuItem.getText(), invokingWidget);
				}
			};
			for (String attribute : attributeList)
				attributesMenu.addItem(attribute, attributeClickedCommand);
			menu.addItem(menuName, attributesMenu);
		}
	}
	private MenuBar getWidgetsList(List<String> widgetsList) {
		MenuBar widgetsMenu = new MenuBar(true){
			@Override
			public void selectItem(MenuItem item) {
				super.selectItem(item);
				selectedMenuItem = item;
			}
		};
		if(widgetsList != null && widgetsList.size() > 0) {
			widgetsMenu.setStyleName("vkgwtdesigner-vertical-menu");
			widgetsMenu.setFocusOnHoverEnabled(false);				
			for (String widgetName : widgetsList)
				widgetsMenu.addItem(widgetName, getWidgetClickedCommand());
		}
		return widgetsMenu;
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
				final Widget panelWidget = invokingWidget;
				final String widgetName = selectedMenuItem.getText();
				final Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName);
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
							VkStateHelper.getInstance().getEngine().addWidget(widget, (IVkPanel)panelWidget, 50, 50);
					}}, new Command(){
							@Override
							public void execute() {
								if(widget != null)
									widget.removeFromParent();
							}});
				if(panelWidget instanceof AbsolutePanel)
					MoveHelper.getInstance().makeMovable(widget);
			};
		};
	}
	private MenuBar getOperationsItems(List<String> operationsList) {
		MenuBar operationsMenu = new MenuBar(true);
		DOM.setStyleAttribute(operationsMenu.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		operationsMenu.setFocusOnHoverEnabled(false);
		operationsMenu.setStyleName("vkgwtdesigner-vertical-menu");
		for (int i = 0; i < operationsList.size(); i++) {
			if(operationsList.get(i).equals(IEngine.REMOVE))
				operationsMenu.addItem(operationsList.get(i) + "(Del)", getRemoveCommand());
			else if(operationsList.get(i).equals(IEngine.MOVE)) {
				operationsMenu.addItem(IEngine.MOVE, new Command(){
					@Override
					public void execute() {
						MoveHelper.getInstance().makeMovable(invokingWidget);
					}});
			}
			else if(operationsList.get(i).equals(IEngine.RESIZE))
				operationsMenu.addItem(operationsList.get(i) + "(Right Click)", getResizeCommand());
			else if(operationsList.get(i).equals(IEngine.CUT))
				operationsMenu.addItem(operationsList.get(i) + "(Ctrl+X)", getCutCommand());
			else if(operationsList.get(i).equals(IEngine.COPY))
				operationsMenu.addItem(operationsList.get(i) + "(Ctrl+C)", getCopyCommand());
			else if(operationsList.get(i).equals(IEngine.COPY_STYLE))
				operationsMenu.addItem(operationsList.get(i) + "(Ctrl+Shift+C)", getCopyStyleCommand());
			else if(operationsList.get(i).equals(IEngine.PASTE_STYLE) && copyStyleWidget != null)
				operationsMenu.addItem(operationsList.get(i) + "(Ctrl+Shift+V)", getPasteStyleCommand());
			else if(operationsList.get(i).equals(IEngine.PASTE) && copyWidget != null)
				operationsMenu.addItem(operationsList.get(i) + "(Ctrl+V)", getPasteCommand());
			else if(operationsList.get(i).equals(IEngine.SAVE))
				operationsMenu.addItem(operationsList.get(i), getSaveCommand());
			else if(operationsList.get(i).equals(IEngine.LOAD))
				operationsMenu.addItem(operationsList.get(i), getLoadCommand());
			else if(operationsList.get(i).equals(IEngine.CLEAR))
				operationsMenu.addItem(operationsList.get(i), getClearCommand());
		}
		addPreviewMenuItem(operationsMenu);
		return operationsMenu;
	}	

	private Command getLoadCommand() {
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
						getClearCommand().execute();
						VkStateHelper.getInstance().setLoadRunning(true);
						VkDesignerUtil.loadApplication(ta.getText());
						loadDialog.hide();
						UndoHelper.getInstance().init();
						VkStateHelper.getInstance().setLoadRunning(false);
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
	private Command getClearCommand() {
		return clearCommand = clearCommand != null ? clearCommand : new Command(){
			@Override
			public void execute() {
				Iterator<Widget> iterator = VkMainDrawingPanel.getInstance().iterator();
				while(iterator.hasNext()) {	
					Widget widget = iterator.next();
					if(widget != menuBar)
						widget.removeFromParent();
				}
				SnapHelper.getInstance().init();
			}
		};
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
				ta.setText(widgetEngine.serialize((IVkWidget) invokingWidget));
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
	Command getPasteStyleCommand() {
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.PASTE_STYLE))//this check is for short cut key call for VkDesignerUtil
			return new Command(){
				@Override
				public void execute() {
					String top = DOM.getStyleAttribute(invokingWidget.getElement(), "top");
					String left = DOM.getStyleAttribute(invokingWidget.getElement(), "left");
					//DOM.setElementAttribute(invokingWidget.getElement(), "style", DOM.getElementAttribute(copyStyleWidget.getElement(), "style"));
					VkDesignerUtil.setCssText(invokingWidget, VkDesignerUtil.getCssText(copyStyleWidget));
					DOM.setStyleAttribute(invokingWidget.getElement(), "left", left);
					DOM.setStyleAttribute(invokingWidget.getElement(), "top", top);
				}
			};
		else
			return null;
	}
	Command getCopyStyleCommand() {
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.COPY_STYLE))//this check is for short cut key call for VkDesignerUtil
			return copyStyleCommand = copyStyleCommand != null ? copyStyleCommand : new Command(){
				@Override
				public void execute() {
					copyStyleWidget = invokingWidget;
				}
			};
		else
			return null;
	}
	private Command getRemoveCommand() {
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.REMOVE))//this check is for short cut key call for VkDesignerUtil
			return new Command(){
				@Override
				public void execute() {
					final Widget widget = invokingWidget;
					final Widget panel = (Widget) ((IVkWidget) widget).getVkParent();
					final int top = widget.getElement().getOffsetTop();
					final int left = widget.getElement().getOffsetLeft();
					int index = -1;
					Widget parent = widget.getParent();
					if(parent instanceof IndexedPanel.ForIsWidget)
						index = ((IndexedPanel.ForIsWidget)parent).getWidgetIndex(widget);
					final int widgetIndex = index;
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							prepareMenu(((IVkWidget) widget).getVkParent());
							widget.removeFromParent();
						}}, new Command(){
								@Override
								public void execute() {
									if(widgetIndex > -1)
										VkStateHelper.getInstance().getEngine().addWidget(widget, (IVkPanel)panel, top, left, widgetIndex);
									else
										VkStateHelper.getInstance().getEngine().addWidget(widget, (IVkPanel)panel, top, left);
								}});
					ToolbarHelper.getInstance().hideToolbar();
			}};
		else
			return null;
	}
	
	Command getResizeCommand() {
		return new Command(){
			@Override
			public void execute() {
				ResizeHelper.getInstance().resize(invokingWidget);
			}};
	}
	Command getCopyCommand() {
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.COPY)) {//this check is for short cut key call for VkDesignerUtil
			return copyCommand = copyCommand != null ? copyCommand :  new Command(){
				@Override
				public void execute() {
					copyWidget = (IVkWidget)invokingWidget;
				}
			};
		} else
			return null;
	}
	Command getCutCommand(){
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.CUT)) {//this check is for short cut key call for VkDesignerUtil
			return cutCommand = cutCommand != null ? cutCommand :  new Command(){
				@Override
				public void execute() {
					getCopyCommand().execute();
					executeCutsRemoveCommand = true;
				}
			};
		} else
			return null;
	};
	Command getPasteCommand() {
		if(widgetEngine.getOperationsList(invokingWidget).contains(IEngine.PASTE)) {//this check is for short cut key call for VkDesignerUtil
			return new Command(){
				@Override
				public void execute() {
					final boolean tempExecuteCutsRemoveCommand = executeCutsRemoveCommand;
					final Widget tempInvokingWidget = invokingWidget;
					final IVkWidget tempCopyWidget = copyWidget;
					final IVkPanel prevParent = (IVkPanel) ((IVkWidget)tempCopyWidget).getVkParent();
					final Widget widget;						
					if(tempExecuteCutsRemoveCommand)
						widget = (Widget) tempCopyWidget;
					else {
						boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
						VkStateHelper.getInstance().setDesignerMode(false);//imp because some widgets like VkFlextable pop up dialogs in constructors
						widget = VkStateHelper.getInstance().getEngine().getWidget((tempCopyWidget).getWidgetName());
						VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
						InitializeHelper.getInstance().initDesignerEvents(widget, widgetEngine);//since this was not called during get Widget as then isDesignerMode = false 
						WidgetEngineMapping.getInstance().getEngineMap().get(tempCopyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
					}
					if(tempCopyWidget != null) {
						if(tempInvokingWidget instanceof IVkPanel) {
							final int top = widget.getElement().getOffsetTop();
							final int left = widget.getElement().getOffsetLeft();
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									widget.removeFromParent();
									VkStateHelper.getInstance().getEngine().addWidget(widget , ((IVkPanel)tempInvokingWidget), 8, 4);
								}}, new Command(){
										@Override
										public void execute() {
											if(tempExecuteCutsRemoveCommand) {
												widget.removeFromParent();
												VkStateHelper.getInstance().getEngine().addWidget(widget , prevParent, top, left);
											} else {
												invokingWidget = widget;
												widget.removeFromParent();
												ToolbarHelper.getInstance().hideToolbar();
											}
										}
									});
					}
					executeCutsRemoveCommand = false;
				}}};
		} else
			return null;
	}
}
