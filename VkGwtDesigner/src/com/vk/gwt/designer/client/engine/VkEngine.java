package com.vk.gwt.designer.client.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkMenu;
import com.vk.gwt.generator.client.JsBridgable;

public class VkEngine {
	private JsBridgable jsBridgable = GWT.create(JsBridgable.class);
	
	protected interface IEventRegister{
		public void registerEvent(String js);
	}
	
	@SuppressWarnings("unchecked")
	public Widget getWidget(String widgetName, final Widget invokingWidget)
	{
		IWidgetEngine widgetEngine = VkDesignerUtil.getEngineMap().get(widgetName);
		Widget widget= widgetEngine.getWidget();
		if(widget != null)
		{
			VkMenu menu =  new VkMenu(widget, widgetEngine);
			VkDesignerUtil.getDrawingPanel().add(menu);
			if(widget instanceof FocusWidget)
				VkDesignerUtil.addPressAndHoldEvent((FocusWidget)widget, menu);
			else
				VkDesignerUtil.addPressAndHoldEvent(widget.getElement(), menu.getElement());
		}
		jsBridgable.createBridge(widget);
		return widget;
	}
	@SuppressWarnings("unchecked")
	public List<String> getAttributesList(Widget invokingWidget, IWidgetEngine widgetEngine) {
		return widgetEngine.getAttributesList(invokingWidget);
	}
	public List<String> getAttributesList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		optionList.add("Class Name");
		if(invokingWidget instanceof HasVkText)
			optionList.add(HasVkText.NAME);
		if(invokingWidget instanceof HasVkAccessKey)
			optionList.add(HasVkAccessKey.NAME);
		if(invokingWidget instanceof HasVkTabIndex)
			optionList.add(HasVkTabIndex.NAME);
		if(invokingWidget instanceof HasVkHtml)
			optionList.add(HasVkHtml.NAME);
		if(invokingWidget instanceof HasVkEnabled)
			optionList.add(HasVkEnabled.NAME);
		
