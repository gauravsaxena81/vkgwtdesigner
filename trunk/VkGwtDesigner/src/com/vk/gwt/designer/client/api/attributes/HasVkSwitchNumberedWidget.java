package com.vk.gwt.designer.client.api.attributes;

public interface HasVkSwitchNumberedWidget {
	public static final String NAME = "Show Widget";
	public void showWidget(int index);
	public int getCurrentlyShowingWidget();
}
