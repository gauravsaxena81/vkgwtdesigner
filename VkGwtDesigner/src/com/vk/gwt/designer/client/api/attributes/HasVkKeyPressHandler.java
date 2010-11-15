package com.vk.gwt.designer.client.api.attributes;

public interface HasVkKeyPressHandler extends HasVkEventHandler{
	public final String NAME = "Key Press Handler";
	public void addKeyPressHandler(String js);
}
