package com.vk.gwt.designer.client.api.attributes;

public interface HasVkErrorHandler extends HasVkEventHandler {
	public static final String NAME = "Error Handler";
	public void addErrorHandler(String js);
}
