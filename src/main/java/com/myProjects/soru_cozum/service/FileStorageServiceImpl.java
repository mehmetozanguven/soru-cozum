package com.myProjects.soru_cozum.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image.AnswerImageCreatorHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image.AnswerImageDirectoryHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image.FileAnswerImageStorageAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.answerUpload.image.FileAnswerImageStorageRequest;
import com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload.DirectoryHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload.FileCreatorHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload.FileQuestionStorageAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.fileStorage.questionUpload.FileQuestionStorageRequest;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.enums.StoreType;
import com.myProjects.soru_cozum.request.service.StudentQuestionUploadRequest;
import com.myProjects.soru_cozum.request.service.TeacherAnswerAudioUploadServiceRequest;
import com.myProjects.soru_cozum.request.service.TeacherAnswerImageUploadServiceRequest;
import com.myProjects.soru_cozum.response.AnswerImageResponse;
import com.myProjects.soru_cozum.response.StudentQuestionUploadResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerAudioServiceResponse;
import com.myProjects.soru_cozum.response.service.TeacherAnswerImageServiceResponse;

@Service
@Transactional
public class FileStorageServiceImpl implements FileStorageService {
	private final Logger LOGGER =LoggerFactory.getLogger(FileStorageServiceImpl.class);
	private String DEFAULT_SUB_CATEGORY = "default";
	private String ANSWER_IMAGE_DIR = "Answer/image";
	private String ANSWER_AUDIO_DIR = "Answer/Audio";
	private String IMAGE_DIR_NAME = "Image";
	private String AUDIO_DIR_NAME = "Audio";
	private String ANSWER_DIR_NAME = "Answer";
	
	private Path questionsFileStorageLocation;
	private Path questionsRelativeFileStorageLocaltion;
	
	private FileQuestionStorageAbstractHandler fileCreatorHandler;
	private FileQuestionStorageAbstractHandler directoryHandler;
	
	/**
	 * FileStorage Constructor will create default folders:
	 * @param environment
	 */
	@Autowired
    public FileStorageServiceImpl(Environment environment) {
		this.fileCreatorHandler = new FileCreatorHandler();
		this.directoryHandler = new DirectoryHandler();
		
		this.IMAGE_DIR_NAME = environment.getProperty("file.imageDirName");
		this.AUDIO_DIR_NAME = environment.getProperty("file.audioDirName");
		this.ANSWER_DIR_NAME = environment.getProperty("file.answerDirName");
		
		this.ANSWER_IMAGE_DIR = environment.getProperty("file.answerImageDir");
		this.ANSWER_AUDIO_DIR = environment.getProperty("file.answerAudioDir");
		this.DEFAULT_SUB_CATEGORY = environment.getProperty("question.defaultSubCategory");

		
		LOGGER.debug(environment.getProperty("file.question_answer_dir"));
        this.questionsFileStorageLocation = Paths.get(environment.getProperty("file.question_answer_dir"))
                .toAbsolutePath().normalize();
        this.questionsRelativeFileStorageLocaltion = Paths.get(environment.getProperty("file.question_answer_dir")).normalize();
        LOGGER.info("student file storage: " + questionsRelativeFileStorageLocaltion);

        try {
        	Files.createDirectories(questionsRelativeFileStorageLocaltion);
        } catch (Exception ex) {
            LOGGER.error("Can't inject file storage location for student & teacher. THIS WILL GIVE AN ERROR.");
            LOGGER.error("Error message: " + ex);
        }
    }
	
