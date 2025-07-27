package com.giaihung.borrowingservice.command.controller;

import com.giaihung.borrowingservice.command.command.CreateBorrowingCommand;
import com.giaihung.borrowingservice.command.model.CreateBorrowingPayload;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrowings")
@RequiredArgsConstructor
public class BorrowingController {
  private final CommandGateway commandGateway;

  @PostMapping
  public String createBookBorrowing(@RequestBody CreateBorrowingPayload payload) {
    CreateBorrowingCommand command = new CreateBorrowingCommand();
    command.setId(UUID.randomUUID().toString());
    command.setBookId(payload.getBookId());
    command.setEmployeeId(payload.getEmployeeId());
    command.setBorrowingDate(new Date());
    return commandGateway.sendAndWait(command);
  }
}
