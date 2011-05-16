package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.widgets.VkDialogBox;

public class VkDialogBoxEngine extends VkAbstractWidgetEngine<VkDialogBox> {
	private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkDialogBox getWidget() {
		VkDialogBox widget = new VkDialogBox();
		widget.setPixelSize(200, 100);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDialogBox widget = (VkDialogBox)invokingWidget;
		if(attributeName.equals(SET_POSITION))
		{
			widget.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
			Window.alert("Position of Panel is saved. Panel will appear at this position when it shows.");
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(SET_POSITION);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	protected void addAttributes(JSONObject childObj, Widget widget) {
		super.addAttributes(childObj, widget);
		if(((HasVkInitiallyShowing)widget).isInitiallyShowing())
			((VkDialogBox)widget).show();
		else
			((VkDialogBox)widget).hide();
	}
}