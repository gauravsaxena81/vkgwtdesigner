package com.vk.gwt.designer.client.designer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.gwtstructs.gwt.client.widgets.jsBridge.JsBridgable;
import com.vk.gwt.designer.client.Panels.VkAbsolutePanel;
import com.vk.gwt.designer.client.Panels.VkCaptionPanel;
import com.vk.gwt.designer.client.Panels.VkDeckPanel;
import com.vk.gwt.designer.client.Panels.VkDecoratedStackPanel;
import com.vk.gwt.designer.client.Panels.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.Panels.VkDisclosurePanel;
import com.vk.gwt.designer.client.Panels.VkDockPanel;
import com.vk.gwt.designer.client.Panels.VkFlowPanel;
import com.vk.gwt.designer.client.Panels.VkFocusPanel;
import com.vk.gwt.designer.client.Panels.VkFormPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalPanel;
import com.vk.gwt.designer.client.Panels.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.Panels.VkHtmlPanel;
import com.vk.gwt.designer.client.Panels.VkPopUpPanel;
import com.vk.gwt.designer.client.Panels.VkScrollPanel;
import com.vk.gwt.designer.client.Panels.VkSimplePanel;
import com.vk.gwt.designer.client.Panels.VkStackPanel;
import com.vk.gwt.designer.client.Panels.VkTabPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.Panels.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAlternateText;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoHide;
import com.vk.gwt.designer.client.api.attributes.HasVkAutoOpen;
import com.vk.gwt.designer.client.api.attributes.HasVkBeforeSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkCaptionText;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkDirection;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkEventHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFormEncoding;
import com.vk.gwt.designer.client.api.attributes.HasVkFormMethod;
import com.vk.gwt.designer.client.api.attributes.HasVkGlass;
import com.vk.gwt.designer.client.api.attributes.HasVkGlassStyle;
import com.vk.gwt.designer.client.api.attributes.HasVkHighlightHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkHistoryToken;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkImageUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkInitializeHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxMultiple;
import com.vk.gwt.designer.client.api.attributes.HasVkListBoxRenderMode;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMaxLength;
import com.vk.gwt.designer.client.api.attributes.HasVkModal;
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
import com.vk.gwt.designer.client.api.attributes.HasVkShowRangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitCompleteHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSubmitHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSwitchNumberedWidget;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderHtml;
import com.vk.gwt.designer.client.api.attributes.HasVkTabHeaderText;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkTarget;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.attributes.HasVkUrl;
import com.vk.gwt.designer.client.api.attributes.HasVkValue;
import com.vk.gwt.designer.client.api.attributes.HasVkValueChangeHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkWordWrap;
import com.vk.gwt.designer.client.api.engine.IEngine;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.HasVkAnchor;
import com.vk.gwt.designer.client.api.widgets.HasVkButton;
import com.vk.gwt.designer.client.api.widgets.HasVkCheckbox;
import com.vk.gwt.designer.client.api.widgets.HasVkDateBox;
import com.vk.gwt.designer.client.api.widgets.HasVkDecoratedTabBar;
import com.vk.gwt.designer.client.api.widgets.HasVkDialogBox;
import com.vk.gwt.designer.client.api.widgets.HasVkFileUpload;
import com.vk.gwt.designer.client.api.widgets.HasVkFlexTable;
import com.vk.gwt.designer.client.api.widgets.HasVkFrame;
import com.vk.gwt.designer.client.api.widgets.HasVkGrid;
import com.vk.gwt.designer.client.api.widgets.HasVkHTMLWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkHidden;
import com.vk.gwt.designer.client.api.widgets.HasVkHyperlink;
import com.vk.gwt.designer.client.api.widgets.HasVkImage;
import com.vk.gwt.designer.client.api.widgets.HasVkInlineHTML;
import com.vk.gwt.designer.client.api.widgets.HasVkInlineHyperlink;
import com.vk.gwt.designer.client.api.widgets.HasVkInlineLabel;
import com.vk.gwt.designer.client.api.widgets.HasVkLabel;
import com.vk.gwt.designer.client.api.widgets.HasVkListBox;
import com.vk.gwt.designer.client.api.widgets.HasVkMenuBarHorizontal;
import com.vk.gwt.designer.client.api.widgets.HasVkMenuBarVertical;
import com.vk.gwt.designer.client.api.widgets.HasVkPasswordTextBox;
import com.vk.gwt.designer.client.api.widgets.HasVkPushButton;
import com.vk.gwt.designer.client.api.widgets.HasVkRadioButton;
import com.vk.gwt.designer.client.api.widgets.HasVkResetButton;
import com.vk.gwt.designer.client.api.widgets.HasVkRichTextArea;
import com.vk.gwt.designer.client.api.widgets.HasVkSubmitButton;
import com.vk.gwt.designer.client.api.widgets.HasVkSuggestBox;
import com.vk.gwt.designer.client.api.widgets.HasVkTabBar;
import com.vk.gwt.designer.client.api.widgets.HasVkTextBox;
import com.vk.gwt.designer.client.api.widgets.HasVkToggleButton;
import com.vk.gwt.designer.client.api.widgets.HasVkTree;
import com.vk.gwt.designer.client.widgets.VkAnchor;
import com.vk.gwt.designer.client.widgets.VkButton;
import com.vk.gwt.designer.client.widgets.VkCheckbox;
import com.vk.gwt.designer.client.widgets.VkDateBox;
import com.vk.gwt.designer.client.widgets.VkDecoratedTabBar;
import com.vk.gwt.designer.client.widgets.VkDialogBox;
import com.vk.gwt.designer.client.widgets.VkFileUpload;
import com.vk.gwt.designer.client.widgets.VkFlexTable;
import com.vk.gwt.designer.client.widgets.VkFrame;
import com.vk.gwt.designer.client.widgets.VkGrid;
import com.vk.gwt.designer.client.widgets.VkHTML;
import com.vk.gwt.designer.client.widgets.VkHidden;
import com.vk.gwt.designer.client.widgets.VkHyperlink;
import com.vk.gwt.designer.client.widgets.VkImage;
import com.vk.gwt.designer.client.widgets.VkInlineHTML;
import com.vk.gwt.designer.client.widgets.VkInlineHyperlink;
import com.vk.gwt.designer.client.widgets.VkInlineLabel;
import com.vk.gwt.designer.client.widgets.VkLabel;
import com.vk.gwt.designer.client.widgets.VkListBox;
import com.vk.gwt.designer.client.widgets.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.widgets.VkMenuBarVertical;
import com.vk.gwt.designer.client.widgets.VkPasswordTextBox;
import com.vk.gwt.designer.client.widgets.VkPushButton;
import com.vk.gwt.designer.client.widgets.VkRadioButton;
import com.vk.gwt.designer.client.widgets.VkResetButton;
import com.vk.gwt.designer.client.widgets.VkRichTextArea;
import com.vk.gwt.designer.client.widgets.VkSubmitButton;
import com.vk.gwt.designer.client.widgets.VkSuggestBox;
import com.vk.gwt.designer.client.widgets.VkTabBar;
import com.vk.gwt.designer.client.widgets.VkTextBox;
import com.vk.gwt.designer.client.widgets.VkToggleButton;
import com.vk.gwt.designer.client.widgets.VkTree;


