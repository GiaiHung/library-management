package com.giaihung.employeeservice.command.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateEmployeeModel {
  @NotBlank(message = "First name must not be blank")
  private String firstName;

  @NotBlank(message = "Last name must not be blank")
  private String lastName;

  @NotBlank(message = "Kin must not be blank")
  private String kin;
}
