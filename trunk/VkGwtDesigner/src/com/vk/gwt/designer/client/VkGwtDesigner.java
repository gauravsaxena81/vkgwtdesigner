package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VkGwtDesigner implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(VkDesignerUtil.getDrawingPanel());
		//VkDesignerUtil.setEngineMap()Up
		//VkDesignerUtil.setEngine();
		//VkDesignerUtil.setMenu(vkMenu)
		if(Window.Location.getParameter("isDesignerMode") != null && Window.Location.getParameter("isDesignerMode").equals("false"))
		{
			VkDesignerUtil.loadApplication(getLoadString());
			VkDesignerUtil.isDesignerMode = false;
		}
		if(VkDesignerUtil.isDesignerMode)
			RootPanel.get().insert(VkDesignerUtil.getMenu(), 0);
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
