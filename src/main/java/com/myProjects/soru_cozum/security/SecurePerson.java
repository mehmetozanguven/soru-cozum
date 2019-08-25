package com.myProjects.soru_cozum.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.myProjects.soru_cozum.model.Student;
import com.myProjects.soru_cozum.model.Teacher;

public class SecurePerson implements UserDetails {
	private static final long serialVersionUID = -4163947338475737094L;
	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public static SecurePerson createSecureStudentFromRepository(Student student) {
		return new SecurePerson(student.getId(), student.getUsername(), student.getPassword());
	}
	
	public static SecurePerson createSecureTeacherFromRepository(Teacher teacher) {
		return new SecurePerson(teacher.getId(), teacher.getUsername(), teacher.getPassword());
	}

	public SecurePerson(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return "SecureStudentUser [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

}
