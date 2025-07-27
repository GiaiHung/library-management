package com.giaihung.borrowingservice.command.aggregate;

import com.giaihung.borrowingservice.command.command.CreateBorrowingCommand;
import com.giaihung.borrowingservice.command.event.CreateBorrowingEvent;
import com.giaihung.borrowingservice.command.event.DeleteBorrowingEvent;
import com.giaihung.commonservice.command.command.DeleteBorrowingCommand;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
@NoArgsConstructor
@Getter
@Setter
public class BorrowingAggregate {
  @AggregateIdentifier private String id;

  private String bookId;

  private String employeeId;

  private Date borrowingDate;

  private Date returnDate;

  @CommandHandler
  public BorrowingAggregate(CreateBorrowingCommand command) {
    CreateBorrowingEvent event = new CreateBorrowingEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handle(DeleteBorrowingCommand command) {
    DeleteBorrowingEvent event = new DeleteBorrowingEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(CreateBorrowingEvent event) {
    this.id = event.getId();
    this.bookId = event.getBookId();
    this.employeeId = event.getEmployeeId();
    this.borrowingDate = event.getBorrowingDate();
  }

  @EventSourcingHandler
  public void on(DeleteBorrowingEvent event) {
    this.id = event.getId();
  }
}
