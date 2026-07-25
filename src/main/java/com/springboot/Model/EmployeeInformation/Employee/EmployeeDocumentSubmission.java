package com.springboot.Model.EmployeeInformation.Employee;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "employee_document_submission")
@Entity
@NoArgsConstructor
@Data
public class EmployeeDocumentSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "document_id")
    private Long documentId;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_no")
    private String documentNo;

    @Column(name = "document_expiry_date")
    private String documentExpiryDate;

    @Column(name = "submission_date")
    private String submissionDate;

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
