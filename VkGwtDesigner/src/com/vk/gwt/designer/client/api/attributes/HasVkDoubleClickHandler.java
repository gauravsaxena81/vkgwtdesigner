package com.vk.gwt.designer.client.api.attributes;

public interface HasVkDoubleClickHandler extends HasVkEventHandler {
	public final String NAME = "Double Click Handler";
	public void addDoubleClickHandler(String js);
}
