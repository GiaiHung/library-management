package com.giaihung.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookCreatedEvent {
    private String id;
    private String name;
    private String author;
    private boolean isReady;
}
