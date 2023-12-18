package TaskManagementSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TaskManagementSourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementSourceApplication.class, args);
	}

}
