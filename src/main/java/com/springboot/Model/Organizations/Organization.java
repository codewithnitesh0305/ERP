package com.springboot.Model.Organizations;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization", uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_name","organization_code","affiliated_no"})})
@NoArgsConstructor
@Data
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_name")
    private String name;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "affiliated_no")
    private String affiliatedNo;

    @Column(name = "mobile_code_id")
    private Long mobileCodeId;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "organization_email")
    private String organizationEmailId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "organization_logo")
    private String organizationLogo;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "state_id")
    private Long state;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "facebook_url")
    private String facebookUrl;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "youtube_rul")
    private String youtubeUrl;

    @Column(name = "linkedin_url")
    private String linkedinUtl;

    @Column(name = "x_url")
    private String xUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private LocalDateTime createOn;

    @Column(name = "updated_by")
    private Long updateBy;

    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
}
