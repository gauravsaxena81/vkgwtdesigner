package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkPopUpPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkPopUpPanelEngine extends VkAbstractWidgetEngine<VkPopUpPanel> {
	//private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkPopUpPanel getWidget() {
		VkPopUpPanel widget = new VkPopUpPanel();
		widget.show();
		widget.setModal(false);
		init(widget);
		return widget;
	}
	/*@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkPopUpPanel widget = (VkPopUpPanel)invokingWidget;
		if(attributeName.equals(SET_POSITION))	{
			widget.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
			Window.alert("Position of Panel is saved. Panel will appear at this position when it shows.");
		} else
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
	public void copyAttributes(Widget widgetSource, Widget widgetTarget) {
		int top = VkDesignerUtil.getPixelValue(widgetSource, "top");
		int left = VkDesignerUtil.getPixelValue(widgetSource, "left");
		((VkPopUpPanel)widgetTarget).setPopupPosition(left, top);
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		super.addAttributes(childObj, widget);
		int top = VkDesignerUtil.getPixelValue(widget, "top");
		int left = VkDesignerUtil.getPixelValue(widget, "left");
		((VkPopUpPanel)widget).setPopupPosition(left, top);
		if(((HasVkInitiallyShowing)widget).isInitiallyShowing())
			((VkPopUpPanel)widget).show();
		else
			((VkPopUpPanel)widget).hide();
	}
}