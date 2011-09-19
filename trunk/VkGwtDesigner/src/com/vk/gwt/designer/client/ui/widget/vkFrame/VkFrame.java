package com.vk.gwt.designer.client.ui.widget.vkFrame;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkFrame extends Frame implements IVkWidget, HasVkUrl, HasVkName{

	public static final String NAME = "Frame";
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public String getName() {
		return DOM.getElementAttribute(getElement(), "name");
	}
	@Override
	public void setName(String name) {
		DOM.setStyleAttribute(getElement(), "name", name);
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getUrl() {
		return super.getUrl();
	}
	@Override
	@Export
	public void setUrl(String url) {
		super.setUrl(url);
	}
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}
