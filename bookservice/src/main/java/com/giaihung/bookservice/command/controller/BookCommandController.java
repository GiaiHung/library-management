package com.giaihung.bookservice.command.controller;

import com.giaihung.bookservice.command.command.CreateBookCommand;
import com.giaihung.bookservice.command.model.BookRequestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/books")
public class BookCommandController {
    private final CommandGateway commandGateway;

    public BookCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addBook(@RequestBody BookRequestModel bookRequestModel) {
        CreateBookCommand createBookCommand = new CreateBookCommand(
            UUID.randomUUID().toString(),
            bookRequestModel.getName(),
            bookRequestModel.getAuthor(),
            true
        );
        return commandGateway.sendAndWait(createBookCommand);
    }
}
