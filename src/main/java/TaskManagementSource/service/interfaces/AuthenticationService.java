package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.dto.binding.UserRegisterBindingModel;
import org.springframework.security.core.Authentication;

public interface AuthenticationService {

    void register(UserRegisterBindingModel userRegisterBindingModel);

    Authentication login(String username);

    void createUserIfNotExist(String username, String email);
}
