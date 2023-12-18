package TaskManagementSource.controller;

import TaskManagementSource.entities.dto.binding.PictureUploadBindingModel;
import TaskManagementSource.service.interfaces.PictureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @PostMapping("/upload")
    public String upload(@Valid PictureUploadBindingModel pictureUploadBindingModel){
        pictureService.upload(pictureUploadBindingModel);
        return "redirect:/users/profile";
    }
}
