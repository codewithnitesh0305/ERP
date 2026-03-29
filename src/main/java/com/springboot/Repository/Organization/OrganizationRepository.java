package com.springboot.Repository.Organization;

import com.springboot.Model.Organizations.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Organization findByNameAndOrganizationCodeAndAffiliatedNo(String organizationName,String organizationCode,String affiliatedNo);

    @Query(nativeQuery = true, value = """
            Select org.organization_logo->>'$.serving_url' AS organizationLogo,org.organization_name as organizationName,org.organization_code as organizationCode,
            org.affiliated_no as affiliatedNo,org.mobile_code_id as mobileCodeId,org.contact_no as contactNo,org.organization_email as organizationEmail,
            org.currency_id as currencyId,org.website_url as webSiteUrl,org.address,org.country_id as countryId,org.state_id as stateId,org.city_id as cityId,org.pin_code as pinCode,
            org.facebook_url as facebookUrl, org.instagram_url as instagramUrl,org.youtube_url as youtubeUrl,org.x_url as xUrl From organization org where id = :organizationId;
            """)
    List<Map<String,Object>> getOrganizationDetails(@Param("organizationId") Long organizationId);
}
