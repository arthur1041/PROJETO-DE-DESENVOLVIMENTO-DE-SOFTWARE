package com.challenge.todo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.challenge.todo.model.Task;
import com.challenge.todo.model.User;
import com.challenge.todo.repository.TaskRepository;

/**
 * Classe service utilizada como meio de campo com os repositories das tasks
 * Serve para proteger os dados por meio de manipulações dos mesmos nas consultas e inserções
 *
 * @author Carlos França
 * @version	1.0
*/

@Service
@Transactional
public class TaskService {

	private TaskRepository taskRepository;

	@Autowired
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public List<Task> findAllTasks() {
		return taskRepository.findAll();
	}

	public Task findById(int id) {
		return taskRepository.findById(id).orElse(null);
	}

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public void deleteTask(Task task) {
		taskRepository.delete(task);
	}

	public Task updateTask(Task task) {

		Optional<Task> optionalTask = taskRepository.findById(task.getId());

		Task taskDb = optionalTask.get();

		taskDb.setDescription(task.getDescription());
		taskDb.setDueDate(task.getDueDate());
		taskDb.setPriority(task.getPriority());

		return taskRepository.save(taskDb);

	}

	public List<Task> findByUser(User user) {
		return taskRepository.findByUser(user);
	}
}