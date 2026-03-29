package com.springboot.Model.EmployeeInformation.Setup;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;

@Entity
@Table(name = "employee_auto_number_scheme")
@NoArgsConstructor
@Data
public class EmployeeAutoNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "financial_year_id")
    private Long financialYearId;

    @Column(name = "prefix")
    private String prefix;

    @Column(name = "starting_no")
    private Integer startingNo;

    @Column(name = "postfix")
    private String postfix;

    @Column(name = "last_no")
    private Integer lastNo;

    @Column(name = "starting_no_size")
    private Integer startingNoSize;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;

    @Column(name = "deleted_by")
    private Long deletedBy;

    @Column(name = "deleted_on")
    private String deletedOn;
}
