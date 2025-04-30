package com.giaihung.bookservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRequestModel {
  private String id;

  @NotBlank(message = "Book name must not be empty")
  @Size(min = 2, max = 30, message = "Book name must be at least 2 characters")
  private String name;

  @NotBlank(message = "Author name must not be empty")
  private String author;

  private boolean isReady;
}
