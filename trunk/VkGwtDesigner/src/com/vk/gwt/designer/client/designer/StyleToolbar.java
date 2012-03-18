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
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.stylesheets.CssImpl;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.ui.widget.colorpicker.VkColorPicker;

public class StyleToolbar extends Composite{
	private static StyleToolbar styleToolbar = new StyleToolbar();
	private static Element invokingElement;
	private PopupPanel stylePickerPopPanel;
	private HandlerRegistration selectElementClickHandlerRegistration;
	private CssImpl cssImpl;
	private String className;
	private MenuItem boldMenuItem;
	private MenuItem italicsMenuItem;
	private MenuItem underlineMenuItem;
	private MenuItem bgColorPickerMenuItem;
	private MenuItem foreColorPickerMenuItem;
	private MenuItem borderColorPickerMenuItem;
	private MenuItem borderWidthPickerMenuItem;
	private MenuItem borderEdgePickerMenuItem;
	private MenuItem textAlignLeftMenuItem;
	private MenuItem textAlignCenterMenuItem;
	private MenuItem textAlignRightMenuItem;
	private MenuItem rightIndentMenuItem;
	private MenuItem leftIndentMenuItem;
	private MenuItem topIndentMenuItem;
	private MenuItem bottomIndentMenuItem;
	private MenuItem increaseFontSizeMenuItem;
	private MenuItem decreaseFontSizeMenuItem;
	private MenuItem fontChooserMenuItem;
	private MenuItem styleDialogMenuItem;
	private MenuItem selectElementForCssEdit;
	private boolean boldButtonVisible = true;
	private boolean italicsButtonVisible = true;
	private boolean underlineButtonVisible = true;
	private boolean bgColorPickerButtonVisible = true;
	private boolean foreColorPickerButtonVisible = true;
	private boolean borderColorPickerButtonVisible = true;
	private boolean borderWidthPickerButtonVisible = true;
	private boolean borderEdgePickerButtonVisible = true;
	private boolean textAlignLeftButtonVisible = true;
	private boolean textAlignCenterButtonVisible = true;
	private boolean textAlignRightButtonVisible = true;
	private boolean rightIndentButtonVisible = true;
	private boolean leftIndentButtonVisible = true;
	private boolean topIndentButtonVisible = true;
	private boolean bottomIndentButtonVisible = true;
	private boolean increaseFontSizeButtonVisible = true;
	private boolean decreaseFontSizeButtonVisible = true;
	private boolean fontChooserButtonVisible = true;
	private boolean styleDialogButtonVisible = true;
	private boolean selectElementForCssEditButtonVisible = true;
	
