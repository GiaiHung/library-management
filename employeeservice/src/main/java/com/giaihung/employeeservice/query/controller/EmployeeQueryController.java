package com.giaihung.employeeservice.query.controller;

import com.giaihung.employeeservice.query.model.EmployeeDTO;
import com.giaihung.employeeservice.query.model.EmployeeSearchCriteria;
import com.giaihung.employeeservice.query.queries.GetAllEmployeesQuery;
import com.giaihung.employeeservice.query.queries.GetDetailEmployeeQuery;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeQueryController {
  private final QueryGateway queryGateway;

  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getEmployees(@ModelAttribute EmployeeSearchCriteria criteria) {
    List<EmployeeDTO> employees = queryGateway.query(
            new GetAllEmployeesQuery(criteria.getIsDiscipline() != null && criteria.getIsDiscipline()),
            ResponseTypes.multipleInstancesOf(EmployeeDTO.class)).join();
    return ResponseEntity.ok(employees);
  }

  @GetMapping("/{employeeId}")
  public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable("employeeId") String employeeId) {
    EmployeeDTO employee = queryGateway.query(
            new GetDetailEmployeeQuery(employeeId),
            ResponseTypes.instanceOf(EmployeeDTO.class)).join();
    return ResponseEntity.ok(employee);
  }
}
