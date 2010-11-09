package com.vk.gwt.designer.client.Panels;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.generator.client.Export;

public class VkDisclosurePanel extends VerticalPanel implements IPanel, HasVkWidgets, HasVkAnimation{
	public static final String NAME = "Disclosure Panel";
	DisclosurePanel dp;
	
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
			Window.alert("Widget added as header of Disclosure Panel");
		}
		else if(dp.getContent() == null)
		{
			dp.add(w);
			Window.alert("Widget added as content of Disclosure Panel");
		}
		else
			Window.alert("Disclosure Panel can add only one widget");
	}
	@Override
	public void addAnimation(boolean enabled) {
		setAnimationEnabled(enabled);
	}
	/**************************Export attribute Methods********************************/
	@Export
	public String getHeaderText()
	{
		return dp.getHeaderTextAccessor().getText();
	}
	@Export
	public void setAnimationEnabled(boolean enabled)
	{
		dp.setAnimationEnabled(enabled);
	}
	@Export
	public boolean isAnimationEnabled()
	{
		return dp.isAnimationEnabled();
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
}
