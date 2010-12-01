package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkTabBar;

public class VkTabBarEngine extends VkAbstractWidgetEngine<VkTabBar> {
	private final String ADD_TAB = "Add a Tab";
	private final String REMOVE_TAB = "Remove Current Tab";
	private final String EDIT_TAB = "Edit Current Tab";
	private final String ADD_ENABLED = "Set Current Tab Enabled";
	private final String MAKE_ENABLE = "Enable a Tab";

	@Override
	public VkTabBar getWidget() {
		VkTabBar widget = new VkTabBar();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.add(ADD_TAB);
		optionList.add(EDIT_TAB);
		optionList.add(REMOVE_TAB);
		optionList.add(ADD_ENABLED);
		optionList.add(MAKE_ENABLE);
		return optionList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkTabBar widget = (VkTabBar)invokingWidget;
		if(attributeName.equals(ADD_TAB))
		{
			final TextArea ta = new TextArea();
			ta.setSize("100px", "50px");
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
				, new IEventRegister() {
				@Override
				public void registerEvent(String js) {
					widget.addTab(ta.getText(), true);
				}
			});
		}
		else if (attributeName.equals(EDIT_TAB))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
			{
				final TextArea ta = new TextArea();
				ta.setText(widget.getTabHTML(widget.getSelectedTab()));
				ta.setSize("100px", "50px");
				VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide HTML for tab name", ta
					, new IEventRegister() {
					@Override
					public void registerEvent(String js) {
						widget.setTabHTML(widget.getSelectedTab(), ta.getText());
					}
				});
			}
		}
		else if(attributeName.equals(REMOVE_TAB))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Select a tab before this operation");
			else
				widget.removeTab(widget.getSelectedTab());
		}
		else if(attributeName.equals(ADD_ENABLED))
		{
			if(widget.getSelectedTab() < 0)
				Window.alert("Please Select a tab before this operation");
			else
				attributeName = HasVkEnabled.NAME;
		}
		else if(attributeName.equals(MAKE_ENABLE))
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
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
}
