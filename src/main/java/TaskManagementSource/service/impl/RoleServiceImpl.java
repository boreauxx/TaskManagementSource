package TaskManagementSource.service.impl;

import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.enums.UserRoles;
import TaskManagementSource.repository.RoleRepository;
import TaskManagementSource.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName (String name) {

        return this.roleRepository.findByName(UserRoles.valueOf(name));
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }
}
