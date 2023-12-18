package TaskManagementSource.service.impl;

import TaskManagementSource.entities.entity.Task;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.dto.binding.AddTaskBindingModel;
import TaskManagementSource.entities.dto.binding.UpdateTaskBindingModel;
import TaskManagementSource.entities.dto.view.TaskViewModel;
import TaskManagementSource.exceptions.TaskNotFoundException;
import TaskManagementSource.repository.TaskRepository;
import TaskManagementSource.service.interfaces.TaskService;
import TaskManagementSource.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    // FIND BY ID
    @Override
    public Task findTaskById(Long id) {
        return this.taskRepository.searchById(id);
    }

    // ADD
    @Override
    public void addTask(AddTaskBindingModel addTaskBindingModel, UserDetails userDetails) {
        Task task = mapper.map(addTaskBindingModel, Task.class);
        task.setAssignor(userService.findUserByUsername(userDetails.getUsername()));
        task.setAssignee(null);
        task.setComments(new ArrayList<>());
        this.taskRepository.save(task);
    }

    // REMOVE
    @Override
    public void removeTask(Long id) {
        this.taskRepository.deleteById(id);
    }

    // ASSIGN
    @Override
    public void assignTask(String username, Long taskId) {
        User assignee = this.userService.findUserByUsername(username);
        Task task = this.taskRepository.searchById(taskId);
        task.setAssignee(assignee);
        taskRepository.save(task);
    }

    // UPDATE
    @Override
    public UpdateTaskBindingModel getUpdateTask(Long id){
        Task task = this.taskRepository.searchById(id);
        UpdateTaskBindingModel update = mapper.map(task, UpdateTaskBindingModel.class);
        return update;
    }
    @Override
    public void updateTask(Long id,UpdateTaskBindingModel updated) {
        Task task = this.taskRepository.searchById(id);
        task.setTitle(updated.getTitle());
        task.setDescription(updated.getDescription());
        task.setDueDate(updated.getDueDate());
        task.setStatus(updated.getStatus());
        this.taskRepository.save(task);
    }

    // TASK DETAILS
    @Override
    public TaskViewModel viewTaskDetails(Long id) {
        Task task = this.taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + id + " was not found!"));
        TaskViewModel viewTask = mapper.map(task, TaskViewModel.class);
        viewTask.setAssignee(task.getAssignee());
        viewTask.setId(task.getId());
        return viewTask;
    }

    // ALL TASKS
    @Override
    public List<TaskViewModel> getAllTasks() {
        List<Task> allTasks = this.taskRepository.findAll();
        List<TaskViewModel> mappedList = new ArrayList<>();
        for (Task allTask : allTasks) {
            mappedList.add(mapper.map(allTask, TaskViewModel.class));
        }
        return mappedList;
    }
}
