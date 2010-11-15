package com.vk.gwt.designer.client.api.attributes;

public interface HasVkCloseHandler extends HasVkEventHandler{
	public static final String NAME = "Close Handler";
	public void addCloseHandler(String js);
}
