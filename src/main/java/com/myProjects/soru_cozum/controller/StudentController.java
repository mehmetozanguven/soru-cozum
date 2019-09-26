package com.myProjects.soru_cozum.controller;



import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.StudentAskQuestionAbstractHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.FileStorageHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.NewQuestionHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.PublisherExistsHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.StudentAskQuestionRequest;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.SomeoneAskThatQuestionHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.StudentAskThatQuestionHandler;
import com.myProjects.soru_cozum.chainPattern.studentAskQuestion.StudentExistsHandler;
import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.json.StudentQuestionJSON;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.response.StudentAskQuestionResponse;
import com.myProjects.soru_cozum.service.FileStorageService;
import com.myProjects.soru_cozum.service.PublisherServiceImpl;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.StudentService;



/**
 * Rest controller class for students
 * @author mehmetozanguven
 *
 */
@RestController
@RequestMapping("/api/student")
public class StudentController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);		
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PublisherServiceImpl publisherService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private FileStorageService fileStorageService;
	
	private StudentAskQuestionAbstractHandler isStudentExistsHandler;
	private StudentAskQuestionAbstractHandler isPublisherExistsCheckHandler;
	private StudentAskQuestionAbstractHandler isStudentAskQuestionHandler;
	private StudentAskQuestionAbstractHandler isSomeoneAskQuestionHandler;
	private StudentAskQuestionAbstractHandler newQuestionHandler;
	private StudentAskQuestionAbstractHandler fileStorageHandler;
	
	public StudentController() {
		isStudentExistsHandler = new StudentExistsHandler();
		isPublisherExistsCheckHandler = new PublisherExistsHandler();
		isStudentAskQuestionHandler = new StudentAskThatQuestionHandler();
		isSomeoneAskQuestionHandler = new SomeoneAskThatQuestionHandler();
		fileStorageHandler = new FileStorageHandler();
		newQuestionHandler = new NewQuestionHandler();		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findStudentById(@PathVariable(value="id") Long studentId) {
		LOGGER.debug(String.valueOf(studentId));
		Optional<Student> student = studentService.findById(studentId);
		if (!student.isPresent())
			return ResponseEntity.ok().body(student);
		else
			return ResponseEntity.notFound().build();
	}
	
	/**
	 * Add Question to Student.
	 * <b> Request body have to include the following properties: </b>
	 * <li> Student object </li>
	 * <li> Publisher(object) of the question </li>
	 * <li> Question page and question number </li> 
	 * <li> Byte array representation of image </li>
	 * </br>
	 * Here are the steps that must be followed:
	 * <ul>
	 * 		<li> Check whether database have that student or not: </li>
	 * 		<ul>
	 * 			<li> If not, return an error "Wrong information" </li>
	 * 		</ul>
	 * 		<b> Intermediate steps: </b>
	 * 		<li> Check, I have that Publisher in my database: </li>
	 * 		<ul>
	 * 			<li> If I don't have, then it means that this is absolutely, new question (don't need to check line after 83)</li>
	 * 			<li> Create new publisher and question </li>
	 * 			<li> Add Question to Student question list, and return "Your question was sent to our teacher" </li>
	 * 		</ul>
	 * 		<li> Check whether student ask that question before or not </li>
	 * 		<li> If asked before, check whether it is answered by any teacher:</li>
	 * 		<ul>
	 * 			<li> If answered by a teacher, return "You asked this before and it is answered check your answer list" </li>
	 * 			<li> Else, return "You asked this before, please wait the answer </li>
	 * 		</ul>
	 * 		
	 * 		<li> Else, check whether that question asked before by another student or not(search question database): </li>
	 * 		<ul>
	 * 			<li> If asked before someone, get the question from database then: </li>
	 * 			<ul>
	 * 				<li> Check it is answered by a teacher or not </li>
	 * 				<li> If it is answered, add Question to Student question list and answer list return 
	 * 						"Someone asked that question and it was answered by our teacher" </li>
	 * 				<li> If it is not answered, add Question to Student Question list and return
	 * 						"Someone asked that question and answer is waiting </li>
	 * 			</ul> 
	 * 			<li> If no one asked before, (assumed that I have known that publisher in my database), create new Question </li>
	 * 			<li> Set properties of that Question </li>
	 * 			<li> Add new Question to Student question list and return "Your question was sent to our teacher </li>
	 * 		</ul>
	 * </ul>
	 * @param addQuestionToStudentRequest
	 * @return
	 */
	@PostMapping("/addQuestionToStudent")
	public ResponseEntity<?> uploadStudentQuestion(@RequestPart(value = "image") MultipartFile image,
			   @RequestPart(value = "studentId") String studentId, 
			   @RequestPart(value = "studentUsername") String username,
			   @RequestPart(value = "questionPageNumber") String pageNumber,
			   @RequestPart(value = "questionNumber") String questionNumber,
			   @RequestPart(value = "publisherId") String publisherId,
			   @RequestPart(value = "category") String category,
			   @RequestPart(value = "subCategory") String subCategory){
		LOGGER.info("Student will try to  add new Question in his list");
		GenericResponse<StudentAskQuestionResponse> response = new GenericResponse<StudentAskQuestionResponse>();
		
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
			response.setInformation(new StudentAskQuestionResponse("Can't convert to String to Number/null pointer exception"));
			return  new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		Optional<Student> student = studentService.findById(studentId_l);
		Optional<Publisher> publisher = publisherService.findById(publisherId_l);
				
		StudentAskQuestionRequest request = new StudentAskQuestionRequest(studentService, questionService,
				publisherService, fileStorageService);
		request.setStudent(student);
		request.setPublisher(publisher);
		request.setQuestionCategory(category);
		request.setQuestionSubCategory(subCategory);
		request.setPageNumber(pageNum);
		request.setQuestionNumber(questionNum);
		request.setQuestionMultipartFile(image);

		isStudentExistsHandler.setNextHandler(isPublisherExistsCheckHandler);
		isPublisherExistsCheckHandler.setNextHandler(isStudentAskQuestionHandler);
		isStudentAskQuestionHandler.setNextHandler(isSomeoneAskQuestionHandler);
		isSomeoneAskQuestionHandler.setNextHandler(fileStorageHandler);
		fileStorageHandler.setNextHandler(newQuestionHandler);
		
		return isStudentExistsHandler.handle(request);	
	}

	@GetMapping("/getStudentQuestionList/{id}")
	public ResponseEntity<?> getStudentQuestionListById(@PathVariable("id") String studentId) {
		// Check the student exists
		Optional<Student> student = studentService.findById(Long.valueOf(studentId));

		if (!student.isPresent())
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"));
		
		List<StudentQuestionJSON> lists = studentService.getQuestionList_new(student.get());
		
		if (lists == null || lists.isEmpty()) {
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You didn't ask any question"));
		}
		
		return ResponseEntity.ok().body(lists);
	}
	
	@GetMapping("/getStudentAnswerList/{id}")
	public ResponseEntity<?> getStudentAnswerListById(@PathVariable("id") String studentId) {
		// Check the student exists
		Optional<Student> student = studentService.findById(Long.valueOf(studentId));

		if (!student.isPresent())
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"));
		
		List<StudentQuestionJSON> answerList = studentService.getStudentAnswerList(student.get());
		
		if (answerList == null || answerList.isEmpty())
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "No answer yet"));
		
		return ResponseEntity.ok().body(answerList);
	}
	
}
