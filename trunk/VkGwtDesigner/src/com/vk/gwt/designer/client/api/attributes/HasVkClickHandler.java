package com.vk.gwt.designer.client.api.attributes;

public interface HasVkClickHandler extends HasVkEventHandler{
	public final String NAME = "Click Handler";
	public void addClickHandler(String js);
}
