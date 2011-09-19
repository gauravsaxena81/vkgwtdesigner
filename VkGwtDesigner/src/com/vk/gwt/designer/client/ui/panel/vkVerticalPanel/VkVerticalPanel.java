package com.vk.gwt.designer.client.ui.panel.vkVerticalPanel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkHorizontalAlignment;
import com.vk.gwt.designer.client.api.attributes.HasVkVerticalAlignment;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class VkVerticalPanel extends VerticalPanel implements IVkPanel, HasVkWidgets, HasVkHorizontalAlignment, HasVkVerticalAlignment {
	public static final String NAME = "Vertical Panel";

	@Override
	protected void add(Widget child, Element container) {
		// So that TD resizes with the widget inside
		if (getWidgetCount() > 0)
			DOM.setElementAttribute((Element) getWidget(getWidgetCount() - 1).getElement().getParentElement(), "height", "1px");
		super.add(child, container);
		DOM.setElementAttribute(container, "height", "*");
	}
	@Override
	public void insert(Widget w, int beforeIndex) {
		super.insert(w, beforeIndex);
		DOM.setElementAttribute((Element)getWidget(beforeIndex).getElement().getParentElement(), "height", "1px");
	}
	public void setHorizontalAlignment(String horizontalAlignment) {
		if (VkDesignerUtil.isDesignerMode) {
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Left", "left");
			listBox.addItem("Center", "center");
			listBox.addItem("Right", "right");
			listBox.setWidth("100px");
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(int widgetIndex, String align) {
					DOM.setElementAttribute((Element) getWidget(widgetIndex).getElement().getParentElement(), "align", align);
				}
			});
		}
	}

	@Override
	public void setVerticalAlignment(String verticalAlignment) {
		if (VkDesignerUtil.isDesignerMode) {
			final ListBox listBox = new ListBox(false);
			listBox.addItem("Top", "top");
			listBox.addItem("Middle", "middle");
			listBox.addItem("Bottom", "bottom");
			listBox.setWidth("100px");
			if(getVerticalAlignmentString().equals("top"))
				listBox.setSelectedIndex(0);
			else if(getVerticalAlignmentString().equals("middle"))
				listBox.setSelectedIndex(1);
			else if(getVerticalAlignmentString().equals("bottom"))
				listBox.setSelectedIndex(2);
			showSetCellAlignmentDialog(listBox, new IAlignment() {
				@Override
				public void doAlignment(int widgetIndex, String align) {
					DOM.setStyleAttribute((Element) getWidget(widgetIndex).getElement().getParentElement(), "verticalAlign", align);
				}
			});
		}
	}

	private void showSetCellAlignmentDialog(final ListBox listBox, final IAlignment iAlignment) {
		final DialogBox origDialog = new DialogBox();
		DOM.setStyleAttribute(origDialog.getElement(), "zIndex",
				Integer.toString(Integer.MAX_VALUE));
		final VerticalPanel dialog = new VerticalPanel();
		origDialog.add(dialog);
		origDialog.setText("Cell Alignment Dialog");
		dialog.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		dialog.add(new Label("Please choose the Widget :"));
		final ListBox widgetIndexLb = new ListBox();
		for (Iterator<Widget> iterator = iterator(); iterator.hasNext();) {
			Widget next = iterator.next();
			widgetIndexLb.addItem(((IVkWidget) next).getWidgetName()
					+ " - at index - " + getWidgetIndex(next));
		}

		widgetIndexLb.setWidth("300px");
		dialog.add(widgetIndexLb);
		widgetIndexLb.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				iAlignment.doAlignment(widgetIndexLb.getSelectedIndex(), listBox.getValue(listBox.getSelectedIndex()));
			}
		});
		dialog.add(new Label("Please choose Alignment"));
		dialog.add(listBox);
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				iAlignment.doAlignment(widgetIndexLb.getSelectedIndex(), listBox.getValue(listBox.getSelectedIndex()));
			}
		});
		HorizontalPanel buttonsPanel = new HorizontalPanel();
		dialog.add(buttonsPanel);
		Button saveButton = new Button("OK");
		buttonsPanel.add(saveButton);
		saveButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//iAlignment.doAlignment(widgetIndexLb.getSelectedIndex(), listBox.getValue(listBox.getSelectedIndex()));
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

	private interface IAlignment {
		public void doAlignment(int widgetIndex, String align);
	}

	public String getHorizontalAlignmentString() {
		if (getHorizontalAlignment().getTextAlignString() != null)
			return getHorizontalAlignment().getTextAlignString();
		else
			return "left";
	}

	public String getVerticalAlignmentString() {
		return getVerticalAlignment().getVerticalAlignString();
	}

	@Override
	public String getWidgetName() {
		return NAME;
	}

	@Override
	public void clone(Widget targetWidget) {
	}

	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return true;
	}
	@Override
	public boolean isResizable() {
		return true;
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		ArrayList<Widget> list = new ArrayList<Widget>();
		Button leftAlign = new Button("L");
		list.add(leftAlign);
		leftAlign.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				for(int i = 0, len = VkVerticalPanel.this.getWidgetCount(); i < len; i++)
					if(VkDesignerUtil.getMenu().isLastSelectedWidget(VkVerticalPanel.this.getWidget(i)))
						DOM.setElementAttribute((Element) getWidget(i).getElement().getParentElement(), "align", "left");
			}
		});
		Button centerAlign = new Button("C");
		list.add(centerAlign);
		centerAlign.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				for(int i = 0, len = VkVerticalPanel.this.getWidgetCount(); i < len; i++)
					if(VkDesignerUtil.getMenu().isLastSelectedWidget(VkVerticalPanel.this.getWidget(i)))
						DOM.setElementAttribute((Element) getWidget(i).getElement().getParentElement(), "align", "center");
			}
		});
		Button rightAlign = new Button("R");
		list.add(rightAlign);
		rightAlign.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				for(int i = 0, len = VkVerticalPanel.this.getWidgetCount(); i < len; i++)
					if(VkDesignerUtil.getMenu().isLastSelectedWidget(VkVerticalPanel.this.getWidget(i)))
						DOM.setElementAttribute((Element) getWidget(i).getElement().getParentElement(), "align", "right");
			}
		});
		return list;
	}
	/************************** Export attribute Methods ********************************/
	@Override
	@Export
	public int getWidgetCount() {
		return super.getWidgetCount();
	}

	@Override
	@Export
	public boolean remove(int index) {
		return super.remove(index);
	}

	@Override
	@Export
	public void setVisible(boolean isVisible) {
		super.setVisible(isVisible);
	}

	@Override
	@Export
	public boolean isVisible() {
		return super.isVisible();
	}

	@Override
	@Export
	public void addStyleName(String className) {
		super.addStyleName(className);
	}

	@Override
	@Export
	public void removeStyleName(String className) {
		super.removeStyleName(className);
	}
}