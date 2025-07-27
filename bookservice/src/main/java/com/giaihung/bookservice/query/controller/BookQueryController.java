package com.giaihung.bookservice.query.controller;

import com.giaihung.bookservice.query.queries.GetAllBooksQuery;
import com.giaihung.commonservice.model.BookResponseModel;
import com.giaihung.commonservice.query.queries.GetSingleBookQuery;
import com.giaihung.commonservice.service.KafkaService;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookQueryController {
  private final QueryGateway queryGateway;
  private final KafkaService kafkaService;

  @GetMapping
  public List<BookResponseModel> getAllBooks() {
    GetAllBooksQuery query = new GetAllBooksQuery();
    CompletableFuture<List<BookResponseModel>> bookFuture =
        queryGateway.query(query, ResponseTypes.multipleInstancesOf(BookResponseModel.class));
    //        bookFuture.thenAccept(books -> {
    //            System.out.println("Books: " + books);
    //        });
    return bookFuture.join();
  }

  @GetMapping("/{bookId}")
  public BookResponseModel getBook(@PathVariable String bookId) {
    GetSingleBookQuery query = new GetSingleBookQuery(bookId);
    CompletableFuture<BookResponseModel> result =
        queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class));
    return result.join();
  }

  @PostMapping("/sendMessage")
  public void sendMessage(@RequestBody String message) {
    kafkaService.sendMessage("test", message);
  }

  @PostMapping("/sendEmail")
  public void sendEmail(@RequestBody String email) {
    kafkaService.sendMessage("send_email_with_template", email);
  }
}
