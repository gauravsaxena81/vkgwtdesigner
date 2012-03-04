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

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

class StyleMenu extends Composite {
	private static StyleMenu styleMenu = new StyleMenu();
	
	private StyleMenu(){
		TabPanel tabPanel = new TabPanel();
		initWidget(tabPanel);
	}
	
	public static StyleMenu getInstance(){
		return styleMenu;
	}
	public void refreshStylePanel(Element invokingElement) {
		TabPanel styleTabPanel = (TabPanel)getWidget();
		styleTabPanel.clear();
		styleTabPanel.setPixelSize(500, 250);
		Panel panel = addDecorationPanel(invokingElement);
		styleTabPanel.add(panel, "Decorations");
		styleTabPanel.getTabBar().selectTab(0);
		panel.setSize("100%", "250px");
		panel = addFontPanel(invokingElement);
		styleTabPanel.add(panel, "Font & Text");
		panel.setSize("100%", "250px");
		panel = addColorPanel(invokingElement);
		styleTabPanel.add(panel, "Color & Background");
		panel.setSize("100%", "250px");
		panel = addMiscellaneousPanel(invokingElement);
		styleTabPanel.add(panel, "Miscellaneous");
		panel.setSize("100%", "250px");
	}
	
	private ScrollPanel addColorPanel(Element invokingElement) {
		ScrollPanel scrollColorHolderPanel = new ScrollPanel();				
		VerticalPanel colorVPanel = new VerticalPanel();
		scrollColorHolderPanel.add(colorVPanel);
		colorVPanel.setWidth("100%");
		colorVPanel.add(addStyleAttribute("Color","color", invokingElement));
		colorVPanel.add(addStyleAttribute("Background Color","backgroundColor", invokingElement));
		colorVPanel.add(addStyleAttribute("Background Image","backgroundImage", invokingElement));
		colorVPanel.add(addStyleAttribute("Background Position","backgroundPosition", invokingElement));
		colorVPanel.add(addStyleAttribute("Background Repeat","backgroundRepeat", invokingElement));
		colorVPanel.add(addStyleAttribute("Background Attachment","backgroundAttachment", invokingElement));
		return scrollColorHolderPanel;
	}
	private ScrollPanel addDecorationPanel(Element invokingElement) {
		ScrollPanel scrollDecorationHolderPanel = new ScrollPanel();				
		VerticalPanel decorationVPanel = new VerticalPanel();
		scrollDecorationHolderPanel.add(decorationVPanel);
		decorationVPanel.setWidth("100%");
		decorationVPanel.add(addStyleAttribute("Bottom Border","borderBottom", invokingElement));
		decorationVPanel.add(addStyleAttribute("Top Border","borderTop", invokingElement));
		decorationVPanel.add(addStyleAttribute("Left Border","borderLeft", invokingElement));
		decorationVPanel.add(addStyleAttribute("Right Border","borderRight", invokingElement));
		decorationVPanel.add(addStyleAttribute("Padding","padding", invokingElement));
		decorationVPanel.add(addStyleAttribute("Margin","margin", invokingElement));
		decorationVPanel.add(addStyleAttribute("Outline Color","outlineColor", invokingElement));
		decorationVPanel.add(addStyleAttribute("Outline Style","outlineStyle", invokingElement));
		decorationVPanel.add(addStyleAttribute("Outline Width","outlineWidth", invokingElement));
		return scrollDecorationHolderPanel;
	}
	private ScrollPanel addFontPanel(Element invokingElement) {
		ScrollPanel scrollFontHolderPanel = new ScrollPanel();
		VerticalPanel fontVPanel = new VerticalPanel();
		scrollFontHolderPanel.add(fontVPanel);
		fontVPanel.setWidth("100%");
		fontVPanel.add(addStyleAttribute("Font Size", "fontSize", invokingElement));
		fontVPanel.add(addStyleAttribute("Font Family","fontFamily", invokingElement));
		fontVPanel.add(addStyleAttribute("Font Stretch","fontStretch", invokingElement));
		fontVPanel.add(addStyleAttribute("Font Style","fontStyle", invokingElement));
		fontVPanel.add(addStyleAttribute("Font Variant","fontVariant", invokingElement));
		fontVPanel.add(addStyleAttribute("Font Weight","fontWeight", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Align","textAlign", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Decoration","textDecoration", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Decoration","textDecoration", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Indent","textIndent", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Shadow","textShadow", invokingElement));
		fontVPanel.add(addStyleAttribute("Text Transform","textTransform", invokingElement));
		fontVPanel.add(addStyleAttribute("Vertical Align","verticalAlign", invokingElement));
		fontVPanel.add(addStyleAttribute("Word Spacing","wordSpacing", invokingElement));
		fontVPanel.add(addStyleAttribute("Line Height","lineHeight", invokingElement));
		fontVPanel.add(addStyleAttribute("Letter Spacing","letterSpacing", invokingElement));
		fontVPanel.add(addStyleAttribute("Word Spacing","wordSpacing", invokingElement));
		return scrollFontHolderPanel;
	}
	private Panel addMiscellaneousPanel(Element invokingElement) {
		ScrollPanel scrollMiscellaneousHolderPanel = new ScrollPanel();				
		VerticalPanel miscellaneousVPanel = new VerticalPanel();
		scrollMiscellaneousHolderPanel.add(miscellaneousVPanel);
		miscellaneousVPanel.setWidth("100%");
		miscellaneousVPanel.add(addStyleAttribute("Top","top", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Left","left", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Width","width", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Height","height", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Cursor","cursor", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Display","display", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Visibility","visibility", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Overflow X","overflowX", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Overflow Y","overflowY", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Z Index","zIndex", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Opacity","opacity", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("Filter(IE)","filter", invokingElement));
		miscellaneousVPanel.add(addStyleAttribute("border-collapse(table only)","borderCollapse", invokingElement));
		return scrollMiscellaneousHolderPanel;
	}
	private Panel addStyleAttribute(String displayName, String attributeName, Element invokingElement) {
		HorizontalPanel styleAttribute = new HorizontalPanel();
		styleAttribute.setWidth("100%");
		styleAttribute.add(new InlineLabel(displayName));
		styleAttribute.setCellWidth(styleAttribute.getWidget(0), "50%");
		DOM.setStyleAttribute(styleAttribute.getElement(), "padding", "2px 0px");
		
		final TextBox styleAttributeTextBox = new TextBox();
		styleAttributeTextBox.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(event.getNativeKeyCode() == KeyCodes.KEY_DELETE)
					event.stopPropagation();
			}});
		DOM.setElementAttribute(styleAttributeTextBox.getElement(), "attributeName", attributeName);
		styleAttribute.add(styleAttributeTextBox);
		addChangeListenerToWidget(attributeName, styleAttributeTextBox, invokingElement);
		try{//IE throws an exception when it is queried about zIndex - GWT Bug 5548
			styleAttributeTextBox.setText(DOM.getStyleAttribute(invokingElement, attributeName));
		}catch(Exception e)	{
			if(invokingElement != null)
				styleAttributeTextBox.setText(getIEZindex(invokingElement));
		}
		return styleAttribute;
	}
	private void addChangeListenerToWidget(final String attribute, final TextBox textBox, final Element invokingElement) {
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				StyleToolbar.getInstance().setAttribute(attribute, textBox.getText());
			}});
	}


	private native String getIEZindex(Element invokingElement) /*-{
		return invokingElement.style.zIndex + '';
	}-*/;
}
