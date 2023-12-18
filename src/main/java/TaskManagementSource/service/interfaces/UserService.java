package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.dto.binding.UserUpdateBindingModel;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.dto.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    public boolean isUniqueUsername(String username);
    public boolean isUniqueEmail(String email);
    User findUserByUsername(String username);
    User findUserById(Long id);
    UserViewModel getUserProfile(UserDetails userDetails);
    void deleteUser(Long id);
    void updateUser(User user, UserUpdateBindingModel userUpdateBindingModel);
    UserUpdateBindingModel getUpdateProfile(Long id);
    List<User> getAllUsers();
}
