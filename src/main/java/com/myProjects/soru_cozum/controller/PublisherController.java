package com.myProjects.soru_cozum.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myProjects.soru_cozum.model.Publisher;
import com.myProjects.soru_cozum.request.NewPublisherRequest;
import com.myProjects.soru_cozum.response.GenericResponse;
import com.myProjects.soru_cozum.service.PublisherService;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
	
	@Autowired
	private PublisherService publisherService;
	
	@PostMapping("/new")
	public ResponseEntity<?> registerNewPublisher(@Valid @RequestBody NewPublisherRequest request){
		Publisher newPublisher = new Publisher();
		newPublisher.setName(request.getPublisherName());
		newPublisher.setPublishYear(request.getPublishYear());
		
		Optional<Long> publishId = publisherService.registerNewPublisher(newPublisher);
		GenericResponse<String> response = new GenericResponse<String>();
		if (!publishId.isPresent()) {
			response.setStatu("Error");
			response.setInformation("Can't create new publisher");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setStatu("Success");
		response.setInformation("new publisher created");
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
