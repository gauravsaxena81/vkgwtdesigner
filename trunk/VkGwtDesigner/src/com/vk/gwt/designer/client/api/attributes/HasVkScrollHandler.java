package com.vk.gwt.designer.client.api.attributes;

public interface HasVkScrollHandler extends HasVkEventHandler {
	public static final String NAME = "Scroll Handler";
	public void addScrollHandler(String js);
}
