package com.springboot.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EmployeeMast")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee extends Common{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String empCode;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid Email")
    private String email;
    @NotEmpty(message = "Password is required")
    private String password;
    @NotEmpty(message = "PhoneNo is required")
    private String phoneNo;
    private String gender;
    private String isActive;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "employee")
    @JsonBackReference
    private RefreshToken refreshToken;
    
    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private List<ClaimRequest> claimRequest;
    
    @OneToMany(mappedBy = "employee")
    @JsonIgnoreProperties("employee")
    private List<Leave> leave;
    
    /*@OneToOne
    private Department department;*/
}
