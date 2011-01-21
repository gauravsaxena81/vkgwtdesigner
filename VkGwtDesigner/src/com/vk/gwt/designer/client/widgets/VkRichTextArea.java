package com.vk.gwt.designer.client.widgets;

import java.util.Map;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.google.gwt.event.logical.shared.InitializeEvent;
import com.google.gwt.event.logical.shared.InitializeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.RichTextArea.FontSize;
import com.google.gwt.user.client.ui.RichTextArea.Justification;
import com.gwtstructs.gwt.client.widgets.jsBridge.Export;
import com.vk.gwt.designer.client.api.attributes.HasVkAccessKey;
import com.vk.gwt.designer.client.api.attributes.HasVkAllKeyHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkAllMouseHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkBlurHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkClickHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkEnabled;
import com.vk.gwt.designer.client.api.attributes.HasVkFocusHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkInitializeHandlers;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyPressHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkKeyUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseDownHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseMoveHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOutHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseOverHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseUpHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkMouseWheelHandler;
import com.vk.gwt.designer.client.api.attributes.HasVkTabIndex;
import com.vk.gwt.designer.client.api.attributes.HasVkText;
import com.vk.gwt.designer.client.api.widgets.IVkWidget;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;
import com.vk.gwt.designer.client.designer.VkEngine.IEventRegister;
import com.vk.gwt.designer.client.widgets.richtexttoolbar.RichTextToolbar;

