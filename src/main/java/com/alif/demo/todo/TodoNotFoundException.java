package com.alif.demo.todo;

public class TodoNotFoundException extends RuntimeException {
	public TodoNotFoundException(String message) {
		super(message);
	}
}