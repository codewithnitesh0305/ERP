package com.springboot.Model.EmployeeInformation.Setup;

import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity(name = "employee_document")
@NoArgsConstructor
@Data
public class EmployeeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "authorized_employee_id")
    private Long authorizedEmployeeId;

    @Column(name = "is_document_upload")
    private Boolean isDocumentUpload;

    @Column(name = "is_reminder_required")
    private Boolean isReminderRequired;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;

}
