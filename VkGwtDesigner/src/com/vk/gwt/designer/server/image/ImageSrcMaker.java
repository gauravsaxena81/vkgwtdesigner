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
package com.vk.gwt.designer.server.image;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.sun.org.apache.xml.internal.security.utils.Base64;

public class ImageSrcMaker {
	private String getBase64EncodedImage(byte[] byteArray){
		return Base64.encode(byteArray);
	}
	private String getUploadedFileBase64Src(FileItem item) throws IOException {
		return "data:image/" + item.getName().substring(item.getName().length() - 3) + ";base64," +  getBase64EncodedImage(item.get());
	}

	public void writeToResponse(HttpServletResponse response, List<FileItem> fileItems) throws IOException {
		if(fileItems != null && !fileItems.isEmpty())
			response.getWriter().write(getUploadedFileBase64Src(fileItems.get(0)));
	}
}
