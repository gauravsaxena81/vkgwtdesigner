package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.vk.gwt.designer.client.api.component.IVkWidget;

public interface IEngine {
	public static final String REMOVE = "Remove";
	public static final String RESIZE = "Resize";
	public static final String MOVE = "Move";
	public static final String ADD_CLASS = "Add Classname";
	public static final String REMOVE_CLASS = "Remove Classname";
	public static final String TOOL_TIP = "Tool Tip";
	public static final String FILL_PARENT = "Fill Parent";
	public static final String CUT = "Cut";
	public static final String COPY = "Copy";
	public static final String PASTE = "Paste";
	public static final String NEW = "New";
	public static final String SAVE = "Save";
	public static final String SAVE_AS = "Save as";
	public static final String OPEN = "Open";
	public static final String LOAD = "Load a file";
	public static final String COPY_STYLE = "Copy Style";
	public static final String PASTE_STYLE = "Paste Style";
	public static final String CLEAR = "Clear";
	public static final String FILE = "File";
	public static final String STRING = "String";
	public List<String> getAttributesList(Widget invokingWidget);
	public void applyAttribute(String attributeName, Widget invokingWidget);
	public List<String> getWidgetsList(Widget invokingWidget);
	public List<String> getPanelsList(IVkWidget invokingWidget);
}
