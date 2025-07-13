package qbe.service;

import java.util.List;

import qbe.model.Task;

public interface TaskService {
	List<Task> getAllTasks();

	List<Task> getSelectTasks(String fist_name, String last_name);

	Task saveTask(Task task);
	
	Task getTaskById(Long id);
	
	Task updateTask(Task task);
	
	void deleteTaskById(Long id);
}
