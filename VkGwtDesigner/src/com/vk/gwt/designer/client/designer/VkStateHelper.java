package com.vk.gwt.designer.client.designer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.engine.IWidgetEngine;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkCaptionPanel.VkCaptionPanel;
import com.vk.gwt.designer.client.ui.panel.vkCaptionPanel.VkCaptionPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkDeckPanel.VkDeckPanel;
import com.vk.gwt.designer.client.ui.panel.vkDeckPanel.VkDeckPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedStackPanel.VkDecoratedStackPanel;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedStackPanel.VkDecoratedStackPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedTabPanel.VkDecoratedTabPanel;
import com.vk.gwt.designer.client.ui.panel.vkDecoratedTabPanel.VkDecoratedTabPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkDisclosurePanel.VkDisclosurePanel;
import com.vk.gwt.designer.client.ui.panel.vkDisclosurePanel.VkDisclosurePanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkDockPanel.VkDockPanel;
import com.vk.gwt.designer.client.ui.panel.vkDockPanel.VkDockPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkFlowPanel.VkFlowPanel;
import com.vk.gwt.designer.client.ui.panel.vkFlowPanel.VkFlowPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkFocusPanel.VkFocusPanel;
import com.vk.gwt.designer.client.ui.panel.vkFocusPanel.VkFocusPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkFormPanel.VkFormPanel;
import com.vk.gwt.designer.client.ui.panel.vkFormPanel.VkFormPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalPanel.VkHorizontalPanel;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalPanel.VkHorizontalPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalSplitPanel.VkHorizontalSplitPanel;
import com.vk.gwt.designer.client.ui.panel.vkHorizontalSplitPanel.VkHorizontalSplitPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkHtmlPanel.VkHtmlPanel;
import com.vk.gwt.designer.client.ui.panel.vkHtmlPanel.VkHtmlPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkPopUpPanel.VkPopUpPanel;
import com.vk.gwt.designer.client.ui.panel.vkPopUpPanel.VkPopUpPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkScrollPanel.VkScrollPanel;
import com.vk.gwt.designer.client.ui.panel.vkScrollPanel.VkScrollPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkSimplePanel.VkSimplePanel;
import com.vk.gwt.designer.client.ui.panel.vkSimplePanel.VkSimplePanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkStackPanel.VkStackPanel;
import com.vk.gwt.designer.client.ui.panel.vkStackPanel.VkStackPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkTabPanel.VkTabPanel;
import com.vk.gwt.designer.client.ui.panel.vkTabPanel.VkTabPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkVerticalPanel.VkVerticalPanel;
import com.vk.gwt.designer.client.ui.panel.vkVerticalPanel.VkVerticalPanelEngine;
import com.vk.gwt.designer.client.ui.panel.vkVerticalSplitPanel.VkVerticalSplitPanel;
import com.vk.gwt.designer.client.ui.panel.vkVerticalSplitPanel.VkVerticalSplitPanelEngine;
import com.vk.gwt.designer.client.ui.widget.button.vkButton.VkButton;
import com.vk.gwt.designer.client.ui.widget.button.vkButton.VkButtonEngine;
import com.vk.gwt.designer.client.ui.widget.button.vkPushButton.VkPushButton;
import com.vk.gwt.designer.client.ui.widget.button.vkPushButton.VkPushButtonEngine;
import com.vk.gwt.designer.client.ui.widget.button.vkResetButton.VkResetButton;
import com.vk.gwt.designer.client.ui.widget.button.vkResetButton.VkResetButtonEngine;
import com.vk.gwt.designer.client.ui.widget.button.vkSubmitButton.VkSubmitButton;
import com.vk.gwt.designer.client.ui.widget.button.vkSubmitButton.VkSubmitButtonEngine;
import com.vk.gwt.designer.client.ui.widget.button.vkToggleButton.VkToggleButton;
import com.vk.gwt.designer.client.ui.widget.button.vkToggleButton.VkToggleButtonEngine;
import com.vk.gwt.designer.client.ui.widget.label.vkHtml.VkHTML;
import com.vk.gwt.designer.client.ui.widget.label.vkHtml.VkHTMLEngine;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineHtml.VkInlineHTML;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineHtml.VkInlineHTMLEngine;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineLabel.VkInlineLabel;
import com.vk.gwt.designer.client.ui.widget.label.vkInlineLabel.VkInlineLabelEngine;
import com.vk.gwt.designer.client.ui.widget.label.vkLabel.VkLabel;
import com.vk.gwt.designer.client.ui.widget.label.vkLabel.VkLabelEngine;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal.VkMenuBarHorizontal;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarHorizontal.VkMenuBarHorizontalEngine;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical.VkMenuBarVertical;
import com.vk.gwt.designer.client.ui.widget.meunbar.vkMenuBarVertical.VkMenuBarVerticalEngine;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkDecoratedTabBar.VkDecoratedTabBar;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkDecoratedTabBar.VkDecoratedTabBarEngine;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkTabBar.VkTabBar;
import com.vk.gwt.designer.client.ui.widget.tabBar.vkTabBar.VkTabBarEngine;
import com.vk.gwt.designer.client.ui.widget.table.vkFlextable.VkFlexTable;
import com.vk.gwt.designer.client.ui.widget.table.vkFlextable.VkFlexTableEngine;
import com.vk.gwt.designer.client.ui.widget.table.vkGrid.VkGrid;
import com.vk.gwt.designer.client.ui.widget.table.vkGrid.VkGridEngine;
import com.vk.gwt.designer.client.ui.widget.text.vkPasswordTextBox.VkPasswordTextBox;
import com.vk.gwt.designer.client.ui.widget.text.vkPasswordTextBox.VkPasswordTextBoxEngine;
import com.vk.gwt.designer.client.ui.widget.text.vkTextArea.VkTextArea;
import com.vk.gwt.designer.client.ui.widget.text.vkTextArea.VkTextAreaEngine;
import com.vk.gwt.designer.client.ui.widget.text.vkTextBox.VkTextBox;
import com.vk.gwt.designer.client.ui.widget.text.vkTextBox.VkTextBoxEngine;
import com.vk.gwt.designer.client.ui.widget.vkAnchor.VkAnchor;
import com.vk.gwt.designer.client.ui.widget.vkAnchor.VkAnchorEngine;
import com.vk.gwt.designer.client.ui.widget.vkCheckbox.VkCheckbox;
import com.vk.gwt.designer.client.ui.widget.vkCheckbox.VkCheckboxEngine;
import com.vk.gwt.designer.client.ui.widget.vkDateBox.VkDateBox;
import com.vk.gwt.designer.client.ui.widget.vkDateBox.VkDateBoxEngine;
import com.vk.gwt.designer.client.ui.widget.vkDialogBox.VkDialogBox;
import com.vk.gwt.designer.client.ui.widget.vkDialogBox.VkDialogBoxEngine;
import com.vk.gwt.designer.client.ui.widget.vkFileUpload.VkFileUpload;
import com.vk.gwt.designer.client.ui.widget.vkFileUpload.VkFileUploadEngine;
import com.vk.gwt.designer.client.ui.widget.vkFrame.VkFrame;
import com.vk.gwt.designer.client.ui.widget.vkFrame.VkFrameEngine;
import com.vk.gwt.designer.client.ui.widget.vkHidden.VkHidden;
import com.vk.gwt.designer.client.ui.widget.vkHidden.VkHiddenEngine;
import com.vk.gwt.designer.client.ui.widget.vkImage.VkImage;
import com.vk.gwt.designer.client.ui.widget.vkImage.VkImageEngine;
import com.vk.gwt.designer.client.ui.widget.vkListBox.VkListBox;
import com.vk.gwt.designer.client.ui.widget.vkListBox.VkListBoxEngine;
import com.vk.gwt.designer.client.ui.widget.vkRadioButton.VkRadioButton;
import com.vk.gwt.designer.client.ui.widget.vkRadioButton.VkRadioButtonEngine;
import com.vk.gwt.designer.client.ui.widget.vkRichText.VkRichTextArea;
import com.vk.gwt.designer.client.ui.widget.vkRichText.VkRichTextAreaEngine;
import com.vk.gwt.designer.client.ui.widget.vkSuggestBox.VkSuggestBox;
import com.vk.gwt.designer.client.ui.widget.vkSuggestBox.VkSuggestBoxEngine;
import com.vk.gwt.designer.client.ui.widget.vkTree.VkTree;
import com.vk.gwt.designer.client.ui.widget.vkTree.VkTreeEngine;