	private StyleToolbar(){
		initWidget(new MenuBar(){
			public void onBrowserEvent(Event event) {
				MenuItem item = findItem(DOM.eventGetTarget(event));
		        if (item != null && (item.getCommand() != null || item.getSubMenu() != null))
		        	super.onBrowserEvent(event);
			}
			private MenuItem findItem(Element hItem) {
			    for (MenuItem item : getItems()) {
			      if (DOM.isOrHasChild(item.getElement(), hItem)) {
			        return item;
			      }
			    }
			    return null;
			  }
		});
		makeToolbar();
	}
	public static StyleToolbar getInstance(){
		return styleToolbar;
	} 
	private void makeToolbar() {
		final MenuBar toolBar = (MenuBar) getWidget();
		toolBar.setStyleName("vkgwtdesigner-toolbar");
		toolBar.addItem(getBoldMenuItem());
		boldMenuItem.setVisible(boldButtonVisible);			
		toolBar.addItem(getItalicMenuItem());
		italicsMenuItem.setVisible(italicsButtonVisible);
		toolBar.addItem(getUnderLineMenuItem());
		underlineMenuItem.setVisible(underlineButtonVisible);
		toolBar.addItem(getBgColorPickerMenuItem());
		bgColorPickerMenuItem.setVisible(bgColorPickerButtonVisible);
		toolBar.addItem(getForeColorPickerMenuItem());
		foreColorPickerMenuItem.setVisible(foreColorPickerButtonVisible);
		toolBar.addItem(getBorderColorPickerMenuItem());
		borderColorPickerMenuItem.setVisible(borderColorPickerButtonVisible);
		toolBar.addItem(getBorderWidthPickerMenuItem());
		borderWidthPickerMenuItem.setVisible(borderWidthPickerButtonVisible);
		toolBar.addItem(getBorderEdgePickerMenuItem());
		borderEdgePickerMenuItem.setVisible(borderEdgePickerButtonVisible);
		toolBar.addItem(getTextAlignLeftMenuItem());
		textAlignLeftMenuItem.setVisible(textAlignLeftButtonVisible);
		toolBar.addItem(getTextAlignCenterMenuItem());
		textAlignCenterMenuItem.setVisible(textAlignCenterButtonVisible);
		toolBar.addItem(getTextAlignRightMenuItem());
		textAlignRightMenuItem.setVisible(textAlignRightButtonVisible);
		toolBar.addItem(getRightIndentMenuItem());
		rightIndentMenuItem.setVisible(rightIndentButtonVisible);
		toolBar.addItem(getLeftIndentMenuItem());
		leftIndentMenuItem.setVisible(leftIndentButtonVisible);
		toolBar.addItem(getTopIndentMenuItem());
		topIndentMenuItem.setVisible(topIndentButtonVisible);
		toolBar.addItem(getBottomIndentMenuItem());
		bottomIndentMenuItem.setVisible(bottomIndentButtonVisible);
		toolBar.addItem(getIncreaseFontSizeMenuItem());
		increaseFontSizeMenuItem.setVisible(increaseFontSizeButtonVisible);
		toolBar.addItem(getDecreaseFontSizeMenuItem());
		decreaseFontSizeMenuItem.setVisible(decreaseFontSizeButtonVisible);
		toolBar.addItem(getFontChooserMenuItem());
		fontChooserMenuItem.setVisible(fontChooserButtonVisible);
		StyleMenu.getInstance().refreshStylePanel(invokingElement);
		toolBar.addItem(getStyleDialogMenuItem(StyleMenu.getInstance()));
		styleDialogMenuItem.setVisible(styleDialogButtonVisible);
		toolBar.addItem(getSelectElementForCssEdit());
		selectElementForCssEdit.setVisible(selectElementForCssEditButtonVisible);
	}
	private MenuItem getSelectElementForCssEdit() {
		return selectElementForCssEdit = selectElementForCssEdit != null ? selectElementForCssEdit : new MenuItem("SE", new Command(){
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
								selectElementClickHandlerRegistration.removeHandler();
							}}.schedule(1000);
							if(cssImpl == null)
								cssImpl = GWT.create(CssImpl.class);
					}
				}, ClickEvent.getType());				
			}});
	}
	private MenuItem getFontChooserMenuItem() {
		if(fontChooserMenuItem == null) {
			MenuBar fontChooser = new MenuBar(true);
			fontChooserMenuItem = new MenuItem("Font", fontChooser);
			fontChooser.addItem("<span style='font-family:\"Arial\"'>Arial", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Arial, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Arial Black\"'>Arial Black", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Arial Black, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Arial Narrow\"'>Arial Narrow", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Arial Narrow, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Calibri\"'>Calibri", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Calibri, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Comic Sans MS\"'>Comic Sans MS", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Comic Sans MS, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Consolas\"'>Consolas", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Consolas, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Corsiva\"'>Corsiva", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Corsiva, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Courier New\"'>Courier New", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Courier New, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Droid Sans\"'>Droid Sans", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Droid Sans, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Droid Serif\"'>Droid Serif", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Droid Serif, serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Garamond\"'>Garamond", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Garamond, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Georgia\"'>Georgia", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Georgia, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Tahoma\"'>Tahoma", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Tahoma, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Times New Roman\"'>Times New Roman", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Times New Roman, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Trebuchet MS\"'>Trebuchet MS", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Trebuchet MS, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Ubuntu\"'>Ubuntu", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Ubuntu, sans-serif");
				}
			});
			fontChooser.addItem("<span style='font-family:\"Verdana\"'>Verdana", true, new Command(){
				@Override
				public void execute() {
					DOM.setStyleAttribute(invokingElement, "fontFamily", "Verdana, sans-serif");
				}
			});
		}
		return fontChooserMenuItem;
	}
	private MenuItem getDecreaseFontSizeMenuItem() {
		return decreaseFontSizeMenuItem = decreaseFontSizeMenuItem != null ? decreaseFontSizeMenuItem : new MenuItem("A-",new Command(){
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
	private MenuItem getIncreaseFontSizeMenuItem() {
		return increaseFontSizeMenuItem = increaseFontSizeMenuItem != null ? increaseFontSizeMenuItem : new MenuItem("A+",new Command(){
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
		VkDesignerUtil.showAddListDialog("Choose css class to modify", listBox, new IDialogCallback() {
			@Override
			public void save(String chosenClassName) {
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
	private MenuItem getRightIndentMenuItem() {
		return rightIndentMenuItem = rightIndentMenuItem != null ? rightIndentMenuItem : new MenuItem("<img src='images/right-indent.png' height=16 width=16'>"
		, true, new Command(){
			@Override
			public void execute() {
				final Element element = invokingElement;
				final String finalClassName = className;
				final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-left");
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) - getPaddingAdjustmentForDivs(element) + "px");
						setAttribute(element, finalClassName, "paddingLeft", prior + 5 + "px");
					}
				}, new Command(){
						@Override
						public void execute() {
							DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) + getPaddingAdjustmentForDivs(element) + "px");
							setAttribute(element, finalClassName, "paddingLeft", prior + "px");
						}});
			}});
	}
	private MenuItem getLeftIndentMenuItem() {
		return leftIndentMenuItem = leftIndentMenuItem != null ? leftIndentMenuItem : new MenuItem("<img src='images/left-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-left");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) + getPaddingAdjustmentForDivs(element) + "px");
								setAttribute(element, finalClassName, "paddingLeft", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "width", element.getOffsetWidth() - VkDesignerUtil.getDecorationsWidth(element) - getPaddingAdjustmentForDivs(element) + "px");
										setAttribute(element, finalClassName, "paddingLeft", prior + "px");
									}});
					}});
	}
	private int getPaddingAdjustmentForDivs(Element elem) {
		if(elem.getTagName().equalsIgnoreCase("DIV"))
			return 5;
		else 
			return 0;
	}
	private MenuItem getTopIndentMenuItem() {
		return topIndentMenuItem = topIndentMenuItem != null ? topIndentMenuItem : new MenuItem("<img src='images/top-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) - getPaddingAdjustmentForDivs(element) + "px");
								setAttribute(element, finalClassName, "paddingTop", prior + 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) + getPaddingAdjustmentForDivs(element) + "px");
										setAttribute(element, finalClassName, "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getBottomIndentMenuItem() {
		return bottomIndentMenuItem = bottomIndentMenuItem != null ? bottomIndentMenuItem : new MenuItem("<img src='images/bottom-indent.png' height=16 width=16'>"
				, true, new Command(){
					@Override
					public void execute() {
						final Element element = invokingElement;
						final String finalClassName = className;
						final int prior = VkDesignerUtil.getPixelValue(invokingElement, "padding-top");
						UndoHelper.getInstance().doCommand(new Command(){
							@Override
							public void execute() {
								DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) + getPaddingAdjustmentForDivs(element) + "px");
								setAttribute(element, finalClassName, "paddingTop", prior - 5 + "px");
							}}, new Command(){
									@Override
									public void execute() {
										DOM.setStyleAttribute(element, "height", element.getOffsetHeight() - VkDesignerUtil.getDecorationsHeight(element) - getPaddingAdjustmentForDivs(element) + "px");
										setAttribute(element, finalClassName, "paddingTop", prior + "px");
									}});
					}});
	}
	private MenuItem getUnderLineMenuItem() {
		return underlineMenuItem = underlineMenuItem != null ? underlineMenuItem : new MenuItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
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
		return italicsMenuItem = italicsMenuItem != null ? italicsMenuItem : new MenuItem("<span style='font-style: italic;'>I</span>", true, new Command(){
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
		return boldMenuItem = boldMenuItem != null ? boldMenuItem : new MenuItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
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
		return textAlignRightMenuItem = textAlignRightMenuItem != null ? textAlignRightMenuItem : new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPfxaNhOBqGIysMRwHpAADGwEe5v4tWjAAAAABJRU5ErkJggg=='>"
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
		return textAlignCenterMenuItem = textAlignCenterMenuItem != null ? textAlignCenterMenuItem : new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='>"
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
		
		return textAlignLeftMenuItem = textAlignLeftMenuItem != null ? textAlignLeftMenuItem : new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='>"
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
		if(styleDialogMenuItem == null) {
			styleDialogMenuItem = new MenuItem("SM", (Command)null);
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
			styleDialogMenuItem.setCommand(new Command(){
				@Override
				public void execute() {
					if(stylePickerPopPanel.isShowing())
						stylePickerPopPanel.hide();
					else {
						StyleMenu.getInstance().refreshStylePanel(invokingElement);
						stylePickerPopPanel.showRelativeTo(styleDialogMenuItem);
					}
				}
			});
			styleDialogMenuItem.setTitle("Detailed Style Dialog");
		}
		return styleDialogMenuItem;
	}
	private MenuItem getBorderEdgePickerMenuItem() {
		if(borderEdgePickerMenuItem == null) {
			MenuBar borderEdgeMenu = new MenuBar(true);
			borderEdgePickerMenuItem = new MenuItem("BE", true, borderEdgeMenu);
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
		}
		return borderEdgePickerMenuItem;
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
		if(borderWidthPickerMenuItem == null) {
			MenuBar borderWidthMenu = new MenuBar(true);
			borderWidthPickerMenuItem = new MenuItem("BW", true, borderWidthMenu);
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
		}
		return borderWidthPickerMenuItem;
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
		if(borderColorPickerMenuItem == null) {
			borderColorPickerMenuItem = new MenuItem("BC", true, (Command)null);
			final PopupPanel borderPickerPopPanel = new PopupPanel(true);
			borderPickerPopPanel.setAutoHideEnabled(true);
			borderColorPickerMenuItem.setCommand(new Command(){
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
						borderPickerPopPanel.showRelativeTo(borderColorPickerMenuItem);
					}
					else if(borderPickerPopPanel.isShowing())
						borderPickerPopPanel.hide();
					else
						borderPickerPopPanel.show();
				}
			});
		}
		return borderColorPickerMenuItem;
	}
	private MenuItem getBgColorPickerMenuItem() {
		if(bgColorPickerMenuItem == null) {
			bgColorPickerMenuItem = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAANUlEQVR42mNgGAW0Av+JxNjB39/s/5ExSDE6H5sYFn10MhCbBdgMGDVw1EBqGkhGFhwFNAQAQy76sGFjx7gAAAAASUVORK5CYII='>"
			, true, (Command)null);
			final PopupPanel bgPickerPopPanel = new PopupPanel(true);
			bgPickerPopPanel.setAutoHideEnabled(true);
			
			bgColorPickerMenuItem.setCommand(new Command(){
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
						bgPickerPopPanel.showRelativeTo(bgColorPickerMenuItem);
					}
					else if(bgPickerPopPanel.isShowing())
						bgPickerPopPanel.hide();
					else
						bgPickerPopPanel.show();
				}
			});
		}
		return bgColorPickerMenuItem;
	}
	private MenuItem getForeColorPickerMenuItem() {
		if(foreColorPickerMenuItem == null) {
			foreColorPickerMenuItem = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAgElEQVR42t2S4QqAMAiEfW//+t42XW4SozQhKuFQaH2duwC+VBxUCrj6wNmZZ4EQAMI7gYjIV0oD2zCkAOahMpCI6kCBiGxFm62nfnB5iZ0ndYjtGYH2qMNRurIDKmCHSb8F9Cuv5jTQZcI9pGk67bA7mEC90wrQJ3tMN5Lyz2sDT5T/KC/lAxMAAAAASUVORK5CYII='>"
			, true, (Command)null);
			final PopupPanel foreColorPickerPopPanel = new PopupPanel(true);
			foreColorPickerPopPanel.setAutoHideEnabled(true);
			
			foreColorPickerMenuItem.setCommand(new Command(){
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
						foreColorPickerPopPanel.showRelativeTo(foreColorPickerMenuItem);
					}
					else if(foreColorPickerPopPanel.isShowing())
						foreColorPickerPopPanel.hide();
					else
						foreColorPickerPopPanel.show();
				}
			});
		}
		return foreColorPickerMenuItem;
	}
	public void setWidget(Widget w){
		className = null;
		Element element = w.getElement();
		if(!element.equals(invokingElement))
			StyleMenu.getInstance().refreshStylePanel(element);
		StyleToolbar.invokingElement = element;
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
	public void showBoldButton(boolean show) {
		if(boldMenuItem != null)
			boldMenuItem.setVisible(show);
		else
			boldButtonVisible = show;
	} 
	public void showItalicsButton(boolean show) {
		if(italicsMenuItem != null)
			italicsMenuItem.setVisible(show);
		else
			italicsButtonVisible = show;
	}
	public void showUnderlineButton(boolean show) {
		if(underlineMenuItem != null)
			underlineMenuItem.setVisible(show);
		else
			underlineButtonVisible = show;
	}
	public void showBackgroundColorPickerButton(boolean show) {
		if(bgColorPickerMenuItem != null)
			bgColorPickerMenuItem.setVisible(show);
		else
			bgColorPickerButtonVisible = show;
	}
	public void showForegroundColorPickerButton(boolean show) {
		if(foreColorPickerMenuItem != null)
			foreColorPickerMenuItem.setVisible(show);
		else
			foreColorPickerButtonVisible = show;
	}
	public void showBorderColorPickerButton(boolean show) {
		if(borderColorPickerMenuItem != null)
			borderColorPickerMenuItem.setVisible(show);
		else
			borderColorPickerButtonVisible = show;
	}
	public void showBorderWidthPickerButton(boolean show) {
		if(borderWidthPickerMenuItem != null)
			borderWidthPickerMenuItem.setVisible(show);
		else
			borderWidthPickerButtonVisible = show;
	}
	public void showBorderEdgePickerButton(boolean show) {
		if(borderEdgePickerMenuItem != null)
			borderEdgePickerMenuItem.setVisible(show);
		else
			borderEdgePickerButtonVisible = show;
	}
	public void showTextAlignLeftButton(boolean show) {
		if(textAlignLeftMenuItem != null)
			textAlignLeftMenuItem.setVisible(show);
		else
			textAlignLeftButtonVisible = show;
	}
	public void showTextAlignCenterButton(boolean show) {
		if(textAlignCenterMenuItem != null)
			textAlignCenterMenuItem.setVisible(show);
		else
			textAlignCenterButtonVisible = show;
	}
	public void showTextAlignRightButton(boolean show) {
		if(textAlignRightMenuItem != null)
			textAlignRightMenuItem.setVisible(show);
		else
			textAlignRightButtonVisible = show;
	}
	public void showRightIndentButton(boolean show) {
		if(rightIndentMenuItem != null)
			rightIndentMenuItem.setVisible(show);
		else
			rightIndentButtonVisible = show;
	}
	public void showLeftIndentButton(boolean show) {
		if(leftIndentMenuItem != null)
			leftIndentMenuItem.setVisible(show);
		else
			leftIndentButtonVisible = show;
	}
	public void showTopIndentButton(boolean show) {
		if(topIndentMenuItem != null)
			topIndentMenuItem.setVisible(show);
		else
			topIndentButtonVisible = show;
	}
	public void showBottomIndentButton(boolean show) {
		if(bottomIndentMenuItem != null)
			bottomIndentMenuItem.setVisible(show);
		else
			bottomIndentButtonVisible = show;
	}
	public void showIncreaseFontSizeButton(boolean show) {
		if(increaseFontSizeMenuItem != null)
			increaseFontSizeMenuItem.setVisible(show);
		else
			increaseFontSizeButtonVisible = show;
	}
	public void showDecreaseFontSizeButton(boolean show) {
		if(decreaseFontSizeMenuItem != null)
			decreaseFontSizeMenuItem.setVisible(show);
		else
			decreaseFontSizeButtonVisible = show;
	}
	public void showFontChooserButton(boolean show) {
		if(fontChooserMenuItem != null)
			fontChooserMenuItem.setVisible(show);
		else
			fontChooserButtonVisible = show;
	}
	public void showStyleDialogButton(boolean show) {
		if(styleDialogMenuItem != null)
			styleDialogMenuItem.setVisible(show);
		else
			styleDialogButtonVisible = show;
	}
	public void showSelectElementForCssEditButton(boolean show) {
		if(selectElementForCssEdit != null)
			selectElementForCssEdit.setVisible(show);
		else
			selectElementForCssEditButtonVisible = show;
	}
}