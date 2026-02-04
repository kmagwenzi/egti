/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zw.co.egti.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.egti.entities.Application;
import zw.co.egti.repositories.ApplicationRepository;

/**
 *
 * @author kmagwenzi
 */
@Service
public class ApplicationsServiceImplementation implements ApplicationService {
    
    ApplicationRepository applicationRepository;
    @Autowired
    public void setApplicationRepository(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    

    @Override
    public boolean saveApplication(Application application) {
        try {
			if (application!= null) {
				applicationRepository.save(application);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
    }

    @Override
    public Application findSpecificApplicant(Long id) {
        
       Optional<Application> item = applicationRepository.findById(id);
        if (item.isPresent()) {
            return item.get();
        }
        
        throw new RuntimeException("Applicant cannot be found with id "+id);
    }

    @Override
    public Page<Application> findAll(Pageable paging) {
       return applicationRepository.findAll(paging);
    }

    @Override
    public Page<Application> findByNameContainingIgnoreCase(String keyword, Pageable paging) {
        return applicationRepository.findByNameContainingIgnoreCase(keyword, paging);
    }

    @Override
    public Application updateApplicant(Application application) {
        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplicant(Long id) {
        applicationRepository.deleteById(id);
    }

  
    
}