public class VkStateHelper {
	private static VkStateHelper vkStateHelper = new VkStateHelper();
	private VkMenu vkMenu = new VkMenu();
	private VkLinkedHashMap engineMap = new VkLinkedHashMap();
	private boolean isDesignerMode = true;
	private boolean isLoadRunning = false;
	
	private VkEngine vkEngine = new VkEngine();
	
	private VkStateHelper(){
		setUpEngineMap();
	}
	
	public static VkStateHelper getInstance(){
		return vkStateHelper;
	}
	
	private void setUpEngineMap() {
		engineMap.put(VkButton.NAME, new VkButtonEngine());
		engineMap.put(VkTextBox.NAME, new VkTextBoxEngine());
		engineMap.put(VkLabel.NAME, new VkLabelEngine());
		engineMap.put(VkFrame.NAME, new VkFrameEngine());
		engineMap.put(VkCheckbox.NAME, new VkCheckboxEngine());
		engineMap.put(VkFileUpload.NAME, new VkFileUploadEngine());
		engineMap.put(VkFlexTable.NAME, new VkFlexTableEngine());
		engineMap.put(VkGrid.NAME, new VkGridEngine());
		engineMap.put(VkHTML.NAME, new VkHTMLEngine());
		engineMap.put(VkHidden.NAME, new VkHiddenEngine());
		engineMap.put(VkImage.NAME, new VkImageEngine());
		engineMap.put(VkListBox.NAME, new VkListBoxEngine());
		engineMap.put(VkMenuBarHorizontal.NAME, new VkMenuBarHorizontalEngine());
		engineMap.put(VkMenuBarVertical.NAME, new VkMenuBarVerticalEngine());
		engineMap.put(VkDialogBox.NAME, new VkDialogBoxEngine());
		engineMap.put(VkPushButton.NAME, new VkPushButtonEngine());
		engineMap.put(VkRadioButton.NAME, new VkRadioButtonEngine());
		engineMap.put(VkRichTextArea.NAME, new VkRichTextAreaEngine());
		engineMap.put(VkSuggestBox.NAME, new VkSuggestBoxEngine());
		engineMap.put(VkTabBar.NAME, new VkTabBarEngine());
		engineMap.put(VkTextArea.NAME, new VkTextAreaEngine());
		engineMap.put(VkToggleButton.NAME, new VkToggleButtonEngine());
		engineMap.put(VkTree.NAME, new VkTreeEngine());
		engineMap.put(VkPasswordTextBox.NAME, new VkPasswordTextBoxEngine());
		engineMap.put(VkAnchor.NAME, new VkAnchorEngine());
		engineMap.put(VkResetButton.NAME, new VkResetButtonEngine());
		engineMap.put(VkSubmitButton.NAME, new VkSubmitButtonEngine());
		engineMap.put(VkDecoratedTabBar.NAME, new VkDecoratedTabBarEngine());
		engineMap.put(VkInlineLabel.NAME, new VkInlineLabelEngine());
		engineMap.put(VkInlineHTML.NAME, new VkInlineHTMLEngine());
		engineMap.put(VkDateBox.NAME, new VkDateBoxEngine());
		
		engineMap.put(VkAbsolutePanel.NAME, new VkAbsolutePanelEngine());
		engineMap.put(VkVerticalPanel.NAME, new VkVerticalPanelEngine());
		engineMap.put(VkCaptionPanel.NAME, new VkCaptionPanelEngine());
		engineMap.put(VkDeckPanel.NAME, new VkDeckPanelEngine());
		engineMap.put(VkDisclosurePanel.NAME, new VkDisclosurePanelEngine());
		engineMap.put(VkDockPanel.NAME, new VkDockPanelEngine());
		engineMap.put(VkFlowPanel.NAME, new VkFlowPanelEngine());
		engineMap.put(VkFocusPanel.NAME, new VkFocusPanelEngine());
		engineMap.put(VkFormPanel.NAME, new VkFormPanelEngine());
		engineMap.put(VkHorizontalPanel.NAME, new VkHorizontalPanelEngine());
		engineMap.put(VkHorizontalSplitPanel.NAME, new VkHorizontalSplitPanelEngine());
		engineMap.put(VkHtmlPanel.NAME, new VkHtmlPanelEngine());
		engineMap.put(VkScrollPanel.NAME, new VkScrollPanelEngine());
		engineMap.put(VkStackPanel.NAME, new VkStackPanelEngine());
		engineMap.put(VkTabPanel.NAME, new VkTabPanelEngine());
		engineMap.put(VkVerticalSplitPanel.NAME, new VkVerticalSplitPanelEngine());
		engineMap.put(VkSimplePanel.NAME, new VkSimplePanelEngine());
		engineMap.put(VkPopUpPanel.NAME, new VkPopUpPanelEngine());
		engineMap.put(VkDecoratedStackPanel.NAME, new VkDecoratedStackPanelEngine());
		engineMap.put(VkDecoratedTabPanel.NAME, new VkDecoratedTabPanelEngine());
	}
	public Map<String, IWidgetEngine<? extends Widget>> getEngineMap() {
		return engineMap;
	}
	public VkMenu getMenu() {
		return vkMenu;
	}
	public void setMenu(VkMenu vkMenu) {
		this.vkMenu = vkMenu;
	}
	public VkEngine getEngine() {
		return vkEngine;
	}
	public void setEngine(VkEngine newVkEngine) {
		this.vkEngine = newVkEngine;
	}
	@SuppressWarnings("serial")
	private class VkLinkedHashMap extends LinkedHashMap<String, IWidgetEngine<? extends Widget>>{
		public VkLinkedHashMap(){
			super.put(VkMainDrawingPanel.NAME, new VkMainDrawingPanelEngine());
		}
		@Override
		public void clear(){
			super.clear();
			put(VkMainDrawingPanel.NAME, new VkMainDrawingPanelEngine());
		}
		@Override
		public IWidgetEngine<? extends Widget> remove(Object o){
			IWidgetEngine<? extends Widget> removedObject = super.remove(o);
			if(size() == 0)
				put(VkMainDrawingPanel.NAME, new VkMainDrawingPanelEngine());
			return removedObject;
		}
		@Override
		public IWidgetEngine<? extends Widget> put(String key, IWidgetEngine<? extends Widget> value){
			if(key.equals(VkMainDrawingPanel.NAME))
				throw new IllegalArgumentException("Cannot replace Main drawing panel. Please change the name of your widget");
			return super.put(key, value);
		}
	}
	public boolean isDesignerMode() {
		return isDesignerMode;
	}

	public void setDesignerMode(boolean isDesignerMode) {
		this.isDesignerMode = isDesignerMode;
	}

	public boolean isLoadRunning() {
		return isLoadRunning;
	}

	public void setLoadRunning(boolean isLoadRunning) {
		this.isLoadRunning = isLoadRunning;
	}
}
