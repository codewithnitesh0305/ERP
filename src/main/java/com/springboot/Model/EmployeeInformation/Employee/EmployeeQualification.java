package com.springboot.Model.EmployeeInformation.Employee;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees_qualification")
@NoArgsConstructor
@Data
public class EmployeeQualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "qualification_id")
    private Long qualificationId;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "board_university")
    private String boardUniversity;

    @Column(name = "completion_year")
    private Integer completionYear;

    @Column(name = "result_value")
    private Integer resultValue;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updateBy;

    @Column(name = "updated_on")
    private String updatedOn;
}
