package TaskManagementSource.controller;

import TaskManagementSource.entities.dto.binding.AddTaskBindingModel;
import TaskManagementSource.entities.dto.binding.CreateCommentBindingModel;
import TaskManagementSource.entities.dto.binding.UpdateTaskBindingModel;
import TaskManagementSource.entities.dto.view.TaskViewModel;
import TaskManagementSource.entities.enums.Status;
import TaskManagementSource.service.interfaces.TaskService;
import TaskManagementSource.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Set;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final TaskService taskService;
    private final UserService userService;

    // ADD TASK
    @GetMapping("/add")
    public String addTask(Model model){
        if(!model.containsAttribute("addTaskBindingModel")){
            model.addAttribute("addTaskBindingModel", new AddTaskBindingModel());
        }
        model.addAttribute("statuses", Set.of(Status.values()));
        model.addAttribute("tomorrow", LocalDate.now());
        return "task-add";
    }
    @PostMapping("/add")
    public String addTask(@Valid AddTaskBindingModel addTaskBindingModel,
                          BindingResult bindingResult, RedirectAttributes redirectAttributes,
                          @AuthenticationPrincipal UserDetails assignor){
        if(bindingResult.hasErrors()){
            final String attributeName = "addTaskBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, addTaskBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/tasks/add";
        }
        this.taskService.addTask(addTaskBindingModel, assignor);
        return "redirect:/home";
    }


    // DETAILS
    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model){
        TaskViewModel task = this.taskService.viewTaskDetails(id);
        if(!model.containsAttribute("createCommentBindingModel")){
            model.addAttribute("createCommentBindingModel", new CreateCommentBindingModel());
        }
        model.addAttribute("task", task);
        return "task-details";
    }


    // ASSIGN
    @PostMapping("/assign/{id}")
    public String assignTask(@PathVariable("id") Long id,
                             @AuthenticationPrincipal UserDetails assignee){
        String username = userService.findUserByUsername(assignee.getUsername()).getUsername();
        this.taskService.assignTask(username, id);
        return "redirect:/home";
    }

    // REMOVE
    @GetMapping("/remove/{id}")
    public String availableTasks(@PathVariable("id") Long id){
        this.taskService.removeTask(id);
        return "redirect:/home";
    }

    // UPDATE
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model){
        if(!model.containsAttribute("updateTaskBindingModel")){
            model.addAttribute("updateTaskBindingModel", new UpdateTaskBindingModel());
        }
        UpdateTaskBindingModel task = this.taskService.getUpdateTask(id);
        model.addAttribute("updateTaskBindingModel", task);
        model.addAttribute("statuses", Set.of(Status.values()));
        return "task-update";
    }
    @PostMapping("/update/{id}")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute("updateTaskBindingModel") @Valid UpdateTaskBindingModel updateTaskBindingModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            final String attributeName = "updateTaskBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, updateTaskBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/tasks/update/{id}";
        }
        this.taskService.updateTask(id, updateTaskBindingModel);
        return "redirect:/home";
    }



}
