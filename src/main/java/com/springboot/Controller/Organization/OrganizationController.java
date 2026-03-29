package com.springboot.Controller.Organization;

import com.springboot.Payload.Response;
import com.springboot.Service.Organization.BloodGroup.BloodGroupService;
import com.springboot.Service.Organization.Caste.CasteService;
import com.springboot.Service.Organization.Department.DepartmentService;
import com.springboot.Service.Organization.Designaiton.DesignationService;
import com.springboot.Service.Organization.FinancialYear.FinancialYearService;
import com.springboot.Service.Organization.Gender.GenderService;
import com.springboot.Service.Organization.OrganizationService;
import com.springboot.Service.Organization.Profession.ProfessionService;
import com.springboot.Service.Organization.Qualification.QualificationService;
import com.springboot.Service.Organization.Religion.ReligionService;
import com.springboot.Service.Organization.Salutation.SalutationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/organization")
@AllArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
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

    @PostMapping("/save-update-organization")
    public ResponseEntity<?> saveUpdateOrganization(@RequestParam("file") MultipartFile file, @RequestParam Map<String, Object> param,HttpServletRequest request) {
        return organizationService.saveUpdateOrganization(file,param,request);
    }

    @GetMapping("/organization-details")
    public ResponseEntity<?> getOrganizationDetails( @RequestParam Map<String, Object> param,HttpServletRequest request) {
        return new ResponseEntity<>(new Response<>("Success",organizationService.getOrganizationDetails(param,request)),HttpStatus.OK);
    }

    @PostMapping("/save-update-department")
    public ResponseEntity<?> saveUpdateDepartment(@RequestBody Map<String,Object> param, HttpServletRequest request){
        return departmentService.saveUpdateDepartment(param,request);
    }

    @GetMapping("/get-all-department")
    public ResponseEntity<?> getAllDepartment(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Success",departmentService.getAllDepartment(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-department")
    public ResponseEntity<?> deleteDepartment(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  departmentService.deleteDepartment(param,request);
    }

    @PatchMapping("/update-department-status")
    public ResponseEntity<?> updateDepartmentStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return departmentService.updateDepartmentStatus(parma,request);
    }

    @PostMapping("/save-update-designation")
    public ResponseEntity<?> saveUpdateDesignation(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return designationService.saveUpdateDesignation(param,request);
    }

    @GetMapping("/get-all-designation")
    public ResponseEntity<?> getAllDesignation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",designationService.getAllDesignation(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-designation")
    public ResponseEntity<?> deleteDesignation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  designationService.deleteDesignation(param,request);
    }

    @PatchMapping("/update-designation-status")
    public ResponseEntity<?> updateDesignationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return designationService.updateDesignationStatus(parma,request);
    }

    @PostMapping("/save-update-blood-group")
    public ResponseEntity<?> saveUpdateBloodGroup(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return bloodGroupService.saveUpdateBloodGroup(param,request);
    }

    @GetMapping("/get-all-blood-group")
    public ResponseEntity<?> getAllBloodGroup(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",bloodGroupService.getAllBloodGroup(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-blood-group")
    public ResponseEntity<?> deleteBloodGroup(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  bloodGroupService.deleteBloodGroup(param,request);
    }

    @PatchMapping("/update-blood-group-status")
    public ResponseEntity<?> updateBloodGroupStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return bloodGroupService.updateBloodGroupStatus(parma,request);
    }

    @PostMapping("/save-update-salutation")
    public ResponseEntity<?> saveUpdateSalutation(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return salutationService.saveUpdateSalutation(param,request);
    }

    @GetMapping("/get-all-salutation")
    public ResponseEntity<?> getAllSalutation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",salutationService.getAllSalutation(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-salutation")
    public ResponseEntity<?> deleteSalutation(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  salutationService.deleteSalutation(param,request);
    }

    @PatchMapping("/update-salutation-status")
    public ResponseEntity<?> updateSalutationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return salutationService.updateSalutationStatus(parma,request);
    }

    @PostMapping("/save-update-religion")
    public ResponseEntity<?> saveUpdateReligion(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return religionService.saveUpdateReligion(param,request);
    }

    @GetMapping("/get-all-religion")
    public ResponseEntity<?> getAllReligion(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",religionService.getAllReligion(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-religion")
    public ResponseEntity<?> deleteReligion(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  religionService.deleteReligion(param,request);
    }

    @PatchMapping("/update-religion-status")
    public ResponseEntity<?> updateReligionStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return religionService.updateReligionStatus(parma,request);
    }

    @PostMapping("/save-update-caste")
    public ResponseEntity<?> saveUpdateCaste(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return casteService.saveUpdateCaste(param,request);
    }

    @GetMapping("/get-all-caste")
    public ResponseEntity<?> getAllCaste(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",casteService.getAllCaste(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-caste")
    public ResponseEntity<?> deleteCaste(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  casteService.deleteCaste(param,request);
    }

    @PatchMapping("/update-caste-status")
    public ResponseEntity<?> updateCasteStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return casteService.updateCasteStatus(parma,request);
    }

    @PostMapping("/save-update-gender")
    public ResponseEntity<?> saveUpdateGender(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return genderService.saveUpdateGender(param,request);
    }

    @GetMapping("/get-all-gender")
    public ResponseEntity<?> getAllGender(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",genderService.getAllGender(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-gender")
    public ResponseEntity<?> deleteGender(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  genderService.deleteGender(param,request);
    }

    @PatchMapping("/update-gender-status")
    public ResponseEntity<?> updateGenderStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return genderService.updateGenderStatus(parma,request);
    }

    @PostMapping("/save-update-qualification")
    public ResponseEntity<?> saveUpdateQualification(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return qualificationService.saveUpdateQualification(param,request);
    }

    @GetMapping("/get-all-qualification")
    public ResponseEntity<?> getAllQualification(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",qualificationService.getAllQualification(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-qualification")
    public ResponseEntity<?> deleteQualification(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  qualificationService.deleteQualification(param,request);
    }

    @PatchMapping("/update-qualification-status")
    public ResponseEntity<?> updateQualificationStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return qualificationService.updateQualificationStatus(parma,request);
    }


    @PostMapping("/save-update-profession")
    public ResponseEntity<?> saveUpdateProfession(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return professionService.saveUpdateProfession(param,request);
    }

    @GetMapping("/get-all-profession")
    public ResponseEntity<?> getAllProfession(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",professionService.getAllProfession(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-profession")
    public ResponseEntity<?> deleteProfession(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  professionService.deleteProfession(param,request);
    }

    @PatchMapping("/update-profession-status")
    public ResponseEntity<?> updateProfessionStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return professionService.updateProfessionStatus(parma,request);
    }

    @PostMapping("/save-update-financial-year")
    public ResponseEntity<?> saveUpdateFinancialYear(@RequestBody Map<String,Object> param,HttpServletRequest request){
        return financialYearService.saveUpdateFinancialYear(param,request);
    }

    @GetMapping("/get-all-financial-year")
    public ResponseEntity<?> getAllFinancialYear(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return new ResponseEntity<>(new Response<>("Successfully",financialYearService.getAllFinancialYear(param,request)),HttpStatus.OK);
    }

    @DeleteMapping("/delete-financial-year")
    public ResponseEntity<?> deleteFinancialYear(@RequestParam Map<String,Object> param,HttpServletRequest request){
        return  financialYearService.deleteFinancialYear(param,request);
    }

    @PatchMapping("/update-financial-year-status")
    public ResponseEntity<?> updateFinancialYearStatus(@RequestParam Map<String,Object> parma,HttpServletRequest request){
        return financialYearService.updateFinancialYearStatus(parma,request);
    }

}
