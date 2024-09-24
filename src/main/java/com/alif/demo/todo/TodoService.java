package com.alif.demo.todo;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	public List<Todo> findAll() {
		return todoRepository.findAll();
	}

	public Optional<Todo> findById(Long id) {
		return todoRepository.findById(id);
	}

	public Todo save(Todo todo) {
		return todoRepository.save(todo);
	}

	public void deleteById(Long id) {
		todoRepository.deleteById(id);
	}

	public Page<Todo> findPaginatedAndFiltered(int page, int size, String taskName) {

		Pageable pageable = PageRequest.of(page, size);

		return todoRepository.findByTaskName(taskName, pageable);

	}
}
