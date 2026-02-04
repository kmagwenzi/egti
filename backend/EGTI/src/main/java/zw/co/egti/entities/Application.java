/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zw.co.egti.entities;



import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import zw.co.egti.entities.enums.Gender;
import zw.co.egti.entities.enums.MaritalStatus;
import zw.co.egti.entities.enums.Program;
import zw.co.egti.entities.enums.Sponsorship;

/**
 *
 * @author kmagwenzi
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="applications")
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    
    @Column(name = "profile_image")
    private String profileImage;
    
    private String name;
    
    @Column(name = "maiden_name")
    private String maidenName;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "place_of_birth")
    private String placeOfBirth;
    
    private String nationality;
    
    @Column(name = "country_of_residence")
    private String countryOfResidence;
    
    @Column(name = "identity_number")
    private String identityNumber;
    
    @Column(name = "passport_number")
    private String passportNumber;
    
    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    
    @Column(name = "contact_number_1")
    private String contactNumber1;
    
    @Column(name = "contact_number_2")
    private String contactNumber2;
    
    private String email;
    
    @Enumerated(EnumType.STRING)
    private Program program;
    
    private String exemptions;
    
    @Enumerated(EnumType.STRING)
    private Sponsorship sponsorship;
    
    
    @Column(name = "sponsors_fullname")
    private String sponsorsFullName;
    
    @DateTimeFormat (pattern="yyyy-MM-dd")
    @Column(name = "startingDate")
    private LocalDate startingDate;
    
    @Column(name = "date_created")
    @CreationTimestamp
    private Date dateCreated;

    @Column(name = "last_updated")
    @UpdateTimestamp
    private Date lastUpdated;
    
    public Long getId() {
        return id;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
