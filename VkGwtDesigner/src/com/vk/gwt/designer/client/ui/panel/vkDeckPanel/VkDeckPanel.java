package com.vk.gwt.designer.client.ui.panel.vkDeckPanel;

import java.util.List;

import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;

public class VkDeckPanel extends DeckPanel implements IVkPanel, HasVkWidgets, HasVkAnimation, HasVkSwitchNumberedWidget {
	final public static String NAME = "Deck Panel";
	@Override
	public int getCurrentlyShowingWidget() {
		return getVisibleWidget();
	}
	@Override
	public void add(Widget w){
		super.add(w);
		super.showWidget(super.getWidgetCount() - 1);
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
