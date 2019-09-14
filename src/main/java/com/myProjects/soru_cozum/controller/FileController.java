package com.myProjects.soru_cozum.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.request.QuestionDownloadRequest;
import com.myProjects.soru_cozum.response.AddQuestionToStudent;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.service.FileStorageService;

@RestController
@RequestMapping("/api/file")
public class FileController {
	private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@PostMapping("/uploadFile/studentQuestion")
	public ResponseEntity<?> uploadStudentQuestion(@RequestPart(value = "image") MultipartFile image,
												   @RequestPart(value = "id") String studentId, 
												   @RequestPart(value = "username") String username,
												   @RequestPart(value = "pageNumber") String pageNumber,
												   @RequestPart(value = "questionNumber") String questionNumber,
												   @RequestPart(value = "publisherId") String publisherId) {
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		int fileTypeStartIndex = image.getOriginalFilename().lastIndexOf(".") + 1;
		String fileType = image.getOriginalFilename().substring(fileTypeStartIndex);
		
		Long studentId_l =null;
		Long pageNum = null;
		Long publisherId_l = null;
		Long questionNum = null;
		try {
			studentId_l = Long.parseLong(studentId);
			pageNum = Long.parseLong(pageNumber);
			publisherId_l = Long.parseLong(publisherId);
			questionNum = Long.parseLong(questionNumber);
		}catch (NumberFormatException e) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("Can't convert to String to Number"));
			return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String questionFilePath = fileStorageService.storeFile(image, 
															   StoreType.STUDENT_QUESTION, 
															   username,  
															   studentId_l,
															   pageNum,
															   questionNum,
															   publisherId_l);
		LOGGER.info("Question file path: " + questionFilePath);
		if (questionFilePath.startsWith("Sorry!")) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("Can't store the file"));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String downloadUri = createFileDownloadUri(questionFilePath);
		
		response.setStatu("Success");
		response.setInformation(new AddQuestionToStudent(downloadUri + "." + fileType));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/downloadFile/Questions/Student/{studentFolder}/{publisherSubFolder}/{imageFile}")
	public ResponseEntity<?> downloadFile(@PathVariable("studentFolder") String studentFolder,
										  @PathVariable("publisherSubFolder") String publisherSubFolder,
										  @PathVariable("imageFile") String imageFile,
										  HttpServletRequest request){
		
		File checkFileExists = Paths.get("Questions/Student/" + studentFolder + "/" + publisherSubFolder + "/" + imageFile).toFile();
		
		LOGGER.info("downloadFile: " + checkFileExists.toString());
		LOGGER.info("downloadFile absolute: " + checkFileExists.getAbsolutePath());
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		
		if (!Files.exists(Paths.get(request.getServletContext().getRealPath(
				"Questions/Student/" + studentFolder + "/" + publisherSubFolder + "/" + imageFile)))) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("There is no file."));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Resource resource = fileStorageService.loadFileAsResource(checkFileExists.toString(), StoreType.STUDENT_QUESTION);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			LOGGER.info("Could not determine file type.");
		}
		// Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
		
	}
	
//	@PostMapping(value = "/downloadSingleQuestion")
//	public ResponseEntity<?> downloadSingleStudentQuestion(@RequestBody QuestionDownloadRequest userRequest, HttpServletRequest request){
//		String filePath = userRequest.getUserId()+"!"+userRequest.getUsername()+"/"+
//				  userRequest.getPublisherId()+"/"+
//				  userRequest.getPageNumber()+"!"+userRequest.getQuestionNumber();
//		File checkFileExists = Paths.get("Questions/Student/"+filePath).toAbsolutePath().normalize().toFile();
//		LOGGER.info("File: " + checkFileExists.getAbsolutePath());
//		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
//		
//		if (!checkFileExists.exists()) {
//			response.setStatu("Error");
//			response.setInformation(new AddQuestionToStudent("There is no file."));
//			new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//		}
//		
//		LOGGER.info("Filename is: " + filePath);
//		
//		Resource resource = fileStorageService.loadFileAsResource(filePath, StoreType.STUDENT_QUESTION);
//		String contentType = null;
//		try {
//			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//		} catch (IOException ex) {
//			LOGGER.info("Could not determine file type.");
//		}
//		// Fallback to the default content type if type could not be determined
//        if(contentType == null) {
//            contentType = "application/octet-stream";
//        }
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
//	}

	private String createFileDownloadUri(String fileName) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();
		return fileDownloadUri;
	}
}
