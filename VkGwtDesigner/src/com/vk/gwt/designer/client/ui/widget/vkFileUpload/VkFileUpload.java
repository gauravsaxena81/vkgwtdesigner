package com.vk.gwt.designer.client.ui.widget.vkFileUpload;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkChangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkFileUpload extends FileUpload implements IVkWidget, HasVkName, HasVkChangeHandler, HasVkEnabled{
	public static final String NAME = "File Upload";
	private HandlerRegistration changeHandlerRegistration;
	private String changeJs;
	private boolean isEnabled = true;
	@Override
	public void addChangeHandler(String js) {
		if(changeHandlerRegistration != null)
			changeHandlerRegistration.removeHandler();
		changeHandlerRegistration = null;
		changeJs  = js.trim();
		if(!changeJs.isEmpty())
		{
			changeHandlerRegistration = addChangeHandler(new ChangeHandler() {
				@Override
				public void onChange(ChangeEvent event) {
					VkDesignerUtil.executeEvent(changeJs, event, true);
				}
			});
		}
	}

	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkChangeHandler.NAME))
			return changeJs;
		else 
			return "";
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void clone(Widget targetWidget) {}
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
	public String getFilename() {
		return super.getFilename();
	}
	@Override
	@Export
	public String getName() {
		return super.getName();
	}
	@Override
	@Export
	public void setName(String name) {
		super.setName(name);
	}
	@Override
	@Export
	public void setEnabled(boolean enabled)
	{
		if(!VkDesignerUtil.isDesignerMode)
			super.setEnabled(enabled);
		else if(!enabled)
			Window.alert("Widget has been disabled and will appear so in preview \n but in designer mode i.e. now, it will appear enabled ");
		isEnabled  = enabled;
	}
	@Override
	@Export
	public boolean isEnabled()
	{
		return isEnabled;
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
