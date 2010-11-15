package com.vk.gwt.designer.client.api.attributes;

public interface HasVkSubmitCompleteHandler extends HasVkEventHandler {
	public static final String NAME = "Submit Complete Handler";
	public void addSubmitCompleteHandler(String js);
}
