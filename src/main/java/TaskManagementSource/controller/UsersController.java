package TaskManagementSource.controller;


import TaskManagementSource.entities.dto.binding.PictureUploadBindingModel;
import TaskManagementSource.entities.dto.binding.ReCaptchaBindingModel;
import TaskManagementSource.entities.dto.binding.UserRegisterBindingModel;
import TaskManagementSource.entities.dto.binding.UserUpdateBindingModel;
import TaskManagementSource.entities.dto.view.UserViewModel;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.enums.Level;
import TaskManagementSource.exceptions.LoginCredentialsException;
import TaskManagementSource.service.interfaces.AuthenticationService;
import TaskManagementSource.service.interfaces.ReCaptchaService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UsersController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final RoleService roleService;
    private final ReCaptchaService reCaptchaService;

    //LOGIN CREDENTIALS EXCEPTION
    @ExceptionHandler(LoginCredentialsException.class)
    public ModelAndView handleLoginCredentialsError(LoginCredentialsException e,
                                                    RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("badCredentials", true);
        System.out.println(e.getMessage());
        return new ModelAndView("redirect:login");
    }

    //LOGIN
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @PostMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("badCredentials", true);
        return "login";
    }

    //REGISTER
    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";
    }
    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                           @RequestParam("g-recaptcha-response") String reCaptchaResponse) {
        boolean isBot = !reCaptchaService
                .verify(reCaptchaResponse)
                .map(ReCaptchaBindingModel::isSuccess)
                .orElse(false);
        if(isBot){
         return "redirect:/";
        }
        if (bindingResult.hasErrors()) {
            final String attributeName = "userRegisterBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, userRegisterBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:register";
        }
        this.authenticationService.register(userRegisterBindingModel);
        return "redirect:login";
    }

    // PROFILE
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails viewer) {
        if(!model.containsAttribute("pictureUploadBindingModel")){
            model.addAttribute("pictureUploadBindingModel", new PictureUploadBindingModel());
        }
        UserViewModel userViewModel = userService.getUserProfile(viewer);
        model.addAttribute("userViewModel", userViewModel);
        return "profile";
    }

    // EDIT PROFILE
    @GetMapping("/update/{id}")
    public String editProfile(@PathVariable("id") Long id, Model model){
        if(!model.containsAttribute("userUpdateBindingModel")){
            UserUpdateBindingModel user = this.userService.getUpdateProfile(id);
            model.addAttribute("userUpdateBindingModel", user);
        }
        model.addAttribute("levels", Set.of(Level.values()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "user-update";
    }
    @PostMapping("/update/{id}")
    public String editProfile(@Valid UserUpdateBindingModel userUpdateBindingModel,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            final String attributeName = "userUpdateBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, userUpdateBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/users/update/" + userUpdateBindingModel.getId();
        }
        User user = this.userService.findUserById(userUpdateBindingModel.getId());
        this.userService.updateUser(user, userUpdateBindingModel);
        return "redirect:/users/profile";
    }
}
