/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zw.co.egti.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.nio.file.Files.size;
import org.slf4j.Logger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import zw.co.egti.entities.Application;
import zw.co.egti.services.ApplicationService;

/**
 *
 * @author kmagwenzi
 */
@Controller
public class ApplicationController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/uploads/profile-image";
    private final String BASE_URL = "http://localhost:4040/";
    
    ApplicationService applicationService;
    @Autowired
    public void setApplicationsService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    

    @PostMapping("/apply")
    public ResponseEntity<Application> saveApplication(@ModelAttribute Application application, final @RequestParam("file") MultipartFile file) { //

        //log.info("file = " + file.toString());
        log.info("application = " + application.toString());
        log.info("name = " + application.getName());
        log.info("gender = " + application.getGender());
        log.info("dateOfBirth = " + application.getDateOfBirth());

        try {
            HttpHeaders headers = new HttpHeaders();
            if (application == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Ensure upload directory exists
            Path uploadPath = Paths.get(uploadDirectory);
            Files.createDirectories(uploadPath);

            // Sanitize filename
            String originalFileName = file.getOriginalFilename();
            if (originalFileName == null || originalFileName.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            String fileName = new File(originalFileName).getName(); // strip path
            // Generate unique filename to avoid overwriting
            String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
            String filePath = Paths.get(uploadDirectory, uniqueFileName).toString();

            // Save the file locally
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            stream.write(file.getBytes());
            stream.close();

            application.setProfileImage(uniqueFileName);

            boolean status = applicationService.saveApplication(application);
            if (status) {
                log.info("Application Created");
                headers.add("Application Saved With Image - ", uniqueFileName);
                return new ResponseEntity<>(application, HttpStatus.CREATED);
            } else {
                log.info("Application creation failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // log.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/applications")
    public String findAllApplications(Model model, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size) {

        List<Application> applications = new ArrayList<Application>();

        try {

            Pageable paging = PageRequest.of(page - 1, size);

            Page<Application> pagedApplication;

            if (keyword == null) {
                pagedApplication = applicationService.findAll(paging);
                log.info("keyword(null) = " + pagedApplication.toString());
            } else {
                pagedApplication = applicationService.findByNameContainingIgnoreCase(keyword, paging);
                model.addAttribute("keyword", keyword);
            }

            applications = pagedApplication.getContent();

            // model.addAttribute("applications", applications);
            // model.addAttribute("currentPage", pagedApplication.getNumber() + 1);
            // model.addAttribute("totalItems", pagedApplication.getTotalElements());
            // model.addAttribute("totalPages", pagedApplication.getTotalPages());
            // model.addAttribute("pageSize", size);
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }

        log.info("application = " + applications.toString());
        model.addAttribute("applications", applications);
        model.addAttribute("baseUrl", BASE_URL);

        return "view-applications";
    }

    @GetMapping("/applications/{id}")
    public String viewDetailedApplicantData(Model model, @PathVariable Long id) {

        Application applicant = applicationService.findSpecificApplicant(id);

        model.addAttribute("applicant", applicant);

        return "applicant-detailed-view";
    }

    @GetMapping("/applications/update-applicant/{id}")
    public String viewUpdateApplicant(Model model, @PathVariable Long id){
        
        Application applicant = applicationService.findSpecificApplicant(id);
        
        model.addAttribute("applicant", applicant);
        model.addAttribute("baseUrl", BASE_URL);
        
        return "update-applicant";
    }
    
    @PostMapping("/applications/update-applicant/{id}")
    public String saveUpdateApplicant(@RequestBody Application application, @PathVariable Long id){
        
        application.setId(id);
        
        applicationService.updateApplicant(application);
 
        return "update-applicant";
    }
    
    
    @GetMapping("/applications/delete-applicant/{id}")
    public String deleteApplicant(Model model, @PathVariable Long id){
        
    applicationService.deleteApplicant(id);
        
        return "redirect:/applications";
    }

}
