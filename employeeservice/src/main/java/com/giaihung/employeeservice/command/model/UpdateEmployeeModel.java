package com.giaihung.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateEmployeeModel {
  @NotBlank(message = "First name must not be blank")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  private String lastName;

  @NotBlank(message = "Kin must not be blank")
  private String kin;

  @NotNull(message = "Disciplined must not be null")
  private Boolean isDiscipline;
}
