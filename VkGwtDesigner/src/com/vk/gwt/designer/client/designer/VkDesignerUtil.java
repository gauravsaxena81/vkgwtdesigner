package com.vk.gwt.designer.client.designer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.Panels.VkDecoratedStackPanel;
import com.vk.gwt.designer.client.Panels.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.Panels.VkDisclosurePanel;
import com.vk.gwt.designer.client.Panels.VkDockPanel;
import com.vk.gwt.designer.client.Panels.VkFlowPanel;
import com.vk.gwt.designer.client.Panels.VkFocusPanel;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.Panels.VkPopUpPanel;
import com.vk.gwt.designer.client.Panels.VkScrollPanel;
import com.vk.gwt.designer.client.Panels.VkSimplePanel;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.engine.VkAbsolutePanelEngine;
import com.vk.gwt.designer.client.engine.VkAnchorEngine;
import com.vk.gwt.designer.client.engine.VkButtonEngine;
import com.vk.gwt.designer.client.engine.VkCaptionPanelEngine;
import com.vk.gwt.designer.client.engine.VkCheckboxEngine;
import com.vk.gwt.designer.client.engine.VkDateBoxEngine;
import com.vk.gwt.designer.client.engine.VkDeckPanelEngine;
import com.vk.gwt.designer.client.engine.VkDecoratedStackPanelEngine;
import com.vk.gwt.designer.client.engine.VkDecoratedTabBarEngine;
import com.vk.gwt.designer.client.engine.VkDecoratedTabPanelEngine;
import com.vk.gwt.designer.client.engine.VkDialogBoxEngine;
import com.vk.gwt.designer.client.engine.VkDisclosurePanelEngine;
import com.vk.gwt.designer.client.engine.VkDockPanelEngine;
import com.vk.gwt.designer.client.engine.VkFileUploadEngine;
import com.vk.gwt.designer.client.engine.VkFlexTableEngine;
import com.vk.gwt.designer.client.engine.VkFlowPanelEngine;
import com.vk.gwt.designer.client.engine.VkFocusPanelEngine;
import com.vk.gwt.designer.client.engine.VkFormPanelEngine;
import com.vk.gwt.designer.client.engine.VkFrameEngine;
import com.vk.gwt.designer.client.engine.VkGridEngine;
import com.vk.gwt.designer.client.engine.VkHTMLEngine;
import com.vk.gwt.designer.client.engine.VkHiddenEngine;
import com.vk.gwt.designer.client.engine.VkHorizontalPanelEngine;
import com.vk.gwt.designer.client.engine.VkHorizontalSplitPanelEngine;
import com.vk.gwt.designer.client.engine.VkHtmlPanelEngine;
import com.vk.gwt.designer.client.engine.VkImageEngine;
import com.vk.gwt.designer.client.engine.VkInlineHTMLEngine;
import com.vk.gwt.designer.client.engine.VkInlineLabelEngine;
import com.vk.gwt.designer.client.engine.VkLabelEngine;
import com.vk.gwt.designer.client.engine.VkListBoxEngine;
import com.vk.gwt.designer.client.engine.VkMenuBarHorizontalEngine;
import com.vk.gwt.designer.client.engine.VkMenuBarVerticalEngine;
import com.vk.gwt.designer.client.engine.VkPasswordTextBoxEngine;
import com.vk.gwt.designer.client.engine.VkPopUpPanelEngine;
import com.vk.gwt.designer.client.engine.VkPushButtonEngine;
import com.vk.gwt.designer.client.engine.VkRadioButtonEngine;
import com.vk.gwt.designer.client.engine.VkResetButtonEngine;
import com.vk.gwt.designer.client.engine.VkRichTextAreaEngine;
import com.vk.gwt.designer.client.engine.VkScrollPanelEngine;
import com.vk.gwt.designer.client.engine.VkSimplePanelEngine;
import com.vk.gwt.designer.client.engine.VkStackPanelEngine;
import com.vk.gwt.designer.client.engine.VkSubmitButtonEngine;
import com.vk.gwt.designer.client.engine.VkSuggestBoxEngine;
import com.vk.gwt.designer.client.engine.VkTabBarEngine;
import com.vk.gwt.designer.client.engine.VkTabPanelEngine;
import com.vk.gwt.designer.client.engine.VkTextAreaEngine;
import com.vk.gwt.designer.client.engine.VkTextBoxEngine;
import com.vk.gwt.designer.client.engine.VkToggleButtonEngine;
import com.vk.gwt.designer.client.engine.VkTreeEngine;
import com.vk.gwt.designer.client.engine.VkVerticalPanelEngine;
import com.vk.gwt.designer.client.engine.VkVerticalSplitPanelEngine;
import com.vk.gwt.designer.client.widgets.VkAnchor;
import com.vk.gwt.designer.client.widgets.VkButton;
import com.vk.gwt.designer.client.widgets.VkCheckbox;
import com.vk.gwt.designer.client.widgets.VkDateBox;
import com.vk.gwt.designer.client.widgets.VkDecoratedTabBar;
import com.vk.gwt.designer.client.widgets.VkDialogBox;
import com.vk.gwt.designer.client.widgets.VkFileUpload;
import com.vk.gwt.designer.client.widgets.VkFlexTable;
import com.vk.gwt.designer.client.widgets.VkFrame;
import com.vk.gwt.designer.client.widgets.VkGrid;
import com.vk.gwt.designer.client.widgets.VkHTML;
import com.vk.gwt.designer.client.widgets.VkHidden;
import com.vk.gwt.designer.client.widgets.VkImage;
import com.vk.gwt.designer.client.widgets.VkInlineHTML;
import com.vk.gwt.designer.client.widgets.VkInlineLabel;
import com.vk.gwt.designer.client.widgets.VkLabel;
import com.vk.gwt.designer.client.widgets.VkListBox;
import com.vk.gwt.designer.client.widgets.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;
import com.vk.gwt.designer.client.widgets.VkPasswordTextBox;
import com.vk.gwt.designer.client.widgets.VkPushButton;
import com.vk.gwt.designer.client.widgets.VkRadioButton;
import com.vk.gwt.designer.client.widgets.VkResetButton;
import com.vk.gwt.designer.client.widgets.VkRichTextArea;
import com.vk.gwt.designer.client.widgets.VkSubmitButton;
import com.vk.gwt.designer.client.widgets.VkSuggestBox;
import com.vk.gwt.designer.client.widgets.VkTabBar;
import com.vk.gwt.designer.client.widgets.VkTextArea;
import com.vk.gwt.designer.client.widgets.VkTextBox;
import com.vk.gwt.designer.client.widgets.VkToggleButton;
import com.vk.gwt.designer.client.widgets.VkTree;

