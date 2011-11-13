package com.vk.gwt.designer.server.filereader;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

public class VkFileReader {
	public void writeToResponse(HttpServletResponse response, List<FileItem> fileItems) throws IOException {
		if(fileItems != null && !fileItems.isEmpty())
			response.getWriter().write(getFileString(fileItems.get(0)));
	}

	private String getFileString(FileItem fileItem) {
		return new String(fileItem.get());
	}
}
