package com.vk.gwt.designer.client.engine;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.kiouri.sliderbar.client.event.BarValueChangedEvent;
import com.kiouri.sliderbar.client.event.BarValueChangedHandler;
import com.kiouri.sliderbar.client.solution.simplehorizontal.SliderBarSimpleHorizontal;
import com.kiouri.sliderbar.client.view.SliderBarHorizontal;
import com.vk.gwt.designer.client.Panels.VkVerticalPanel;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.engine.IPanel;
import com.vk.gwt.designer.client.api.engine.VkAbstractWidgetEngine;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkVerticalPanelEngine extends VkAbstractWidgetEngine<VkVerticalPanel>{
	private static final String CHANGE_CELL_HEIGHT = "Change Cell Height";
	@Override
	public VkVerticalPanel getWidget() {
		VkVerticalPanel widget = new VkVerticalPanel();
		init(widget);
		return widget;
	}
	@Override
	public List<String> getAttributesList(Widget invokingWidget)
	{
		List<String> attributesList = VkDesignerUtil.getEngine().getAttributesList(invokingWidget);
		attributesList.add(CHANGE_CELL_HEIGHT);
		return attributesList;
	}
	@Override
	public void applyAttribute(String attributeName, Widget invokingWidget) {
		if(attributeName.equals(HasVkHorizontalAlignment.NAME))
			((VkVerticalPanel)invokingWidget).setHorizontalAlignment(attributeName);
		else if(attributeName.equals(HasVkVerticalAlignment.NAME))
			((VkVerticalPanel)invokingWidget).setVerticalAlignment(attributeName);
		else if(attributeName.equals(CHANGE_CELL_HEIGHT))
			showChangeHeightDialog((VkVerticalPanel) invokingWidget);
		else
			VkDesignerUtil.getEngine().applyAttribute(attributeName, invokingWidget);
	}
	private void showChangeHeightDialog(final VkVerticalPanel invokingWidget) {
		final DialogBox origDialog = new DialogBox();
		origDialog.setText("Change height of widget holders");
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex", Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		dialog.add(new Label("Please choose the Widget :"));
		final ListBox widgetIndexLb = new ListBox();
		for (Iterator<Widget> iterator = invokingWidget.iterator(); iterator.hasNext();) {
			Widget next = iterator.next();
			widgetIndexLb.addItem(((IVkWidget)next).getWidgetName() + " - at index - " + invokingWidget.getWidgetIndex(next));
		}
		final SliderBarHorizontal sliderBar = new SliderBarSimpleHorizontal(invokingWidget.getOffsetHeight(), "300px", false);
		widgetIndexLb.addChangeHandler(new ChangeHandler(){
			@Override
			public void onChange(ChangeEvent event) {
				sliderBar.setValue(invokingWidget.getWidget(widgetIndexLb.getSelectedIndex()).getElement().getParentElement().getOffsetHeight());
			}});
		widgetIndexLb.setWidth("300px");
		dialog.add(widgetIndexLb);
		dialog.add(new Label("Drag to change height:"));
		dialog.add(sliderBar);
		sliderBar.addBarValueChangedHandler(new BarValueChangedHandler() {
			@Override
			public void onBarValueChanged(BarValueChangedEvent event) {
				DOM.setStyleAttribute((com.google.gwt.user.client.Element) invokingWidget.getWidget(widgetIndexLb.getSelectedIndex()).getElement().getParentElement(), "height", event.getValue() + "px");
			}
		});
		sliderBar.setValue(invokingWidget.getWidget(0).getElement().getParentElement().getOffsetHeight());
		sliderBar.setMinMarkStep(50);
		Button okButton = new Button("OK");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialog.add(okButton);
		okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				origDialog.hide();
			}
		});
		origDialog.center();
	}
	@Override
	public Widget deepClone(Widget sourceWidget, Widget targetWidget) {
		boolean isVkDesignerMode = VkDesignerUtil.isDesignerMode;
		VkDesignerUtil.isDesignerMode = false;
		((IVkWidget)sourceWidget).clone(targetWidget);
		VkDesignerUtil.getEngineMap().get(((IVkWidget)targetWidget).getWidgetName()).copyAttributes(sourceWidget, targetWidget);
		VkDesignerUtil.isDesignerMode = isVkDesignerMode;
		if(sourceWidget instanceof IPanel && targetWidget instanceof IPanel)
		{
			Iterator<Widget> widgets = ((IPanel)sourceWidget).iterator();
			while(widgets.hasNext())
			{
				Widget currentWidget = widgets.next();
				if(currentWidget instanceof IVkWidget) {
					Element tdParentElement = currentWidget.getElement().getParentElement();
					setHorizontalAlignment(tdParentElement, (VkVerticalPanel) targetWidget, DOM.getElementAttribute((com.google.gwt.user.client.Element) tdParentElement, "align"));
					setVerticalAlignment(tdParentElement, (VkVerticalPanel) targetWidget, DOM.getStyleAttribute((com.google.gwt.user.client.Element) tdParentElement, "verticalAlign"));
					Widget tdWidget = VkDesignerUtil.getEngine().getWidget(((IVkWidget)currentWidget).getWidgetName());
					VkDesignerUtil.getEngine().addWidget(VkDesignerUtil.getEngineMap().get(((IVkWidget) currentWidget).getWidgetName()).deepClone(currentWidget, tdWidget), (IPanel)targetWidget);
					String height = DOM.getStyleAttribute((com.google.gwt.user.client.Element) tdParentElement, "height");
					if(height != null && !height.isEmpty())
						 DOM.setStyleAttribute((com.google.gwt.user.client.Element) tdWidget.getElement().getParentElement(), "height", height);
				}
			}
		}
		return targetWidget;
	}
	private void setVerticalAlignment(Element tdParentElement, VkVerticalPanel targetWidget, String vAlign) {
		if(vAlign != null){
			if(vAlign.equals("top"))
				targetWidget.setVerticalAlignment(VkVerticalPanel.ALIGN_TOP);
			else if(vAlign.equals("middle"))
				targetWidget.setVerticalAlignment(VkVerticalPanel.ALIGN_MIDDLE);
			else
				targetWidget.setVerticalAlignment(VkVerticalPanel.ALIGN_BOTTOM);
		}
	}
	private void setHorizontalAlignment(Element tdParentElement, VkVerticalPanel targetWidget, String align) {
		if(align != null){
			if(align.equals("left"))
				targetWidget.setHorizontalAlignment(VkVerticalPanel.ALIGN_LEFT);
			else if(align.equals("center"))
				targetWidget.setHorizontalAlignment(VkVerticalPanel.ALIGN_CENTER);
			else
				targetWidget.setHorizontalAlignment(VkVerticalPanel.ALIGN_RIGHT);
		}
	}
	@Override
	public void copyAttributes(Widget widgetSource, Widget widgetTarget)
	{
		DOM.setElementAttribute(widgetTarget.getElement(), "style", DOM.getElementAttribute(widgetSource.getElement(), "style"));
		widgetTarget.setStyleName(widgetSource.getStyleName());
		widgetTarget.setTitle(widgetSource.getTitle());
	}
	@Override
	public String serialize(IVkWidget widget)
	{
		StringBuffer buffer = new StringBuffer("{");
		buffer.append("widgetName:'").append(widget.getWidgetName()).append("'");
		buffer.append(",style:'").append(VkDesignerUtil.getCssText((Widget) widget)).append("'");
		serializeAttributes(buffer, (Widget) widget);
		buffer.append(",attrs:[");
		Iterator<Widget> widgetList = ((IPanel)widget).iterator();
		while(widgetList.hasNext())
		{
			Widget child = widgetList.next();
			com.google.gwt.user.client.Element tdParentElement = (com.google.gwt.user.client.Element) child.getElement().getParentElement();
			buffer.append("{align:'").append(DOM.getElementAttribute(tdParentElement, "align")).append("'")
			.append(",vAlign:'").append(DOM.getStyleAttribute(tdParentElement, "verticalAlign")).append("'")
			.append(",height:'").append(DOM.getStyleAttribute(tdParentElement, "height")).append("'},");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("],children:[");
		widgetList = ((IPanel)widget).iterator();
		while(widgetList.hasNext())
		{
			Widget child = widgetList.next();
			if(child instanceof IVkWidget)
				buffer.append(VkDesignerUtil.getEngineMap().get(((IVkWidget)child).getWidgetName()).serialize((IVkWidget) child)).append(",");
		}
		if(buffer.charAt(buffer.length() - 1) == ',')
			buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("]}");
		return buffer.toString();
	}
	@Override
	protected void serializeAttributes(StringBuffer buffer, Widget widgetSource)
	{
		if(!widgetSource.getStyleName().isEmpty())
			buffer.append(",className:'" + widgetSource.getStyleName() + "'");
		if(!widgetSource.getTitle().isEmpty())
			buffer.append(",title:'" + widgetSource.getTitle() + "'");
		if(!widgetSource.getElement().getId().isEmpty())
			buffer.append(",id:'" + widgetSource.getElement().getId() + "'");
	}
	@Override
	public void buildWidget(JSONObject jsonObj, Widget parent) {
		JSONArray childrenArray = jsonObj.put("children", null).isArray();
		JSONArray attrs = jsonObj.put("attrs", null).isArray();
		addAttributes(jsonObj, parent);
		for(int i = 0; i < childrenArray.size(); i++) {
			JSONObject childObj = childrenArray.get(i).isObject();
			if(childObj == null)
				return;
			JSONString widgetName = childObj.get("widgetName").isString();
			Widget widget = VkDesignerUtil.getEngine().getWidget(widgetName.stringValue());
			JSONObject attr = attrs.get(i).isObject();
			setHorizontalAlignment(widget.getElement().getParentElement(), (VkVerticalPanel) parent, attr.get("align").isString().stringValue());
			if(attr.get("vAlign").isNull() == null)
				setVerticalAlignment(widget.getElement().getParentElement(), (VkVerticalPanel) parent, attr.get("vAlign").isString().stringValue());
			else
				setVerticalAlignment(widget.getElement().getParentElement(), (VkVerticalPanel) parent, "top");
			VkDesignerUtil.getEngine().addWidget(widget, ((IPanel)parent));
			DOM.setStyleAttribute((com.google.gwt.user.client.Element) widget.getElement().getParentElement(), "height", attr.get("height").isString().stringValue());
			VkDesignerUtil.getEngineMap().get(((IVkWidget)widget).getWidgetName()).buildWidget(childObj, widget);
		}
	}
	@Override
	protected void addAttributes(JSONObject childObj, Widget widget) {
		JSONString attributeStringObj;
		JSONValue attributeJsObj = childObj.get("style");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "style", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("title");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "title", attributeStringObj.stringValue());
		attributeJsObj = childObj.get("id");
		if(attributeJsObj != null && (attributeStringObj = attributeJsObj.isString()) != null)
			DOM.setElementAttribute(widget.getElement(), "id", attributeStringObj.stringValue()); 
		attributeJsObj = childObj.get("className");
	}
}
