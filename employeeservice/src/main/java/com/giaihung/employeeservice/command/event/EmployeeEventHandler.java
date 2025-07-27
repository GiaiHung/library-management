package com.giaihung.employeeservice.command.event;

import com.giaihung.employeeservice.command.data.Employee;
import com.giaihung.employeeservice.command.data.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventHandler {
  private final EmployeeRepository employeeRepository;

  @EventHandler
  public void on(EmployeeCreatedEvent event) {
    Employee employee = new Employee();
    BeanUtils.copyProperties(event, employee);
    employeeRepository.save(employee);
  }

  @EventHandler
  public void on(EmployeeUpdatedEvent event) {
    Employee employee =
        employeeRepository
            .findById(event.getId())
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    employee.setFirstName(event.getFirstName());
    employee.setLastName(event.getLastName());
    employee.setKin(event.getKin());
    employee.setIsDiscipline(event.getIsDiscipline());
    employeeRepository.save(employee);
  }

  @EventHandler
  public void on(EmployeeDeletedEvent event) {
    try {
      employeeRepository
          .findById(event.getId())
          .orElseThrow(() -> new RuntimeException("Employee not found"));
      employeeRepository.deleteById(event.getId());
    } catch (RuntimeException e) {
      log.error(e.getMessage());
    }
  }
}
