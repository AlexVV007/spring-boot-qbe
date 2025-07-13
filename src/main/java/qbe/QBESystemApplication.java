package qbe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import qbe.repository.TaskRepository;

@SpringBootApplication
public class QBESystemApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(qbe.QBESystemApplication.class, args);
	}

	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public void run(String... args) throws Exception {
		

	}

}
