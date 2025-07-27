package com.giaihung.employeeservice.query.projection;

import com.giaihung.commonservice.query.model.EmployeeDTO;
import com.giaihung.commonservice.query.queries.GetDetailEmployeeQuery;
import com.giaihung.employeeservice.command.data.Employee;
import com.giaihung.employeeservice.command.data.EmployeeRepository;
import com.giaihung.employeeservice.query.queries.GetAllEmployeesQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeProjection {
  private final EmployeeRepository employeeRepository;

  @QueryHandler
  public List<EmployeeDTO> handle(GetAllEmployeesQuery query) {
    List<Employee> employees = employeeRepository.findAllByIsDiscipline(query.isDiscipline());
    return employees.stream()
        .map(
            employee -> {
              EmployeeDTO employeeDTO = new EmployeeDTO();
              BeanUtils.copyProperties(employee, employeeDTO);
              return employeeDTO;
            })
        .toList();
  }

  @QueryHandler
  public EmployeeDTO handle(GetDetailEmployeeQuery query) {
    try {
      Employee employee =
          employeeRepository
              .findById(query.getId())
              .orElseThrow(() -> new RuntimeException("Employee not found"));
      EmployeeDTO employeeDTO = new EmployeeDTO();
      BeanUtils.copyProperties(employee, employeeDTO);
      return employeeDTO;
    } catch (RuntimeException e) {
      log.error(e.getMessage());
    }

    return null;
  }
}
