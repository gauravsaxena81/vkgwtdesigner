/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
