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
package com.vk.gwt.designer.client.designer;

import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public interface IClipBoard {

	public void copyWidget(IVkWidget widget);

	public void cutWidget(IVkWidget widget);

	public void pasteWidget(IVkPanel panel);

	public void copyStyle(IVkWidget widget);

	public void pasteStyle(IVkWidget widget);

	public boolean isPasteWidgetPossible();

	public boolean isPasteStylePossible();

}
