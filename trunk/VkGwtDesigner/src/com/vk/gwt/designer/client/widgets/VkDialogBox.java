package com.vk.gwt.designer.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkDialogBox extends DialogBox implements IPanel, HasVkCloseHandler, HasVkCaptionText, HasVkCaptionHtml, HasVkAutoHide, HasVkGlass
, HasVkGlassStyle, HasVkModal{
	public static final String NAME = "Dialog Box(added to Page)";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	public VkDialogBox()
	{
		setText("Untitled");
		setModal(false);
	}
	@Override
	public void add(Widget w) {
		if(getWidget() != null)
			Window.alert("DialogBox can add only one widget");
		else
			super.add(w);
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeRegistration != null)
			closeRegistration.removeHandler();
		closeJs = js;
		closeRegistration = addCloseHandler(new CloseHandler<PopupPanel>() {
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("autoClosed",Boolean.toString(event.isAutoClosed()));
				VkDesignerUtil.executeEvent(closeJs, map);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkCloseHandler.NAME))
			return closeJs;
		else
			return "";
	}
	@Override
	public void setHeight(String height)
	{
		Window.alert("Please resize the contained widget to resize the dialogbox");
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void setWidth(String width){}
	@Override
	public void clone(Widget targetWidget) {}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void setCaptionText(String text)
	{
		super.setText(text);
	}
	@Override
	@Export
	public String getCaptionText()
	{
		return super.getText();
	}
	@Override
	@Export
	public void setCaptionHtml(String html)
	{
		super.setHTML(html);
	}
	@Override
	@Export
	public String getCaptionHtml()
	{
		return super.getHTML();
	}
	@Override
	@Export
	public void center()
	{
		RootPanel.get().remove(this);
		super.center();
		DOM.setStyleAttribute(getElement(), "display", "");
	}
	@Override
	@Export
	public void hide() {
		super.hide();
		RootPanel.get().add(this);
		DOM.setStyleAttribute(getElement(), "display", "none");
	}
	@Override
	@Export
	public boolean isShowing() {
		return super.isShowing();
	}
	@Override
	@Export
	public void setPopupPosition(int left, int top) {
		super.setPopupPosition(left, top);
	}
	@Override
	@Export
	public void show()
	{
		RootPanel.get().remove(this);
		super.show();
		DOM.setStyleAttribute(getElement(), "display", "");
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
