package TaskManagementSource.controller;

import TaskManagementSource.entities.dto.binding.UserUpdateBindingModel;
import TaskManagementSource.entities.dto.view.UserViewModel;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.enums.Level;
import TaskManagementSource.entities.enums.UserRoles;
import TaskManagementSource.service.interfaces.RoleService;
import TaskManagementSource.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final UserService userService;
    private final RoleService roleService;

    // ADMIN
    @GetMapping
    public String admin(Model model){
        model.addAttribute("allUsers", this.userService.getAllUsers());
        return "admin-search";
    }
    @PostMapping
    public String admin(@RequestParam("username") String username, Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (userService.findUserByUsername(username) != null) {
            if (username.equals(userDetails.getUsername())){
                return "redirect:/users/profile";
            }
            return "redirect:/admin/edit/" + username;
        }
        else {
            return "redirect:/admin?error=No user found with username" + username;
        }
    }

    // ADMIN-EDIT
    @GetMapping("/edit/{username}")
    public String adminEdit(@PathVariable("username") String username, Model model){
        User user = this.userService.findUserByUsername(username);
        if(user != null){
            model.addAttribute("user", user);
            return "admin-edit";
        }
        else {
            model.addAttribute("error", "No user found with username: " + username);
            return "redirect:/admin?error=No user found with username " + username;
        }
    }

    // ADMIN-DELETE
    @PostMapping("/delete/{id}")
    public String adminDelete(@PathVariable("id") Long id){
        this.userService.deleteUser(id);
        return "redirect:/home";
    }

    // ADMIN-UPDATE
    @GetMapping("/update/{id}")
    public String adminUpdate(@PathVariable("id") Long id, Model model){
        if(!model.containsAttribute("userUpdateBindingModel")){
            UserUpdateBindingModel user = this.userService.getUpdateProfile(id);
            model.addAttribute("userUpdateBindingModel", user);
        }
        model.addAttribute("levels", Set.of(Level.values()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-update";
    }
    @PostMapping("/update/{id}")
    public String adminUpdate(@Valid UserUpdateBindingModel userUpdateBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            final String attributeName = "userUpdateBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, userUpdateBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/admin/update/" + userUpdateBindingModel.getId();
        }
        User user = this.userService.findUserById(userUpdateBindingModel.getId());
        this.userService.updateUser(user, userUpdateBindingModel);
        return "redirect:/admin/edit/" + userUpdateBindingModel.getUsername();
    }
}
