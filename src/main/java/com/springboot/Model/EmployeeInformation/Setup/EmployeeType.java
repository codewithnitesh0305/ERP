package com.springboot.Model.EmployeeInformation.Setup;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee_type")
@NoArgsConstructor
@Data
public class EmployeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updateBy;

    @Column(name = "updated_on")
    private String updatedOn;

}
