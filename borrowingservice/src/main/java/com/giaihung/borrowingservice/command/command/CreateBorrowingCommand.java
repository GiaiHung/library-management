package com.giaihung.borrowingservice.command.command;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowingCommand {
  @TargetAggregateIdentifier private String id;

  private String bookId;

  private String employeeId;

  private Date borrowingDate;
}
