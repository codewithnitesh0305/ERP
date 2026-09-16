package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class EmployeeExperienceDto {

    @Positive(message = "Invalid employee experience.")
    private Long id;

    @Positive(message = "Invalid employee.")
    private String employeeId;

    private String companyName;
    private String jobTitle;
    private String designation;
    private Boolean isFileChange;
    private MultipartFile attachment;
}
