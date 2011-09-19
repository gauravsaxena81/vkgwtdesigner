package com.vk.gwt.designer.client.api.attributes;

public interface HasVkChangeHandler extends HasVkEventHandler{
	public final static String NAME = "Change Handler";
	public void addChangeHandler(String js);
}
