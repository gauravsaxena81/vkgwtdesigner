package com.vk.gwt.designer.client.designer;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.Panels.VkDisclosurePanel;
import com.vk.gwt.designer.client.Panels.VkDockPanel;
import com.vk.gwt.designer.client.Panels.VkFlowPanel;
import com.vk.gwt.designer.client.Panels.VkFocusPanel;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.Panels.VkScrollPanel;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkFormTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMaxLength;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkName;
import com.vk.gwt.designer.client.api.attributes.HasVkOpenHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollBarShowing;
import com.vk.gwt.designer.client.api.attributes.HasVkScrollHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitCompleteHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.HasVkButton;
import com.vk.gwt.designer.client.api.widgets.HasVkCheckbox;
import com.vk.gwt.designer.client.api.widgets.HasVkFileUpload;
import com.vk.gwt.designer.client.api.widgets.HasVkFlexTable;
import com.vk.gwt.designer.client.api.widgets.HasVkFrame;
import com.vk.gwt.designer.client.api.widgets.HasVkLabel;
import com.vk.gwt.designer.client.api.widgets.HasVkTextBox;
import com.vk.gwt.designer.client.widgets.VkButton;
import com.vk.gwt.designer.client.widgets.VkCheckbox;
import com.vk.gwt.designer.client.widgets.VkFileUpload;
import com.vk.gwt.designer.client.widgets.VkFlexTable;
import com.vk.gwt.designer.client.widgets.VkFrame;
import com.vk.gwt.designer.client.widgets.VkLabel;
import com.vk.gwt.designer.client.widgets.VkTextBox;
import com.vk.gwt.generator.client.JsBridgable;

