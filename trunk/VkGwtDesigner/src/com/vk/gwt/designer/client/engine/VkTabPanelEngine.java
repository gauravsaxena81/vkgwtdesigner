package com.vk.gwt.designer.client.engine;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkTabPanelEngine extends VkAbstractWidgetEngine<VkTabPanel> {
	private final String ADD_TEXT = "Add Current Tab Header Text";
	private final String ADD_HTML = "Add Current Tab Header HTML";
	private final String ADD_ENABLED = "Set Current Tab Enabled";
	private final String MAKE_ENABLE = "Enable a Tab";
	@Override
	public VkTabPanel getWidget() {
		VkTabPanel widget = new VkTabPanel();
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
		if(attributeName.equals(ADD_TEXT))
			attributeName = HasVkText.NAME; //Vk Engine needs exact attribute text to apply attribute
		else if(attributeName.equals(ADD_HTML))
			attributeName = HasVkHtml.NAME;
		else if(attributeName.equals(ADD_ENABLED))
			attributeName = HasVkEnabled.NAME;
		else if(attributeName.equals(MAKE_ENABLE))
			showAddTextAttributeDialog((VkTabPanel) invokingWidget);
		VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void showAddTextAttributeDialog(final VkTabPanel invokingWidget) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		final TextBox addTextTa = new TextBox();
		dialog.add(addTextTa);
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Add Tab number to enable"));		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try{
					invokingWidget.setTabEnabled(Integer.parseInt(addTextTa.getText()), true);
				}catch(NumberFormatException e)
				{
					Window.alert("Tab number cannot be non-numeric");
				}
				dialog.removeFromParent();
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				dialog.removeFromParent();
			}
		});
	}
}
