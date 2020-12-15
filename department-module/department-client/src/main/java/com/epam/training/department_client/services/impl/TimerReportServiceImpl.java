package com.epam.training.department_client.services.impl;

import com.epam.training.common_employee_api.api.EmployeeResourceApi;
import com.epam.training.common_employee_api.dto.EmployeeDto;
import com.epam.training.department_client.models.Department;
import com.epam.training.department_client.models.Report;
import com.epam.training.department_client.repositories.EmployeeRepository;
import com.epam.training.department_client.repositories.ReportRepository;
import com.epam.training.department_client.services.EmployeeService;
import com.epam.training.department_client.services.TimerReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class TimerReportServiceImpl implements TimerReportService {

    private final EmployeeService employeeService;
    private final ReportRepository reportRepository;
    private Map<Long, Double> departmentId2SalaryFund;

    @Scheduled(fixedRate = 60000)
    @Override
    public void recalculateReports() {
        setDepartmentIdToSalaryFund();
        createOrUpdateReports();
        deleteDeletedDepartmentsFromReports();
    }

    private void setDepartmentIdToSalaryFund() {
        departmentId2SalaryFund = new HashMap<>();
        List<EmployeeDto> employees = employeeService.getAll();
        employees.forEach(employee -> departmentId2SalaryFund.merge(employee.getDepartmentId(), employee.getSalary(), Double::sum));
    }

    private void createOrUpdateReports() {
        departmentId2SalaryFund.forEach((departmentId, salaryFund) -> {
            Optional<Report> oldReport = reportRepository.findByDepartmentId(departmentId);
            if (oldReport.isPresent()) {
                Report updatableReport = oldReport.get();
                updatableReport.setSalaryFund(salaryFund);
                reportRepository.save(updatableReport);
            } else {
                var newReport = new Report();
                var department = new Department();
                department.setId(departmentId);
                newReport.setDepartment(department);
                newReport.setSalaryFund(salaryFund);
                reportRepository.save(newReport);
            }
        });
    }

    private void deleteDeletedDepartmentsFromReports() {
        List<Report> reports = reportRepository.findAll();
        reports.removeIf(report -> departmentId2SalaryFund.containsKey(report.getDepartment().getId()));
        reportRepository.deleteAll(reports);
    }
}
