package com.vk.gwt.designer.client.designer;

import com.google.gwt.user.client.ui.Widget;

public interface ISnapHelper {

	int getSnappedLeft(int left, int width);

	int getSnappedTop(int top, int height);

	int getSnappedWidth(int left, int width);

	int getSnappedHeight(int top, int height);

	void addToSnappableWidgets(Widget widget);

	void removeFromSnappableWidgets(Widget widget);

	void clearSnappableWidgets();

	Widget getIgnoreWidget();

	void setIgnoreWidget(Widget ignoreWidget);

}
