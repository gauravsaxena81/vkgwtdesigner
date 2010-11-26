package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkStackPanel extends StackPanel implements HasVkWidgets, IPanel, HasVkSwitchNumberedWidget, HasVkHtml, HasVkText {
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
	public void setHTML(String html) {
		setHeaderHtml(getSelectedIndex(), html);
	}
	@Override
	public void setText(String text) {
		setHeaderText(getSelectedIndex(), text);
	}
	@Override
	public String getHTML() {
		return getHTML(getSelectedIndex());
	}
	@Override
	public String getText() {
		return getText(getSelectedIndex());
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
	public void setHeaderHtml(int selectedIndex, String html) {
		super.setStackText(selectedIndex, html, true);
	}
	@Export
	public String getHTML(int index) {
		return DOM.getInnerHTML(getHeaderElement(index));
	}
	@Export
	public String getText(int index) {
		return DOM.getInnerText(getHeaderElement(index));
	}
	@Export
	public void setHeaderText(int selectedIndex, String text) {
		super.setStackText(selectedIndex, text, false);
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
}
