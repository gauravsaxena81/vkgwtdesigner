package com.vk.gwt.designer.client.designer;

import java.util.Map;

import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.user.client.Element;

public class EventHelper {
	private static EventHelper eventHelper = new EventHelper();
	
	private EventHelper(){}
	
	public static EventHelper getInstance(){
		return eventHelper;
	}
	public void executeEvent(String js, Map<String, String> eventproperties) {
		if(eventproperties != null)
			prepareLocalEvent(eventproperties);
		if(!VkStateHelper.getInstance().isDesignerMode())
			runJs(formatJs(js));
	}
	public void executeEvent(String js, @SuppressWarnings("rawtypes") DomEvent event, boolean instantiateEventObject) {
		EventTarget currentEventTarget = event.getNativeEvent().getCurrentEventTarget();
		Element currentEventTargetElement = currentEventTarget != null && Element.is(currentEventTarget)? (Element) Element.as(currentEventTarget) : null;
		EventTarget eventTarget = event.getNativeEvent().getEventTarget();
		Element eventTargetElement = eventTarget != null && Element.is(eventTarget)
			? (Element) Element.as(eventTarget) : null;
		EventTarget relativeEventTarget = event.getNativeEvent().getRelatedEventTarget();
		Element relativeEventTargetElement = relativeEventTarget != null ? (Element) Element.as(relativeEventTarget) : null;
		//JSNI was not able to work with methods of NativeElement and kept on throwing errors which made no sense.
		//Have a look at the issue raised at http://stackoverflow.com/questions/4086392/jsni-cannot-find-a-method-in-nativeevent-class-of-gwt 
		prepareLocalEvent(event, instantiateEventObject, event.getNativeEvent().getAltKey(), event.getNativeEvent().getButton(), event.getNativeEvent().getClientX() 
			, event.getNativeEvent().getClientY(), event.getNativeEvent().getCtrlKey(), currentEventTargetElement, eventTargetElement
			, event.getNativeEvent().getKeyCode(), event.getNativeEvent().getCharCode(), event.getNativeEvent().getMetaKey(), event.getNativeEvent().getMouseWheelVelocityY()
			, relativeEventTargetElement, event.getNativeEvent().getScreenX(), event.getNativeEvent().getScreenY(),event.getNativeEvent().getShiftKey());
		if(!VkStateHelper.getInstance().isDesignerMode())
			runJs(formatJs(js));
	}
	private static native void runJs(String formatJs) /*-{
		//in javascript the variables are either function scope or window scope. Thus, to ensure any variables declared in the script are not declared in 
		//window scope, the script has been wrapped in a function.
		$wnd.eval("var vkgdTemp = function(){ " + formatJs + " }; vkgdTemp(); vkgdTemp = null;");
	}-*/;

	private native void prepareLocalEvent(Map<String, String> eventProperties) /*-{
		$wnd.vkEvent = {};
		var keySetIterator = eventProperties.@java.util.Map::keySet()().@java.util.Set::iterator()();
		while(keySetIterator.@java.util.Iterator::hasNext()())
		{
			var key = keySetIterator.@java.util.Iterator::next()();
			$wnd.vkEvent[key] = eventProperties.@java.util.Map::get(Ljava/lang/Object;)(key);
		}
	}-*/;
	private native void prepareLocalEvent(DomEvent<?> event, boolean instantiateEventObject, boolean alt
	, int buttonNum, int clientx, int clienty, boolean ctrl, Element currentEvtTarget, Element actualEvtTarget
	, int keyCode, int charCode, boolean meta, int mouseWheelVel, Element relativeEvtTarget, int screenx, int screeny
	, boolean shift) /*-{
		if(instantiateEventObject)
			$wnd.vkEvent = {};
		$wnd.vkEvent.altKey = alt;
		$wnd.vkEvent.button = buttonNum;
		$wnd.vkEvent.clientX = clientx;
		$wnd.vkEvent.clientY = clienty;
		$wnd.vkEvent.ctrlKey = ctrl;
		$wnd.vkEvent.currentEventTarget = currentEvtTarget;
		$wnd.vkEvent.eventTarget = actualEvtTarget;
		$wnd.vkEvent.keyCode = keyCode;
		$wnd.vkEvent.charCode = charCode;
		$wnd.vkEvent.metaKey = meta;
		$wnd.vkEvent.mouseWheelVelocity = mouseWheelVel;
		$wnd.vkEvent.relativeEventTarget = relativeEvtTarget;
		$wnd.vkEvent.screenX = screenx;
		$wnd.vkEvent.screenY = screeny;
		$wnd.vkEvent.shiftKey = shift;
		$wnd.vkEvent.preventDefault = function(){
			event.@com.google.gwt.event.dom.client.DomEvent::preventDefault()();
		}
		$wnd.vkEvent.stopPropagation = function(){
			event.@com.google.gwt.event.dom.client.DomEvent::stopPropagation()();
		}
	}-*/;
	public static native String formatJs(String js) /*-{
		var idArray = js.match(/&\(.+?\)/g);
		if(idArray != null)
			for(var i = 0; i < idArray.length; i++)
				js = js.replace(idArray[i],"window.document.getElementById('" + idArray[i].substr(2,idArray[i].length - 3) + "')");
		return js;
	}-*/;
}
