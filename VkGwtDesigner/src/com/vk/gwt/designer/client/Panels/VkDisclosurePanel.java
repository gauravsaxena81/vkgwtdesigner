package com.vk.gwt.designer.client.Panels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkOpenHandler;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkDisclosurePanel extends VerticalPanel implements IPanel, HasVkWidgets, HasVkAnimation, HasVkCloseHandler, HasVkOpenHandler{
	public static final String NAME = "Disclosure Panel";
	private DisclosurePanel dp;
	private HandlerRegistration closeHandlerRegistration;
	private HandlerRegistration openHandlerRegistration;
	private String closeJs = "";
	private String openJs = "";
	
	public VkDisclosurePanel(){
		dp = new DisclosurePanel();
		VkDisclosurePanel.super.add(dp);
		setPixelSize(100, 100);
	}
	
	public void add(Widget w)
	{
		if(dp.getHeader() == null)
		{
			dp.setHeader(w);
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as header of Disclosure Panel");
		}
		else if(dp.getContent() == null)
		{
			dp.add(w);
			DOM.setStyleAttribute(w.getElement(), "margin", "0px");
			if(VkDesignerUtil.isDesignerMode)
				Window.alert("Widget added as content of Disclosure Panel");
		}
		else
			Window.alert("Disclosure Panel can add only two widgets");
	}
	@Override
	public Iterator<Widget> iterator() 
	{
		List<Widget> list = new ArrayList<Widget>();
		if(dp.getHeader() != null)
			list.add(dp.getHeader());
		if(dp.getContent() != null)
			list.add(dp.getContent());
		if(list.size() == 0)
			return super.iterator();//when widget is initialized, panel housekeeping needs the iterator of VerticalPanel
		else
			return list.iterator();//used by the serializer
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeHandlerRegistration != null)
			closeHandlerRegistration.removeHandler();
		closeJs = js;
		closeHandlerRegistration = dp.addCloseHandler(new CloseHandler<DisclosurePanel>() {
			@Override
			public void onClose(CloseEvent<DisclosurePanel> event) {
				VkDesignerUtil.executeEvent(closeJs, (Map<String, String>)null);
			}
		});
	}

	@Override
	public void addOpenHandler(String js) {
		if(openHandlerRegistration != null)
			openHandlerRegistration.removeHandler();
		openJs = js;
		openHandlerRegistration = dp.addOpenHandler(new OpenHandler<DisclosurePanel>() {
			@Override
			public void onOpen(OpenEvent<DisclosurePanel> event) {
				VkDesignerUtil.executeEvent(openJs, (Map<String, String>)null);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkCloseHandler.NAME))
			return closeJs;
		else if(eventName.equals(HasVkOpenHandler.NAME))
			return openJs;
		else
			return "";
	}
	public void setAnimationEnabled(boolean enabled)
	{
		dp.setAnimationEnabled(enabled);
	}
	public boolean isAnimationEnabled()
	{
		return dp.isAnimationEnabled();
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	/**************************Export attribute Methods********************************/
	@Export
	public String getHeaderText()
	{
		return dp.getHeaderTextAccessor().getText();
	}
	@Export
	public void setOpen(boolean open)
	{
		dp.setOpen(open);
	}
	@Export
	public boolean isOpen()
	{
		return dp.isOpen();
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