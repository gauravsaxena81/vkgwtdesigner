package com.vk.gwt.designer.client.designer;

import java.util.ArrayList;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

class SnapHelper {
	private static SnapHelper snapHelper = new SnapHelper();
	public final int SNAP_TO_FIT_TOP = 20;
	public final int SNAP_TO_FIT_LEFT = 20;
	private PopupPanel snapToTargetLineVertical;
	private ArrayList<Widget> snappableWidgets;
	private PopupPanel snapToTargetLineHorizontal;
	
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
	public int getSnappedLeft(int left){
		int newLeft = SNAP_TO_FIT_LEFT * (left / SNAP_TO_FIT_LEFT);
		int origCalculatedLeft = newLeft;
		newLeft = getHorizontalSnapTarget(newLeft);
		if(newLeft != -1){
			snapToTargetLineVertical.setPixelSize(1, Window.getClientHeight());
			snapToTargetLineVertical.setPopupPosition(newLeft, 0);
			snapToTargetLineVertical.show();
			return newLeft;
		} else
			return origCalculatedLeft;
	}
	
	private int getHorizontalSnapTarget(int pos) {
		int max = SNAP_TO_FIT_LEFT;
		for(int i = 0, len = snappableWidgets.size(); i < len; i++) {
			int absLeft = snappableWidgets.get(i).getAbsoluteLeft();
			int width = snappableWidgets.get(i).getOffsetWidth();
			if(Math.abs(absLeft - pos) < max) {
				max = Math.abs(absLeft - pos);
				pos = absLeft;
			} 
			if(Math.abs(absLeft + width - pos) < max){
				max = Math.abs(absLeft + width - pos);
				pos = absLeft + width;
			}
		}
		if(max == SNAP_TO_FIT_LEFT)
			return -1;
		else
			return pos;
	}
	
	private int getVerticalSnapTarget(int pos) {
		int max = SNAP_TO_FIT_TOP;
		for(int i = 0, len = snappableWidgets.size(); i < len; i++) {
			int absTop = snappableWidgets.get(i).getAbsoluteTop();
			int height = snappableWidgets.get(i).getOffsetHeight();
			if(Math.abs(absTop - pos) < max) {
				max = Math.abs(absTop - pos);
				pos = absTop;
			} 
			if(Math.abs(absTop + height - pos) < max){
				max = Math.abs(absTop + height - pos);
				pos = absTop + height;
			}
		}
		if(max == SNAP_TO_FIT_LEFT)
			return -1;
		else
			return pos;
	}

	public int getSnappedTop(int top){
		int newTop = SNAP_TO_FIT_TOP * (top / SNAP_TO_FIT_TOP);
		int origCalculatedTop = newTop;
		newTop = getVerticalSnapTarget(newTop);
		if(newTop != -1){
			snapToTargetLineHorizontal.setPixelSize(Window.getClientWidth(), 1);
			snapToTargetLineHorizontal.setPopupPosition(0, newTop);
			snapToTargetLineHorizontal.show();
			return newTop;
		} else
			return origCalculatedTop;
	}
	
	public int getSnappedWidth(int left, int width) {
		int newRight = left + (SNAP_TO_FIT_LEFT * (width / SNAP_TO_FIT_LEFT));
		int origCalculatedRight = newRight;
		newRight = getHorizontalSnapTarget(newRight);
		if(newRight != -1){
			snapToTargetLineVertical.setPixelSize(1, Window.getClientHeight());
			snapToTargetLineVertical.setPopupPosition(newRight, 0);
			snapToTargetLineVertical.show();
			return newRight - left;
		} else
			return origCalculatedRight - left;
		
	}
	
	public int getSnappedHeight(int top, int height) {
		int newBottom = top + (SNAP_TO_FIT_TOP * (height / SNAP_TO_FIT_TOP));
		int origCalculatedBottom = newBottom;
		newBottom = getVerticalSnapTarget(newBottom);
		if(newBottom != -1){
			snapToTargetLineHorizontal.setPixelSize(Window.getClientWidth(), 1);
			snapToTargetLineHorizontal.setPopupPosition(0, newBottom);
			snapToTargetLineHorizontal.show();
			return newBottom - top;
		} else
			return origCalculatedBottom - top;
		
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
}
