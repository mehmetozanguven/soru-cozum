package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.Teacher;

public interface TeacherDAO {

	Optional<Teacher> getTeacherById(long teacherId);

	boolean checksTeacherExistsWithUsernameAndPassword(String name, String password);

	void registerNewTeacher(Teacher teacher);

}
