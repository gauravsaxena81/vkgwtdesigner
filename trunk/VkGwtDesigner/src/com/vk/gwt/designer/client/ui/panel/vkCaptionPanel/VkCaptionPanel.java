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
package com.vk.gwt.designer.client.ui.panel.vkCaptionPanel;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkCaptionPanel extends CaptionPanel implements IVkPanel,HasVkWidgets, HasVkCaptionText, HasVkCaptionHtml {
	public static final String NAME = "Caption Panel";
	private IVkWidget vkParent;
	@Override
	public void add(Widget w) {
		if(getContentWidget() != null) {
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Caption Panel can add only one Widget");
		} else {
			super.add(w);
			if(w instanceof IVkWidget)
				((IVkWidget)w).setVkParent(this);
		}
	}
	@Override
	public String getCaptionHtml() {
		return super.getCaptionHTML();
	}
	@Override
	public void setCaptionHtml(String html) {
		super.setCaptionHTML(html);
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
	public void setCaptionText(String text)
	{
		super.setCaptionText(text);
	}
	@Override
	@Export
	public String getCaptionText()
	{
		return super.getCaptionText();
	}
	@Override
	@Export
	public void setCaptionHTML(String html)
	{
		super.setCaptionHTML(html);
	}
	@Override
	@Export
	public String getCaptionHTML()
	{
		return super.getCaptionHTML();
	}
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
	@Override
	public List<Widget> getToolbarWidgets() {
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
