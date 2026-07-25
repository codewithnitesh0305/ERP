package com.springboot.Dto.EmployeeInformation;

import lombok.Data;

@Data
public class DocumentSubmissionDto {

    private Long id;
    private Long documentId;
    private String documentName;
    private String expiryDate;
    private String submissionDate;
    private String fileName;
    private String documentNumber;
    private Boolean isFileChange;
}
