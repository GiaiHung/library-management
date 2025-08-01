package com.giaihung.bookservice.command.event;

import com.giaihung.bookservice.command.data.Book;
import com.giaihung.bookservice.command.data.repositories.BookRepository;
import com.giaihung.commonservice.command.event.BookStatusRollbackEvent;
import com.giaihung.commonservice.command.event.BookStatusUpdatedEvent;
import java.util.Optional;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BookEventHandler {
  private final BookRepository bookRepository;

  public BookEventHandler(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @EventHandler
  public void onCreate(BookCreatedEvent event) {
    Book book = new Book();
    BeanUtils.copyProperties(event, book);
    bookRepository.save(book);
  }

  @EventHandler
  public void onUpdate(BookUpdatedEvent event) {
    Optional<Book> currentBookOptional = bookRepository.findById(event.getId());
    if (currentBookOptional.isPresent()) {
      Book currentBook = currentBookOptional.get();
      currentBook.setName(event.getName());
      currentBook.setAuthor(event.getAuthor());
      currentBook.setReady(event.isReady());

      bookRepository.save(currentBook);
    }
  }

  @EventHandler
  public void onDelete(BookDeletedEvent event) {
    Optional<Book> currentBookOptional = bookRepository.findById(event.getId());
    currentBookOptional.ifPresent(book -> bookRepository.deleteById(book.getId()));
  }

  @EventHandler
  public void onUpdateStatus(BookStatusUpdatedEvent event) {
    Optional<Book> bookOptional = bookRepository.findById(event.getBookId());
    bookOptional.ifPresent(
        book -> {
          book.setReady(event.getIsReady());
          bookRepository.save(book);
        });
  }

  @EventHandler
  public void onRollbackStatus(BookStatusRollbackEvent event) {
    Optional<Book> bookOptional = bookRepository.findById(event.getBookId());
    bookOptional.ifPresent(
        book -> {
          book.setReady(event.getIsReady());
          bookRepository.save(book);
        });
  }
}
