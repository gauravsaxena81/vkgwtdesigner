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
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkDeckPanelEngine extends VkAbstractWidgetEngine<VkDeckPanel>{
	@Override
	public VkDeckPanel getWidget() {
		VkDeckPanel widget = new VkDeckPanel();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals("Switch Deck"))//cast is safe because this attribute is exposed only by Deck Panel
			showProvideWidgetIndex(new TextBox(), (VkDeckPanel) invokingWidget);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> optionList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		optionList.add("Switch Deck");
		return optionList;
	}
	private void showProvideWidgetIndex(final TextBoxBase addTextTb, final VkDeckPanel invokingWidget) {
		final VerticalPanel dialog = new VerticalPanel();
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		dialog.setStyleName("VkDesigner-dialog");
		
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTb.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Please add Text below:"));
		dialog.add(addTextTb);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("OK");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				try{
					invokingWidget.showWidget(Integer.parseInt(addTextTb.getText()));
				}catch(NumberFormatException e)
				{
					Window.alert("Deck number needs to be a positive number or 0");
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
