package com.alif.demo.todo;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TodoConfig implements CommandLineRunner {

	private final Faker faker = new Faker();
	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void run(String... args) throws Exception {

		//generate fake data
		for (int i = 0; i < 100; i++) {
			String title = faker.lorem().sentence(5);
			boolean completed = faker.bool().bool();
			Todo todo = new Todo(null, title, completed);
			todoRepository.save(todo);
		}


	}
}
