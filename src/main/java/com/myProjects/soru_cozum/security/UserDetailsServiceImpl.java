package com.myProjects.soru_cozum.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.Teacher;
import com.myProjects.soru_cozum.repository.StudentDAO;
import com.myProjects.soru_cozum.repository.TeacherDAO;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService{
	private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Value("${app.login.type.teacher}")
	private String teacherLoginType;
	
	@Value("${app.login.type.student}")
	private String studentLoginType;
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Autowired
	private TeacherDAO teacherDAO;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.debug("After Login Request because of configuration, our user details service will call this method -loadUserByUsername");
		LOGGER.debug("username parameter will be the name that we passed in UsernamePasswordAuthenticationToken user (request.getName(), request.getPassword()");

		String[] splitUsername = username.split(",");
		LOGGER.debug("login type: " + splitUsername[0] + " login username: " + splitUsername[1]);
		if (splitUsername[0].trim().equalsIgnoreCase(String.valueOf(studentLoginType))) {
			Optional<Student> student = studentDAO.findByUsername(splitUsername[1]);
			if (student.isPresent()) {
				return SecurePerson.createSecureStudentFromRepository(student.get());
			}else {
				 throw new UsernameNotFoundException("User not found with username or email");
			}
		}else if (splitUsername[0].trim().equalsIgnoreCase(teacherLoginType)) {
			Optional<Teacher> teacher = teacherDAO.findTeacherByUsername(splitUsername[1]);
			if (teacher.isPresent())
				return SecurePerson.createSecureTeacherFromRepository(teacher.get());
			else
				throw new UsernameNotFoundException("User not found with username or email");
		}else {
			throw new UsernameNotFoundException("You wrote wrong login type");
		}
		
		
	}
	
	/*public UserDetails loadUserByUsernameAndPassword(String username, String password) throws UsernameNotFoundException{
		Optional<Student> student = studentDAO.findByUsernameAndPassword(username, password);
		if (student.get().getName() == "nonce")
			throw new UsernameNotFoundException("Invalid username or password");
		else {
			return SecurePerson.createSecureStudentFromRepository(student.get());
		}
	}*/
	
	public UserDetails loadUserById(String loginType, Long userId) {
		if (loginType.equalsIgnoreCase(studentLoginType)) {
			Optional<Student> student = studentDAO.findStudentById(userId);
			if (student.isPresent())
				return SecurePerson.createSecureStudentFromRepository(student.get());
			else 
				throw new UsernameNotFoundException("Invalid token");
		}else if (loginType.equalsIgnoreCase(teacherLoginType)) {
			Optional<Teacher> teacher = teacherDAO.findTeacherById(userId);
			if (teacher.isPresent())
				return SecurePerson.createSecureTeacherFromRepository(teacher.get());
			else
				throw new UsernameNotFoundException("Invalid token");
		}else {
			throw new UsernameNotFoundException("Invalid token");
		}
		
	}
	
	
}
