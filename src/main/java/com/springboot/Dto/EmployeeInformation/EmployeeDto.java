package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private Long id;
    @NotNull(message = "Kindly select financial year.")
    private Long financialYearId;
    private String employeeCode;
    private Boolean isChange;
    private Long salutationId;
    @NotEmpty(message = "First name is required.")
    private String firstName;
    private String middleName;
    private String lastName;
    @NotNull(message = "Kindly select gender.")
    private Long genderId;
    @NotEmpty(message = "Date of birth is required.")
    private String dateOfBirth;
    private String contactNoCountryCode;
    @NotEmpty(message = "Contact No. is required.")
    private String contactNo;
    @NotEmpty(message = "Email ID is required.")
    private String emailId;
    private Long bloodGroupId;
    private Long casteId;
    private Long religionId;
    @NotNull(message = "Kindly select nationality.")
    private Long nationalityId;
    private Long maritalStatusId;
    @NotNull(message = "Kindly select department.")
    private Long departmentId;
    @NotNull(message = "Kindly select designation.")
    private Long designationId;
    @NotNull(message = "Kindly select user type.")
    private Long userTypeId;
    @NotEmpty(message = "Date of joining is required.")
    private String dateOfJoining;
    @NotNull(message = "Kindly select employee type.")
    private Long employeeTypeId;
    private Long reportingAuthorityId;
    private String uanNo;

    private Long fatherSalutationId;
    private String fatherName;
    private String fatherContactNoCountryCode;
    private String fatherContactNo;
    private String fatherEmailId;

    private Long motherSalutationId;
    private String motherName;
    private String motherContactNoCountryCode;
    private String motherContactNo;
    private String motherEmailId;

    private Long spouseSalutationId;
    private String spouseName;
    private String spouseContactNoCountryCode;
    private String spouseContactNo;
    private String spouseEmailId;

    private Boolean isCorrespondenceSameAsPermanent;
    private String correspondingAddress;
    private Long correspondingCountryId;
    private Long correspondingStateId;
    private Long correspondingCityId;
    private String correspondingPinCode;

    private String permanentAddress;
    private Long permanentCountryId;
    private Long permanentStateId;
    private Long permanentCityId;
    private String permanentPinCode;

    private String accountName;
    private String accountNo;
    private String ifscCode;
    private String bankName;
    private String branch;
    private String reEnterAccountNo;

    private List<DocumentSubmissionDto> documentSubmissionList;
    private Map<String, MultipartFile> fileMap;

}
