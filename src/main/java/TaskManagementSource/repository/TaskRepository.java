package TaskManagementSource.repository;

import TaskManagementSource.entities.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Task searchById(Long id);
    
}