public class VkEngine implements IEngine{
	
	private JsBridgable jsBridgable = GWT.create(JsBridgable.class);
	
	public interface IEventRegister{
		public void registerEvent(String js);
	}
	
	@SuppressWarnings("unchecked")
	public Widget getWidget(String widgetName)
	{
		IWidgetEngine widgetEngine = VkDesignerUtil.getEngineMap().get(widgetName);
		Widget widget= widgetEngine.getWidget();
		if(widget != null)
			prepareWidget(widget, widgetEngine);
		return widget;
	}
	@SuppressWarnings("unchecked")
	public void prepareWidget(Widget widget, IWidgetEngine widgetEngine) {
			VkDesignerUtil.assignId(widget);
			if(VkDesignerUtil.isDesignerMode)
				VkDesignerUtil.initDesignerEvents(widget, widgetEngine);
			if(widget instanceof IPanel)//all panels
				addJavascriptAddWidgetFunction((IPanel) widget);
			addRemoveJsFunction(widget);
			jsBridgable.createBridge(widget);
		}
	//being used in native function
	@SuppressWarnings("unused")
	private void addWidget(IPanel invokingWidget, String widgetName)
	{
		Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName);
		if(widget != null)
			VkDesignerUtil.addWidget(widget, invokingWidget);
		else
			Window.alert("Widget with name '" + widgetName +"' not found");
	}

	private native void addJavascriptAddWidgetFunction(IPanel invokingWidget) /*-{
		var a = this;
		invokingWidget.@com.google.gwt.user.client.ui.Panel::getElement()().addWidget = $entry(function(widgetName){
			a.@com.vk.gwt.designer.client.designer.VkEngine::addWidget(Lcom/vk/gwt/designer/client/api/engine/IPanel;Ljava/lang/String;)(invokingWidget
				, widgetName);
		});
	}-*/;
	private native void addRemoveJsFunction(Widget widget) /*-{
		widget.@com.google.gwt.user.client.ui.Widget::getElement()().removeFromParent = $entry(function(){
			widget.@com.google.gwt.user.client.ui.Widget::removeFromParent()();
		});
	}-*/;
	public List<String> getOperationsList(Widget invokingWidget) {
		List<String> operationsList = new ArrayList<String>();
		if(!(invokingWidget instanceof VkMainDrawingPanel))
		{
			operationsList.add(IEngine.REMOVE);
			operationsList.add(IEngine.RESIZE);
			operationsList.add(IEngine.COPY_STYLE);
			operationsList.add(IEngine.PASTE_STYLE);
			operationsList.add(IEngine.CUT);
			operationsList.add(IEngine.COPY);
		}
		if(invokingWidget instanceof IPanel)
			operationsList.add(PASTE);
		if((invokingWidget instanceof VkMainDrawingPanel))
		{
			operationsList.add(IEngine.SAVE);
			operationsList.add(IEngine.LOAD);
		}
		return operationsList;
	}
	@SuppressWarnings("unchecked")
	public List<String> getAttributesList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		optionList.add("Class Name");
		optionList.add("Tool Tip");
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
		if(invokingWidget instanceof HasVkTarget)
			optionList.add(HasVkTarget.NAME);
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
		if(invokingWidget instanceof HasVkCaptionText)
			optionList.add(HasVkCaptionText.NAME);
		if(invokingWidget instanceof HasVkCaptionHtml)
			optionList.add(HasVkCaptionHtml.NAME);
		if(invokingWidget instanceof HasVkAutoHide)
			optionList.add(HasVkAutoHide.NAME);
		if(invokingWidget instanceof HasVkGlass)
			optionList.add(HasVkGlass.NAME);
		if(invokingWidget instanceof HasVkGlassStyle)
			optionList.add(HasVkGlassStyle.NAME);
		if(invokingWidget instanceof HasVkModal)
			optionList.add(HasVkModal.NAME);
		if(invokingWidget instanceof HasVkFormEncoding)
			optionList.add(HasVkFormEncoding.NAME);
		if(invokingWidget instanceof HasVkHistoryToken)
			optionList.add(HasVkHistoryToken.NAME);
		if(invokingWidget instanceof HasVkAlternateText)
			optionList.add(HasVkAlternateText.NAME);
		if(invokingWidget instanceof HasVkListBoxMultiple)
			optionList.add(HasVkListBoxMultiple.NAME);
		if(invokingWidget instanceof HasVkAutoOpen)
			optionList.add(HasVkAutoOpen.NAME);
		if(invokingWidget instanceof HasVkTabHeaderText)
			optionList.add(HasVkTabHeaderText.NAME);
		if(invokingWidget instanceof HasVkTabHeaderHtml)
			optionList.add(HasVkTabHeaderHtml.NAME);
		if(invokingWidget instanceof HasVkValue)
			optionList.add(HasVkValue.NAME);
		if(invokingWidget instanceof HasVkListBoxRenderMode)
			optionList.add(HasVkListBoxRenderMode.NAME);
		
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
		if(invokingWidget instanceof HasVkInitializeHandlers)
			optionList.add(HasVkInitializeHandlers.NAME);
		if(invokingWidget instanceof HasVkValueChangeHandler)
			optionList.add(HasVkValueChangeHandler.NAME);
		if(invokingWidget instanceof HasVkHighlightHandlers)
			optionList.add(HasVkHighlightHandlers.NAME);
		if(invokingWidget instanceof HasVkShowRangeHandler)
			optionList.add(HasVkShowRangeHandler.NAME);
		if(invokingWidget instanceof HasVkLoadHandler)
			optionList.add(HasVkLoadHandler.NAME);
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
		if(invokingWidget instanceof HasVkGrid)
			optionList.add(VkGrid.NAME);
		if(invokingWidget instanceof HasVkHTMLWidget)
			optionList.add(VkHTML.NAME);
		if(invokingWidget instanceof HasVkHidden)
			optionList.add(VkHidden.NAME);
		if(invokingWidget instanceof HasVkHyperlink)
			optionList.add(VkHyperlink.NAME);
		if(invokingWidget instanceof HasVkImage)
			optionList.add(VkImage.NAME);
		if(invokingWidget instanceof HasVkListBox)
			optionList.add(VkListBox.NAME);
		if(invokingWidget instanceof HasVkMenuBarHorizontal)
			optionList.add(VkMenuBarHorizontal.NAME);
		if(invokingWidget instanceof HasVkMenuBarVertical)
			optionList.add(VkMenuBarVertical.NAME);
		if(invokingWidget instanceof HasVkDialogBox)
			optionList.add(VkDialogBox.NAME);
		if(invokingWidget instanceof HasVkPushButton)
			optionList.add(VkPushButton.NAME);
		if(invokingWidget instanceof HasVkRadioButton)
			optionList.add(VkRadioButton.NAME);
		if(invokingWidget instanceof HasVkRichTextArea)
			optionList.add(VkRichTextArea.NAME);
		if(invokingWidget instanceof HasVkSuggestBox)
			optionList.add(VkSuggestBox.NAME);
		if(invokingWidget instanceof HasVkTabBar)
			optionList.add(VkTabBar.NAME);
		if(invokingWidget instanceof HasVkToggleButton)
			optionList.add(VkToggleButton.NAME);
		if(invokingWidget instanceof HasVkTree)
			optionList.add(VkTree.NAME);
		if(invokingWidget instanceof HasVkPasswordTextBox)
			optionList.add(VkPasswordTextBox.NAME);
		if(invokingWidget instanceof HasVkAnchor)
			optionList.add(VkAnchor.NAME);
		if(invokingWidget instanceof HasVkResetButton)
			optionList.add(VkResetButton.NAME);
		if(invokingWidget instanceof HasVkSubmitButton)
			optionList.add(VkSubmitButton.NAME);
		if(invokingWidget instanceof HasVkDecoratedTabBar)
			optionList.add(VkDecoratedTabBar.NAME);
		if(invokingWidget instanceof HasVkInlineLabel)
			optionList.add(VkInlineLabel.NAME);
		if(invokingWidget instanceof HasVkInlineHTML)
			optionList.add(VkInlineHTML.NAME);
		if(invokingWidget instanceof HasVkInlineHyperlink)
			optionList.add(VkInlineHyperlink.NAME);
		if(invokingWidget instanceof HasVkDateBox)
			optionList.add(VkDateBox.NAME);
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
			optionList.add(VkSimplePanel.NAME);
			optionList.add(VkPopUpPanel.NAME);
			optionList.add(VkDecoratedStackPanel.NAME);
			optionList.add(VkDecoratedTabPanel.NAME);
		}
		return optionList;
	}
	
	@SuppressWarnings("unchecked")
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals("Class Name"))
			showAddClassNameDialog(invokingWidget);
		if(attributeName.equals("Tool Tip"))
			showAddTitleDialog(invokingWidget);
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
		else if(attributeName.equals(HasVkTarget.NAME))
			showAddFormTargetDialog((HasVkTarget) invokingWidget);
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
		else if(attributeName.equals(HasVkCaptionText.NAME))
			showAddCaptionTextDialog((HasVkCaptionText) invokingWidget);
		else if(attributeName.equals(HasVkCaptionHtml.NAME))
			showAddCaptionHtmlDialog((HasVkCaptionHtml) invokingWidget);
		else if(attributeName.equals(HasVkAutoHide.NAME))
			showAutoHideDialog((HasVkAutoHide) invokingWidget);
		else if(attributeName.equals(HasVkGlass.NAME))
			showGlassDialog((HasVkGlass) invokingWidget);
		else if(attributeName.equals(HasVkGlassStyle.NAME))
			showAddGlassStyleDialog((HasVkGlassStyle) invokingWidget);
		else if(attributeName.equals(HasVkModal.NAME))
			showModalDialog((HasVkModal) invokingWidget);
		else if(attributeName.equals(HasVkFormEncoding.NAME))
			showFormEncodingDialog((HasVkFormEncoding) invokingWidget);
		else if(attributeName.equals(HasVkHistoryToken.NAME))
			showHistoryTokenDialog((HasVkHistoryToken) invokingWidget);
		else if(attributeName.equals(HasVkAlternateText.NAME))
			showAlternateTextDialog((HasVkAlternateText) invokingWidget);
		else if(attributeName.equals(HasVkListBoxMultiple.NAME))
			showListBoxMultipleDialog((HasVkListBoxMultiple) invokingWidget);
		else if(attributeName.equals(HasVkAutoOpen.NAME))
			showAutoOpenDialog((HasVkAutoOpen) invokingWidget);
		else if(attributeName.equals(HasVkTabHeaderText.NAME))
			showAddTabHeaderTextDialog((HasVkTabHeaderText) invokingWidget);
		else if(attributeName.equals(HasVkTabHeaderHtml.NAME))
			showAddTabHeaderHtmlDialog((HasVkTabHeaderHtml) invokingWidget);
		else if(attributeName.equals(HasVkValue.NAME))
			showAddValueDialog((HasVkValue) invokingWidget);
		else if(attributeName.equals(HasVkListBoxRenderMode.NAME))
			showChoseListboxRenderModeDialog((HasVkListBoxRenderMode) invokingWidget);
		
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
		else if(attributeName.equals(HasVkInitializeHandlers.NAME))
			showEventHandlingDialog((HasVkInitializeHandlers) invokingWidget);
		else if(attributeName.equals(HasVkValueChangeHandler.NAME))
			showEventHandlingDialog((HasVkValueChangeHandler) invokingWidget);
		else if(attributeName.equals(HasVkHighlightHandlers.NAME))
			showEventHandlingDialog((HasVkHighlightHandlers) invokingWidget);
		else if(attributeName.equals(HasVkShowRangeHandler.NAME))
			showEventHandlingDialog((HasVkShowRangeHandler) invokingWidget);
		else if(attributeName.equals(HasVkLoadHandler.NAME))
			showEventHandlingDialog((HasVkLoadHandler) invokingWidget);
	}
	private void showEventHandlingDialog(final HasVkLoadHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkLoadHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addLoadHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkShowRangeHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkShowRangeHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addShowRangeHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkHighlightHandlers invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkHighlightHandlers.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addHighlightHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkValueChangeHandler invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkValueChangeHandler.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addValueChangeHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkInitializeHandlers invokingWidget) {
		showEventRegistrationDialog(invokingWidget, HasVkInitializeHandlers.NAME, new IEventRegister(){
			@Override
			public void registerEvent(String js) {
				invokingWidget.addInitializeHandler(js);
			}
		});
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
	private void showAddTitleDialog(final Widget invokingWidget)
	{
		TextBox addTextTa = new TextBox();
		addTextTa.setValue(invokingWidget.getTitle());
		addTextTa.setWidth("200px");
		showAddTextAttributeDialog("Please add title below", addTextTa,new IEventRegister() {
			@Override
			public void registerEvent(String text) {
					invokingWidget.setTitle(text);
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
		if(invokingWidget.getAccessKey() > 0)
			addTextTb.setText(Character.toString(invokingWidget.getAccessKey()));
		showAddTextAttributeDialog("Please add access key below", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				String charString = addTextTb.getText().trim();
				if(charString.length() > 0)
					invokingWidget.setAccessKey(charString.charAt(0));
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
		showAddTextAttributeDialog("Please add maxlength below (-1 to remove)", addTextTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				try{
					int maxLength = Integer.parseInt(addTextTb.getText());
					if(maxLength >= 0)
						invokingWidget.setMaxLength(maxLength);
					else
						invokingWidget.removeMaxLength();
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
	private void showAddFormTargetDialog(final HasVkTarget invokingWidget) {
		List<String> targetList = new ArrayList<String>();
		targetList.add("_self");
		targetList.add("_top");
		targetList.add("_parent");
		targetList.add("_blank");
		AutoCompleterTextBox targetTb = new AutoCompleterTextBox(targetList);
		targetTb.setWidth("100px");
		targetTb.setText(invokingWidget.getTarget());
		targetTb.setSuggestionWidth(100);
		showAddAutoCompleteTextDialog("Please provide Form Target", targetTb, new IEventRegister() {
			@Override
			public void registerEvent(String target) {
				invokingWidget.setTarget(target);
			}
		});
	}
	private void showAddNumberedWidgetDialog(final HasVkSwitchNumberedWidget invokingWidget) {
		final TextBox actionTb = new TextBox();
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
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getName());
		showAddTextAttributeDialog("Please provide name of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String name) {
				invokingWidget.setName(name);
			}
		});
	}
	private void showAddCaptionTextDialog(final HasVkCaptionText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getCaptionText());
		showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setCaptionText(text);
			}
		});
	}
	private void showAddCaptionHtmlDialog(final HasVkCaptionHtml invokingWidget) {
		final TextArea actionTb = new TextArea();
		actionTb.setSize("200px","50px");
		actionTb.setText(invokingWidget.getCaptionHtml());
		showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String html) {
				invokingWidget.setCaptionHtml(html);
			}
		});
	}
	private void showAutoHideDialog(final HasVkAutoHide invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isAutoHideEnabled())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose Auto Hide Setting", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String method) {
				invokingWidget.setAutoHideEnabled(Boolean.valueOf(method));
			}
		});
	}
	private void showGlassDialog(final HasVkGlass invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isGlassEnabled())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose form method", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String method) {
				invokingWidget.setGlassEnabled(Boolean.valueOf(method));
			}
		});
	}
	private void showAddGlassStyleDialog(final HasVkGlassStyle invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getGlassStyleName());
		showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String name) {
				invokingWidget.setGlassStyleName(name);
			}
		});
	}
	private void showModalDialog(final HasVkModal invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isModal())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose form method", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String value) {
				invokingWidget.setModal(Boolean.valueOf(value));
			}
		});
	}
	private void showFormEncodingDialog(final HasVkFormEncoding invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getEncoding());
		showAddTextAttributeDialog("Please provide Form encoding", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String encoding) {
				invokingWidget.setEncoding(encoding);
			}
		});
	}
	private void showHistoryTokenDialog(final HasVkHistoryToken invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getTargetHistoryToken());
		showAddTextAttributeDialog("Please provide history token", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String token) {
				invokingWidget.setTargetHistoryToken(token);
			}
		});
	}
	private void showAlternateTextDialog(final HasVkAlternateText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getAlt());
		showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setAlt(text);
			}
		});
	}
	private void showListBoxMultipleDialog(final HasVkListBoxMultiple invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isMultipleEnabled())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose form method", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String value) {
				invokingWidget.setMultipleEnabled(Boolean.valueOf(value));
			}
		});
	}
	private void showAutoOpenDialog(final HasVkAutoOpen invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.getAutoOpen())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("100px");
		showAddListDialog("Please choose form method", scrollLb, new IEventRegister() {
			@Override
			public void registerEvent(String value) {
				invokingWidget.setAutoOpen(Boolean.valueOf(value));
			}
		});
	}
	private void showAddTabHeaderTextDialog(final HasVkTabHeaderText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("100px");
		actionTb.setText(invokingWidget.getTabText());
		showAddTextAttributeDialog("Please provide text for tab header", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setTabText(text);
			}
		});
	}
	private void showAddTabHeaderHtmlDialog(final HasVkTabHeaderHtml invokingWidget) {
		final TextArea actionTb = new TextArea();
		actionTb.setText(((HasVkTabHeaderText) invokingWidget).getTabText());
		actionTb.setSize("100px","50px");
		actionTb.setText(invokingWidget.getTabHTML());
		showAddTextAttributeDialog("Please provide html for tab header", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String html) {
				invokingWidget.setTabHTML(html);
			}
		});
	}
	@SuppressWarnings("unchecked")
	private void showAddValueDialog(final HasVkValue invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(invokingWidget.getValue().toString());
		actionTb.setWidth("100px");
		showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IEventRegister() {
			@Override
			public void registerEvent(String text) {
				invokingWidget.setValue(text);
			}
		});
	}
	private void showChoseListboxRenderModeDialog(final HasVkListBoxRenderMode invokingWidget) {
		final ListBox listBox = new ListBox();
		listBox.addItem("Drop Down","true");
		listBox.addItem("List","false");
		showAddListDialog("Pick a render mode", listBox, new IEventRegister() {
				@Override
				public void registerEvent(String value) {
					invokingWidget.setDropDown(Boolean.parseBoolean(value));
				}
			});
	}
	public void showAddListDialog(String heading, final ListBox listBox, final IEventRegister eventRegister) {
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
				VkDesignerUtil.centerDialog(dialog);
				listBox.setFocus(true);
			}
		}.schedule(100);
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
	public void showAddTextAttributeDialog(String heading, final TextBoxBase addTextTa, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		Timer t = new Timer(){
			@Override
			public void run() {
				VkDesignerUtil.centerDialog(dialog);
				addTextTa.setFocus(true);
			}
		};
		t.schedule(100);
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
	public void showEventRegistrationDialog(HasVkEventHandler invokingWidget, String eventName, final IEventRegister iEventRegister) {
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
				VkDesignerUtil.centerDialog(dialog);
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
	}
	public void showAddAutoCompleteTextDialog(String heading, final AutoCompleterTextBox targetTb, final IEventRegister eventRegister) {
		final DialogBox origDialog = new DialogBox();
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText(heading);
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.setWidth("100%");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.MAX_VALUE + "");
		Timer t = new Timer(){
			@Override
			public void run() {
				targetTb.setFocus(true);
			}
		};
		t.schedule(100);
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
}
