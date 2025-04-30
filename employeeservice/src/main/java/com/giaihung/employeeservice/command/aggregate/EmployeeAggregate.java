package com.giaihung.employeeservice.command.aggregate;

import com.giaihung.employeeservice.command.command.CreateEmployeeCommand;
import com.giaihung.employeeservice.command.command.DeleteEmployeeCommand;
import com.giaihung.employeeservice.command.command.UpdateEmployeeCommand;
import com.giaihung.employeeservice.command.event.EmployeeCreatedEvent;
import com.giaihung.employeeservice.command.event.EmployeeDeletedEvent;
import com.giaihung.employeeservice.command.event.EmployeeUpdatedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Aggregate
public class EmployeeAggregate {
  @AggregateIdentifier private String id;

  private String firstName;

  private String lastName;

  private String kin;

  private Boolean isDiscipline;

  @CommandHandler
  public EmployeeAggregate(CreateEmployeeCommand command) {
    EmployeeCreatedEvent event = new EmployeeCreatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handleUpdateEmployee(UpdateEmployeeCommand command) {
    EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handleUpdateEmployee(DeleteEmployeeCommand command) {
    EmployeeUpdatedEvent event = new EmployeeUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handleDeleteEmployee(DeleteEmployeeCommand command) {
    EmployeeDeletedEvent event = new EmployeeDeletedEvent(command.getId());
    AggregateLifecycle.apply(event);
  }

  @EventHandler
  public void on(EmployeeCreatedEvent event) {
    this.id = event.getId();
    this.firstName = event.getFirstName();
    this.lastName = event.getLastName();
    this.kin = event.getKin();
    this.isDiscipline = event.getIsDiscipline();
  }

  @EventHandler
  public void on(EmployeeUpdatedEvent event) {
    this.id = event.getId();
    this.firstName = event.getFirstName();
    this.lastName = event.getLastName();
    this.kin = event.getKin();
    this.isDiscipline = event.getIsDiscipline();
  }

  @EventHandler
  public void on(EmployeeDeletedEvent event) {
    this.id = event.getId();
  }
}
