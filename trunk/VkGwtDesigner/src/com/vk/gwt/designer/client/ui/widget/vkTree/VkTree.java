package com.vk.gwt.designer.client.ui.widget.vkTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAllKeyHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkAnimation;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkCloseHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkOpenHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkSelectionHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.component.IVkWidget;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.designer.EventHelper;

public class VkTree extends Tree implements HasVkAnimation, HasVkAllKeyHandlers, HasVkFocusHandler, HasVkBlurHandler,HasVkSelectionHandler, HasVkOpenHandler
, HasVkCloseHandler, HasVkMouseDownHandler, HasVkMouseMoveHandler, HasVkMouseOutHandler, HasVkMouseOverHandler, HasVkMouseUpHandler, HasVkMouseWheelHandler
, HasVkTabIndex, HasVkAccessKey, IVkPanel, HasVkWidgets{
	public static final String NAME = "Tree";
	private HandlerRegistration mouseDownHandlerRegistration;
	private HandlerRegistration mouseUpHandlerRegistration;
	private HandlerRegistration mouseMoveHandlerRegistration;
	private HandlerRegistration mouseOutHandlerRegistration;
	private HandlerRegistration mouseOverHandlerRegistration;
	private HandlerRegistration mouseWheelHandlerRegistration;
	private HandlerRegistration keyUpHandlerRegistration;
	private HandlerRegistration keyDownHandlerRegistration;
	private HandlerRegistration keyPressHandlerRegistration;
	private HandlerRegistration focusHandlerRegistration;
	private HandlerRegistration blurHandlerRegistration;
	private HandlerRegistration selectionHandlerRegsitration;
	private HandlerRegistration closeHandlerRegistration;
	private HandlerRegistration openHandlerRegistration;
	private String mouseDownJs = "";
	private String mouseUpJs = "";
	private String mouseMoveJs = "";
	private String mouseOutJs = "";
	private String mouseOverJs = "";
	private String mouseWheelJs = "";
	private String keyDownJs = "";
	private String keyUpJs = "";
	private String keyPressJs = "";
	private String focusJs = "";
	private String blurJs = "";
	private String selectionJs = "";
	private String closeJs = "";
	private String openJs = "";
	private char accessKey;
	private IVkWidget vkParent;
	
	public VkTree()
	{
		addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				if(Element.as(event.getNativeEvent().getEventTarget()) == VkTree.this.getElement() && event.getNativeButton() == NativeEvent.BUTTON_LEFT)
					VkTree.this.setSelectedItem(null);
			}
		});
	}
	@Override
	public void add(Widget widget)
	{
		if(super.getSelectedItem() == null)
			super.addItem(widget);
		else
			super.getSelectedItem().addItem(widget);
		if(widget instanceof IVkWidget)
			((IVkWidget)widget).setVkParent(this);
	}
	
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkMouseDownHandler.NAME))
			return mouseDownJs;
		else if(eventName.equals(HasVkMouseUpHandler.NAME))
			return mouseUpJs;
		else if(eventName.equals(HasVkMouseOverHandler.NAME))
			return mouseOverJs;
		else if(eventName.equals(HasVkMouseOutHandler.NAME))
			return mouseOutJs;
		else if(eventName.equals(HasVkMouseWheelHandler.NAME))
			return mouseWheelJs;
		else if(eventName.equals(HasVkMouseMoveHandler.NAME))
			return mouseMoveJs;
		else if(eventName.equals(HasVkKeyUpHandler.NAME))
			return keyUpJs;
		else if(eventName.equals(HasVkKeyDownHandler.NAME))
			return keyDownJs;
		else if(eventName.equals(HasVkKeyPressHandler.NAME))
			return keyPressJs;
		else if(eventName.equals(HasVkFocusHandler.NAME))
			return focusJs;
		else if(eventName.equals(HasVkBlurHandler.NAME))
			return blurJs;
		else if(eventName.equals(HasVkOpenHandler.NAME))
			return openJs;
		else if(eventName.equals(HasVkCloseHandler.NAME))
			return closeJs;
		else if(eventName.equals(HasVkSelectionHandler.NAME))
			return selectionJs;
		else return "";
	}
	@Override
	public void addMouseDownHandler(String js) {
		if(mouseDownHandlerRegistration != null)
			mouseDownHandlerRegistration.removeHandler();
		mouseDownHandlerRegistration = null;
		mouseDownJs = js.trim();
		if(!mouseDownJs.isEmpty())
		{
			mouseDownHandlerRegistration = addMouseDownHandler(new MouseDownHandler() {
				@Override
				public void onMouseDown(MouseDownEvent event) {
					EventHelper.getInstance().executeEvent(mouseDownJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseUpHandler(String js) {
		if(mouseUpHandlerRegistration != null)
			mouseUpHandlerRegistration.removeHandler();
		mouseUpHandlerRegistration = null;
		mouseUpJs = js.trim();
		if(!mouseUpJs.isEmpty())
		{
			mouseUpHandlerRegistration = addMouseUpHandler(new MouseUpHandler() {
				@Override
				public void onMouseUp(MouseUpEvent event) {
					EventHelper.getInstance().executeEvent(mouseUpJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseMoveHandler(String js) {
		if(mouseMoveHandlerRegistration != null)
			mouseMoveHandlerRegistration.removeHandler();
		mouseMoveHandlerRegistration = null;
		mouseMoveJs = js.trim();
		if(!mouseMoveJs.isEmpty())
		{
			mouseMoveHandlerRegistration = addMouseMoveHandler(new MouseMoveHandler() {
				@Override
				public void onMouseMove(MouseMoveEvent event) {
					EventHelper.getInstance().executeEvent(mouseMoveJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseOverHandler(String js) {
		if(mouseOverHandlerRegistration != null)
			mouseOverHandlerRegistration.removeHandler();
		mouseOverHandlerRegistration = null;
		mouseOverJs = js.trim();
		if(!mouseOverJs.isEmpty())
		{
			mouseOverHandlerRegistration = addMouseOverHandler(new MouseOverHandler() {
				@Override
				public void onMouseOver(MouseOverEvent event) {
					EventHelper.getInstance().executeEvent(mouseOverJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseOutHandler(String js) {
		if(mouseOutHandlerRegistration != null)
			mouseOutHandlerRegistration.removeHandler();
		mouseOutHandlerRegistration = null;
		mouseOutJs = js;
		if(!mouseOutJs.isEmpty())
		{
			mouseOutHandlerRegistration = addMouseOutHandler(new MouseOutHandler() {
				@Override
				public void onMouseOut(MouseOutEvent event) {
					EventHelper.getInstance().executeEvent(mouseOutJs, event, true);
				}
			});
		}
	}
	@Override
	public void addMouseWheelHandler(String js) {
		if(mouseWheelHandlerRegistration != null)
			mouseWheelHandlerRegistration.removeHandler();
		mouseWheelHandlerRegistration = null;
		mouseWheelJs = js.trim();
		if(!mouseWheelJs.isEmpty())
		{
			mouseWheelHandlerRegistration = addMouseWheelHandler(new MouseWheelHandler() {
				@Override
				public void onMouseWheel(MouseWheelEvent event) {
					EventHelper.getInstance().executeEvent(mouseWheelJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyDownHandler(String js) {
		if(keyDownHandlerRegistration != null)
			keyDownHandlerRegistration.removeHandler();
		keyDownHandlerRegistration = null;
		keyDownJs = js.trim();
		if(!keyDownJs.isEmpty())
		{
			keyDownHandlerRegistration = addKeyDownHandler(new KeyDownHandler() {
				@Override
				public void onKeyDown(KeyDownEvent event) {
					EventHelper.getInstance().executeEvent(keyDownJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyUpHandler(String js) {
		if(keyUpHandlerRegistration != null)
			keyUpHandlerRegistration.removeHandler();
		keyUpHandlerRegistration = null;
		keyUpJs = js.trim();
		if(!keyUpJs.isEmpty())
		{
			keyUpHandlerRegistration = addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					EventHelper.getInstance().executeEvent(keyUpJs, event, true);
				}
			});
		}
	}
	@Override
	public void addKeyPressHandler(String js) {
		if(keyPressHandlerRegistration != null)
			keyPressHandlerRegistration.removeHandler();
		keyPressHandlerRegistration = null;
		keyPressJs = js.trim();
		if(!keyPressJs.isEmpty())
		{
			keyPressHandlerRegistration = addKeyPressHandler(new KeyPressHandler() {
				@Override
				public void onKeyPress(KeyPressEvent event) {
					EventHelper.getInstance().executeEvent(keyPressJs, event, true);
				}
			});
		}
	}
	@Override
	public void addFocusHandler(String js) {
		if(focusHandlerRegistration != null)
			focusHandlerRegistration.removeHandler();
		focusHandlerRegistration = null;
		focusJs = js.trim();
		if(!focusJs.isEmpty())
		{
			focusHandlerRegistration = addFocusHandler(new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					EventHelper.getInstance().executeEvent(focusJs, event, true);
				}
			});
		}
	}
	@Override
	public void addBlurHandler(String js) {
		if(blurHandlerRegistration != null)
			blurHandlerRegistration.removeHandler();
		blurHandlerRegistration = null;
		blurJs = js.trim();
		if(!blurJs.isEmpty())
		{
			blurHandlerRegistration = addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					EventHelper.getInstance().executeEvent(blurJs, event, true);
				}
			});
		}
	}
	@Override
	public void addSelectionHandler(String js) {
		if(selectionHandlerRegsitration != null)
			selectionHandlerRegsitration.removeHandler();
		selectionHandlerRegsitration = null;
		selectionJs = js.trim();
		if(!selectionJs.isEmpty())
		{
			selectionHandlerRegsitration = super.addSelectionHandler(new SelectionHandler<TreeItem>() {
				@SuppressWarnings("unchecked")
				@Override
				public void onSelection(SelectionEvent<TreeItem> event) {
					@SuppressWarnings("rawtypes")
					Map eventproperties = new HashMap();
					TreeItem treeItem = event.getSelectedItem();
					setUpTreeEvent(eventproperties, treeItem);
					EventHelper.getInstance().executeEvent(selectionJs, eventproperties);
				}
			});
		}
	}
	@Override
	public void addCloseHandler(String js) {
		if(closeHandlerRegistration != null)
			closeHandlerRegistration.removeHandler();
		closeHandlerRegistration = null;
		closeJs = js.trim();
		if(!closeJs.isEmpty())
		{
			closeHandlerRegistration = super.addCloseHandler(new CloseHandler<TreeItem>() {
				@SuppressWarnings("unchecked")
				@Override
				public void onClose(CloseEvent<TreeItem> event) {
					@SuppressWarnings("rawtypes")
					Map eventproperties = new HashMap();
					TreeItem treeItem = event.getTarget();
					setUpTreeEvent(eventproperties, treeItem);
					EventHelper.getInstance().executeEvent(closeJs, eventproperties);
				}
			});
		}
	}
	@Override
	public void addOpenHandler(String js) {
		if(openHandlerRegistration != null)
			openHandlerRegistration.removeHandler();
		openHandlerRegistration = null;
		openJs = js.trim();
		if(!openJs.isEmpty())
		{
			openHandlerRegistration = super.addOpenHandler(new OpenHandler<TreeItem>() {
				@SuppressWarnings("unchecked")
				@Override
				public void onOpen(OpenEvent<TreeItem> event) {
					@SuppressWarnings("rawtypes")
					Map eventproperties = new HashMap();
					TreeItem treeItem = event.getTarget();
					setUpTreeEvent(eventproperties, treeItem);
					EventHelper.getInstance().executeEvent(openJs, eventproperties);
				}
			});
		}
	}
	@SuppressWarnings("unchecked")
	private void setUpTreeEvent(@SuppressWarnings("rawtypes") Map eventproperties, TreeItem treeItem) {
		eventproperties.put("state", Boolean.toString(treeItem.getState()));
		eventproperties.put("tree", treeItem.getTree().getElement());
		eventproperties.put("treeItem", treeItem.getElement());
	}
	@Override
	public void setAccessKey(char key)
	{
		super.setAccessKey(key);
		accessKey = key;
	}
	public char getAccessKey() {
		return accessKey;
	}
	@Override
	public boolean remove(Widget w)
	{
		boolean retVal = false;
		if(w != null)
		{
			for(int i = 0; i < super.getItemCount(); i++)
			{
				if(w.equals(super.getItem(i).getWidget()))
				{
					super.removeItem(super.getItem(i));
					return true;
				}
			}
		}
		for(int i = 0; i < super.getItemCount(); i++)
		{
			if(retVal)
				return true;
			else
				retVal |= treeLookUp(getItem(i), w, retVal);
		}
		return false;
	}
	private boolean treeLookUp(TreeItem currentTreeItem, Widget w, boolean retVal) {
		if(!retVal)
		{
			for(int i = 0; i < currentTreeItem.getChildCount(); i++)
			{
				if(w.equals(currentTreeItem.getChild(i).getWidget()))
				{
					currentTreeItem.removeItem(currentTreeItem.getChild(i));
					return true;
				}
				retVal |= treeLookUp(currentTreeItem.getChild(i), w, retVal);
			}
		}
		return retVal;
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	public void clone(Widget targetWidget) {}
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
	/**************************Export attribute Methods********************************/
	@Override
	@Export
	public void clear() {
		super.clear();
	}
	@Override
	@Export
	public int getItemCount() {
		return super.getItemCount();
	}
	@Override
	@Export
	public void setFocus(boolean focus) {
		super.setFocus(focus);
	}
	@Export
	public void setSelectedItem(int index){
		super.setSelectedItem(super.getItem(index));
	}
	@Export
	public void setSelectedItem(int index, boolean fireEvent){
		super.setSelectedItem(super.getItem(index), fireEvent);
	}
	@Export
	public Element getItemElement(int index)
	{
		Widget widget = getItem(index).getWidget();
		if(widget != null)
			return widget.getElement();
		else
			return null;
	}
	@Export
	public Element getSelectedItemElement()
	{
		Widget widget = getSelectedItem().getWidget();
		if(widget != null)
			return widget.getElement();
		else
			return null;
	}
	@Export
	public void remove(int index)
	{
		super.removeItem(super.getSelectedItem());
	}
	@Override
	public List<Widget> getToolbarWidgets() {
		return null;
	}
	@Override
	public IVkWidget getVkParent() {
		return vkParent;
	}
	@Override
	public void setVkParent(IVkWidget panel) {
		this.vkParent = panel;
	}
}
