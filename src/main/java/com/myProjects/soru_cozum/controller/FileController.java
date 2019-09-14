package com.myProjects.soru_cozum.controller;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.myProjects.soru_cozum.enums.StoreType;
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
			@RequestPart(value = "id") String studentId, @RequestPart(value = "username") String username) {
		GenericResponse<AddQuestionToStudent> response = new GenericResponse<AddQuestionToStudent>();
		
		Long studentId_l = Long.parseLong(studentId);
		String questionFilePath = fileStorageService.storeFile(
				image, StoreType.STUDENT_QUESTION, studentId_l, username);
		LOGGER.info("Question file path: " + questionFilePath);
		if (questionFilePath.startsWith("Sorry!")) {
			response.setStatu("Error");
			response.setInformation(new AddQuestionToStudent("Can't store the file"));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String downloadUri = createFileDownloadUri(questionFilePath);
		
		response.setStatu("Success");
		response.setInformation(new AddQuestionToStudent(downloadUri));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/downloadSingleQuestion/{filename}")
	public ResponseEntity<?> downloadSingleStudentQuestion(@PathVariable("filename") String fileName, HttpServletRequest request){
		LOGGER.info("Filename is: " + fileName);
		Resource resource = fileStorageService.loadFileAsResource(fileName, StoreType.STUDENT_QUESTION);
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
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}

	private String createFileDownloadUri(String fileName) {
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/downloadFile/")
                .path(fileName)
                .toUriString();
		return fileDownloadUri;
	}
}
