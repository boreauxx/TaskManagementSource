package TaskManagementSource.controller;

import TaskManagementSource.entities.dto.binding.CreateCommentBindingModel;
import TaskManagementSource.service.interfaces.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    public static final String BINDING_RESULT_PATH = "org.springframework.validation.BindingResult";
    public static final String DOT = ".";

    private final CommentService commentService;

    @PostMapping("/create")
    public String createComment(@Valid CreateCommentBindingModel commentBindingModel,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal UserDetails commenter){
        final Long taskId = commentBindingModel.getTaskId();
        if(bindingResult.hasErrors()){
            final String attributeName = "createCommentBindingModel";
            redirectAttributes
                    .addFlashAttribute(attributeName, commentBindingModel)
                    .addFlashAttribute(BINDING_RESULT_PATH + DOT + attributeName, bindingResult);
            return "redirect:/tasks/details/" + taskId;
        }
        commentService.create(commentBindingModel, commenter);
        return "redirect:/tasks/details/" + taskId;
    }
}
