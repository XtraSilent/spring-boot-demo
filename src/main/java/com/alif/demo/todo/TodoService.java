package com.alif.demo.todo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TodoService {

	private static final Logger logger = LoggerFactory.getLogger(TodoService.class);

	@Autowired
	private TodoRepository todoRepository;

	public List<Todo> findAll() {
		logger.info("Fetching all todos");
		return todoRepository.findAll();
	}

	public Todo findById(Long id) {
		logger.info("Fetching todo with id: {}", id);
		return todoRepository.findById(id)
				.orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
	}

	@Transactional
	public Todo save(Todo todo) {
		if (todo.getTaskName() == null || todo.getTaskName().trim().isEmpty()) {
			throw new IllegalArgumentException("Task name must not be null or empty");
		}

		logger.info("Saving todo: {}", todo);
		return todoRepository.save(todo);
	}

	@Transactional
	public void deleteById(Long id) {
		if (!todoRepository.existsById(id)) {
			throw new TodoNotFoundException("Todo not found with id: " + id);
		}
		logger.info("Deleting todo with id: {}", id);
		todoRepository.deleteById(id);
	}

	public Page<Todo> findPaginatedAndFiltered(int page, int size, String taskName) {
		Pageable pageable = PageRequest.of(page, size);
		logger.info("Fetching paginated todos - page: {}, size: {}, taskName: {}", page, size, taskName);
		return todoRepository.findByTaskName(taskName, pageable);
	}
}
