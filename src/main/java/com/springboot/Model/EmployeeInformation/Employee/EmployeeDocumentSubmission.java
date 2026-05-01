package com.springboot.Model.EmployeeInformation.Employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Table(name = "employee_document_submission")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDocumentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_url")
    private String documentUrl;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;
}
