package com.vk.gwt.designer.client.api.attributes;

public interface HasVkBeforeSelectionHandler extends HasVkEventHandler {
	public static final String NAME = "Add Before Selection Handler";
	public void addBeforeSelectionHandler(String js);
}