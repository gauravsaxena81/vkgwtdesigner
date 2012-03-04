package com.vk.gwt.designer.client.designer;

import java.util.Map;

import com.google.gwt.event.dom.client.DomEvent;

public interface IEventHelper {

	void executeEvent(String js, Map<String, String> eventproperties);

	void executeEvent(String js, DomEvent<?> event, boolean instantiateEventObject);

}
