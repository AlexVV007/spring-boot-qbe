package qbe.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import qbe.model.Task;
import qbe.repository.TaskRepository;
import qbe.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	
	public TaskServiceImpl(TaskRepository taskRepository) {
		super();
		this.taskRepository = taskRepository;
	}

	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	@Override
	public List<Task> getSelectTasks(String fist_name, String last_name) {
		return taskRepository.findByFirstNameAndLastName(fist_name, last_name);}

	@Override
	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public Task getTaskById(Long id) {
		return taskRepository.findById(id).get();
	}

	@Override
	public Task updateTask(Task task) {
		return taskRepository.save(task);
	}

	@Override
	public void deleteTaskById(Long id) {
		taskRepository.deleteById(id);
	}

}
