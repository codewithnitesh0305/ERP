package com.springboot.Dto.Organization;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationBranchRequestDto {

    private Long id;
    private String organizationLogo;
    private Boolean isLogoChange;
    @NotEmpty(message = "Organization name is required.")
    private String name;
    @NotEmpty(message = "Organization code is required.")
    private String organizationCode;
    @NotEmpty(message = "Organization affiliation no. is required.")
    private String affiliatedNo;
    private String contactCountryCode;
    @NotEmpty(message = "Organization contact no. is required.")
    private String contactNo;
    @NotEmpty(message = "Organization emailId is required.")
    private String organizationEmailId;
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
