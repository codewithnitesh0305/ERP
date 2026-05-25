package com.springboot.Dto.Organization.OrganizationDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class OrganizationResponseDto {

    private Long id;
    private String name;
    private String code;
    private String affiliationNo;
    private String contactCode;
    private String contactNo;
    private String emailId;
    private Long currencyId;
    private String webSiteUrl;
    private Boolean isMoreBranch;
    private String address;
    private Long countryId;
    private Long stateId;
    private Long cityId;
    private String pinCode;
    private String faceBookUrl;
    private String instagramUrl;
    private String youTubeUrl;
    private String linkedInUrl;
    private String xUrl;
}
