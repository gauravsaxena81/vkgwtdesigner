package com.vk.gwt.designer.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.RootPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VkGwtDesigner implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(VkDesignerUtil.getDrawingPanel());
		RichTextArea richTextArea = new RichTextArea();
		RootPanel.get().add(richTextArea);
		richTextArea.addMouseWheelHandler(new MouseWheelHandler(){
			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				System.out.println("1");
			}});
		//VkDesignerUtil.setEngineMap()Up
		//VkDesignerUtil.setEngine();
		//VkDesignerUtil.setMenu(vkMenu)
		if(Window.Location.getParameter("isDesignerMode") != null && Window.Location.getParameter("isDesignerMode").equals("false"))
		{
			VkDesignerUtil.isDesignerMode = false;
			VkDesignerUtil.loadApplication(getLoadString());
		}
		if(VkDesignerUtil.isDesignerMode)
			RootPanel.get().insert(VkDesignerUtil.getMenu(), 0);
	}
	private native String getLoadString() /*-{
		return $wnd.opener.loadStr;
	}-*/;
}
