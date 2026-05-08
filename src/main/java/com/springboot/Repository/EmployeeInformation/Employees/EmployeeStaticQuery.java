package com.springboot.Repository.EmployeeInformation.Employees;

public class EmployeeStaticQuery {

    public final static String EMPLOYEE_LIST = "Select emp.id,emp.employee_code as EmployeeCode,emp.employee_profile_image employeeProfileImage,emp.salutation_id as salutationId,emp.full_name as fullName," +
            "emp.gender_id as genderId,emp.date_of_birth as dateOfBirth,emp.contact_no_country_code as contactNoCountryCode,emp.contact_no as contactNo,emp.email_id as emailId,emp.department_id as departmentId,emp.designation_id as designationId," +
            "emp.user_type_id as userTypeId,emp.employee_type_id as employeeTypeId,emp.date_of_joining as dateOfJoining" +
            "From employees emp";
}
