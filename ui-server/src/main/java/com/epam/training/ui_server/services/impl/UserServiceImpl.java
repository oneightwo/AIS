package com.epam.training.ui_server.services.impl;

import com.epam.training.common_department_api.api.DepartmentResourceApi;
import com.epam.training.common_department_api.dto.DepartmentDto;
import com.epam.training.common_department_api.dto.DepartmentInformationDto;
import com.epam.training.common_department_api.dto.DepartmentTransferDto;
import com.epam.training.common_department_api.dto.PositionDto;
import com.epam.training.common_employee_api.api.EmployeeResourceApi;
import com.epam.training.common_employee_api.dto.DismissalEmployeeDto;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.ui_server.dto.UiDepartmentInfo;
import com.epam.training.ui_server.dto.UiEmployee;
import com.epam.training.ui_server.models.DepartmentSnapshot;
import com.epam.training.ui_server.models.EmployeeSnapshot;
import com.epam.training.ui_server.models.KeycloakUserToEmployee;
import com.epam.training.ui_server.repositories.DepartmentRepository;
import com.epam.training.ui_server.repositories.EmployeeRepository;
import com.epam.training.ui_server.repositories.KeycloakUserToEmployeeRepository;
import com.epam.training.ui_server.services.KeycloakUserService;
import com.epam.training.ui_server.services.UserService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final KeycloakUserToEmployeeRepository keycloakUserToEmployeeRepository;
    private final KeycloakUserService keycloakUserService;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final DepartmentResourceApi departmentResourceApi;
    private final EmployeeResourceApi employeeResourceApi;
    private final MapperFacade mapper;

    @Override
    public UiEmployee getUser(Principal principal) {
        String userId = getKeycloakUserId(principal);
        KeycloakUserToEmployee byKeycloakUserId = keycloakUserToEmployeeRepository.findByKeycloakUserId(userId)
                .orElseThrow(() -> new RuntimeException("ERROR"));
        return mapper.map(byKeycloakUserId.getEmployee(), UiEmployee.class);
    }

    @Override
    public String getUserRole(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        boolean isAdmin = keycloakAuthenticationToken.getAccount().getRoles().stream().anyMatch(role -> role.equals("admin"));
        return isAdmin ? "admin" : "user";
    }

    @Override
    public DepartmentDto getCurrentDepartment(Principal principal) {
        String userId = getKeycloakUserId(principal);
        KeycloakUserToEmployee keycloakUserToEmployee = keycloakUserToEmployeeRepository.findByKeycloakUserId(userId)
                .orElseThrow(() -> new RuntimeException("ERROR"));
        return mapper.map(keycloakUserToEmployee.getEmployee().getDepartment(), DepartmentDto.class);
    }

    @Override
    public UiDepartmentInfo getCurrentDepartmentInfo(Principal principal) {
        DepartmentInformationDto department = departmentResourceApi.getDepartmentInfoById(getEmployee(principal).getDepartment().getId());
        return getUiDepartmentInfo(department);
    }

    @Override
    public List<UiDepartmentInfo> getChildrenDepartmentsInfo(Principal principal) {
        List<UiDepartmentInfo> departmentInfoList = new ArrayList<>();
        departmentResourceApi.getChildrenHierarchy(getEmployee(principal).getDepartment().getId())
                .forEach(departmentDto -> {
                    departmentInfoList.add(getUiDepartmentInfo(departmentResourceApi.getDepartmentInfoById(departmentDto.getId())));
                });
        return departmentInfoList;
    }

    @Override
    public List<UiEmployee> getEmployees(Principal principal) {
        if (keycloakUserService.getRole(principal).contains("admin")) {
            return mapper.mapAsList(employeeRepository.findAllByDepartmentId(getEmployee(principal).getDepartment().getId())
                    .stream()
                    .filter(s -> Objects.isNull(s.getDateOfDismissal()))
                    .filter(s -> !s.getIsLeader())
                    .collect(Collectors.toList()), UiEmployee.class);
        }
        return mapper.mapAsList(employeeRepository.findAllByDepartmentId(getEmployee(principal).getDepartment().getId())
                .stream()
                .filter(s -> Objects.isNull(s.getDateOfDismissal()))
                .collect(Collectors.toList()), UiEmployee.class);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return mapper.map(employeeRepository.findById(id).orElse(new EmployeeSnapshot()), EmployeeDto.class);
    }

    @Override
    public List<DepartmentDto> getDepartmentsAvailableForCurrentPrincipal(Principal principal) {
        EmployeeSnapshot employee = getEmployee(principal);
        List<DepartmentDto> childrenHierarchy = departmentResourceApi.getChildrenHierarchy(employee.getDepartment().getId());
        childrenHierarchy.add(mapper.map(employee.getDepartment(), DepartmentDto.class));
        return childrenHierarchy;
    }

    @Override
    public List<PositionDto> getPositions() {
        return departmentResourceApi.getAllPositions();
    }

    @Override
    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        keycloakUserService.updateUser(mapper.map(employeeDto, EmployeeSnapshot.class));
        return employeeResourceApi.updateEmployee(employeeDto.getId(), employeeDto);
    }

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        return employeeResourceApi.saveEmployee(employeeDto);
    }

    @Override
    public void dismissalEmployee(DismissalEmployeeDto dismissalEmployeeDto) {
        try {
            employeeResourceApi.dismissEmployee(dismissalEmployeeDto.getId(), dismissalEmployeeDto);
            keycloakUserService.deleteUser(dismissalEmployeeDto);
        } catch (RuntimeException e) {

        }
    }

    @Override
    public DepartmentDto createDepartment(DepartmentDto departmentDto) {
        return departmentResourceApi.createDepartment(departmentDto);
    }

    @Override
    public DepartmentDto updateDepartment(DepartmentDto departmentDto) {
        var resultDepartmentDto = new DepartmentDto();
        DepartmentSnapshot departmentSnapshot = departmentRepository.findById(departmentDto.getId())
                .orElseThrow(() -> new RuntimeException("ERROR updateDepartment"));
        if (!departmentDto.getName().equals(departmentSnapshot.getName())) {
            resultDepartmentDto = departmentResourceApi.renameDepartment(departmentDto.getId(), departmentDto);
        }
        if (Objects.nonNull(departmentDto.getParentId()) &&
                !departmentDto.getParentId().equals(departmentSnapshot.getParent().getId())) {
            var departmentTransferDto = new DepartmentTransferDto();
            departmentTransferDto.setCurrentDepartmentId(departmentDto.getId());
            departmentTransferDto.setDestinationDepartmentId(departmentDto.getParentId());
            resultDepartmentDto = departmentResourceApi.transferDepartment(departmentDto.getId(), departmentTransferDto);
        }
        return resultDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        return mapper.map(departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("ERROR getDepartmentByName")), DepartmentDto.class);
    }

    @Override
    public void deleteDepartment(Long departmentId) {
        departmentResourceApi.deleteById(departmentId);
    }

    @Override
    public File createReportByDepartments(Principal principal) {
        List<UiDepartmentInfo> departments = getChildrenDepartmentsInfo(principal);
        departments.add(getCurrentDepartmentInfo(principal));
        String path = createDepartmentsDocument(departments);
        return new File(path);
    }

    private String createDepartmentsDocument(List<UiDepartmentInfo> departments) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + "\\departments_report.pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(path));
            document.open();
            BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //подключаем файл шрифта, который поддерживает кириллицу
            Font font = new Font(bf);
            Paragraph title1 = new Paragraph("Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA,
                            18, Font.BOLDITALIC, new CMYKColor(0, 255, 255, 17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);
            Paragraph title11 = new Paragraph("Отчет по департаментам", font);
            Section section1 = chapter1.addSection(title11);
            PdfPTable t = new PdfPTable(4);
            t.setSpacingBefore(25);
            t.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("Название", font));
            t.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Дата создания", font));
            t.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("ФИО руководителя", font));
            t.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("Количество сотрудников", font));
            t.addCell(c4);
            for (UiDepartmentInfo departmentInfo : departments) {
                t.addCell(new Phrase(departmentInfo.getName(), font));
                t.addCell(new Phrase(departmentInfo.getCreationDate().toString(), font));
                var leader = departmentInfo.getLeader();
                t.addCell(new Phrase(leader.getSurname() + " " + leader.getName() + " " + leader.getPatronymic(), font));
                t.addCell(new Phrase(departmentInfo.getNumberEmployees().toString(), font));

            }
            section1.add(t);
            document.add(section1);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
        return path;
    }

    @Override
    public File createReportByEmployees(Principal principal) {
        List<UiEmployee> employees = getEmployees(principal);
        employees.add(mapper.map(getEmployee(principal), UiEmployee.class));
        String path = createEmployeesDocument(employees);
        return new File(path);
    }

    private String createEmployeesDocument(List<UiEmployee> employees) {
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        String path = FileSystemView.getFileSystemView().getHomeDirectory() + "\\employees_report.pdf";
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(path));
            document.open();
            BaseFont bf = BaseFont.createFont("c:/Windows/Fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); //подключаем файл шрифта, который поддерживает кириллицу
            Font font = new Font(bf);
            Paragraph title1 = new Paragraph("Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA,
                            18, Font.BOLDITALIC, new CMYKColor(0, 255, 255, 17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);
            Paragraph title11 = new Paragraph("Отчет по сотрудникам", font);
            Section section1 = chapter1.addSection(title11);
            PdfPTable t = new PdfPTable(6);
            t.setSpacingBefore(25);
            t.setSpacingAfter(25);
            PdfPCell c1 = new PdfPCell(new Phrase("ФИО", font));
            t.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Дата рождения", font));
            t.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("Пол", font));
            t.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("Email", font));
            t.addCell(c4);
            PdfPCell c5 = new PdfPCell(new Phrase("Дата трудоустройства", font));
            t.addCell(c5);
            PdfPCell c6 = new PdfPCell(new Phrase("Должность", font));
            t.addCell(c6);
            for (UiEmployee employee : employees) {
                t.addCell(new Phrase(employee.getSurname() + " " + employee.getName() + " " + employee.getPatronymic(), font));
                t.addCell(new Phrase(employee.getDateOfBirth().toString(), font));
                t.addCell(new Phrase(employee.getGender(), font));
                t.addCell(new Phrase(employee.getEmail(), font));
                t.addCell(new Phrase(employee.getEmploymentDate().toString(), font));
                t.addCell(new Phrase(employee.getPositionName(), font));
            }
            section1.add(t);
            document.add(section1);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.close();
        return path;
    }

    private UiDepartmentInfo getUiDepartmentInfo(DepartmentInformationDto department) {
        var uiDepartmentInfo = new UiDepartmentInfo();
        uiDepartmentInfo.setId(department.getId());
        uiDepartmentInfo.setName(department.getName());
        uiDepartmentInfo.setCreationDate(department.getCreationDate());
        uiDepartmentInfo.setNumberEmployees(department.getNumberEmployees());
        if (Objects.nonNull(department.getLeader())) {
            uiDepartmentInfo.setLeader(mapper.map(employeeRepository.findById(department.getLeader().getId())
                    .orElse(new EmployeeSnapshot()), UiEmployee.class));
        } else {
            uiDepartmentInfo.setLeader(new UiEmployee());
        }
        return uiDepartmentInfo;
    }

    private EmployeeSnapshot getEmployee(Principal principal) {
        String userId = getKeycloakUserId(principal);
        KeycloakUserToEmployee keycloakUserToEmployee = keycloakUserToEmployeeRepository.findByKeycloakUserId(userId)
                .orElseThrow(() -> new RuntimeException("ERROR"));
        return keycloakUserToEmployee.getEmployee();
    }

    private String getKeycloakUserId(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        return keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getIdToken().getSubject();
    }
}
