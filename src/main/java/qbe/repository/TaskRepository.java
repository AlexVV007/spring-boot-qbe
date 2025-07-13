package qbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qbe.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long>{

    public List<Task> findByFirstNameAndLastName(String fist_name, String last_name);

}
