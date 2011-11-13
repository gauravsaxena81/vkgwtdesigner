package com.vk.gwt.designer.client.ui.widget.button.vkToggleButton;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.designer.VkStateHelper;

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
			tb.setValue(widget.getImageUpSrc());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up image url", tb
					, new IDialogCallback() {
						@Override
						public void save(String src) {
							widget.getUpFace().setImage(new Image(src));
							widget.setImageUpSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_IMAGE))
		{
			TextBox tb = new TextBox();
			tb.setValue(widget.getImageDownSrc());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down image url", tb
					, new IDialogCallback() {
						@Override
						public void save(String src) {
							widget.getDownFace().setImage(new Image(src));
							widget.setImageDownSrc(src);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_HTML))
		{
			TextArea ta = new TextArea();
			ta.setValue(widget.getUpFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up html", ta
					, new IDialogCallback() {
						@Override
						public void save(String html) {
							widget.getUpFace().setHTML(html);
							widget.setImageUpSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_HTML))
		{
			TextArea ta = new TextArea();
			ta.setValue(widget.getDownFace().getHTML());
			ta.setSize("300px", "100px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down html", ta
					, new IDialogCallback() {
						@Override
						public void save(String html) {
							widget.getDownFace().setHTML(html);
							widget.setImageDownSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_UP_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setValue(widget.getUpFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide up text", tb
					, new IDialogCallback() {
						@Override
						public void save(String text) {
							widget.getUpFace().setText(text);
							widget.setImageUpSrc(null);
						}
					});
		}
		else if(attributeName.equals(ADD_DOWN_TEXT))
		{
			TextBox tb = new TextBox();
			tb.setValue(widget.getDownFace().getText());
			tb.setWidth("300px");
			VkDesignerUtil.showAddTextAttributeDialog("Please provide down text", tb
					, new IDialogCallback() {
						@Override
						public void save(String text) {
							widget.getDownFace().setText(text);
							widget.setImageDownSrc(null);
						}
					});
		}
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
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
		list.addAll(VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget));
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
			buttonTarget.getUpFace().setImage(new Image(buttonSource.getImageUpSrc()));
		if(!buttonSource.getDownFace().getText().isEmpty())
			buttonTarget.getDownFace().setText(buttonSource.getDownFace().getText());
		else if(!buttonSource.getDownFace().getHTML().isEmpty())
			buttonTarget.getDownFace().setHTML(buttonSource.getDownFace().getHTML());
		else
			buttonTarget.getDownFace().setImage(new Image(buttonSource.getImageDownSrc()));
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		VkToggleButton button = (VkToggleButton)widget;
		buffer.append(",upFace:{");
		if(button.getImageUpSrc() != null)
			buffer.append("image:'").append(button.getImageUpSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getUpFace().getHTML()).append("'}");
		buffer.append(",downFace:{");
		if(button.getImageDownSrc() != null)
			buffer.append("image:'").append(button.getImageDownSrc()).append("'}");
		else
			buffer.append("html:'").append(button.getDownFace().getHTML()).append("'}");
		buffer.append(",children:[").append("]}");
		return buffer.toString();
	}
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		VkToggleButton button = (VkToggleButton)parent;
		JSONObject faceObj = jsonObj.get("upFace").isObject();
		if(faceObj.get("image") != null)
			button.getUpFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getUpFace().setHTML(faceObj.get("html").isString().stringValue());
		faceObj = jsonObj.get("downFace").isObject();
		if(faceObj.get("image") != null)
			button.getDownFace().setImage(new Image(faceObj.get("image").isString().stringValue()));
		else
			button.getDownFace().setHTML(faceObj.get("html").isString().stringValue());
	}
}

