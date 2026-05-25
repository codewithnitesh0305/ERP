package com.springboot.Controller.Organization;

import com.springboot.Dto.Organization.OrganizationBranchDTO.OrganizationBranchRequestDto;
import com.springboot.Dto.Organization.OrganizationDTO.OrganizationRequestDto;
import com.springboot.Dto.Organization.OrganizationDTO.OrganizationResponseDto;
import com.springboot.Model.Organizations.Organization;
import com.springboot.Payload.Response;
import com.springboot.Service.Organization.BloodGroup.BloodGroupService;
import com.springboot.Service.Organization.Caste.CasteService;
import com.springboot.Service.Organization.Department.DepartmentService;
import com.springboot.Service.Organization.Designaiton.DesignationService;
import com.springboot.Service.Organization.FinancialYear.FinancialYearService;
import com.springboot.Service.Organization.Gender.GenderService;
import com.springboot.Service.Organization.Organization.OrganizationService;
import com.springboot.Service.Organization.OrganizationBranch.OrganizationBranchService;
import com.springboot.Service.Organization.Profession.ProfessionService;
import com.springboot.Service.Organization.Qualification.QualificationService;
import com.springboot.Service.Organization.Religion.ReligionService;
import com.springboot.Service.Organization.Salutation.SalutationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/organization")
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    private final OrganizationBranchService organizationBranchService;
    private final DepartmentService departmentService;
    private final DesignationService designationService;
    private final BloodGroupService bloodGroupService;
    private final SalutationService salutationService;
    private final ReligionService religionService;
    private final CasteService casteService;
    private final GenderService genderService;
    private final QualificationService qualificationService;
    private final ProfessionService professionService;
    private final FinancialYearService financialYearService;

    @PostMapping("/organization")
    public ResponseEntity<?> saveUpdateOrganization(@RequestPart(required = false) MultipartFile file, @Valid @RequestPart OrganizationRequestDto organizationRequestDto, HttpServletRequest request) throws IOException {
        return organizationService.saveUpdateOrganization(file,organizationRequestDto,request);
    }

    @GetMapping("/organization")
    public ResponseEntity<?> getOrganizationDetails(@RequestParam Long organizationId,HttpServletRequest request) {
        return new ResponseEntity<>(new Response<>("Success",organizationService.getOrganizationDetails(organizationId,request)),HttpStatus.OK);
    }

    @PostMapping("/organization-branch")
    public ResponseEntity<?> saveUpdateOrganizationBranch(@RequestPart(required = false) MultipartFile file, @Valid @RequestPart OrganizationBranchRequestDto organizationBranchRequestDto, HttpServletRequest request) {
        return organizationBranchService.saveUpdateOrganizationBranch(file,organizationBranchRequestDto,request);
    }

    @GetMapping("/organization-branch")
    public ResponseEntity<?> getOrganizationBranchDetails( @RequestParam Map<String, Object> param,HttpServletRequest request) {
        return new ResponseEntity<>(new Response<>("Success",organizationBranchService.getOrganizationDetails(param,request)),HttpStatus.OK);
    }

    @PostMapping("/department")
    public ResponseEntity<?> saveUpdateDepartment(@RequestBody Map<String,Object> param, HttpServletRequest request){
        return departmentService.saveUpdateDepartment(param,request);
    }

    @GetMapping("/department")
    public ResponseEntity<?> getAllDepartment(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Success",departmentService.getAllDepartment(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/department")
    public ResponseEntity<?> deleteDepartment(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  departmentService.deleteDepartment(param,request);
    }

    @PatchMapping("/department")
    public ResponseEntity<?> updateDepartmentStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return departmentService.updateDepartmentStatus(parma,request);
    }

    @PostMapping("/designation")
    public ResponseEntity<?> saveUpdateDesignation(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return designationService.saveUpdateDesignation(param,request);
    }

    @GetMapping("/designation")
    public ResponseEntity<?> getAllDesignation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",designationService.getAllDesignation(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/designation")
    public ResponseEntity<?> deleteDesignation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  designationService.deleteDesignation(param,request);
    }

    @PatchMapping("/designation")
    public ResponseEntity<?> updateDesignationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return designationService.updateDesignationStatus(parma,request);
    }

    @PostMapping("/blood-group")
    public ResponseEntity<?> saveUpdateBloodGroup(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return bloodGroupService.saveUpdateBloodGroup(param,request);
    }

    @GetMapping("/blood-group")
    public ResponseEntity<?> getAllBloodGroup(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",bloodGroupService.getAllBloodGroup(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/blood-group")
    public ResponseEntity<?> deleteBloodGroup(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  bloodGroupService.deleteBloodGroup(param,request);
    }

    @PatchMapping("/blood-group")
    public ResponseEntity<?> updateBloodGroupStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return bloodGroupService.updateBloodGroupStatus(parma,request);
    }

    @PostMapping("/salutation")
    public ResponseEntity<?> saveUpdateSalutation(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return salutationService.saveUpdateSalutation(param,request);
    }

    @GetMapping("/salutation")
    public ResponseEntity<?> getAllSalutation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",salutationService.getAllSalutation(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/salutation")
    public ResponseEntity<?> deleteSalutation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  salutationService.deleteSalutation(param,request);
    }

    @PatchMapping("/salutation")
    public ResponseEntity<?> updateSalutationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return salutationService.updateSalutationStatus(parma,request);
    }

    @PostMapping("/religion")
    public ResponseEntity<?> saveUpdateReligion(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return religionService.saveUpdateReligion(param,request);
    }

    @GetMapping("/religion")
    public ResponseEntity<?> getAllReligion(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",religionService.getAllReligion(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/religion")
    public ResponseEntity<?> deleteReligion(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  religionService.deleteReligion(param,request);
    }

    @PatchMapping("/religion")
    public ResponseEntity<?> updateReligionStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return religionService.updateReligionStatus(parma,request);
    }

    @PostMapping("/caste")
    public ResponseEntity<?> saveUpdateCaste(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return casteService.saveUpdateCaste(param,request);
    }

    @GetMapping("/caste")
    public ResponseEntity<?> getAllCaste(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",casteService.getAllCaste(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/caste")
    public ResponseEntity<?> deleteCaste(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  casteService.deleteCaste(param,request);
    }

    @PatchMapping("/caste")
    public ResponseEntity<?> updateCasteStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return casteService.updateCasteStatus(parma,request);
    }

    @PostMapping("/gender")
    public ResponseEntity<?> saveUpdateGender(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return genderService.saveUpdateGender(param,request);
    }

    @GetMapping("/gender")
    public ResponseEntity<?> getAllGender(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",genderService.getAllGender(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/gender")
    public ResponseEntity<?> deleteGender(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  genderService.deleteGender(param,request);
    }

    @PatchMapping("/gender")
    public ResponseEntity<?> updateGenderStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return genderService.updateGenderStatus(parma,request);
    }

    @PostMapping("/qualification")
    public ResponseEntity<?> saveUpdateQualification(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return qualificationService.saveUpdateQualification(param,request);
    }

    @GetMapping("/qualification")
    public ResponseEntity<?> getAllQualification(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",qualificationService.getAllQualification(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/qualification")
    public ResponseEntity<?> deleteQualification(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  qualificationService.deleteQualification(param,request);
    }

    @PatchMapping("/qualification")
    public ResponseEntity<?> updateQualificationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return qualificationService.updateQualificationStatus(parma,request);
    }


    @PostMapping("/profession")
    public ResponseEntity<?> saveUpdateProfession(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return professionService.saveUpdateProfession(param,request);
    }

    @GetMapping("/profession")
    public ResponseEntity<?> getAllProfession(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",professionService.getAllProfession(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/profession")
    public ResponseEntity<?> deleteProfession(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  professionService.deleteProfession(param,request);
    }

    @PatchMapping("/profession")
    public ResponseEntity<?> updateProfessionStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return professionService.updateProfessionStatus(parma,request);
    }

    @PostMapping("/financial-year")
    public ResponseEntity<?> saveUpdateFinancialYear(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return financialYearService.saveUpdateFinancialYear(param,request);
    }

    @GetMapping("/financial-year")
    public ResponseEntity<?> getAllFinancialYear(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",financialYearService.getAllFinancialYear(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/financial-year")
    public ResponseEntity<?> deleteFinancialYear(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  financialYearService.deleteFinancialYear(param,request);
    }

    @PatchMapping("/financial-year")
    public ResponseEntity<?> updateFinancialYearStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return financialYearService.updateFinancialYearStatus(parma,request);
    }

}
