/*
 * Copyright 2011 Gaurav Saxena < gsaxena81 AT gmail.com >
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vk.gwt.designer.client.designer;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.IndexedPanel;
import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.autocompleterTextbox.AutoCompleterTextBox;
import com.gwtstructs.gwt.client.widgets.jsBridge.JsBridgable;
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
import com.vk.gwt.designer.client.api.attributes.HasVkDoubleClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
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
import com.vk.gwt.designer.client.api.attributes.HasVkInitiallyShowing;
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
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.engine.IEngine;
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
import com.vk.gwt.designer.client.api.widgets.HasVkImage;
import com.vk.gwt.designer.client.api.widgets.HasVkInlineHTML;
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
import com.vk.gwt.designer.client.api.widgets.HasVkTextArea;
import com.vk.gwt.designer.client.api.widgets.HasVkTextBox;
import com.vk.gwt.designer.client.api.widgets.HasVkToggleButton;
import com.vk.gwt.designer.client.api.widgets.HasVkTree;
import com.vk.gwt.designer.client.designer.VkDesignerUtil.IDialogCallback;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;
import com.vk.gwt.designer.client.ui.panel.vkCaptionPanel.VkCaptionPanel;
import com.vk.gwt.designer.client.ui.panel.vkDeckPanel.VkDeckPanel;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedStackPanel.VkDecoratedStackPanel;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedTabPanel.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.ui.panel.vkDisclosurePanel.VkDisclosurePanel;
import com.vk.gwt.designer.client.ui.panel.vkDockPanel.VkDockPanel;
import com.vk.gwt.designer.client.ui.panel.vkFlowPanel.VkFlowPanel;
import com.vk.gwt.designer.client.ui.panel.vkFocusPanel.VkFocusPanel;
import com.vk.gwt.designer.client.ui.panel.vkFormPanel.VkFormPanel;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalPanel.VkHorizontalPanel;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalSplitPanel.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.ui.panel.vkHtmlPanel.VkHtmlPanel;
import com.vk.gwt.designer.client.ui.panel.vkPopUpPanel.VkPopUpPanel;
import com.vk.gwt.designer.client.ui.panel.vkScrollPanel.VkScrollPanel;
import com.vk.gwt.designer.client.ui.panel.vkSimplePanel.VkSimplePanel;
import com.vk.gwt.designer.client.ui.panel.vkStackPanel.VkStackPanel;
import com.vk.gwt.designer.client.ui.panel.vkTabPanel.VkTabPanel;
import com.vk.gwt.designer.client.ui.panel.vkVerticalPanel.VkVerticalPanel;
import com.vk.gwt.designer.client.ui.panel.vkVerticalSplitPanel.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.ui.widget.button.vkButton.VkButton;
import com.vk.gwt.designer.client.ui.widget.button.vkPushButton.VkPushButton;
import com.vk.gwt.designer.client.ui.widget.button.vkResetButton.VkResetButton;
import com.vk.gwt.designer.client.ui.widget.button.vkSubmitButton.VkSubmitButton;
import com.vk.gwt.designer.client.ui.widget.button.vkToggleButton.VkToggleButton;
import com.vk.gwt.designer.client.ui.widget.label.vkHtml.VkHTML;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineHtml.VkInlineHTML;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineLabel.VkInlineLabel;
import com.vk.gwt.designer.client.ui.widget.label.vkLabel.VkLabel;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical.VkMenuBarVertical;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkDecoratedTabBar.VkDecoratedTabBar;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkTabBar.VkTabBar;
import com.vk.gwt.designer.client.ui.widget.table.vkFlextable.VkFlexTable;
import com.vk.gwt.designer.client.ui.widget.table.vkGrid.VkGrid;
import com.vk.gwt.designer.client.ui.widget.text.vkPasswordTextBox.VkPasswordTextBox;
import com.vk.gwt.designer.client.ui.widget.text.vkTextArea.VkTextArea;
import com.vk.gwt.designer.client.ui.widget.text.vkTextBox.VkTextBox;
import com.vk.gwt.designer.client.ui.widget.vkAnchor.VkAnchor;
import com.vk.gwt.designer.client.ui.widget.vkCheckbox.VkCheckbox;
import com.vk.gwt.designer.client.ui.widget.vkDateBox.VkDateBox;
import com.vk.gwt.designer.client.ui.widget.vkDialogBox.VkDialogBox;
import com.vk.gwt.designer.client.ui.widget.vkFileUpload.VkFileUpload;
import com.vk.gwt.designer.client.ui.widget.vkFrame.VkFrame;
import com.vk.gwt.designer.client.ui.widget.vkHidden.VkHidden;
import com.vk.gwt.designer.client.ui.widget.vkImage.VkImage;
import com.vk.gwt.designer.client.ui.widget.vkListBox.VkListBox;
import com.vk.gwt.designer.client.ui.widget.vkRadioButton.VkRadioButton;
import com.vk.gwt.designer.client.ui.widget.vkRichText.VkRichTextArea;
import com.vk.gwt.designer.client.ui.widget.vkSuggestBox.VkSuggestBox;
import com.vk.gwt.designer.client.ui.widget.vkTree.VkTree;


public class VkEngine implements IEngine{
	
	private JsBridgable jsBridgable = GWT.create(JsBridgable.class);
	private int widgetCount = 0;
	@Override
	public Widget getWidget(String widgetName) {
		Widget widget= VkStateHelper.getInstance().getWidgetEngineMapping().getEngineMap().get(widgetName).getWidget();
		if(widget != null)
			prepareWidget(widget);
		return widget;
	}
	@Override
	public void prepareWidget(Widget widget) {
		VkStateHelper.getInstance().getSnapHelper().addToSnappableWidgets(widget);
		assignId(widget);
		if(VkStateHelper.getInstance().isDesignerMode() || VkStateHelper.getInstance().isLoadRunning())
			InitializeHelper.getInstance().initDesignerEvents(widget);
		if(widget instanceof IVkPanel)//all panels
			addJavascriptAddWidgetFunction((IVkPanel) widget);
		addRemoveJsFunction(widget);
		jsBridgable.createBridge(widget);
	}
	private void assignId(Widget widget) {
		widget.getElement().setId(++widgetCount + "");
	}
	private native void addJavascriptAddWidgetFunction(IVkPanel invokingWidget) /*-{
		var a = this;
		invokingWidget.@com.google.gwt.user.client.ui.Panel::getElement()().addWidget = $entry(function(widgetName){
			a.@com.vk.gwt.designer.client.designer.VkEngine::addWidget(Ljava/lang/String;Lcom/vk/gwt/designer/client/api/component/IVkPanel;)(widgetName, invokingWidget);
		});
	}-*/;
	private native void addRemoveJsFunction(Widget widget) /*-{
		widget.@com.google.gwt.user.client.ui.Widget::getElement()().removeFromParent = $entry(function(){
			widget.@com.google.gwt.user.client.ui.Widget::removeFromParent()();
		});
	}-*/;
	@Override
	public void removeWidget(final Widget widget) {
		final Widget panel = (Widget) ((IVkWidget) widget).getVkParent();
		final int top = VkDesignerUtil.getOffsetTop(widget.getElement());
		final int left = VkDesignerUtil.getOffsetLeft(widget.getElement());
		int index = -1;
		Widget parent = widget.getParent();
		if(parent instanceof IndexedPanel.ForIsWidget)
			index = ((IndexedPanel.ForIsWidget)parent).getWidgetIndex(widget);
		final int widgetIndex = index;
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				VkStateHelper.getInstance().getSnapHelper().removeFromSnappableWidgets(widget);
				widget.removeFromParent();
			}}, new Command(){
					@Override
					public void execute() {
						if(widgetIndex > -1)
							VkStateHelper.getInstance().getEngine().addWidget(widget, (IVkPanel)panel, top, left, widgetIndex);
						else
							VkStateHelper.getInstance().getEngine().addWidget(widget, (IVkPanel)panel, top, left);
					}});
		VkStateHelper.getInstance().getToolbarHelper().hideToolbar();
	}
	@Override
	public Widget addWidget(String widgetName, IVkPanel panelWidget) {
		return addWidget(widgetName, panelWidget, 0, 0);
	}
	@Override
	public void addWidget(Widget widget, IVkPanel invokingWidget) {
		addWidget(widget, invokingWidget, 0, 0);
	}
	@Override
	public Widget addWidget(final String widgetName, final IVkPanel panelWidget, final int top, final int left) {
		final Widget widget = getWidget(widgetName);
		UndoHelper.getInstance().doCommand(new Command(){
			@Override
			public void execute() {
				if(widget != null)
					addWidget(widget, (IVkPanel)panelWidget, top, left);
			}}, new Command(){
					@Override
					public void execute() {
						if(widget != null)
							widget.removeFromParent();
					}});
		if(panelWidget instanceof AbsolutePanel)
			VkStateHelper.getInstance().getMoveHelper().makeMovable((IVkWidget) widget);
		return widget;
	}
	@Override
	public void addWidget(Widget widget, IVkPanel invokingWidget, int top, int left) {
		placeAddedElement(widget.getElement(), invokingWidget, top, left);
		if((widget instanceof PopupPanel))
			((PopupPanel)widget).center();
		else
			invokingWidget.add(widget);
	}
	@Override
	public void addWidget(Widget widget, IVkPanel invokingWidget, int top, int left, int beforeIndex) {
		placeAddedElement(widget.getElement(), invokingWidget, top, left);
		if(!VkStateHelper.getInstance().isDesignerMode() && (widget instanceof PopupPanel))
			((PopupPanel)widget).center();
		else if (invokingWidget instanceof IndexedPanel.ForIsWidget)
			((InsertPanel.ForIsWidget)invokingWidget).insert(widget, beforeIndex);
		else
			invokingWidget.add(widget);
	}
	private void placeAddedElement(Element element, IVkPanel invokingWidget, int top, int left) {
		if(invokingWidget instanceof AbsolutePanel)	{
			DOM.setStyleAttribute(element, "position", "absolute");
			DOM.setStyleAttribute(element, "top", top + "px");
			DOM.setStyleAttribute(element, "left", left + "px");
		} else if(DOM.getStyleAttribute(element, "position").equals("absolute"))
			DOM.setStyleAttribute(element, "position", "");
	}
	@Override
	public List<String> getOperationsList(Widget invokingWidget) {
		List<String> operationsList = new ArrayList<String>();
		if(!(invokingWidget instanceof VkMainDrawingPanel))	{
			operationsList.add(IEngine.DELETE);
			operationsList.add(IEngine.MOVE);
			operationsList.add(IEngine.RESIZE);
			operationsList.add(IEngine.COPY_STYLE);
			operationsList.add(IEngine.PASTE_STYLE);
			operationsList.add(IEngine.CUT);
			operationsList.add(IEngine.COPY);
		}
		if(invokingWidget instanceof IVkPanel)
			operationsList.add(PASTE);
		if((invokingWidget instanceof VkMainDrawingPanel)) {
			operationsList.add(IEngine.SAVE);
			operationsList.add(IEngine.LOAD);
			operationsList.add(IEngine.CLEAR);
		}
		return operationsList;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		optionList.add(ADD_CLASS);
		optionList.add(REMOVE_CLASS);
		if(!(invokingWidget instanceof VkMainDrawingPanel))	{
			optionList.add(TOOL_TIP);
			optionList.add(FILL_PARENT);
		}
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
		/*if(invokingWidget instanceof HasVkHistoryToken)
			optionList.add(HasVkHistoryToken.NAME);*/
		if(invokingWidget instanceof HasVkAlternateText)
			optionList.add(HasVkAlternateText.NAME);
		if(invokingWidget instanceof HasVkListBoxMultiple)
			optionList.add(HasVkListBoxMultiple.NAME);
		if(invokingWidget instanceof HasVkAutoOpen)
			optionList.add(HasVkAutoOpen.NAME);
		if(invokingWidget instanceof HasVkInitiallyShowing)
			optionList.add(HasVkInitiallyShowing.NAME);
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
		if(invokingWidget instanceof HasVkDoubleClickHandler)
			optionList.add(HasVkDoubleClickHandler.NAME);
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
	@Override
	public List<String> getWidgetsList(Widget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		if(invokingWidget instanceof HasVkTextBox)
			optionList.add(VkTextBox.NAME);
		if(invokingWidget instanceof HasVkButton)
			optionList.add(VkButton.NAME);
		if(invokingWidget instanceof HasVkLabel)
			optionList.add(VkLabel.NAME);
		if(invokingWidget instanceof HasVkTextArea)
			optionList.add(VkTextArea.NAME);
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
		if(invokingWidget instanceof HasVkDateBox)
			optionList.add(VkDateBox.NAME);
		return optionList;
	}
	@Override
	public List<String> getPanelsList(IVkWidget invokingWidget) {
		List<String> optionList = new ArrayList<String>();
		if(invokingWidget instanceof IVkPanel) {
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
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(ADD_CLASS))
			showAddClassNameDialog(invokingWidget);
		if(attributeName.equals(REMOVE_CLASS))
			showRemoveClassNameDialog(invokingWidget);
		if(attributeName.equals(TOOL_TIP))
			showAddTitleDialog(invokingWidget);
		if(attributeName.equals(FILL_PARENT))
			VkDesignerUtil.fillParent(invokingWidget);
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
		else if(attributeName.equals(HasVkInitiallyShowing.NAME))
			showShowingDialog((HasVkInitiallyShowing) invokingWidget);
		else if(attributeName.equals(HasVkTabHeaderText.NAME))
			showAddTabHeaderTextDialog((HasVkTabHeaderText) invokingWidget);
		else if(attributeName.equals(HasVkTabHeaderHtml.NAME))
			showAddTabHeaderHtmlDialog((HasVkTabHeaderHtml) invokingWidget);
		else if(attributeName.equals(HasVkValue.NAME))
			showAddValueDialog((HasVkValue<?>) invokingWidget);
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
		else if(attributeName.equals(HasVkDoubleClickHandler.NAME))
			showEventHandlingDialog((HasVkDoubleClickHandler) invokingWidget);
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
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkLoadHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addLoadHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkShowRangeHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkShowRangeHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addShowRangeHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkHighlightHandlers invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkHighlightHandlers.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addHighlightHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkValueChangeHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkValueChangeHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addValueChangeHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkInitializeHandlers invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkInitializeHandlers.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addInitializeHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkSelectionHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkSelectionHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addSelectionHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkBeforeSelectionHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkBeforeSelectionHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addBeforeSelectionHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkScrollHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkScrollHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addScrollHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkSubmitCompleteHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkSubmitCompleteHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addSubmitCompleteHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkSubmitHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkSubmitHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addSubmitHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkCloseHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkCloseHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addCloseHandler(js);
			}
		});
		
	}
	private void showEventHandlingDialog(final HasVkOpenHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkOpenHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addOpenHandler(js);
			}
		});
		
	}
	private void showEventHandlingDialog(final HasVkKeyPressHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkKeyPressHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addKeyPressHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyUpHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkKeyUpHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addKeyUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkKeyDownHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkKeyDownHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addKeyDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseMoveHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseMoveHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseMoveHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseWheelHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseWheelHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseWheelHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOutHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseOutHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseOutHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseOverHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseOverHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseOverHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseUpHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseUpHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseUpHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkMouseDownHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkMouseDownHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addMouseDownHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkClickHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkClickHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addClickHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkDoubleClickHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkDoubleClickHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addDoubleClickHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkFocusHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkFocusHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addFocusHandler(js);
			}
		});
	}
	private void showEventHandlingDialog(final HasVkBlurHandler invokingWidget) {
		VkDesignerUtil.showEventRegistrationDialog(invokingWidget, HasVkBlurHandler.NAME, new IDialogCallback(){
			@Override
			public void save(String js) {
				invokingWidget.addBlurHandler(js);
			}
		});
	}
	private void showRemoveClassNameDialog(final Widget invokingWidget) {
		final ListBox listBox = new ListBox(false);
		String[] styleNames = invokingWidget.getStyleName().replaceAll("vk-selectedWidget","").split("\\s+");
		for(int i = 0, len = styleNames.length; i < len; i++)
			if(styleNames[i].trim().length() > 0)
				listBox.addItem(styleNames[i], styleNames[i]);
		listBox.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose true to make widget enabled", listBox, new IDialogCallback() {
			@Override
			public void save(String text) {
				invokingWidget.removeStyleName(text);
			}
		});
	}
	private void showAddClassNameDialog(final Widget invokingWidget)
	{
		TextBox addTextTa = new TextBox();
		addTextTa.setWidth("300px");
		VkDesignerUtil.showAddTextAttributeDialog("Please add class name below", addTextTa,new IDialogCallback() {
			@Override
			public void save(String text) {
				if(text.indexOf(" ") == -1)
					invokingWidget.addStyleName(text.trim());
				else
					Window.alert("Class names should not contain white space");
			}
		});
	}
	private void showAddTitleDialog(final Widget invokingWidget)
	{
		TextBox addTextTa = new TextBox();
		addTextTa.setValue(invokingWidget.getTitle());
		addTextTa.setWidth("300px");
		VkDesignerUtil.showAddTextAttributeDialog("Please add title below", addTextTa,new IDialogCallback() {
			@Override
			public void save(String text) {
					invokingWidget.setTitle(text);
			}
		});
	}
	private void showAddTextDialog(final HasVkText invokingWidget) {
		TextBox addTextTb = new TextBox();
		addTextTb.setWidth("300px");
		addTextTb.setText(invokingWidget.getText());
		VkDesignerUtil.showAddTextAttributeDialog("Please add text below", addTextTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getText();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setText(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setText(prior);						
					}});
			}
		});
	}
	private void showAddHTMLDialog(final HasVkHtml invokingWidget) {
		TextArea addTextTa = new TextArea();
		addTextTa.setSize("300px", "100px");
		addTextTa.setText(invokingWidget.getHTML());
		VkDesignerUtil.showAddTextAttributeDialog("Please add html below", addTextTa, new IDialogCallback() {
			@Override
			public void save(final String html) {
				final String prior = invokingWidget.getHTML();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setHTML(html);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setHTML(prior);				
					}});
			}
		});
	}
	private void showAddAccessKeyDialog(final HasVkAccessKey invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("300px");
		addTextTb.setMaxLength(1);
		if(invokingWidget.getAccessKey() > 0)
			addTextTb.setText(Character.toString(invokingWidget.getAccessKey()));
		VkDesignerUtil.showAddTextAttributeDialog("Please add access key below", addTextTb, new IDialogCallback() {
			@Override
			public void save(String text) {
				String charString = text.trim();
				if(charString.length() > 0)
					invokingWidget.setAccessKey(charString.charAt(0));
			}
		});
	}
	private void showAddTabIndexDialog(final HasVkTabIndex invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("300px");
		addTextTb.setText(Integer.toString(invokingWidget.getTabIndex()));
		VkDesignerUtil.showAddTextAttributeDialog("Please add tabindex below", addTextTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final int prior = invokingWidget.getTabIndex();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						try{
							invokingWidget.setTabIndex(Integer.parseInt(text));
						} catch(NumberFormatException e) {
							Window.alert("TabIndex cannot be non-numeric");
						}
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setTabIndex(prior);
					}});
			}
		});
	}
	private void showChooseEnabledDialog(final HasVkEnabled invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("200px");
		if(invokingWidget.isEnabled())
			listBox.setSelectedIndex(0);
		else
			listBox.setSelectedIndex(1);
		VkDesignerUtil.showAddListDialog("Please choose true to make widget enabled", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.isEnabled();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setEnabled(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						
						invokingWidget.setEnabled(prior);
					}});
			}
		});
	}
	private void showChooseWordWrapDialog(final HasVkWordWrap invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("True", "true");
		listBox.addItem("False", "false");
		listBox.setWidth("200px");
		listBox.setSelectedIndex(invokingWidget.getWordWrap() ? 0 : 1);
		VkDesignerUtil.showAddListDialog("Please choose true to make text wrap", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.getWordWrap();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setWordWrap(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						
						invokingWidget.setWordWrap(prior);
					}});
			}
		});
	}
	private void showChooseDirectionDialog(final HasVkDirection invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Right To Left", "RTL");
		listBox.addItem("Left To Right", "LTR");
		listBox.addItem("Default", "DEFAULT");
		listBox.setWidth("200px");
		String directionString = invokingWidget.getDirectionString();
		if(directionString.equals("RTL"))
			listBox.setSelectedIndex(0);
		else if(directionString.equals("LTR"))
			listBox.setSelectedIndex(1);
		else
			listBox.setSelectedIndex(2);
		VkDesignerUtil.showAddListDialog("Please set text direction", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getDirectionString();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setDirection(text);
					}}, new Command(){
					@Override
					public void execute() {
						
						invokingWidget.setDirection(prior);
					}});
			}
		});
	}
	private void showAddMaxLengthDialog(final HasVkMaxLength invokingWidget) {
		final TextBox addTextTb = new TextBox();
		addTextTb.setWidth("300px");
		addTextTb.setText(Integer.toString(invokingWidget.getMaxLength()));
		VkDesignerUtil.showAddTextAttributeDialog("Please add maxlength below (-1 to remove)", addTextTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final int prior = invokingWidget.getMaxLength();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						try{
							int maxLength = Integer.parseInt(text);
							if(maxLength >= 0)
								invokingWidget.setMaxLength(maxLength);
							else
								invokingWidget.removeMaxLength();
						} catch(NumberFormatException e) {
							Window.alert("MaxLength cannot be non-numeric");
						}
					}}, new Command(){
					@Override
					public void execute() {
						if(prior >= 0)
							invokingWidget.setMaxLength(prior);
						else
							invokingWidget.removeMaxLength();
					}});
			}
		});
	}
	private void showChooseHorizontalAlignmentDialog(final HasVkHorizontalAlignment invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Left", "left");
		listBox.addItem("Center", "center");
		listBox.addItem("Right", "right");
		listBox.setWidth("200px");
		if(invokingWidget.getHorizontalAlignmentString() != null) {
			if(invokingWidget.getHorizontalAlignmentString().equals("left"))
				listBox.setSelectedIndex(0);
			else if(invokingWidget.getHorizontalAlignmentString().equals("center"))
				listBox.setSelectedIndex(1);
			else
				listBox.setSelectedIndex(2);
		}
		VkDesignerUtil.showAddListDialog("Please choose widget's horizontal alignment", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getHorizontalAlignmentString();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setHorizontalAlignment(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setHorizontalAlignment(prior);
					}});
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
		listBox.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose true make widget animated", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.isAnimationEnabled();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setAnimationEnabled(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setAnimationEnabled(prior);
					}});
			}
		});
	}
	private void showChooseVerticalAlignmentDialog(final HasVkVerticalAlignment invokingWidget) {
		final ListBox listBox = new ListBox(false);
		listBox.addItem("Top", "top");
		listBox.addItem("Middle", "middle");
		listBox.addItem("Bottom", "bottom");
		listBox.setWidth("200px");
		if(invokingWidget.getVerticalAlignmentString() != null) {
			if(invokingWidget.getVerticalAlignmentString().equals("top"))
				listBox.setSelectedIndex(0);
			else if(invokingWidget.getVerticalAlignmentString().equals("middel"))
				listBox.setSelectedIndex(1);
			else
				listBox.setSelectedIndex(2);
		}
		VkDesignerUtil.showAddListDialog("Please choose widget's vertical alignment", listBox, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getVerticalAlignmentString();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setVerticalAlignment(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setVerticalAlignment(prior);
					}});
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
		methodLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose form method", methodLb, new IDialogCallback() {
			@Override
			public void save(final String method) {
				final String prior = invokingWidget.getMethod();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setMethod(method);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setMethod(prior);
					}});
			}
		});
	}
	private void showAddUrlDialog(final HasVkUrl invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(((HasVkUrl) invokingWidget).getUrl());
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getUrl());
		VkDesignerUtil.showAddTextAttributeDialog("Please add url below", actionTb, new IDialogCallback() {
			@Override
			public void save(final String action) {
				final String prior = invokingWidget.getUrl();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setUrl(action);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setUrl(prior);
					}});
			}
		});
	}
	private void showAddImageUrlDialog(final HasVkImageUrl invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(((HasVkImageUrl) invokingWidget).getImageUrl());
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getImageUrl());
		VkDesignerUtil.showAddTextAttributeDialog("Please add url below", actionTb, new IDialogCallback() {
			@Override
			public void save(final String action) {
				final String prior = invokingWidget.getImageUrl();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setImageUrl(action);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setImageUrl(prior);
					}});
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
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose form method", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.isAlwaysShowScrollBars();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setAlwaysShowScrollBars(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setAlwaysShowScrollBars(prior);
					}});
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
		targetTb.setWidth("300px");
		targetTb.setText(invokingWidget.getTarget());
		targetTb.setSuggestionWidth(100);
		VkDesignerUtil.showAddAutoCompleteTextDialog("Please provide Form Target", targetTb, new IDialogCallback() {
			@Override
			public void save(final String target) {
				final String prior = invokingWidget.getTarget();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setTarget(target);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setTarget(prior);
					}});
			}
		});
	}
	private void showAddNumberedWidgetDialog(final HasVkSwitchNumberedWidget invokingWidget) {
		final ListBox scrollLb = new ListBox();
		scrollLb.setWidth("200px");
		for(int i = 0, len = invokingWidget.getWidgetCount(); i < len; i++)
			scrollLb.addItem(Integer.toString(i), Integer.toString(i));
		VkDesignerUtil.showAddListDialog("Please select widget number to show below", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String number) {
				final int prior = invokingWidget.getCurrentlyShowingWidget();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.showWidget(Integer.parseInt(number));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.showWidget(prior);
					}});
			}
		});
	}
	private void showAddNameDialog(final HasVkName invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getName());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide name of widget", actionTb, new IDialogCallback() {
			@Override
			public void save(final String name) {
				final String prior = invokingWidget.getName();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setName(name);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setName(prior);
					}});
			}
		});
	}
	private void showAddCaptionTextDialog(final HasVkCaptionText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getCaptionText());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getCaptionText();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setCaptionText(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setCaptionText(prior);
					}});
			}
		});
	}
	private void showAddCaptionHtmlDialog(final HasVkCaptionHtml invokingWidget) {
		final TextArea actionTb = new TextArea();
		actionTb.setSize("300px","100px");
		actionTb.setText(invokingWidget.getCaptionHtml());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IDialogCallback() {
			@Override
			public void save(final String html) {
				final String prior = invokingWidget.getCaptionHtml();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setCaptionHtml(html);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setCaptionHtml(prior);
					}});
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
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose Auto Hide Setting", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.isAutoHideEnabled();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setAutoHideEnabled(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setAutoHideEnabled(prior);
					}});
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
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose form method", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final boolean prior = invokingWidget.isGlassEnabled();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setGlassEnabled(Boolean.valueOf(text));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setGlassEnabled(prior);
					}});
			}
		});
	}
	private void showAddGlassStyleDialog(final HasVkGlassStyle invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getGlassStyleName());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide stylename of the glass", actionTb, new IDialogCallback() {
			@Override
			public void save(final String name) {
				final String prior = invokingWidget.getGlassStyleName();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setGlassStyleName(name);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setGlassStyleName(prior);
					}});
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
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose form method", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String value) {
				final boolean prior = invokingWidget.isModal();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setModal(Boolean.valueOf(value));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setModal(prior);
					}});
			}
		});
	}
	private void showFormEncodingDialog(final HasVkFormEncoding invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getEncoding());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide Form encoding", actionTb, new IDialogCallback() {
			@Override
			public void save(final String encoding) {
				final String prior = invokingWidget.getEncoding();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setEncoding(encoding);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setEncoding(prior);
					}});
			}
		});
	}
	private void showHistoryTokenDialog(final HasVkHistoryToken invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getTargetHistoryToken());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide history token", actionTb, new IDialogCallback() {
			@Override
			public void save(final String token) {
				final String prior = invokingWidget.getTargetHistoryToken();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setTargetHistoryToken(token);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setTargetHistoryToken(prior);
					}});
			}
		});
	}
	private void showAlternateTextDialog(final HasVkAlternateText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getAlt());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide caption of widget", actionTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getAlt();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setAlt(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setAlt(prior);
					}});
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
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose form method", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String value) {
				final boolean prior = invokingWidget.isMultipleEnabled();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setMultipleEnabled(Boolean.valueOf(value));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setMultipleEnabled(prior);
					}});
			}
		});
	}
	private void showAutoOpenDialog(final HasVkAutoOpen invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isAutoOpen())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose auto open option", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String value) {
				final boolean prior = invokingWidget.isAutoOpen();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setAutoOpen(Boolean.valueOf(value));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setAutoOpen(prior);
					}});
			}
		});
	}
	private void showShowingDialog(final HasVkInitiallyShowing invokingWidget) {
		ListBox scrollLb = new ListBox();
		scrollLb.addItem("True", "true");
		scrollLb.addItem("False", "false");
		if(invokingWidget.isInitiallyShowing())
			scrollLb.setSelectedIndex(0);
		else
			scrollLb.setSelectedIndex(1);
		scrollLb.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Please choose if initially dialog is showing", scrollLb, new IDialogCallback() {
			@Override
			public void save(final String value) {
				final boolean prior = invokingWidget.isInitiallyShowing();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setInitiallyShowing(Boolean.valueOf(value));
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setInitiallyShowing(prior);
					}});
			}
		});
	}
	private void showAddTabHeaderTextDialog(final HasVkTabHeaderText invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setWidth("300px");
		actionTb.setText(invokingWidget.getTabText());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide text for tab header", actionTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getTabText();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setTabText(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setTabText(prior);
					}});
			}
		});
	}
	private void showAddTabHeaderHtmlDialog(final HasVkTabHeaderHtml invokingWidget) {
		final TextArea actionTb = new TextArea();
		actionTb.setText(((HasVkTabHeaderText) invokingWidget).getTabText());
		actionTb.setSize("300px","100px");
		actionTb.setText(invokingWidget.getTabHTML());
		VkDesignerUtil.showAddTextAttributeDialog("Please provide html for tab header", actionTb, new IDialogCallback() {
			@Override
			public void save(final String html) {
				final String prior = invokingWidget.getTabHTML();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setTabHTML(html);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setTabHTML(prior);
					}});
			}
		});
	}
	private void showAddValueDialog(final HasVkValue<?> invokingWidget) {
		final TextBox actionTb = new TextBox();
		actionTb.setText(invokingWidget.getValue().toString());
		actionTb.setWidth("300px");
		VkDesignerUtil.showAddTextAttributeDialog("Please provide value of widget", actionTb, new IDialogCallback() {
			@Override
			public void save(final String text) {
				final String prior = invokingWidget.getValue().toString();
				UndoHelper.getInstance().doCommand(new Command(){
					@Override
					public void execute() {
						invokingWidget.setValue(text);
					}}, new Command(){
					@Override
					public void execute() {
						invokingWidget.setValue(prior);
					}});
			}
		});
		
	}
	private void showChoseListboxRenderModeDialog(final HasVkListBoxRenderMode invokingWidget) {
		final ListBox listBox = new ListBox();
		listBox.addItem("Drop Down","true");
		listBox.addItem("List","false");
		listBox.setWidth("200px");
		VkDesignerUtil.showAddListDialog("Pick a render mode", listBox, new IDialogCallback() {
				@Override
				public void save(final String value) {
					final boolean prior = invokingWidget.isDropDown();
					UndoHelper.getInstance().doCommand(new Command(){
						@Override
						public void execute() {
							invokingWidget.setDropDown(Boolean.parseBoolean(value));
						}}, new Command(){
						@Override
						public void execute() {
							invokingWidget.setDropDown(prior);
						}});
				}
			});
	}
}