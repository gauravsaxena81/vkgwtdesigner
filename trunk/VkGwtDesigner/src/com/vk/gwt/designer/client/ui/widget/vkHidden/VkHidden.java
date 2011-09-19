package com.vk.gwt.designer.client.ui.widget.vkHidden;

import java.util.List;

import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkHidden extends SimplePanel implements IVkWidget, HasVkName, HasVkValue<String>{
	public static final String NAME = "Hidden";
	private Hidden hidden = new Hidden();
	public VkHidden()
	{
		add(hidden);
	}
	@Override
	public String getWidgetName() {
		return NAME;
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
	public String getName() {
		return hidden.getName();
	}

	@Override
	@Export
	public void setName(String name) {
		hidden.setName(name);
	}
	@Override
	@Export
	public String getValue() {
		return hidden.getValue();
	}
	@Override
	@Export
	public void setValue(String value) {
		hidden.setValue(value);
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}
