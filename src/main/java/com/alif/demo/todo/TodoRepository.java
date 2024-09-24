package com.alif.demo.todo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	// find using task name or complete flag
	Page<Todo> findByTaskNameAndCompleted(String taskName, Boolean completed, Pageable pageable);

	// find using task name or complete flag
	Page<Todo> findByTaskName(String taskName,Pageable pageable);

	Page<Todo> findByCompleted(Boolean completed,Pageable pageable);
}