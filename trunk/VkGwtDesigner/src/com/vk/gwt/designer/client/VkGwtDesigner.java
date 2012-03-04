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
package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.ClipBoardHelper;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.KeyBoardHelper;
import com.vk.gwt.designer.client.designer.LoadSaveHelper;
import com.vk.gwt.designer.client.designer.MoveHelper;
import com.vk.gwt.designer.client.designer.ResizeHelper;
import com.vk.gwt.designer.client.designer.SnapHelper;
import com.vk.gwt.designer.client.designer.ToolbarHelper;
import com.vk.gwt.designer.client.designer.UndoHelper;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine;
import com.vk.gwt.designer.client.designer.VkMainDrawingPanel;
import com.vk.gwt.designer.client.designer.VkMenu;
import com.vk.gwt.designer.client.designer.VkStateHelper;
import com.vk.gwt.designer.client.designer.WidgetEngineMapping;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VkGwtDesigner implements EntryPoint {
	public final void onModuleLoad() {
		init();
		if(Window.Location.getParameter("isDesignerMode") != null && Window.Location.getParameter("isDesignerMode").equals("false")) {
			VkStateHelper.getInstance().setDesignerMode(false);
			VkDesignerUtil.loadApplication(getLoadString());
		}
		if(VkStateHelper.getInstance().isDesignerMode())
			RootPanel.get().add(VkStateHelper.getInstance().getMenu());
		RootPanel.get().add(VkMainDrawingPanel.getInstance());
	}
	protected void init() {
		VkStateHelper.getInstance().setEngine(new VkEngine());
		VkStateHelper.getInstance().setMenu(new VkMenu());
		VkStateHelper.getInstance().setClipBoardHelper(new ClipBoardHelper());
		VkStateHelper.getInstance().setEventHelper(new EventHelper());
		VkStateHelper.getInstance().setKeyBoardHelper(new KeyBoardHelper());
		VkStateHelper.getInstance().setLoadSaveHelper(new LoadSaveHelper());
		VkStateHelper.getInstance().setMoveHelper(new MoveHelper());
		VkStateHelper.getInstance().setResizeHelper(new ResizeHelper());
		VkStateHelper.getInstance().setSnapHelper(new SnapHelper());
		VkStateHelper.getInstance().setToolbarHelper(new ToolbarHelper());
		VkStateHelper.getInstance().setUndoHelper(new UndoHelper());
		VkStateHelper.getInstance().setWidgetEngineMapping(new WidgetEngineMapping());
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
