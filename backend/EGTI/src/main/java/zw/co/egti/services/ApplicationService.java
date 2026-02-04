package zw.co.egti.services;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.egti.entities.Application;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author kmagwenzi
 */
@Service
public interface ApplicationService {

    public boolean saveApplication(Application application);

    public Application findSpecificApplicant(Long id);

    public Page<Application> findAll(Pageable paging);

    public Page<Application> findByNameContainingIgnoreCase(String keyword, Pageable paging);

    public Application updateApplicant(Application application);

    public void deleteApplicant(Long id);


}
