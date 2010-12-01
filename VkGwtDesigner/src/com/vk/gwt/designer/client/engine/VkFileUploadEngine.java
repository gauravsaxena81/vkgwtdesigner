package com.vk.gwt.designer.client.engine;

import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.widgets.VkFileUpload;

public class VkFileUploadEngine extends VkAbstractWidgetEngine<VkFileUpload> {
	@Override
	public VkFileUpload getWidget() {
		return new VkFileUpload();
	}
}
