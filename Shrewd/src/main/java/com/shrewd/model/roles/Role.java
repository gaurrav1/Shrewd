package com.shrewd.model.roles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shrewd.model.Employee;
import com.shrewd.model.Organization;
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
@Table(name = "roles")
public class Role {

    public Role(ROLES roleName) {
        this.roleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private ROLES roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @ToString.Exclude
    @JsonBackReference
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @ToString.Exclude
    @JsonBackReference
    private Set<Organization> organizations = new HashSet<>();

}
