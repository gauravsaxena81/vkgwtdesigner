package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VkGwtDesigner implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(VkDesignerUtil.getDrawingPanel());
		//VkDesignerUtil.getEngineMap()
		//VkDesignerUtil.setEngine();
	}
}
