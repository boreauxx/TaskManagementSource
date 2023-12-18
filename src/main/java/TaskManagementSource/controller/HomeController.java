package TaskManagementSource.controller;

import TaskManagementSource.entities.dto.view.TaskViewModel;
import TaskManagementSource.service.interfaces.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final TaskService taskService;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/about")
    public String about(){
        return "about";
    }
    @GetMapping("/home")
    public String home(Model model){
        List<TaskViewModel> mappedTasks = this.taskService.getAllTasks();
        model.addAttribute("tasks", mappedTasks);
        return "home";
    }
}
