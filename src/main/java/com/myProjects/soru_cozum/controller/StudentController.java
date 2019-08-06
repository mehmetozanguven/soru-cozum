package com.myProjects.soru_cozum.controller;



import java.io.File;
import java.io.FileInputStream;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.enums.QuestionCategory;
import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.model.Question;
import com.myProjects.soru_cozum.model.QuestionImage;
import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.request.AddQuestionToStudentRequest;
import com.myProjects.soru_cozum.response.AddQuestionToStudentErrorResponse;
import com.myProjects.soru_cozum.response.StudentQuestionAnswerResponse;
import com.myProjects.soru_cozum.service.PublisherService;
import com.myProjects.soru_cozum.service.QuestionService;
import com.myProjects.soru_cozum.service.StudentService;

import net.bytebuddy.implementation.bytecode.Addition;


/**
 * Rest controller class for students
 * @author mehmetozanguven
 *
 */
@RestController
@RequestMapping("/student")
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);	
	
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private PublisherService publisherService;
	
	@Autowired
	private QuestionService questionService;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findStudentById(@PathVariable(value="id") Long studentId) {
		logger.debug(String.valueOf(studentId));
		Student student = studentService.findById(studentId);
		if (student.getName() != "nonce")
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
	public ResponseEntity<?> addQuestionToStudent(@RequestBody AddQuestionToStudentRequest addQuestionToStudentRequest){
		// Check the student exists
		Student student = studentService.findById((long)addQuestionToStudentRequest.getStudentId());
		
		if (student.getName() == "nonce") 
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"));
		
		// Check the publisher exists, if not create new one
		Publisher publisher = publisherService.findById((long) addQuestionToStudentRequest.getPublisher().getId());
		logger.debug("Publisher from database by id: " + publisher);
		// If there is no publisher with these properties, then this is the new question
		if (publisher.getName() == "nonce") {
			Publisher newPublisher = publisherService.createNewPublisherFromRequest(addQuestionToStudentRequest.getPublisher());
			// true means that, add new publisher to question
			Question newQuestion = createNewQuestion(addQuestionToStudentRequest, true, newPublisher);
			
			// Add Question to Student
			student = studentService.addQuestionToStudent(student, newQuestion);
			studentService.updateStudent(student);

			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "Your question was sent to our teacher"));
			
		}
		
		// Check the question asked before that student

		Question isStudentAskedThatQuestionBefore = studentService.isStudentAskedThatQuestionBefore(student, publisher,
				addQuestionToStudentRequest.getPageNumber(), addQuestionToStudentRequest.getQuestionNumber());
		logger.debug("Did student ask that question before ?:"  + isStudentAskedThatQuestionBefore.getId());

		if (isStudentAskedThatQuestionBefore.getId() != 0) {
			if (isStudentAskedThatQuestionBefore.isAnswered())
				return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You asked that question before and it was answered by a teacher check your answer list"));
			else
				return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You asked that question before, answer waiting"));
		}
		
		Question isQuestionAskedBySomeone = questionService.findQuestionByPageNumberQuestionNumberPublisher(
				addQuestionToStudentRequest.getPageNumber(), addQuestionToStudentRequest.getQuestionNumber(),
				addQuestionToStudentRequest.getPublisher());
		logger.debug("Did anyone ask that question before ?:"  + isQuestionAskedBySomeone.getId());
		// Question exists:
		if (isQuestionAskedBySomeone.getId() != 0) {
			student = studentService.addQuestionToStudent(student, isQuestionAskedBySomeone);
			studentService.updateStudent(student);
			return ResponseEntity.ok()
					.body(new AddQuestionToStudentErrorResponse("Success", "Question already have been asked by someone, we added to your list"));

		}
		
		// After all, this is new question (absolutely!!!)
		Question newQuestion = createNewQuestion(addQuestionToStudentRequest, true, publisher);
		// Add Question to Student
		student = studentService.addQuestionToStudent(student, newQuestion);
		studentService.updateStudent(student);

		return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "QuestionAdded"));
	}

	
	@GetMapping("/getStudentQuestionList/{id}")
	public ResponseEntity<?> getStudentQuestionListById(@PathVariable("id") String studentId) {
		// Check the student exists
		Student student = studentService.findById(Long.valueOf(studentId));

		if (student.getName() == "nonce")
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"));
		
		List<StudentQuestionAnswerResponse> questionList = studentService.getQuestionList(student);
		
		if (questionList == null || questionList.isEmpty())
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "You didn't ask any question"));
		
		return ResponseEntity.ok().body(questionList);
	}
	
	@GetMapping("/getStudentAnswerList/{id}")
	public ResponseEntity<?> getStudentAnswerListById(@PathVariable("id") String studentId) {
		// Check the student exists
		Student student = studentService.findById(Long.valueOf(studentId));

		if (student.getName() == "nonce")
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Error", "Invalid Student ID"));
		
		List<StudentQuestionAnswerResponse> answerList = studentService.getAnswerList(student);
		
		if (answerList == null || answerList.isEmpty())
			return ResponseEntity.ok().body(new AddQuestionToStudentErrorResponse("Success", "No answer yet"));
		
		return ResponseEntity.ok().body(answerList);
	}
	
	private Question createNewQuestion(AddQuestionToStudentRequest addQuestionToStudentRequest, boolean isNewPublisher,
				Publisher newPublisher) {
		int pageNumber = addQuestionToStudentRequest.getPageNumber();
		int questionNumber = addQuestionToStudentRequest.getQuestionNumber();
		QuestionCategory questionCategory = addQuestionToStudentRequest.getQuestionCategory();
		String questionSubCategory = addQuestionToStudentRequest.getQuestionSubCategory();
		tempConvertStringToImageByte(addQuestionToStudentRequest);
		byte[] questionImageByte = addQuestionToStudentRequest.getImageByte();
		
		Question newQuestion = questionService.createNewQuestionWithCommonProperties(pageNumber, questionNumber, questionCategory,
												questionSubCategory, questionImageByte);
		
		/*newQuestion = questionService.addIsAnsweredProperty(newQuestion,false);
		newQuestion = questionService.addPageNumberToQuestion(newQuestion, addQuestionToStudentRequest.getPageNumber());
		newQuestion = questionService.addQuestionNumberToQuestion(newQuestion, addQuestionToStudentRequest.getQuestionNumber());
		newQuestion = questionService.addQuestionCategory(newQuestion, addQuestionToStudentRequest.getQuestionCategory());
		newQuestion = questionService.addQuestionSubCategory(newQuestion, addQuestionToStudentRequest.getQuestionSubCategory());
		tempConvertStringToImageByte(addQuestionToStudentRequest);
		QuestionImage questionImage = questionService.createNewQuestionImage(addQuestionToStudentRequest.getImageByte());
		
		// Add image to question 
		newQuestion = questionService.addQuestionImageToQuestion(newQuestion, questionImage);*/
		if (isNewPublisher)
			questionService.addPublisherToQuestionn(newQuestion, newPublisher);
		return newQuestion;
		
	}

	private void tempConvertStringToImageByte(AddQuestionToStudentRequest addQuestionToStudentRequest) {
		File file = new File(addQuestionToStudentRequest.getFilePath());
        byte[] bFile = new byte[(int) file.length()];
        
        try {
	     FileInputStream fileInputStream = new FileInputStream(file);
	     //convert file into array of bytes
	     fileInputStream.read(bFile);
	     fileInputStream.close();
        } catch (Exception e) {
	     e.printStackTrace();
        }
        
        addQuestionToStudentRequest.setImageByte(bFile);
		
	}
}
