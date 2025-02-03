package com.shrewd.model.users;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee implements User {

    public Employee(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;


    private String username;
    private String email;
    private String name;
    private String phone;

    @Size(max = 120)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonBackReference
    @ToString.Exclude
    private Role role;


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
    @Column(updatable = false, name = "created_date")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

}