	@Override
	public Optional<TeacherAnswerAudioServiceResponse> storeTeacherAnswerAudio(TeacherAnswerAudioUploadServiceRequest serviceRequest){
		TeacherAnswerAudioServiceResponse response = new TeacherAnswerAudioServiceResponse();
		
		String questionPath_str = serviceRequest.getPublisherId() + "/" + serviceRequest.getQuestionCategory() + "/"
				+ serviceRequest.getQuestionSubCategory() + "/" + serviceRequest.getPageNumber();
		Path questionPath = questionsRelativeFileStorageLocaltion.resolve(questionPath_str);
		File isQuestionDirExists = questionPath.toFile();

		if (!isQuestionDirExists.exists())
			return Optional.empty();
		
		Path audioPath = createAnswerAudioDirectory(questionPath);
		Path audioFile = audioPath.resolve(serviceRequest.getTeacherId() + "_" + serviceRequest.getQuestionId());
		
		try {
			Files.copy(serviceRequest.getAnswerAudioFile().getInputStream(), audioFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			LOGGER.error("Error happening when uploading file");
			LOGGER.error(e.getMessage());
			return Optional.empty();
		}
		
		response.setPageNumber(serviceRequest.getPageNumber());
		response.setPublisherId(serviceRequest.getPublisherId());
		response.setQuestionCategory(serviceRequest.getQuestionCategory());
		response.setQuestionId(serviceRequest.getQuestionId());
		response.setQuestionNumber(serviceRequest.getQuestionNumber());
		response.setQuestionSubCategory(serviceRequest.getQuestionSubCategory());
		response.setTeacherId(serviceRequest.getTeacherId());
		
		return Optional.ofNullable(response);

	}
	
	@Override
	public Optional<TeacherAnswerImageServiceResponse> storeTeacherAnswerImage(TeacherAnswerImageUploadServiceRequest serviceRequest) {
		LOGGER.info("Find the question path...");
		TeacherAnswerImageServiceResponse response = new TeacherAnswerImageServiceResponse();
		
		String questionPath_str = serviceRequest.getPublisherId() +"/"+ serviceRequest.getQuestionCategory() +"/"+
								  serviceRequest.getQuestionSubCategory() +"/"+ serviceRequest.getPageNumber();
		Path questionPath = questionsRelativeFileStorageLocaltion.resolve(questionPath_str);
		File isQuestionDirExists = questionPath.toFile();
		
		// If question path doesn't exists, answer will be unnecessary
		if (!isQuestionDirExists.exists())
			return Optional.empty();
		
		Path imagePath = createAnswerImageDirectory(questionPath);
		Path imageFile = imagePath.resolve(serviceRequest.getTeacherId() + "_" + serviceRequest.getQuestionId());
		
		try {
			Files.copy(serviceRequest.getAnswerImageFile().getInputStream(), imageFile, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			LOGGER.error("Error happening when uploading file");
			LOGGER.error(e.getMessage());
			return Optional.empty();
		}
		
		response.setPageNumber(serviceRequest.getPageNumber());
		response.setPublisherId(serviceRequest.getPublisherId());
		response.setQuestionCategory(serviceRequest.getQuestionCategory());
		response.setQuestionId(serviceRequest.getQuestionId());
		response.setQuestionNumber(serviceRequest.getQuestionNumber());
		response.setQuestionSubCategory(serviceRequest.getQuestionSubCategory());
		response.setTeacherId(serviceRequest.getTeacherId());
		
		return Optional.ofNullable(response);
	}

	@Override
	public Optional<StudentQuestionUploadResponse> storeStudentQuestion_new(StudentQuestionUploadRequest serviceRequest){
		StudentQuestionUploadResponse response = new StudentQuestionUploadResponse();
			
		Path publishFolder = this.questionsRelativeFileStorageLocaltion.resolve(String.valueOf(serviceRequest.getPublisherId()));
		Path questionCategoryFolder = publishFolder.resolve(serviceRequest.getQuestionCategory());
		
		Path questionSubCategoryFolder = null;
		LOGGER.info(serviceRequest.getQuestionSubCategory().equalsIgnoreCase("nonce") + " ");
		Path pageNumberFolder = questionCategoryFolder.resolve(String.valueOf(serviceRequest.getPageNumber()));
		if (serviceRequest.getQuestionSubCategory().equalsIgnoreCase("nonce")) {
			LOGGER.debug("Empty sub category, default will be provided");
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else if (serviceRequest.getQuestionSubCategory().isEmpty()) {
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else {
			questionSubCategoryFolder = questionCategoryFolder.resolve(serviceRequest.getQuestionSubCategory());
			pageNumberFolder = questionSubCategoryFolder.resolve(String.valueOf(serviceRequest.getPageNumber()));
		}
		
		FileQuestionStorageRequest handlerRequest = FileQuestionStorageRequest.createFileStorageFromServiceRequest(serviceRequest);

		handlerRequest.setPublisherSubFolder(publishFolder);
		handlerRequest.setQuestionCategoryFolder(questionCategoryFolder);
		handlerRequest.setQuestionSubCategoryFolder(questionSubCategoryFolder);
		handlerRequest.setPageNumberFolder(pageNumberFolder);
		
		directoryHandler.setNextHandler(fileCreatorHandler);
		response = directoryHandler.handle(handlerRequest);
		return Optional.ofNullable(response);
	}
	
	@Override
	public StudentQuestionUploadResponse storeStudentQuestion(MultipartFile file, 
							StoreType types, 
							Integer pageNumber,
							Integer questionNumber,
							Long publisherId, 
							String questionCategory, 
							String questionSubCategory) {
		if (types == null)
			return new StudentQuestionUploadResponse();
		LOGGER.info("Question category: " + questionCategory);
		LOGGER.info("Question sub-category: " + questionSubCategory);
		Path publishFolder = this.questionsRelativeFileStorageLocaltion.resolve(String.valueOf(publisherId));
		Path questionCategoryFolder = publishFolder.resolve(questionCategory);
		
		Path questionSubCategoryFolder = null;
		LOGGER.info(questionSubCategory.equalsIgnoreCase("nonce") + " ");
		Path pageNumberFolder = questionCategoryFolder.resolve(String.valueOf(pageNumber));
		if (questionSubCategory.equalsIgnoreCase("nonce")) {
			LOGGER.debug("Empty sub category, default will be provided");
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else if (questionSubCategory.isEmpty()) {
			questionSubCategoryFolder = questionCategoryFolder.resolve(DEFAULT_SUB_CATEGORY);
		}else {
			questionSubCategoryFolder = questionCategoryFolder.resolve(questionSubCategory);
			pageNumberFolder = questionSubCategoryFolder.resolve(String.valueOf(pageNumber));
		}
		
		FileQuestionStorageRequest handlerRequest = new FileQuestionStorageRequest(file, types, pageNumber, questionNumber, publisherId);

		handlerRequest.setPublisherSubFolder(publishFolder);
		handlerRequest.setQuestionCategoryFolder(questionCategoryFolder);
		handlerRequest.setQuestionSubCategoryFolder(questionSubCategoryFolder);
		handlerRequest.setPageNumberFolder(pageNumberFolder);
		
		directoryHandler.setNextHandler(fileCreatorHandler);

		return directoryHandler.handle(handlerRequest);
	}

	@Override
	public Resource loadFileAsResource(String fileName, StoreType types) {
        try {
        	Path filePath = null;
        	
        	filePath = this.questionsRelativeFileStorageLocaltion.resolve(fileName).normalize();
 
            LOGGER.info("File Path after normalize: " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
            	LOGGER.error("File not found " + fileName);
                return null;
            }
        } catch (MalformedURLException ex) {
        	LOGGER.error("File not found " + fileName + " " + ex);
            return null;
        }
    }
	
	@Override
	public Optional<Resource> loadFileAsResource_opt(String fileName) {
        try {
        	Path filePath = null;
        	
        	filePath = this.questionsFileStorageLocation.resolve(fileName).normalize();
 
            LOGGER.info("File Path after normalize: " + filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return Optional.ofNullable(resource);
            } else {
            	LOGGER.error("File not found " + fileName);
                return Optional.empty();
            }
        } catch (MalformedURLException ex) {
        	LOGGER.error("File not found " + fileName + " " + ex);
            return Optional.empty();
        }
    }


	@Override
	public String createAnswerImageFilePath(TeacherAnswerImageServiceResponse userRequest) {
		String filePath = userRequest.getPublisherId() + "/" + userRequest.getQuestionCategory() + "/" +
						  userRequest.getQuestionSubCategory() + "/" + userRequest.getPageNumber() + "/" +
						  ANSWER_IMAGE_DIR + "/" + userRequest.getTeacherId() + "_" + userRequest.getQuestionId();
				
		return filePath;
	}

	@Override
	public String createAnswerAudioFilePath(TeacherAnswerAudioServiceResponse userRequest) {
		String filePath = userRequest.getPublisherId() + "/" + userRequest.getQuestionCategory() + "/" +
				  userRequest.getQuestionSubCategory() + "/" + userRequest.getPageNumber() + "/" +
				  ANSWER_AUDIO_DIR + "/" + userRequest.getTeacherId() + "_" + userRequest.getQuestionId();
		return filePath;
	}

	/////////////// Private methods ////////////////////
	private Path createAnswerAudioDirectory(Path questionPath) {
		Path answerPath = questionPath.resolve(ANSWER_DIR_NAME);

		File isAnswerDirExists = answerPath.toFile();

		if (!isAnswerDirExists.exists())
			isAnswerDirExists.mkdir();

		Path audioPath = answerPath.resolve(AUDIO_DIR_NAME);

		File isImageDirExists = audioPath.toFile();

		if (!isImageDirExists.exists())
			isImageDirExists.mkdir();
		return audioPath;
	}
	
	private Path createAnswerImageDirectory(Path questionPath) {
		Path answerPath = questionPath.resolve(ANSWER_DIR_NAME);

		File isAnswerDirExists = answerPath.toFile();

		if (!isAnswerDirExists.exists())
			isAnswerDirExists.mkdir();

		Path imagePath = answerPath.resolve(IMAGE_DIR_NAME);

		File isImageDirExists = imagePath.toFile();

		if (!isImageDirExists.exists())
			isImageDirExists.mkdir();
		return imagePath;
	}
	
	
}
