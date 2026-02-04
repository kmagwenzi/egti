/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package zw.co.egti.repositories;


/**
 *
 * @author kmagwenzi
 */
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.egti.entities.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    public Page<Application> findByNameContainingIgnoreCase(String keyword, Pageable pageable);
    
    
    
}
