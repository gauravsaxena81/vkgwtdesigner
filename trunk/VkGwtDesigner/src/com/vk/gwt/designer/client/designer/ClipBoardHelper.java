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

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class ClipBoardHelper implements IClipBoard {
	private boolean executeCutsRemoveCommand;
	private IVkWidget copyWidget;
	private IVkWidget copyStyleWidget;
	@Override
	public void copyWidget(IVkWidget widget) {
		copyWidget = widget;
	}
	@Override
	public void cutWidget(IVkWidget widget){
		copyWidget(widget);
		executeCutsRemoveCommand = true;
	}
	@Override
	public void pasteWidget(IVkPanel panel) {
		final boolean tempExecuteCutsRemoveCommand = executeCutsRemoveCommand;
		final IVkPanel tempInvokingWidget = panel;
		final IVkWidget tempCopyWidget = copyWidget;
		final IVkPanel prevParent = (IVkPanel) ((IVkWidget)tempCopyWidget).getVkParent();
		final Widget widget;						
		if(tempExecuteCutsRemoveCommand)
			widget = (Widget) tempCopyWidget;
		else {
			widget = VkStateHelper.getInstance().getEngine().getWidget((tempCopyWidget).getWidgetName());
			VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(tempCopyWidget.getWidgetName()).deepClone((Widget)copyWidget, widget);
		}
		if(tempCopyWidget != null) {
			if(tempInvokingWidget instanceof IVkPanel) {
				final int top = VkDesignerUtil.getOffsetTop(((UIObject) widget).getElement());
				final int left = VkDesignerUtil.getOffsetLeft(((UIObject) widget).getElement());
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						widget.removeFromParent();
						VkStateHelper.getInstance().getEngine().addWidget(widget , ((IVkPanel)tempInvokingWidget), 8, 4);
					}}, new Command(){
							@Override
							public void execute() {
								if(tempExecuteCutsRemoveCommand) {
									widget.removeFromParent();
									VkStateHelper.getInstance().getEngine().addWidget(widget , prevParent, top, left);
								} else {
									widget.removeFromParent();
									VkStateHelper.getInstance().getToolbarHelper().hideToolbar();
								}
							}
						});
			}
		}
		executeCutsRemoveCommand = false;
	}
	@Override
	public void copyStyle(IVkWidget widget) {
		this.copyStyleWidget = widget;
	}
	@Override
	public void pasteStyle(IVkWidget widget) {
		String top = DOM.getStyleAttribute(((UIObject) widget).getElement(), "top");
		String left = DOM.getStyleAttribute(((UIObject) widget).getElement(), "left");
		VkDesignerUtil.setCssText((Widget) widget, VkDesignerUtil.getCssText((Widget) copyStyleWidget));
		DOM.setStyleAttribute(((UIObject) widget).getElement(), "left", left);
		DOM.setStyleAttribute(((UIObject) widget).getElement(), "top", top);
	}
	@Override
	public boolean isPasteWidgetPossible() {
		return copyWidget != null;
	}
	@Override
	public boolean isPasteStylePossible() {
		return copyStyleWidget != null;
	}
}
