package com.shrewd.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDTO {
    private String orgName;
    private String email;
    private String phone;
    private String address;
    private String tenantId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
