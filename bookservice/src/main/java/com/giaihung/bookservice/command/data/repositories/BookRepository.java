package com.giaihung.bookservice.command.data.repositories;

import com.giaihung.bookservice.command.data.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {}
