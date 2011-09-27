package com.vk.gwt.designer.client.ui.widget.button.vkPushButton;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IEventRegister;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkPushButtonEngine extends VkAbstractWidgetEngine<VkPushButton> {
	private static final String ADD_UP_IMAGE = "Add Up Image";
	private static final String ADD_UP_HTML = "Add Up Html";
	private static final String ADD_UP_TEXT = "Add Up Text";
	private static final String ADD_UP_HOVERING_IMAGE = "Add Up Hover Image";
	private static final String ADD_UP_HOVERING_HTML = "Add Up Hover Html";
	private static final String ADD_UP_HOVERING_TEXT = "Add Up Hover Text";
	private static final String ADD_UP_DISABLED_IMAGE = "Add Up Disabled Image";
	private static final String ADD_UP_DISABLED_HTML = "Add Up Disabled Html";
	private static final String ADD_UP_DISABLED_TEXT = "Add Up Disabled Text";
	private static final String ADD_DOWN_IMAGE = "Add Down Image";
	private static final String ADD_DOWN_HTML = "Add Down Html";
	private static final String ADD_DOWN_TEXT = "Add Down Text";
	private static final String ADD_DOWN_HOVERING_IMAGE = "Add Down Hover Image";
	private static final String ADD_DOWN_HOVERING_HTML = "Add Down Hover Html";
	private static final String ADD_DOWN_HOVERING_TEXT = "Add Down Hover Text";
	private static final String ADD_DOWN_DISABLED_IMAGE = "Add Down Disabled Image";
	private static final String ADD_DOWN_DISABLED_HTML = "Add Down Disabled Html";
	private static final String ADD_DOWN_DISABLED_TEXT = "Add Down Disabled Text";
	private static final String ALLOW_HOLD_DOWN = "Click Holds Button Down";
	
	@Override
	public VkPushButton getWidget() {
		VkPushButton widget = new VkPushButton();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkPushButton widget = (VkPushButton)invokingWidget;
		if(attributeName.equals(ADD_UP_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageUpSrc() != null)
				tb.setText(widget.getImageUpSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpFace().setImage(new Image(src));
							widget.setImageUpSrc(src);
						}
					});
		}
		if(attributeName.equals(ADD_UP_HOVERING_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageUpHoveringSrc() != null)
				tb.setText(widget.getImageUpHoveringSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up hover image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpHoveringFace().setImage(new Image(src));
							widget.setImageUpHoveringSrc(src);
						}
					});
		}
		if(attributeName.equals(ADD_UP_DISABLED_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageUpDisabledSrc() != null)
				tb.setText(widget.getImageUpDisabledSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up disabled image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getUpDisabledFace().setImage(new Image(src));
							widget.setImageUpDisabledSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageDownSrc() != null)
				tb.setText(widget.getImageDownSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownFace().setImage(new Image(src));
							widget.setImageDownSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageDownHoveringSrc() != null)
				tb.setText(widget.getImageDownHoveringSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down hover image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownHoveringFace().setImage(new Image(src));
							widget.setImageDownHoveringSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setWidth("300px");
			if(widget.getImageDownDisabledSrc() != null)
				tb.setText(widget.getImageDownDisabledSrc());
			else
				tb.setText("");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down disabled image url", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String src) {
							widget.getDownDisabledFace().setImage(new Image(src));
							widget.setImageDownDisabledSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getUpFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpFace().setHTML(html);
							widget.setImageUpSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HOVERING_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getUpHoveringFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up hover html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpHoveringFace().setHTML(html);
							widget.setImageUpHoveringSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_DISABLED_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getUpDisabledFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up disabled html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getUpDisabledFace().setHTML(html);
							widget.setImageUpDisabledSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getDownFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownFace().setHTML(html);
							widget.setImageDownSrc (null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getDownHoveringFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down hover html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownHoveringFace().setHTML(html);
							widget.setImageDownHoveringSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_HTML))
		{
			TextArea ta = new TextArea();
			ta.setText(widget.getDownDisabledFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down disabled html", ta
					, new IEventRegister() {
						@Override
						public void registerEvent(String html) {
							widget.getDownDisabledFace().setHTML(html);
							widget.setImageDownDisabledSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getUpFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpFace().setText(text);
							widget.setImageUpSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HOVERING_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getUpHoveringFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up hover text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpHoveringFace().setText(text);
							widget.setImageUpHoveringSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_DISABLED_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getUpDisabledFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up disabled text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getUpDisabledFace().setText(text);
							widget.setImageUpDisabledSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getDownFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.setImageDownSrc(null);
							widget.getDownFace().setText(text);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HOVERING_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getDownHoveringFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down hover text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getDownHoveringFace().setText(text);
							widget.setImageDownHoveringSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_DISABLED_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setText(widget.getDownDisabledFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down disabled text", tb
					, new IEventRegister() {
						@Override
						public void registerEvent(String text) {
							widget.getDownDisabledFace().setText(text);
							widget.setImageDownDisabledSrc(null);
						}
					});
		}
		else if(attributeName.equals(ALLOW_HOLD_DOWN))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("100px");
			listBox.addItem("True","0");
			listBox.addItem("False","1");
			listBox.setSelectedIndex(widget.isAllowHoldDown() ? 0 : 1);
			VkDesignerUtil.showAddListDialog("Please provide option for holding button down on click", listBox, new IEventRegister(){
				@Override
				public void registerEvent(String js) {
					if(listBox.getSelectedIndex() == 0)
						widget.setAllowHoldDown(true);
					else
						widget.setAllowHoldDown(false);
				}});
		}
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(ADD_UP_IMAGE);
		list.add(ADD_UP_HTML);
		list.add(ADD_UP_TEXT);
		list.add(ADD_UP_HOVERING_IMAGE);
		list.add(ADD_UP_HOVERING_HTML);
		list.add(ADD_UP_HOVERING_TEXT);
		list.add(ADD_UP_DISABLED_IMAGE);
		list.add(ADD_UP_DISABLED_HTML);
		list.add(ADD_UP_DISABLED_TEXT);
		list.add(ADD_DOWN_IMAGE);
		list.add(ADD_DOWN_HTML);
		list.add(ADD_DOWN_TEXT);
		list.add(ADD_DOWN_HOVERING_IMAGE);
		list.add(ADD_DOWN_HOVERING_HTML);
		list.add(ADD_DOWN_HOVERING_TEXT);
		list.add(ADD_DOWN_DISABLED_IMAGE);
		list.add(ADD_DOWN_DISABLED_HTML);
		list.add(ADD_DOWN_DISABLED_TEXT);
		list.add(ALLOW_HOLD_DOWN);
		list.addAll(VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget));
		return list;
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget)
	{
		super.copyAttributes(widgetSource, widgetTarget);
		VkPushButton buttonSource = (VkPushButton)widgetSource;
		VkPushButton buttonTarget = (VkPushButton)widgetTarget;
		if(!buttonSource.getUpFace().getText().isEmpty())
			buttonTarget.getUpFace().setText(buttonSource.getUpFace().getText());
		else if(!buttonSource.getUpFace().getHTML().isEmpty())
			buttonTarget.getUpFace().setHTML(buttonSource.getUpFace().getHTML());
		else
			buttonTarget.getUpFace().setImage(new Image(buttonSource.getImageUpSrc()));
		
		if(!buttonSource.getUpHoveringFace().getText().isEmpty())
			buttonTarget.getUpHoveringFace().setText(buttonSource.getUpHoveringFace().getText());
		else if(!buttonSource.getUpHoveringFace().getHTML().isEmpty())
			buttonTarget.getUpHoveringFace().setHTML(buttonSource.getUpHoveringFace().getHTML());
		else
			buttonTarget.getUpHoveringFace().setImage(new Image(buttonSource.getImageUpHoveringSrc()));
		
		if(!buttonSource.getUpDisabledFace().getText().isEmpty())
			buttonTarget.getUpDisabledFace().setText(buttonSource.getUpDisabledFace().getText());
		else if(!buttonSource.getUpDisabledFace().getHTML().isEmpty())
			buttonTarget.getUpDisabledFace().setHTML(buttonSource.getUpDisabledFace().getHTML());
		else
			buttonTarget.getUpDisabledFace().setImage(new Image(buttonSource.getImageUpDisabledSrc()));
		
		if(!buttonSource.getDownFace().getText().isEmpty())
			buttonTarget.getDownFace().setText(buttonSource.getDownFace().getText());
		else if(!buttonSource.getDownFace().getHTML().isEmpty())
			buttonTarget.getDownFace().setHTML(buttonSource.getDownFace().getHTML());
		else
			buttonTarget.getDownFace().setImage(new Image(buttonSource.getImageDownSrc()));
		
		if(!buttonSource.getDownHoveringFace().getText().isEmpty())
			buttonTarget.getDownHoveringFace().setText(buttonSource.getDownHoveringFace().getText());
		else if(!buttonSource.getDownHoveringFace().getHTML().isEmpty())
			buttonTarget.getDownHoveringFace().setHTML(buttonSource.getDownHoveringFace().getHTML());
		else
			buttonTarget.getDownHoveringFace().setImage(new Image(buttonSource.getImageDownHoveringSrc()));
		
		if(!buttonSource.getDownDisabledFace().getText().isEmpty())
			buttonTarget.getDownDisabledFace().setText(buttonSource.getDownDisabledFace().getText());
		else if(!buttonSource.getDownDisabledFace().getHTML().isEmpty())
			buttonTarget.getDownDisabledFace().setHTML(buttonSource.getDownDisabledFace().getHTML());
		else
			buttonTarget.getDownDisabledFace().setImage(new Image(buttonSource.getImageDownDisabledSrc()));
		buttonTarget.setAllowHoldDown(buttonSource.isAllowHoldDown());
		buttonTarget.setDown(buttonSource.isDown());
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkPushButton button = (VkPushButton)widget;
		buffer.append(",upDisabledFace:{");
		if(button.getImageUpDisabledSrc() != null)
			buffer.append("image:'").append(button.getImageUpDisabledSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getUpDisabledFace().getHTML()).append("'}");
		buffer.append(",upFace:{");
		if(button.getImageUpSrc() != null)
			buffer.append("image:'").append(button.getImageUpSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getUpFace().getHTML()).append("'}");
		buffer.append(",upHoveringFace:{");
		if(button.getImageUpHoveringSrc() != null)
			buffer.append("image:'").append(button.getImageUpHoveringSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getUpHoveringFace().getHTML()).append("'}");
		buffer.append(",downDisabledFace:{");
		if(button.getImageDownDisabledSrc() != null)
			buffer.append("image:'").append(button.getImageDownDisabledSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getDownDisabledFace().getHTML()).append("'}");
		buffer.append(",downFace:{");
		if(button.getImageDownSrc() != null)
			buffer.append("image:'").append(button.getImageDownSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getDownFace().getHTML()).append("'}");
		buffer.append(",downHoveringFace:{");
		if(button.getImageDownHoveringSrc() != null)
			buffer.append("image:'").append(button.getImageDownHoveringSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getDownHoveringFace().getHTML()).append("'}");
		buffer.append(",allowHoldDown:").append(button.isAllowHoldDown());
		buffer.append(",isDown:").append(button.isDown());
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		addAttributes(jsonObj, parent);
		VkPushButton button = (VkPushButton)parent;
		JSONObject faceObj = jsonObj.get("upDisabledFace").isObject();
		if(faceObj.get("image") != null)
			button.getUpDisabledFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getUpDisabledFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("upFace").isObject();
		if(faceObj.get("image") != null)
			button.getUpFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getUpFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("upHoveringFace").isObject();
		if(faceObj.get("image") != null)
			button.getUpHoveringFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getUpHoveringFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("downDisabledFace").isObject();
		if(faceObj.get("image") != null)
			button.getDownDisabledFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getDownDisabledFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("downFace").isObject();
		if(faceObj.get("image") != null)
			button.getDownFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getDownFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("downHoveringFace").isObject();
		if(faceObj.get("image") != null)
			button.getDownHoveringFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getDownHoveringFace().setHTML(faceObj.get("html").isString().stringValue());
		button.setAllowHoldDown(jsonObj.get("allowHoldDown").isBoolean().booleanValue());
		button.setDown(jsonObj.get("isDown").isBoolean().booleanValue());
	}
}
