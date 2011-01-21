package com.vk.gwt.designer.client.api.attributes;


public interface HasVkInitializeHandlers extends HasVkEventHandler{
	public static final String NAME = "Initialize Handler";
	public void addInitializeHandler(String js);
}
