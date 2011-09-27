package com.vk.gwt.designer.client.designer;

import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public class VkDesignerUtil {
	public interface IEventRegister{
		public void registerEvent(String js);
	}
	public static native void setCapture(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		@com.google.gwt.user.client.DOM::setCapture(Lcom/google/gwt/user/client/Element;)(elem);
		if($doc.all)//IE 8: need to test to confirm if required
			elem.setCapture(true);
	}-*/;
	public static native void releaseCapture(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		@com.google.gwt.user.client.DOM::releaseCapture(Lcom/google/gwt/user/client/Element;)(elem);
		if($doc.all)//IE 8: need to test to confirm if required
			elem.setCapture(false);
	}-*/;
	
	public static void centerDialog(Panel dialog) {
		dialog.setVisible(true);
		DOM.setStyleAttribute(dialog.getElement(), "top", (VkMainDrawingPanel.getInstance().getOffsetHeight() / 2 - dialog.getOffsetHeight() / 2) +  "px");
		DOM.setStyleAttribute(dialog.getElement(), "left", (VkMainDrawingPanel.getInstance().getOffsetWidth() / 2 - dialog.getOffsetWidth() / 2) +  "px");
	}
	public static native String getComputedStyleAttribute(Element elem, String attr) /*-{
	    if (document.defaultView && document.defaultView.getComputedStyle) { // W3C DOM method
	      var value = null;
	      if (attr == 'float') { // fix reserved word
	        attr = 'cssFloat';
	      }
	      var computed = elem.ownerDocument.defaultView.getComputedStyle(elem, '');
	      if (computed) { // test computed before touching for safari
	        value = computed[attr];
	      }
	      return (elem.style[attr] || value || '');
	    } else if(elem.currentStyle){//IE
	    	return (elem.currentStyle[attr] || '');
	    }
	    else { // default to inline only
	      return (elem.style[attr] || '');
	    }
	}-*/;
	public static void loadApplication(String text) {
		VkStateHelper.getInstance().getEngineMap().get(VkMainDrawingPanel.NAME).deserialize((IVkWidget) VkMainDrawingPanel.getInstance(), text);
		EventHelper.getInstance().executeEvent(VkMainDrawingPanel.getInstance().getPriorJs(HasVkLoadHandler.NAME), (Map<String, String>)null);
	}
	public native static void setLoadString(String str) /*-{
		$wnd.loadStr = str;
	}-*/;
	/**
	 * @param w
	 * @param prop css style property name e.g. border-top-width
	 * @return
	 */
	public static native int getPixelValue(Element w, String prop)/*-{
		var ret;
		//var element = w.@com.google.gwt.user.client.ui.Widget::getElement()();
		var element = w;
		if(document.all){//IE
			var propertyArray = prop.split('-');
			var property = propertyArray[0];
			for(var i = 1, len = propertyArray.length; i < len; i++)
				property += propertyArray[i].substr(0, 1).toUpperCase() + propertyArray[i].substr(1);
			var temp = element.style.left;
			element.style.left = element.style[property];
			ret = element.style.pixelLeft;
			element.style.left = temp;
		} else
			ret = $doc.defaultView.getComputedStyle(element, null).getPropertyValue(prop);
		try{
			return parseInt(ret);
		} catch(e){
			return -1;
		}
	}-*/;
	public native static String getCssText(Widget widget) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		return elem.style.cssText;
	}-*/;
	public static double getDecorationsWidth(Element elem) {
		if(elem.getTagName().equalsIgnoreCase("button"))
			return 0;
		int width = VkDesignerUtil.getPixelValue(elem, "border-left-width");
		width += VkDesignerUtil.getPixelValue(elem, "border-right-width");
		width += VkDesignerUtil.getPixelValue(elem, "padding-left");
		width += VkDesignerUtil.getPixelValue(elem, "padding-right");
		/*width += VkDesignerUtil.getPixelValue(widget, "margin-left");
		width += VkDesignerUtil.getPixelValue(widget, "margin-right");*/
		return width;
	}
	public static double getDecorationsHeight(Element elem) {
		if(elem.getTagName().equalsIgnoreCase("button"))
			return 0;
		int height = VkDesignerUtil.getPixelValue(elem, "border-top-width");
		height += VkDesignerUtil.getPixelValue(elem, "border-bottom-width");
		height += VkDesignerUtil.getPixelValue(elem, "padding-bottom");
		height += VkDesignerUtil.getPixelValue(elem, "padding-top");
		/*height += VkDesignerUtil.getPixelValue(widget, "margin-bottom");
		height += VkDesignerUtil.getPixelValue(widget, "margin-top");*/
		return height;
	}
	public static void showAddListDialog(String heading, final ListBox listBox, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		
		new Timer(){
			@Override
			public void run() {
				//VkDesignerUtil.centerDialog(dialog);
				listBox.setFocus(true);
			}
		}.schedule(100);
		listBox.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(listBox.getValue(listBox.getSelectedIndex()));
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
	public static void showAddTextAttributeDialog(String heading, final TextBoxBase addTextTa, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		FocusPanel fp = new FocusPanel();
		origDialog.add(fp);
		fp.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ESCAPE)
					origDialog.hide();
			}
		});
		final VerticalPanel dialog = new VerticalPanel();
		fp.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		new Timer(){
			@Override
			public void run() {
				//VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		}.schedule(100);
		addTextTa.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(addTextTa);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(addTextTa.getText());
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
	public static void showEventRegistrationDialog(HasVkEventHandler invokingWidget, String eventName, final IEventRegister iEventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Please add JS code below:");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		final VkEventTextArea addTextTa = new VkEventTextArea(); 
		new Timer(){
			@Override
			public void run() {
				//VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		}.schedule(100);
		dialog.add(addTextTa);
		addTextTa.setPixelSize(500, 200);
		addTextTa.setText(invokingWidget.getPriorJs(eventName));
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iEventRegister.registerEvent(addTextTa.getText());
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
		origDialog.setPopupPosition(origDialog.getPopupLeft() + 1, origDialog.getPopupTop());//for IE 9, the textarea otherwise doesn't take id of other widgets when clicked on
	}
	public static void showAddAutoCompleteTextDialog(String heading, final AutoCompleterTextBox targetTb, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		new Timer(){
			@Override
			public void run() {
				targetTb.setFocus(true);
			}
		}.schedule(100);
		targetTb.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		dialog.add(targetTb);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Ok");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(targetTb.getText());
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
	public static native void clearSelection() /*-{
		if($doc.selection)
			$doc.selection.empty();
		else
			$wnd.getSelection().removeAllRanges();
	}-*/;
	public static native void setCssText(Widget widget, String cssText) /*-{
		var elem = widget.@com.google.gwt.user.client.ui.Widget::getElement()();
		elem.style.cssText = cssText;
	}-*/;
	public static void fillParent(Widget widget) {
		Widget parent = widget.getParent();
		if(widget instanceof Button)
			widget.setPixelSize(parent.getOffsetWidth(), parent.getOffsetHeight());
		else
			widget.setPixelSize(parent.getOffsetWidth() -(int)getDecorationsWidth(parent.getElement()) - (int)getDecorationsWidth(widget.getElement())
			, parent.getOffsetHeight() - (int)getDecorationsHeight(parent.getElement()) - (int)getDecorationsHeight(widget.getElement()));
		if(DOM.getStyleAttribute(parent.getElement(), "position").isEmpty())
			DOM.setStyleAttribute(parent.getElement(), "position","static");
		DOM.setStyleAttribute(widget.getElement(), "top", "0px");
		DOM.setStyleAttribute(widget.getElement(), "left", "0px");
	}
}