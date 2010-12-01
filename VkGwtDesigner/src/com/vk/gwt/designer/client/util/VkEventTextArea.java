package com.vk.gwt.designer.client.util;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.TextArea;

public class VkEventTextArea extends TextArea {
	{
		addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				DOM.setCapture(getElement());
			}
		});
		addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				DOM.releaseCapture(getElement());
			}
		});
	}
	@Override
	public void onBrowserEvent(Event event)
	{
		super.onBrowserEvent(event);
		if(DOM.eventGetType(event) == Event.ONMOUSEDOWN)
		{
			String id = getRealElementId(Element.as(event.getEventTarget()));
			if(!id.isEmpty())
			{
				setText(getText().substring(0, getCursorPos()) + "&(" +  id + ")" + getText().substring(getCursorPos(), getText().length()));
				setFocus(true);
			}
		}
	}
	private String getRealElementId(com.google.gwt.dom.client.Element element) 
	{
		/*if(element.getTagName().equals("TD") && element.getId().isEmpty())
			return element.getParentElement().getParentElement().getParentElement().getId();//table
		else*/
		com.google.gwt.dom.client.Element currentElement = element;
		while(currentElement.getId().isEmpty())
			currentElement = currentElement.getParentElement();
		if(!currentElement.getId().equals("drawingPanel"))
			return currentElement.getId();
		else
			return "";
	}
}
