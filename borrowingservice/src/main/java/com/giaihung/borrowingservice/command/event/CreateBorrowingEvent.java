package com.giaihung.borrowingservice.command.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateBorrowingEvent {
    private String id;

    private String bookId;

    private String employeeId;

    private Date borrowingDate;
}
