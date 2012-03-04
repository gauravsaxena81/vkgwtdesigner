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

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;

public class KeyBoardHelper implements IKeyBoardHelper{
	private IVkWidget widget;
	
	public KeyBoardHelper(){
		declareKeyBoardHandler();
	}
	
	private native void declareKeyBoardHandler() /*-{
		var keyBoardHandler = this;
		var stateHelper = @com.vk.gwt.designer.client.designer.VkStateHelper::getInstance()();
		var resizeHelper = stateHelper.@com.vk.gwt.designer.client.designer.VkStateHelper::getResizeHelper()();
		var moveHelper = stateHelper.@com.vk.gwt.designer.client.designer.VkStateHelper::getMoveHelper()(); 
		$doc.onkeydown = function(ev){
			if(typeof ev == 'undefined')
				ev = $wnd.event;
			if(ev.keyCode == 46)//delete
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::deleteWidget()();
			else if(ev.keyCode == 67 && ev.ctrlKey && ev.shiftKey) //ctrl + shift + c
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::copyWidgetStyle()();
			else if(ev.keyCode == 86 && ev.ctrlKey && ev.shiftKey)//ctrl + shift + v
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::pasteWidgetStyle()();
			else if(ev.keyCode == 67 && ev.ctrlKey)//ctrl + c
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::copyWidget()();
			else if(ev.keyCode == 86 && ev.ctrlKey)//ctrl + v
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::pasteWidget()();
			else if(ev.keyCode == 88 && ev.ctrlKey) //ctrl + x
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::cutWidget()();
			else if(ev.keyCode == 89 && ev.ctrlKey)//ctrl + z
				keyBoardHandler.@com.vk.gwt.designer.client.designer.KeyBoardHelper::redo()();
			else if(ev.keyCode == 90 && ev.ctrlKey)//ctrl + y
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

	private void resizeWidgetRight() {
		VkStateHelper.getInstance().getResizeHelper().resizeWidgetRight(widget);
	}
	private void moveWidgetRight() {
		VkStateHelper.getInstance().getMoveHelper().moveWidgetRight(widget);
	}
	private void resizeWidgetLeft() {
		VkStateHelper.getInstance().getResizeHelper().resizeWidgetLeft(widget);
	}
	private void moveWidgetLeft() {
		VkStateHelper.getInstance().getMoveHelper().moveWidgetLeft(widget);
	}
	private void resizeWidgetUp() {
		VkStateHelper.getInstance().getResizeHelper().resizeWidgetUp(widget);
	}
	private void moveWidgetUp() {
		VkStateHelper.getInstance().getMoveHelper().moveWidgetUp(widget);
	}
	private void resizeWidgetDown() {
		VkStateHelper.getInstance().getResizeHelper().resizeWidgetDown(widget);
	}
	private void moveWidgetDown() {
		VkStateHelper.getInstance().getMoveHelper().moveWidgetDown(widget);
	}
	private void deleteWidget(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.DELETE)) {
			VkStateHelper.getInstance().getEngine().removeWidget((Widget) widget);
			VkStateHelper.getInstance().getMenu().prepareMenu(widget.getVkParent());
		}
	}
	private void copyWidgetStyle(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.COPY_STYLE))
			VkStateHelper.getInstance().getClipBoardHelper().copyStyle(widget);
	}
	private void pasteWidgetStyle(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.PASTE_STYLE))
			VkStateHelper.getInstance().getClipBoardHelper().pasteStyle(widget);
	}
	private void copyWidget(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.COPY))
			VkStateHelper.getInstance().getClipBoardHelper().copyWidget(widget);
	}
	private void pasteWidget(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.PASTE) 
				&& widget instanceof IVkPanel)
			VkStateHelper.getInstance().getClipBoardHelper().pasteWidget((IVkPanel) widget);
	}
	private void cutWidget(){
		if(VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widget.getWidgetName()).getOperationsList((Widget) widget).contains(IEngine.COPY))
			VkStateHelper.getInstance().getClipBoardHelper().cutWidget(widget);
	}
	private void redo(){
		UndoHelper.getInstance().redo();
	}
	private void undo(){
		UndoHelper.getInstance().undo();
	}
	@Override
	public void setWidget(IVkWidget invokingWidget) {
		this.widget = invokingWidget;
	}
}
