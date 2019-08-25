package com.myProjects.soru_cozum.response;

import com.myProjects.soru_cozum.security.SecurePerson;

public class JwtAuthenticationResponse {
	private String accessToken;
	private SecurePerson securePerson;
	private String tokenType = "Bearer";

	public JwtAuthenticationResponse(String accessToken, SecurePerson securePerson) {
		this.accessToken = accessToken;
		this.securePerson = securePerson;
	}

	public SecurePerson getSecurePerson() {
		return securePerson;
	}

	public void setSecurePerson(SecurePerson securePerson) {
		this.securePerson = securePerson;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

}
