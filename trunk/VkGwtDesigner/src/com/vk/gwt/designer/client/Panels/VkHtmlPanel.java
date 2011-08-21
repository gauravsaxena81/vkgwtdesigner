package com.vk.gwt.designer.client.Panels;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkHtmlPanel extends HTMLPanel implements HasVkWidgets, IPanel, HasVkHtml {
	public static final String NAME = "Html Panel";
	private String id;
	public VkHtmlPanel() {
		super("<div id='html1' style='width: 100%; height: 100%'></div>");
		showAddHTMLDialog(this);
		id = hashCode() + "";
		super.getElement().getFirstChildElement().setId(id);
	}
	public void add(Widget widget)
	{
		if(VkDesignerUtil.isDesignerMode)
			showAddTextAttributeDialog(widget);	
	}
	private void showAddHTMLDialog(final VkHtmlPanel vkHtmlPanel) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Html Panel");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		final TextArea addTextTa = new TextArea();
		dialog.add(addTextTa);
		addTextTa.setText(vkHtmlPanel.getElement().getInnerHTML());
		addTextTa.setSize("300px", "100px");
		new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		}.schedule(100);
		addTextTa.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				vkHtmlPanel.getElement().setInnerHTML(addTextTa.getText());
				origDialog.hide();
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
			}
		});
		origDialog.center();
	}
	private void showAddTextAttributeDialog(final Widget widget) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Please provide id of element in which widget will be contained");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("300px");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTb.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("(Leave the textbox blank, if adding to parent panel)"));
		dialog.add(addTextTb);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String id;
				if(!addTextTb.getText().trim().isEmpty())
					id = addTextTb.getText();
				else
					id = VkHtmlPanel.this.id;
				Element elem = getElementById(id);
				if (elem != null) {
					VkHtmlPanel.super.add(widget, id);
					origDialog.hide();
				} else
					Window.alert("Element with the id doesn't exist");
			}
		});
		Button cancelButton = new Button("Cancel");
		buttonsPanel.add(cancelButton);
		cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
			}
		});
		origDialog.center();
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
	public void setHTML(String html) {
		super.getElement().setInnerHTML(html);
	}
	@Override
	public String getHTML() {
		return super.getElement().getInnerHTML();
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
	public int getWidgetCount()
	{
		return super.getWidgetCount();
	}
	@Override
	@Export
	public boolean remove(int index)
	{
		return super.remove(index);
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