		if(invokingWidget instanceof HasVkBlurHandler)
			optionList.add(HasVkBlurHandler.NAME);
		if(invokingWidget instanceof HasVkFocusHandler)
			optionList.add(HasVkFocusHandler.NAME);
		if(invokingWidget instanceof HasVkKeyDownHandler)
			optionList.add(HasVkKeyDownHandler.NAME);
		if(invokingWidget instanceof HasVkKeyPressHandler)
			optionList.add(HasVkKeyPressHandler.NAME);
		if(invokingWidget instanceof HasVkKeyUpHandler)
			optionList.add(HasVkKeyUpHandler.NAME);
		if(invokingWidget instanceof HasVkClickHandler)
			optionList.add(HasVkClickHandler.NAME);
		if(invokingWidget instanceof HasVkMouseDownHandler)
			optionList.add(HasVkMouseDownHandler.NAME);
		if(invokingWidget instanceof HasVkMouseMoveHandler)
			optionList.add(HasVkMouseMoveHandler.NAME);
		if(invokingWidget instanceof HasVkMouseUpHandler)
			optionList.add(HasVkMouseUpHandler.NAME);
		if(invokingWidget instanceof HasVkMouseOutHandler)
			optionList.add(HasVkMouseOutHandler.NAME);
		if(invokingWidget instanceof HasVkMouseOverHandler)
			optionList.add(HasVkMouseOverHandler.NAME);
		if(invokingWidget instanceof HasVkMouseWheelHandler)
			optionList.add(HasVkMouseWheelHandler.NAME);
		return optionList;
	}
	@SuppressWarnings("unchecked")
	public List<String> getWidgetsList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		if(invokingWidget instanceof HasVkWidgets)
		{
			Map<String, IWidgetEngine> engineMap = VkDesignerUtil.getEngineMap();
			for (Iterator<String> iterator = engineMap.keySet().iterator(); iterator.hasNext();) 
				optionList.add(iterator.next());
			addJavascriptAddFunction((Panel) invokingWidget);
		}
		return optionList;
	}
	//being used in native function
	@SuppressWarnings("unused")
	private void addWidget(Panel invokingWidget, String widgetName)
	{
		VkDesignerUtil.addWidget(getWidget(widgetName, invokingWidget), invokingWidget);
	}

	private native void addJavascriptAddFunction(Panel invokingWidget) /*-{
		var a = this;
		invokingWidget.@com.google.gwt.user.client.ui.Panel::getElement()().addWidget = $entry(function(widgetName){
			a.@com.vk.gwt.designer.client.engine.VkEngine::addWidget(Lcom/google/gwt/user/client/ui/Panel;Ljava/lang/String;)(invokingWidget, widgetName);
		});
	}-*/;
	
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals("Class Name"))
			showAddClassNameDialog(invokingWidget);
		else if(attributeName.equals(HasVkText.NAME))
			showAddTextDialog((HasVkText) invokingWidget);
		else if(attributeName.equals(HasVkHtml.NAME))
			showAddHTMLDialog((HasVkHtml) invokingWidget);
		else if(attributeName.equals(HasVkAccessKey.NAME))
			showAddAccessKeyDialog((HasVkAccessKey) invokingWidget);
		else if(attributeName.equals(HasVkEnabled.NAME))
			showAddEnabledDialog((HasVkEnabled) invokingWidget);
		else if(attributeName.equals(HasVkTabIndex.NAME))
			showAddTabIndexDialog((HasVkTabIndex) invokingWidget);
		else if(attributeName.equals(HasVkWordWrap.NAME))
			showAddWordWrapDialog((HasVkWordWrap) invokingWidget);
		
		else if(attributeName.equals(HasVkBlurHandler.NAME))
			showEventHandlingDialog((HasVkBlurHandler) invokingWidget);
		else if(attributeName.equals(HasVkFocusHandler.NAME))
			showEventHandlingDialog((HasVkFocusHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseDownHandler.NAME))
			showEventHandlingDialog((HasVkMouseDownHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseUpHandler.NAME))
			showEventHandlingDialog((HasVkMouseUpHandler) invokingWidget);
		else if(attributeName.equals(HasVkClickHandler.NAME))
			showEventHandlingDialog((HasVkClickHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseOverHandler.NAME))
			showEventHandlingDialog((HasVkMouseOverHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseOutHandler.NAME))
			showEventHandlingDialog((HasVkMouseOutHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseWheelHandler.NAME))
			showEventHandlingDialog((HasVkMouseWheelHandler) invokingWidget);
		else if(attributeName.equals(HasVkMouseWheelHandler.NAME))
			showEventHandlingDialog((HasVkMouseMoveHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyDownHandler.NAME))
			showEventHandlingDialog((HasVkKeyDownHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyUpHandler.NAME))
			showEventHandlingDialog((HasVkKeyUpHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyPressHandler.NAME))
			showEventHandlingDialog((HasVkKeyPressHandler) invokingWidget);
	}
	private void showEventHandlingDialog(final HasVkKeyPressHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyPressHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyUpHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyDownHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseMoveHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseMoveHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseWheelHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseWheelHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOutHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseOutHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOverHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseOverHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseUpHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseDownHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkClickHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addClickHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkFocusHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addFocusHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkBlurHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addBlurHandler(js);
			}
		});
	}
	private void showAddClassNameDialog(final Widget invokingWidget)
	{
		TextBox addTextTa = new TextBox();
		addTextTa.setWidth("200px");
		showAddTextAttributeDialog(addTextTa,new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				if(text.indexOf(" ") == -1)
					invokingWidget.addStyleName(text);
				else
					Window.alert("Class names should not contain white space");
			}
		});
	}
	private void showAddTextDialog(final HasVkText invokingWidget) {
		TextBox addTextTa = new TextBox();
		addTextTa.setWidth("200px");
		showAddTextAttributeDialog(addTextTa, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.addText(text);
			}
		});
	}
	private void showAddHTMLDialog(final HasVkHtml invokingWidget) {
		TextArea addTextTa = new TextArea();
		addTextTa.setPixelSize(200, 50);
		showAddTextAttributeDialog(addTextTa, new IEventRegister() {
			@Override
			public void registerEvent(String html) {
				invokingWidget.addHtml(html);
			}
		});
	}
	private void showAddAccessKeyDialog(final HasVkAccessKey invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("100px");
		addTextTb.setMaxLength(1);
		showAddTextAttributeDialog(addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.addAccessKey(addTextTb.getText().charAt(0));
			}
		});
	}
	private void showAddTabIndexDialog(final HasVkTabIndex invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("100px");
		showAddTextAttributeDialog(addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					invokingWidget.addTabIndex(Integer.parseInt(addTextTb.getText()));
				}
				catch(NumberFormatException e)
				{
					Window.alert("TabIndex cannot be non-numeric");
				}
			}
		});
	}
	private void showAddEnabledDialog(final HasVkEnabled invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("100px");
		showAddListDialog(listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.addEnabled(Boolean.valueOf(text));
			}
		});
	}
	private void showAddWordWrapDialog(final HasVkWordWrap invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("100px");
		showAddListDialog(listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.addWordWrap(Boolean.valueOf(text));
			}
		});
	}
	private void showAddListDialog(final ListBox listBox, final IEventRegister eventRegister) {
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
				listBox.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Please add Text below:"));
		dialog.add(listBox);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(listBox.getValue(listBox.getSelectedIndex()));
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
	public void showAddTextAttributeDialog(final TextBoxBase addTextTa, final IEventRegister eventRegister) {
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
				addTextTa.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Please add Text below:"));
		dialog.add(addTextTa);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(addTextTa.getText());
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
	private void showEventRegistrationDialog(HasVkEventHandler invokingWidget, final IEventRegister iEventRegister) {
		final VerticalPanel dialog = new VerticalPanel();
		VkDesignerUtil.getDrawingPanel().add(dialog);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setVisible(false);
		DOM.setStyleAttribute(dialog.getElement(), "position", "absolute");
		DOM.setStyleAttribute(dialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		dialog.setStyleName("VkDesigner-dialog");
		
		final TextArea addTextTa = new TextArea(){
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
		};
		addTextTa.setText(invokingWidget.getPriorJs(invokingWidget));
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		};
		t.schedule(100);
		addTextTa.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				DOM.setCapture(addTextTa.getElement());
			}
		});
		addTextTa.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				DOM.releaseCapture(addTextTa.getElement());
			}
		});
		final Label heading = new Label("Please add JS code below:");
		heading.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkDesignerUtil.makeMovable(dialog);
			}
		});
		addTextTa.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if(Element.as(event.getNativeEvent().getEventTarget()).equals( heading.getElement()))
				{
					DOM.releaseCapture(addTextTa.getElement());
					VkDesignerUtil.makeMovable(dialog);
				}
			}
		});
		dialog.add(heading);
		dialog.add(addTextTa);
		addTextTa.setPixelSize(300, 100);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				iEventRegister.registerEvent(addTextTa.getText());
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
	protected String getRealElementId(com.google.gwt.dom.client.Element element) 
	{
		if(element.getTagName().equals("TD") && element.getId().isEmpty())
			return element.getParentElement().getParentElement().getParentElement().getId();//table
		else
			return element.getId();
	}
}
