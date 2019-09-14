package com.myProjects.soru_cozum.chainPattern.fileStorage;

import java.io.File;

public class DirectoryHandler extends FileStorageAbstractHandler {

	@Override
	public String handle(FileStorageRequest request) {
		File isStudentFolderExists = new File(request.getStudentFolder().toString());
		
		if (!isStudentFolderExists.exists())
			isStudentFolderExists.mkdir();
		
		File isPublishFolderExists = new File(request.getPublisherSubFolder().toString());
		
		if (!isPublishFolderExists.exists())
			isPublishFolderExists.mkdir();
		
		return getNextHandler().handle(request);
	}
	
	
}
