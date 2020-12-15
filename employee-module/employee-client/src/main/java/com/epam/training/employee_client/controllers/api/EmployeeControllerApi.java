package com.epam.training.employee_client.controllers.api;

import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.employee_client.dto.EmployeeTransferDto;
import com.epam.training.employee_client.dto.EmployeesTransferDto;
import com.epam.training.employee_client.models.Employee;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public interface EmployeeControllerApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDto saveEmployee(@RequestBody @Valid EmployeeDto employeeDto);

    @GetMapping("/{id}")
    EmployeeDto getEmployeeById(@PathVariable Long id);

    @GetMapping("/departments/{id}")
    List<EmployeeDto> getEmployeesByDepartmentId(@PathVariable Long id);

    @PutMapping("/{id}")
    EmployeeDto updateEmployee(@PathVariable Long id, @RequestBody @Valid EmployeeDto employeeDto);

    @GetMapping("/departments/{id}/leaders")
    EmployeeDto getLeaderInDepartment(@PathVariable Long id);

    @PutMapping("/{id}/transfer")
    EmployeeDto transferEmployeeToAnotherDepartment(@PathVariable Long id, @RequestBody @Valid EmployeeTransferDto moveEmployeeDto);

    @DeleteMapping("/{id}")
    EmployeeDto dismissEmployee(@PathVariable Long id, @RequestBody @Valid DismissalEmployeeDto dismissalEmployeeDto);

    @GetMapping("/{id}/leaders")
    EmployeeDto getEmployeeLeader(@PathVariable Long id);

    @PutMapping("/transfer")
    List<EmployeeDto> transferEmployeesToAnotherDepartment(@RequestBody @Valid EmployeesTransferDto employeesTransferDto);

    @GetMapping
    List<EmployeeDto> getEmployeesByField(
            @And({
                    @Spec(path = "positionId", params = "positionId", spec = Equal.class),
                    @Spec(path = "surname", params = "surname", spec = Like.class),
                    @Spec(path = "name", params = "name", spec = Like.class),
                    @Spec(path = "patronymic", params = "patronymic", spec = Like.class)
            }) Specification<Employee> employeeSpecification);

}
