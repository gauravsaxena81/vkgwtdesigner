package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkToggleButton;

public class VkToggleButtonEngine extends VkAbstractWidgetEngine<VkToggleButton> {
	private static final String ADD_UP_IMAGE = "Add Up Image";
	private static final String ADD_DOWN_IMAGE = "Add Down Image";
	private static final String ADD_UP_HTML = "Add Up Html";
	private static final String ADD_DOWN_HTML = "Add Down Html";
	private static final String ADD_UP_TEXT = "Add Up Text";
	private static final String ADD_DOWN_TEXT = "Add Down Text";

	@Override
	public VkToggleButton getWidget() {
		VkToggleButton widget = new VkToggleButton();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkToggleButton widget = (VkToggleButton)invokingWidget;
		if(attributeName.equals(ADD_UP_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpFace().setImage(new Image(src));
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownFace().setImage(new Image(src));
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpFace().setHTML(html);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownFace().setHTML(html);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpFace().setText(text);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getDownFace().setText(text);
						}
					});
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(ADD_UP_IMAGE);
		list.add(ADD_DOWN_IMAGE);
		list.add(ADD_UP_HTML);
		list.add(ADD_DOWN_HTML);
		list.add(ADD_UP_TEXT);
		list.add(ADD_DOWN_TEXT);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
}