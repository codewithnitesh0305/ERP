package com.springboot.Model.EmployeeInformation.Employee;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_experience")
@NoArgsConstructor
@Data
public class EmployeeExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "designation")
    private String designation;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "update_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;
}
