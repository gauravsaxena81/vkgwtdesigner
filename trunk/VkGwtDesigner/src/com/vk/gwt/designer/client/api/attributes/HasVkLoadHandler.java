package com.vk.gwt.designer.client.api.attributes;

public interface HasVkLoadHandler extends HasVkEventHandler {
	public static final String NAME = "Load Handler";
	public void addLoadHandler(String js);
}
