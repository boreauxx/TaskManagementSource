package TaskManagementSource.repository;

import TaskManagementSource.entities.entity.Role;
import TaskManagementSource.entities.enums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName (UserRoles role);
}
