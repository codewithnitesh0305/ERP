package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentSubmissionDto {

    @Positive(message = "Document submission id can't be 0.")
    private Long id;

    @Positive(message = "Document id can't be 0.")
    private Long documentId;
    MultipartFile multipartFile;
    private String documentName;
    private String expiryDate;
    private String submissionDate;
    private String fileName;
    private String documentNumber;
    private Boolean isFileChange;
}
