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
