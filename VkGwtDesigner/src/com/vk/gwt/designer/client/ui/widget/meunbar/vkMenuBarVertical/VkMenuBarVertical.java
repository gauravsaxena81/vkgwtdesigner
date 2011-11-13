package com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.ResizeHelper;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal.VkMenuBarHorizontal;

public class VkMenuBarVertical extends VkMenuBarHorizontal{
	public static final String NAME = "Menu Bar Vertical";
	
	public VkMenuBarVertical() {
		super(true);
	}
	@Override
	public void add(Widget widget) {
		addWidgetToCollection(widget);
		final MenuItem menuItem = new MenuItem("Widget", (Command) null){
			@Override
			public void setSelectionStyle(boolean selected)
			{
				super.setSelectionStyle(selected);
				if(VkMenuBarVertical.this.getAutoOpen() && getCommand() != null)
					getCommand().execute();
			}
		};
		this.addItem(menuItem);
		final PopupPanel popupPanel = new PopupPanel() {
			public void hide(boolean autoClosed) {
				if(autoClosed) {
					new Timer(){
						@Override
						public void run() {
							lateHide();
						}}.schedule(100);
				} else
					super.hide(autoClosed);
			}
			private void lateHide() {
				if(!ResizeHelper.getInstance().isResizing())
					super.hide(true);
			}
		};
		popupPanel.add(widget);
		if(widget instanceof IVkWidget)
			((IVkWidget)widget).setVkParent(this);
		popupPanel.setAutoHideEnabled(true);
		popupPanel.addAutoHidePartner(menuItem.getElement());
		menuItem.setCommand(new Command(){
			@Override
			public void execute() {
				if(popupPanel.isShowing())
					popupPanel.hide();
				else {
					popupPanel.showRelativeTo(menuItem);
					DOM.setStyleAttribute(popupPanel.getElement(), "left", VkDesignerUtil.getOffsetLeft(popupPanel.getElement()) + VkMenuBarVertical.this.getOffsetWidth() + "");
					DOM.setStyleAttribute(popupPanel.getElement(), "top",  menuItem.getAbsoluteTop() + "");
					popupPanel.show();
				}
			}});
		
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public boolean isResizable() {
		return true; 	
	}
	@Override
	public boolean isMovable() {
		if(getParent() instanceof MenuBar)
			return false;
		else
			return true;
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible() {
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className) {
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className) {
		super.removeStyleName(className);
	}
}
