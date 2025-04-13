package com.giaihung.bookservice.command.controller;

import com.giaihung.bookservice.command.command.CreateBookCommand;
import com.giaihung.bookservice.command.command.DeleteBookCommand;
import com.giaihung.bookservice.command.command.UpdateBookCommand;
import com.giaihung.bookservice.command.model.BookRequestModel;
import jakarta.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    private final CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBook(@Valid @RequestBody BookRequestModel bookRequestModel) {
        CreateBookCommand createBookCommand = new CreateBookCommand(
                UUID.randomUUID().toString(),
                bookRequestModel.getName(),
                bookRequestModel.getAuthor(),
                true
        );
        return commandGateway.sendAndWait(createBookCommand);
    }

    @PutMapping("/{bookId}")
    public String updateBook(@RequestBody BookRequestModel bookRequestModel, @PathVariable("bookId") String bookId) {
        UpdateBookCommand command = new UpdateBookCommand(
                bookId,
                bookRequestModel.getName(),
                bookRequestModel.getAuthor(),
                bookRequestModel.isReady()
        );
        return commandGateway.sendAndWait(command);
    }

    @DeleteMapping("/{bookId}")
    public String deleteBook(@PathVariable("bookId") String bookId) {
        DeleteBookCommand command = new DeleteBookCommand(bookId);
        return commandGateway.sendAndWait(command);
    }
}
