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

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

public class SnapHelper {
	private static SnapHelper snapHelper = new SnapHelper();
	public final int SNAP_TO_FIT_TOP = 20;
	public final int SNAP_TO_FIT_LEFT = 20;
	private PopupPanel snapToTargetLineVertical;
	private ArrayList<Widget> snappableWidgets;
	private PopupPanel snapToTargetLineHorizontal;
	private Widget ignoreWidget;
	
	private SnapHelper(){
		snappableWidgets = new ArrayList<Widget>();
		snapToTargetLineVertical = new PopupPanel(){
			@Override
			public void show(){
				super.show();
				new Timer(){
					@Override
					public void run() {
						hide();
					}}.schedule(1000);
			}
		};
		snapToTargetLineVertical.setWidget(new HTML());
		snapToTargetLineVertical.setStyleName("none");
		snapToTargetLineVertical.getWidget().setStyleName("vk-snap-to-target");
		snapToTargetLineHorizontal = new PopupPanel(){
			@Override
			public void show(){
				super.show();
				new Timer(){
					@Override
					public void run() {
						hide();
					}}.schedule(1000);
			}
		};
		snapToTargetLineHorizontal.setWidget(new HTML());
		snapToTargetLineHorizontal.setStyleName("none");
		snapToTargetLineHorizontal.getWidget().setStyleName("vk-snap-to-target");
	}
	public static SnapHelper getInstance(){
		return snapHelper;
	}
	public int getSnappedLeft(int left, int width){
		int snappedLeft = SNAP_TO_FIT_LEFT * (int)Math.ceil((double)left / SNAP_TO_FIT_LEFT);
		int leftWhenLeftSnapped = getHorizontalSnapElementTarget(left);
		int leftWhenRightSnapped = getHorizontalSnapElementTarget(left + width);
		if(leftWhenRightSnapped != -1 && Math.abs(leftWhenRightSnapped - width - left) < Math.abs(leftWhenLeftSnapped - left) && Math.abs(leftWhenRightSnapped - width - left) < Math.abs(snappedLeft - left)){
			snapToTargetLineVertical.setPixelSize(1, Window.getClientHeight());
			snapToTargetLineVertical.setPopupPosition(leftWhenRightSnapped, 0);
			snapToTargetLineVertical.show();
			return leftWhenRightSnapped - width;
		} else if(leftWhenLeftSnapped != -1 && Math.abs(leftWhenLeftSnapped - left) < Math.abs(snappedLeft - left)){
			snapToTargetLineVertical.setPixelSize(1, Window.getClientHeight());
			snapToTargetLineVertical.setPopupPosition(leftWhenLeftSnapped, 0);
			snapToTargetLineVertical.show();
			return leftWhenLeftSnapped;
		} else
			return snappedLeft;
	}
	
	public int getSnappedTop(int top, int height){
		int snappedTop = SNAP_TO_FIT_TOP * (int) Math.ceil((double)top / SNAP_TO_FIT_TOP);
		int topWhenTopSnapped = getVerticalSnapElementTarget(top);
		int topWhenBottomSnapped = getVerticalSnapElementTarget(top  + height);
		if(topWhenBottomSnapped != -1 && Math.abs(topWhenBottomSnapped - height - top) < Math.abs(topWhenTopSnapped - top) && Math.abs(topWhenBottomSnapped - height - top) < Math.abs(snappedTop - top)){
			snapToTargetLineHorizontal.setPixelSize(Window.getClientWidth(), 1);
			snapToTargetLineHorizontal.setPopupPosition(0, topWhenBottomSnapped);
			snapToTargetLineHorizontal.show();
			return topWhenBottomSnapped - height;
		} else if(topWhenTopSnapped != -1 && Math.abs(topWhenTopSnapped - top) < Math.abs(snappedTop - top)){
			snapToTargetLineHorizontal.setPixelSize(Window.getClientWidth(), 1);
			snapToTargetLineHorizontal.setPopupPosition(0, topWhenTopSnapped);
			snapToTargetLineHorizontal.show();
			return topWhenTopSnapped;
		} else
			return snappedTop;
	}
	
