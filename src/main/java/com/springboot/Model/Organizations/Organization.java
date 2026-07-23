package com.springboot.Model.Organizations;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organizations" ,uniqueConstraints = {@UniqueConstraint(columnNames = {"name","code","affiliated_no"})})
@NoArgsConstructor
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_logo")
    private String organizationLogo;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "affiliation_no")
    private String affiliationNo;

    @Column(name = "contact_code")
    private String contactCode;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "website_url")
    private String webSiteUrl;

    @Column(name = "is_more_branch")
    private Boolean isMoreBranch;

    @Column(name = "address")
    private String address;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "pin_code")
    private Long pinCode;

    @Column(name = "facebook_url")
    private String faceBookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "youtube_url")
    private String youTubeUrl;

    @Column(name = "linkedIn_url")
    private String linkedInUrl;

    @Column(name = "x_url")
    private String xUrl;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_on")
    private String updatedOn;
}
