package com.vk.gwt.designer.client.api.attributes;

public interface HasVkValue<T> {
	public static final String NAME = "Add Value";
	public void setValue(String value);
	public T getValue();
}
