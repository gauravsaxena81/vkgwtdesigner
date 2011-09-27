package com.vk.gwt.designer.client.designer;

import java.util.Iterator;
import java.util.List;

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

public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget copyStyleWidget;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	//private int top;
	//private int left;
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
			//setPosition();
			initializeMenu(invokingWidget);
			clearItems();
			addItem("Operations", getOperationsItems(widgetEngine.getOperationsList((Widget) invokingWidget)));
			List<String> widgetsList = VkStateHelper.getInstance().getEngine().getWidgetsList((Widget) invokingWidget);
			MenuBar widgetsMenu = getWidgetsList(widgetsList);
			if(widgetsMenu != null)	{
				addSeparator();
				addItem("Widgets", widgetsMenu);
			}
			List<String> panelsList = VkStateHelper.getInstance().getEngine().getPanelsList(invokingWidget);
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
		this.widgetEngine = VkStateHelper.getInstance().getEngineMap().get(widget.getWidgetName());
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
				VkDesignerUtil.setLoadString(VkStateHelper.getInstance().getEngineMap().get(VkMainDrawingPanel.getInstance().getWidgetName()).serialize((IVkWidget) VkMainDrawingPanel.getInstance()));
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
		undoItem.setVisible(UndoHelper.getInstance().isUndoStackEmpty());
		redoItem.setVisible(UndoHelper.getInstance().isRedoStackEmpty());
	}
	Command getRedoCommand() {
		return redoCommand = redoCommand != null ? redoCommand : new Command(){
			@Override
			public void execute() {
				redoItem.setVisible(UndoHelper.getInstance().redo());
				//hideMenu();
			}};
	}
	Command getUndoCommand() {
		return undoCommand = undoCommand != null ? undoCommand : new Command(){
			@Override
			public void execute() {
				undoItem.setVisible(UndoHelper.getInstance().undo());
				//hideMenu();
			}};
	}
	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addHandler(handler, BlurEvent.getType());
	}
	/*public void setTop(int top) {
		this.top = top;
	}
	public void setLeft(int left) {
		this.left = left;
	}*/	
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
						final int prior = VkDesignerUtil.getPixelValue(widget.getElement(), "padding-left");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget.getElement()) - 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget.getElement()) + 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + "px");
									}});
					}});
	}
	private MenuItem getLeftIndentItem() {
		return new MenuItem("<img src='images/left-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget.getElement(), "padding-left");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget.getElement()) + 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "width", widget.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(widget.getElement()) - 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingLeft", prior + "px");
									}});
					}});
	}
	private MenuItem getTopIndentItem() {
		return new MenuItem("<img src='images/top-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget.getElement(), "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget.getElement()) - 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget.getElement()) + 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getBottomIndentItem() {
		return new MenuItem("<img src='images/bottom-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final int prior = VkDesignerUtil.getPixelValue(widget.getElement(), "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget.getElement()) + 5 + "px");
								DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "height", widget.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(widget.getElement()) - 5 + "px");
										DOM.setStyleAttribute(widget.getElement(), "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getUnderLineMenuItem() {
		return new MenuItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(invokingWidget.getElement(), "textDecoration").equals("underline"))
							DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "underline");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "");
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "textDecoration", prior);
							}});
			}});
	}
	private MenuItem getItalicMenuItem() {
		return new MenuItem("<span style='font-style: italic;'>I</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontStyle").equals("italic"))
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "italic");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "");
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "fontStyle", prior);
							}});
			}});
	}
	private MenuItem getBoldMenuItem() {
		
		return new MenuItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
			@Override
			public void execute() {
				final Widget widget = invokingWidget;
				final String prior = DOM.getStyleAttribute(widget.getElement(), "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(DOM.getStyleAttribute(invokingWidget.getElement(), "fontWeight").isEmpty())
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "bold");
						else
							DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "");
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "fontWeight", prior);
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
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(widget.getElement(), "textAlign", "right");
						}}, new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
								}});
				}});
	}
	private MenuItem getTextAlignCenterMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='>"
			, true, new Command(){
				@Override
				public void execute() {
					final Widget widget = invokingWidget;
					final String prior = DOM.getStyleAttribute(widget.getElement(), "textAlign");
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(widget.getElement(), "textAlign", "center");
						}}, new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
								}});
				}});
	}
	private MenuItem getTextAlignLeftMenuItem() {
		
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final String prior = DOM.getStyleAttribute(widget.getElement(), "textAlign");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "textAlign", "left");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(widget.getElement(), "textAlign", prior);
									}});
					}});
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
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(suffix != null)
							DOM.setStyleAttribute(widget.getElement(), attribute, "solid");
						else
							DOM.setStyleAttribute(widget.getElement(), attribute, "none");
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), attribute, prior);
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
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", borderWidth + "px");
						DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", borderWidth + "px");
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", prior);
								DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", prior);
							}});
			}};
	}
	private MenuItem getBorderColorPickerMenuItem() {
		final MenuItem borderColorPicker = new MenuItem("BC", true, (Command)null);
		final PopupPanel borderPickerPopPanel = new PopupPanel(true);
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
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "borderColor", color);
								}}, new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "borderColor", prior);
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
		final PopupPanel bgPickerPopPanel = new PopupPanel(true);
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
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "background", color);
								}}, new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "background", prior);
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
		final PopupPanel foreColorPickerPopPanel = new PopupPanel(true);
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
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(widget.getElement(), "color", color);
								}}, new Command(){
										@Override
										public void execute() {
											DOM.setStyleAttribute(widget.getElement(), "color", prior);
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
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(widget.getElement(), attribute, textBox.getText());
					}}, new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(widget.getElement(), attribute, prior);
							}});
			}});
	}


	private native String getIEZindex(Widget widget) /*-{
		return widget.@com.google.gwt.user.client.ui.Widget::getElement()().style.zIndex + '';
	}-*/;

	private void addAttributesList(IVkWidget widget) {
		addAttributesList(widget, this, "Attributes");
	}
	
	private void addAttributesList(final IVkWidget widget, MenuBar menu, String menuName) {
		List<String> attributeList = VkStateHelper.getInstance().getEngineMap().get(widget.getWidgetName()).getAttributesList((Widget) widget);
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
						ToolbarHelper.getInstance().showToolbar((Widget) invokingWidget);
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
				final Widget widget = VkStateHelper.getInstance().getEngine().getWidget(widgetName);
				//final int top = VkMenu.this.top	- panelWidget.getAbsoluteTop();
				//final int left = VkMenu.this.left - panelWidget.getAbsoluteLeft();
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
						MoveHelper.getInstance().makeMovable(invokingWidget);
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
					if(widget != VkMenu.this)
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
					final Widget panel = widget.getParent();
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
							prepareMenu((IVkWidget) widget.getParent());
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
					final Panel prevParent = (Panel) ((Widget)tempCopyWidget).getParent();
					final Widget widget;						
					if(tempExecuteCutsRemoveCommand)
						widget = (Widget) tempCopyWidget;
					else {
						boolean isVkDesignerMode = VkStateHelper.getInstance().isDesignerMode();
						VkStateHelper.getInstance().setDesignerMode(false);//imp because some widgets like VkFlextable pop up dialogs in constructors
						widget = VkStateHelper.getInstance().getEngine().getWidget((tempCopyWidget).getWidgetName());
						VkStateHelper.getInstance().setDesignerMode(isVkDesignerMode);
						InitializeHelper.getInstance().initDesignerEvents(widget, widgetEngine);//since this was not called during get Widget as then isDesignerMode = false 
						VkStateHelper.getInstance().getEngineMap().get(tempCopyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
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
												VkStateHelper.getInstance().getEngine().addWidget(widget , (IVkPanel) prevParent, top, left);
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
	/*private void hideMenu() {
		VkMenu.this.setVisible(false);
	}
	private void setPosition() {
		this.top = getElement().getAbsoluteTop();
		this.top = this.top - (this.top % VkDesignerUtil.SNAP_TO_FIT_TOP) + 200;
		this.left = getElement().getAbsoluteLeft();
		this.left = this.left - (this.left % VkDesignerUtil.SNAP_TO_FIT_LEFT) + 200;
	}*/
}
