package com.vk.gwt.designer.client.widgets;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.widgets.HasVkMenuBarHorizontal;
import com.vk.gwt.designer.client.api.widgets.HasVkMenuBarVertical;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkMenuBarHorizontal extends MenuBar implements HasVkCloseHandler, HasVkAnimation, HasVkMenuBarHorizontal, HasVkMenuBarVertical{
	public static final String NAME = "Menu Bar Horizontal";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	public VkMenuBarHorizontal(){}
	public VkMenuBarHorizontal(boolean b) {
		super(b);
	}
	public MenuItem getMenuItem(int index)
	{
		return super.getItems().get(index);
	}
	public int getItemCount()
	{
		return super.getItems().size();
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
	/**************************Export attribute Methods********************************/
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
