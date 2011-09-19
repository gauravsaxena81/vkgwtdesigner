package com.vk.gwt.designer.client.ui.panel.vkStackPanel;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkStackPanel extends StackPanel implements HasVkWidgets, IVkPanel, HasVkSwitchNumberedWidget, HasVkCaptionHtml, HasVkCaptionText {
	public static final String NAME = "Stack Panel";
	 @Override
	 public void add(Widget w) {
		super.add(w);
		setHeaderHtml(getWidgetCount()- 1, "Untitled");
	 }
	@Override
	public int getCurrentlyShowingWidget() {
		return getSelectedIndex();
	}
	@Override
	public void showWidget(int index) {
		super.showStack(index);
	}
	private Element getHeaderElement(int index)
	{
		Element body = (Element) getElement().getElementsByTagName("TBODY").getItem(0);
		Element tdWrapper = DOM.getChild(DOM.getChild( body, index * 2), 0);
		return DOM.getFirstChild(tdWrapper);
	}
	@Override
	public void setCaptionHtml(String html) {
		if(checkAccess(getSelectedIndex()))
			setHeaderHtml(getSelectedIndex(), html);
	}
	@Override
	public void setCaptionText(String text) {
		if(checkAccess(getSelectedIndex()))
			setHeaderText(getSelectedIndex(), text);
	}
	@Override
	public String getCaptionHtml() {
		if(checkAccess(getSelectedIndex()))
			return getHTML(getSelectedIndex());
		else
			throw new IndexOutOfBoundsException();
	}
	@Override
	public String getCaptionText() {
		if(checkAccess(getSelectedIndex()))
			return getText(getSelectedIndex());
		else
			throw new IndexOutOfBoundsException();
	}
	private boolean checkAccess(int index) {
		if(index >=0 && index < super.getWidgetCount())
			return true;
		else if(VkDesignerUtil.isDesignerMode)
			Window.alert("None of the stack panels are selected");
		return false;
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {
		int widgetCount = this.getWidgetCount();
		for(int i = 0 ; i < widgetCount; i++)
			((VkStackPanel)targetWidget).setHeaderHtml(i, this.getHTML(i));
	}
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
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
	}
	@Override
	@Export
	public int getSelectedIndex() {
		return super.getSelectedIndex();
	}
	@Override
	@Export
	public void showStack(int index)
	{
		super.showStack(index);
	}
	@Export
	public void setHeaderHtml(int index, String html) {
		checkIndexBoundsForAccess(index);
		super.setStackText(index, html, true);
	}
	@Export
	public String getHTML(int index) {
		checkIndexBoundsForAccess(index);
		return DOM.getInnerHTML(getHeaderElement(index));
	}
	@Export
	public String getText(int index) {
		checkIndexBoundsForAccess(index);
		return DOM.getInnerText(getHeaderElement(index));
	}
	@Export
	public void setHeaderText(int index, String text) {
		checkIndexBoundsForAccess(index);
		super.setStackText(index, text, false);
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
