package com.springboot.Model.EmployeeInformation.Employee;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
public class Employees {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "financial_year_id")
    private Long financialYearId;

    @Column(name = "employee_code")
    private String employeeCode;

    @Column(name = "employee_profile_image")
    private String employeeProfileImage;

    @Column(name = "salutation_id")
    private Long salutationId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "contact_no_country_code")
    private String contactNoCountryCode;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "blood_group_id")
    private Long bloodGroupId;

    @Column(name = "caste_id")
    private Long casteId;

    @Column(name = "religion_id")
    private Long religionId;

    @Column(name = "nationality_id")
    private Long nationalityId;

    @Column(name = "marital_status_id")
    private Long maritalStatusId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "designation_id")
    private Long designationId;

    @Column(name = "user_type_id")
    private Long userTypeId;

    @Column(name = "date_of_joining")
    private String dateOfJoining;

    @Column(name = "employee_type_id")
    private Long employeeTypeId;

    @Column(name = "reporting_authority_id")
    private Long reportingAuthorityId;

    @Column(name = "uan_no")
    private String uanNo;

    @Column(name = "father_salutation_id")
    private Long fatherSalutationId;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "father_contact_no_country_code")
    private String fatherContactNoCountryCode;

    @Column(name = "father_contact_no")
    private String fatherContactNo;

    @Column(name = "father_email_id")
    private String fatherEmailId;

    @Column(name = "mother_salutation_id")
    private Long motherSalutationId;

    @Column(name = "mother_name")
    private String motherName;

    @Column(name = "mother_contact_no_country_code")
    private String motherContactNoCountryCode;

    @Column(name = "mother_contact_no")
    private String motherContactNo;

    @Column(name = "mother_email_id")
    private String motherEmailId;

    @Column(name = "spouse_salutation_id")
    private Long spouseSalutationId;

    @Column(name = "spouse_name")
    private String spouseName;

    @Column(name = "spouse_contact_no_country_code")
    private Long spouseContactNoCountryCode;

    @Column(name = "spouse_contact_no")
    private String spouseContactNo;

    @Column(name = "spouse_email_id")
    private String spouseEmailId;

    @Column(name = "is_permanent_same_as_correspondence")
    private Boolean isPermanentSameAsCorrespondence;

    @Column(name = "permanent_address", columnDefinition = "TEXT")
    private String permanentAddress;

    @Column(name = "permanent_country_id")
    private Long permanentCountryId;

    @Column(name = "permanent_state_id")
    private Long permanentStateId;

    @Column(name = "permanent_city_id")
    private Long permanentCityId;

    @Column(name = "permanent_pin_code")
    private String permanentPinCode;

    @Column(name = "corresponding_address", columnDefinition = "TEXT")
    private String correspondingAddress;

    @Column(name = "corresponding_country_id")
    private Long correspondingCountryId;

    @Column(name = "corresponding_state_id")
    private Long correspondingStateId;

    @Column(name = "corresponding_city_id")
    private Long correspondingCityId;

    @Column(name = "corresponding_pin_code")
    private String correspondingPinCode;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch")
    private String branch;

    @Column(name = "resign_date")
    private String resignDate;

    @Column(name = "resign_remarks")
    private String resign_remarks;

    @Column(name = "resign_document")
    private String resign_document;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;
}