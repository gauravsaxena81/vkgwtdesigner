package com.vk.gwt.designer.client.designer;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;

public class VkMainDrawingPanelEngine extends VkAbstractWidgetEngine<VkMainDrawingPanel> {
	@Override
	public VkMainDrawingPanel getWidget() {
		VkMainDrawingPanel drawingPanel = VkMainDrawingPanel.getInstance();
		return drawingPanel;
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		addAttributes(jsonObj, parent);
		super.buildWidget(jsonObj, parent);
	}
}
