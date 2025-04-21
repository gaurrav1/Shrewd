package com.shrewd.model.orgs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization")
public class Organization{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "org_id")
    private String orgId;

    @Column(name = "org_name")
    private String orgName;
    private String email;
    private String phone;
    private String address;

    @Column(unique = true, nullable = false, name = "tenant_id")
    private String tenantId;

    @Column(name = "jdbc_url", nullable = false)
    private String jdbcUrl;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(updatable = false, name="created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
