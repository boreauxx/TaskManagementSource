package TaskManagementSource.entities.entity;

import TaskManagementSource.entities.enums.UserRoles;

import TaskManagementSource.util.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRoles name;

    @OneToMany(mappedBy = "role")
    List<User> users;

    @Override
    public String toString(){
        return this.name.toString();
    }
}
