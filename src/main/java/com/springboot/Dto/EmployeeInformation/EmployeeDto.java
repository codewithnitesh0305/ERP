package com.springboot.Dto.EmployeeInformation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDto {

    private Long id;
    @NotNull(message = "Kindly select financial year.")
    @Positive(message = "Invalid financial year.")
    private Long financialYearId;

    private String employeeCode;
    private Boolean isChange;

    private MultipartFile employeeProfileImage;

    @Positive(message = "Invalid salutation.")
    @Positive(message = "Salutation id can't be zero.")
    private Long salutationId;

    @NotEmpty(message = "First name is required.")
    private String firstName;
    private String middleName;
    private String lastName;

    @NotNull(message = "Kindly select gender.")
    @Positive(message = "Gender id can't be zero.")
    private Long genderId;

    @NotEmpty(message = "Date of birth is required.")
    private String dateOfBirth;

    private String contactNoCountryCode;

    @NotEmpty(message = "Contact No. is required.")
    private String contactNo;

    @NotEmpty(message = "Email ID is required.")
    private String emailId;

    @Positive(message = "Blood group id can't be 0.")
    private Long bloodGroupId;

    @Positive(message = "Caste id can't be 0.")
    private Long casteId;

    @Positive(message = "Religion id can't be 0.")
    private Long religionId;

    @NotNull(message = "Kindly select nationality.")
    private Long nationalityId;

    @Positive(message = "Marital Status id can't be 0.")
    private Long maritalStatusId;

    @NotNull(message = "Kindly select department.")
    @Positive(message = "Department id can't be 0.")
    private Long departmentId;

    @NotNull(message = "Kindly select designation.")
    @Positive(message = "Designation id can't be 0.")
    private Long designationId;

    @NotNull(message = "Kindly select user type.")
    @Positive(message = "User type id can't be 0.")
    private Long userTypeId;

    @NotEmpty(message = "Date of joining is required.")
    private String dateOfJoining;

    @NotNull(message = "Kindly select employee type.")
    @Positive(message = "Employee type id can't be zero.")
    private Long employeeTypeId;

    @Positive(message = "Reporting Authority id can't be 0.")
    private Long reportingAuthorityId;

    private String uanNo;

    @Positive(message = "Father salutation id can't be 0.")
    private Long fatherSalutationId;
    private String fatherName;
    private String fatherContactNoCountryCode;
    private String fatherContactNo;
    private String fatherEmailId;

    @Positive(message = "Mother salutation id can't be 0.")
    private Long motherSalutationId;
    private String motherName;
    private String motherContactNoCountryCode;
    private String motherContactNo;
    private String motherEmailId;

    @Positive(message = "Spouse salutation id can't be 0.")
    private Long spouseSalutationId;
    private String spouseName;
    private String spouseContactNoCountryCode;
    private String spouseContactNo;
    private String spouseEmailId;

    private Boolean isCorrespondenceSameAsPermanent;

    private String correspondingAddress;

    @Positive(message = "Invalid corresponding country.")
    private Long correspondingCountryId;

    @Positive(message = "Invalid corresponding state.")
    private Long correspondingStateId;

    @Positive(message = "Invalid corresponding state.")
    private Long correspondingCityId;

    private String correspondingPinCode;

    private String permanentAddress;

    @Positive(message = "Invalid permanent country.")
    private Long permanentCountryId;

    @Positive(message = "Invalid permanent state.")
    private Long permanentStateId;

    @Positive(message = "Invalid permanent city.")
    private Long permanentCityId;

    private String permanentPinCode;

    private String accountName;
    private String accountNo;
    private String ifscCode;
    private String bankName;
    private String branch;
    private String reEnterAccountNo;

    private List<DocumentSubmissionDto> documentSubmissionList;

    private Set<Long> deletedEmployeeQualificationIds;
    private List<EmployeeQualificationDto> employeeQualificationList;

    private Set<Long> deletedEmployeeExperienceIds;
    private List<EmployeeExperienceDto> employeeExperienceList;

}
