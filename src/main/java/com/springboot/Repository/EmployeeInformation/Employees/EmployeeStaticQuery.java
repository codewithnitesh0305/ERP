package com.springboot.Repository.EmployeeInformation.Employees;

public class EmployeeStaticQuery {

    public final static String EMPLOYEE_DATA_QUERY = """
            Select emp.id,emp.employee_code as EmployeeCode,emp.employee_profile_image employeeProfileImage,emp.salutation_id as salutationId,emp.full_name as fullName,"
            "emp.gender_id as genderId,emp.date_of_birth as dateOfBirth,emp.contact_no_country_code as contactNoCountryCode,emp.contact_no as contactNo,emp.email_id as emailId,emp.department_id as departmentId,"
            "emp.designation_id as designationId,emp.user_type_id as userTypeId,emp.employee_type_id as employeeTypeId,emp.date_of_joining as dateOfJoining,emp.blood_group_id as bloodGroupId,emp.martial_status_id as martialStatusId,"
            "emp.nationality_id as nationalityId,emp.religion_id as religionId,emp.caste_id as casteId,emp.department_id as departmentId,emp.designation_id as designationId,emp.user_type_id as userTypeId,"
            "emp.reporting_authority_id as reportingAuthorityId, emp.uan_no as uanNo,"
            "emp.father_salutation_id as fatherSalutationId, emp.father_name as fatherName, emp.father_contact_no_country_code as fatherContactCode,emp.father_contact_no fatherContactNo,emp.father_email_id as fatherEmailId,"
            "emp.mother_salutation_id as motherSalutationId, emp.mother_name as motherName,emp.mother_contact_no_country_code as motherContactCode,emp.mother_contact_no as motherContactNo,emp.mother_email_id as motherEmailId,"
            "emp.spouse_salutation_id as spouseSalutationId,emp.spouse_name spouseName,emp.spouse_contact_no_country_code as spouseContactCode,emp.spouse_contact_no spouseContactNo,emp.spouse_email_id as spouseEmailId,"
            "emp.is_permanent_same_as_correspondence as isPermanentSameAsCorresponding, emp.permanent_address as permanentAddress,emp.permanent_country_id as permanentCountryId,emp.permanent_state_id as permanentStateId,emp.permanent_state_id as permanentStateId,emp.permanent_city_id as permanentCityId,emp.permanent_pin_code as permanentPinCode,"
            "emp.corresponding_address as correspondingAddress,emp.corresponding_country_id as correspondingCountryId,emp.corresponding_state_id as correspondingStateId,emp.corresponding_state_id as correspondingStateId,emp.corresponding_city_id as correspondingCityId,emp.corresponding_pin_code as correspondingPinCode,"
            "From employees emp
            """;
}
