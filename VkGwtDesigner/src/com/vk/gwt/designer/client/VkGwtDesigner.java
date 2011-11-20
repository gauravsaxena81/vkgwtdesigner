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
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine;
import com.vk.gwt.designer.client.designer.VkMainDrawingPanel;
import com.vk.gwt.designer.client.designer.VkMenu;
import com.vk.gwt.designer.client.designer.VkStateHelper;

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
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
