package com.vk.gwt.designer.client.designer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
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
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
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
import com.vk.gwt.designer.client.engine.VkHyperlinkEngine;
import com.vk.gwt.designer.client.engine.VkImageEngine;
import com.vk.gwt.designer.client.engine.VkInlineHTMLEngine;
import com.vk.gwt.designer.client.engine.VkInlineHyperlinkEngine;
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
import com.vk.gwt.designer.client.widgets.VkHyperlink;
import com.vk.gwt.designer.client.widgets.VkImage;
import com.vk.gwt.designer.client.widgets.VkInlineHTML;
import com.vk.gwt.designer.client.widgets.VkInlineHyperlink;
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
	private static int widgetCount = 0;
	private static VkMenu menu = new VkMenu();
	private static Map<String, IWidgetEngine<? extends Widget>> engineMap = new LinkedHashMap<String, IWidgetEngine<? extends Widget>>();
	private static VkAbsolutePanel drawingPanel;
	private static VkEngine vkEngine = new VkEngine();
	public static boolean isDesignerMode = true;
	public native static void addPressAndHoldEvent(Widget widget, IWidgetEngine<? extends Widget> widgetEngine) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		//if(element.tagName == 'IMG')
			setTimeout(createMouseDownEvent, 200);//image takes time to load, thus event is not attached successfully if not waited for it
		//else
		//	createMouseDownEvent();
		$wnd.document.onmouseup = function(){
			@com.vk.gwt.designer.client.designer.VkDesignerUtil::setShowMenuFlag(Z)(false);
		};
		function isChild(target, parent)
		{
			while(target != null)
			{
				if(target == parent)
					return true;
				else
					target = target.parentNode;
			}
			return false;
		}
		function createMouseDownEvent()
		{
			element.oncontextmenu = function(ev){
				if(typeof ev == 'undefined')
					ev = $wnd.event;
				@com.vk.gwt.designer.client.designer.VkDesignerUtil::setShowMenuFlag(Z)(true);
				if(element.id != '')
				{
					//setTimeout(function(){
					//	if(showMenuFlag)
						{
							var menu = @com.vk.gwt.designer.client.designer.VkDesignerUtil::getMenu()();
							menu.@com.vk.gwt.designer.client.designer.VkMenu::prepareMenu(Lcom/google/gwt/user/client/ui/Widget;Lcom/vk/gwt/designer/client/api/engine/IWidgetEngine;)(widget, widgetEngine);
							if(isChild(!!ev.target ? ev.target : ev.srcElement, element))
							{
								menu = menu.@com.vk.gwt.designer.client.designer.VkMenu::getElement()();
								menu.style.display = "block";
								menu.style.left = ev.clientX + "px";
								menu.style.top = ev.clientY + "px";
								menu.focus();
							}
						}
						//else
							//menu.style.display = "none";
					//}, 500);
				}
				if(typeof ev.stopPropagation == 'undefined' || ev.stopPropagation == null)
					ev.cancelBubble = true;
				else
					ev.stopPropagation();
				return false;
			};
		}
	}-*/;
	
	@SuppressWarnings("unused")
	private native static void setShowMenuFlag(boolean flag)/*-{
		showMenuFlag = flag;
	}-*/;
	@SuppressWarnings("unused")
	private native static boolean getShowMenuFlag()/*-{
		return showMenuFlag;
	}-*/;
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
				js = js.replace(idArray[i],"$wnd.document.getElementById('" + idArray[i].substr(2,idArray[i].length - 3) + "')");
		return js;
	}-*/;

	public static void assignId(Widget widget) {
		widget.getElement().setId(++widgetCount + "");
	}

	public static void makeMovable(final Widget invokingWidget) {
		final HTML draggingWidget = new HTML("&nbsp;");
		DOM.setStyleAttribute(draggingWidget.getElement(), "background", "blue");
		draggingWidget.getElement().getStyle().setOpacity(0.2);
		getDrawingPanel().add(draggingWidget);
		DOM.setStyleAttribute(draggingWidget.getElement(), "position", "absolute");
		draggingWidget.setPixelSize(invokingWidget.getOffsetWidth(), invokingWidget.getOffsetHeight());
		DOM.setStyleAttribute(draggingWidget.getElement(), "top", invokingWidget.getElement().getAbsoluteTop() + "px");
		DOM.setStyleAttribute(draggingWidget.getElement(), "left", invokingWidget.getElement().getAbsoluteLeft() + "px");
		DOM.setCapture(draggingWidget.getElement());
		draggingWidget.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				int top = event.getClientY();
				int left = event.getClientX();
				if(top % 2 == 1)
					DOM.setStyleAttribute(draggingWidget.getElement(), "top", top + "px");
				if(left % 2 == 1)
					DOM.setStyleAttribute(draggingWidget.getElement(), "left", left + "px");
			}
		});
		draggingWidget.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				DOM.releaseCapture(draggingWidget.getElement());
				DOM.setStyleAttribute(invokingWidget.getElement(), "top", invokingWidget.getElement().getOffsetTop() 
						+ draggingWidget.getElement().getAbsoluteTop() - invokingWidget.getElement().getAbsoluteTop() + "px");
				DOM.setStyleAttribute(invokingWidget.getElement(), "left", invokingWidget.getElement().getOffsetLeft() 
						+ draggingWidget.getElement().getAbsoluteLeft()	- invokingWidget.getElement().getAbsoluteLeft() + "px");
				draggingWidget.removeFromParent();
			}
		});
	}
	public static void addWidget(Widget widget, IPanel invokingWidget) {
		addWidget(widget, invokingWidget, 0, 0);
	}
	public static void addWidget(Widget widget, IPanel invokingWidget, int top, int left) {
		if(!(widget instanceof PopupPanel))//Pop up panels should not be added to DOM
			invokingWidget.add(widget);
		else
			((PopupPanel)widget).center();
		placeAddedElement(widget.getElement(), invokingWidget, top, left);
	}
	private static void placeAddedElement(Element element, IPanel invokingWidget, int top, int left) {
		if(invokingWidget instanceof AbsolutePanel)
		{
			DOM.setStyleAttribute(element, "position", "absolute");
			DOM.setStyleAttribute(element, "top", top + "px");
			DOM.setStyleAttribute(element, "left", left + "px");
		}
		else if(!DOM.getStyleAttribute(element, "position").equals("absolute"))
			DOM.setStyleAttribute(element, "position", "relative");
	}
	private static void init()
	{
		setUpEngineMap();
		drawingPanel = new VkAbsolutePanel();
		VkDesignerUtil.addPressAndHoldEvent(drawingPanel, getEngineMap().get(VkAbsolutePanel.NAME));
		drawingPanel.getElement().setId("drawingPanel");
		drawingPanel.setPixelSize(Window.getClientWidth() - 10, Window.getClientHeight() - 10);
		DOM.setStyleAttribute(drawingPanel.getElement(), "border", "solid 1px gray");
		drawingPanel.add(menu);
	}
	private static void setUpEngineMap() {
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
		engineMap.put(VkHyperlink.NAME, new VkHyperlinkEngine());
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
		engineMap.put(VkInlineHyperlink.NAME, new VkInlineHyperlinkEngine());
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
		return menu;
	}
	public static void setMenu(VkMenu vkMenu)
	{
		menu = vkMenu;
	}
	public static VkAbsolutePanel getDrawingPanel() {
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
		final HTML scriptHtml = new HTML("<script> { " + VkDesignerUtil.formatJs(js) + " } </script>");//braces to ensure that none of the variables are declared in window scope
		VkDesignerUtil.getDrawingPanel().add(scriptHtml);
		Timer t = new Timer(){
			@Override
			public void run() {
				scriptHtml.removeFromParent();
			}
		};
		t.schedule(200);
	}
	@SuppressWarnings("unchecked")
	public static void executeEvent(String js, DomEvent event) {
		EventTarget currentEventTarget = event.getNativeEvent().getCurrentEventTarget();
		Element currentEventTargetElement = currentEventTarget != null && Element.is(currentEventTarget)? (Element) Element.as(currentEventTarget) : null;
		EventTarget eventTarget = event.getNativeEvent().getEventTarget();
		Element eventTargetElement = eventTarget != null ? (Element) Element.as(eventTarget) : null;
		EventTarget relativeEventTarget = event.getNativeEvent().getRelatedEventTarget();
		Element relativeEventTargetElement = relativeEventTarget != null ? (Element) Element.as(relativeEventTarget) : null;
		//JSNI was not able to work with methods of NativeElement and kept on throwing errors which made no sense.
		//Have a look at the issue raised at http://stackoverflow.com/questions/4086392/jsni-cannot-find-a-method-in-nativeevent-class-of-gwt 
		prepareLocalEvent(event, event.getNativeEvent().getAltKey(), event.getNativeEvent().getButton(), event.getNativeEvent().getClientX() 
				, event.getNativeEvent().getClientY(), event.getNativeEvent().getCtrlKey(), currentEventTargetElement, eventTargetElement
				, event.getNativeEvent().getKeyCode(), event.getNativeEvent().getMetaKey(), event.getNativeEvent().getMouseWheelVelocityY()
				, relativeEventTargetElement, event.getNativeEvent().getScreenX(), event.getNativeEvent().getScreenY(),event.getNativeEvent().getShiftKey());
		runJs("{ " + VkDesignerUtil.formatJs(js) + " }");//braces to ensure that none of the variables are declared in window scope
	}
	private static native void runJs(String formatJs) /*-{
		eval(formatJs);
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
	@SuppressWarnings("unchecked")
	private native static void prepareLocalEvent(DomEvent event, boolean alt, int buttonNum, int clientx, int clienty, boolean ctrl
			, Element currentEvtTarget, Element actualEvtTarget, int keycode, boolean meta, int mouseWheelVel, Element relativeEvtTarget, int screenx
			, int screeny, boolean shift) /*-{
		$wnd.vkEvent = {};
		$wnd.vkEvent.altKey = alt;
		$wnd.vkEvent.button = buttonNum;
		$wnd.vkEvent.clientX = clientx;
		$wnd.vkEvent.clientY = clienty;
		$wnd.vkEvent.ctrlKey = ctrl;
		$wnd.vkEvent.currentEventTarget = currentEvtTarget;
		$wnd.vkEvent.eventTarget = actualEvtTarget;
		$wnd.vkEvent.keyCode = keycode;
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
	public static native String getComputedStyleAttribute(Element elem, String attr) 
	  /*-{
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
}
