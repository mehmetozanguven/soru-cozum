package com.myProjects.soru_cozum.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.myProjects.soru_cozum.enums.Department;

@Entity
@Table(name = "SS_TEACHER_DETAILS")
public class TeacherDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEACHER_DETAILS_ID")
	private Long id;

	@Column(name = "SCHOOL_NAME")
	private String schoolName;

	@Column(name = "DEPARTMENT")
	private String department;
	
	@OneToOne(mappedBy = "teacherDetails", cascade = CascadeType.ALL)
	private Teacher teacher;

	public TeacherDetails() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getTeacherDepartment() {
		return department;
	}

	public void setTeacherDepartment(Department teacherDepartment) {
		this.department = teacherDepartment.getValue();
	}

	@Override
	public String toString() {
		return "TeacherDetails [id=" + id + ", schoolName=" + schoolName + ", teacherDepartment=" + department
				+ "]";
	}

}
