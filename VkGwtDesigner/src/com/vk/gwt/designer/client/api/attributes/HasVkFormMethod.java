package com.vk.gwt.designer.client.api.attributes;

public interface HasVkFormMethod {
	public static final String NAME = "Set Method";
	public void addMethod(String methodName);
	public String getMethod();
}