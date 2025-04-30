package com.giaihung.employeeservice.command.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateEmployeeCommand {
  @TargetAggregateIdentifier private String id;

  private String firstName;

  private String lastName;

  private String kin;

  private Boolean isDiscipline;
}
