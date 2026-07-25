package com.springboot.Repository.EmployeeInformation.Employees;

public class EmployeeStaticQuery {

    public final static String EMPLOYEE_DATA_QUERY = """
            Select emp.id,emp.employee_code as employeeCode,emp.employee_profile_image employeeProfileImage,emp.salutation_id as salutationId,emp.full_name as fullName,
            emp.gender_id as genderId,emp.date_of_birth as dateOfBirth,emp.contact_no_country_code as contactNoCountryCode,emp.contact_no as contactNo,emp.email_id as emailId,emp.department_id as departmentId,dept.name as department,
            emp.user_type_id as userTypeId,emp.employee_type_id as employeeTypeId,empTyp.name as employeeType,emp.date_of_joining as dateOfJoining,emp.blood_group_id as bloodGroupId,emp.marital_status_id as maritalStatusId,
            emp.nationality_id as nationalityId,emp.religion_id as religionId,emp.caste_id as casteId,emp.designation_id as designationId,deg.name as designation,emp.user_type_id as userTypeId,
            emp.reporting_authority_id as reportingAuthorityId, emp.uan_no as uanNo,
            emp.father_salutation_id as fatherSalutationId, emp.father_name as fatherName, emp.father_contact_no_country_code as fatherContactCode,emp.father_contact_no fatherContactNo,emp.father_email_id as fatherEmailId,
            emp.mother_salutation_id as motherSalutationId, emp.mother_name as motherName,emp.mother_contact_no_country_code as motherContactCode,emp.mother_contact_no as motherContactNo,emp.mother_email_id as motherEmailId,
            emp.spouse_salutation_id as spouseSalutationId,emp.spouse_name spouseName,emp.spouse_contact_no_country_code as spouseContactCode,emp.spouse_contact_no spouseContactNo,emp.spouse_email_id as spouseEmailId,
            emp.is_corresponding_same_as_permanent as isPermanentSameAsCorresponding, emp.permanent_address as permanentAddress,emp.permanent_country_id as permanentCountryId,emp.permanent_state_id as permanentStateId,emp.permanent_state_id as permanentStateId,emp.permanent_city_id as permanentCityId,emp.permanent_pin_code as permanentPinCode,
            emp.corresponding_address as correspondingAddress,emp.corresponding_country_id as correspondingCountryId,emp.corresponding_state_id as correspondingStateId,emp.corresponding_state_id as correspondingStateId,emp.corresponding_city_id as correspondingCityId,emp.corresponding_pin_code as correspondingPinCode
            From employees emp
            Left join organization_department dept on dept.id = emp.department_id
            Left join organization_designation deg on deg.id = emp.designation_id
            Left join employee_type empTyp on empTyp.id = emp.employee_type_id
            """;

    public final static String INACTIVE_EMPLOYEE_QUERY = """
            Select emp.id as id,emp.first_name as employeeName,emp.employee_code as employeeCode,emp.employee_profile_image as employeeProfileImage,emp.resign_document as resignImage,
            emp.resign_date as resignDate,emp.releasing_date as releasingDate,emp.contact_no_country_code as employeeContactCode,emp.contact_no employeeContactNo,emp.email_id employeeEmail,
            emp.department_id as departmentId,emp.designation_id as designationId
            From employees emp
            """;

    public final static String EMPLOYEE_PREVIEW_QUERY = """
            SELECT emp.id,emp.employee_code AS employeeCode,emp.employee_profile_image employeeProfileImage,emp.salutation_id AS salutationId,emp.full_name AS fullName,
            emp.gender_id AS genderId,emp.date_of_birth AS dateOfBirth,emp.contact_no_country_code AS contactNoCountryCode,emp.contact_no AS contactNo,emp.email_id AS emailId,emp.department_id AS departmentId,
            emp.designation_id AS designationId,emp.user_type_id AS userTypeId,emp.employee_type_id AS employeeTypeId,emp.date_of_joining AS dateOfJoining,emp.blood_group_id AS bloodGroupId,emp.marital_status_id AS martialStatusId,
            nat.nationality_name AS nationalityName,emp.religion_id AS religionId,emp.caste_id AS casteId,emp.department_id AS departmentId,emp.designation_id AS designationId,emp.user_type_id AS userTypeId,
            emp.reporting_authority_id AS reportingAuthorityId, emp.uan_no AS uanNo,
            emp.father_salutation_id AS fatherSalutationId, emp.father_name AS fatherName, emp.father_contact_no_country_code AS fatherContactCode,emp.father_contact_no fatherContactNo,emp.father_email_id AS fatherEmailId,
            emp.mother_salutation_id AS motherSalutationId, emp.mother_name AS motherName,emp.mother_contact_no_country_code AS motherContactCode,emp.mother_contact_no AS motherContactNo,emp.mother_email_id AS motherEmailId,
            emp.spouse_salutation_id AS spouseSalutationId,emp.spouse_name spouseName,emp.spouse_contact_no_country_code AS spouseContactCode,emp.spouse_contact_no spouseContactNo,emp.spouse_email_id AS spouseEmailId,
            emp.permanent_address AS permanentAddress,pc.country_name AS permanentCountry,ps.name AS permanentState,pCty.name AS permanentCity,emp.permanent_pin_code AS permanentPinCode,
            emp.corresponding_address AS correspondingAddress,cc.country_name AS correspondingCountry,cs.name AS correspondingState,cCty.name AS correspondingCity,emp.corresponding_pin_code AS correspondingPinCode
            FROM employees emp
            LEFT JOIN country_codes nat ON emp.nationality_id = nat.id
            LEFT JOIN country_codes pc ON emp.permanent_country_id = pc.id
            LEFT JOIN state ps ON emp.permanent_state_id = ps.id
            LEFT JOIN city pCty ON emp.permanent_city_id = pCty.id
            LEFT JOIN country_codes cc ON emp.corresponding_country_id = cc.id
            LEFT JOIN state cs ON emp.corresponding_state_id = cs.id
            LEFT JOIN city cCty ON emp.corresponding_city_id = cCty.id
            """;
}
