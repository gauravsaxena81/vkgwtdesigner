package com.vk.gwt.designer.client.designer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.stylesheets.CssImpl;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IEventRegister;
import com.vk.gwt.designer.client.ui.widget.colorpicker.VkColorPicker;

public class StyleToolbar extends Composite{
	private static StyleToolbar styleToolbar = new StyleToolbar();
	private static Element invokingElement;
	private PopupPanel stylePickerPopPanel;
	private HandlerRegistration selectElementClickHandlerRegistration;
	private CssImpl cssImpl;
	private String className;
	
	private StyleToolbar(){
		initWidget(new MenuBar());
		makeToolbar();
	}
	public static StyleToolbar getInstance(){
		if(invokingElement != null)
			StyleMenu.getInstance().refreshStylePanel(invokingElement);
		return styleToolbar;
	} 
	private void makeToolbar() {
		final MenuBar toolBar = (MenuBar) getWidget();
		toolBar.setStyleName("vkgwtdesigner-toolbar");
		toolBar.setAutoOpen(true);
		toolBar.addItem(getBoldMenuItem());
		toolBar.addItem(getItalicMenuItem());
		toolBar.addItem(getUnderLineMenuItem());
		toolBar.addItem(getBgColorPickerMenuItem());
		toolBar.addItem(getForeColorPickerMenuItem());
		toolBar.addItem(getBorderColorPickerMenuItem());
		toolBar.addItem(getBorderWidthPickerMenuItem());
		toolBar.addItem(getBorderEdgePickerMenuItem());
		toolBar.addItem(getTextAlignLeftMenuItem());
		toolBar.addItem(getTextAlignCenterMenuItem());
		toolBar.addItem(getTextAlignRightMenuItem());
		toolBar.addItem(getRightIndentItem());
		toolBar.addItem(getLeftIndentItem());
		toolBar.addItem(getTopIndentItem());
		toolBar.addItem(getBottomIndentItem());
		toolBar.addItem(getIncreaseFontSizeItem());
		toolBar.addItem(getDecreaseFontSizeItem());
		StyleMenu.getInstance().refreshStylePanel(invokingElement);
		toolBar.addItem(getStyleDialogMenuItem(StyleMenu.getInstance()));
		
		toolBar.addSeparator();
		toolBar.addItem("SE", new Command(){
			@Override
			public void execute() {
				selectElementClickHandlerRegistration = VkMainDrawingPanel.getInstance().addDomHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						invokingElement = (Element) Element.as(event.getNativeEvent().getEventTarget());
						className = invokingElement.getClassName();
						if(className != null && !className.isEmpty()) {
							String[] classNameArray = className.split(" ");
							showClassChoser(classNameArray);
						}
						DOM.setStyleAttribute(invokingElement, "outline", "dashed 1px blue");
						new Timer(){
							@Override
							public void run() {
								DOM.setStyleAttribute(invokingElement, "outline", "");								
							}}.schedule(1000);
							if(cssImpl == null)
								cssImpl = GWT.create(CssImpl.class);
						selectElementClickHandlerRegistration.removeHandler();
					}
				}, ClickEvent.getType());				
			}});
	}
	private MenuItem getDecreaseFontSizeItem() {
		return new MenuItem("A-",new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final int prior = VkDesignerUtil.getPixelValue(invokingElement, "font-size");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(element, "fontSize", prior - 2 + "px");
					}
				}, new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(element, "fontSize", prior + "px");
						}});
			}});
	}
	private MenuItem getIncreaseFontSizeItem() {
		return new MenuItem("A+",new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final int prior = VkDesignerUtil.getPixelValue(invokingElement, "font-size");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(element, "fontSize", prior + 2 + "px");
					}
				}, new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(element, "fontSize", prior + "px");
						}});
			}});
	}
	private void showClassChoser(String[] classNameArray) {
		ListBox listBox = new ListBox();
		for(int i = 0; i < classNameArray.length; i++)
			listBox.addItem(classNameArray[i], classNameArray[i]);
		VkDesignerUtil.showAddListDialog("Choose css class to modify", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String chosenClassName) {
				className = chosenClassName;
			}
		});
	}
	private void setAttribute(Element element, String className, String attribute, String value) {
		if(className == null)
			DOM.setStyleAttribute(element, attribute, value);
		else
			editCss(className, attribute, value);
	}
	private void editCss(String className, String attribute, String value) {
		if(!cssImpl.editRule("." + className, attribute, value))
			cssImpl.addRule("." + className, "{" + attribute + " : " + value + "}");
	}
	private MenuItem getRightIndentItem() {
		return new MenuItem("<img src='images/right-indent.ico' height=16 width=16'>"
		, true, new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-left");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) - 5 + "px");
						setAttribute(element, finalClassName, "paddingLeft", prior + 5 + "px");
					}
				}, new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) + 5 + "px");
							setAttribute(element, finalClassName, "paddingLeft", prior + "px");
						}});
			}});
	}
	private MenuItem getLeftIndentItem() {
		return new MenuItem("<img src='images/left-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-left");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) + 5 + "px");
								setAttribute(element, finalClassName, "paddingLeft", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) - 5 + "px");
										setAttribute(element, finalClassName, "paddingLeft", prior + "px");
									}});
					}});
	}
	private MenuItem getTopIndentItem() {
		return new MenuItem("<img src='images/top-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) - 5 + "px");
								setAttribute(element, finalClassName, "paddingTop", prior + 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) + 5 + "px");
										setAttribute(element, finalClassName, "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getBottomIndentItem() {
		return new MenuItem("<img src='images/bottom-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) + 5 + "px");
								setAttribute(element, finalClassName, "paddingTop", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) - 5 + "px");
										setAttribute(element, finalClassName, "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getUnderLineMenuItem() {
		return new MenuItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final String prior = DOM.getStyleAttribute(invokingElement, "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(element, "textDecoration").equals("underline"))
							setAttribute(element, finalClassName, "textDecoration", "underline");
						else
							setAttribute(element, finalClassName, "textDecoration", "");
					}}, new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, "textDecoration", prior);
							}});
			}});
	}
	private MenuItem getItalicMenuItem() {
		return new MenuItem("<span style='font-style: italic;'>I</span>", true, new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final String prior = DOM.getStyleAttribute(invokingElement, "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(!DOM.getStyleAttribute(element, "fontStyle").equals("italic"))
							setAttribute(element, finalClassName, "fontStyle", "italic");
						else
							setAttribute(element, finalClassName, "fontStyle", "");
					}}, new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, "fontStyle", prior);
							}});
			}});
	}
	private MenuItem getBoldMenuItem() {
		
		return new MenuItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final String prior = DOM.getStyleAttribute(invokingElement, "fontWeight");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(DOM.getStyleAttribute(element, "fontWeight").isEmpty())
							setAttribute(element, finalClassName, "fontWeight", "bold");
						else
							DOM.setStyleAttribute(element, "fontWeight", "");
					}}, new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, "fontWeight", prior);
							}});
			}});
	}
	private MenuItem getTextAlignRightMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPfxaNhOBqGIysMRwHpAADGwEe5v4tWjAAAAABJRU5ErkJggg=='>"
			, true, new Command(){
				@Override
				public void execute() {
					final Element element = invokingElement;
					final String finalClassName = className;
					final String prior = DOM.getStyleAttribute(invokingElement, "textAlign");
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							setAttribute(element, finalClassName, "textAlign", "right");
						}}, new Command(){
								@Override
								public void execute() {
									setAttribute(element, finalClassName, "textAlign", prior);
								}});
				}});
	}
	private MenuItem getTextAlignCenterMenuItem() {
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='>"
			, true, new Command(){
				@Override
				public void execute() {
					final Element element = invokingElement;
					final String finalClassName = className;
					final String prior = DOM.getStyleAttribute(invokingElement, "textAlign");
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							setAttribute(element, finalClassName, "textAlign", "center");
						}}, new Command(){
								@Override
								public void execute() {
									setAttribute(element, finalClassName, "textAlign", prior);
								}});
				}});
	}
	private MenuItem getTextAlignLeftMenuItem() {
		
		return new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final String prior = DOM.getStyleAttribute(invokingElement, "textAlign");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, "textAlign", "left");
							}}, new Command(){
									@Override
									public void execute() {
										setAttribute(element, finalClassName, "textAlign", prior);
									}});
					}});
	}
	private MenuItem getStyleDialogMenuItem(final StyleMenu styleMenu) {
		final MenuItem styleMenuItem = new MenuItem("SM", (Command)null);
		styleMenu.addDomHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_ESCAPE)
					stylePickerPopPanel.hide();
			}
		}, KeyDownEvent.getType());
		stylePickerPopPanel = new PopupPanel();
		stylePickerPopPanel.setWidget(styleMenu);
		stylePickerPopPanel.hide();
		DOM.setStyleAttribute(stylePickerPopPanel.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		styleMenuItem.setCommand(new Command(){
			@Override
			public void execute() {
				if(stylePickerPopPanel.isShowing())
					stylePickerPopPanel.hide();
				else
					stylePickerPopPanel.showRelativeTo(styleMenuItem);
			}
		});
		styleMenuItem.setTitle("Detailed Style Dialog");
		return styleMenuItem;
	}
	private MenuItem getBorderEdgePickerMenuItem() {
		MenuBar borderEdgeMenu = new MenuBar(true);
		MenuItem borderEdgePicker = new MenuItem("BE", true, borderEdgeMenu);
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px black; border-left: solid 1px #DDDDDD; border-right: solid 1px #DDDDDD; border-bottom: solid 1px #DDDDDD;'></div>"
		, true, setBorderEdge("Top"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px black; border-right: solid 1px #DDDDDD; border-bottom: solid 1px #DDDDDD;'></div>"
		, true, setBorderEdge("Left"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px #DDDDDD; border-right: solid 1px black; border-bottom: solid 1px #DDDDDD;'></div>"
		, true, setBorderEdge("Right"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border-top: solid 1px #DDDDDD; border-left: solid 1px #DDDDDD; border-right: solid 1px #DDDDDD; border-bottom: solid 1px black;'></div>"
		, true, setBorderEdge("Bottom"));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border: solid 1px #DDDDDD;'></div>", true, setBorderEdge(null));
		borderEdgeMenu.addItem("<div style='width: 10px; height: 10px; border: solid 1px black;'></div>", true, setBorderEdge(""));
		return borderEdgePicker;
	}
	private Command setBorderEdge(final String suffix) {
		return new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final String prior = DOM.getStyleAttribute(invokingElement, "borderWidth");
				final String attribute;
				if(suffix != null)
					attribute = "border" + suffix + "Style";
				else
					attribute = "borderStyle";
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						if(suffix != null)
							setAttribute(element, finalClassName, attribute, "solid");
						else
							setAttribute(element, finalClassName, attribute, "none");
					}}, new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, attribute, prior);
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
				final String prior = DOM.getStyleAttribute(invokingElement, "borderWidth");
				final Element element = invokingElement;
				final String finalClassName = className;
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						setAttribute(element, finalClassName, "borderTopWidth", borderWidth + "px");
						setAttribute(element, finalClassName, "borderLeftWidth", borderWidth + "px");
						setAttribute(element, finalClassName, "borderRightWidth", borderWidth + "px");
						setAttribute(element, finalClassName, "borderBottomWidth", borderWidth + "px");
					}}, new Command(){
							@Override
							public void execute() {
								setAttribute(element, finalClassName, "borderTopWidth", prior);
								setAttribute(element, finalClassName, "borderLeftWidth", prior);
								setAttribute(element, finalClassName, "borderRightWidth", prior);
								setAttribute(element, finalClassName, "borderBottomWidth", prior);
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
							final Element element = invokingElement;
							final String finalClassName = className;
							final String prior = DOM.getStyleAttribute(invokingElement, "borderColor");
							final String color = event.getValue().getColor();
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									setAttribute(element, finalClassName, "borderColor", color);
								}}, new Command(){
										@Override
										public void execute() {
											setAttribute(element, finalClassName, "borderColor", prior);
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
							final Element element = invokingElement;
							final String finalClassName = className;
							final String prior = DOM.getStyleAttribute(invokingElement, "background");
							final String color = event.getValue().getColor();
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									setAttribute(element, finalClassName, "background", color);
								}}, new Command(){
										@Override
										public void execute() {
											setAttribute(element, finalClassName, "background", prior);
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
							final Element element = invokingElement;
							final String finalClassName = className;
							final String prior = DOM.getStyleAttribute(invokingElement, "color");
							final String color = event.getValue().getColor();
							UndoHelper.getInstance().doCommand(new Command(){
								@Override
								public void execute() {
									setAttribute(element, finalClassName, "color", color);
								}}, new Command(){
										@Override
										public void execute() {
											setAttribute(element, finalClassName, "color", prior);
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
	public void setWidget(Widget w){
		className = null;
		StyleToolbar.invokingElement = w.getElement();
	}
	public void setAttribute(final String attribute, final String text) {
		final Element element = invokingElement;
		final String finalClassName = className;
		final String prior = DOM.getStyleAttribute(invokingElement, attribute);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				setAttribute(element, finalClassName, attribute, text);
			}}, new Command(){
					@Override
					public void execute() {
						setAttribute(element, finalClassName, attribute, prior);
					}});
	}
}
