package qbe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import qbe.model.Task;
import qbe.service.TaskService;

@Controller
public class TaskController {
	
	private final TaskService taskService;

	public TaskController(TaskService taskService) {
		super();
		this.taskService = taskService;
	}
	
	// handler method to handle list tasks and return mode and view
	@GetMapping("/tasks")
	public String listTasks(Model model) {
		model.addAttribute("task", taskService.getAllTasks());
		return "tasks";
	}
	
	@GetMapping("/task/new")
	public String createTaskForm(Model model) {
		
		// create task object to hold task form data
		Task task = new Task();
		model.addAttribute("task", task);
		return "create_task";
		
	}
	
	@PostMapping("/tasks")
	public String saveTask(@RequestParam String action,
							  @ModelAttribute("task") Task task,
							  Model model) {
		switch (action) {
			case "generate":
				model.addAttribute("requestAdd",
				" INSERT INTO tasks (first_name, last_name, email, task_content) VALUES (\'"+
						task.getFirstName()+"\', \'"+task.getLastName()+
			            "\', \'"+task.getEmail()+"\', \'"+task.getTask_content()+"\')");
				model.addAttribute("first_name", task.getFirstName());
				model.addAttribute("last_name", task.getLastName());
				model.addAttribute("email", task.getEmail());
				model.addAttribute("task_content", task.getTask_content());
				return "create_task";
			case "run":
				taskService.saveTask(task);
				return "redirect:/tasks";
		}
		return "redirect:/tasks";
	}
	
	@GetMapping("/tasks/edit/{id}")
	public String editTaskForm(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskService.getTaskById(id));
		return "edit_task";
	}

	@PostMapping("/tasks/{id}")
	public String updateTask(@RequestParam String action, @PathVariable Long id,
			@ModelAttribute("task") Task task,
			Model model) {
		Task existingTask = taskService.getTaskById(id);
		switch (action) {
			case "generate":
				String textRequest ="update task set" ;
				if (!existingTask.getFirstName().equals(task.getFirstName())) {
					textRequest +=" first_name = \'"+task.getFirstName()+"\'";
				}
				if (!existingTask.getLastName().equals(task.getLastName())) {
					if (textRequest.length()!=15) {
						textRequest +=" , ";
					}
					textRequest +=" last_name = \'"+task.getLastName()+"\'";
				}
				if (!existingTask.getEmail().equals(task.getEmail())) {
					if (textRequest.length()!=15) {
						textRequest +=" , ";
					}
					textRequest +=" email = \'"+task.getEmail()+"\'";
				}
				if (!existingTask.getTask_content().equals(task.getTask_content())) {
					if (textRequest.length()!=15) {
						textRequest +=" , ";
					}
					textRequest +=" task_content = \'"+task.getTask_content()+"\'";
				}
				if (textRequest.length()!=15) {
					textRequest +=" WHERE id = "+task.getId();
				} else {
					textRequest = "the data has not changed";
				}
				model.addAttribute("requestAdd",textRequest);
				model.addAttribute("first_name", task.getFirstName());
				model.addAttribute("last_name", task.getLastName());
				model.addAttribute("email", task.getEmail());
				model.addAttribute("task_content", task.getTask_content());
				return "edit_task";
			case "run":
				// get task from database by id

				existingTask.setId(id);
				existingTask.setFirstName(task.getFirstName());
				existingTask.setLastName(task.getLastName());
				existingTask.setEmail(task.getEmail());
				existingTask.setTask_content(task.getTask_content());


				// save updated task object
				taskService.updateTask(existingTask);
				return "redirect:/tasks";
		}
		return "redirect:/tasks";
	}
	
	// handler method to handle delete task request

	@GetMapping("/tasks/delete/{id}")
	public String deleteTaskForm(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskService.getTaskById(id));
		return "delete_task";
	}
	@PostMapping("/tasks/delete {id}")
	public String deleteTask(@RequestParam String action, @PathVariable Long id,
								@ModelAttribute("task") Task task,
								Model model) {
		switch (action) {
			case "generate":
			model.addAttribute("requestDelete",
					" DELETE FROM tasks WHERE id = " +id);
				return "delete_task";
			case "run":
				taskService.deleteTaskById(id);
				return "redirect:/tasks";
		}

		return "redirect:/tasks";
	}

	@GetMapping("/tasks/select/{id}")
	public String selectTaskForm(@PathVariable Long id, Model model) {
		model.addAttribute("task", taskService.getTaskById(id));
		return "select_task";
	}
	@PostMapping("/tasks/select {id}")
	public String SelectTask(@RequestParam String action, @PathVariable Long id,
							 @ModelAttribute("task") Task task,
							 Model model) {

		switch (action) {
			case "generate":
				String textRequest = "";
				if ((task.getFirstName().trim().length()+
					task.getLastName().trim().length())!=0){
					textRequest ="SELECT * FROM tasks WHERE" +
							" first_name = \'"+task.getFirstName()+"\' ," +
							" last_name = \'"+task.getLastName()+"\' ";
				} else {
					textRequest ="SELECT * FROM tasks";
				}

				model.addAttribute("requestSelect", textRequest);
				return "select_task";
			case "run":
				if ((task.getFirstName().trim().length()+
						task.getLastName().trim().length())!=0){
					model.addAttribute("task",
							taskService.getSelectTasks(task.getFirstName(),task.getLastName()));
					return "/tasks";
				} else {
					return "redirect:/tasks";
				}
		}

		return "redirect:/tasks";
	}


}
