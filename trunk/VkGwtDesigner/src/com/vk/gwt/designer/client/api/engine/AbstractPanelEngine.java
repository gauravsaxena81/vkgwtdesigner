package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public abstract class AbstractPanelEngine<T extends Panel> extends AbstractWidgetEngine<T> implements IPanelEngine<T> {
	@Override
	public List<String> getWidgetsList(Widget invokingWidget) {
		return VkDesignerUtil.getEngine().getWidgetsList(invokingWidget);
	}
}