public class VkRichTextArea extends Grid implements IVkWidget, HasVkText, HasVkAllKeyHandlers, HasVkAllMouseHandlers, HasVkFocusHandler
, HasVkBlurHandler, HasVkInitializeHandlers, HasVkAccessKey, HasVkTabIndex, HasVkEnabled{
	public static final String NAME = "Rich Text Area";
	private RichTextArea richTextArea;
	private RichTextToolbar toolbar;
	private HandlerRegistration clickHandlerRegistration;
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
	private HandlerRegistration initializeHandlerRegistration;
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
	private String clickJs = "";
	private String initializeJs = "";
	private char accessKey;
	public VkRichTextArea()
	{
		super(2,1);
		richTextArea = new RichTextArea();
		richTextArea.setPixelSize(100, 20);
		toolbar = new RichTextToolbar(richTextArea);
		toolbar.setWidth("100%");
		setWidget(0, 0, toolbar);
		setWidget(1, 0, richTextArea);
		richTextArea.setWidth("100%");
	}
	protected RichTextArea getRichTextArea()
	{
		return richTextArea;
	}
	protected void setRichTextToolbar(Widget widget)
	{
		setWidget(0, 0, widget);
	}
	@Override
	public void setHeight(String height)
	{
		richTextArea.setHeight((Integer.parseInt(height.replace("px","")) - toolbar.getOffsetHeight()) + "px");
	}
	@Override
	public void addClickHandler(final String js) {
		if(clickHandlerRegistration != null)
			clickHandlerRegistration.removeHandler();
		clickJs = js;
		clickHandlerRegistration = richTextArea.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				VkDesignerUtil.executeEvent(clickJs, event);
			}
		});
	}
	@Override
	public void addMouseDownHandler(String js) {
		if(mouseDownHandlerRegistration != null)
			mouseDownHandlerRegistration.removeHandler();
		mouseDownJs = js;
		mouseDownHandlerRegistration = richTextArea.addMouseDownHandler(new MouseDownHandler() {
			@Override
			public void onMouseDown(MouseDownEvent event) {
				VkDesignerUtil.executeEvent(mouseDownJs, event);
			}
		});
	}
	@Override
	public void addMouseUpHandler(String js) {
		if(mouseUpHandlerRegistration != null)
			mouseUpHandlerRegistration.removeHandler();
		mouseUpJs = js;
		mouseUpHandlerRegistration = richTextArea.addMouseUpHandler(new MouseUpHandler() {
			@Override
			public void onMouseUp(MouseUpEvent event) {
				VkDesignerUtil.executeEvent(mouseUpJs, event);
			}
		});
	}
	@Override
	public void addMouseMoveHandler(String js) {
		if(mouseMoveHandlerRegistration != null)
			mouseMoveHandlerRegistration.removeHandler();
		mouseMoveJs = js;
		mouseMoveHandlerRegistration = richTextArea.addMouseMoveHandler(new MouseMoveHandler() {
			@Override
			public void onMouseMove(MouseMoveEvent event) {
				VkDesignerUtil.executeEvent(mouseMoveJs, event);
			}
		});
	}
	@Override
	public void addMouseOverHandler(String js) {
		if(mouseOverHandlerRegistration != null)
			mouseOverHandlerRegistration.removeHandler();
		mouseOverJs = js;
		mouseOverHandlerRegistration = richTextArea.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				VkDesignerUtil.executeEvent(mouseOverJs, event);
			}
		});
	}
	@Override
	public void addMouseOutHandler(String js) {
		if(mouseOutHandlerRegistration != null)
			mouseOutHandlerRegistration.removeHandler();
		mouseOutJs = js;
		mouseOutHandlerRegistration = richTextArea.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				VkDesignerUtil.executeEvent(mouseOutJs, event);
			}
		});
	}
	@Override
	public void addMouseWheelHandler(String js) {
		if(mouseWheelHandlerRegistration != null)
			mouseWheelHandlerRegistration.removeHandler();
		mouseWheelJs = js;
		mouseWheelHandlerRegistration = richTextArea.addMouseWheelHandler(new MouseWheelHandler() {
			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				VkDesignerUtil.executeEvent(mouseWheelJs, event);
			}
		});
	}
	@Override
	public void addKeyDownHandler(String js) {
		if(keyDownHandlerRegistration != null)
			keyDownHandlerRegistration.removeHandler();
		keyDownJs = js;
		keyDownHandlerRegistration = richTextArea.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				VkDesignerUtil.executeEvent(keyDownJs, event);
			}
		});
	}
	@Override
	public void addKeyUpHandler(String js) {
		if(keyUpHandlerRegistration != null)
			keyUpHandlerRegistration.removeHandler();
		keyUpJs = js;
		keyUpHandlerRegistration = richTextArea.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				VkDesignerUtil.executeEvent(keyUpJs, event);
			}
		});
	}
	@Override
	public void addKeyPressHandler(String js) {
		if(keyPressHandlerRegistration != null)
			keyPressHandlerRegistration.removeHandler();
		keyPressJs = js;
		keyPressHandlerRegistration = richTextArea.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				VkDesignerUtil.executeEvent(keyPressJs, event);
			}
		});
	}
	@Override
	public void addFocusHandler(String js) {
		if(focusHandlerRegistration != null)
			focusHandlerRegistration.removeHandler();
		focusJs = js;
		focusHandlerRegistration = richTextArea.addFocusHandler(new FocusHandler() {
			@Override
			public void onFocus(FocusEvent event) {
				VkDesignerUtil.executeEvent(focusJs, event);
			}
		});
	}
	@Override
	public void addBlurHandler(String js) {
		if(blurHandlerRegistration != null)
			blurHandlerRegistration.removeHandler();
		blurJs = js;
		blurHandlerRegistration = richTextArea.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				VkDesignerUtil.executeEvent(blurJs, event);
			}
		});
	}
	@Override
	public void addInitializeHandler(String js) {
		if(initializeHandlerRegistration != null)
			initializeHandlerRegistration.removeHandler();
		initializeJs = js;
		initializeHandlerRegistration = richTextArea.addInitializeHandler(new InitializeHandler() {
			private String initializeJs;

			@Override
			public void onInitialize(InitializeEvent event) {
				VkDesignerUtil.executeEvent(initializeJs, (Map<String, String>) null);
			}
		});
	}
	@Override
	public String getPriorJs(String eventName) {
		if(eventName.equals(HasVkClickHandler.NAME))
			return clickJs;
		else if(eventName.equals(HasVkMouseDownHandler.NAME))
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
		else if(eventName.equals(HasVkInitializeHandlers.NAME))
			return initializeJs;
		else return "";
	}
	@Override
	public char getAccessKey()
	{
		return accessKey;
	}
	@Override
	public void setAccessKey(char key)
	{
		accessKey = key;
		richTextArea.setAccessKey(key);
	}
	@Override
	public String getWidgetName() {
		return NAME;
	}
	@Override
	public int getTabIndex() {
		return richTextArea.getTabIndex();
	}
	@Override
	public void setTabIndex(int tabIndex) {
		richTextArea.setTabIndex(tabIndex);
	}
	@Override
	public void clone(Widget targetWidget) {}
	/**************************Export attribute Methods********************************/
	@Export
	public void createLink(String link)
	{
		final TextBox tb = new TextBox();
		tb.setWidth("100px");
		VkDesignerUtil.getEngine().showAddTextAttributeDialog("Please provide a link", tb
			, new IEventRegister() {
				@Override
				public void registerEvent(String link) {
					richTextArea.getFormatter().createLink(tb.getText());
				}
		});
	}
	@Export
	public String getBackColor(){
		return richTextArea.getFormatter().getBackColor();
	}
	@Export
	public String getForeColor(){
		return richTextArea.getFormatter().getForeColor();
	}
	@Export
	public void insertHorizontalRule(){
		richTextArea.getFormatter().insertHorizontalRule();
	}
	@Export
	public void insertHTML(String html){
		richTextArea.getFormatter().insertHTML(html);
	}
	@Export
	public void insertImage(String url){
		richTextArea.getFormatter().insertImage(url);
	}
	@Export
	public void insertOrderedList(){
		richTextArea.getFormatter().insertOrderedList();
	}
	@Export
	public void insertUnorderedList(){
		richTextArea.getFormatter().insertUnorderedList();
	}
	@Export
	public boolean isBold(){
		return richTextArea.getFormatter().isBold();
	}
	@Export
	public boolean isItalic(){
		return richTextArea.getFormatter().isItalic();
	}
	@Export
	public boolean isStrikethrough(){
		return richTextArea.getFormatter().isStrikethrough();
	}
	@Export
	public boolean isSubscript(){
		return richTextArea.getFormatter().isSubscript();
	}
	@Export
	public boolean isSuperscript(){
		return richTextArea.getFormatter().isSuperscript();
	}
	@Export
	public boolean isUnderlined(){
		return richTextArea.getFormatter().isUnderlined();
	}
	@Export
	public void leftIndent(){
		richTextArea.getFormatter().leftIndent();
	}
	@Export
	public void redo(){
		richTextArea.getFormatter().redo();
	}
	@Export
	public void removeFormat(){
		richTextArea.getFormatter().removeFormat();
	}
	@Export
	public void removeLink(){
		richTextArea.getFormatter().removeLink();
	}
	@Export
	public void rightIndent(){
		richTextArea.getFormatter().rightIndent();
	}
	@Export
	public void selectAll(){
		richTextArea.getFormatter().selectAll();
	}
	@Export
	public void setBackColor(String backColor){
		richTextArea.getFormatter().setBackColor(backColor);
	}
	@Export
	public void setFontName(String name){
		richTextArea.getFormatter().setFontName(name);
	}
	@Export
	public void setFontSize(FontSize fontSize){
		richTextArea.getFormatter().setFontSize(fontSize);
	}
	@Export
	public void setForeColor(String color){
		richTextArea.getFormatter().setForeColor(color);
	}
	@Export
	public void setJustification(Justification justification){
		richTextArea.getFormatter().setJustification(justification);
	}
	@Export
	public void toggleBold(){
		richTextArea.getFormatter().toggleBold();
	}
	@Export
	public void toggleItalic(){
		richTextArea.getFormatter().toggleItalic();
	}
	@Export
	public void toggleStrikethrough(){
		richTextArea.getFormatter().toggleStrikethrough();
	}
	@Export
	public void toggleSubscript(){
		richTextArea.getFormatter().toggleSubscript();
	}
	@Export
	public void toggleSuperscript(){
		richTextArea.getFormatter().toggleSuperscript();
	}
	@Export
	public void toggleUnderline(){
		richTextArea.getFormatter().toggleUnderline();
	}
	@Export
	public void undo(){
		richTextArea.getFormatter().undo();
	}
	@Override
	@Export
	public void setText(String text)
	{
		richTextArea.setText(text);
	}
	@Override
	@Export
	public String getText()
	{
		return richTextArea.getText();
	}
	
	@Override
	@Export
	public void setEnabled(boolean enabled)
	{
		richTextArea.setEnabled(enabled);
	}
	@Override
	@Export
	public boolean isEnabled()
	{
		return richTextArea.isEnabled();
	}
	@Export
	public void setFocus(boolean focused)
	{
		richTextArea.setFocus(focused);
	}
	@Override
	@Export
	public void setVisible(boolean isVisible)
	{
		super.setVisible(isVisible);
	}
	@Override
	@Export
	public boolean isVisible()
	{
		return super.isVisible();
	}
	@Override
	@Export
	public void addStyleName(String className)
	{
		super.addStyleName(className);
	}
	@Override
	@Export
	public void removeStyleName(String className)
	{
		super.removeStyleName(className);
	}
}
