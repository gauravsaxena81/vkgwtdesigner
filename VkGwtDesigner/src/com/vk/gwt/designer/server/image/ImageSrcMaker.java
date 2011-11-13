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
