package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkPopUpPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkPopUpPanelEngine extends VkAbstractWidgetEngine<VkPopUpPanel> {
	private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkPopUpPanel getWidget() {
		VkPopUpPanel widget = new VkPopUpPanel();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkPopUpPanel widget = (VkPopUpPanel)invokingWidget;
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
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		super.buildWidget(jsonObj, parent);
		if(((HasVkInitiallyShowing)parent).isInitiallyShowing())
			((VkPopUpPanel)parent).show();
		else
			((VkPopUpPanel)parent).hide();
	}
}
