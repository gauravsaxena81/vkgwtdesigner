package com.vk.gwt.designer.client.ui.panel.vkDisclosurePanel;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkOpenHandler;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDisclosurePanel extends Composite implements IVkPanel, HasVkWidgets, HasVkAnimation, HasVkCloseHandler, HasVkOpenHandler{
	public static final String NAME = "Disclosure Panel";
	private DisclosurePanel dp;
	private HandlerRegistration closeHandlerRegistration;
	private HandlerRegistration openHandlerRegistration;
	private String closeJs = "";
	private String openJs = "";
	private IVkWidget vkParent;
	
	public VkDisclosurePanel(){
		dp = new DisclosurePanel();
		initWidget(dp);
		setPixelSize(100, 100);
	}
	
	public void add(Widget w)
	{
		if(dp.getHeader() == null)
		{
			dp.setHeader(w);
			w.setWidth(VkDesignerUtil.getPixelValue(dp.getElement(), "width") + "px");
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as header of Disclosure Panel");
			if(w instanceof IVkWidget)
				((IVkWidget)w).setVkParent(this);
		}
		else if(dp.getContent() == null)
		{
			dp.add(w);
			w.setWidth(VkDesignerUtil.getPixelValue(dp.getElement(), "width") + "px");
			DOM.setStyleAttribute(w.getElement(), "margin", "0px");
			if(VkStateHelper.getInstance().isDesignerMode())
				Window.alert("Widget added as content of Disclosure Panel");
			if(w instanceof IVkWidget)
				((IVkWidget)w).setVkParent(this);
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
		return list.iterator();
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeHandlerRegistration != null)
			closeHandlerRegistration.removeHandler();
		closeHandlerRegistration = null;
		closeJs = js.trim();
		if(!closeJs.isEmpty())
		{
			closeHandlerRegistration = dp.addCloseHandler(new CloseHandler<DisclosurePanel>() {
				@Override
				public void onClose(CloseEvent<DisclosurePanel> event) {
					EventHelper.getInstance().executeEvent(closeJs, (Map<String, String>)null);
				}
			});
		}
	}

	@Override
	public void addOpenHandler(String js) {
		if(openHandlerRegistration != null)
			openHandlerRegistration.removeHandler();
		openHandlerRegistration = null;
		openJs = js.trim();
		if(!openJs.isEmpty())
		{
			openHandlerRegistration = dp.addOpenHandler(new OpenHandler<DisclosurePanel>() {
				@Override
				public void onOpen(OpenEvent<DisclosurePanel> event) {
					EventHelper.getInstance().executeEvent(openJs, (Map<String, String>)null);
				}
			});
		}
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
	@Override
	public void clone(Widget targetWidget) {
		((VkDisclosurePanel)targetWidget).setOpen(isOpen());
	}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public void setWidth(String width){
		dp.setWidth(width);
		int widthValue = VkDesignerUtil.getPixelValue(dp.getElement(), "width");
		if(dp.getHeader() != null)
			dp.getHeader().setWidth(widthValue + "px");
		if(dp.getContent() != null)
			dp.getContent().setWidth(widthValue + "px");
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

	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
