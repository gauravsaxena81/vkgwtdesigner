package com.vk.gwt.designer.client.api.attributes;

public interface HasVkMouseDownHandler extends HasVkEventHandler{
	public final String NAME = "Mouse Down Handler";
	public void addMouseDownHandler(String js);
}
