package com.vk.gwt.designer.server.upload;

import java.io.File;

import javax.servlet.ServletContext;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.FileCleanerCleanup;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileCleaningTracker;

public class UploadStateHelper {
	private static UploadStateHelper uploadStateHelper = new UploadStateHelper();
	private DiskFileItemFactory factory;
	private ServletFileUpload upload;
	
	private UploadStateHelper(){}
	
	public static UploadStateHelper getInstance(){
		return uploadStateHelper;
	}
	public void init(ServletContext context){
		File REPOSITORY = new File(".");
		FileCleaningTracker fileCleaningTracker = FileCleanerCleanup.getFileCleaningTracker(context);
		factory = new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, REPOSITORY);
		factory.setFileCleaningTracker(fileCleaningTracker);
		upload = new ServletFileUpload(factory);
		upload.setSizeMax(1048576);//1MB
	}
	public DiskFileItemFactory getDiskFileItemFactory() {
		return factory;
	}
	public ServletFileUpload getServletFileUpload(){
		return upload;
	}
}
