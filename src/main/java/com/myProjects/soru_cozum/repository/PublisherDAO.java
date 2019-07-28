package com.myProjects.soru_cozum.repository;

import java.util.Optional;

import com.myProjects.soru_cozum.model.Publisher;

public interface PublisherDAO {
	
	Optional<Publisher> findById(Long publisherId);

	Publisher createNewPublisher(Publisher publisher);
}
