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

import java.util.Iterator;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class LoadSaveHelper {
	private static LoadSaveHelper helper = new LoadSaveHelper();

	public void clearApplication() {
		Iterator<Widget> iterator = VkMainDrawingPanel.getInstance().iterator();
		while(iterator.hasNext())
			iterator.next().removeFromParent();
		SnapHelper.getInstance().init();
		UndoHelper.getInstance().init();
		ToolbarHelper.getInstance().hideToolbar();
	}

	public static LoadSaveHelper getInstance() {
		return helper;
	}

	public void saveApplication() {
		final DialogBox saveDialog = new DialogBox();
		DOM.setStyleAttribute(saveDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		saveDialog.setText("Please copy the save string below to reproduce application later");
		VerticalPanel vp = new VerticalPanel();
		vp.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		saveDialog.add(vp);
		TextArea ta = new TextArea();
		vp.add(ta);
		ta.setText(getSaveString());
		ta.setPixelSize(500, 200);
		Button ok = new Button("OK");
		vp.add(ok);
		ok.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				saveDialog.hide();
			}
		});
		saveDialog.center();		
	}

	public String getSaveString() {
		return WidgetEngineMapping.getInstance().getEngineMap().get(VkMainDrawingPanel.NAME).serialize(VkMainDrawingPanel.getInstance());
	}

	public void loadApplication(String str) {
		clearApplication();
		VkStateHelper.getInstance().setLoadRunning(true);
		VkDesignerUtil.loadApplication(str);
		VkStateHelper.getInstance().setLoadRunning(false);
	}
}
