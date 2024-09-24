package com.alif.demo.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/advice")
public class AdviceController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/get")
	public String getAdvice() {
		String apiUrl = "https://api.adviceslip.com/advice";

		try {
			ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
			return response.getBody();
		} catch (Exception e) {
			throw e;
		}

	}
}
