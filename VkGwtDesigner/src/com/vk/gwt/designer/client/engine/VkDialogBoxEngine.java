package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.VkDialogBox;

public class VkDialogBoxEngine extends VkAbstractWidgetEngine<VkDialogBox> {
	private static final String ADD_CAPTION = "Add Caption";
	private static final String SET_AUTO_HIDE = "Set Auto Hide";
	private static final String SET_GLASS = "Set Glass";
	private static final String SET_GLASS_CLASS = "Set Glass Style Name";
	private static final String SET_MODAL = "Set Modal";
	private static final String SET_POSITION = "Save Panel Position";
	@Override
	public VkDialogBox getWidget() {
		VkDialogBox widget = new VkDialogBox();
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		final VkDialogBox widget = (VkDialogBox)invokingWidget;
		if(attributeName.equals(ADD_CAPTION))
		{
			final TextArea textArea = new TextArea();
			textArea.setSize("100px", "50px");
			textArea.setText(widget.getHTML());
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Provide the HTML of caption", textArea
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						widget.setGlassStyleName(textArea.getText());
					}
				});
		}
		else if(attributeName.equals(SET_AUTO_HIDE))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("100px");
			listBox.addItem("True", "true");
			listBox.addItem("False", "false");
			listBox.setSelectedIndex(widget.isAutoHideEnabled()? 0 : 1);
			VkDesignerUtil.getEngine().showAddListDialog("Should the dialog to auto-hide when clicked outside?", listBox
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						widget.setAutoHideEnabled(listBox.getSelectedIndex() == 0);
					}
				});
		}
		else if(attributeName.equals(SET_GLASS))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("100px");
			listBox.addItem("True", "true");
			listBox.addItem("False", "false");
			listBox.setSelectedIndex(widget.isGlassEnabled()? 0 : 1);
			VkDesignerUtil.getEngine().showAddListDialog("Should the background be blocked by glass pane?", listBox
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						widget.setGlassEnabled(listBox.getSelectedIndex() == 0);
					}
				});
		}
		else if(attributeName.equals(SET_GLASS_CLASS))
		{
			final TextBox textBox = new TextBox();
			textBox.setWidth("100px");
			textBox.setText(widget.getGlassStyleName());
			VkDesignerUtil.getEngine().showAddTextAttributeDialog("Provide the classname of glass", textBox
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						widget.setGlassStyleName(textBox.getText());
					}
				});
		}
		else if(attributeName.equals(SET_MODAL))
		{
			final ListBox listBox = new ListBox();
			listBox.setWidth("100px");
			listBox.addItem("True", "true");
			listBox.addItem("False", "false");
			listBox.setSelectedIndex(widget.isModal()? 0 : 1);
			VkDesignerUtil.getEngine().showAddListDialog("Should the popup be modal?", listBox
				, new IEventRegister() {
					
					@Override
					public void registerEvent(String js) {
						widget.setModal(listBox.getSelectedIndex() == 0);
					}
				});
		}
		else if(attributeName.equals(SET_POSITION))
		{
			widget.setPopupPosition(widget.getAbsoluteLeft(), widget.getAbsoluteTop());
			Window.alert("Position of Panel is saved. Panel will appear at this position when it shows.");
		}
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> list = new ArrayList<String>();
		list.add(SET_AUTO_HIDE);
		list.add(SET_GLASS);
		list.add(SET_GLASS_CLASS);
		list.add(SET_MODAL);
		list.add(SET_POSITION);
		list.addAll(VkDesignerUtil.getEngine().getAttributesList(invokingWidget));
		return list;
	}
}
