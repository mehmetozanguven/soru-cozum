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
import org.springframework.core.env.Environment;
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

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.request.QuestionDownloadRequest;
import com.myProjects.soru_cozum.response.AddQuestionToStudent;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.StudentQuestionDownloadRequest;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;
import com.myProjects.soru_cozum.service.FileStorageService;

@RestController
@RequestMapping("/api/file")
public class FileController {
	private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileStorageService fileStorageService;
	
	@Autowired
	private Environment environment;
	
	@PostMapping("/uploadFile/TeacherAnswer")
	public ResponseEntity<?> uploadTeacherAnswer(@RequestPart(value = "image") MultipartFile answerImage,
												 @RequestPart(value = "audio") MultipartFile answerAudio,
												 @RequestPart(value = "questionPageNumber") String pageNumber,
												 @RequestPart(value = "questionNumber") String questionNumber,
												 @RequestPart(value = "publisherId") String publisherId,
												 @RequestPart(value = "category") String category,
												 @RequestPart(value = "subCategory") String subCategory){
		LOGGER.info(answerImage.getOriginalFilename());
		LOGGER.info(answerAudio.getOriginalFilename());
		return null;
	}
	
	/**
	 * Student will upload the question to the database with this method
	 * @return download url as a response
	 */
	@PostMapping(value = "/uploadFile/studentQuestion", consumes = {"multipart/form-data"})
	public ResponseEntity<?> uploadStudentQuestion(@RequestPart(value = "image") MultipartFile image,
												   @RequestPart(value = "studentId") String studentId, 
												   @RequestPart(value = "studentUsername") String username,
												   @RequestPart(value = "questionPageNumber") String pageNumber,
												   @RequestPart(value = "questionNumber") String questionNumber,
												   @RequestPart(value = "publisherId") String publisherId,
												   @RequestPart(value = "category") String category,
												   @RequestPart(value = "subCategory") String subCategory) {
		GenericResponse<StudentQuestionUploadResponse> response = new GenericResponse<StudentQuestionUploadResponse>();

		Long studentId_l =null;
		Long publisherId_l = null;
		Integer pageNum = null;
		Integer questionNum = null;
		
		QuestionCategory categoryFromValue;
		try {
			studentId_l = Long.parseLong(studentId);
			publisherId_l = Long.parseLong(publisherId);
			pageNum = Integer.parseInt(pageNumber);
			questionNum = Integer.parseInt(questionNumber);
			categoryFromValue = QuestionCategory.questionCategoryFromValue(category);
			categoryFromValue.getValue(); // don't remove this line otherwise nullpointer exception couldn't be caught
		}catch (NumberFormatException | NullPointerException e) {
			response.setStatu("Error");
			return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		LOGGER.info("Question category: " + categoryFromValue.getValue());
		StudentQuestionUploadResponse serviceResponse = fileStorageService.storeFile(image, 
															   StoreType.STUDENT_QUESTION, 
															   pageNum,
															   questionNum,
															   publisherId_l,
															   categoryFromValue,
															   subCategory);
		LOGGER.info("Question file path: " + serviceResponse);
//		if (serviceResponse.getResponse().startsWith("Sorry!")) {
//			response.setStatu("Error");
//			response.setInformation(serviceResponse);
//			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
		
		response.setStatu("Success");
		response.setInformation(serviceResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@PostMapping("/downloadFile/Question")
	public ResponseEntity<?> downloadStudentQuestion(@RequestBody StudentQuestionDownloadRequest userRequest, HttpServletRequest request){
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		LOGGER.info(userRequest.toString());
		
		// /Questions/Students
		Path prefixPath = Paths.get(environment.getProperty("file.uploadStudentDir")).normalize();
		Path questionRelativePath;
		if (userRequest.getQuestionSubCategory() != ""){
			questionRelativePath = Paths.get(userRequest.getPublisherId() + "/" 
					+ userRequest.getQuestionCategory() + "/" + userRequest.getQuestionSubCategory() + "/" + 
					userRequest.getPageNumber() + "/" +  userRequest.getQuestionNumber());
		}else {
			questionRelativePath = Paths.get(userRequest.getPublisherId() + "/" 
					+ userRequest.getQuestionCategory() + "/" + 
					userRequest.getPageNumber() + "/" +  userRequest.getQuestionNumber());
		}
		
		File isQuestionFileExists = prefixPath.resolve(questionRelativePath).toFile();
		if (!isQuestionFileExists.isFile()) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("There is no file."));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		Resource resource = fileStorageService.loadFileAsResource(questionRelativePath.toString(), StoreType.STUDENT_QUESTION);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException | NullPointerException ex) {
			LOGGER.info("Exception: " + ex.getMessage() );
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
	
	/**
	 * //localhost:8080/api/file/downloadFile/Questions/Student/1/1/Matematik/H%C3%BCcreler/13/12
	 * Student or Teacher will download the question's image with this method.
	 * @return
	 */
	@GetMapping("/downloadFile/Questions/Student/{studentFolder}/{publisherSubFolder}/{questionCategory}/{questionSubCategory}/{pageNumber}/{imageFile}")
	public ResponseEntity<?> downloadStudentQuestion(@PathVariable("studentFolder") String studentFolder,
										  @PathVariable("publisherSubFolder") String publisherSubFolder,
										  @PathVariable("questionCategory") String questionCategory,
										  @PathVariable("questionSubCategory") String questionSubCategory,
										  @PathVariable("pageNumber") String pageNumber,
										  @PathVariable("imageFile") String imageFile,
										  HttpServletRequest request){
		
		File isFileExists = Paths.get("Questions/Student/" + studentFolder + "/" + publisherSubFolder + "/" 
		+ questionCategory + "/" + questionSubCategory + "/" + pageNumber + "/" +  imageFile).toFile();
		
		LOGGER.info("downloadFile: " + isFileExists.toString());
		LOGGER.info("downloadFile absolute file path: " + isFileExists.getAbsolutePath());
		
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		
		if (!isFileExists.isFile()) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("There is no file."));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		String relativePath = isFileExists.toString();
		LOGGER.info("Relative path is: " + relativePath);
		Resource resource = fileStorageService.loadFileAsResource(relativePath, StoreType.STUDENT_QUESTION);
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
	


	private String createFileDownloadUri(String questionFilePath) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(questionFilePath)
                .toUriString();
		return fileDownloadUri;
	}
}
