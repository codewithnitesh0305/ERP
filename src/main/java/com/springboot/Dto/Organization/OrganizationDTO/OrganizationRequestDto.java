package com.springboot.Dto.Organization.OrganizationDTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class OrganizationRequestDto {
    private Long id;

    private Boolean isChange;
    private MultipartFile file;
    @NotEmpty(message = "Organization Name is required.")
    private String name;
    @NotEmpty(message = "Organization Code is required.")
    private String code;
    @NotEmpty(message = "Affiliation No is required.")
    private String affiliationNo;
    @NotEmpty(message = "Kindly select contact code.")
    private String contactCode;
    @NotEmpty(message = "Contact No. is required.")
    private String contactNo;
    @Email(message = "Invalid email id.")
    private String emailId;
    @NotNull(message = "Kindly select currency.")
    private Long currencyId;
    private String webSiteUrl;
    private Boolean isMoreBranch;
    @NotEmpty(message = "Address is required.")
    private String address;
    @NotNull(message = "Kindly select country.")
    private Long countryId;
    @NotNull(message = "Kindly select state.")
    private Long stateId;
    @NotNull(message = "Kindly select city.")
    private Long cityId;
    @NotNull(message = "Pin code is required")
    @Min(value = 100000, message = "Pin code must be 6 digits")
    @Max(value = 999999, message = "Pin code must be 6 digits")
    private Long pinCode;
    private String faceBookUrl;
    private String instagramUrl;
    private String youTubeUrl;
    private String linkedInUrl;
    private String xUrl;
}
