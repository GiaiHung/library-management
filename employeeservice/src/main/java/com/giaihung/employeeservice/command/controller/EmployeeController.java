package com.giaihung.employeeservice.command.controller;

import com.giaihung.employeeservice.command.command.CreateEmployeeCommand;
import com.giaihung.employeeservice.command.command.DeleteEmployeeCommand;
import com.giaihung.employeeservice.command.command.UpdateEmployeeCommand;
import com.giaihung.employeeservice.command.model.CreateEmployeeModel;
import com.giaihung.employeeservice.command.model.UpdateEmployeeModel;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
  private final CommandGateway commandGateway;

  @PostMapping
  public String createEmployee(@Valid @RequestBody CreateEmployeeModel payload) {
    CreateEmployeeCommand command =
        CreateEmployeeCommand.builder()
            .id(UUID.randomUUID().toString())
            .firstName(payload.getFirstName())
            .lastName(payload.getLastName())
            .kin(payload.getKin())
            .isDiscipline(false)
            .build();

    return commandGateway.sendAndWait(command);
  }

  @PutMapping("/{employeeId}")
  public String updateEmployee(
      @Valid @RequestBody UpdateEmployeeModel model, @PathVariable String employeeId) {
    UpdateEmployeeCommand command =
        UpdateEmployeeCommand.builder()
            .id(employeeId)
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .kin(model.getKin())
            .isDiscipline(model.getIsDiscipline())
            .build();
    return commandGateway.sendAndWait(command);
  }

  @DeleteMapping("/{employeeId}")
  public String deleteEmployee(@PathVariable("employeeId") String employeeId) {
    DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
    return commandGateway.sendAndWait(command);
  }
}
