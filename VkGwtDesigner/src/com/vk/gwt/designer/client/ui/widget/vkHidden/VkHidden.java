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
package com.vk.gwt.designer.client.ui.widget.vkHidden;

import java.util.List;

import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkHidden extends SimplePanel implements IVkWidget, HasVkName, HasVkValue<String>{
	public static final String NAME = "Hidden";
	private Hidden hidden = new Hidden();
	private IVkWidget vkParent;
	public VkHidden()
	{
		add(hidden);
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getName() {
		return hidden.getName();
	}

	@Override
	@Export
	public void setName(String name) {
		hidden.setName(name);
	}
	@Override
	@Export
	public String getValue() {
		return hidden.getValue();
	}
	@Override
	@Export
	public void setValue(String value) {
		hidden.setValue(value);
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
