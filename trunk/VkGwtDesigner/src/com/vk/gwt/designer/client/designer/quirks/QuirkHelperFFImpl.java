package com.vk.gwt.designer.client.designer.quirks;

import com.google.gwt.user.client.Element;
import com.vk.gwt.designer.client.designer.VkDesignerUtil;

public class QuirkHelperFFImpl extends QuirkHelper{
	public int getOffsetTop(Element elem){
		return elem.getOffsetTop() + VkDesignerUtil.getPixelValue((Element) elem.getOffsetParent(), "border-top-width");
	}
	public int getOffsetLeft(Element elem) {
		return elem.getOffsetLeft() + VkDesignerUtil.getPixelValue((Element) elem.getOffsetParent(), "border-left-width");
	}
}
