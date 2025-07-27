package com.giaihung.borrowingservice.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowingPayload {
  private String bookId;

  private String employeeId;
}
