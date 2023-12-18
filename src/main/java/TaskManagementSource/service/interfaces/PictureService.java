package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.dto.binding.PictureUploadBindingModel;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {

    void upload(PictureUploadBindingModel pictureUploadBindingModel);
}
