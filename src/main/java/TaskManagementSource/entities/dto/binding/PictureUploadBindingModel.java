package TaskManagementSource.entities.dto.binding;

import TaskManagementSource.validation.anotations.FileAnnotation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter @NoArgsConstructor
public class PictureUploadBindingModel {

    private Long id;

    @FileAnnotation(contentTypes = {"image/png", "image/jpeg"})
    private MultipartFile picture;

}
