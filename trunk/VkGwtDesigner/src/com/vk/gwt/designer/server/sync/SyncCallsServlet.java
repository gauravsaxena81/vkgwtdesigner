package com.vk.gwt.designer.server.sync;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.vk.gwt.designer.server.filereader.VkFileReader;
import com.vk.gwt.designer.server.image.ImageSrcMaker;
import com.vk.gwt.designer.server.upload.UploadHelper;
import com.vk.gwt.designer.server.upload.UploadStateHelper;

@SuppressWarnings("serial")
public class SyncCallsServlet extends HttpServlet{
	@Override
	public void init(){
		UploadStateHelper.getInstance().init(getServletContext());
	}
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response){
		try {
			List<FileItem> fileItems = RequestParser.parse(request, response);
			response.setContentType("text/html");
			if("imgUpload".equals(getParameter("op", fileItems))) {
				fileItems = UploadHelper.getInstance().doUpload(fileItems);
				new ImageSrcMaker().writeToResponse(response, fileItems);
			} else if ("downLoadLayoutFile".equals(getParameter("op", fileItems))) {
				response.setContentType("application/octet-stream"); 
		        response.setHeader("Content-disposition", "attachment;filename=layout.vk");
				response.getWriter().write(getParameter("downloadString", fileItems));
			} else if ("upLoadLayoutFile".equals(getParameter("op", fileItems))) {
				fileItems = UploadHelper.getInstance().doUpload(fileItems);
				new VkFileReader().writeToResponse(response, fileItems);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private String getParameter(String name, List<FileItem> fileItems) {
		for(FileItem i : fileItems)
			if(i.getFieldName().equals(name))
				return i.getString();
		return null;
	}
}