public class VkEngine implements IEngine{
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
			//VkMenu menu =  new VkMenu(widget, widgetEngine);
			//VkDesignerUtil.getDrawingPanel().add(menu);
			if(widget instanceof FocusWidget)
				VkDesignerUtil.addPressAndHoldEvent((FocusWidget)widget, widgetEngine);
			else
				VkDesignerUtil.addPressAndHoldEvent(widget, widgetEngine);
			if(invokingWidget instanceof IPanel)//all panels
				addJavascriptAddWidgetFunction((IPanel) invokingWidget);
			addRemoveJsFunction(widget);
			jsBridgable.createBridge(widget);
		}
		return widget;
	}
	//being used in native function
	@SuppressWarnings("unused")
	private void addWidget(IPanel invokingWidget, String widgetName)
	{
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName, (Widget) invokingWidget);
		if(widget != null)
			VkDesignerUtil.addWidget(widget, invokingWidget);
		else
			Window.alert("Widget with name '" + widgetName +"' not found");
	}

	private native void addJavascriptAddWidgetFunction(IPanel invokingWidget) /*-{
		var a = this;
		invokingWidget.@com.google.gwt.user.client.ui.Panel::getElement()().addWidget = $entry(function(widgetName){
			a.@com.vk.gwt.designer.client.designer.VkEngine::addWidget(Lcom/vk/gwt/designer/client/api/engine/IPanel;Ljava/lang/String;)(invokingWidget, widgetName);
		});
	}-*/;
	private native void addRemoveJsFunction(Widget widget) /*-{
		widget.@com.google.gwt.user.client.ui.Widget::getElement()().removeFromParent = $entry(function(){
			widget.@com.google.gwt.user.client.ui.Widget::removeFromParent()();
		});
	}-*/;
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
		if(invokingWidget instanceof HasVkWordWrap)
			optionList.add(HasVkWordWrap.NAME);
		if(invokingWidget instanceof HasVkDirection)
			optionList.add(HasVkDirection.NAME);
		if(invokingWidget instanceof HasVkMaxLength)
			optionList.add(HasVkMaxLength.NAME);
		if(invokingWidget instanceof HasVkHorizontalAlignment)
			optionList.add(HasVkHorizontalAlignment.NAME);
		if(invokingWidget instanceof HasVkAnimation)
			optionList.add(HasVkAnimation.NAME);
		if(invokingWidget instanceof HasVkVerticalAlignment)
			optionList.add(HasVkVerticalAlignment.NAME);
		if(invokingWidget instanceof HasVkFormTarget)
			optionList.add(HasVkFormTarget.NAME);
		if(invokingWidget instanceof HasVkUrl)
			optionList.add(HasVkUrl.NAME);
		if(invokingWidget instanceof HasVkFormMethod)
			optionList.add(HasVkFormMethod.NAME);
		if(invokingWidget instanceof HasVkImageUrl)
			optionList.add(HasVkImageUrl.NAME);
		if(invokingWidget instanceof HasVkScrollBarShowing)
			optionList.add(HasVkScrollBarShowing.NAME);
		if(invokingWidget instanceof HasVkSwitchNumberedWidget)
			optionList.add(HasVkSwitchNumberedWidget.NAME);
		if(invokingWidget instanceof HasVkName)
			optionList.add(HasVkName.NAME);
		
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
		if(invokingWidget instanceof HasVkCloseHandler)
			optionList.add(HasVkCloseHandler.NAME);
		if(invokingWidget instanceof HasVkOpenHandler)
			optionList.add(HasVkOpenHandler.NAME);
		if(invokingWidget instanceof HasVkSubmitHandler)
			optionList.add(HasVkSubmitHandler.NAME);
		if(invokingWidget instanceof HasVkSubmitCompleteHandler)
			optionList.add(HasVkSubmitCompleteHandler.NAME);
		if(invokingWidget instanceof HasVkScrollHandler)
			optionList.add(HasVkScrollHandler.NAME);
		if(invokingWidget instanceof HasVkBeforeSelectionHandler)
			optionList.add(HasVkBeforeSelectionHandler.NAME);
		if(invokingWidget instanceof HasVkSelectionHandler)
			optionList.add(HasVkSelectionHandler.NAME);
		return optionList;
	};
	public List<String> getWidgetsList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		if(invokingWidget instanceof HasVkTextBox)
			optionList.add(VkTextBox.NAME);
		if(invokingWidget instanceof HasVkButton)
			optionList.add(VkButton.NAME);
		if(invokingWidget instanceof HasVkLabel)
			optionList.add(VkLabel.NAME);
		if(invokingWidget instanceof HasVkFrame)
			optionList.add(VkFrame.NAME);
		if(invokingWidget instanceof HasVkCheckbox)
			optionList.add(VkCheckbox.NAME);
		if(invokingWidget instanceof HasVkFileUpload)
			optionList.add(VkFileUpload.NAME);
		if(invokingWidget instanceof HasVkFlexTable)
			optionList.add(VkFlexTable.NAME);
		return optionList;
	}
	
	public List<String> getPanelsList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		if(invokingWidget instanceof IPanel)
		{
			optionList.add(VkAbsolutePanel.NAME);
			optionList.add(VkVerticalPanel.NAME);
			optionList.add(VkCaptionPanel.NAME);
			optionList.add(VkDeckPanel.NAME);
			optionList.add(VkDisclosurePanel.NAME);
			optionList.add(VkDockPanel.NAME);
			optionList.add(VkFlowPanel.NAME);
			optionList.add(VkFocusPanel.NAME);
			optionList.add(VkFormPanel.NAME);
			optionList.add(VkHorizontalPanel.NAME);
			optionList.add(VkHorizontalSplitPanel.NAME);
			optionList.add(VkHtmlPanel.NAME);
			optionList.add(VkScrollPanel.NAME);
			optionList.add(VkStackPanel.NAME);
			optionList.add(VkTabPanel.NAME);
			optionList.add(VkVerticalSplitPanel.NAME);
		}
		return optionList;
	}
	
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
			showChooseEnabledDialog((HasVkEnabled) invokingWidget);
		else if(attributeName.equals(HasVkTabIndex.NAME))
			showAddTabIndexDialog((HasVkTabIndex) invokingWidget);
		else if(attributeName.equals(HasVkWordWrap.NAME))
			showChooseWordWrapDialog((HasVkWordWrap) invokingWidget);
		else if(attributeName.equals(HasVkDirection.NAME))
			showChooseDirectionDialog((HasVkDirection) invokingWidget);
		else if(attributeName.equals(HasVkMaxLength.NAME))
			showAddMaxLengthDialog((HasVkMaxLength) invokingWidget);
		else if(attributeName.equals(HasVkHorizontalAlignment.NAME))
			showChooseHorizontalAlignmentDialog((HasVkHorizontalAlignment) invokingWidget);
		else if(attributeName.equals(HasVkAnimation.NAME))
			showChooseAnimationDialog((HasVkAnimation) invokingWidget);
		else if(attributeName.equals(HasVkVerticalAlignment.NAME))
			showChooseVerticalAlignmentDialog((HasVkVerticalAlignment) invokingWidget);
		else if(attributeName.equals(HasVkFormTarget.NAME))
			showAddFormTargetDialog((HasVkFormTarget) invokingWidget);
		else if(attributeName.equals(HasVkUrl.NAME))
			showAddUrlDialog((HasVkUrl) invokingWidget);
		else if(attributeName.equals(HasVkFormMethod.NAME))
			showChooseFormMethodDialog((HasVkFormMethod) invokingWidget);
		else if(attributeName.equals(HasVkImageUrl.NAME))
			showAddImageUrlDialog((HasVkImageUrl) invokingWidget);
		else if(attributeName.equals(HasVkScrollBarShowing.NAME))
			showAddAlwaysShowScrollBarDialog((HasVkScrollBarShowing) invokingWidget);
		else if(attributeName.equals(HasVkSwitchNumberedWidget.NAME))
			showAddNumberedWidgetDialog((HasVkSwitchNumberedWidget) invokingWidget);
		else if(attributeName.equals(HasVkName.NAME))
			showAddNameDialog((HasVkName) invokingWidget);
		
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
		else if(attributeName.equals(HasVkMouseMoveHandler.NAME))
			showEventHandlingDialog((HasVkMouseMoveHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyDownHandler.NAME))
			showEventHandlingDialog((HasVkKeyDownHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyUpHandler.NAME))
			showEventHandlingDialog((HasVkKeyUpHandler) invokingWidget);
		else if(attributeName.equals(HasVkKeyPressHandler.NAME))
			showEventHandlingDialog((HasVkKeyPressHandler) invokingWidget);
		else if(attributeName.equals(HasVkCloseHandler.NAME))
			showEventHandlingDialog((HasVkCloseHandler) invokingWidget);
		else if(attributeName.equals(HasVkOpenHandler.NAME))
			showEventHandlingDialog((HasVkOpenHandler) invokingWidget);
		else if(attributeName.equals(HasVkSubmitHandler.NAME))
			showEventHandlingDialog((HasVkSubmitHandler) invokingWidget);
		else if(attributeName.equals(HasVkSubmitCompleteHandler.NAME))
			showEventHandlingDialog((HasVkSubmitCompleteHandler) invokingWidget);
		else if(attributeName.equals(HasVkScrollHandler.NAME))
			showEventHandlingDialog((HasVkScrollHandler) invokingWidget);
		else if(attributeName.equals(HasVkBeforeSelectionHandler.NAME))
			showEventHandlingDialog((HasVkBeforeSelectionHandler) invokingWidget);
		else if(attributeName.equals(HasVkSelectionHandler.NAME))
			showEventHandlingDialog((HasVkSelectionHandler) invokingWidget);
	}
	private void showEventHandlingDialog(final HasVkSelectionHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkSelectionHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addSelectionHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkBeforeSelectionHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkBeforeSelectionHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addBeforeSelectionHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkScrollHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkScrollHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addScrollHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkSubmitCompleteHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkSubmitCompleteHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addSubmitCompleteHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkSubmitHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkSubmitHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addSubmitHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkCloseHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkCloseHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addCloseHandler(js);
			}
		});
		
	}
	private void showEventHandlingDialog(final HasVkOpenHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkOpenHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addOpenHandler(js);
			}
		});
		
	}
	private void showEventHandlingDialog(final HasVkKeyPressHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkKeyPressHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyPressHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyUpHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkKeyUpHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyDownHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkKeyDownHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addKeyDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseMoveHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseMoveHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseMoveHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseWheelHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseWheelHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseWheelHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOutHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseOutHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseOutHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOverHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseOverHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseOverHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseUpHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseUpHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseDownHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkMouseDownHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addMouseDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkClickHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkClickHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addClickHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkFocusHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkFocusHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addFocusHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkBlurHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkBlurHandler.NAME, new IEventRegister(){
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
		showAddTextAttributeDialog("Please add class name below", addTextTa,new IEventRegister() {
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
		TextBox addTextTb = new TextBox();
		addTextTb.setWidth("200px");
		addTextTb.setText(invokingWidget.getText());
		showAddTextAttributeDialog("Please add text below", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setText(text);
			}
		});
	}
	private void showAddHTMLDialog(final HasVkHtml invokingWidget) {
		TextArea addTextTa = new TextArea();
		addTextTa.setPixelSize(200, 50);
		addTextTa.setText(invokingWidget.getHTML());
		showAddTextAttributeDialog("Please add html below", addTextTa, new IEventRegister() {
			@Override
			public void registerEvent(String html) {
				invokingWidget.setHTML(html);
			}
		});
	}
	private void showAddAccessKeyDialog(final HasVkAccessKey invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("100px");
		addTextTb.setMaxLength(1);
		addTextTb.setText(Character.toString(invokingWidget.getAccessKey()));
		showAddTextAttributeDialog("Please add access key below", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setAccessKey(addTextTb.getText().charAt(0));
			}
		});
	}
	private void showAddTabIndexDialog(final HasVkTabIndex invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("100px");
		addTextTb.setText(Integer.toString(invokingWidget.getTabIndex()));
		showAddTextAttributeDialog("Please add tabindex below", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					invokingWidget.setTabIndex(Integer.parseInt(addTextTb.getText()));
				}
				catch(NumberFormatException e)
				{
					Window.alert("TabIndex cannot be non-numeric");
				}
			}
		});
	}
	private void showChooseEnabledDialog(final HasVkEnabled invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("100px");
		if(invokingWidget.isEnabled())
			listBox.setSelectedIndex(0);
		else
			listBox.setSelectedIndex(1);
		showAddListDialog("Please choose true to make widget enabled", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setEnabled(Boolean.valueOf(text));
			}
		});
	}
	private void showChooseWordWrapDialog(final HasVkWordWrap invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("100px");
		showAddListDialog("Please choose true to make text wrap", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setWordWrap(Boolean.valueOf(text));
			}
		});
	}
	private void showChooseDirectionDialog(final HasVkDirection invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Right To Left", "RTL");
		listBox.addItem("Left To Right", "LTR");
		listBox.addItem("Default", "DEFAULT");
		listBox.setWidth("100px");
		String directionString = invokingWidget.getDirectionString();
		if(directionString.equals("RTL"))
			listBox.setSelectedIndex(0);
		else if(directionString.equals("LTR"))
			listBox.setSelectedIndex(1);
		else
			listBox.setSelectedIndex(2);
		showAddListDialog("Please set text direction", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setDirection(text);
			}
		});
	}
	private void showAddMaxLengthDialog(final HasVkMaxLength invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("100px");
		addTextTb.setText(Integer.toString(invokingWidget.getMaxLength()));
		showAddTextAttributeDialog("Please add maxlength below", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					invokingWidget.setMaxLength(Integer.parseInt(addTextTb.getText()));
				}
				catch(NumberFormatException e)
				{
					Window.alert("MaxLength cannot be non-numeric");
				}
			}
		});
	}
	private void showChooseHorizontalAlignmentDialog(final HasVkHorizontalAlignment invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Left", "left");
		listBox.addItem("Center", "center");
		listBox.addItem("Right", "right");
		listBox.setWidth("100px");
		showAddListDialog("Please choose widget's horizontal alignment", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setHorizontalAlignment(text);
			}
		});
	}
	private void showChooseAnimationDialog(final HasVkAnimation invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		if(invokingWidget.isAnimationEnabled())
			listBox.setSelectedIndex(0);
		else
			listBox.setSelectedIndex(1);
		listBox.setWidth("100px");
		showAddListDialog("Please choose true make widget animated", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setAnimationEnabled(Boolean.valueOf(text));
			}
		});
	}
	private void showChooseVerticalAlignmentDialog(final HasVkVerticalAlignment invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Top", "top");
		listBox.addItem("Middle", "middle");
		listBox.addItem("Bottom", "bottom");
		listBox.setWidth("100px");
		showAddListDialog("Please choose widget's vertical alignment", listBox, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setVerticalAlignment(text);
			}
		});
	}
	private void showChooseFormMethodDialog(final HasVkFormMethod invokingWidget) {
		ListBox methodLb = new ListBox();
		methodLb.addItem("GET", "get");
		methodLb.addItem("POST", "post");
		if(invokingWidget.getMethod().equals("get"))
			methodLb.setSelectedIndex(0);
		else
			methodLb.setSelectedIndex(1);
		methodLb.setWidth("100px");
		showAddListDialog("Please choose form method", methodLb, new IEventRegister() {
			@Override
			public void registerEvent(String method) {
				invokingWidget.setMethod(method);
			}
		});
	}
	private void showAddUrlDialog(final HasVkUrl invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(((HasVkUrl) invokingWidget).getUrl());
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getUrl());
		showAddTextAttributeDialog("Please add url below", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String action) {
				invokingWidget.setUrl(action);
			}
		});
	}
	private void showAddImageUrlDialog(final HasVkImageUrl invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(((HasVkImageUrl) invokingWidget).getImageUrl());
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getImageUrl());
		showAddTextAttributeDialog("Please add url below", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String action) {
				invokingWidget.setImageUrl(action);
			}
		});
	}
	private void showAddAlwaysShowScrollBarDialog(final HasVkScrollBarShowing invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isAlwaysShowScrollBars())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose form method", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String method) {
				invokingWidget.setAlwaysShowScrollBars(Boolean.valueOf(method));
			}
		});
	}
	private void showAddFormTargetDialog(final HasVkFormTarget invokingWidget) {
		List<String> targetList = new ArrayList<String>();
		targetList.add("_self");
		targetList.add("_top");
		targetList.add("_parent");
		AutoCompleterTextBox targetTb = new AutoCompleterTextBox(targetList);
		targetTb.setWidth("100px");
		targetTb.setText(((VkFormPanel) invokingWidget).getTarget());
		showAddAutoCompleteTextDialog(targetTb, new IEventRegister() {
			@Override
			public void registerEvent(String target) {
				invokingWidget.setTarget(target);
			}
		});
	}
	private void showAddNumberedWidgetDialog(final HasVkSwitchNumberedWidget invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(Integer.toString(((HasVkSwitchNumberedWidget) invokingWidget).getCurrentlyShowingWidget()));
		actionTb.setWidth("100px");
		actionTb.setText(Integer.toString(invokingWidget.getCurrentlyShowingWidget()));
		showAddTextAttributeDialog("Please provide widget number to show below", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String number) {
				try{
					invokingWidget.showWidget(Integer.parseInt(number));
				}
				catch(NumberFormatException e)
				{
					Window.alert("Widget number cannot be non-numeric");
				}
			}
		});
	}
	private void showAddNameDialog(final HasVkName invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(Integer.toString(((HasVkSwitchNumberedWidget) invokingWidget).getCurrentlyShowingWidget()));
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getName());
		showAddTextAttributeDialog("Please provide name of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String name) {
				invokingWidget.setName(name);
			}
		});
	}
	void showAddListDialog(String heading, final ListBox listBox, final IEventRegister eventRegister) {
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
		dialog.add(new Label(heading));
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
	protected void showAddTextAttributeDialog(String heading, final TextBoxBase addTextTa, final IEventRegister eventRegister) {
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
		dialog.add(new Label(heading));
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
	protected void showEventRegistrationDialog(HasVkEventHandler invokingWidget, String eventName, final IEventRegister iEventRegister) {
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
		};
		addTextTa.setText(invokingWidget.getPriorJs(eventName));
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
	protected void showAddAutoCompleteTextDialog(final AutoCompleterTextBox targetTb, final IEventRegister eventRegister) {
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
				targetTb.setFocus(true);
			}
		};
		t.schedule(100);
		dialog.add(new Label("Please add Text below:"));
		dialog.add(targetTb);
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("Save");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				eventRegister.registerEvent(targetTb.getText());
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
}
