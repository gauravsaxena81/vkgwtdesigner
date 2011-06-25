package com.vk.gwt.designer.client.designer;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class VkEventTextArea extends Composite {
	private FocusPanel fp = new FocusPanel();
	private TextArea ta = new TextArea();
	private JavaScriptObject editor;
	private PopupPanel autoCompletePanel = new PopupPanel();
	public VkEventTextArea()
	{
		initWidget(fp);
		fp.setSize("100%", "100%");
		DOM.setStyleAttribute(fp.getElement(), "border", "solid 1px gray");
		ScrollPanel scrollPanel = new ScrollPanel();
		autoCompletePanel.add(scrollPanel);
		VerticalPanel optionsHolderPanel = new VerticalPanel();
		scrollPanel.add(optionsHolderPanel);
		DOM.setStyleAttribute(autoCompletePanel.getElement(), "zIndex", Integer.MAX_VALUE + "");
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
		fp.addKeyDownHandler(new KeyDownHandler(){
			@Override
			public void onKeyDown(KeyDownEvent event) {
				event.stopPropagation();
			}});
	}
	private void showSuggestions(int keyCode)
	{
		String sentence = getText();
		int startIndex = sentence.length() - 1;
		String elementId = "";
		while(startIndex >= 0 && sentence.charAt(startIndex--) != '.');
		if(startIndex != 0 && startIndex < sentence.length() - 2)
		{
			String phrase = startIndex == sentence.length() - 1 ? "" : sentence.substring(startIndex + 2);//after the dot is found
			startIndex++;//startIndex points at dot now
			if(startIndex > 0 && sentence.charAt(--startIndex) == ')')
			{
				char nextChar;
				while(startIndex > 0 && Character.isDigit(nextChar = sentence.charAt(--startIndex)))
					elementId += nextChar;
				if(startIndex > 0 && sentence.charAt(startIndex) == '(')
				{
					if(startIndex > 0 && sentence.charAt(--startIndex) == '&')
						showFunctions(elementId, phrase);
				}
			}
		}
	}
	private native void showFunctions(String elementId, String phrase) /*-{
		var elem = $doc.getElementById(elementId);
		var functions = '';
		var j = 0;
		for(i in elem)
			if(i.toLowerCase().indexOf(phrase.toLowerCase()) == 0 && i.indexOf('_') != 0)
				functions += i + ',';
		var coords = this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor.cursorCoords(true);
		this.@com.vk.gwt.designer.client.designer.VkEventTextArea::showAutoComplete(Ljava/lang/String;Lcom/google/gwt/dom/client/Element;III)(functions, elem, phrase.length, coords.x, coords.y);
	}-*/;
	@SuppressWarnings("unused")//called from native function
	private void showAutoComplete(String optionString, final com.google.gwt.dom.client.Element element, final int phraseLength, int x, int y)
	{
		final String[] options = optionString.split(",");
		ScrollPanel scrollPanel = (ScrollPanel)autoCompletePanel.getWidget();
		VerticalPanel optionHolderPanel = (VerticalPanel)scrollPanel.getWidget();
		optionHolderPanel.clear();
		for (int i = 0, len = options.length; i < len; i++)
		{
			final Anchor option = getOption(options[i]);
			final int optionsNum = i;
			optionHolderPanel.add(option);
			option.addClickHandler(new ClickHandler(){
				@Override
				public void onClick(ClickEvent event) {
					setAutoCompleteOption(options[optionsNum], phraseLength, element);
					autoCompletePanel.hide();
				}});
		}
		autoCompletePanel.setAutoHideEnabled(true);
		autoCompletePanel.setPopupPosition(x, y + 10);
		autoCompletePanel.show();
		scrollPanel.setSize("200px",Math.min(optionHolderPanel.getOffsetHeight(), 300) + "px");
	}
	private native void setAutoCompleteOption(String autoCompleteText, int phraseLength, com.google.gwt.dom.client.Element element) /*-{
		var editor = this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor;
		var cursorPos = editor.getCursor(true);
		var value = element[autoCompleteText];
		if(typeof value == 'function')
			autoCompleteText += '()';
		editor.replaceRange(autoCompleteText, {line: cursorPos.line, ch: cursorPos.ch - phraseLength}, 
		{line: cursorPos.line, ch: cursorPos.ch});
		editor.focus();
	}-*/;
	private Anchor getOption(String option) {
		final Anchor optionLabel = new Anchor(option);
		optionLabel.setStyleName("eventcode-autocomplete");
		return optionLabel;
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
		var cursorPos = editor.getCursor(true);
		var stringId = "&(" +  id + ")";
		editor.replaceRange(stringId, {line: cursorPos.line, ch: cursorPos.ch});
		editor.focus();
	}-*/;
	private native JavaScriptObject initCodeMirror(Element textarea)/*-{
		var vkEventTextArea = this;
		//var autoCompletePanel = vkEventTextArea.@com.vk.gwt.designer.client.designer.VkEventTextArea::autoCompletePanel;
		 //|| autoCompletePanel.@com.google.gwt.user.client.ui.PopupPanel::isShowing()
		return $wnd.CodeMirror.fromTextArea(textarea, {		 
			 matchBrackets: true,
			 mode:  "javascript",
			 onKeyEvent: function(editor, keyEvent){
				var keyCode = keyEvent.keyCode;
				if((keyEvent.ctrlKey && keyCode == 32))
					vkEventTextArea.@com.vk.gwt.designer.client.designer.VkEventTextArea::showSuggestions(I)(keyCode);
			}
		 });
	}-*/;
	public native String getText()/*-{
		return this.@com.vk.gwt.designer.client.designer.VkEventTextArea::editor.getValue().replace(/\s+/g,' ');
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
				editor.replaceRange(text, {line: 0, ch: 0});
				editor.refresh();
			}-*/;
		}.schedule(200);
	}
	private native void setText(JavaScriptObject editor, String text, int lineNum, int charNum) /*-{
		editor.replaceRange(text, {line: lineNum, ch: charNum});
	}-*/;
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
