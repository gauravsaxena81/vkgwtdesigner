package com.vk.gwt.designer.client.widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoOpen;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;

public class VkMenuBarHorizontal extends MenuBar implements IVkWidget, HasVkCloseHandler, HasVkAnimation, HasVkAutoOpen{
	public static final String NAME = "Menu Bar Horizontal";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	private List<String> commandJs = new ArrayList<String>();
	private List<Integer> seperatorIndices = new ArrayList<Integer>();
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
	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		switch (DOM.eventGetType(event)) {
	      	case Event.ONCLICK:{
	      		//submenus are always VkMenuBarVertical type
	    	  	VkMenuBarVertical menu = ((VkMenuBarVertical)getSelectedItem().getSubMenu());
	    	  	if(menu != null)
	    	  	{
		    	  	menu.setTop(menu.getAbsoluteTop());
		    	  	menu.setLeft(menu.getAbsoluteLeft());
	    	  	}
	      	}
		}
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	//making the method public for serialization and deserialization
	@Override
	public List<MenuItem> getItems() {
	    return super.getItems();
	}
	public List<String> getCommandJs() {
		return commandJs;
	}
	public List<Integer> getSeperatorIndices() {
		return seperatorIndices;
	}
	@Override
	public void clone(Widget targetWidget) {
		((VkMenuBarHorizontal)targetWidget).commandJs = new ArrayList<String>();
		((VkMenuBarHorizontal)targetWidget).commandJs.addAll(commandJs);
		((VkMenuBarHorizontal)targetWidget).seperatorIndices = new ArrayList<Integer>();
		((VkMenuBarHorizontal)targetWidget).seperatorIndices.addAll(seperatorIndices);
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
