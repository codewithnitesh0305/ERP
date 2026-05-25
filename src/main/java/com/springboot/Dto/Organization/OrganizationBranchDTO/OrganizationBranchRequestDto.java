package com.springboot.Dto.Organization.OrganizationBranchDTO;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationBranchRequestDto {

    private Long id;
    private String organizationLogo;
    private Boolean isLogoChange;
    @NotEmpty(message = "Name is required.")
    private String name;
    @NotEmpty(message = "Code is required.")
    private String organizationCode;
    @NotEmpty(message = "Affiliation No. is required.")
    private String affiliatedNo;
    private String contactCountryCode;
    @NotEmpty(message = "Contact no. is required.")
    private String contactNo;
    @NotEmpty(message = "Email ID is required.")
    private String emailId;
    @NotNull(message = "Kindly select currency.")
    private Long currencyId;

    @NotEmpty(message = "Address is required.")
    private String address;
    @NotNull(message = "Kindly select country.")
    private Long countryId;
    @NotNull(message = "Kindly select state.")
    private Long stateId;
    @NotNull(message = "Kindly select city.")
    private Long cityId;
    @NotNull(message = "Pin Code is required.")
    private String pinCode;

}
