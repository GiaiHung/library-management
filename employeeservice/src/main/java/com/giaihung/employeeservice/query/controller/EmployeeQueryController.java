package com.giaihung.employeeservice.query.controller;

import com.giaihung.employeeservice.query.model.EmployeeDTO;
import com.giaihung.employeeservice.query.model.EmployeeSearchCriteria;
import com.giaihung.employeeservice.query.queries.GetAllEmployeesQuery;
import com.giaihung.employeeservice.query.queries.GetDetailEmployeeQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
@Tag(name = "Employee Query", description = "Full CRUD Management for Employee Entity")
@Slf4j
public class EmployeeQueryController {
    private final QueryGateway queryGateway;

    @GetMapping
    @Operation(summary = "Get All Employees", description = "Get All Employees with IS DISCIPLINE filter")
    public ResponseEntity<List<EmployeeDTO>> getEmployees(@ModelAttribute EmployeeSearchCriteria criteria) {
        log.info("Calling Get All Employees");
        List<EmployeeDTO> employees = queryGateway.query(
                new GetAllEmployeesQuery(criteria.getIsDiscipline() != null && criteria.getIsDiscipline()),
                ResponseTypes.multipleInstancesOf(EmployeeDTO.class)).join();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get Employee Details", description = "Get Employee Details")
    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") String employeeId) {
        EmployeeDTO employee = queryGateway.query(
                new GetDetailEmployeeQuery(employeeId),
                ResponseTypes.instanceOf(EmployeeDTO.class)).join();
        return ResponseEntity.ok(employee);
    }
}
