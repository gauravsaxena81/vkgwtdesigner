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

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public interface IEngine {
	public static final String DELETE = "Delete";
	public static final String RESIZE = "Resize";
	public static final String MOVE = "Move";
	public static final String ADD_CLASS = "Add Classname";
	public static final String REMOVE_CLASS = "Remove Classname";
	public static final String TOOL_TIP = "Tool Tip";
	public static final String FILL_PARENT = "Fill Parent";
	public static final String CUT = "Cut";
	public static final String COPY = "Copy";
	public static final String PASTE = "Paste";
	public static final String NEW = "New";
	public static final String SAVE = "Save";
	public static final String SAVE_AS = "Save as";
	public static final String OPEN = "Open";
	public static final String LOAD = "Load a file";
	public static final String COPY_STYLE = "Copy Style";
	public static final String PASTE_STYLE = "Paste Style";
	public static final String CLEAR = "Clear";
	public static final String FILE = "File";
	public static final String STRING = "String";
	public List<String> getAttributesList(Widget invokingWidget);
	public void applyAttribute(String attributeName, Widget invokingWidget);
	public List<String> getWidgetsList(Widget invokingWidget);
	public List<String> getPanelsList(IVkWidget invokingWidget);
	List<String> getOperationsList(Widget invokingWidget);
	Widget getWidget(String widgetName);
	void prepareWidget(Widget widget);
	void removeWidget(Widget widget);
	void addWidget(Widget widget, IVkPanel panelWidget);
	void addWidget(Widget widget, IVkPanel panelWidget, int top, int left);
	void addWidget(Widget widget, IVkPanel panelWidget, int top, int left, int beforeIndex);
	Widget addWidget(String widgetName, IVkPanel panelWidget, int top, int left);
	Widget addWidget(String widgetName, IVkPanel panelWidget);
	
}
