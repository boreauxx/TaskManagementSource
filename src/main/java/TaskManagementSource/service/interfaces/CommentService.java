package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.dto.binding.CreateCommentBindingModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface CommentService {

    void create(CreateCommentBindingModel commentBindingModel, UserDetails commenter);
}
