package com.giaihung.bookservice.query.controller;

import com.giaihung.bookservice.query.model.BookResponseModel;
import com.giaihung.bookservice.query.queries.GetAllBooksQuery;
import com.giaihung.bookservice.query.queries.GetSingleBookQuery;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/books")
public class BookQueryController {
  private final QueryGateway queryGateway;

  public BookQueryController(QueryGateway queryGateway) {
    this.queryGateway = queryGateway;
  }

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
}
