package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.ui.DeckPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkDeckPanel extends DeckPanel implements IPanel, HasVkWidgets, HasVkAnimation, HasVkSwitchNumberedWidget {
	final public static String NAME = "Deck Panel";
	@Override
	public int getCurrentlyShowingWidget() {
		return getVisibleWidget();
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getVisibleWidget() {
	    return super.getVisibleWidget();
	}
	@Override
	@Export
	public boolean isAnimationEnabled()
	{
		return super.isAnimationEnabled();
	}
	@Override
	@Export
	public void setAnimationEnabled(boolean enable) {
	    super.setAnimationEnabled(enable);
	}
	@Override
	@Export
	public void showWidget(int index)
	{
		super.showWidget(index);
	}
	@Override
	@Export
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
}
