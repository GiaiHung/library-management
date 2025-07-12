package com.giaihung.bookservice.command.aggregate;

import com.giaihung.bookservice.command.command.*;
import com.giaihung.bookservice.command.event.*;
import com.giaihung.commonservice.command.command.RollbackBookStatusCommand;
import com.giaihung.commonservice.command.command.UpdateBookStatusCommand;
import com.giaihung.commonservice.command.event.BookStatusRollbackEvent;
import com.giaihung.commonservice.command.event.BookStatusUpdatedEvent;
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
public class BookAggregate {
  @AggregateIdentifier private String id;
  private String name;
  private String author;
  private boolean isReady;

  @CommandHandler
  public BookAggregate(CreateBookCommand createBookCommand) {
    BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();
    BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
    AggregateLifecycle.apply(bookCreatedEvent);
  }

  @CommandHandler
  public void handleUpdateBook(UpdateBookCommand command) {
    BookUpdatedEvent updateEvent = new BookUpdatedEvent();
    BeanUtils.copyProperties(command, updateEvent);
    AggregateLifecycle.apply(updateEvent);
  }

  @CommandHandler
  public void handleDeleteBook(DeleteBookCommand command) {
    BookDeletedEvent deleteEvent = new BookDeletedEvent();
    BeanUtils.copyProperties(command, deleteEvent);
    AggregateLifecycle.apply(deleteEvent);
  }

  @CommandHandler
  public void handleUpdateBookStatus(UpdateBookStatusCommand command) {
    BookStatusUpdatedEvent event = new BookStatusUpdatedEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @CommandHandler
  public void handleRollbackBookStatus(RollbackBookStatusCommand command) {
    BookStatusRollbackEvent event = new BookStatusRollbackEvent();
    BeanUtils.copyProperties(command, event);
    AggregateLifecycle.apply(event);
  }

  @EventSourcingHandler
  public void on(BookCreatedEvent bookCreatedEvent) {
    this.id = bookCreatedEvent.getId();
    this.name = bookCreatedEvent.getName();
    this.author = bookCreatedEvent.getAuthor();
    this.isReady = bookCreatedEvent.isReady();
  }

  @EventSourcingHandler
  public void on(BookUpdatedEvent bookUpdatedEvent) {
    this.id = bookUpdatedEvent.getId();
    this.name = bookUpdatedEvent.getName();
    this.author = bookUpdatedEvent.getAuthor();
    this.isReady = bookUpdatedEvent.isReady();
  }

  @EventSourcingHandler
  public void on(BookDeletedEvent bookDeletedEvent) {
    this.id = bookDeletedEvent.getId();
  }

  @EventSourcingHandler
  public void on(BookStatusUpdatedEvent bookStatusUpdatedEvent) {
    this.id = bookStatusUpdatedEvent.getBookId();
    this.isReady = bookStatusUpdatedEvent.getIsReady();
  }

  @EventSourcingHandler
  public void on(BookStatusRollbackEvent bookStatusRollbackEvent) {
    this.id = bookStatusRollbackEvent.getBookId();
    this.isReady = bookStatusRollbackEvent.getIsReady();
  }
}
