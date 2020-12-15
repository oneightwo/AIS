package com.epam.training.department_client.services;

import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_employee_api.api.EmployeeResourceApi;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.core_common_api.exceptions.MethodNotSupportedException;
import com.epam.training.core_common_api.exceptions.ObjectNotFoundException;
import com.epam.training.department_client.data.DepartmentTestDataProvider;
import com.epam.training.department_client.data.EmployeeTestDataProvider;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.department_client.dto.TotalEmployeeSalary;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.repositories.DepartmentRepository;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.validators.DepartmentValidator;
import ma.glasnost.orika.MapperFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @MockBean
    private MapperFacade mapper;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private DepartmentValidator departmentValidator;
    @MockBean
    private EmployeeResourceApi employeeResourceApi;
    @MockBean
    private DepartmentHistory departmentHistory;


    @Test
    void getByIdWithCorrectId() {
        assertEquals(gatDataForGetById(),
                departmentService.getById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void getByIdWithIncorrectId() {
        gatDataForGetById();
        assertThrows(ObjectNotFoundException.class, () -> departmentService.getById(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID));
    }

    private DepartmentDto gatDataForGetById() {
        Department departmentWithChildren = DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(departmentWithChildren));
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(departmentWithChildren));
        return DepartmentTestDataProvider.map(departmentWithChildren);
    }

    @Test
    void getInformationByIdWithCorrectId() {
        assertEquals(getDataDepartmentInformation(),
                departmentService.getInformationById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void getInformationByIdWithIncorrectId() {
        getDataDepartmentInformation();
        assertThrows(ObjectNotFoundException.class,
                () -> departmentService.getInformationById(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID));
    }

    private DepartmentInformationDto getDataDepartmentInformation() {
        var departmentInformation = new DepartmentInformationDto();
        departmentInformation.setName(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID).getName());
        departmentInformation.setLeader(EmployeeTestDataProvider.getEmployeeLeaderDto());
        departmentInformation.setNumberEmployees(List.of(EmployeeTestDataProvider.getEmployeeDto()).size());
        departmentInformation.setCreationDate(DepartmentTestDataProvider.CREATION_DATE);
        Department departmentWithChildren = DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(departmentWithChildren));
        EmployeeDto employeeLeaderDto = EmployeeTestDataProvider.getEmployeeLeaderDto();
        when(employeeRepository.findEmployeeByDepartmentIdAndIsLeaderTrue(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(EmployeeTestDataProvider.map(employeeLeaderDto)));
        when(employeeRepository.findAllByDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(List.of(EmployeeTestDataProvider.map(EmployeeTestDataProvider.getEmployeeLeaderDto())));
        when(mapper.map(any(), eq(EmployeeDto.class)))
                .thenReturn(employeeLeaderDto);
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(departmentWithChildren));
        return departmentInformation;
    }

    @Test
    void renameWithCorrectId() {
        DepartmentDto departmentDto = getDataForRename();
        assertEquals(departmentDto, departmentService.rename(departmentDto));
    }

    @Test
    void renameWithIncorrectId() {
        DepartmentDto departmentDto = getDataForRename();
        departmentDto.setId(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID);
        assertThrows(ObjectNotFoundException.class,
                () -> departmentService.rename(departmentDto));
    }

    private DepartmentDto getDataForRename() {
        Department department = DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(department));
        department.setName("NEW_NAME");
        when(departmentRepository.save(any()))
                .thenReturn(department);
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(department));
        return DepartmentTestDataProvider.map(department);
    }

    @Test
    void update() {
        assertThrows(MethodNotSupportedException.class,
                () -> departmentService.update(DepartmentTestDataProvider
                        .map(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))));
    }

    @Test
    void save() {
        DepartmentDto departmentDto = DepartmentTestDataProvider
                .map(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
        when(departmentRepository.save(any()))
                .thenReturn(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(departmentDto);
        assertEquals(departmentDto,
                departmentService.save(departmentDto));
    }

    @Test
    void getChildrenHierarchyWithCorrectId() {
        assertEquals(getDataForChildrenHierarchy(),
                departmentService.getChildrenHierarchy(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void getChildrenHierarchyWithIncorrectId() {
        getDataForChildrenHierarchy();
        assertThrows(ObjectNotFoundException.class,
                () -> departmentService.getChildrenHierarchy(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private List<DepartmentDto> getDataForChildrenHierarchy() {
        List<DepartmentDto> departmentDtoList = List.of(
                DepartmentTestDataProvider.map(
                        DepartmentTestDataProvider.getDepartmentWithChildren(
                                DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID,
                                List.of(DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID,
                                        DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)))),
                DepartmentTestDataProvider.map(
                        DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(
                                DepartmentTestDataProvider.THIRD_DEPARTMENT_ID,
                                DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)));
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(mapper.mapAsList(anyList(), eq(DepartmentDto.class)))
                .thenReturn(departmentDtoList);
        return departmentDtoList;
    }

    @Test
    void getCurrentChildrenWithCorrectId() {
        assertEquals(getDataForCurrentChildren(),
                departmentService.getCurrentChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void getCurrentChildrenWithIncorrectId() {
        getDataForCurrentChildren();
        assertThrows(ObjectNotFoundException.class,
                () -> departmentService.getCurrentChildren(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private List<DepartmentDto> getDataForCurrentChildren() {
        List<DepartmentDto> departmentDtoList = List.of(
                DepartmentTestDataProvider.map(
                        DepartmentTestDataProvider.getDepartmentWithChildren(
                                DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID,
                                List.of(DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID,
                                        DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)))));
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(mapper.mapAsList(anyList(), eq(DepartmentDto.class)))
                .thenReturn(departmentDtoList);
        return departmentDtoList;
    }

    @Test
    void getByNameWithCorrectName() {
        assertEquals(getDataForGetByName(),
                departmentService.getByName(DepartmentTestDataProvider.DEPARTMENT_NAME));
    }

    @Test
    void getByNameWithIncorrectName() {
        getDataForGetByName();
        assertThrows(ObjectNotFoundException.class, () -> departmentService.getByName("OTHER_NAME"));
    }

    private DepartmentDto getDataForGetByName() {
        Department departmentWithChildren = DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findByName(DepartmentTestDataProvider.DEPARTMENT_NAME))
                .thenReturn(Optional.of(departmentWithChildren));
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(departmentWithChildren));
        return DepartmentTestDataProvider.map(departmentWithChildren);
    }

    @Test
    void departmentTransferWithCorrectIds() {
        var departmentTransferDto = new DepartmentTransferDto();
        departmentTransferDto.setCurrentDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        departmentTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID);
        var departmentDto = getDataForDepartmentTransfer(departmentTransferDto);
        assertTrue(departmentDto.getParentId().equals(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID) &&
                departmentDto.getChildren().isEmpty());
    }

    @Test
    void departmentTransferWithIncorrectIds() {
        var departmentTransferDto = new DepartmentTransferDto();
        departmentTransferDto.setCurrentDepartmentId(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID);
        departmentTransferDto.setDestinationDepartmentId(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID);
        assertThrows(ObjectNotFoundException.class, () -> getDataForDepartmentTransfer(departmentTransferDto));
    }

    private DepartmentDto getDataForDepartmentTransfer(DepartmentTransferDto departmentTransferDto) {
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
        when(departmentRepository.findById(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID,
                        DepartmentTestDataProvider.SECOND_DEPARTMENT_ID)));
        var department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID,
                DepartmentTestDataProvider.THIRD_DEPARTMENT_ID);
        when(mapper.map(any(), eq(DepartmentDto.class)))
                .thenReturn(DepartmentTestDataProvider.map(department));
        return departmentService.departmentTransfer(departmentTransferDto);
    }

    @Test
    void getParentsHierarchyWithCorrectId() {
        assertEquals(getDataForParentsHierarchy(), departmentService.getParentsHierarchy(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID));
    }

    @Test
    void getParentsHierarchyWithIncorrectId() {
        getDataForParentsHierarchy();
        assertThrows(ObjectNotFoundException.class, () -> departmentService.getParentsHierarchy(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private List<DepartmentDto> getDataForParentsHierarchy() {
        Department department = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID,
                DepartmentTestDataProvider.SECOND_DEPARTMENT_ID);
        department.getParent().setParent(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
        when(departmentRepository.findById(DepartmentTestDataProvider.THIRD_DEPARTMENT_ID))
                .thenReturn(Optional.of(department));
        List<DepartmentDto> departmentDtoList = List.of(DepartmentTestDataProvider.map(department.getParent()),
                DepartmentTestDataProvider.map(department.getParent().getParent()));
        when(mapper.mapAsList(anyList(), eq(DepartmentDto.class)))
                .thenReturn(departmentDtoList);
        return departmentDtoList;
    }

    @Test
    void deleteWithCorrectId() {
        getDataForDelete();
        assertDoesNotThrow(() -> departmentService.delete(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void deleteWithIncorrectId() {
        getDataForDelete();
        assertThrows(ObjectNotFoundException.class, () -> departmentService.delete(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private void getDataForDelete() {
        when(departmentRepository.findById(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(Optional.of(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID)));
    }

    @Test
    void getParentWithCorrectId() {
        assertEquals(getDataForGetParent(),
                departmentService.getParent(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID));
    }

    @Test
    void getParentWithIncorrectId() {
        getDataForGetParent();
        assertThrows(ObjectNotFoundException.class, () -> departmentService.getParent(DepartmentTestDataProvider.INCORRECT_DEPARTMENT_ID));
    }

    private DepartmentDto getDataForGetParent() {
        Department departmentWithoutChildren = DepartmentTestDataProvider.getDepartmentWithParentWithoutChildren(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID,
                DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        when(departmentRepository.findById(DepartmentTestDataProvider.SECOND_DEPARTMENT_ID))
                .thenReturn(Optional.of(departmentWithoutChildren));
        when(mapper.map(departmentWithoutChildren.getParent(), DepartmentDto.class))
                .thenReturn(DepartmentTestDataProvider.map(departmentWithoutChildren.getParent()));
        return DepartmentTestDataProvider.map(DepartmentTestDataProvider.getDepartmentWithoutChildrenAndParent(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    @Test
    void getEmployeeSalaryByDepartmentIdWithCorrectId() {
        assertEquals(getDataForEmployeeSalaryByDepartmentId(),
                departmentService.getEmployeeSalaryByDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID));
    }

    private TotalEmployeeSalary getDataForEmployeeSalaryByDepartmentId() {
        var totalEmployeeSalary = new TotalEmployeeSalary();
        totalEmployeeSalary.setDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID);
        totalEmployeeSalary.setTotalSalary(EmployeeTestDataProvider.getEmployeeDto().getSalary() +
                EmployeeTestDataProvider.getEmployeeDto().getSalary());
        when(employeeRepository.findAllByDepartmentId(DepartmentTestDataProvider.FIRST_DEPARTMENT_ID))
                .thenReturn(List.of(EmployeeTestDataProvider.map(EmployeeTestDataProvider.getEmployeeDto()),
                        EmployeeTestDataProvider.map(EmployeeTestDataProvider.getEmployeeDto())));
        return totalEmployeeSalary;
    }

}
