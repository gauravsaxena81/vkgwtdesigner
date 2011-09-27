package com.vk.gwt.designer.client.designer;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.HasDoubleClickHandlers;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;

public class InitializeHelper {
	private static InitializeHelper initializeHelper = new InitializeHelper();
	
	private InitializeHelper(){}
	
	public static InitializeHelper getInstance(){
		return initializeHelper;
	}
	
	public void initDesignerEvents(Widget widget, IWidgetEngine<? extends Widget> widgetEngine){
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
			@com.vk.gwt.designer.client.designer.InitializeHelper::addTextBox(Lcom/google/gwt/user/client/ui/Widget;)(widget);
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
					case KeyCodes.KEY_DELETE:
						event.stopPropagation();
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
				//element.addEventListener("mouseover", mouseOverHandler, false);
			}
			else if(element.attachEvent) {
				element.attachEvent("onmousedown", mouseDownHandler);
				//element.attachEvent("onmouseover", mouseOverHandler);
			}
			function mouseOverHandler(ev) {
				ev = ev || $wnd.event;
				if(element.id != 'drawingPanel' && getTarget(ev.target || ev.srcElement) == element) {
					//ev = ev || $wnd.event;
					//if(ev.stopPropagation)
					//	ev.stopPropagation();
					//else
					//	ev.cancelBubble = true;
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
					var toolBarHelper = @com.vk.gwt.designer.client.designer.ToolbarHelper::getInstance()()
					toolBarHelper.@com.vk.gwt.designer.client.designer.ToolbarHelper::showToolbar(Lcom/google/gwt/user/client/ui/Widget;)(widget);
					var vkStatehelper = @com.vk.gwt.designer.client.designer.VkStateHelper::getInstance()();
					var menu = vkStatehelper.@com.vk.gwt.designer.client.designer.VkStateHelper::getMenu()();
					menu.@com.vk.gwt.designer.client.designer.VkMenu::prepareMenu(Lcom/vk/gwt/designer/client/api/component/IVkWidget;)(widget);
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
				var vkStatehelper = @com.vk.gwt.designer.client.designer.VkStateHelper::getInstance()();
				var menu = vkStatehelper.@com.vk.gwt.designer.client.designer.VkStateHelper::getMenu()();
				if(ev.keyCode == 46) {//remove widget
					var deleteCommand = menu.@com.vk.gwt.designer.client.designer.VkMenu::getRemoveCommand()();
					if(deleteCommand != null)
					{
						deleteCommand.@com.google.gwt.user.client.Command::execute()();
						var toolBarHelper = @com.vk.gwt.designer.client.designer.ToolbarHelper::getInstance()()
						toolBarHelper.@com.vk.gwt.designer.client.designer.ToolbarHelper::hideToolbar()();
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
}
