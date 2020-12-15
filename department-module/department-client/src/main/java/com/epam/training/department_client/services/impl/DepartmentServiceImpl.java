package com.epam.training.department_client.services.impl;

import com.epam.training.common_department_api.constants.DepartmentExceptionConstants;
import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.department_client.constants.HistoryType;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.department_client.dto.TotalEmployeeSalary;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.models.EmployeeSnapshot;
import com.epam.training.department_client.repositories.DepartmentRepository;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.services.DepartmentHistory;
import com.epam.training.department_client.services.DepartmentService;
import com.epam.training.department_client.validators.DepartmentValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final MapperFacade mapper;
    private final DepartmentRepository departmentRepository;
    private final DepartmentValidator departmentValidator;
    private final EmployeeRepository employeeRepository;
    private final DepartmentHistory departmentHistory;

    @Transactional(readOnly = true)
    @Override
    public DepartmentDto getById(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return mapper.map(department, DepartmentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public DepartmentInformationDto getInformationById(Long departmentId) {
        var departmentInformationDto = new DepartmentInformationDto();
        DepartmentDto departmentDto = mapper.map(getDepartmentById(departmentId), DepartmentDto.class);
        departmentInformationDto.setId(departmentDto.getId());
        departmentInformationDto.setName(departmentDto.getName());
        departmentInformationDto.setCreationDate(departmentDto.getCreationDate());
        departmentInformationDto.setLeader(mapper.map(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(departmentId)
                .orElse(null), EmployeeDto.class));
        departmentInformationDto.setNumberEmployees(employeeRepository.findAllByDepartmentId(departmentId).size());
        return departmentInformationDto;
    }

    @Transactional
    @Override
    public DepartmentDto rename(DepartmentDto departmentDto) {
        departmentHistory.update(getDepartmentById(departmentDto.getId()), () -> {
            Department department = getDepartmentById(departmentDto.getId());
            department.setName(departmentDto.getName());
            return departmentRepository.save(department);
        });
        return mapper.map(getDepartmentById(departmentDto.getId()), DepartmentDto.class);
    }

    @Transactional
    @Override
    public DepartmentDto save(DepartmentDto departmentDto) {
        departmentValidator.validateCreation(departmentDto);
        Department department = mapper.map(departmentDto, Department.class);
        Department newDepartment = departmentRepository.save(department);
        departmentHistory.save(department, HistoryType.CREATE);
        return mapper.map(newDepartment, DepartmentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentDto> getChildrenHierarchy(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return mapper.mapAsList(getChildrenList(department.getChildren()), DepartmentDto.class);
    }

    private List<Department> getChildrenList(List<Department> children) {
        List<Department> tempChildren = new ArrayList<>(children);
        children.stream()
                .filter(department -> !department.getChildren().isEmpty())
                .map(department -> getChildrenList(department.getChildren()))
                .forEach(tempChildren::addAll);
        return tempChildren;
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentDto> getCurrentChildren(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return mapper.mapAsList(department.getChildren(), DepartmentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public DepartmentDto getByName(String name) {
        Department department = departmentRepository.findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_WITH_THIS_NAME_DOES_NOT_EXIST));
        return mapper.map(department, DepartmentDto.class);
    }

    @Transactional
    @Override
    public DepartmentDto departmentTransfer(DepartmentTransferDto departmentTransferDto) {
        departmentHistory.update(getDepartmentById(departmentTransferDto.getCurrentDepartmentId()), () -> {
            Department currentDepartment = getDepartmentById(departmentTransferDto.getCurrentDepartmentId());
            currentDepartment.getChildren().forEach(child -> departmentHistory.update(child, () -> {
                child.setParent(currentDepartment.getParent());
                return child;
            }));
            Department destinationDepartment = getDepartmentById(departmentTransferDto.getDestinationDepartmentId());
            destinationDepartment.getChildren().add(currentDepartment);
            currentDepartment.setParent(destinationDepartment);
            currentDepartment.setChildren(Collections.emptyList());
            return currentDepartment;
        });
        return mapper.map(getDepartmentById(departmentTransferDto.getCurrentDepartmentId()), DepartmentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentDto> getParentsHierarchy(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return mapper.mapAsList(getParentList(department), DepartmentDto.class);
    }

    private List<Department> getParentList(Department department) {
        List<Department> departments = new LinkedList<>();
        if (Objects.nonNull(department.getParent())) {
            departments.add(department.getParent());
            departments.addAll(getParentList(department.getParent()));
            return departments;
        }
        return departments;
    }

    @Transactional
    @Override
    public void delete(Long departmentId) {
        departmentValidator.validateDeletion(getById(departmentId));
        departmentHistory.save(getDepartmentById(departmentId), HistoryType.DELETE);
        departmentRepository.deleteById(departmentId);
    }

    @Transactional(readOnly = true)
    @Override
    public DepartmentDto getParent(Long departmentId) {
        Department department = getDepartmentById(departmentId);
        return mapper.map(department.getParent(), DepartmentDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public TotalEmployeeSalary getEmployeeSalaryByDepartmentId(Long departmentId) {
        departmentValidator.validateDepartment(departmentId);
        var employeeSalary = new TotalEmployeeSalary();
        employeeSalary.setDepartmentId(departmentId);
        employeeSalary.setTotalSalary(getTotalSalaryByDepartmentId(departmentId));
        return employeeSalary;
    }

    private Double getTotalSalaryByDepartmentId(Long departmentId) {
        return employeeRepository.findAllByDepartmentId(departmentId).stream()
                .mapToDouble(EmployeeSnapshot::getSalary)
                .sum();
    }

    private Department getDepartmentById(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ObjectNotFoundException(DepartmentExceptionConstants.DEPARTMENT_WITH_THIS_ID_DOES_NOT_EXIST));
    }
}
