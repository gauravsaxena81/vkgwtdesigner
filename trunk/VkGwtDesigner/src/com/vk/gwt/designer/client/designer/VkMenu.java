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
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Panel;
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

public class VkMenu extends MenuBar implements HasBlurHandlers{
	private Widget invokingWidget;
	private MenuItem selectedMenuItem;
	private int top;
	private int left;
	private IWidgetEngine<? extends Widget> widgetEngine;
	private IVkWidget copyWidget;
	protected Widget copyStyleWidget;

	public VkMenu() {
		super(true);
		setStyleName("vkgwtdesigner-vertical-menu");
		setAutoOpen(true);
		setAnimationEnabled(true);
		sinkEvents(Event.ONCLICK | Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONFOCUS | Event.ONKEYDOWN | Event.ONBLUR);
		setVisible(false);
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				hideMenu();
			}
		});
		DOM.setStyleAttribute(getElement(), "position", "absolute");
		DOM.setStyleAttribute(getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(getElement(), "zIndex", Integer.MAX_VALUE + "");
	}
	public void prepareMenu(Widget invokingWidget, IWidgetEngine<? extends Widget> widgetEngine) {
		this.invokingWidget = invokingWidget;
		this.widgetEngine = widgetEngine;
		clearItems();
		addOperationsItems(widgetEngine.getOperationsList(invokingWidget));
		List<String> widgetsList = VkDesignerUtil.getEngine().getWidgetsList(invokingWidget);
		addWidgetsList(widgetsList);
		List<String> panelsList = VkDesignerUtil.getEngine().getPanelsList(invokingWidget);
		addPanelsList(panelsList);
		List<String> attributesList = widgetEngine.getAttributesList(invokingWidget);
		addAttributesList(attributesList);
		addSeparator();
		addItem("Style", showStyleDialog(invokingWidget));
	}

	protected Command showStyleDialog(final Widget invokingWidget) {
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
				applyChangeToWidget(attributeName, styleAttributeTextBox);
				try{//IE throws an exception when it is queried about zIndex - GWT Bug 5548
					styleAttributeTextBox.setText(DOM.getStyleAttribute(invokingWidget.getElement(), attributeName));
				}catch(Exception e)	{
					styleAttributeTextBox.setText(getIEZindex(invokingWidget));
				}
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
			addSeparator();
			for (String attribute : attributesList) {
				attributesMenu.addItem(attribute, widgetClickedCommand);
			}
			addItem("Attributes", attributesMenu);
		}
	}
	
	protected void addWidgetsList(List<String> widgetsList)
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
			addSeparator();
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, widgetClickedCommand);
			}
			addItem("Widgets", widgetsMenu);
		}
	}
	
	protected void addPanelsList(List<String> widgetsList)
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
			addSeparator();
			for (String widgetName : widgetsList) {
				widgetsMenu.addItem(widgetName, widgetClickedCommand);
			}
			addItem("Panels", widgetsMenu);
		}
	}

	private void addOperationsItems(List<String> operationsList) {
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
			else if(operationsList.get(i).equals(VkEngine.MOVE))
			{
				operationsMenu.addItem(operationsList.get(i), new Command(){
					@Override
					public void execute() {
						VkDesignerUtil.makeMovable(invokingWidget);
						hideMenu();
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
						final int top = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getTop() : invokingWidget.getElement().getAbsoluteTop();
						final int left = isPopUpMenuBar && !isAttached ? ((VkMenuBarVertical)invokingWidget).getLeft() : invokingWidget.getElement().getAbsoluteLeft();
						DOM.setStyleAttribute(draggingWidget.getElement(), "top", top + "px");
						DOM.setStyleAttribute(draggingWidget.getElement(), "left", left + "px");
						DOM.setCapture(draggingWidget.getElement());
						draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
							@Override
							public void onMouseMove(MouseMoveEvent event) {
								int width = event.getClientX() - left;
								int height = event.getClientY() - top;
								if(width % 2 == 0)
									DOM.setStyleAttribute(draggingWidget.getElement(), "width", width + "px");
								if(height % 2 == 0)
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
						Button ok = new Button("Render");
						vp.add(ok);
						ok.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								widgetEngine.deserialize((IVkWidget) invokingWidget, ta.getText());
								loadDialog.hide();
							}
						});
						loadDialog.center();
					}
				});
			}
		}
		addItem("Operations", operationsMenu);
	}	

	private void hideMenu() {
		if(invokingWidget != null)
		{
			this.top = getElement().getOffsetTop() - VkDesignerUtil.getCumulativeTop(invokingWidget.getElement()); 
			this.left = getElement().getOffsetLeft() - VkDesignerUtil.getCumulativeLeft(invokingWidget.getElement());
		}
		final int lastLeft = getElement().getOffsetLeft();
		final int lastTop = getElement().getOffsetTop();
		new Timer(){
			@Override
			public void run() {
				if(lastLeft == VkMenu.this.getElement().getOffsetLeft() && lastTop == getElement().getOffsetTop())
					VkMenu.this.setVisible(false);
			}}.schedule(100);
	}

	@Override
	public HandlerRegistration addBlurHandler(BlurHandler handler) {
		return addHandler(handler, BlurEvent.getType());
	}
}
