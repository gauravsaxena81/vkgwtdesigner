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
