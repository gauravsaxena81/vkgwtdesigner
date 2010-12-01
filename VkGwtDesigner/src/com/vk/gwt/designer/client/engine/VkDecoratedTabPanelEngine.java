package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;

public class VkDecoratedTabPanelEngine extends VkAbstractWidgetEngine<VkDecoratedTabPanel> {
	private final String ADD_TEXT = "Add Current Tab Header Text";
	private final String ADD_HTML = "Add Current Tab Header HTML";
	private final String ADD_ENABLED = "Set Current Tab Enabled";
	private final String MAKE_ENABLE = "Enable a Tab";
	@Override
	public VkDecoratedTabPanel getWidget() {
		VkDecoratedTabPanel widget = new VkDecoratedTabPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.remove(HasVkText.NAME);
		optionList.remove(HasVkHtml.NAME);
		optionList.add(ADD_TEXT);
		optionList.add(ADD_HTML);
		optionList.add(ADD_ENABLED);
		optionList.add(MAKE_ENABLE);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkTabPanel widget = (VkTabPanel)invokingWidget;
		if(attributeName.equals(ADD_TEXT))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				attributeName = HasVkText.NAME; //Vk Engine needs exact attribute text to apply attribute
		}
		else if(attributeName.equals(ADD_HTML))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				attributeName = HasVkHtml.NAME;
		}
		else if(attributeName.equals(ADD_ENABLED))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				attributeName = HasVkEnabled.NAME;
		}
		else if(attributeName.equals(MAKE_ENABLE))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
			{
				VkDesignerUtil.getEngine().showAddTextAttributeDialog("Add Tab number to enable", new TextBox()
				, new IEventRegister() {
					@Override
					public void registerEvent(String text) {
						try{
							int index = Integer.parseInt(text);
							if(index >= widget.getTabCount())
								Window.alert("tab number should be less than maximum number of tabs");
							else
								widget.setTabEnabled(Integer.parseInt(text), true);
						}catch(NumberFormatException e)
						{
							Window.alert("Tab number cannot be non-numeric");
						}
					}
				});
			}
		}
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
}
