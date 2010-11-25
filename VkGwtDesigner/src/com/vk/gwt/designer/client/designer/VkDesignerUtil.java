package com.vk.gwt.designer.client.designer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.Panels.VkDisclosurePanel;
import com.vk.gwt.designer.client.Panels.VkDockPanel;
import com.vk.gwt.designer.client.Panels.VkFlowPanel;
import com.vk.gwt.designer.client.Panels.VkFocusPanel;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.Panels.VkScrollPanel;
import com.vk.gwt.designer.client.Panels.VkSimplePanel;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.engine.VkAbsolutePanelEngine;
import com.vk.gwt.designer.client.engine.VkButtonEngine;
import com.vk.gwt.designer.client.engine.VkCaptionPanelEngine;
import com.vk.gwt.designer.client.engine.VkCheckboxEngine;
import com.vk.gwt.designer.client.engine.VkDeckPanelEngine;
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
import com.vk.gwt.designer.client.engine.VkLabelEngine;
import com.vk.gwt.designer.client.engine.VkListBoxEngine;
import com.vk.gwt.designer.client.engine.VkMenuBarHorizontalEngine;
import com.vk.gwt.designer.client.engine.VkMenuBarVerticalEngine;
import com.vk.gwt.designer.client.engine.VkScrollPanelEngine;
import com.vk.gwt.designer.client.engine.VkSimplePanelEngine;
import com.vk.gwt.designer.client.engine.VkStackPanelEngine;
import com.vk.gwt.designer.client.engine.VkTabPanelEngine;
import com.vk.gwt.designer.client.engine.VkTextBoxEngine;
import com.vk.gwt.designer.client.engine.VkVerticalPanelEngine;
import com.vk.gwt.designer.client.engine.VkVerticalSplitPanelEngine;
import com.vk.gwt.designer.client.widgets.VkButton;
import com.vk.gwt.designer.client.widgets.VkCheckbox;
import com.vk.gwt.designer.client.widgets.VkFileUpload;
import com.vk.gwt.designer.client.widgets.VkFlexTable;
import com.vk.gwt.designer.client.widgets.VkFrame;
import com.vk.gwt.designer.client.widgets.VkGrid;
import com.vk.gwt.designer.client.widgets.VkHTML;
import com.vk.gwt.designer.client.widgets.VkHidden;
import com.vk.gwt.designer.client.widgets.VkHyperlink;
import com.vk.gwt.designer.client.widgets.VkImage;
import com.vk.gwt.designer.client.widgets.VkLabel;
import com.vk.gwt.designer.client.widgets.VkListBox;
import com.vk.gwt.designer.client.widgets.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;
import com.vk.gwt.designer.client.widgets.VkTextBox;

public class VkDesignerUtil {
	private static int widgetCount = 0;
	private static VkMenu menu = new VkMenu();
	private static Map<String, IWidgetEngine<? extends Widget>> engineMap = new LinkedHashMap<String, IWidgetEngine<? extends Widget>>();
	private static VkAbsolutePanel drawingPanel;
	private static VkEngine vkEngine = new VkEngine();
	public native static void addPressAndHoldEvent(Widget widget, IWidgetEngine<? extends Widget> widgetEngine) /*-{
		var element = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		if(element.tagName == 'IMG')
			setTimeout(createMouseDownEvent, 200);//image takes time to load, thus event is not attached successfully if not waited for it
		else
			createMouseDownEvent();
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
			element.onmousedown = function(ev){
				@com.vk.gwt.designer.client.designer.VkDesignerUtil::setShowMenuFlag(Z)(true);
				if(element.id != '' && ev.button == 1)
				{
					setTimeout(function(){
						if(showMenuFlag)
						{
							var menu = @com.vk.gwt.designer.client.designer.VkDesignerUtil::getMenu()();
							menu.@com.vk.gwt.designer.client.designer.VkMenu::setWidgetEngine(Lcom/vk/gwt/designer/client/api/engine/IWidgetEngine;)(widgetEngine);
							menu.@com.vk.gwt.designer.client.designer.VkMenu::setInvokingWidget(Lcom/google/gwt/user/client/ui/Widget;)(widget);
							menu.@com.vk.gwt.designer.client.designer.VkMenu::prepareMenu()();
							if(isChild(ev.target, element))
							{
								menu = menu.@com.vk.gwt.designer.client.designer.VkMenu::getElement()();
								menu.style.display = "block";
								menu.style.left = ev.clientX + "px";
								menu.style.top = ev.clientY + "px";
							}
						}
						else
							menu.style.display = "none";
					}, 500);
				}
				ev.stopPropagation();
			};
		}
	}-*/;
	
