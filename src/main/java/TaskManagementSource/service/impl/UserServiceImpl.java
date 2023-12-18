package TaskManagementSource.service.impl;


import TaskManagementSource.entities.dto.binding.UserUpdateBindingModel;
import TaskManagementSource.entities.dto.view.UserViewModel;
import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.exceptions.UserNotFoundException;
import TaskManagementSource.repository.UserRepository;
import TaskManagementSource.service.interfaces.RoleService;
import TaskManagementSource.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @Override
    public boolean isUniqueUsername(String username) {
        return this.userRepository.findByUsername(username).isEmpty();
    }

    @Override
    public boolean isUniqueEmail(String email) {
        return this.userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public User findUserByUsername(String username) {
        if(this.userRepository.findByUsername(username).isPresent()){
            return this.userRepository.findByUsername(username).get();
        }
        return null;
    }

    @Override
    public User findUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow( () -> new UserNotFoundException("Username with id: " + id + "does not exist."));
    }

    @Override
    public UserViewModel getUserProfile(UserDetails viewer) {
        User user = this.findUserByUsername(viewer.getUsername());
        return modelMapper.map(user, UserViewModel.class);
    }

    @Override
    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public void updateUser(User user, UserUpdateBindingModel userUpdateBindingModel) {
        Role role = this.roleService.getRoleByName(userUpdateBindingModel.getRole().toString());
        user.setUsername(userUpdateBindingModel.getUsername());
        user.setFullName(userUpdateBindingModel.getFullName());
        user.setAge(userUpdateBindingModel.getAge());
        user.setRole(role);
        user.setLevel(userUpdateBindingModel.getLevel());
        this.userRepository.save(user);
    }

    @Override
    public UserUpdateBindingModel getUpdateProfile(Long id) {
        User user = this.findUserById(id);
        return modelMapper.map(user, UserUpdateBindingModel.class);
    }

    @Override
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
