package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VkGwtDesigner implements EntryPoint {
	public final void onModuleLoad() {
		RootPanel.get().add(VkDesignerUtil.getDrawingPanel());
		init();
		if(Window.Location.getParameter("isDesignerMode") != null && Window.Location.getParameter("isDesignerMode").equals("false")) {
			VkDesignerUtil.isDesignerMode = false;
			VkDesignerUtil.loadApplication(getLoadString());
		}
		if(VkDesignerUtil.isDesignerMode)
			RootPanel.get().insert(VkDesignerUtil.getMenu(), 0);
	}
	protected void init() {
		//VkDesignerUtil.setEngineMap()Up
		//VkDesignerUtil.setEngine();
		//VkDesignerUtil.setMenu(vkMenu)
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
