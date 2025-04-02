package com.giaihung.bookservice.command.event;

import com.giaihung.bookservice.command.data.Book;
import com.giaihung.bookservice.command.data.repositories.BookRepository;
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
    public void on(BookCreatedEvent event) {
        Book book = new Book();
        BeanUtils.copyProperties(event, book);
        bookRepository.save(book);
    }
}
