package com.shrewd.model.roles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shrewd.model.Organization;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "org_roles")
public class OrgRole {

    public OrgRole(OrgRoles roleName) {
        this.roleName = roleName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "role_name")
    private OrgRoles roleName;

    @OneToMany(mappedBy = "orgRole", fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
    @JsonBackReference
    @ToString.Exclude
    private Set<Organization> organizations = new HashSet<>();

}