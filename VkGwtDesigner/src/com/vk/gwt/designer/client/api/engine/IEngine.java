package com.vk.gwt.designer.client.api.engine;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;

public interface IEngine {
	public static final String REMOVE = "Remove";
	public static final String RESIZE = "Resize";
	public static final String CUT = "Cut";
	public static final String COPY = "Copy";
	public static final String PASTE = "Paste";
	public static final String SAVE = "Save";
	public static final String LOAD = "Load";
	public static final String COPY_STYLE = "Copy Style";
	public static final String PASTE_STYLE = "Paste Style";
	public List<String> getAttributesList(Widget invokingWidget);
	public void applyAttribute(String attributeName, Widget invokingWidget);
	public List<String> getWidgetsList(Widget invokingWidget);
}
