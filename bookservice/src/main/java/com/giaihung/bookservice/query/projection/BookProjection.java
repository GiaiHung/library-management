package com.giaihung.bookservice.query.projection;

import com.giaihung.bookservice.command.data.Book;
import com.giaihung.bookservice.command.data.repositories.BookRepository;
import com.giaihung.bookservice.query.model.BookResponseModel;
import com.giaihung.bookservice.query.queries.GetAllBooksQuery;
import com.giaihung.bookservice.query.queries.GetSingleBookQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookProjection {
    private final BookRepository bookRepository;

    public BookProjection(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @QueryHandler
    public List<BookResponseModel> handleQueryAllBooks(GetAllBooksQuery query) {
        List<Book> books = bookRepository.findAll();
//        List<BookResponseModel> bookResponseModels = new ArrayList<>();
//        for (Book book : books) {
//            BookResponseModel bookResponseModel = new BookResponseModel();
//            BeanUtils.copyProperties(book, bookResponseModel);
//            bookResponseModels.add(bookResponseModel);
//        }
        return books.stream().map(book -> {
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book, bookResponseModel);
            return bookResponseModel;
        }).toList();
    }

    @QueryHandler
    public BookResponseModel handleQuerySingleBook(GetSingleBookQuery query) throws Exception {
        Optional<Book> book = bookRepository.findById(query.getBookId());
        if(book.isPresent()) {
            BookResponseModel bookResponseModel = new BookResponseModel();
            BeanUtils.copyProperties(book.get(), bookResponseModel);
            return bookResponseModel;
        } else {
            throw new Exception("Book not found with ID: " + query.getBookId());
        }
    }
}
