package com.giaihung.bookservice.command.aggregate;

import com.giaihung.bookservice.command.command.CreateBookCommand;
import com.giaihung.bookservice.command.command.DeleteBookCommand;
import com.giaihung.bookservice.command.command.UpdateBookCommand;
import com.giaihung.bookservice.command.event.BookCreatedEvent;
import com.giaihung.bookservice.command.event.BookDeletedEvent;
import com.giaihung.bookservice.command.event.BookUpdatedEvent;
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
}
