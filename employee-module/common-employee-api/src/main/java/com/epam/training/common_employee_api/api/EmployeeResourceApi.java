package com.epam.training.common_employee_api.api;

import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.services.EmployeeResourceApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "employee-server/employees", fallback = EmployeeResourceApiFallback.class/* fallbackFactory = EmployeeResourceFallbackFactory.class*/)
public interface EmployeeResourceApi {

    @GetMapping("/departments/{id}/leaders")
    EmployeeDto getLeaderInDepartment(@PathVariable long id);

    @GetMapping("/departments/{id}")
    List<EmployeeDto> getEmployeesByDepartmentId(@PathVariable long id);

    @GetMapping("/")
    List<EmployeeDto> getAll();

    @PutMapping("/{id}")
    EmployeeDto updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto);

    @DeleteMapping("/{id}")
    EmployeeDto dismissEmployee(@PathVariable Long id, @RequestBody @Valid DismissalEmployeeDto dismissalEmployeeDto);

    @PostMapping
    EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto employeeDto);
}
