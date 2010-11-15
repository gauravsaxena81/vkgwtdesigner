package com.vk.gwt.designer.client.api.attributes;

public interface HasVkKeyDownHandler extends HasVkEventHandler{
	public final String NAME = "Key Down Handler";
	public void addKeyDownHandler(String js);
}
