package TaskManagementSource.util;

import TaskManagementSource.entities.entity.Picture;
import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.entity.User;
import TaskManagementSource.entities.enums.Level;
import TaskManagementSource.entities.enums.UserRoles;
import TaskManagementSource.repository.RoleRepository;
import TaskManagementSource.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(roleRepository.count()<1){
            Role guest = new Role(UserRoles.GUEST, new ArrayList<>());
            Role moderator = new Role(UserRoles.MODERATOR, new ArrayList<>());
            Role admin = new Role(UserRoles.ADMIN, new ArrayList<>());
            roleRepository.saveAll(List.of(guest, moderator, admin));
        }
        if(userRepository.count()<1){
            Role role = this.roleRepository.findByName(UserRoles.ADMIN);
            User user = new User("admin", passwordEncoder.encode("admin"), "admin admin", "admin@abv.bg", 24, Level.ADVANCED,
                    ".\\src\\main\\resources\\static\\images\\", new ArrayList<>(),role, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            userRepository.save(user);
        }

    }
}
