/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zw.co.egti.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author kmagwenzi
 */
@Controller
public class StaticController {
    
     @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Home - EGTI Zimbabwe");
        return "index";
    }
    
    @GetMapping("/programs")
    public String programs(Model model) {
        model.addAttribute("pageTitle", "Programs - EGTI Zimbabwe");
        return "programs";
    }
    
    @GetMapping("/fees")
    public String fees(Model model) {
        model.addAttribute("pageTitle", "Fees - EGTI Zimbabwe");
        return "fees";
    }
    
    @GetMapping("/apply")
    public String application(Model model) {
        model.addAttribute("pageTitle", "Application - EGTI Zimbabwe");
        return "apply";
    }
    
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "About Us - EGTI Zimbabwe");
        return "about";
    }
    
    
}
