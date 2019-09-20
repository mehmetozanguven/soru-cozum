package com.myProjects.soru_cozum.util;

import java.util.HashMap;
import java.util.Map;

public class QuestionUrlParser {
	public static Map<String, String> parseTheQuestionDownloadURL(String questionUrl) {
		// split http(s):// and url
		String[] arr = questionUrl.split("//");
		
		if (arr == null || arr[1] == null) {
			return null;
		}
		String[] parseURL = arr[1].split("/");
		Map<String, String> parsedMap = new HashMap<String, String>();
//		localhost:8080/api/file/downloadFile/Questions/Student/1/1/qCategory/qSubCategory/13/12
		int count = 0;
		for (String eachPart : parseURL) {
			if (eachPart.length() == 10) {
				if (count == 5)
					parsedMap.put("studentID", eachPart);
				else if (count == 6)
					parsedMap.put("publisherId", eachPart);
				else if (count == 7)
					parsedMap.put("questionCategory", eachPart);
				else if (count == 8)
					parsedMap.put("questionSubCategory", eachPart);
				else if (count == 9)
					parsedMap.put("pageNumber", eachPart);
				else if (count == 10)
					parsedMap.put("questionNumber", eachPart);
				else {
					// do nothing
				}
			}else {
				if (count == 5)
					parsedMap.put("studentID", eachPart);
				else if (count == 6)
					parsedMap.put("publisherId", eachPart);
				else if (count == 7)
					parsedMap.put("questionCategory", eachPart);
				else if (count == 8)
					parsedMap.put("pageNumber", eachPart);
				else if (count == 9)
					parsedMap.put("questionNumber", eachPart);
				else {
					// do nothing
				}
			}
			
			count ++;
		}
		return parsedMap;
	}

}
