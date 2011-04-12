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
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;
import com.vk.gwt.designer.client.widgets.colorpicker.VkColorPicker;

@SuppressWarnings("serial")
public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget copyStyleWidget;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private int top;
	private int left;
	private IWidgetEngine<? extends Widget> widgetEngine;
	private IVkWidget copyWidget;
	private AbsolutePanel toolBar;
	private Stack<Command> undoStack = new Stack<Command>(){
		@Override
		public Command push(Command c)
		{
			if(size() == 10)
				remove(9);
			redoStack.clear();
			return super.push(c);
		}
		@Override
		public Command pop()
		{
			if(size() > 0)
				return super.pop();
			else return null;
		}
	};
	private Stack<Command> redoStack = new Stack<Command>(){
		@Override
		public Command push(Command c)
		{
			if(size() == 10)
				remove(9);
			return super.push(c);
		}
		@Override
		public Command pop()
		{
			if(size() > 0)
				return super.pop();
			else return null;
		}
	};
	private Command copyCommand;
	private Command pasteCommand;
	private Command undoCommand;
	private Command redoCommand;
	private TabPanel styleTabPanel;
	private Command cutCommand;
	
	public VkMenu() {
		this(true);
		setStyleName("vkgwtdesigner-vertical-menu");
		setVisible(false);
		DOM.setStyleAttribute(getElement(), "position", "absolute");
		DOM.setStyleAttribute(getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(getElement(), "zIndex", Integer.MAX_VALUE + "");
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				hideMenu();
			}
		});
	}
	public VkMenu(boolean isMenu) {
		super(isMenu);
		setAutoOpen(true);
		setAnimationEnabled(true);
		sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
	}
	public void prepareMenu(Widget invokingWidget, IWidgetEngine<? extends Widget> widgetEngine) {
		initializeMenu(invokingWidget, widgetEngine);
		clearItems();
		addItem("Operations", getOperationsItems(widgetEngine.getOperationsList(invokingWidget)));
		List<String> widgetsList = VkDesignerUtil.getEngine().getWidgetsList(invokingWidget);
		MenuBar widgetsMenu = getWidgetsList(widgetsList);
		if(widgetsMenu != null)
		{
			addSeparator();
			addItem("Widgets", widgetsMenu);
		}
		List<String> panelsList = VkDesignerUtil.getEngine().getPanelsList(invokingWidget);
		MenuBar panelsMenu = getPanelsList(panelsList);
		if(panelsMenu != null)
		{
			addSeparator();
			addItem("Panels", panelsMenu);
		}
		addSeparator();
		List<String> attributesList = widgetEngine.getAttributesList(invokingWidget);
		addAttributesList(attributesList);
		addSpecificItems();
	}
	protected void initializeMenu(Widget invokingWidget, IWidgetEngine<? extends Widget> widgetEngine) {
		if(this.invokingWidget != null && VkDesignerUtil.isDesignerMode)
			this.invokingWidget.removeStyleName("vk-selectedWidget");
		this.invokingWidget = invokingWidget;
		if(VkDesignerUtil.isDesignerMode)
			this.invokingWidget.addStyleName("vk-selectedWidget");
		this.widgetEngine = widgetEngine;
		refreshStylePanelValues();
	}
	private void refreshStylePanelValues() {
		if(styleTabPanel != null){
			NodeList<Element> inputList = styleTabPanel.getElement().getElementsByTagName("INPUT");
			for(int i = 0; i < inputList.getLength(); i++)
			{
				String attributeName = DOM.getElementAttribute(
					(com.google.gwt.user.client.Element) inputList.getItem(i), "attributeName");
				String styleAttribute = DOM.getStyleAttribute(invokingWidget.getElement(), attributeName);
				if(!attributeName.trim().isEmpty())
					DOM.setElementProperty((com.google.gwt.user.client.Element) inputList.getItem(i), "value"
							, styleAttribute);
			}
		}
	}
	protected void addSpecificItems()
	{
		addSeparator();
		addItem("Toolbar", new Command(){
			@Override
			public void execute() {
				if(toolBar == null)
					makeToolbar();
				if(RootPanel.get().getWidgetIndex(toolBar) == -1)
					RootPanel.get().insert(toolBar, 0);
				toolBar.setVisible(true);
				hideMenu();
			}});
		addSeparator();
		addItem("Quick Preview", new Command() {
			@Override
			public void execute() {
				VkDesignerUtil.setLoadString(widgetEngine.serialize((IVkWidget) invokingWidget));
				String href = Window.Location.getHref();
				if(Window.Location.getParameterMap().size() == 0)
					href += "?isDesignerMode=false";
				else
					href += "&isDesignerMode=false";
				Window.open(href, "Preview", null);
				hideMenu();
			}
		});
		if(!undoStack.isEmpty())
		{
			addSeparator();
			addItem("Undo Ctrl+Z", getUndoCommand());
		}
		if(!redoStack.isEmpty())
		{
			addSeparator();
			addItem("Redo Ctrl+Y", getRedoCOmmand());
		}
	}
	Command getRedoCOmmand() {
		return redoCommand = redoCommand != null ? redoCommand : new Command(){
			@Override
			public void execute() {
				redoStack.pop().execute();
				hideMenu();
			}};
	}
	Command getUndoCommand() {
		return undoCommand = undoCommand != null ? undoCommand : new Command(){
			@Override
			public void execute() {
				undoStack.pop().execute();
				hideMenu();
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
	protected void makeToolbar()
	{
		toolBar = new AbsolutePanel();
		toolBar.addStyleName("vkgwtdesigner-toolbar");
		MenuBar styleMenu = new MenuBar();
		toolBar.add(styleMenu);
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
		styleMenu.addItem(getCopyStyleMenuItem());
		styleTabPanel = getStyleTabPanel();
		styleMenu.addItem(getStyleDialogMenuItem(styleTabPanel));
		
		styleMenu.addSeparator();
		styleMenu.addItem("X", new Command(){
			@Override
			public void execute() {
				toolBar.setVisible(false);
			}});
	}
	private MenuItem getCopyStyleMenuItem() {
		final MenuBar menuBar = new MenuBar(){
			public void onLoad()
			{
				super.onLoad();
				DOM.setStyleAttribute(getElement(), "visibility", "hidden");
			}
		};
		menuBar.addAttachHandler(new AttachEvent.Handler(){
			@Override
			public void onAttachOrDetach(AttachEvent event) {
				Window.alert("1");
			}});
		MenuItem copyStyleMenuItem = new MenuItem("CS", menuBar);
		return copyStyleMenuItem;
	}
	private MenuItem getUnderLineMenuItem() {
		return new MenuItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "textDecoration").equals("underline"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "underline");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "");
			}});
	}
	private MenuItem getItalicMenuItem() {
		return new MenuItem("<span style='font-style: italic;'>I</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontStyle").equals("italic"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "italic");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "");
			}});
	}
	private MenuItem getBoldMenuItem() {
		return new MenuItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontWeight").equals("bold"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "bold");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "");
			}});
	}
	private MenuItem getTextAlignRightMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPfxaNhOBqGIysMRwHpAADGwEe5v4tWjAAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "textAlign");
						final Widget widget = invokingWidget;
						new Command(){
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
							}}.execute();
					}
		});
	}
	private MenuItem getTextAlignCenterMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "textAlign");
						final Widget widget = invokingWidget;
						new Command(){
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
							}}.execute();
					}
		});
	}
	private MenuItem getTextAlignLeftMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "textAlign");
						final Widget widget = invokingWidget;
						new Command(){
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
							}}.execute();
					}
		});
	}
	private MenuItem getStyleDialogMenuItem(final TabPanel styleTabPanel) {
		final MenuItem styleMenu = new MenuItem("SM", (Command)null);
		final PopupPanel stylePickerPopPanel = new PopupPanel();
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
				final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "borderWidth");
				final String attribute;
				if(suffix != null)
					attribute = "border" + suffix + "Style";
				else
					attribute = "borderStyle";
				final Widget widget = invokingWidget;
				new Command(){
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
				}}.execute();
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
				new Command(){
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
				}}.execute();				
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
							final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "borderColor");
							final Widget widget = invokingWidget;
							final String color = event.getValue().getColor();
							new Command(){
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
							}}.execute();
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
							final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "background");
							final Widget widget = invokingWidget;
							final String color = event.getValue().getColor();
							new Command(){
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
							}}.execute();
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
							final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), "color");
							final Widget widget = invokingWidget;
							final String color = event.getValue().getColor();
							new Command(){
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
							}}.execute();
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
	@SuppressWarnings("deprecation")
	protected TabPanel getStyleTabPanel() {
		final TabPanel styleTabPanel = new TabPanel();
		refreshStylePanel(styleTabPanel);
		return styleTabPanel;
	}
	@SuppressWarnings("deprecation")
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
	private Panel addStyleAttribute(String displayName, String attributeName)
{
	HorizontalPanel styleAttribute = new HorizontalPanel();
	styleAttribute.setWidth("100%");
	styleAttribute.add(new InlineLabel(displayName));
	styleAttribute.setCellWidth(styleAttribute.getWidget(0), "50%");
	DOM.setStyleAttribute(styleAttribute.getElement(), "padding", "2px 0px");
	
	final TextBox styleAttributeTextBox = new TextBox();
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
				final String prior = DOM.getStyleAttribute(invokingWidget.getElement(), attribute);
				final Widget widget = invokingWidget;
				new Command(){
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
				}}.execute();
			}
		});
	}


	private native String getIEZindex(Widget widget) /*-{
		return widget.@com.google.gwt.user.client.ui.Widget::getElement()().style.zIndex + '';
	}-*/;

	protected void addAttributesList(List<String> attributesList)
	{
		if(attributesList != null && attributesList.size() > 0)
		{
			MenuBar attributesMenu = new MenuBar(true){
				@Override
				public void selectItem(MenuItem item)
				{
					super.selectItem(item);
					selectedMenuItem = item;
				}
			};
			attributesMenu.setStyleName("vkgwtdesigner-vertical-menu");
			attributesMenu.setFocusOnHoverEnabled(false);
			Command widgetClickedCommand = new Command() {
				@Override
				public void execute() {
					widgetEngine.applyAttribute(selectedMenuItem.getText(), invokingWidget);
					hideMenu();
				}
			};
			for (String attribute : attributesList) {
				attributesMenu.addItem(attribute, widgetClickedCommand);
			}
			addItem("Attributes", attributesMenu);
		}
	}
	
	protected MenuBar getWidgetsList(List<String> widgetsList)
	{
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
			Command widgetClickedCommand = new Command() {
				@Override
				public void execute() {
					final Widget widget = VkDesignerUtil.getEngine().getWidget(selectedMenuItem.getText());
					final Widget panelWidget = invokingWidget;
					final int top = VkMenu.this.top	- panelWidget.getAbsoluteTop();
					final int left = VkMenu.this.left - panelWidget.getAbsoluteLeft();
					new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
							{
								VkDesignerUtil.addWidget(widget, (IPanel)panelWidget, top, left);
								invokingWidget = widget;
							}
							undoStack.push(new Command(){
								@Override
								public void execute() {
									if(widget != null)
										widget.removeFromParent();
									redoStack.push(redoCommand);
								}});
					}}.execute();
					hideMenu();
				}
			};
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, widgetClickedCommand);
			}
			return widgetsMenu;
		}
		return null;
	}
	
	protected MenuBar getPanelsList(List<String> widgetsList)
	{
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
			Command widgetClickedCommand = new Command() {
				@Override
				public void execute() {
					final Widget widget = VkDesignerUtil.getEngine().getWidget(selectedMenuItem.getText());
					final Widget panelWidget = invokingWidget;
					final int top = VkMenu.this.top - panelWidget.getAbsoluteTop();
					final int left = VkMenu.this.left - panelWidget.getAbsoluteLeft();
					new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
							{
								VkDesignerUtil.addWidget(widget, (IPanel)panelWidget, top, left);
								invokingWidget = widget;
							}
							undoStack.push(new Command(){
								@Override
								public void execute() {
									if(widget != null)
										widget.removeFromParent();
									redoStack.push(redoCommand);
								}});
					}}.execute();
					hideMenu();
				}
			};
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, widgetClickedCommand);
			}
			return widgetsMenu;
		}
		return null;
	}

	protected MenuBar getOperationsItems(List<String> operationsList) {
		MenuBar operationsMenu = new MenuBar(true);
		operationsMenu.setFocusOnHoverEnabled(false);
		operationsMenu.setStyleName("vkgwtdesigner-vertical-menu");
		for (int i = 0; i < operationsList.size(); i++) 
		{
			if(operationsList.get(i).equals(IEngine.REMOVE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						final Widget widget = invokingWidget;
						final Widget panel = widget.getParent();
						final int top = invokingWidget.getElement().getOffsetTop();
						final int left = invokingWidget.getElement().getOffsetLeft();
						new Command(){
							private final Command redoCommand = this;
							@Override
							public void execute() {
								widget.removeFromParent();
								invokingWidget = null;
								undoStack.push(new Command(){
									@Override
									public void execute() {
										VkDesignerUtil.addWidget(widget, (IPanel)panel, top, left);
										invokingWidget = widget;
										redoStack.push(redoCommand);
									}});
						}}.execute();
					}});
			}
			else if(operationsList.get(i).equals(IEngine.RESIZE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						final HTML draggingWidget = new HTML("&nbsp;");
						DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
						draggingWidget.getElement().getStyle().setOpacity(0.2);
						VkDesignerUtil.getDrawingPanel().add(draggingWidget);
						DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
						draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
						boolean isAttached = invokingWidget.isAttached();
						boolean isPopUpMenuBar = invokingWidget instanceof VkMenuBarVertical;
						//when menubars are added as submenus then on pressing resize they vanish which leads to top and left being evaluated to 0
						final int top = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getTop() : 
							(invokingWidget.getElement().getAbsoluteTop() - VkDesignerUtil.getDrawingPanel().getElement().getOffsetTop());
						final int left = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getLeft() : invokingWidget.getElement().getAbsoluteLeft();
						DOM.setStyleAttribute(draggingWidget.getElement(), "top", top + "px");
						DOM.setStyleAttribute(draggingWidget.getElement(), "left", left + "px");
						DOM.setCapture(draggingWidget.getElement());
						draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
							@Override
							public void onMouseMove(MouseMoveEvent event) {
								int width = event.getClientX() - left - VkDesignerUtil.getDrawingPanel().getElement().getOffsetLeft() ;
								int height = event.getClientY() - top - VkDesignerUtil.getDrawingPanel().getElement().getOffsetTop();
								if((left + width) % VkDesignerUtil.SNAP_TO_FIT_LEFT == 0)
									DOM.setStyleAttribute(draggingWidget.getElement(), "width", width + "px");
								if((top + height) % VkDesignerUtil.SNAP_TO_FIT_TOP == 0)
									DOM.setStyleAttribute(draggingWidget.getElement(), "height", height + "px");
							}
						});
						draggingWidget.addMouseUpHandler(new MouseUpHandler() {
							@Override
							public void onMouseUp(MouseUpEvent event) {
								DOM.releaseCapture(draggingWidget.getElement());
								final int initialHeight = invokingWidget.getOffsetHeight();
								final int initialWidth = invokingWidget.getOffsetWidth();
								final Widget widget = invokingWidget;
								String borderTopWidth = DOM.getStyleAttribute(widget.getElement(), "borderTopWidth");
								String borderBottomWidth = DOM.getStyleAttribute(widget.getElement(), "borderBottomWidth");
								String borderLeftWidth = DOM.getStyleAttribute(widget.getElement(), "borderLeftWidth");
								String borderRightWidth = DOM.getStyleAttribute(widget.getElement(), "borderRightWidth");
								String padding = DOM.getStyleAttribute(widget.getElement(), "padding");
								String margin = DOM.getStyleAttribute(widget.getElement(), "margin");
								DOM.setStyleAttribute(widget.getElement(), "borderWidth", "0px");
								DOM.setStyleAttribute(widget.getElement(), "padding", "0px");
								DOM.setStyleAttribute(widget.getElement(), "margin", "0px");
								//This is necessary because offsetWidth contains all the decorations but when width is set, the figure is assumed to be independent 
								//of decorations
								final int finalWidth = draggingWidget.getOffsetWidth() - initialWidth 
									+ widget.getOffsetWidth();
								final int finalHeight = draggingWidget.getOffsetHeight() - initialHeight 
									+ widget.getOffsetHeight();
								DOM.setStyleAttribute(widget.getElement(), "borderTopWidth", borderTopWidth);
								DOM.setStyleAttribute(widget.getElement(), "borderBottomWidth", borderBottomWidth);
								DOM.setStyleAttribute(widget.getElement(), "borderLeftWidth", borderLeftWidth);
								DOM.setStyleAttribute(widget.getElement(), "borderRightWidth", borderRightWidth);
								DOM.setStyleAttribute(widget.getElement(), "padding", padding);
								DOM.setStyleAttribute(widget.getElement(), "margin", margin);
								draggingWidget.removeFromParent();
								new Command(){
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
									}}.execute();
							}
						});
						hideMenu();
					}});
			}
			else if(operationsList.get(i).equals(IEngine.CUT))
			{
				operationsMenu.addItem(operationsList.get(i) + " Ctrl+X", getCutCommand());
			}
			else if(operationsList.get(i).equals(IEngine.COPY))
			{
				operationsMenu.addItem(operationsList.get(i) + " Ctrl+C", getCopyCommand());
			}
			else if(operationsList.get(i).equals(IEngine.COPY_STYLE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						copyStyleWidget = invokingWidget;
					}
				});
			}
			else if(operationsList.get(i).equals(IEngine.PASTE_STYLE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						String top = DOM.getStyleAttribute(invokingWidget.getElement(), "top");
						String left = DOM.getStyleAttribute(invokingWidget.getElement(), "left");
						DOM.setElementAttribute(invokingWidget.getElement(), "style", DOM.getElementAttribute(copyStyleWidget.getElement(), "style"));
						DOM.setStyleAttribute(invokingWidget.getElement(), "left", left);
						DOM.setStyleAttribute(invokingWidget.getElement(), "top", top);
					}
				});
			}
			else if(operationsList.get(i).equals(IEngine.PASTE))
			{
				operationsMenu.addItem(operationsList.get(i) + " Ctrl+V", getPasteCommand());
			}
			else if(operationsList.get(i).equals(IEngine.SAVE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						final DialogBox saveDialog = new DialogBox();
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
				});
			}
			else if(operationsList.get(i).equals(IEngine.LOAD))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						final DialogBox loadDialog = new DialogBox();
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
								Iterator<Widget> iterator = VkDesignerUtil.getDrawingPanel().iterator();
								while(iterator.hasNext())
								{	
									Widget widget = iterator.next();
									if(widget != VkMenu.this)
										widget.removeFromParent();
								}
								VkDesignerUtil.loadApplication(ta.getText());
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
				});
			}
		}
		return operationsMenu;
	}	

	Command getCopyCommand() {
		return copyCommand = copyCommand != null ? copyCommand :  new Command(){
			@Override
			public void execute() {
				copyWidget = (IVkWidget)invokingWidget;
			}
		};
	}
	Command getCutCommand(){
		return cutCommand = cutCommand != null ? cutCommand :  new Command(){
			@Override
			public void execute() {
				getCopyCommand().execute();
			}
		};
	};
	Command getPasteCommand() {
		return pasteCommand = pasteCommand != null ? pasteCommand : new Command(){
			@Override
			public void execute() {
				if(copyWidget != null)
				{
					Widget widget = VkDesignerUtil.getEngine().getWidget((copyWidget).getWidgetName());
					VkDesignerUtil.isDesignerMode = false;
					VkDesignerUtil.getEngineMap().get(copyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
					VkDesignerUtil.isDesignerMode = true;
					if(invokingWidget instanceof Panel)
						if(VkMenu.this.top > 0 && VkMenu.this.left > 0)
							VkDesignerUtil.addWidget(widget , ((IPanel)invokingWidget)
								, VkMenu.this.top - invokingWidget.getAbsoluteTop()
									, VkMenu.this.left - invokingWidget.getAbsoluteTop());
						else
							VkDesignerUtil.addWidget(widget , ((IPanel)invokingWidget));
					else
						Window.alert("Only a panel allows pasting a widget");
				}
			}
		};
	}
	protected void hideMenu() {
		if(invokingWidget != null)
		{
			this.top = getElement().getAbsoluteTop();
			this.top = this.top - (this.top % VkDesignerUtil.SNAP_TO_FIT_TOP);
			this.left = getElement().getAbsoluteLeft();
			this.left = this.left - (this.left % VkDesignerUtil.SNAP_TO_FIT_LEFT);
		}
		VkMenu.this.setVisible(false);
	}
	public Stack<Command> getUndoStack() {
		return undoStack;
	}
	public Stack<Command> getRedoStack() {
		return redoStack;
	}
}
