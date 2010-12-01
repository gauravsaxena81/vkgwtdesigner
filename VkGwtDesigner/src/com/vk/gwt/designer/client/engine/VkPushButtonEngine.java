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
import com.vk.gwt.designer.client.widgets.VkPushButton;
import com.vk.gwt.designer.client.widgets.VkToggleButton;

public class VkPushButtonEngine extends VkAbstractWidgetEngine<VkPushButton> {
	private static final String ADD_UP_IMAGE = "Add Up Image";
	private static final String ADD_UP_HOVERING_IMAGE = "Add Up Hover Image";
	private static final String ADD_UP_DISABLED_IMAGE = "Add Up Disabled Image";
	private static final String ADD_DOWN_IMAGE = "Add Down Image";
	private static final String ADD_DOWN_HOVERING_IMAGE = "Add Down Hover Image";
	private static final String ADD_DOWN_DISABLED_IMAGE = "Add Down Disabled Image";
	private static final String ADD_UP_HTML = "Add Up Html";
	private static final String ADD_UP_HOVERING_HTML = "Add Up Hover Html";
	private static final String ADD_UP_DISABLED_HTML = "Add Up Disabled Html";
	private static final String ADD_DOWN_HTML = "Add Down Html";
	private static final String ADD_DOWN_HOVERING_HTML = "Add Down Hover Html";
	private static final String ADD_DOWN_DISABLED_HTML = "Add Down Disabled Html";
	private static final String ADD_UP_TEXT = "Add Up Text";
	private static final String ADD_UP_HOVERING_TEXT = "Add Up Hover Text";
	private static final String ADD_UP_DISABLED_TEXT = "Add Up Disabled Text";
	private static final String ADD_DOWN_TEXT = "Add Down Text";
	private static final String ADD_DOWN_HOVERING_TEXT = "Add Down Hover Text";
	private static final String ADD_DOWN_DISABLED_TEXT = "Add Down Disabled Text";
	private String imageUpSrc;
	private String imageDownSrc;
	@Override
	public VkPushButton getWidget() {
		VkPushButton widget = new VkPushButton();
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
							imageUpSrc = src;
						}
					});
		}
		if(attributeName.equals(ADD_UP_HOVERING_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up hover image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpHoveringFace().setImage(new Image(src));
							imageUpSrc = src;
						}
					});
		}
		if(attributeName.equals(ADD_UP_DISABLED_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up disabled image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpDisabledFace().setImage(new Image(src));
							imageUpSrc = src;
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
							imageDownSrc = src;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down hover image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownHoveringFace().setImage(new Image(src));
							imageDownSrc = src;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down disabled image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownDisabledFace().setImage(new Image(src));
							imageDownSrc = src;
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
							imageUpSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HOVERING_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up hover html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpHoveringFace().setHTML(html);
							imageUpSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_UP_DISABLED_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up disabled html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpDisabledFace().setHTML(html);
							imageUpSrc = null;
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
							imageDownSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down hover html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownHoveringFace().setHTML(html);
							imageDownSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_HTML))
		{
			TextArea ta = new TextArea();
			ta.setSize("100px","50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down disabled html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownDisabledFace().setHTML(html);
							imageDownSrc = null;
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
							imageUpSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HOVERING_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up hover text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpHoveringFace().setText(text);
							imageUpSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_UP_DISABLED_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide up disabled text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpDisabledFace().setText(text);
							imageUpSrc = null;
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
							imageDownSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down hover text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getDownHoveringFace().setText(text);
							imageDownSrc = null;
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setWidth("100px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide down disabled text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getDownDisabledFace().setText(text);
							imageDownSrc = null;
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
		list.add(ADD_UP_HOVERING_IMAGE);
		list.add(ADD_UP_DISABLED_IMAGE);
		list.add(ADD_DOWN_IMAGE);
		list.add(ADD_DOWN_HOVERING_IMAGE);
		list.add(ADD_DOWN_DISABLED_IMAGE);
		list.add(ADD_UP_HTML);
		list.add(ADD_UP_HOVERING_HTML);
		list.add(ADD_UP_DISABLED_HTML);
		list.add(ADD_DOWN_HTML);
		list.add(ADD_DOWN_HOVERING_HTML);
		list.add(ADD_DOWN_DISABLED_HTML);
		list.add(ADD_UP_TEXT);
		list.add(ADD_UP_HOVERING_TEXT);
		list.add(ADD_UP_DISABLED_TEXT);
		list.add(ADD_DOWN_TEXT);
		list.add(ADD_DOWN_HOVERING_TEXT);
		list.add(ADD_DOWN_DISABLED_TEXT);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget)
	{
		super.copyAttributes(widgetSource, widgetTarget);
		VkToggleButton buttonSource = (VkToggleButton)widgetSource;
		VkToggleButton buttonTarget = (VkToggleButton)widgetTarget;
		if(!buttonSource.getUpFace().getText().isEmpty())
			buttonTarget.getUpFace().setText(buttonSource.getUpFace().getText());
		else if(!buttonSource.getUpFace().getHTML().isEmpty())
			buttonTarget.getUpFace().setHTML(buttonSource.getUpFace().getHTML());
		else
			buttonTarget.getUpFace().setImage(new Image(imageUpSrc));
		
		if(!buttonSource.getUpHoveringFace().getText().isEmpty())
			buttonTarget.getUpHoveringFace().setText(buttonSource.getUpHoveringFace().getText());
		else if(!buttonSource.getUpHoveringFace().getHTML().isEmpty())
			buttonTarget.getUpHoveringFace().setHTML(buttonSource.getUpHoveringFace().getHTML());
		else
			buttonTarget.getUpHoveringFace().setImage(new Image(imageUpSrc));
		
		if(!buttonSource.getUpDisabledFace().getText().isEmpty())
			buttonTarget.getUpDisabledFace().setText(buttonSource.getUpDisabledFace().getText());
		else if(!buttonSource.getUpDisabledFace().getHTML().isEmpty())
			buttonTarget.getUpDisabledFace().setHTML(buttonSource.getUpDisabledFace().getHTML());
		else
			buttonTarget.getUpDisabledFace().setImage(new Image(imageUpSrc));
		
		if(!buttonSource.getDownFace().getText().isEmpty())
			buttonTarget.getDownFace().setText(buttonSource.getDownFace().getText());
		else if(!buttonSource.getDownFace().getHTML().isEmpty())
			buttonTarget.getDownFace().setHTML(buttonSource.getDownFace().getHTML());
		else
			buttonTarget.getDownFace().setImage(new Image(imageDownSrc));
		
		if(!buttonSource.getDownHoveringFace().getText().isEmpty())
			buttonTarget.getDownHoveringFace().setText(buttonSource.getDownHoveringFace().getText());
		else if(!buttonSource.getDownHoveringFace().getHTML().isEmpty())
			buttonTarget.getDownHoveringFace().setHTML(buttonSource.getDownHoveringFace().getHTML());
		else
			buttonTarget.getDownHoveringFace().setImage(new Image(imageDownSrc));
		
		if(!buttonSource.getDownDisabledFace().getText().isEmpty())
			buttonTarget.getDownDisabledFace().setText(buttonSource.getDownDisabledFace().getText());
		else if(!buttonSource.getDownDisabledFace().getHTML().isEmpty())
			buttonTarget.getDownDisabledFace().setHTML(buttonSource.getDownDisabledFace().getHTML());
		else
			buttonTarget.getDownDisabledFace().setImage(new Image(imageDownSrc));
	}
}
