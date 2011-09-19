package com.vk.gwt.designer.client.designer;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;

public class VkMainDrawingPanelEngine extends VkAbstractWidgetEngine<VkMainDrawingPanel> {
	@Override
	public VkMainDrawingPanel getWidget() {
		VkMainDrawingPanel drawingPanel = VkMainDrawingPanel.getInstance();
		drawingPanel.getElement().setId("drawingPanel");
		drawingPanel.setPixelSize(Window.getClientWidth() - 10, Window.getClientHeight() - 10);
		DOM.setStyleAttribute(drawingPanel.getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(drawingPanel.getElement(), "position", "relative");
		return drawingPanel;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		addAttributes(jsonObj, parent);
		super.buildWidget(jsonObj, parent);
	}
}
