package com.vk.gwt.designer.client.designer;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class VkEventTextArea extends Composite {
	private FocusPanel fp = new FocusPanel();
	private TextArea ta = new TextArea();
	private JavaScriptObject editor;
	public VkEventTextArea()
	{
		initWidget(fp);
		fp.setSize("100%", "100%");
		DOM.setStyleAttribute(fp.getElement(), "border", "solid 1px gray");
		fp.add(ta);
		fp.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				String id = getRealElementId(Element.as(event.getNativeEvent().getEventTarget()));
				if(!id.isEmpty())
					addElementId(id);
				else
					DOM.releaseCapture(getElement());
			}
		});
		fp.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				DOM.setCapture(getElement());
			}
		});
		fp.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				DOM.releaseCapture(getElement());
			}
		});		
	}
	@Override
	protected void initWidget(Widget widget)
	{
		super.initWidget(widget);
	}
	@Override
	public void onLoad()
	{
		editor = initCodeMirror(ta.getElement());
		new Timer(){
			@Override
			public void run() {
				try{
					focus();
				}catch(Exception e)
				{
					schedule(200);
				}
			}
		}.schedule(200);
	}
	private native void addElementId(String id)/*-{
		var editor = this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor;
		var cursorPos = editor.cursorPosition();
		var stringId = "&(" +  id + ")";
		editor.insertIntoLine(cursorPos.line, cursorPos.character, stringId);
		setTimeout(function(){
			editor.focus();
			editor.selectLines(cursorPos.line, cursorPos.character + stringId.length, cursorPos.line, cursorPos.character + stringId.length);
		},300);
	}-*/;
	private native JavaScriptObject initCodeMirror(Element textarea)/*-{
		 return new $wnd.CodeMirror($wnd.CodeMirror.replace(textarea), {
			 height: "100%",
			 width: "100%",
			 content: textarea.value,
			 parserfile: ["tokenizejavascript.js", "parsejavascript.js"],
			 stylesheet: "codemirror/css/jscolors.css",
			 path: "codemirror/js/",
			 autoMatchParens: true
		 });
	}-*/;
	public native String getText()/*-{
		return this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor.getCode().replace(/\s+/g,' ');
	}-*/;
	public void setText(final String text){
		new Timer(){
			@Override
			public void run() {
				try{
					if(editor != null)
						populateJs(editor, text.replaceAll(";", ";\n").replaceAll("\\{","{\n").replaceAll("\\}","}\n").trim());
					else
						schedule(200);
				}
				catch(Exception e)
				{
					schedule(200);//editor is not initialized properly
				}
			}
			private native void populateJs(JavaScriptObject editor, String text) /*-{
				editor.setCode(text);
				editor.reindent();
			}-*/;
		}.schedule(200);
	}
	private String getRealElementId(com.google.gwt.dom.client.Element element) 
	{
		com.google.gwt.dom.client.Element currentElement = element;
		while(currentElement != null && currentElement.getId().isEmpty())
			currentElement = currentElement.getParentElement();
		if(currentElement != null && currentElement != VkDesignerUtil.getDrawingPanel().getElement())
			return currentElement.getId();
		else
			return "";
	}
	public void addMouseDownHandler(MouseDownHandler mouseDownHandler) {
		ta.addMouseDownHandler(mouseDownHandler);
	}
	public void setFocus(boolean b) {
		focus();
	}
	private native void focus() /*-{
		this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor.focus();
	}-*/;
}
