<!DOCTYPE html>

<html  xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Hello</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script></head>
<body>
	<div class="container">
		<h1>This page contains api information for Soru-Cozum application</h1>
		<p> There are 4 basic endpoints:</p>
		<ul>
			<li><code>.../login/**</code> => For logging operation. <b>No token needed</b></li>
			<li><code>.../signup/**</code> => For signup operation. <b>No token needed</b></li>
			<li><code>.../api/**</code> => All other operations except login and signup. <b>Token needed</b></li>
			<li><code>.../info</code> => This page. <b>No token needed</b></li>
		</ul>
		<div class="alert alert-primary" role="alert">
			<h2>Signup endpoint (POST Request)</h2>
			<ol>
				<li>classNum can be empty or not for teacher signup request</li>
				<li>departmant attibute must be : "SAY, SOZ, EST, DIL"</li>
				<li>Username must be unique for each student and teacher </li>
			</ol>
			{ 
			"name": "ozan", <br>
			"surname":"guven",  <br>
			"username": "ozan",  <br>
			"password":"1234",  <br>
			"schoolName":"IYTE",  <br>
			"department":"SAY", <br>
			"classNum":"3"  <br>
			}
		</div>
		<p>We thought that there will be no signup page in the mobile application. We will add the students/teachers ourselves.</p>
		<div class="alert alert-secondary" role="alert">
			<h2>.../signup/student</h2>
		</div>
		<p>Successful request and response:</p>
		<img src="https://firebasestorage.googleapis.com/v0/b/sample-images-4c3c8.appspot.com/o/1.png?alt=media&token=b6999a6c-6f61-4de3-8782-55d5e52c10cb" class="img-fluid" alt="1.png Successful response"/>
		<p>If username already taken by someone:</p>
		<img src="https://firebasestorage.googleapis.com/v0/b/sample-images-4c3c8.appspot.com/o/2.png?alt=media&token=1cdb0e6b-740a-41d4-a24c-fe2c9c59a5a5" class="img-fluid" alt="2.png Username already taken">
		<div class="alert alert-secondary" role="alert">
			<h2>.../signup/teacher</h2>
			<p>Same response with student </p>
		</div>
		<div class="alert alert-primary" role="alert">
			<h2>Login endpoint (POST Request)</h2>
			<ol>
				<li>loginType must be either S (represents the student login) or T (represents the teacher login)</li>
			</ol>
			{ <br> "username":"ozan1",<br> "password":"1234",
			<br> "loginType":"S" <br> }
		</div>
		<p class="text-success">Successful response will return something like that: </p>
		<code>
			{<br> "accessToken":
			"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJTLDEiLCJpYXQiOjE1NjcyNTMxNjcsImV4cCI6MTU2Nzg1Nzk2N30.kghiuUGwUMvqEjgNRLtH8SrLplLOpMMYKVAOPPUYYRXbRXL_4vPKF9GWIlxClGuGV8JetoVIq9yuuoyg4drf2A",<br>
			"securePerson": {<br> "id": 1,<br> "username": "ozan",<br>
			"password":
			"$2a$10$rXb/6EMkf/oeFFWs.HfxIeg/N0fOn0JM5fO6UZhyrPrEWYq1DnKqG",<br>
			"authorities": null,<br> "accountNonExpired": true,<br>
			"accountNonLocked": true,<br> "credentialsNonExpired": true,<br>
			"enabled": true<br> }<br> }<br>
		</code><br>

		<p class="text-danger">Wrong credentials will return:</p><br>
		<code>
			{<br> "timestamp": "2019-08-31T12:10:29.869+0000",<br>
			"status": 401,<br> "error": "Unauthorized",<br> "message":
			"Access denied",<br> "trace":
			"org.springframework.security.authentication.BadCredentialsException:
			Bad credentials\n\tat ....<br>}
		</code>
		<div class="alert alert-primary" role="alert">
			<h2>/API</h2>
		</div>
		<div class="alert alert-secondary" role="alert">
			<h2>Get all questions that asked by StudentId (GET Request) </h2>
			<p>Get all questions that asked by a student. If StudentId not fount, it will return an error</p>
		</div>
		<p>Successful request:</p>
		<img src="https://firebasestorage.googleapis.com/v0/b/sample-images-4c3c8.appspot.com/o/4.png?alt=media&token=2224a73c-7583-43aa-8cfd-f2537972ad5d" class="img-fluid" alt="4.png Successful response"/>
		<p>If student id is invalid:</p>
		<img src="https://firebasestorage.googleapis.com/v0/b/sample-images-4c3c8.appspot.com/o/6.png?alt=media&token=c33118ec-294d-43de-aba0-337c74e80ffc" class="img-fluid" alt="6.png StudentId invalid">
		
	</div>
</body>
</html>