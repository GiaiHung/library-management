package com.giaihung.bookservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookStatusRollbackEvent {
    private String bookId;

    private Boolean isReady;

    private String employeeId;

    private String borrowingId;
}