public class VkDesignerUtil {
	
	public interface IEventRegister{
		public void registerEvent(String js);
	}
	
	public static final int SNAP_TO_FIT_TOP = 5;
	public static final int SNAP_TO_FIT_LEFT = 5;
	private static int widgetCount = 0;
	private static VkMenu vkMenu = new VkMenu();
	private static Map<String, IWidgetEngine<? extends Widget>> engineMap = new LinkedHashMap<String, IWidgetEngine<? extends Widget>>();
	private static VkMainDrawingPanel drawingPanel;
	private static VkEngine vkEngine = new VkEngine();
	public static boolean isDesignerMode = true;
	private static PopupPanel moveImagePanel = new PopupPanel(true);
	private static Widget popUpAssociateWidget;
	static boolean isLoadRunning = false;
	private static HTML moveImage;
	private static HTML resizeImage;
	
	static void initDesignerEvents(Widget widget, IWidgetEngine<? extends Widget> widgetEngine){
		allWidgetEvents(widget, widgetEngine);
		automaticTextAddition(widget);
	};
	private static void automaticTextAddition(final Widget widget) {
		if(widget instanceof HasVkText) {
			if(widget instanceof HasDoubleClickHandlers) {
				((HasDoubleClickHandlers)widget).addDoubleClickHandler(new DoubleClickHandler() {
					@Override
					public void onDoubleClick(DoubleClickEvent event) {
						addTextBox(widget);
					}
				});
			} else
				addNativeDoubleClickHandler(widget);
		}
	}
	private native static void addNativeDoubleClickHandler(Widget widget) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		function dblClickHandler(){
			@com.vk.gwt.designer.client.designer.VkDesignerUtil::addTextBox(Lcom/google/gwt/user/client/ui/Widget;)(widget);
		}
		if(element.addEventListener)
				element.addEventListener("dblclick", dblClickHandler, false);
		else if(element.attachEvent)
			element.attachEvent("ondblclick", dblClickHandler);
	}-*/;
	private static void addTextBox(final Widget widget) {
		final TextBox tb = new TextBox();
		tb.setText(((HasVkText) widget).getText());
		tb.setPixelSize(widget.getOffsetWidth(), Math.max(30, widget.getOffsetHeight()));
		DOM.setStyleAttribute(tb.getElement(), "top", widget.getElement().getAbsoluteTop() + "px");
		DOM.setStyleAttribute(tb.getElement(), "left", widget.getElement().getAbsoluteLeft() + "px");
		DOM.setStyleAttribute(tb.getElement(), "position", "absolute");
		DOM.setStyleAttribute(tb.getElement(), "zIndex", "1");
		RootPanel.get().add(tb);
		tb.setFocus(true);
		tb.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				((HasVkText) widget).setText(tb.getText());
				tb.removeFromParent();
			}
		});
		tb.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				switch(event.getNativeKeyCode()){
					case KeyCodes.KEY_ENTER :
						((HasVkText) widget).setText(tb.getText());
						tb.removeFromParent();
					break;
					case KeyCodes.KEY_ESCAPE:
						tb.removeFromParent();
					break;
				}
			}
		});
	}
	private native static void allWidgetEvents(Widget widget, IWidgetEngine<? extends Widget> widgetEngine) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		setTimeout(createMouseDownEvent, 200);//widgets(like images) may take time to load, thus event is not attached successfully if not waited for it
		function isChild(target, parent) {
			while(target != null)
			{
				if(target == parent)
					return true;
				else
					target = target.parentNode;
			}
			return false;
		}
		function createMouseDownEvent()	{
			element.isWidget = 'true';
			if(element.addEventListener) {
				element.addEventListener("mousedown", mouseDownHandler, false);
				element.addEventListener("mouseover", mouseOverHandler, false);
			}
			else if(element.attachEvent) {
				element.attachEvent("onmousedown", mouseDownHandler);
				element.attachEvent("onmouseover", mouseOverHandler);
			}
			function mouseOverHandler(ev) {
				ev = ev || $wnd.event;
				if(element.id != 'drawingPanel' && getTarget(ev.target || ev.srcElement) == element) {
					//ev = ev || $wnd.event;
					//if(ev.stopPropagation)
					//	ev.stopPropagation();
					//else
					//	ev.cancelBubble = true;
					@com.vk.gwt.designer.client.designer.VkDesignerUtil::showMoveIcon(Lcom/google/gwt/user/client/ui/Widget;)(widget);
				}
			}
			function getTarget(target){
				while(typeof target.isWidget == 'undefined')
					target = target.parentNode;
				return target;
			}
			function mouseDownHandler(ev) {
				ev = ev || $wnd.event;
				if(element.id != '' && getTarget(ev.target || ev.srcElement) == element) {
					var menu = @com.vk.gwt.designer.client.designer.VkDesignerUtil::getMenu()();
					menu.@com.vk.gwt.designer.client.designer.VkMenu::prepareMenu(Lcom/vk/gwt/designer/client/api/widgets/IVkWidget;)(widget);
					//if(ev.cancelBubble)
					///	ev.cancelBubble = true;
					//else
					//	ev.stopPropagation();
					//if(element.id != 'drawingPanel' && (ev.button == 1 || ev.button == 0) 
					//	&& widget.@com.google.gwt.user.client.ui.Widget::getElement()().style.position == 'absolute')
					//	@com.vk.gwt.designer.client.designer.VkDesignerUtil::makeMovable(Lcom/google/gwt/user/client/ui/Widget;)(widget);
				}
			}
			$doc.onkeydown = function(ev){
				if(typeof ev == 'undefined')
					ev = $wnd.event;
				var menu = @com.vk.gwt.designer.client.designer.VkDesignerUtil::getMenu()();
				if(ev.keyCode == 46) {//remove widget
					var deleteCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getRemoveCommand()();
					if(deleteCommand != null)
					{
						deleteCommand.@com.google.gwt.user.client.Command::execute()();
						@com.vk.gwt.designer.client.designer.VkDesignerUtil::hideMoveIcon()();
					}
				}
				else if(ev.keyCode == 67 && ev.ctrlKey && ev.shiftKey) {//copy style
					var copyCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getCopyStyleCommand()();
					if(copyCommand != null)
						copyCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 86 && ev.ctrlKey && ev.shiftKey){//paste style
					var pasteCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getPasteStyleCommand()();
					if(pasteCommand != null)
						pasteCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 67 && ev.ctrlKey){//copy
					var copyCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getCopyCommand()();
					if(copyCommand != null)
						copyCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 86 && ev.ctrlKey){//paste
					var pasteCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getPasteCommand()();
					if(pasteCommand != null)
						pasteCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 88 && ev.ctrlKey){//redo
					var cutCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getCutCommand()();
					if(cutCommand != null)
						cutCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 89 && ev.ctrlKey){//redo
					var redoCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getRedoCommand()();
					redoCommand.@com.google.gwt.user.client.Command::execute()();
				}
				else if(ev.keyCode == 90 && ev.ctrlKey){//undo
					var undoCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getUndoCommand()();
					undoCommand.@com.google.gwt.user.client.Command::execute()();
				}
			};
		}
	}-*/;
	public static void showMoveIcon(Widget widget) {
		moveImagePanel.showRelativeTo(widget);
		moveImagePanel.setPopupPosition(moveImagePanel.getPopupLeft(), moveImagePanel.getPopupTop() - widget.getOffsetHeight() - moveImagePanel.getOffsetHeight() + 3);
		DOM.setStyleAttribute(moveImagePanel.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		popUpAssociateWidget = widget;
		moveImage.setVisible(((IVkWidget)popUpAssociateWidget).isMovable() && ((IVkWidget)popUpAssociateWidget).isMovable() && popUpAssociateWidget.getParent() instanceof AbsolutePanel);
		resizeImage.setVisible(((IVkWidget)popUpAssociateWidget).isResizable());
	}
	public static int getCumulativeTop(Element invokingWidgetElement) {
		int top = 0;
		Element tempWidget = invokingWidgetElement;
		while(tempWidget != null)
		{
			top += tempWidget.getOffsetTop();
			tempWidget  = (Element) tempWidget.getOffsetParent();
		}
		return top;
	}
	public static int getCumulativeLeft(Element invokingWidgetElement) {
		int left = 0;
		Element tempWidget = invokingWidgetElement;
		while(tempWidget != null)
		{
			left += tempWidget.getOffsetLeft();
			tempWidget  = (Element) tempWidget.getOffsetParent();
		}
		return left;
	}

	public native static String formatJs(String js) /*-{
		var idArray = js.match(/&\(.+?\)/g);
		if(idArray != null)
			for(var i = 0; i < idArray.length; i++)
				js = js.replace(idArray[i],"window.document.getElementById('" + idArray[i].substr(2,idArray[i].length - 3) + "')");
		return js;
	}-*/;

	public static void assignId(Widget widget) {
		widget.getElement().setId(++widgetCount + "");
	}

	static void makeMovable(final Widget invokingWidget) {
		final HTML draggingWidget = new HTML("&nbsp;");
		((Panel) invokingWidget.getParent()).add(draggingWidget);
		DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
		draggingWidget.getElement().getStyle().setOpacity(0.2);
		DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
		draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
		DOM.setStyleAttribute(draggingWidget.getElement(), "top", invokingWidget.getElement().getOffsetTop() 
			/*- drawingPanel.getElement().getOffsetTop() - RootPanel.getBodyElement().getScrollTop()*/ + "px");
		DOM.setStyleAttribute(draggingWidget.getElement(), "left", invokingWidget.getElement().getOffsetLeft() + "px");
		//DOM.setCapture(draggingWidget.getElement());
		setCapture(draggingWidget);
		draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int top = event.getClientY() /*- VkDesignerUtil.getDrawingPanel().getElement().getOffsetTop()*/	+ RootPanel.getBodyElement().getScrollTop();
				int left = event.getClientX() /*- VkDesignerUtil.getDrawingPanel().getElement().getOffsetLeft()*/;
				DOM.setStyleAttribute(draggingWidget.getElement(), "top", (top / SNAP_TO_FIT_TOP) * SNAP_TO_FIT_TOP + "px");
				DOM.setStyleAttribute(draggingWidget.getElement(), "left", (left / SNAP_TO_FIT_LEFT) * SNAP_TO_FIT_LEFT + "px");
				event.preventDefault();
			}
		});
		draggingWidget.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				final int initialTop = invokingWidget.getElement().getOffsetTop();
				final int initialLeft = invokingWidget.getElement().getOffsetLeft();
				final int finalTop = /*invokingWidget.getElement().getOffsetTop() */
				+ draggingWidget.getElement().getOffsetTop() /*- invokingWidget.getElement().getAbsoluteTop()*/;
				final int finalLeft = /*invokingWidget.getElement().getOffsetLeft() */
				+ draggingWidget.getElement().getOffsetLeft()/* - invokingWidget.getElement().getAbsoluteLeft()*/;
				if(initialTop != finalTop - 1 || initialLeft != finalLeft - 1) {//-1 is hack for FF
					new Command(){
						private final Command redoCommand = this;
						@Override
						public void execute() {
							DOM.setStyleAttribute(invokingWidget.getElement(), "top", finalTop + "px"); 
							DOM.setStyleAttribute(invokingWidget.getElement(), "left", finalLeft + "px");
							showMoveIcon(invokingWidget);
							vkMenu.getUndoStack().push(new Command(){
								@Override
								public void execute() {
									DOM.setStyleAttribute(invokingWidget.getElement(), "top", initialTop + "px"); 
									DOM.setStyleAttribute(invokingWidget.getElement(), "left", initialLeft + "px");
									showMoveIcon(invokingWidget);
									vkMenu.getRedoStack().push(redoCommand);
								}});
					}}.execute();
				} else
					showMoveIcon(invokingWidget);
				//DOM.releaseCapture(draggingWidget.getElement());
				VkDesignerUtil.releaseCapture(draggingWidget);
				draggingWidget.removeFromParent();
				if(finalLeft - initialLeft == 1 && finalTop  - initialTop == 1) {//draggingwidget is 1 pixel off in position
					invokingWidget.fireEvent(event);
					invokingWidget.onBrowserEvent((Event) event.getNativeEvent());
				}
			}
		});
	}
	public static native void setCapture(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		@com.google.gwt.user.client.DOM::setCapture(Lcom/google/gwt/user/client/Element;)(elem);
		if($doc.all)//IE 8: need to test to confirm if required
			elem.setCapture(true);
	}-*/;
	public static native void releaseCapture(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		@com.google.gwt.user.client.DOM::releaseCapture(Lcom/google/gwt/user/client/Element;)(elem);
		if($doc.all)//IE 8: need to test to confirm if required
			elem.setCapture(false);
	}-*/;
	private static void init() {
		setUpEngineMap();
		drawingPanel = (VkMainDrawingPanel) getEngineMap().get(VkMainDrawingPanel.NAME).getWidget();
		if(isDesignerMode) {
			VkDesignerUtil.initDesignerEvents(drawingPanel, getEngineMap().get(VkMainDrawingPanel.NAME));
			vkMenu.prepareMenu(drawingPanel);
			HorizontalPanel moveHp = new HorizontalPanel();
			moveImagePanel.setWidget(moveHp);
			moveImage = new HTML("<img src='images/cursor_move.png' height=18 width=18>");
			moveHp.add(moveImage);
			resizeImage = new HTML("<img src='images/cursor_resize.png' height=16 width=16>");
			moveHp.add(resizeImage);
			moveImage.addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					//DOM.setCapture(moveImage.getElement());
					setCapture(moveImage);
				}
			});
			moveImage.addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					//DOM.releaseCapture(moveImage.getElement());
					releaseCapture(moveImage);
				}
			});
			moveImage.addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					vkMenu.prepareMenu((IVkWidget) popUpAssociateWidget);
					if(event.getNativeButton() == NativeEvent.BUTTON_LEFT && ((IVkWidget)popUpAssociateWidget).isMovable() && popUpAssociateWidget.getParent() instanceof AbsolutePanel) {
						makeMovable(popUpAssociateWidget);
						moveImagePanel.hide();
						event.preventDefault();
						event.stopPropagation();
					} else if (event.getNativeButton() == NativeEvent.BUTTON_RIGHT && ((IVkWidget)popUpAssociateWidget).isResizable()) {
						vkMenu.getResizeCommand().execute();
						event.preventDefault();
						event.stopPropagation();
					}
				}});
			resizeImage.addMouseDownHandler(new MouseDownHandler(){
				@Override
				public void onMouseDown(MouseDownEvent event) {
					vkMenu.prepareMenu((IVkWidget) popUpAssociateWidget);
					if (event.getNativeButton() == NativeEvent.BUTTON_LEFT && ((IVkWidget)popUpAssociateWidget).isResizable()) {
						vkMenu.getResizeCommand().execute();
						event.preventDefault();
						event.stopPropagation();
					}
				}});
			moveImagePanel.setStyleName("none");
			popUpAssociateWidget = null;//for LOAD command
		}
	}
	static void hideMoveIcon() {
		moveImagePanel.hide();
	}
	private static void setUpEngineMap() {
		engineMap.put(VkMainDrawingPanel.NAME, new VkMainDrawingPanelEngine());
		
		engineMap.put(VkButton.NAME, new VkButtonEngine());
		engineMap.put(VkTextBox.NAME, new VkTextBoxEngine());
		engineMap.put(VkLabel.NAME, new VkLabelEngine());
		engineMap.put(VkFrame.NAME, new VkFrameEngine());
		engineMap.put(VkCheckbox.NAME, new VkCheckboxEngine());
		engineMap.put(VkFileUpload.NAME, new VkFileUploadEngine());
		engineMap.put(VkFlexTable.NAME, new VkFlexTableEngine());
		engineMap.put(VkGrid.NAME, new VkGridEngine());
		engineMap.put(VkHTML.NAME, new VkHTMLEngine());
		engineMap.put(VkHidden.NAME, new VkHiddenEngine());
		engineMap.put(VkImage.NAME, new VkImageEngine());
		engineMap.put(VkListBox.NAME, new VkListBoxEngine());
		engineMap.put(VkMenuBarHorizontal.NAME, new VkMenuBarHorizontalEngine());
		engineMap.put(VkMenuBarVertical.NAME, new VkMenuBarVerticalEngine());
		engineMap.put(VkDialogBox.NAME, new VkDialogBoxEngine());
		engineMap.put(VkPushButton.NAME, new VkPushButtonEngine());
		engineMap.put(VkRadioButton.NAME, new VkRadioButtonEngine());
		engineMap.put(VkRichTextArea.NAME, new VkRichTextAreaEngine());
		engineMap.put(VkSuggestBox.NAME, new VkSuggestBoxEngine());
		engineMap.put(VkTabBar.NAME, new VkTabBarEngine());
		engineMap.put(VkTextArea.NAME, new VkTextAreaEngine());
		engineMap.put(VkToggleButton.NAME, new VkToggleButtonEngine());
		engineMap.put(VkTree.NAME, new VkTreeEngine());
		engineMap.put(VkPasswordTextBox.NAME, new VkPasswordTextBoxEngine());
		engineMap.put(VkAnchor.NAME, new VkAnchorEngine());
		engineMap.put(VkResetButton.NAME, new VkResetButtonEngine());
		engineMap.put(VkSubmitButton.NAME, new VkSubmitButtonEngine());
		engineMap.put(VkDecoratedTabBar.NAME, new VkDecoratedTabBarEngine());
		engineMap.put(VkInlineLabel.NAME, new VkInlineLabelEngine());
		engineMap.put(VkInlineHTML.NAME, new VkInlineHTMLEngine());
		engineMap.put(VkDateBox.NAME, new VkDateBoxEngine());
		
		engineMap.put(VkAbsolutePanel.NAME, new VkAbsolutePanelEngine());
		engineMap.put(VkVerticalPanel.NAME, new VkVerticalPanelEngine());
		engineMap.put(VkCaptionPanel.NAME, new VkCaptionPanelEngine());
		engineMap.put(VkDeckPanel.NAME, new VkDeckPanelEngine());
		engineMap.put(VkDisclosurePanel.NAME, new VkDisclosurePanelEngine());
		engineMap.put(VkDockPanel.NAME, new VkDockPanelEngine());
		engineMap.put(VkFlowPanel.NAME, new VkFlowPanelEngine());
		engineMap.put(VkFocusPanel.NAME, new VkFocusPanelEngine());
		engineMap.put(VkFormPanel.NAME, new VkFormPanelEngine());
		engineMap.put(VkHorizontalPanel.NAME, new VkHorizontalPanelEngine());
		engineMap.put(VkHorizontalSplitPanel.NAME, new VkHorizontalSplitPanelEngine());
		engineMap.put(VkHtmlPanel.NAME, new VkHtmlPanelEngine());
		engineMap.put(VkScrollPanel.NAME, new VkScrollPanelEngine());
		engineMap.put(VkStackPanel.NAME, new VkStackPanelEngine());
		engineMap.put(VkTabPanel.NAME, new VkTabPanelEngine());
		engineMap.put(VkVerticalSplitPanel.NAME, new VkVerticalSplitPanelEngine());
		engineMap.put(VkSimplePanel.NAME, new VkSimplePanelEngine());
		engineMap.put(VkPopUpPanel.NAME, new VkPopUpPanelEngine());
		engineMap.put(VkDecoratedStackPanel.NAME, new VkDecoratedStackPanelEngine());
		engineMap.put(VkDecoratedTabPanel.NAME, new VkDecoratedTabPanelEngine());
	}
	public static Map<String, IWidgetEngine<? extends Widget>> getEngineMap() {
		return engineMap;
	}
	public static void setEngineMap(Map<String, IWidgetEngine<? extends Widget>> map) {
		engineMap = map;
	}
	public static VkMenu getMenu()
	{
		return vkMenu;
	}
	public static void setMenu(VkMenu vkMenu) {
		VkDesignerUtil.vkMenu = vkMenu;
	}
	public static VkMainDrawingPanel getDrawingPanel() {
		if(drawingPanel == null)
			init();
		return drawingPanel;
	}

	public static VkEngine getEngine() {
		return vkEngine;
	}
	public static void setEngine(VkEngine newVkEngine) {
		vkEngine = newVkEngine;
	}
	public static void executeEvent(String js, Map<String, String> eventproperties) {
		if(eventproperties != null)
			prepareLocalEvent(eventproperties);
		runJs(VkDesignerUtil.formatJs(js));
	}
	/**
	 * @param js
	 * @param event
	 * @param instantiateEventObject
	 */
	@SuppressWarnings("rawtypes")
	public static void executeEvent(String js, DomEvent event, boolean instantiateEventObject) {
		EventTarget currentEventTarget = event.getNativeEvent().getCurrentEventTarget();
		Element currentEventTargetElement = currentEventTarget != null && Element.is(currentEventTarget)? (Element) Element.as(currentEventTarget) : null;
		EventTarget eventTarget = event.getNativeEvent().getEventTarget();
		Element eventTargetElement = eventTarget != null && Element.is(eventTarget)
			? (Element) Element.as(eventTarget) : null;
		EventTarget relativeEventTarget = event.getNativeEvent().getRelatedEventTarget();
		Element relativeEventTargetElement = relativeEventTarget != null ? (Element) Element.as(relativeEventTarget) : null;
		//JSNI was not able to work with methods of NativeElement and kept on throwing errors which made no sense.
		//Have a look at the issue raised at http://stackoverflow.com/questions/4086392/jsni-cannot-find-a-method-in-nativeevent-class-of-gwt 
		prepareLocalEvent(event, instantiateEventObject, event.getNativeEvent().getAltKey(), event.getNativeEvent().getButton(), event.getNativeEvent().getClientX() 
			, event.getNativeEvent().getClientY(), event.getNativeEvent().getCtrlKey(), currentEventTargetElement, eventTargetElement
			, event.getNativeEvent().getKeyCode(), event.getNativeEvent().getCharCode(), event.getNativeEvent().getMetaKey(), event.getNativeEvent().getMouseWheelVelocityY()
			, relativeEventTargetElement, event.getNativeEvent().getScreenX(), event.getNativeEvent().getScreenY(),event.getNativeEvent().getShiftKey());
		runJs( VkDesignerUtil.formatJs(js));
	}
	private static native void runJs(String formatJs) /*-{
		//in javascript the variables are either function scope or window scope. Thus, to ensure any variables declared in the script are not declared in 
		//window scope, the script has been wrapped in a function.
		if(!@com.vk.gwt.designer.client.designer.VkDesignerUtil::isDesignerMode)
			$wnd.eval("var vkgdTemp = function(){ " + formatJs + " }; vkgdTemp(); vkgdTemp = null;");
	}-*/;

	private native static void prepareLocalEvent(Map<String, String> eventProperties) /*-{
		$wnd.vkEvent = {};
		var keySetIterator = eventProperties.@java.util.Map::keySet()().@java.util.Set::iterator()();
		while(keySetIterator.@java.util.Iterator::hasNext()())
		{
			var key = keySetIterator.@java.util.Iterator::next()();
			$wnd.vkEvent[key] = eventProperties.@java.util.Map::get(Ljava/lang/Object;)(key);
		}
	}-*/;
	private native static void prepareLocalEvent(@SuppressWarnings("rawtypes") DomEvent event, boolean instantiateEventObject, boolean alt
	, int buttonNum, int clientx, int clienty, boolean ctrl, Element currentEvtTarget, Element actualEvtTarget
	, int keyCode, int charCode, boolean meta, int mouseWheelVel, Element relativeEvtTarget, int screenx, int screeny
	, boolean shift) /*-{
		if(instantiateEventObject)
			$wnd.vkEvent = {};
		$wnd.vkEvent.altKey = alt;
		$wnd.vkEvent.button = buttonNum;
		$wnd.vkEvent.clientX = clientx;
		$wnd.vkEvent.clientY = clienty;
		$wnd.vkEvent.ctrlKey = ctrl;
		$wnd.vkEvent.currentEventTarget = currentEvtTarget;
		$wnd.vkEvent.eventTarget = actualEvtTarget;
		$wnd.vkEvent.keyCode = keyCode;
		$wnd.vkEvent.charCode = charCode;
		$wnd.vkEvent.metaKey = meta;
		$wnd.vkEvent.mouseWheelVelocity = mouseWheelVel;
		$wnd.vkEvent.relativeEventTarget = relativeEvtTarget;
		$wnd.vkEvent.screenX = screenx;
		$wnd.vkEvent.screenY = screeny;
		$wnd.vkEvent.shiftKey = shift;
		$wnd.vkEvent.preventDefault = function(){
			event.@com.google.gwt.event.dom.client.DomEvent::preventDefault()();
		}
		$wnd.vkEvent.stopPropagation = function(){
			event.@com.google.gwt.event.dom.client.DomEvent::stopPropagation()();
		}
	}-*/;

	public static void centerDialog(Panel dialog) {
		dialog.setVisible(true);
		DOM.setStyleAttribute(dialog.getElement(), "top", 
				(VkDesignerUtil.getDrawingPanel().getOffsetHeight() / 2 - dialog.getOffsetHeight() / 2) +  "px");
		DOM.setStyleAttribute(dialog.getElement(), "left", 
				(VkDesignerUtil.getDrawingPanel().getOffsetWidth() / 2 - dialog.getOffsetWidth() / 2) +  "px");
	}
	public static native String getComputedStyleAttribute(Element elem, String attr) /*-{
	    if (document.defaultView && document.defaultView.getComputedStyle) { // W3C DOM method
	      var value = null;
	      if (attr == 'float') { // fix reserved word
	        attr = 'cssFloat';
	      }
	      var computed = elem.ownerDocument.defaultView.getComputedStyle(elem, '');
	      if (computed) { // test computed before touching for safari
	        value = computed[attr];
	      }
	      return (elem.style[attr] || value || '');
	    } else if(elem.currentStyle){//IE
	    	return (elem.currentStyle[attr] || '');
	    }
	    else { // default to inline only
	      return (elem.style[attr] || '');
	    }
	}-*/;
	public static void loadApplication(String text) {
		engineMap.get(VkMainDrawingPanel.NAME).deserialize((IVkWidget) drawingPanel, text);
		VkDesignerUtil.executeEvent(drawingPanel.getPriorJs(HasVkLoadHandler.NAME), (Map<String, String>)null);
	}
	public native static void setLoadString(String str) /*-{
		$wnd.loadStr = str;
	}-*/;
	/**
	 * @param w
	 * @param prop css style property name e.g. border-top-width
	 * @return
	 */
	public static native int getPixelValue(Widget w, String prop)/*-{
		var ret;
		if(document.all){//IE
			var element = w.@com.google.gwt.user.client.ui.Widget::getElement()();
			var propertyArray = prop.split('-');
			var property = propertyArray[0];
			for(var i = 1, len = propertyArray.length(); i < len; i++)
				property += propertyArray[i].substr(0, 1).toUpperCase() + propertyArray[i].substr(1);
			var temp = element.style.left;
			element.style.left = element.style[property];
			ret = element.style.pixelLeft;
			element.style.left = temp;
		}
		else
			ret = $doc.defaultView.getComputedStyle(w.@com.google.gwt.user.client.ui.Widget::getElement()(), null).getPropertyValue(prop);
		try{
			return parseInt(ret);
		} catch(e){
			return -1;
		}
	}-*/;
	public native static String getCssText(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		return elem.style.cssText;
	}-*/;
	public static double getDecorationsWidth(Widget widget) {
		int height = VkDesignerUtil.getPixelValue(widget, "border-left-width");
		height += VkDesignerUtil.getPixelValue(widget, "border-right-width");
		height += VkDesignerUtil.getPixelValue(widget, "padding-left");
		height += VkDesignerUtil.getPixelValue(widget, "padding-right");
		height += VkDesignerUtil.getPixelValue(widget, "margin-left");
		height += VkDesignerUtil.getPixelValue(widget, "margin-right");
		return height;
	}
	public static double getDecorationsHeight(Widget widget) {
		int height = VkDesignerUtil.getPixelValue(widget, "border-top-width");
		height += VkDesignerUtil.getPixelValue(widget, "border-bottom-width");
		height += VkDesignerUtil.getPixelValue(widget, "padding-bottom");
		height += VkDesignerUtil.getPixelValue(widget, "padding-top");
		height += VkDesignerUtil.getPixelValue(widget, "margin-bottom");
		height += VkDesignerUtil.getPixelValue(widget, "margin-top");
		return height;
	}
	public static void showAddListDialog(String heading, final ListBox listBox, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				listBox.setFocus(true);
			}
		}.schedule(100);
		listBox.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(listBox.getValue(listBox.getSelectedIndex()));
				origDialog.hide();
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
	public static void showAddTextAttributeDialog(String heading, final TextBoxBase addTextTa, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		FocusPanel fp = new FocusPanel();
		origDialog.add(fp);
		fp.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE)
					origDialog.hide();
			}
		});
		final VerticalPanel dialog = new VerticalPanel();
		fp.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		}.schedule(100);
		addTextTa.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(addTextTa);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(addTextTa.getText());
				origDialog.hide();
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
	public static void showEventRegistrationDialog(HasVkEventHandler invokingWidget, String eventName, final IEventRegister iEventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Please add JS code below:");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		final VkEventTextArea addTextTa = new VkEventTextArea(); 
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		}.schedule(100);
		dialog.add(addTextTa);
		addTextTa.setPixelSize(500, 200);
		addTextTa.setText(invokingWidget.getPriorJs(eventName));
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iEventRegister.registerEvent(addTextTa.getText());
				origDialog.hide();
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
		origDialog.setPopupPosition(origDialog.getPopupLeft() + 1, origDialog.getPopupTop());//for IE 9, the textarea otherwise doesn't take id of other widgets when clicked on
	}
	public static void showAddAutoCompleteTextDialog(String heading, final AutoCompleterTextBox targetTb, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		new Timer(){
			@Override
			public void run() {
				targetTb.setFocus(true);
			}
		}.schedule(100);
		targetTb.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(targetTb);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(targetTb.getText());
				origDialog.hide();
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
	public static native void clearSelection() /*-{
		if($doc.selection)
			$doc.selection.empty();
		else
			$wnd.getSelection().removeAllRanges();
	}-*/;
	public static native void setCssText(Widget widget, String cssText) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		elem.style.cssText = cssText;
	}-*/;
}