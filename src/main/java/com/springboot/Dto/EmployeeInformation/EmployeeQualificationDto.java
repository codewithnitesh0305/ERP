package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class EmployeeQualificationDto {

    @Positive(message = "Invalid employee qualification.")
    public Long id;

    @Positive(message = "Employee Id can't be 0.")
    public Long employeeId;

    @Positive(message = "Qualification Id can't be 0.")
    public Long qualificationId;

    public String specialization;

    public String boardUniversity;

    public Integer completionYear;

    public Integer resultValue;

    public Boolean isFileChange;

    public MultipartFile attachment;

}
