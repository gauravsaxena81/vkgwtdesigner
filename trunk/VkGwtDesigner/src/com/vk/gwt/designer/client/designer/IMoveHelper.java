package com.vk.gwt.designer.client.designer;

import com.vk.gwt.designer.client.api.component.IVkWidget;

public interface IMoveHelper {

	void makeMovable(IVkWidget widget);

	void moveWidgetLeft(IVkWidget widget);

	void moveWidgetDown(IVkWidget widget);

	void moveWidgetRight(IVkWidget widget);

	void moveWidgetUp(IVkWidget widget);

}
