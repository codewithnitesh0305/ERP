package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DocumentSubmissionDto {

    @Positive(message = "Document submission id can't be 0.")
    private Long id;

    @Positive(message = "Document id can't be 0.")
    private Long documentId;
    private String documentName;
    private String expiryDate;
    private String submissionDate;
    private String fileName;
    private String documentNumber;
    private Boolean isFileChange;
}
