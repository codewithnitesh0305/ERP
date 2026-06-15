package com.springboot.Model.Organizations;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization_branch", uniqueConstraints = {@UniqueConstraint(columnNames = {"organization_name","organization_code","affiliated_no"})})
@NoArgsConstructor
@Data
public class OrganizationBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "organization_id")
    private Long organizationId;

    @Column(name = "organization_name")
    private String name;

    @Column(name = "organization_code")
    private String organizationCode;

    @Column(name = "affiliated_no")
    private String affiliatedNo;

    @Column(name = "contact_country_code")
    private String contactCountryCode;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "organization_email")
    private String emailId;

    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "branch_logo")
    private String  branchLogo;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "country_id")
    private Long countryId;

    @Column(name = "state_id")
    private Long stateId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_on")
    private String createOn;

    @Column(name = "updated_by")
    private Long updateBy;

    @Column(name = "updated_on")
    private String updatedOn;
}
