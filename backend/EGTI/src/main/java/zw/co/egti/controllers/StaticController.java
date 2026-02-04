/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zw.co.egti.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author kmagwenzi
 */
@Controller
public class StaticController {
    
    @GetMapping("/about-us")
    public String viewAboutUs(){
       
        return "about-us";
    }
    
    @GetMapping("/apply")
    public String viewApply(){
       
        return "apply";
    }

    @GetMapping("/fees")
    public String viewFees(){
       
        return "fees";
    }
    
    @GetMapping(value={"", "/", "/home"})
    public String viewHome(){
       
        return "index";
    }
    
    @GetMapping("/programs")
    public String viewPrograms(){
       
        return "programs";
    }
    
    
}
