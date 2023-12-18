package TaskManagementSource.service.interfaces;

import TaskManagementSource.entities.entity.Role;

import java.util.List;

public interface RoleService {
    Role getRoleByName (String name);
    List<Role> getAllRoles();
}
