package com.vk.gwt.designer.client.Panels;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollBarShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollHandler;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

public class VkScrollPanel extends ScrollPanel implements HasVkWidgets, IPanel, HasVkScrollHandler, HasVkScrollBarShowing {
	public static final String NAME = "Scroll Panel";
	private HandlerRegistration scrollHandlerRegistration;
	private String scrollJs = "";
	@Override
	public void add(Widget widget)
	{
		if(getWidget() == null)
			setWidget(widget);
		else
			Window.alert("Scroll Panel can add only one Widget");
	}
	@Override
	public void addScrollHandler(String js) {
		if(scrollHandlerRegistration != null)
			scrollHandlerRegistration.removeHandler();
		scrollJs  = js;
		scrollHandlerRegistration = addScrollHandler(new ScrollHandler() {
			@Override
			public void onScroll(ScrollEvent event) {
				VkDesignerUtil.executeEvent(scrollJs, event);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		return scrollJs;
	}
	@Override
	public void setAlwaysShowScrollBars(boolean alwaysShow) {
		super.setAlwaysShowScrollBars(alwaysShow);
	}
	@Override
	public boolean isAlwaysShowScrollBars() {
		return DOM.getStyleAttribute(getElement(), "overflow").equals("scroll");
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public int getHorizontalScrollPosition() 
	{
		return super.getHorizontalScrollPosition();
	}
	@Override
	@Export
	public int getScrollPosition() 
	{
		return super.getScrollPosition();
	}
	@Override
	@Export
	public void setHorizontalScrollPosition(int pos) 
	{
		super.setHorizontalScrollPosition(pos);
	}
	@Override
	@Export
	public void setScrollPosition(int pos) 
	{
		super.setScrollPosition(pos);
	}
	@Override
	@Export
	public void scrollToBottom()
	{
		super.scrollToBottom();
	}
	@Override
	@Export
	public void scrollToTop()
	{
		super.scrollToTop();
	}
	@Override
	@Export
	public void scrollToLeft()
	{
		super.scrollToLeft();
	}
	@Override
	@Export
	public void scrollToRight()
	{
		super.scrollToRight();
	}
}