package com.vk.gwt.designer.client.designer;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class KeyBoardHelper {
	private static KeyBoardHelper boardHelper = new KeyBoardHelper();
	private IVkWidget widget;
	
	private KeyBoardHelper(){
		declareKeyBoardHandler();
	}
	
	private native void declareKeyBoardHandler() /*-{
		var keyBoardHandler = this;
		$doc.onkeydown = function(ev){
			if(typeof ev == 'undefined')
				ev = $wnd.event;
			if(ev.keyCode == 46)//remove widget
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::deleteWidget()();
			else if(ev.keyCode == 67 && ev.ctrlKey && ev.shiftKey) //copy style
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::copyWidgetStyle()();
			else if(ev.keyCode == 86 && ev.ctrlKey && ev.shiftKey)//paste style
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::pasteWidgetStyle()();
			else if(ev.keyCode == 67 && ev.ctrlKey)//copy
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::copyWidget()();
			else if(ev.keyCode == 86 && ev.ctrlKey)//paste
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::pasteWidget()();
			else if(ev.keyCode == 88 && ev.ctrlKey) //cut
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::cutWidget()();
			else if(ev.keyCode == 89 && ev.ctrlKey)//redo
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::redo()();
			else if(ev.keyCode == 90 && ev.ctrlKey)//undo
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::undo()();
			else if(ev.keyCode == 39){//right arrow
				if(ev.ctrlKey)
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::resizeWidgetRight()();
				else
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::moveWidgetRight()();
			}
			else if(ev.keyCode == 37){//left arrow
				if(ev.ctrlKey)
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::resizeWidgetLeft()();
				else
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::moveWidgetLeft()();
			}
			else if(ev.keyCode == 38){//up arrow
				if(ev.ctrlKey)
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::resizeWidgetUp()();
				else
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::moveWidgetUp()();
			}
			else if(ev.keyCode == 40){//down arrow
				if(ev.ctrlKey)
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::resizeWidgetDown()();
				else
					keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::moveWidgetDown()();
			}
		};
	}-*/;

	public static KeyBoardHelper getInstance(){
		return boardHelper;
	}
	private void deleteWidget(){
		Command deleteCommand = VkStateHelper.getInstance().getMenu().getRemoveCommand();
		if(deleteCommand != null) {
			deleteCommand.execute();
			ToolbarHelper.getInstance().hideToolbar();
		}
	}
	private void copyWidgetStyle(){
		Command copyCommand = VkStateHelper.getInstance().getMenu().getCopyStyleCommand();
		if(copyCommand != null)
			copyCommand.execute();
	}
	private void pasteWidgetStyle(){
		Command pasteCommand = VkStateHelper.getInstance().getMenu().getPasteStyleCommand();
		if(pasteCommand != null)
			pasteCommand.execute();
	}
	private void copyWidget(){
		Command copyCommand = VkStateHelper.getInstance().getMenu().getCopyCommand();
		if(copyCommand != null)
			copyCommand.execute();
	}
	private void pasteWidget(){
		Command pasteCommand = VkStateHelper.getInstance().getMenu().getPasteCommand();
		if(pasteCommand != null)
			pasteCommand.execute();
	}
	private void cutWidget(){
		Command cutCommand = VkStateHelper.getInstance().getMenu().getCutCommand();
		if(cutCommand != null)
			cutCommand.execute();
	}
	private void redo(){
		Command redoCommand = VkStateHelper.getInstance().getMenu().getRedoCommand();
		redoCommand.execute();
	}
	private void undo(){
		Command undoCommand = VkStateHelper.getInstance().getMenu().getUndoCommand();
		undoCommand.execute();
	}
	private void moveWidgetRight(){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "left", VkDesignerUtil.getOffsetLeft(((UIObject) widget).getElement()) + 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	private void resizeWidgetRight(){
		if(widget.isResizable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "width", ((UIObject) widget).getElement().getOffsetWidth() 
			- VkDesignerUtil.getPixelValue(((Widget) widget).getElement(), "border-left-width") - VkDesignerUtil.getPixelValue(((Widget) widget).getElement(), "border-right-width") + 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	private void moveWidgetLeft(){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "left", VkDesignerUtil.getOffsetLeft(((UIObject) widget).getElement()) - 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	private void resizeWidgetLeft(){
		resizeWidgetRight();
		moveWidgetLeft();
	}
	private void moveWidgetUp(){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "top", VkDesignerUtil.getOffsetTop(((UIObject) widget).getElement()) - 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	private void resizeWidgetUp(){
		resizeWidgetDown();
		moveWidgetUp();
	}
	private void moveWidgetDown(){
		if(widget.isMovable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "top", VkDesignerUtil.getOffsetTop(((UIObject) widget).getElement()) + 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	private void resizeWidgetDown(){
		if(widget.isResizable()) {
			DOM.setStyleAttribute(((UIObject) widget).getElement(), "height", ((UIObject) widget).getElement().getOffsetHeight() 
			- VkDesignerUtil.getPixelValue(((Widget) widget).getElement(), "border-top-width") - VkDesignerUtil.getPixelValue(((Widget) widget).getElement(), "border-bottom-width") + 1 + "px");
			ToolbarHelper.getInstance().showToolbar((Widget) widget);
		}
	}
	public void setWidget(IVkWidget invokingWidget) {
		this.widget = invokingWidget;
	}
}
