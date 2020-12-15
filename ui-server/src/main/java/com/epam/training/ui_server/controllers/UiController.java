package com.epam.training.ui_server.controllers;

import com.epam.training.ui_server.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class UiController {

    private final HttpServletRequest request;
    private final UserService userService;

    @GetMapping("/")
    public String getRoot() {
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String getMain(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal));
        model.addAttribute("role", userService.getUserRole(principal));
        return "main_page";
    }

    @GetMapping("/employees")
    public String getAdminEmployees(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal));
        model.addAttribute("role", userService.getUserRole(principal));
        model.addAttribute("employees", userService.getEmployees(principal));
        return "employees_page";
    }

    @GetMapping("/admin/departments")
    public String getAdminDepartments(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal));
        model.addAttribute("role", userService.getUserRole(principal));
        model.addAttribute("currentDepartment", userService.getCurrentDepartmentInfo(principal));
        model.addAttribute("childrenDepartments", userService.getChildrenDepartmentsInfo(principal));
        return "departments_page";
    }

    @GetMapping("/admin/reports")
    public String getAdminReports(Model model, Principal principal) {
        model.addAttribute("user", userService.getUser(principal));
        model.addAttribute("role", userService.getUserRole(principal));
        return "reports_page";
    }

    @GetMapping("/logout")
    public String logout() throws ServletException {
        request.logout();
        return "redirect:/main";
    }
}
