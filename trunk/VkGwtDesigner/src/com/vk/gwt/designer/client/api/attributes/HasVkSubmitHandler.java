package com.vk.gwt.designer.client.api.attributes;

public interface HasVkSubmitHandler extends HasVkEventHandler {
	public static final String NAME = "Submit Handler";
	/**
	 * Requires that the javascript entered in handler should be a function which returns either true or false. When true is returned form is submitted
	 * and when false is returned form is not submitted.
	 * @param js
	 */
	public void addSubmitHandler(String js);
}
