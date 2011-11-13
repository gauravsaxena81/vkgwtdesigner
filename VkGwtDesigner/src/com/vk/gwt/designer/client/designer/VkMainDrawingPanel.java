package com.vk.gwt.designer.client.designer;

import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.attributes.HasVkLoadHandler;
import com.vk.gwt.designer.client.api.component.IVkPanel;
import com.vk.gwt.designer.client.api.widgets.HasVkWidgets;
import com.vk.gwt.designer.client.ui.panel.vkAbsolutePanel.VkAbsolutePanel;

public class VkMainDrawingPanel extends VkAbsolutePanel implements IVkPanel, HasVkLoadHandler, HasVkWidgets{
	public static String NAME = "Drawing Panel";
	private String loadHandlerJs = "";
	private static VkMainDrawingPanel drawingPanel = new VkMainDrawingPanel();
	private VkMainDrawingPanel(){
		getElement().setId("drawingPanel");
		setPixelSize(Window.getClientWidth() - 5, Window.getClientHeight() - 50);
		DOM.setStyleAttribute(getElement(), "border", "solid 1px gray");
		DOM.setStyleAttribute(getElement(), "position", "relative");
		if(VkStateHelper.getInstance().isDesignerMode()) {
			InitializeHelper.getInstance().initDesignerEvents(this, WidgetEngineMapping.getInstance().getEngineMap().get(VkMainDrawingPanel.NAME));
			VkStateHelper.getInstance().getMenu().prepareMenu(this);
		}
	}
	
	public static VkMainDrawingPanel getInstance(){
		return drawingPanel;
	}
	
	@Override
	public void clone(Widget targetWidget) {}

	@Override
	public String getWidgetName() {
		return NAME;
	}

	@Override
	public void addLoadHandler(String js) {
		loadHandlerJs = js;
	}

	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkLoadHandler.NAME))
			return loadHandlerJs;
		else
			return "";
	}
	/*@Override
	public Iterator<Widget> iterator()	{
		List<Widget> list = new ArrayList<Widget>();
		Iterator<Widget> widgetIterator = super.iterator();
		while(widgetIterator.hasNext())
			list.add(widgetIterator.next());
		return list.iterator();
	}*/
	@Override
	public boolean showMenu() {
		return true;
	}
	@Override
	public boolean isMovable() {
		return false;
	}
	@Override
	public boolean isResizable() {
		return false;
	}

	@Override
	public List<Widget> getToolbarWidgets() {
		// TODO Auto-generated method stub
		return null;
	}
}
