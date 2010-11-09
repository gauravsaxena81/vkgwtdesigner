package com.vk.gwt.designer.client.designer;

import java.util.List;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasBlurHandlers;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;

public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private int top;
	private int left;
	@SuppressWarnings("unchecked")
	private IWidgetEngine widgetEngine;
	
	private VkMenu(){
		super(true);
	}

	@SuppressWarnings("unchecked")
	public VkMenu(final Widget invokingWidget, IWidgetEngine widgetEngine) {
		super(true);
		setAutoOpen(true);
		setAnimationEnabled(true);
		sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
		this.invokingWidget = invokingWidget;
		this.widgetEngine = widgetEngine;
		setVisible(false);
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				hideMenu();
			}
		});
		DOM.setStyleAttribute(getElement(), "position", "absolute");
		DOM.setStyleAttribute(getElement(), "border", "solid 1px green");
		DOM.setStyleAttribute(getElement(), "zIndex", Integer.MAX_VALUE + "");
		addOperationsItems();
		List<String> widgetsList = VkDesignerUtil.getEngine().getWidgetsList(invokingWidget);
		addWidgetsList(widgetsList);
		List<String> attributesList = widgetEngine.getAttributesList(invokingWidget);
		addAttributesList(attributesList);
		addSeparator();
		addItem("Style", showStyleDialog(invokingWidget));
	}
	
	private Command showStyleDialog(final Widget invokingWidget) {
		return new Command(){
			@Override
			public void execute() {
				final VerticalPanel tabPanelHolder = new VerticalPanel();
				DOM.setStyleAttribute(tabPanelHolder.getElement(), "background", "white");
				DOM.setStyleAttribute(tabPanelHolder.getElement(), "position", "absolute");
				tabPanelHolder.setStyleName("VkDesigner-styledialog");
				tabPanelHolder.setPixelSize(500, 300);
				VkDesignerUtil.getDrawingPanel().add(tabPanelHolder);
				
				Label heading = new Label("Set Style");
				heading.setStyleName("VkDesigner-styledialog-heading");
				heading.setWidth("100%");
				tabPanelHolder.add(heading);
				heading.addMouseDownHandler(new MouseDownHandler(){
					@Override
					public void onMouseDown(MouseDownEvent event) {
						VkDesignerUtil.makeMovable(tabPanelHolder);
					}
				});
				
				TabPanel styleTabPanel = new TabPanel();
				tabPanelHolder.add(styleTabPanel);
				styleTabPanel.setPixelSize(500, 250);
				
				Panel panel = addDecorationPanel();
				styleTabPanel.add(panel, "Decorations");
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
				
				tabPanelHolder.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
				Button close = new Button("OK");
				tabPanelHolder.add(close);
				close.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						tabPanelHolder.removeFromParent();
					}
				});
				
				VkDesignerUtil.centerDialog(tabPanelHolder);
				styleTabPanel.getTabBar().selectTab(0);
				hideMenu();
				
			}
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
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Cursor","cursor"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Display","display"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Visibility","visibility"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Overflow X","overflowX"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Overflow Y","overflowY"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Z Index","zIndex"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Opacity","opacity"));
				miscellaneousVPanel.add(addStyleAttribute(invokingWidget, "Filter(IE)","filter"));
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
				applyChangeToWidget(attributeName, styleAttributeTextBox);
				styleAttributeTextBox.setText(DOM.getStyleAttribute(invokingWidget.getElement(), attributeName));
				return styleAttribute;
			}

			private void applyChangeToWidget(final String attribute, final TextBox paddingTextBox) {
				
				paddingTextBox.addChangeHandler(new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent event) {
						DOM.setStyleAttribute(invokingWidget.getElement(), attribute, paddingTextBox.getText());
					}
				});
			}
		};
	}

	private void addAttributesList(List<String> attributesList)
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
			attributesMenu.setFocusOnHoverEnabled(false);
			Command widgetClickedCommand = new Command() {
				@Override
				public void execute() {
					widgetEngine.applyAttribute(selectedMenuItem.getText(), invokingWidget);
					hideMenu();
				}
			};
			addSeparator();
			for (String attribute : attributesList) {
				attributesMenu.addItem(attribute, widgetClickedCommand);
			}
			addItem("Attributes", attributesMenu);
		}
	}
	
	private void addWidgetsList(List<String> widgetsList)
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
			widgetsMenu.setFocusOnHoverEnabled(false);
			Command widgetClickedCommand = new Command() {
				@Override
				public void execute() {
					Widget widget = null;
					widget = VkDesignerUtil.getEngine().getWidget(selectedMenuItem.getText(), invokingWidget);
					if(widget != null)//cast is safe because the restriction on widgets,  if any, is placed by menu while it shows widget list
						VkDesignerUtil.addWidget(widget, (IPanel)invokingWidget, VkMenu.this.top, VkMenu.this.left);
					hideMenu();
				}
			};
			addSeparator();
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, widgetClickedCommand);
			}
			addItem("Widgets", widgetsMenu);
		}
	}

	private void addOperationsItems() {
		MenuBar operationsMenu = new VkMenu();
		operationsMenu.setFocusOnHoverEnabled(false);
		operationsMenu.addItem("Remove", new Command(){
			@Override
			public void execute() {
				invokingWidget.removeFromParent();
				VkMenu.this.removeFromParent();
				invokingWidget = null;
			}});
		operationsMenu.addItem("Move", new Command(){
			@Override
			public void execute() {
				VkDesignerUtil.makeMovable(invokingWidget);
				hideMenu();
			}});
		operationsMenu.addItem("Resize", new Command(){
			@Override
			public void execute() {
				final HTML draggingWidget = new HTML("&nbsp;");
				DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
				draggingWidget.getElement().getStyle().setOpacity(0.2);
				VkDesignerUtil.getDrawingPanel().add(draggingWidget);
				DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
				draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
				DOM.setStyleAttribute(draggingWidget.getElement(), "top", invokingWidget.getElement().getAbsoluteTop() + "px");
				DOM.setStyleAttribute(draggingWidget.getElement(), "left", invokingWidget.getElement().getAbsoluteLeft() + "px");
				DOM.setCapture(draggingWidget.getElement());
				draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
					@Override
					public void onMouseMove(MouseMoveEvent event) {
						DOM.setStyleAttribute(draggingWidget.getElement(), "width", event.getClientX() 
								- invokingWidget.getElement().getAbsoluteLeft() + "px");
						DOM.setStyleAttribute(draggingWidget.getElement(), "height", event.getClientY() 
								- invokingWidget.getElement().getAbsoluteTop() + "px");
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
						String borderWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "borderWidth");
						String paddingWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "padding");
						String marginWidth = DOM.getStyleAttribute(invokingWidget.getElement(), "margin");
						DOM.setStyleAttribute(invokingWidget.getElement(), "borderWidth", "0px");
						DOM.setStyleAttribute(invokingWidget.getElement(), "padding", "0px");
						DOM.setStyleAttribute(invokingWidget.getElement(), "margin", "0px");
						
						DOM.setStyleAttribute(invokingWidget.getElement(), "width", draggingWidget.getOffsetWidth() 
								- initialWidth + invokingWidget.getOffsetWidth() + "px");
						DOM.setStyleAttribute(invokingWidget.getElement(), "height", draggingWidget.getOffsetHeight() 
								- initialHeight + invokingWidget.getOffsetHeight() + "px");
						
						DOM.setStyleAttribute(invokingWidget.getElement(), "borderWidth", borderWidth);
						DOM.setStyleAttribute(invokingWidget.getElement(), "padding", paddingWidth);
						DOM.setStyleAttribute(invokingWidget.getElement(), "margin", marginWidth);
						draggingWidget.removeFromParent();
					}
				});
				hideMenu();
			}});
		addItem("Operations", operationsMenu);
	}	

	private void hideMenu() {
		this.top = getElement().getOffsetTop() - VkDesignerUtil.getCumulativeTop(invokingWidget.getElement()); 
		this.left = getElement().getOffsetLeft() - VkDesignerUtil.getCumulativeLeft(invokingWidget.getElement());; 
		Timer t = new Timer(){
			@Override
			public void run() {
				VkMenu.this.setVisible(false);
			}};
		t.schedule(50);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addHandler(handler, BlurEvent.getType());
	}
}
