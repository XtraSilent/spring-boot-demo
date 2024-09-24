package com.alif.demo.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);


	@GetMapping
	public String index() {
		return "hello world";
	}

	@GetMapping("/all")
	public ResponseEntity<?> findAll() {
		try {
			List<Todo> todos = todoService.findAll();

			logger.info("Todos get all: {}", todos.stream().map(Todo::toString));

			return ResponseEntity.ok(todos);
		} catch (Exception e) {
			logger.error("error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching todos: " + e.getMessage());
		}
	}

	@PostMapping()
	public ResponseEntity<?> saveTodo(@RequestBody Todo todo) {
		try {
			Todo res = todoService.save(todo);

			logger.info("Todo save: {}", res);
			return ResponseEntity.status(HttpStatus.CREATED).body(res);
		} catch (Exception e) {
			logger.error("error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving todo: " + e.getMessage());
		}
	}

	@PutMapping
	public ResponseEntity<?> updateTodo(@RequestBody Todo todo) {
		try {
			Optional<Todo> res = todoService.findById(todo.getId());
			if (res.isPresent()) {
				Todo updatedTodo = todoService.save(todo);
				logger.info("Todo update: {}", updatedTodo);
				return ResponseEntity.ok(updatedTodo);
			} else {
				logger.info("Todo update =>", "Todo not found");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
			}
		} catch (Exception e) {
			logger.error("error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating todo: " + e.getMessage());
		}
	}

	@DeleteMapping
	public ResponseEntity<?> deleteTodo(@RequestBody Todo todo) {
		try {
			Optional<Todo> res = todoService.findById(todo.getId());
			if (res.isPresent()) {
				todoService.deleteById(todo.getId());
				logger.info("Todo delete =>", "success");
				return ResponseEntity.ok().body("Todo deleted successfully");
			} else {
				logger.info("Todo delete =>", "failed");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
			}
		} catch (Exception e) {
			logger.error("error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting todo: " + e.getMessage());
		}
	}

	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam(defaultValue = "0") int page,
	                                @RequestParam(defaultValue = "10") int size,
	                                @RequestParam(required = false) String taskName) {
		try {
			Page<Todo> res = todoService.findPaginatedAndFiltered(page, size, taskName);
			logger.info("Todos get all: {}", res.stream().map(Todo::toString));
			return ResponseEntity.ok(res);
		} catch (Exception e) {
			logger.error("error: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching todos: " + e.getMessage());
		}
	}
}
