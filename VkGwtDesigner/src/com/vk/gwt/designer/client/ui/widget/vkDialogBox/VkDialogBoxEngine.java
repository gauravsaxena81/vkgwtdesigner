package com.vk.gwt.designer.client.ui.widget.vkDialogBox;

import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;

public class VkDialogBoxEngine extends VkAbstractWidgetEngine<VkDialogBox> {
	//private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkDialogBox getWidget() {
		VkDialogBox widget = new VkDialogBox();
		widget.setText("Untitled");
		widget.setModal(false);
		if(VkDesignerUtil.isDesignerMode)
			widget.add(VkDesignerUtil.getEngine().getWidget(VkAbsolutePanel.NAME));
		widget.setPixelSize(200, 100);
		return widget;
	}
	/*@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDialogBox widget = (VkDialogBox)invokingWidget;
		if(attributeName.equals(SET_POSITION))
		{
			widget.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
			Window.alert("Position of Panel is saved. Panel will appear at this position when it shows.");
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}*/
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		//list.add(0, SET_POSITION);
		return list;
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		super.addAttributes(childObj, widget);
		int top = VkDesignerUtil.getPixelValue(widget, "top");
		int left = VkDesignerUtil.getPixelValue(widget, "left");
		((VkDialogBox)widget).setPopupPosition(left, top);
		if(((HasVkInitiallyShowing)widget).isInitiallyShowing())
			((VkDialogBox)widget).show();
		else
			((VkDialogBox)widget).hide();
	}
}
