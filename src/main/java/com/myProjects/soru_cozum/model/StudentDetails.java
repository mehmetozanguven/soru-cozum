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
@Table(name = "SS_STUDENT_DETAILS")
public class StudentDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STUDENT_DETAILS_ID")
	private Long id;
	
	@Column(name = "SCHOOL_NAME")
	private String schoolName;
	
	@Column(name = "CLASS")
	private String classNum;
	
	@Column(name = "DEPARTMENT")
	private String department;
	
	@OneToOne(mappedBy = "studentDetails", cascade = CascadeType.ALL)
	private Student student;

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department.getValue();
	}
	
	
}
