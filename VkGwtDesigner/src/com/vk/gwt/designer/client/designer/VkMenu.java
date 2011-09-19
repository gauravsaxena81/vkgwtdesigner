package com.vk.gwt.designer.client.designer;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.ui.widget.colorpicker.VkColorPicker;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical.VkMenuBarVertical;
import com.vk.gwt.designer.client.ui.widget.vkFrame.VkFrame;

@SuppressWarnings("serial")
public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget copyStyleWidget;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private int top;
	private int left;
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
	private MenuBar styleMenu;
	private MenuItem undoItem;
	private MenuItem redoItem;
	private boolean executeCutsRemoveCommand;
	private Widget lastSelectedWidget;
	private PopupPanel stylePickerPopPanel;	
	private Command clearCommand;
	private Stack<Command> undoStack = new Stack<Command>(){
		@Override
		public Command push(Command c) {
			if(size() == 10)
				remove(0);
			Command push = super.push(c);
			refreshUndoRedo();
			return push;
		}
		@Override
		public Command pop() {
			if(size() > 0)
			{
				Command pop = super.pop();
				refreshUndoRedo();
				return pop;
			}
			else return null;
		}
	};
	private Stack<Command> redoStack = new Stack<Command>(){
		@Override
		public Command push(Command c)
		{
			if(size() == 10)
				remove(0);
			Command push = super.push(c);
			refreshUndoRedo();
			return push;
		}
		@Override
		public Command pop() {
			if(size() > 0)
			{
				Command pop = super.pop();
				refreshUndoRedo();
				return pop;
			}
			else return null;
		}
	};
	
	public VkMenu() {
		this(false);
		setStyleName("vkgwtdesigner-vertical-menu");
		//setVisible(false);
		//DOM.setStyleAttribute(getElement(), "position", "absolute");
		DOM.setStyleAttribute(getElement(), "zIndex", Integer.MAX_VALUE + "");
		/*addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				hideMenu();
			}
		});*/
	}
	public VkMenu(boolean isMenu) {
		super(isMenu);
		setAutoOpen(true);
		setAnimationEnabled(true);
		sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
	}
	void prepareMenu(IVkWidget invokingWidget) {
		if(invokingWidget.showMenu()) {
			setPosition();
			initializeMenu(invokingWidget);
			clearItems();
			addItem("Operations", getOperationsItems(widgetEngine.getOperationsList((Widget) invokingWidget)));
			List<String> widgetsList = VkDesignerUtil.getEngine().getWidgetsList((Widget) invokingWidget);
			MenuBar widgetsMenu = getWidgetsList(widgetsList);
			if(widgetsMenu != null)
			{
				addSeparator();
				addItem("Widgets", widgetsMenu);
			}
			List<String> panelsList = VkDesignerUtil.getEngine().getPanelsList(invokingWidget);
			MenuBar panelsMenu = getPanelsList(panelsList);
			if(panelsMenu != null) {
				addSeparator();
				addItem("Panels", panelsMenu);
			}
			addSeparator();
			addAttributesList(invokingWidget);
			addSpecificItems();
			VkMenu.this.closeAllChildren(false);
		}
	}
	private void initializeMenu(IVkWidget widget) {
		if(this.invokingWidget != null && VkDesignerUtil.isDesignerMode) {
			this.invokingWidget.removeStyleName("vk-selectedWidget");
			((Widget) invokingWidget).getElement().getStyle().clearZIndex();
		}
		lastSelectedWidget = invokingWidget; 
		invokingWidget = (Widget)widget; 
		if(VkDesignerUtil.isDesignerMode && !((Widget) invokingWidget).getElement().getId().equals("drawingPanel"))	{
			this.invokingWidget.addStyleName("vk-selectedWidget");
			DOM.setStyleAttribute(invokingWidget.getElement(), "zIndex", "1");
		}
		this.widgetEngine = VkDesignerUtil.getEngineMap().get(widget.getWidgetName());
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
	private void addSpecificItems() {
		addSeparator();
		addItem("Style Toolbar", new Command(){
			@Override
			public void execute() {
				if(styleMenu != null && styleMenu.isVisible())
					styleMenu.setVisible(false);
				else {
					if(styleMenu == null)
						makeToolbar();
					if(RootPanel.get().getWidgetIndex(styleMenu) == -1)
						RootPanel.get().insert(styleMenu, 1);
					styleMenu.setVisible(true);
				}
			}});
		addSeparator();
		addItem("Preview", new Command() {
			@Override
			public void execute() {
				VkDesignerUtil.setLoadString(VkDesignerUtil.getEngineMap().get(VkDesignerUtil.getDrawingPanel().getWidgetName()).serialize((IVkWidget) VkDesignerUtil.getDrawingPanel()));
				String href = Window.Location.getHref();
				if(Window.Location.getParameterMap().size() == 0)
					href += "?isDesignerMode=false";
				else
					href += "&isDesignerMode=false";
				Window.open(href, "_blank", Window.Navigator.getUserAgent().indexOf("IE") > -1 ? "fullscreen=1" : null);
				//hideMenu();
			}
		});
		undoItem = addItem("Undo(Ctrl+Z)", getUndoCommand());
		redoItem = addItem("Redo(Ctrl+Y)", getRedoCommand());
		refreshUndoRedo();
	}
	private void refreshUndoRedo() {
		if(!undoStack.isEmpty()) 
			undoItem.setVisible(true);
		else
			undoItem.setVisible(false);
		
		if(!redoStack.isEmpty())
			redoItem.setVisible(true);
		else
			redoItem.setVisible(false);
	}
	Command getRedoCommand() {
		return redoCommand = redoCommand != null ? redoCommand : new Command(){
			@Override
			public void execute() {
				redoStack.pop().execute();
				refreshUndoRedo();
				//hideMenu();
			}};
	}
	Command getUndoCommand() {
		return undoCommand = undoCommand != null ? undoCommand : new Command(){
			@Override
			public void execute() {
				undoStack.pop().execute();
				refreshUndoRedo();
				//hideMenu();
			}};
	}
	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addHandler(handler, BlurEvent.getType());
	}
	public void setTop(int top) {
		this.top = top;
	}
	public void setLeft(int left) {
		this.left = left;
	}	
	private void makeToolbar() {
		styleMenu = new MenuBar();
		styleMenu.setStyleName("vkgwtdesigner-toolbar");
		styleMenu.setAutoOpen(true);
		styleMenu.addItem(getBoldMenuItem());
		styleMenu.addItem(getItalicMenuItem());
		styleMenu.addItem(getUnderLineMenuItem());
		styleMenu.addItem(getBgColorPickerMenuItem());
		styleMenu.addItem(getForeColorPickerMenuItem());
		styleMenu.addItem(getBorderColorPickerMenuItem());
		styleMenu.addItem(getBorderWidthPickerMenuItem());
		styleMenu.addItem(getBorderEdgePickerMenuItem());
		styleMenu.addItem(getTextAlignLeftMenuItem());
		styleMenu.addItem(getTextAlignCenterMenuItem());
		styleMenu.addItem(getTextAlignRightMenuItem());
		styleMenu.addItem(getRightIndentItem());
		styleMenu.addItem(getLeftIndentItem());
		styleMenu.addItem(getTopIndentItem());
		styleMenu.addItem(getBottomIndentItem());
		styleTabPanel = getStyleTabPanel();
		styleMenu.addItem(getStyleDialogMenuItem(styleTabPanel));
		
		styleMenu.addSeparator();
		styleMenu.addItem("X", new Command(){
			@Override
			public void execute() {
				styleMenu.setVisible(false);
				stylePickerPopPanel.hide();
			}});
	}
	private MenuItem getRightIndentItem() {
		return new MenuItem("<img src='images/right-indent.ico' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget, "padding-left");
						applyCommand(new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget) - 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + 5 + "px");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget) + 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + "px");
										redoStack.push(redoCommand);
									}});
							}});
					}
			});
	}
	private MenuItem getLeftIndentItem() {
		return new MenuItem("<img src='images/left-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget, "padding-left");
						applyCommand(new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget) + 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior - 5 + "px");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget) - 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + "px");
										redoStack.push(redoCommand);
									}});
							}});
					}
			});
	}
	private MenuItem getTopIndentItem() {
		return new MenuItem("<img src='images/top-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget, "padding-top");
						applyCommand(new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget) - 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + 5 + "px");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget) + 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + "px");
										redoStack.push(redoCommand);
									}});
							}});
					}
			});
	}
	private MenuItem getBottomIndentItem() {
		return new MenuItem("<img src='images/bottom-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget, "padding-top");
						applyCommand(new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget) + 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior - 5 + "px");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget) - 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + "px");
										redoStack.push(redoCommand);
									}});
							}});
					}
			});
	}
	private MenuItem getUnderLineMenuItem() {
		return new MenuItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(invokingWidget.getElement(), "textDecoration").equals("underline"))
							DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "underline");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "");
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "textDecoration", prior);
								redoStack.push(redoCommand);
							}});
					}});
			}});
	}
	private MenuItem getItalicMenuItem() {
		return new MenuItem("<span style='font-style: italic;'>I</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontStyle").equals("italic"))
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "italic");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "");
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "fontStyle", prior);
								redoStack.push(redoCommand);
							}});
					}});
			}});
	}
	private MenuItem getBoldMenuItem() {
		
		return new MenuItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						if(DOM.getStyleAttribute(invokingWidget.getElement(), "fontWeight").isEmpty())
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "bold");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "");
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "fontWeight", prior);
								redoStack.push(redoCommand);
							}});
					}});
			}});
	}
	private MenuItem getTextAlignRightMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPfxaNhOBqGIysMRwHpAADGwEe5v4tWjAAAAABJRU5ErkJggg=='>"
			, true, new Command(){
				@Override
				public void execute() {
					final Widget widget = invokingWidget;
					final String prior = DOM.getStyleAttribute(widget.getElement(), "textAlign");
					applyCommand(new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							DOM.setStyleAttribute(widget.getElement(), "textAlign", "right");
							undoStack.push(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
									redoStack.push(redoCommand);
								}});
						}});
				}
		});
	}
	private MenuItem getTextAlignCenterMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='>"
			, true, new Command(){
				@Override
				public void execute() {
					final Widget widget = invokingWidget;
					final String prior = DOM.getStyleAttribute(widget.getElement(), "textAlign");
					applyCommand(new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							DOM.setStyleAttribute(widget.getElement(), "textAlign", "center");
							undoStack.push(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
									redoStack.push(redoCommand);
								}});
						}});
				}
		});
	}
	private MenuItem getTextAlignLeftMenuItem() {
		
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final String prior = DOM.getStyleAttribute(widget.getElement(), "textAlign");
						applyCommand(new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "textAlign", "left");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
										redoStack.push(redoCommand);
									}});
							}});
					}
		});
	}
	private MenuItem getStyleDialogMenuItem(final TabPanel styleTabPanel) {
		final MenuItem styleMenu = new MenuItem("SM", (Command)null);
		styleTabPanel.addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE)
					stylePickerPopPanel.hide();
			}
		}, KeyDownEvent.getType());
		stylePickerPopPanel = new PopupPanel();
		DOM.setStyleAttribute(stylePickerPopPanel.getElement(), "zIndex", Integer.MAX_VALUE + "");
		styleMenu.setCommand(new Command(){
			@Override
			public void execute() {
				if(stylePickerPopPanel.getWidget() == null){
					stylePickerPopPanel.setWidget(styleTabPanel);
					stylePickerPopPanel.showRelativeTo(styleMenu);
				}
				else if(stylePickerPopPanel.isShowing())
					stylePickerPopPanel.hide();
				else
					stylePickerPopPanel.show();
			}
		});
		styleMenu.setTitle("Detailed Style Dialog");
		return styleMenu;
	}
	private MenuItem getBorderEdgePickerMenuItem() {
		MenuBar borderEdgeMenu = new MenuBar(true);
		MenuItem borderEdgePicker = new MenuItem("BE", true, borderEdgeMenu);
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px black; border-left: solid 1px #DDDDDD; border-right: solid 1px #DDDDDD; border-bottom: solid 1px #DDDDDD;'></div>", true, setBorderEdge("Top"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px black; border-right: solid 1px #DDDDDD; border-bottom: solid 1px #DDDDDD;'></div>", true, setBorderEdge("Left"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px #DDDDDD; border-right: solid 1px black; border-bottom: solid 1px #DDDDDD;'></div>", true, setBorderEdge("Right"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px #DDDDDD; border-right: solid 1px #DDDDDD; border-bottom: solid 1px black;'></div>", true, setBorderEdge("Bottom"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border: solid 1px #DDDDDD;'></div>", true, setBorderEdge(null));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border: solid 1px black;'></div>", true, setBorderEdge(""));
		return borderEdgePicker;
	}
	private Command setBorderEdge(final String suffix) {
		return new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "borderWidth");
				final String attribute;
				if(suffix != null)
					attribute = "border" + suffix + "Style";
				else
					attribute = "borderStyle";
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						if(suffix != null)
							DOM.setStyleAttribute(widget.getElement(), attribute, "solid");
						else
							DOM.setStyleAttribute(widget.getElement(), attribute, "none");
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), attribute, prior);
								redoStack.push(redoCommand);
							}});
				}});
			}};
	}
	private MenuItem getBorderWidthPickerMenuItem() {
		MenuBar borderWidthMenu = new MenuBar(true);
		MenuItem borderWidthPicker = new MenuItem("BW", true, borderWidthMenu);
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 1px black'></div>", true, setBorderWidth(1));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 2px black'></div>", true, setBorderWidth(2));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 3px black'></div>", true, setBorderWidth(3));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 4px black'></div>", true, setBorderWidth(4));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 5px black'></div>", true, setBorderWidth(5));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 6px black'></div>", true, setBorderWidth(6));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 7px black'></div>", true, setBorderWidth(7));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 8px black'></div>", true, setBorderWidth(8));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 9px black'></div>", true, setBorderWidth(9));
		borderWidthMenu.addItem("<div style='width: 40px; border-top: solid 10px black'></div>", true, setBorderWidth(10));
		return borderWidthPicker;
	}
	private Command setBorderWidth(final int borderWidth) {
		return new Command(){
			@Override
			public void execute() {
				final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "borderWidth");
				final Widget widget = invokingWidget;
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", borderWidth + "px");
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", prior);
								redoStack.push(redoCommand);
							}});
				}});				
			}};
	}
	private MenuItem getBorderColorPickerMenuItem() {
		final MenuItem borderColorPicker = new MenuItem("BC", true, (Command)null);
		final PopupPanel borderPickerPopPanel = new PopupPanel();
		borderPickerPopPanel.setAutoHideEnabled(true);
		borderColorPicker.setCommand(new Command(){
			@Override
			public void execute() {
				if(borderPickerPopPanel.getWidget() == null){
					VkColorPicker vkColorPicker = new VkColorPicker();
					borderPickerPopPanel.setWidget(vkColorPicker);
					vkColorPicker.addValueChangeHandler(new ValueChangeHandler<VkColorPicker>() {
						@Override
						public void onValueChange(final ValueChangeEvent<VkColorPicker> event) {
							final Widget widget = invokingWidget;
							final String prior = DOM.getStyleAttribute(widget.getElement(), "borderColor");
							final String color = event.getValue().getColor();
							applyCommand(new Command(){
								private final Command redoCommand = this;
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "borderColor", color);
									undoStack.push(new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "borderColor", prior);
											redoStack.push(redoCommand);
										}});
							}});
							borderPickerPopPanel.hide();
						}
					});
					borderPickerPopPanel.showRelativeTo(borderColorPicker);
				}
				else if(borderPickerPopPanel.isShowing())
					borderPickerPopPanel.hide();
				else
					borderPickerPopPanel.show();
			}
		});
		return borderColorPicker;
	}
	private MenuItem getBgColorPickerMenuItem() {
		final MenuItem bgColorPicker = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAANUlEQVR42mNgGAW0Av+JxNjB39/s/5ExSDE6H5sYFn10MhCbBdgMGDVw1EBqGkhGFhwFNAQAQy76sGFjx7gAAAAASUVORK5CYII='>"
				, true, (Command)null);
		final PopupPanel bgPickerPopPanel = new PopupPanel();
		bgPickerPopPanel.setAutoHideEnabled(true);
		
		bgColorPicker.setCommand(new Command(){
			@Override
			public void execute() {
				if(bgPickerPopPanel.getWidget() == null){
					VkColorPicker vkColorPicker = new VkColorPicker();
					bgPickerPopPanel.setWidget(vkColorPicker);
					vkColorPicker.addValueChangeHandler(new ValueChangeHandler<VkColorPicker>() {
						@Override
						public void onValueChange(ValueChangeEvent<VkColorPicker> event) {
							final Widget widget = invokingWidget;
							final String prior = DOM.getStyleAttribute(widget.getElement(), "background");
							final String color = event.getValue().getColor();
							applyCommand(new Command(){
								private final Command redoCommand = this;
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "background", color);
									undoStack.push(new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "background", prior);
											redoStack.push(redoCommand);
										}});
							}});
							bgPickerPopPanel.hide();
						}
					});
					bgPickerPopPanel.showRelativeTo(bgColorPicker);
				}
				else if(bgPickerPopPanel.isShowing())
					bgPickerPopPanel.hide();
				else
					bgPickerPopPanel.show();
			}
		});
		return bgColorPicker;
	}
	private MenuItem getForeColorPickerMenuItem() {
		final MenuItem foreColorPicker = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAgElEQVR42t2S4QqAMAiEfW//+t42XW4SozQhKuFQaH2duwC+VBxUCrj6wNmZZ4EQAMI7gYjIV0oD2zCkAOahMpCI6kCBiGxFm62nfnB5iZ0ndYjtGYH2qMNRurIDKmCHSb8F9Cuv5jTQZcI9pGk67bA7mEC90wrQJ3tMN5Lyz2sDT5T/KC/lAxMAAAAASUVORK5CYII='>"
				, true, (Command)null);
		final PopupPanel foreColorPickerPopPanel = new PopupPanel();
		foreColorPickerPopPanel.setAutoHideEnabled(true);
		
		foreColorPicker.setCommand(new Command(){
			@Override
			public void execute() {
				if(foreColorPickerPopPanel.getWidget() == null){
					VkColorPicker vkColorPicker = new VkColorPicker();
					foreColorPickerPopPanel.setWidget(vkColorPicker);
					vkColorPicker.addValueChangeHandler(new ValueChangeHandler<VkColorPicker>() {
						@Override
						public void onValueChange(ValueChangeEvent<VkColorPicker> event) {
							final Widget widget = invokingWidget;
							final String prior = DOM.getStyleAttribute(widget.getElement(), "color");
							final String color = event.getValue().getColor();
							applyCommand(new Command(){
								private final Command redoCommand = this;
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "color", color);
									undoStack.push(new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "color", prior);
											redoStack.push(redoCommand);
										}});
							}});
							foreColorPickerPopPanel.hide();
						}
					});
					foreColorPickerPopPanel.showRelativeTo(foreColorPicker);
				}
				else if(foreColorPickerPopPanel.isShowing())
					foreColorPickerPopPanel.hide();
				else
					foreColorPickerPopPanel.show();
			}
		});
		return foreColorPicker;
	}
	private TabPanel getStyleTabPanel() {
		final TabPanel styleTabPanel = new TabPanel();
		refreshStylePanel(styleTabPanel);
		return styleTabPanel;
	}
	private void refreshStylePanel(TabPanel styleTabPanel) {
		styleTabPanel.setPixelSize(500, 250);
		Panel panel = addDecorationPanel();
		styleTabPanel.add(panel, "Decorations");
		styleTabPanel.getTabBar().selectTab(0);
		panel.setSize("100%", "250px");
		panel = addFontPanel();
		styleTabPanel.add(panel, "Font & Text");
		panel.setSize("100%", "250px");
		panel = addColorPanel();
		styleTabPanel.add(panel, "Color & Background");
		panel.setSize("100%", "250px");
		panel = addMiscellaneousPanel();
		styleTabPanel.add(panel, "Miscellaneous");
		panel.setSize("100%", "250px");
	}
	
	private ScrollPanel addColorPanel() {
		ScrollPanel scrollColorHolderPanel = new ScrollPanel();				
		VerticalPanel colorVPanel = new VerticalPanel();
		scrollColorHolderPanel.add(colorVPanel);
		colorVPanel.setWidth("100%");
		colorVPanel.add(addStyleAttribute("Color","color"));
		colorVPanel.add(addStyleAttribute("Background Color","backgroundColor"));
		colorVPanel.add(addStyleAttribute("Background Image","backgroundImage"));
		colorVPanel.add(addStyleAttribute("Background Position","backgroundPosition"));
		colorVPanel.add(addStyleAttribute("Background Repeat","backgroundRepeat"));
		colorVPanel.add(addStyleAttribute("Background Attachment","backgroundAttachment"));
		return scrollColorHolderPanel;
	}
	private ScrollPanel addDecorationPanel() {
		ScrollPanel scrollDecorationHolderPanel = new ScrollPanel();				
		VerticalPanel decorationVPanel = new VerticalPanel();
		scrollDecorationHolderPanel.add(decorationVPanel);
		decorationVPanel.setWidth("100%");
		decorationVPanel.add(addStyleAttribute("Bottom Border","borderBottom"));
		decorationVPanel.add(addStyleAttribute("Top Border","borderTop"));
		decorationVPanel.add(addStyleAttribute("Left Border","borderLeft"));
		decorationVPanel.add(addStyleAttribute("Right Border","borderRight"));
		decorationVPanel.add(addStyleAttribute("Padding","padding"));
		decorationVPanel.add(addStyleAttribute("Margin","margin"));
		decorationVPanel.add(addStyleAttribute("Outline Color","outlineColor"));
		decorationVPanel.add(addStyleAttribute("Outline Style","outlineStyle"));
		decorationVPanel.add(addStyleAttribute("Outline Width","outlineWidth"));
		return scrollDecorationHolderPanel;
	}
	private ScrollPanel addFontPanel() {
		ScrollPanel scrollFontHolderPanel = new ScrollPanel();
		VerticalPanel fontVPanel = new VerticalPanel();
		scrollFontHolderPanel.add(fontVPanel);
		fontVPanel.setWidth("100%");
		fontVPanel.add(addStyleAttribute("Font Size", "fontSize"));
		fontVPanel.add(addStyleAttribute("Font Family","fontFamily"));
		fontVPanel.add(addStyleAttribute("Font Stretch","fontStretch"));
		fontVPanel.add(addStyleAttribute("Font Style","fontStyle"));
		fontVPanel.add(addStyleAttribute("Font Variant","fontVariant"));
		fontVPanel.add(addStyleAttribute("Font Weight","fontWeight"));
		fontVPanel.add(addStyleAttribute("Text Align","textAlign"));
		fontVPanel.add(addStyleAttribute("Text Decoration","textDecoration"));
		fontVPanel.add(addStyleAttribute("Text Decoration","textDecoration"));
		fontVPanel.add(addStyleAttribute("Text Indent","textIndent"));
		fontVPanel.add(addStyleAttribute("Text Shadow","textShadow"));
		fontVPanel.add(addStyleAttribute("Text Transform","textTransform"));
		fontVPanel.add(addStyleAttribute("Vertical Align","verticalAlign"));
		fontVPanel.add(addStyleAttribute("Word Spacing","wordSpacing"));
		fontVPanel.add(addStyleAttribute("Line Height","lineHeight"));
		fontVPanel.add(addStyleAttribute("Letter Spacing","letterSpacing"));
		fontVPanel.add(addStyleAttribute("Word Spacing","wordSpacing"));
		return scrollFontHolderPanel;
	}
	private Panel addMiscellaneousPanel() {
		ScrollPanel scrollMiscellaneousHolderPanel = new ScrollPanel();				
		VerticalPanel miscellaneousVPanel = new VerticalPanel();
		scrollMiscellaneousHolderPanel.add(miscellaneousVPanel);
		miscellaneousVPanel.setWidth("100%");
		miscellaneousVPanel.add(addStyleAttribute("Width","width"));
		miscellaneousVPanel.add(addStyleAttribute("Height","height"));
		miscellaneousVPanel.add(addStyleAttribute("Cursor","cursor"));
		miscellaneousVPanel.add(addStyleAttribute("Display","display"));
		miscellaneousVPanel.add(addStyleAttribute("Visibility","visibility"));
		miscellaneousVPanel.add(addStyleAttribute("Overflow X","overflowX"));
		miscellaneousVPanel.add(addStyleAttribute("Overflow Y","overflowY"));
		miscellaneousVPanel.add(addStyleAttribute("Z Index","zIndex"));
		miscellaneousVPanel.add(addStyleAttribute("Opacity","opacity"));
		miscellaneousVPanel.add(addStyleAttribute("Filter(IE)","filter"));
		miscellaneousVPanel.add(addStyleAttribute("border-collapse(table only)","borderCollapse"));
		return scrollMiscellaneousHolderPanel;
	}
	private Panel addStyleAttribute(String displayName, String attributeName) {
		HorizontalPanel styleAttribute = new HorizontalPanel();
		styleAttribute.setWidth("100%");
		styleAttribute.add(new InlineLabel(displayName));
		styleAttribute.setCellWidth(styleAttribute.getWidget(0), "50%");
		DOM.setStyleAttribute(styleAttribute.getElement(), "padding", "2px 0px");
		
		final TextBox styleAttributeTextBox = new TextBox();
		styleAttributeTextBox.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		DOM.setElementAttribute(styleAttributeTextBox.getElement(), "attributeName", attributeName);
		styleAttribute.add(styleAttributeTextBox);
		addChangeListenerToWidget(attributeName, styleAttributeTextBox);
		try{//IE throws an exception when it is queried about zIndex - GWT Bug 5548
			styleAttributeTextBox.setText(DOM.getStyleAttribute(invokingWidget.getElement(), attributeName));
		}catch(Exception e)	{
			if(invokingWidget != null)
				styleAttributeTextBox.setText(getIEZindex(invokingWidget));
		}
		return styleAttribute;
	}

	private void addChangeListenerToWidget(final String attribute, final TextBox textBox) {
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), attribute);
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						DOM.setStyleAttribute(widget.getElement(), attribute, textBox.getText());
						undoStack.push(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), attribute, prior);
								redoStack.push(redoCommand);
							}});
				}});
			}
		});
	}


	private native String getIEZindex(Widget widget) /*-{
		return widget.@com.google.gwt.user.client.ui.Widget::getElement()().style.zIndex + '';
	}-*/;

	private void addAttributesList(IVkWidget widget) {
		addAttributesList(widget, this, "Attributes");
	}
	
	private void addAttributesList(final IVkWidget widget, MenuBar menu, String menuName) {
		List<String> attributeList = VkDesignerUtil.getEngineMap().get(widget.getWidgetName()).getAttributesList((Widget) widget);
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
					//hideMenu();
				}
			};
			for (String attribute : attributeList)
				attributesMenu.addItem(attribute, attributeClickedCommand);
			menu.addItem(menuName, attributesMenu);
			final Widget parent = ((Widget)widget).getParent();
			if(parent instanceof IVkWidget) {
				attributesMenu.addItem(((IVkWidget)parent).getWidgetName(), new Command(){
					@Override
					public void execute() {
						prepareMenu((IVkWidget)parent);
						VkDesignerUtil.showToolbar((Widget) invokingWidget);
					}});
			}
		}
	}
	private MenuBar getWidgetsList(List<String> widgetsList) {
		if(widgetsList != null && widgetsList.size() > 0)
		{
			MenuBar widgetsMenu = new MenuBar(true){
				@Override
				public void selectItem(MenuItem item)
				{
					super.selectItem(item);
					selectedMenuItem = item;
				}
			};
			widgetsMenu.setStyleName("vkgwtdesigner-vertical-menu");
			widgetsMenu.setFocusOnHoverEnabled(false);				
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, getWidgetClickedCommand());
			}
			return widgetsMenu;
		}
		return null;
	}
	
	private MenuBar getPanelsList(List<String> widgetsList) {
		if(widgetsList != null && widgetsList.size() > 0)
		{
			MenuBar widgetsMenu = new MenuBar(true){
				@Override
				public void selectItem(MenuItem item) {
					super.selectItem(item);
					selectedMenuItem = item;
				}
			};
			widgetsMenu.setStyleName("vkgwtdesigner-vertical-menu");
			widgetsMenu.setFocusOnHoverEnabled(false);
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, getWidgetClickedCommand());
			}
			return widgetsMenu;
		}
		return null;
	}

	private Command getWidgetClickedCommand() {
		return new Command() {
			@Override
			public void execute() {
				final Widget panelWidget = invokingWidget;
				final String widgetName = selectedMenuItem.getText();
				final Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName);
				final int top = VkMenu.this.top	- panelWidget.getAbsoluteTop();
				final int left = VkMenu.this.left - panelWidget.getAbsoluteLeft();
				applyCommand(new Command(){
					private final Command redoCommand = this;
					@Override
					public void execute() {
						if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
							VkDesignerUtil.getEngine().addWidget(widget, (IVkPanel)panelWidget, top, left);
						undoStack.push(new Command(){
							@Override
							public void execute() {
								if(widget != null)
									widget.removeFromParent();
								redoStack.push(redoCommand);
							}});
				}});
				if(panelWidget instanceof AbsolutePanel)
					VkDesignerUtil.makeMovable(widget);
				//moveMenu(widget);
				//hideMenu();
			};
			/*private void moveMenu(Widget widget) {
				DOM.setStyleAttribute(VkMenu.this.getElement(), "top"
						, (VkMenu.this.top += widget.getOffsetHeight()) + "px");
			}*/
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
			else if(operationsList.get(i).equals(IEngine.MOVE))
				operationsMenu.addItem(IEngine.MOVE, new Command(){
					@Override
					public void execute() {
						VkDesignerUtil.makeMovable(invokingWidget);
					}});
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
						VkDesignerUtil.isLoadRunning = true;
						VkDesignerUtil.loadApplication(ta.getText());
						loadDialog.hide();
						undoStack.clear();
						redoStack.clear();
						VkDesignerUtil.isLoadRunning = false;
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
				Iterator<Widget> iterator = VkDesignerUtil.getDrawingPanel().iterator();
				while(iterator.hasNext()) {	
					Widget widget = iterator.next();
					if(widget != VkMenu.this)
						widget.removeFromParent();
				}
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
					final Widget panel = widget.getParent();
					final int top = widget.getElement().getOffsetTop();
					final int left = widget.getElement().getOffsetLeft();
					applyCommand(new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							Widget parent = widget.getParent();
							int index = -1;
							if(parent instanceof IndexedPanel.ForIsWidget)
								index = ((IndexedPanel.ForIsWidget)parent).getWidgetIndex(widget);
							prepareMenu((IVkWidget) widget.getParent());
							widget.removeFromParent();
							final int widgetIndex = index;
							undoStack.push(new Command(){
								@Override
								public void execute() {
									if(widgetIndex > -1)
										VkDesignerUtil.getEngine().addWidget(widget, (IVkPanel)panel, top, left, widgetIndex);
									else
										VkDesignerUtil.getEngine().addWidget(widget, (IVkPanel)panel, top, left);
									redoStack.push(redoCommand);
								}});
					}});
					VkDesignerUtil.hideMoveIcon();
			}};
		else
			return null;
	}
	
	Command getResizeCommand() {
		return new Command(){
			@Override
			public void execute() {
				final HTML draggingWidget = new HTML("&nbsp;");
				draggingWidget.addMouseDownHandler(new MouseDownHandler() {
					@Override
					public void onMouseDown(MouseDownEvent event) {
						event.stopPropagation();
					}
				});
				DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
				DOM.setStyleAttribute(draggingWidget.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
				draggingWidget.getElement().getStyle().setOpacity(0.2);
				RootPanel.get().add(draggingWidget);
				DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
				draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
				boolean isAttached = invokingWidget.isAttached();
				boolean isPopUpMenuBar = invokingWidget instanceof VkMenuBarVertical;
				//when menubars are added as submenus then on pressing resize they vanish which leads to top and left being evaluated to 0
				final int top = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getTop() : 
					(invokingWidget.getElement().getAbsoluteTop()/* - VkDesignerUtil.getDrawingPanel().getElement().getOffsetTop()*/);
				final int left = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getLeft() : invokingWidget.getElement().getAbsoluteLeft();
				DOM.setStyleAttribute(draggingWidget.getElement(), "top", top + "px");
				DOM.setStyleAttribute(draggingWidget.getElement(), "left", left + "px");
				//DOM.setCapture(draggingWidget.getElement());
				VkDesignerUtil.setCapture(draggingWidget);
				if(invokingWidget instanceof Frame)
					invokingWidget.setVisible(false);
				draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(MouseMoveEvent event) {
						DOM.setStyleAttribute(draggingWidget.getElement(), "width"
							, VkDesignerUtil.SNAP_TO_FIT_LEFT * ((event.getClientX() - left) / VkDesignerUtil.SNAP_TO_FIT_LEFT) + "px");
						DOM.setStyleAttribute(draggingWidget.getElement()
							, "height", VkDesignerUtil.SNAP_TO_FIT_TOP * ((event.getClientY() - top) / VkDesignerUtil.SNAP_TO_FIT_TOP) + "px");
					}
				});
				draggingWidget.addMouseUpHandler(new MouseUpHandler() {
					@Override
					public void onMouseUp(MouseUpEvent event) {
						if(invokingWidget instanceof VkFrame)
							invokingWidget.setVisible(true);
						//DOM.releaseCapture(draggingWidget.getElement());
						final Widget widget = invokingWidget;
						VkDesignerUtil.releaseCapture(draggingWidget);
						final int initialHeight = invokingWidget.getOffsetHeight() - (int)VkDesignerUtil.getDecorationsWidth(widget);
						final int initialWidth = invokingWidget.getOffsetWidth() - (int)VkDesignerUtil.getDecorationsHeight(widget);
						
						/*String borderTopWidth = DOM.getStyleAttribute(widget.getElement(), "borderTopWidth");
						String borderBottomWidth = DOM.getStyleAttribute(widget.getElement(), "borderBottomWidth");
						String borderLeftWidth = DOM.getStyleAttribute(widget.getElement(), "borderLeftWidth");
						String borderRightWidth = DOM.getStyleAttribute(widget.getElement(), "borderRightWidth");
						String padding = DOM.getStyleAttribute(widget.getElement(), "padding");
						String margin = DOM.getStyleAttribute(widget.getElement(), "margin");
						DOM.setStyleAttribute(widget.getElement(), "borderWidth", "0px");
						DOM.setStyleAttribute(widget.getElement(), "padding", "0px");
						DOM.setStyleAttribute(widget.getElement(), "margin", "0px");*/
						//This is necessary because offsetWidth contains all the decorations but when width is set, the figure is assumed to be independent 
						//of decorations
						final int finalWidth = draggingWidget.getOffsetWidth() /*- initialWidth + widget.getOffsetWidth()*/ - (int)VkDesignerUtil.getDecorationsWidth(widget);
						final int finalHeight = draggingWidget.getOffsetHeight() /*- initialHeight + widget.getOffsetHeight()*/ - (int)VkDesignerUtil.getDecorationsHeight(widget);
						/*DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", borderTopWidth);
						DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", borderBottomWidth);
						DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", borderLeftWidth);
						DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", borderRightWidth);
						DOM.setStyleAttribute(widget.getElement(), "padding", padding);
						DOM.setStyleAttribute(widget.getElement(), "margin", margin);*/
						draggingWidget.removeFromParent();
						if(finalWidth > 0)
							widget.setWidth(finalWidth + "px");
						if(finalHeight > 0)
							widget.setHeight(finalHeight + "px");
						VkDesignerUtil.showToolbar(invokingWidget);
						applyCommand(new Command(){
							final Command redoCommand = this;
							@Override
							public void execute() {
								if(finalWidth > 0)
									widget.setWidth(finalWidth + "px");
								if(finalHeight > 0)
									widget.setHeight(finalHeight + "px");
								undoStack.push(new Command(){
									@Override
									public void execute() {
										widget.setWidth(initialWidth + "px");
										widget.setHeight(initialHeight + "px");
										redoStack.push(redoCommand);
									}});
							}});
					}
				});
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
					final Panel prevParent = (Panel) ((Widget)tempCopyWidget).getParent();
					final Widget widget;						
					if(tempExecuteCutsRemoveCommand)
						widget = (Widget) tempCopyWidget;
					else
					{
						boolean isVkDesignerMode = VkDesignerUtil.isDesignerMode;
						VkDesignerUtil.isDesignerMode = false;//imp because some widgets like VkFlextable pop up dialogs in constructors
						widget = VkDesignerUtil.getEngine().getWidget((tempCopyWidget).getWidgetName());
						VkDesignerUtil.isDesignerMode = isVkDesignerMode;
						VkDesignerUtil.initDesignerEvents(widget, widgetEngine);//since this was not called during get Widget as then isDesignerMode = false 
						VkDesignerUtil.getEngineMap().get(tempCopyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
						
					}
					applyCommand(new Command(){
						@Override
						public void execute() {
							final Command redoCommand = this;
							if(tempCopyWidget != null)
							{					
								if(tempInvokingWidget instanceof IVkPanel)
								{
									final int top = widget.getElement().getOffsetTop();
									final int left = widget.getElement().getOffsetLeft();
									widget.removeFromParent();
									VkDesignerUtil.getEngine().addWidget(widget , ((IVkPanel)tempInvokingWidget), 8, 4);
									undoStack.push(new Command(){
										@Override
										public void execute() {
											if(tempExecuteCutsRemoveCommand)
											{
												widget.removeFromParent();
												VkDesignerUtil.getEngine().addWidget(widget , (IVkPanel) prevParent, top, left);
											}
											else
											{
												invokingWidget = widget;
												widget.removeFromParent();
												VkDesignerUtil.hideMoveIcon();
											}
											redoStack.push(redoCommand);
										}
									});
								}
								else
									Window.alert("Only a panel allows pasting a widget");
							}
					}});
					executeCutsRemoveCommand = false;
				}
			};
		} else
			return null;
	}
	/*private void hideMenu() {
		VkMenu.this.setVisible(false);
	}*/
	private void setPosition() {
		this.top = getElement().getAbsoluteTop();
		this.top = this.top - (this.top % VkDesignerUtil.SNAP_TO_FIT_TOP) + 200;
		this.left = getElement().getAbsoluteLeft();
		this.left = this.left - (this.left % VkDesignerUtil.SNAP_TO_FIT_LEFT) + 200;
	}
	
	public Stack<Command> getUndoStack() {
		return undoStack;
	}
	public Stack<Command> getRedoStack() {
		return redoStack;
	}
	private void applyCommand(Command command) {
		command.execute();
		redoStack.clear();
	}
}
