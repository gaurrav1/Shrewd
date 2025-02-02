package com.shrewd.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "organization")
public class Organization{


    public Organization(String userName, String email, String password) {
        this.username = userName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "org_id")
    private String orgId;

    @Column(name = "org_name")
    private String orgName;
    private String username;
    private String email;
    private String phone;
    private String address;

    @Size(max = 120)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false, name = "tenant_id")
    private String tenantId;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "account_non_expired")
    private boolean accountNonExpired;

    @Column(name = "credentials_non_expired")
    private boolean credentialsNonExpired;
    private boolean enabled;

    @Column(name = "credentials_expiry_date")
    private LocalDate credentialsExpiryDate;
    @Column(name = "account_expiry_date")
    private LocalDate accountExpiryDate;

    @CreationTimestamp
    @Column(updatable = false, name="created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
