package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkMainDrawingPanel;
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
		//VkDesignerUtil.setEngineMap();
		//VkDesignerUtil.setEngine();
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
