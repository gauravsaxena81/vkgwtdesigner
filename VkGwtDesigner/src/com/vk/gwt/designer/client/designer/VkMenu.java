package com.vk.gwt.designer.client.designer;

import java.util.Iterator;
import java.util.List;

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
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;
import com.vk.gwt.designer.client.widgets.colorpicker.VkColorPicker;

public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget copyStyleWidget;
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private int top;
	private int left;
	private IWidgetEngine<? extends Widget> widgetEngine;
	private IVkWidget copyWidget;
	private AbsolutePanel tabPanelHolder;
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
		intializeMenu(invokingWidget, widgetEngine);
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
	protected void intializeMenu(Widget invokingWidget, IWidgetEngine<? extends Widget> widgetEngine) {
		if(this.invokingWidget != null && VkDesignerUtil.isDesignerMode)
			this.invokingWidget.removeStyleName("vk-selectedWidget");
		this.invokingWidget = invokingWidget;
		if(VkDesignerUtil.isDesignerMode)
			this.invokingWidget.addStyleName("vk-selectedWidget");
		this.widgetEngine = widgetEngine;
	}
	protected void addSpecificItems()
	{
		addSeparator();
		addItem("Style", new Command(){
			@Override
			public void execute() {
				if(tabPanelHolder == null)
					getStyleMenu();
				if(RootPanel.get().getWidgetIndex(tabPanelHolder) == -1)
					RootPanel.get().insert(tabPanelHolder, 0);
				tabPanelHolder.setVisible(true);
				hideMenu();
			}});
		addSeparator();
		addItem("Quick Preview", new Command() {
			@Override
			public void execute() {
				VkDesignerUtil.setLoadString(widgetEngine.serialize((IVkWidget) invokingWidget));
				Window.open(Window.Location.getHref() + "&isDesignerMode=false", "Preview", null);
				hideMenu();
			}
		});
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
	protected AbsolutePanel getStyleMenu()
	{
		tabPanelHolder = new AbsolutePanel();
		MenuBar styleMenu = new MenuBar();
		tabPanelHolder.add(styleMenu);
		styleMenu.addItem("<span style='font-weight: bolder;'>B</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontWeight").equals("bold"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "bold");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontWeight", "");
			}});
		styleMenu.addItem("<span style='font-style: italic;'>I</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "fontStyle").equals("italic"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "italic");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "fontStyle", "");
			}});
		styleMenu.addItem("<span style='text-decoration: underline;'>U</span>", true, new Command(){
			@Override
			public void execute() {
				if(!DOM.getStyleAttribute(invokingWidget.getElement(), "textDecoration").equals("underline"))
					DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "underline");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "textDecoration", "");
			}});
		final TabPanel styleTabPanel = getStyleTabPanel();
		tabPanelHolder.add(styleTabPanel);
		styleMenu.addItem(getBgColorPickerMenuItem());
		styleMenu.addItem(getForeColorPickerMenuItem());
		styleMenu.addItem(getBorderColorPickerMenuItem());
		styleMenu.addItem(getBorderWidthPickerMenuItem());
		styleMenu.addItem(getBorderEdgePickerMenuItem());
		styleMenu.addItem(new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPPhaNhOBqGIzsMRwHpAACk/ke5B2guPwAAAABJRU5ErkJggg=='"
				, true, new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(invokingWidget.getElement(), "textAlign", "left");
					}
		}));
		styleMenu.addItem(new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPXpaNhOBqGIzMMRwHpAAC130e5KyRN6AAAAABJRU5ErkJggg=='"
				, true, new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(invokingWidget.getElement(), "textAlign", "center");
					}
		}));
		styleMenu.addItem(new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAH0lEQVR42mNgGAXUBv/JxIPfxaNhOBqGIysMRwHpAADGwEe5v4tWjAAAAABJRU5ErkJggg=='"
				, true, new Command(){
					@Override
					public void execute() {
						DOM.setStyleAttribute(invokingWidget.getElement(), "textAlign", "right");
					}
		}));
		styleMenu.addItem("V", new Command(){
			@Override
			public void execute() {
				styleTabPanel.setVisible(!styleTabPanel.isVisible());
			}});
		styleMenu.addItem("X", new Command(){
			@Override
			public void execute() {
				tabPanelHolder.setVisible(false);
			}});
		return tabPanelHolder;
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
				if(suffix != null)
					DOM.setStyleAttribute(invokingWidget.getElement(), "border" + suffix + "Style", "solid");
				else
					DOM.setStyleAttribute(invokingWidget.getElement(), "borderStyle", "none");
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
				DOM.setStyleAttribute(invokingWidget.getElement(), "borderTopWidth", borderWidth + "px");
				DOM.setStyleAttribute(invokingWidget.getElement(), "borderLeftWidth", borderWidth + "px");
				DOM.setStyleAttribute(invokingWidget.getElement(), "borderRightWidth", borderWidth + "px");
				DOM.setStyleAttribute(invokingWidget.getElement(), "borderBottomWidth", borderWidth + "px");
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
						public void onValueChange(ValueChangeEvent<VkColorPicker> event) {
							DOM.setStyleAttribute(invokingWidget.getElement(), "borderColor", event.getValue().getColor());
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
		final MenuItem bgColorPicker = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAANUlEQVR42mNgGAW0Av+JxNjB39/s/5ExSDE6H5sYFn10MhCbBdgMGDVw1EBqGkhGFhwFNAQAQy76sGFjx7gAAAAASUVORK5CYII='"
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
							DOM.setStyleAttribute(invokingWidget.getElement(), "background", event.getValue().getColor());
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
		final MenuItem foreColorPicker = new MenuItem("<img src='data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABQAAAAUCAYAAACNiR0NAAAAgElEQVR42t2S4QqAMAiEfW//+t42XW4SozQhKuFQaH2duwC+VBxUCrj6wNmZZ4EQAMI7gYjIV0oD2zCkAOahMpCI6kCBiGxFm62nfnB5iZ0ndYjtGYH2qMNRurIDKmCHSb8F9Cuv5jTQZcI9pGk67bA7mEC90wrQJ3tMN5Lyz2sDT5T/KC/lAxMAAAAASUVORK5CYII='"
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
							DOM.setStyleAttribute(invokingWidget.getElement(), "color", event.getValue().getColor());
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
	protected TabPanel getStyleTabPanel() {
		final TabPanel styleTabPanel = new TabPanel(){
			@Override
			public void setVisible(boolean visible)
			{
				super.setVisible(visible);
				if(visible)
				{
					clear();
					refreshStylePanel(this);
				}
			}
		};
		refreshStylePanel(styleTabPanel);
		styleTabPanel.setVisible(false);
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
	/*private Command showStyleDialog(final Widget invokingWidget) {
		final DialogBox origDialog = new DialogBox();
		origDialog.setText("Set Style");
		return new Command(){
			@Override
			public void execute() {
				VerticalPanel stylePopUp = getStyleMenu();
				((MenuBar)stylePopUp.getWidget(0)).addItem("X", new Command(){
					@Override
					public void execute() {
						origDialog.hide();
					}});
				origDialog.add(stylePopUp);
				origDialog.showRelativeTo(invokingWidget);
				hideMenu();
			}
		};
	}*/

	
	private ScrollPanel addColorPanel() {
		ScrollPanel scrollColorHolderPanel = new ScrollPanel();				
		VerticalPanel colorVPanel = new VerticalPanel();
		scrollColorHolderPanel.add(colorVPanel);
		colorVPanel.setWidth("100%");
		colorVPanel.add(addStyleAttribute(invokingWidget, "Color","color"));
		colorVPanel.add(addStyleAttribute(invokingWidget, "Background Color","backgroundColor"));
		colorVPanel.add(addStyleAttribute(invokingWidget, "Background Image","backgroundImage"));
		colorVPanel.add(addStyleAttribute(invokingWidget, "Background Position","backgroundPosition"));
		colorVPanel.add(addStyleAttribute(invokingWidget, "Background Repeat","backgroundRepeat"));
		colorVPanel.add(addStyleAttribute(invokingWidget, "Background Attachment","backgroundAttachment"));
		return scrollColorHolderPanel;
	}
	private ScrollPanel addDecorationPanel() {
		ScrollPanel scrollDecorationHolderPanel = new ScrollPanel();				
		VerticalPanel decorationVPanel = new VerticalPanel();
		scrollDecorationHolderPanel.add(decorationVPanel);
		decorationVPanel.setWidth("100%");
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Bottom Border","borderBottom"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Top Border","borderTop"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Left Border","borderLeft"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Right Border","borderRight"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Padding","padding"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Margin","margin"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Outline Color","outlineColor"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Outline Style","outlineStyle"));
		decorationVPanel.add(addStyleAttribute(invokingWidget, "Outline Width","outlineWidth"));
		return scrollDecorationHolderPanel;
	}
	private ScrollPanel addFontPanel() {
		ScrollPanel scrollFontHolderPanel = new ScrollPanel();
		VerticalPanel fontVPanel = new VerticalPanel();
		scrollFontHolderPanel.add(fontVPanel);
		fontVPanel.setWidth("100%");
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Size", "fontSize"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Family","fontFamily"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Stretch","fontStretch"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Style","fontStyle"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Variant","fontVariant"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Font Weight","fontWeight"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Align","textAlign"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Decoration","textDecoration"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Decoration","textDecoration"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Indent","textIndent"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Shadow","textShadow"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Text Transform","textTransform"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Vertical Align","verticalAlign"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Word Spacing","wordSpacing"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Line Height","lineHeight"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Letter Spacing","letterSpacing"));
		fontVPanel.add(addStyleAttribute(invokingWidget, "Word Spacing","wordSpacing"));
		return scrollFontHolderPanel;
	}
	private Panel addMiscellaneousPanel() {
		ScrollPanel scrollMiscellaneousHolderPanel = new ScrollPanel();				
		VerticalPanel miscellaneousVPanel = new VerticalPanel();
		scrollMiscellaneousHolderPanel.add(miscellaneousVPanel);
		miscellaneousVPanel.setWidth("100%");
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Width","width"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Height","height"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Cursor","cursor"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Display","display"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Visibility","visibility"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Overflow X","overflowX"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Overflow Y","overflowY"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Z Index","zIndex"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Opacity","opacity"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Filter(IE)","filter"));
		miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "border-collapse(table only)","borderCollapse"));
		return scrollMiscellaneousHolderPanel;
	}
	private Panel addStyleAttribute(Widget invokingWidget, String displayName, String attributeName)
{
	HorizontalPanel styleAttribute = new HorizontalPanel();
	styleAttribute.setWidth("100%");
	styleAttribute.add(new InlineLabel(displayName));
	styleAttribute.setCellWidth(styleAttribute.getWidget(0), "50%");
	DOM.setStyleAttribute(styleAttribute.getElement(), "padding", "2px 0px");
	
	final TextBox styleAttributeTextBox = new TextBox();
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
				DOM.setStyleAttribute(invokingWidget.getElement(), attribute, textBox.getText());
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
					Widget widget = null;
					widget = VkDesignerUtil.getEngine().getWidget(selectedMenuItem.getText());
					if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
						VkDesignerUtil.addWidget(widget, (IPanel)invokingWidget, VkMenu.this.top, VkMenu.this.left);
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
					Widget widget = null;
					widget = VkDesignerUtil.getEngine().getWidget(selectedMenuItem.getText());
					if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
						VkDesignerUtil.addWidget(widget, (IPanel)invokingWidget, VkMenu.this.top, VkMenu.this.left);
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
			if(operationsList.get(i).equals(VkEngine.REMOVE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						invokingWidget.removeFromParent();
						invokingWidget = null;
					}});
			}
			else if(operationsList.get(i).equals(VkEngine.RESIZE))
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
						final int top = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getTop() : invokingWidget.getElement().getOffsetTop();
						final int left = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getLeft() : invokingWidget.getElement().getOffsetLeft();
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
								int initialHeight = invokingWidget.getOffsetHeight();
								int initialWidth = invokingWidget.getOffsetWidth();
								//This is necessary because offsetWidth contains all the decorations but when width is set, the figure is assumed to be independent 
								//of decorations
								String borderTopWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "borderTopWidth");
								String borderBottomWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "borderBottomWidth");
								String borderLeftWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "borderLeftWidth");
								String borderRightWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "borderRightWidth");
								String padding = DOM.getStyleAttribute(invokingWidget.getElement(), "padding");
								String margin = DOM.getStyleAttribute(invokingWidget.getElement(), "margin");
								DOM.setStyleAttribute(invokingWidget.getElement(), "borderWidth", "0px");
								DOM.setStyleAttribute(invokingWidget.getElement(), "padding", "0px");
								DOM.setStyleAttribute(invokingWidget.getElement(), "margin", "0px");
								int width = draggingWidget.getOffsetWidth()	- initialWidth + invokingWidget.getOffsetWidth();
								if(width > 0)
									invokingWidget.setWidth(width + "px");
								int height = draggingWidget.getOffsetHeight() - initialHeight + invokingWidget.getOffsetHeight();
								if(height > 0)
									invokingWidget.setHeight(height + "px");
								
								DOM.setStyleAttribute(invokingWidget.getElement(), "borderTopWidth", borderTopWidth);
								DOM.setStyleAttribute(invokingWidget.getElement(), "borderBottomWidth", borderBottomWidth);
								DOM.setStyleAttribute(invokingWidget.getElement(), "borderLeftWidth", borderLeftWidth);
								DOM.setStyleAttribute(invokingWidget.getElement(), "borderRightWidth", borderRightWidth);
								DOM.setStyleAttribute(invokingWidget.getElement(), "padding", padding);
								DOM.setStyleAttribute(invokingWidget.getElement(), "margin", margin);
								draggingWidget.removeFromParent();
							}
						});
						hideMenu();
					}});
			}
			else if(operationsList.get(i).equals(VkEngine.COPY))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						copyWidget = (IVkWidget)invokingWidget;
					}
				});
			}
			else if(operationsList.get(i).equals(VkEngine.COPY_STYLE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						copyStyleWidget = invokingWidget;
					}
				});
			}
			else if(operationsList.get(i).equals(VkEngine.PASTE_STYLE))
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
			else if(operationsList.get(i).equals(VkEngine.PASTE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						if(copyWidget != null)
						{
							Widget widget = VkDesignerUtil.getEngine().getWidget((copyWidget).getWidgetName());
							VkDesignerUtil.isDesignerMode = false;
							VkDesignerUtil.getEngineMap().get(copyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
							VkDesignerUtil.isDesignerMode = true;
							VkDesignerUtil.addWidget(widget , ((IPanel)invokingWidget), VkMenu.this.top, VkMenu.this.left);
						}
						else
							Window.alert("Cannot paste as no widget is copied");
					}
				});
			}
			else if(operationsList.get(i).equals(VkEngine.SAVE))
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
			else if(operationsList.get(i).equals(VkEngine.LOAD))
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

	protected void hideMenu() {
		if(invokingWidget != null)
		{
			this.top = getElement().getOffsetTop() - VkDesignerUtil.getCumulativeTop(invokingWidget.getElement());
			this.top = this.top - (this.top % VkDesignerUtil.SNAP_TO_FIT_TOP);
			this.left = getElement().getOffsetLeft() - VkDesignerUtil.getCumulativeLeft(invokingWidget.getElement());
			this.left = this.left - (this.left % VkDesignerUtil.SNAP_TO_FIT_LEFT);
		}
		/*final int lastLeft = getElement().getOffsetLeft();
		final int lastTop = getElement().getOffsetTop();*/
		VkMenu.this.setVisible(false);
		/*new Timer(){
			@Override
			public void run() {
				if(lastLeft == VkMenu.this.getElement().getOffsetLeft() && lastTop == getElement().getOffsetTop())
					VkMenu.this.setVisible(false);
			}}.schedule(1);*/
	}
}
