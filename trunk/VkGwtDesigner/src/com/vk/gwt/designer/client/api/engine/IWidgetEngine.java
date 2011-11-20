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
package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public interface IWidgetEngine<T extends Widget> {
	 public T getWidget();
	 public void applyAttribute(String attributeName, Widget invokingWidget);
	 public List<String> getAttributesList(Widget invokingWidget);
	 public List<String> getOperationsList(Widget invokingWidget);
	 public Widget deepClone(Widget sourceWidget, Widget targetWidget);
	 public String serialize(IVkWidget widget);
	 public void deserialize(IVkWidget invokingWidget, String jsonString);
	 public void buildWidget(JSONObject childObj, Widget parent);
}
