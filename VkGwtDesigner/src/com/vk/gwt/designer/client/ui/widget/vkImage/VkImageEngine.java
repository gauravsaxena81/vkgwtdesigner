package com.vk.gwt.designer.client.ui.widget.vkImage;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.designer.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkImageEngine extends VkAbstractWidgetEngine<VkImage> {
	private static final String UPLOAD_IMAGE = "Upload Image";
	private static final String SC_LENGTH_REQUIRED = "411";
	private static final String SC_BAD_REQUEST = "400";
	@Override
	public VkImage getWidget() {
		VkImage widget = new VkImage();
		init(widget);
		return widget;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(UPLOAD_IMAGE))
			createUploadImageDialog((VkImage)invokingWidget);
		else
			VkStateHelper.getInstance().getEngine().applyAttribute(attributeName, invokingWidget);
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget) {
		List<String> attributesList = VkStateHelper.getInstance().getEngine().getAttributesList(invokingWidget);
		attributesList.add(4, UPLOAD_IMAGE);
		return attributesList;
	}
	private void createUploadImageDialog(final VkImage image) {
		final DialogBox origDialog = new DialogBox();
		final FormPanel fp = new FormPanel();
		fp.setAction("./vkgwtdesigner/vksync");
		fp.setMethod(FormPanel.METHOD_POST);
		fp.setEncoding(FormPanel.ENCODING_MULTIPART);
		fp.addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				String results = event.getResults();
				if(results.equals(SC_LENGTH_REQUIRED))
					Window.alert("File size was greater than 1MB");
				else if (results.equals(SC_BAD_REQUEST)) {
					Window.alert("File Upload failed");
					origDialog.hide();
				} else {
					image.setUrl(results);
					origDialog.hide();
				}
			}
		});
		origDialog.setWidget(fp);
		final VerticalPanel dialog = new VerticalPanel();
		fp.setWidget(dialog);
		origDialog.setText("Upload Image");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		FileUpload fu = new FileUpload();
		fu.setName("file");
		dialog.add(fu);
		dialog.add(new Hidden("op", "imgUpload"));
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Submit");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				fp.submit();
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
}
