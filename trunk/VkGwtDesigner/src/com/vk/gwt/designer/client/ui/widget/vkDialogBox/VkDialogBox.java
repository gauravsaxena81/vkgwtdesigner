package com.vk.gwt.designer.client.ui.widget.vkDialogBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.designer.EventHelper;
import com.vk.gwt.designer.client.designer.VkStateHelper;

public class VkDialogBox extends DialogBox implements IVkPanel, HasVkCloseHandler, HasVkCaptionText, HasVkCaptionHtml, HasVkAutoHide, HasVkGlass
, HasVkGlassStyle, HasVkModal, HasVkInitiallyShowing{
	public static final String NAME = "Dialog Box(added to Page)";
	private HandlerRegistration closeRegistration;
	private String closeJs = "";
	private boolean isInitiallyShowing = true;
	@Override
	public void add(Widget w) {
		if(getWidget() != null)
			Window.alert("DialogBox can add only one widget");
		else
			super.add(w);
	}
	//to suppress its own dragging during designer mode
	@Override
	protected void beginDragging(MouseDownEvent event) {
		if(!VkStateHelper.getInstance().isDesignerMode())
			super.beginDragging(event);
	}
	@Override
	protected void endDragging(MouseUpEvent event) {
		if(!VkStateHelper.getInstance().isDesignerMode())
			super.endDragging(event);
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeRegistration != null)
			closeRegistration.removeHandler();
		closeRegistration = null;
		closeJs = js.trim();
		if(!closeJs.isEmpty())
		{
			closeRegistration = addCloseHandler(new CloseHandler<PopupPanel>() {
				@Override
				public void onClose(CloseEvent<PopupPanel> event) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("autoClosed",Boolean.toString(event.isAutoClosed()));
					EventHelper.getInstance().executeEvent(closeJs, map);
				}
			});
		}
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkCloseHandler.NAME))
			return closeJs;
		else
			return "";
	}
	@Override
	public void setHeight(String height)
	{
		//Window.alert("Please resize the contained widget to resize the dialogbox");
		if(getWidget() != null)
			getWidget().setHeight(height);
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public void setWidth(String width){
		if(getWidget() != null)
			getWidget().setWidth(width);
	}
	@Override
	public void clone(Widget targetWidget) {}
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isInitiallyShowing() {
		return isInitiallyShowing;
	}
	@Override
	public void setInitiallyShowing(boolean showing) {
		this.isInitiallyShowing = showing;
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
	public void setCaptionText(String text)
	{
		super.setText(text);
	}
	@Override
	@Export
	public String getCaptionText()
	{
		return super.getText();
	}
	@Override
	@Export
	public void setCaptionHtml(String html)
	{
		super.setHTML(html);
	}
	@Override
	@Export
	public String getCaptionHtml()
	{
		return super.getHTML();
	}
	@Override
	@Export
	public void center()
	{
		RootPanel.get().remove(this);
		super.center();
		DOM.setStyleAttribute(getElement(), "display", "");
	}
	@Override
	@Export
	public void hide() {
		super.hide();
		RootPanel.get().add(this);
		DOM.setStyleAttribute(getElement(), "display", "none");
	}
	@Override
	@Export
	public boolean isShowing() {
		return super.isShowing();
	}
	@Override
	@Export
	public void setPopupPosition(int left, int top) {
		super.setPopupPosition(left, top);
	}
	@Override
	@Export
	public void show()
	{
		RootPanel.get().remove(this);
		super.show();
		DOM.setStyleAttribute(getElement(), "display", "");
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
	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}
