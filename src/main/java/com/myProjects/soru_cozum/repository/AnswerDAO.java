package com.myProjects.soru_cozum.repository;

import com.myProjects.soru_cozum.model.AnswerAudio;
import com.myProjects.soru_cozum.model.AnswerImage;

public interface AnswerDAO {
	void deleteAnswerImage(AnswerImage deletedImage);
	
	void deleteAnswerAudio(AnswerAudio deletedAudio) ;
}
