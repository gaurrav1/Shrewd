package com.shrewd.model.roles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shrewd.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_roles")
public class UserRole {

    public UserRole(UserRoles roleName) {
        this.roleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private UserRoles roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @ToString.Exclude
    @JsonBackReference
    private Set<User> users = new HashSet<>();

}
