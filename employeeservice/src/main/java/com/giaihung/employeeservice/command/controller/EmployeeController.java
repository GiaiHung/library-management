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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {
  private final CommandGateway commandGateway;

  @PostMapping
  public ResponseEntity<String> createEmployee(@Valid @RequestBody CreateEmployeeModel payload) {
    CreateEmployeeCommand command =
        CreateEmployeeCommand.builder()
            .id(UUID.randomUUID().toString())
            .firstName(payload.getFirstName())
            .lastName(payload.getLastName())
            .kin(payload.getKin())
            .isDiscipline(false)
            .build();

    return ResponseEntity.status(HttpStatus.CREATED).body(commandGateway.sendAndWait(command));
  }

  @PutMapping("/{employeeId}")
  public ResponseEntity<Void> updateEmployee(
      @Valid @RequestBody UpdateEmployeeModel model, @PathVariable String employeeId) {
    UpdateEmployeeCommand command =
        UpdateEmployeeCommand.builder()
            .id(employeeId)
            .firstName(model.getFirstName())
            .lastName(model.getLastName())
            .kin(model.getKin())
            .isDiscipline(model.getIsDiscipline())
            .build();
    commandGateway.sendAndWait(command);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{employeeId}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable("employeeId") String employeeId) {
    DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
    commandGateway.sendAndWait(command);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
