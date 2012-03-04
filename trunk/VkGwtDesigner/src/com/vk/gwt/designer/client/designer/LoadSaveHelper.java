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

import java.util.Iterator;

import com.google.gwt.user.client.ui.Widget;

public class LoadSaveHelper implements ILoadSaveHelper{
	@Override
	public void clearApplication() {
		Iterator<Widget> iterator = VkMainDrawingPanel.getInstance().iterator();
		while(iterator.hasNext())
			iterator.next().removeFromParent();
		VkStateHelper.getInstance().getSnapHelper().clearSnappableWidgets();
		UndoHelper.getInstance().clear();
		VkStateHelper.getInstance().getToolbarHelper().hideToolbar();
	}
	@Override
	public String getSaveString() {
		return VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(VkMainDrawingPanel.NAME).serialize(VkMainDrawingPanel.getInstance());
	}
	@Override
	public void loadApplication(String str) {
		clearApplication();
		VkStateHelper.getInstance().setLoadRunning(true);
		VkDesignerUtil.loadApplication(str);
		VkStateHelper.getInstance().setLoadRunning(false);
	}
}
