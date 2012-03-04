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

import com.vk.gwt.designer.client.api.engine.IEngine;

public class VkStateHelper {
	private static VkStateHelper vkStateHelper = new VkStateHelper();
	private IVkMenu vkMenu;
	private IClipBoard clipBoardHelper;
	private IEventHelper eventHelper;
	private IKeyBoardHelper keyBoardHelper;
	private ILoadSaveHelper loadSaveHelper;
	private IMoveHelper moveHelper;
	private ResizeHelper resizeHelper;
	private ISnapHelper snapHelper;
	private IToolbarHelper toolbarHelper;
	private IUndoHelper undoHelper;
	private boolean isDesignerMode = true;
	private boolean isLoadRunning = false;
	private WidgetEngineMapping widgetEngineMapping;
	private IEngine vkEngine;
	
	private VkStateHelper(){}
	
	public IClipBoard getClipBoardHelper() {
		return clipBoardHelper;
	}

	public void setClipBoardHelper(IClipBoard clipBoardHelper) {
		this.clipBoardHelper = clipBoardHelper;
	}	
	public static VkStateHelper getInstance(){
		return vkStateHelper;
	}
	public IVkMenu getMenu() {
		return vkMenu;
	}
	public void setMenu(IVkMenu vkMenu) {
		this.vkMenu = vkMenu;
	}
	public IEngine getEngine() {
		return vkEngine;
	}
	public void setEngine(IEngine newVkEngine) {
		this.vkEngine = newVkEngine;
	}
	
	public boolean isDesignerMode() {
		return isDesignerMode;
	}

	public void setDesignerMode(boolean isDesignerMode) {
		this.isDesignerMode = isDesignerMode;
	}

	public boolean isLoadRunning() {
		return isLoadRunning;
	}

	public void setLoadRunning(boolean isLoadRunning) {
		this.isLoadRunning = isLoadRunning;
	}

	public void setEventHelper(IEventHelper eventHelper) {
		this.eventHelper = eventHelper;
	}

	public IEventHelper getEventHelper() {
		return eventHelper;
	}

	public void setKeyBoardHelper(IKeyBoardHelper keyBoardHelper) {
		this.keyBoardHelper = keyBoardHelper;
	}

	public IKeyBoardHelper getKeyBoardHelper() {
		return keyBoardHelper;
	}

	public void setLoadSaveHelper(ILoadSaveHelper loadSaveHelper) {
		this.loadSaveHelper = loadSaveHelper;
	}

	public ILoadSaveHelper getLoadSaveHelper() {
		return loadSaveHelper;
	}

	public void setMoveHelper(IMoveHelper moveHelper) {
		this.moveHelper = moveHelper;
	}

	public IMoveHelper getMoveHelper() {
		return moveHelper;
	}

	public void setResizeHelper(ResizeHelper resizeHelper) {
		this.resizeHelper = resizeHelper;
	}

	public ResizeHelper getResizeHelper() {
		return resizeHelper;
	}

	public void setSnapHelper(ISnapHelper snapHelper) {
		this.snapHelper = snapHelper;
	}

	public ISnapHelper getSnapHelper() {
		return snapHelper;
	}

	public void setToolbarHelper(IToolbarHelper toolbarHelper) {
		this.toolbarHelper = toolbarHelper;
	}

	public IToolbarHelper getToolbarHelper() {
		return toolbarHelper;
	}

	public void setUndoHelper(IUndoHelper undoHelper) {
		this.undoHelper = undoHelper;
	}

	public IUndoHelper getUndoHelper() {
		return undoHelper;
	}

	public void setWidgetEngineMapping(WidgetEngineMapping widgetEngineMapping) {
		this.widgetEngineMapping = widgetEngineMapping;
	}

	public WidgetEngineMapping getWidgetEngineMapping() {
		return widgetEngineMapping;
	}
}
