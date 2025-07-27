package com.giaihung.borrowingservice.command.event;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowingEvent {
  private String id;

  private String bookId;

  private String employeeId;

  private Date borrowingDate;
}
