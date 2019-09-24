package com.myProjects.soru_cozum.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.myProjects.soru_cozum.chainPattern.teacher.answer.AnswerQuestionHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.answer.QuestionExistsHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.answer.TeacherAnswerAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.answer.TeacherAnswerRequest;
import com.myProjects.soru_cozum.chainPattern.teacher.answer.TeacherExistsHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.TeacherOldAnswerAudioHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.TeacherOldAnswerImageHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.TeacherUpdateAnswerAbstactHandler;
import com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.TeacherUpdateAnswerRequest;
import com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.UpdateAnswerHandler;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.model.json.AnsweredQuestionJSON;
import com.myProjects.soru_cozum.request.AnswerQuestionRequest;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.TeacherResponse;
import com.myProjects.soru_cozum.service.FileStorageService;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.TeacherService;

/**
 * @author mehmetozanguven
 *
 */
@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	private TeacherAnswerAbstractHandler teacherExists;
	private TeacherAnswerAbstractHandler questionExists;
	private TeacherAnswerAbstractHandler answerQuestion;
	
	private TeacherUpdateAnswerAbstactHandler teacherExists_updateHandler;
	private TeacherUpdateAnswerAbstactHandler oldAnswerImageHandler;
	private TeacherUpdateAnswerAbstactHandler oldAnswerAudioHandler;
	private TeacherUpdateAnswerAbstactHandler updateAnswerHandler;
	
	public TeacherController() {
		teacherExists = new TeacherExistsHandler();
		questionExists = new QuestionExistsHandler();
		answerQuestion = new AnswerQuestionHandler();
		
		teacherExists_updateHandler = new com.myProjects.soru_cozum.chainPattern.teacher.updateAnswer.TeacherExistsHandler();
		oldAnswerImageHandler = new TeacherOldAnswerImageHandler();
		oldAnswerAudioHandler = new TeacherOldAnswerAudioHandler();
		updateAnswerHandler = new UpdateAnswerHandler();
	}
	
	/**
	 * Returns all non-answered specific type questions
	 * @param questionCategory
	 * @return
	 */
	@GetMapping("/allQuestion/{categoryName}")
	public ResponseEntity<?> getAllNonAnsweredQuestionBySpecificType(@PathVariable("categoryName") QuestionCategory questionCategory){
		List<Question> allSpecificQuestions = questionService.getAllNonAnsweredQuestionsBySpecificType(questionCategory);
		return ResponseEntity.ok(allSpecificQuestions);
	}
	
	@GetMapping("/getQuestionImage/{questionId}")
	public ResponseEntity<?> getQuestionImageByQuestionId(@PathVariable("questionId") Long questionId){
		QuestionImage questionImage = questionService.getQuestionImageByQuestionId(questionId);
		if (questionImage.getId() == 0) {
			GenericResponse<TeacherResponse> response = new GenericResponse<TeacherResponse>();
			response.setStatu("Error");
			response.setInformation(new TeacherResponse("Invalid question image id"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		else
			return ResponseEntity.ok().body(questionImage);
	}
	
	@GetMapping("/getMyAnsweredQuestion/{teacherId}")
	public ResponseEntity<?> getAnsweredQuestionByTeacherId(@PathVariable(value = "teacherId") Long teacherId){
		Optional<Teacher> teacher = teacherService.findTeacherById(teacherId);
		if (!teacher.isPresent()) {
			GenericResponse<TeacherResponse> response = new GenericResponse<TeacherResponse>();
			response.setStatu("Error");
			response.setInformation(new TeacherResponse("Invalid Teacher id"));
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		List<AnsweredQuestionJSON> answerList = teacherService.getAnsweredQuestionByTeacherId(teacher.get());
		return new ResponseEntity<>(answerList, HttpStatus.OK);
	}
	
	/**
	 * Answer the specific question
	 * <ul>
	 * 	<li> Checks the teacher id is valid, if not return error </li>
	 * 	<li> Checks the question id is valid, if not return error </li>
	 * 	<li> Update teacher answer image and video properties </li>
	 * 	<li> Update Question and save to the database </li>
	 * </ul>
	 * @param answerQuestionRequest
	 * @return
	 */
	@PostMapping("/answerQuestion")
	public ResponseEntity<?> addAnswerImageToQuestion(@RequestPart(value = "answerImage") MultipartFile answerImage,
											@RequestPart(value = "teacherId") String teacherId_str,
											@RequestPart(value = "questionId") String questionId_str){
		
		Long teacherId = null;
		Long questionId = null;
		
		try {
			teacherId = Long.parseLong(teacherId_str);
			questionId = Long.parseLong(questionId_str);
		}catch (NumberFormatException e) {
			return new ResponseEntity<>("Invalid request parameters", HttpStatus.BAD_REQUEST);
		}
		
		Optional<Teacher> teacher = teacherService.findTeacherById(teacherId);
		Optional<Question> question = questionService.findQuestionById(questionId);
	
		TeacherAnswerRequest request = new TeacherAnswerRequest(questionService, teacherService, teacher, question, fileStorageService);
		request.setImageFile(answerImage);
		
		teacherExists.setNextHandler(questionExists);
		questionExists.setNextHandler(answerQuestion);
		return teacherExists.handle(request);
	}
	
	@PostMapping("/updateAnswer")
	public ResponseEntity<?> updateAnswer(@RequestBody AnswerQuestionRequest answerQuestionRequest){
		TeacherUpdateAnswerRequest request = new TeacherUpdateAnswerRequest(teacherService, answerQuestionRequest);
		
		teacherExists_updateHandler.setNextHandler(oldAnswerImageHandler);
		oldAnswerImageHandler.setNextHandler(oldAnswerAudioHandler);
		oldAnswerAudioHandler.setNextHandler(updateAnswerHandler);
		
		return teacherExists_updateHandler.handle(request);
	
	}
	
	
}
