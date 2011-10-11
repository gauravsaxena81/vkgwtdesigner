package com.vk.gwt.designer.server.upload;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;

public class UploadHelper {
	private static UploadHelper helper = new UploadHelper();
	
	private UploadHelper(){}
	
	public static UploadHelper getInstance(){
		return helper;
	}
	public List<FileItem> doUpload(List<FileItem> items) throws IOException{
		Iterator<FileItem> iter = items.iterator();
		while (iter.hasNext())
		    if (iter.next().isFormField()) 
		    	iter.remove();
		return items;
	}
}
