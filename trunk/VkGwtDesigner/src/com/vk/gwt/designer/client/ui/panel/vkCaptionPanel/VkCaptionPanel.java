package com.vk.gwt.designer.client.ui.panel.vkCaptionPanel;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkCaptionPanel extends CaptionPanel implements IVkPanel,HasVkWidgets, HasVkCaptionText, HasVkCaptionHtml{
	public static final String NAME = "Caption Panel";
	@Override
	public void add(Widget w)
	{
		if(getContentWidget() != null)
		{
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Caption Panel can add only one Widget");
		}
		else
			super.add(w);
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
		// TODO Auto-generated method stub
		return null;
	}
}
