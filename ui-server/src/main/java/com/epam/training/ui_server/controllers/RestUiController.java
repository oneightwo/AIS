package com.epam.training.ui_server.controllers;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.ui_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestUiController {

    private final UserService userService;

    @GetMapping("/employees/{id}")
    public Map<String, Object> getEmployeeForUpdate(@PathVariable Long id, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("employee", userService.getEmployeeById(id));
        response.put("departments", userService.getDepartmentsAvailableForCurrentPrincipal(principal));
        response.put("positions", userService.getPositions());
        return response;
    }

    @GetMapping("/employees/modals")
    public Map<String, Object> getEmployeeDataForCreate(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("departments", userService.getDepartmentsAvailableForCurrentPrincipal(principal));
        response.put("positions", userService.getPositions());
        return response;
    }

    @PostMapping("/employees")
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return ResponseEntity.ok(userService.createEmployee(employeeDto));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto) {
        employeeDto.setId(id);
        return ResponseEntity.ok(userService.updateEmployee(employeeDto));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> dismissalEmployee(@PathVariable Long id, @RequestBody @Valid DismissalEmployeeDto employeeDto) {
        employeeDto.setId(id);
        userService.dismissalEmployee(employeeDto);
        return ResponseEntity.ok().build();
    }

    //------------------------------------------------------------

    @PostMapping("/departments")
    public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentDto departmentDto) {
        return ResponseEntity.ok(userService.createDepartment(departmentDto));
    }

    @PutMapping("/departments/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody @Valid DepartmentDto departmentDto) {
        departmentDto.setId(id);
        return ResponseEntity.ok(userService.updateDepartment(departmentDto));
    }

    @GetMapping("/departments/modals")
    public Map<String, Object> getDepartmentDataForCreate(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("departments", userService.getDepartmentsAvailableForCurrentPrincipal(principal));
        return response;
    }

    @GetMapping("/departments/{id}")
    public Map<String, Object> getDepartmentDataForCreate(@PathVariable Long id, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("department", userService.getDepartmentById(id));
        response.put("departments", userService.getDepartmentsAvailableForCurrentPrincipal(principal));
        return response;
    }

    @DeleteMapping("/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id, Principal principal) {
        userService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    //-------------------------------------------------------------------------

    @GetMapping("/admin/departments/reports")
    public ResponseEntity<?> getReportByDepartment(Principal principal) throws FileNotFoundException {
        File file = userService.createReportByDepartments(principal);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=departments_report.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @GetMapping("/admin/employees/reports")
    public ResponseEntity<?> getReportByEmployees(Principal principal) throws FileNotFoundException {
        File file = userService.createReportByEmployees(principal);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees_report.pdf");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }



}
