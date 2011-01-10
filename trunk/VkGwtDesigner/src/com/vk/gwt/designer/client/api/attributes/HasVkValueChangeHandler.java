package com.vk.gwt.designer.client.api.attributes;

public interface HasVkValueChangeHandler extends HasVkEventHandler {
	public static final String NAME = "Value Change Handler";
	public void addValueChangeHandler(String js);
}
