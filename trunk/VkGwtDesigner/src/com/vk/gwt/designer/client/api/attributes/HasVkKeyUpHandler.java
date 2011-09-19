package com.vk.gwt.designer.client.api.attributes;

public interface HasVkKeyUpHandler extends HasVkEventHandler{
	public final String NAME = "Key Up Handler";
	public void addKeyUpHandler(String js);
}