	private native static void setShowMenuFlag(boolean flag)/*-{
		showMenuFlag = flag;
	}-*/;
	private native static boolean getShowMenuFlag()/*-{
		return showMenuFlag;
	}-*/;

	public static void addPressAndHoldEvent(final FocusWidget widget, final IWidgetEngine<? extends Widget> widgetEngine) {
		widget.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(final MouseDownEvent event) {
				final int top = event.getClientY();
				final int left = event.getClientX();
				setShowMenuFlag(true);
				final EventTarget eventTarget = event.getNativeEvent().getEventTarget();
				if(event.getNativeButton() == NativeEvent.BUTTON_MIDDLE)
				{
					Timer t = new Timer(){
						@Override
						public void run() {
							if(getShowMenuFlag())
							{
								if( Element.as(eventTarget).equals(widget.getElement()))
								{
									getMenu().setInvokingWidget(widget);
									getMenu().setWidgetEngine(widgetEngine);
									getMenu().prepareMenu();
									getMenu().setVisible(true);
									getMenu().focus();
									DOM.setStyleAttribute(getMenu().getElement(), "top", top + "px");
									DOM.setStyleAttribute(getMenu().getElement(), "left", left + "px");
									setShowMenuFlag(false);
								}
							}
						}
					};
					t.schedule(500);
					event.stopPropagation();
				}
			}
		});
		widget.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setShowMenuFlag(false);
			}
		});
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
				js = js.replace(idArray[i],"document.getElementById('" + idArray[i].substr(2,idArray[i].length - 3) + "')");
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
		widget.setPixelSize(100, 20);
		assignId(widget);
		invokingWidget.add(widget);
		placeAddedElement(widget.getElement(), invokingWidget, top, left);
	}
	private static void placeAddedElement(Element element, IPanel invokingWidget, int top, int left) {
		if(invokingWidget instanceof AbsolutePanel)
		{
			DOM.setStyleAttribute(element, "position", "absolute");
			DOM.setStyleAttribute(element, "top", top + "px");
			DOM.setStyleAttribute(element, "left", left + "px");
		}
	}
	private static void init()
	{
		setUpEngineMap();
		drawingPanel = new VkAbsolutePanel();
		//VkMenu designerMenu = new VkMenu(drawingPanel, VkDesignerUtil.getEngineMap().get(VkAbsolutePanel.NAME));
		//drawingPanel.add(designerMenu);
		VkDesignerUtil.addPressAndHoldEvent(drawingPanel, getEngineMap().get(VkAbsolutePanel.NAME));
		drawingPanel.getElement().setId("drawingPanel");
		drawingPanel.setPixelSize(Window.getClientWidth() - 20, Window.getClientHeight() - 20);
		DOM.setStyleAttribute(drawingPanel.getElement(), "border", "solid 1px green");
		drawingPanel.add(menu);
		/*Button saveJson = new Button("Save");
		RootPanel.get().add(saveJson);
		saveJson.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				for(int i = 0; i < drawingPanel.getWidgetCount(); i++)
					if(!drawingPanel.getWidget(i).getElement().getId().isEmpty())
						Window.alert(drawingPanel.getWidget(i).getElement().getId());
			}
		});*/
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
	}
	public static Map<String, IWidgetEngine<? extends Widget>> getEngineMap() {
		return engineMap;
	}
	public static VkMenu getMenu()
	{
		return menu;
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
		Element currentEventTargetElement = currentEventTarget != null ? (Element) Element.as(currentEventTarget) : null;
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