	public int getSnappedWidth(int left, int width) {
		int snappedWidth = (int)(SNAP_TO_FIT_LEFT * Math.ceil((double)width / SNAP_TO_FIT_LEFT));
		int newRight = left + snappedWidth;
		newRight = getHorizontalSnapElementTarget(newRight);
		if(newRight != -1 && Math.abs(newRight - left - width) < Math.abs(snappedWidth - width)){
			snapToTargetLineVertical.setPixelSize(1, Window.getClientHeight());
			snapToTargetLineVertical.setPopupPosition(newRight, 0);
			snapToTargetLineVertical.show();
			return newRight - left;
		} else
			return snappedWidth;
	}
	
	public int getSnappedHeight(int top, int height) {
		int snappedHeight = (int)(SNAP_TO_FIT_TOP * Math.ceil((double)height / SNAP_TO_FIT_TOP));
		int newBottom = getVerticalSnapElementTarget(top + height);
		if(newBottom != -1 && Math.abs(newBottom - top - height) < Math.abs(snappedHeight - height)) {
			snapToTargetLineHorizontal.setPixelSize(Window.getClientWidth(), 1);
			snapToTargetLineHorizontal.setPopupPosition(0, newBottom);
			snapToTargetLineHorizontal.show();
			return newBottom - top;
		} else
			return snappedHeight;
	}
	private int getHorizontalSnapElementTarget(int pos) {
		int max = SNAP_TO_FIT_LEFT;
		int originalPos = pos;
		for(int i = 0, len = snappableWidgets.size(); i < len; i++) {
			if(!snappableWidgets.get(i).equals(ignoreWidget)) {
				int absLeft = snappableWidgets.get(i).getAbsoluteLeft();
				int width = snappableWidgets.get(i).getOffsetWidth();
				int borderLeftWidth = VkDesignerUtil.getPixelValue(snappableWidgets.get(i).getElement(), "border-left-width");
				int borderRightWidth = VkDesignerUtil.getPixelValue(snappableWidgets.get(i).getElement(), "border-right-width");
				if(Math.abs(absLeft - originalPos) < max) {
					pos = absLeft;
					max = Math.abs(pos - originalPos);
				} 
				if(Math.abs(absLeft - originalPos + borderLeftWidth) < max) {
					pos = absLeft + borderLeftWidth;
					max = Math.abs(pos - originalPos);
				}
				if(Math.abs(absLeft + width - borderRightWidth - originalPos) < max) {
					pos = absLeft + width - borderRightWidth;
					max = Math.abs(pos - originalPos);
				}
				if(Math.abs(absLeft + width - originalPos) < max) {
					pos = absLeft + width;
					max = Math.abs(pos - originalPos);
				}
			}
		}
		if(max == SNAP_TO_FIT_LEFT)
			return -1;
		else
			return pos;
	}
	
	private int getVerticalSnapElementTarget(int pos) {
		int max = SNAP_TO_FIT_TOP;
		int originalPos = pos;
		for(int i = 0, len = snappableWidgets.size(); i < len; i++) {
			if(!snappableWidgets.get(i).equals(ignoreWidget)) {
				int absTop = snappableWidgets.get(i).getElement().getAbsoluteTop();
				int height = snappableWidgets.get(i).getOffsetHeight();
				int borderTopWidth = VkDesignerUtil.getPixelValue(snappableWidgets.get(i).getElement(), "border-top-width");
				int borderBottomWidth = VkDesignerUtil.getPixelValue(snappableWidgets.get(i).getElement(), "border-bottom-width");
				if(Math.abs(absTop - originalPos) < max) {
					pos = absTop;
					max = Math.abs(pos - originalPos);
				} 
				if(Math.abs(absTop - originalPos + borderTopWidth) < max) {
					pos = absTop + borderTopWidth;
					max = Math.abs(pos - originalPos);
				}
				if(Math.abs(absTop + height - originalPos) < max){
					pos = absTop + height;
					max = Math.abs(pos - originalPos);
				}
				if(Math.abs(absTop + height - originalPos - borderBottomWidth) < max){
					pos = absTop + height - borderBottomWidth;
					max = Math.abs(pos - originalPos);
				}
			}
		}
		if(max == SNAP_TO_FIT_LEFT)
			return -1;
		else
			return pos;
	}
	public void addToSnappableWidgets(Widget widget){
		snappableWidgets.add(widget);
	}
	public void removeFromSnappableWidgets(Widget widget){
		snappableWidgets.remove(widget);
	}
	public void init() {
		snappableWidgets.clear();
	}
	public Widget getIgnoreWidget() {
		return ignoreWidget;
	}
	public void setIgnoreWidget(Widget ignoreWidget) {
		this.ignoreWidget = ignoreWidget;
	}
}
