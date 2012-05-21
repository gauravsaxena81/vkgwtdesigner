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
package com.vk.gwt.designer.client.ui.widget.text.vkPasswordTextBox;

import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;

public class VkPasswordTextBoxEngine extends VkAbstractWidgetEngine<VkPasswordTextBox> {
	@Override
	public VkPasswordTextBox getWidget() {
		VkPasswordTextBox widget = new VkPasswordTextBox();
		init(widget);
		return widget;
	}
}