package com.giaihung.borrowingservice.command.event;

import com.giaihung.borrowingservice.command.data.Borrowing;
import com.giaihung.borrowingservice.command.data.BorrowingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BorrowingEventHandler {
  private final BorrowingRepository borrowingRepository;

  @EventHandler
  public void on(CreateBorrowingEvent event) {
    Borrowing borrowing = new Borrowing();
    borrowing.setId(event.getId());
    borrowing.setBookId(event.getBookId());
    borrowing.setEmployeeId(event.getEmployeeId());
    borrowing.setBorrowingDate(event.getBorrowingDate());
    borrowingRepository.save(borrowing);
  }

  @EventHandler
  public void on(DeleteBorrowingEvent event) {
    Optional<Borrowing> borrowingOptional = borrowingRepository.findById(event.getId());
    borrowingOptional.ifPresent(
        borrowing -> {
          this.borrowingRepository.deleteById(event.getId());
        });
  }
}
