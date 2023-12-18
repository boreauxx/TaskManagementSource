package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.entity.Task;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.dto.binding.AddTaskBindingModel;
import TaskManagementSource.entities.dto.binding.UpdateTaskBindingModel;
import TaskManagementSource.entities.dto.view.TaskViewModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface TaskService {

    Task findTaskById(Long id);
    void addTask(AddTaskBindingModel addTaskBindingModel, UserDetails user);

    void removeTask(Long id);

    void assignTask(String username, Long taskId);

    UpdateTaskBindingModel getUpdateTask(Long id);
    void updateTask(Long id, UpdateTaskBindingModel newTask);

    TaskViewModel viewTaskDetails(Long id);

    List<TaskViewModel> getAllTasks();
}
