package com.vk.gwt.designer.client.Panels;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkFormTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitCompleteHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.generator.client.Export;

/**
 * @author gaurav.saxena
 *
 */
public class VkFormPanel extends FormPanel implements HasVkWidgets, IPanel, HasVkSubmitHandler, HasVkSubmitCompleteHandler, HasVkFormTarget, HasVkFormMethod, 
HasVkUrl{
	public static final String NAME = "Form Panel";
	private HandlerRegistration submitCompleteHandler;
	private String submitCompleteJs = "";
	private String submitJs = "";
	@Override
	public void add(Widget widget)
	{
		if(getWidget() != null)
			Window.alert("Form Panel can contain only one widget");
		else
			super.add(widget);
	}
	@Override
	public void addSubmitCompleteHandler(String js) {
		if(submitCompleteHandler != null)
			submitCompleteHandler.removeHandler();
		submitCompleteJs = js;
		submitCompleteHandler = addSubmitCompleteHandler(new SubmitCompleteHandler() {
			@Override
			public void onSubmitComplete(SubmitCompleteEvent event) {
				Map<String, String> eventproperties = new HashMap<String, String>();
				eventproperties.put("resultHtml", event.getResults());
				VkDesignerUtil.executeEvent(submitCompleteJs, eventproperties);
			}
		});
	}
	@Override
	public void addSubmitHandler(String js) {
		submitJs = js;
		addOnSubmitFunction(VkDesignerUtil.formatJs(submitJs));
	}
	private native void addOnSubmitFunction(String formatJs) /*-{
		this.@com.vk.gwt.designer.client.Panels.VkFormPanel::getElement()().onsubmit = function(){
			//find the function name and appends it at the end so that eval can return the function. E.g. function x(){alert('1')} will become
			//function x(){alert('1')} x;
			var z = eval(formatJs + formatJs.match(/function\s+[^\(\)]+\(\)/)[0].replace('function','').replace('()','') + ';');
			return z();
		}
	}-*/;
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkSubmitHandler.NAME))
			return submitJs;
		else if (eventName.equals(HasVkSubmitCompleteHandler.NAME))
			return submitCompleteJs;
		else
			return "";
	}
	@Override
	public void setTarget(String target) {
		DOM.setElementAttribute(getElement(), "target", target);
	}
	@Override
	public void setUrl(String url) {
		setAction(url);
	}
	@Override
	public String getUrl() {
		return getAction();
	}
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public String getMethod()
	{
		return super.getMethod();
	}
	@Override
	@Export
	public String getAction()
	{
		return super.getAction();
	}
	@Override
	@Export
	public String getEncoding()
	{
		return super.getEncoding();
	}
	@Override
	@Export
	public String getTarget()
	{
		return super.getTarget();
	}
	@Override
	@Export
	public void setAction(String url)
	{
		super.setAction(url);
	}
	@Override
	@Export
	public void setMethod(String method)
	{
		super.setMethod(method);
	}
}
