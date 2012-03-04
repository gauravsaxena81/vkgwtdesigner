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
package com.vk.gwt.designer.server.sync;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.vk.gwt.designer.server.upload.UploadStateHelper;

public class RequestParser {
	@SuppressWarnings({ "unchecked" })
	public static List<FileItem> parse(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			ServletFileUpload upload = UploadStateHelper.getInstance().getServletFileUpload();
			return upload.parseRequest(request);
		} catch (SizeLimitExceededException e){
			response.getWriter().write(HttpServletResponse.SC_LENGTH_REQUIRED);
			return null;
		} catch (FileUploadException e) {
			response.getWriter().write(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}
}
