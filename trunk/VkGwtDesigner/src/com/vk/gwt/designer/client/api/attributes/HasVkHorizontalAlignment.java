package com.vk.gwt.designer.client.api.attributes;

public interface HasVkHorizontalAlignment {
	public static final String NAME = "Set Horizontal Alignment";
	public static final String LEFT = "Align Left";
	public static final String CENTER = "Align Center";
	public static final String RIGHT = "Align Right";
	public void setHorizontalAlignment(String horizontalAlignment);
	public String getHorizontalAlignmentString();
}
