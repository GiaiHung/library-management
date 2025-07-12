package com.giaihung.borrowingservice.command.saga;

import com.giaihung.commonservice.command.command.RollbackBookStatusCommand;
import com.giaihung.commonservice.command.command.UpdateBookStatusCommand;
import com.giaihung.commonservice.command.event.BookStatusRollbackEvent;
import com.giaihung.commonservice.command.event.BookStatusUpdatedEvent;
import com.giaihung.commonservice.model.BookResponseModel;
import com.giaihung.commonservice.query.queries.GetSingleBookQuery;
import com.giaihung.commonservice.command.command.DeleteBorrowingCommand;
import com.giaihung.borrowingservice.command.event.CreateBorrowingEvent;
import com.giaihung.borrowingservice.command.event.DeleteBorrowingEvent;
import com.giaihung.commonservice.query.model.EmployeeDTO;
import com.giaihung.commonservice.query.queries.GetDetailEmployeeQuery;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@NoArgsConstructor
@Slf4j
public class BorrowingSaga {
    @Setter
    private transient CommandGateway commandGateway;

    @Setter
    private transient QueryGateway queryGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Autowired
    public void setQueryGateway(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    private void handle(CreateBorrowingEvent event) {
        log.info("CreateBorrowingEvent saga started from employee id: {} - for book id: {}", event.getEmployeeId(), event.getId());

        try {
            GetSingleBookQuery query = new GetSingleBookQuery(event.getBookId());
            BookResponseModel response = queryGateway.query(query, ResponseTypes.instanceOf(BookResponseModel.class)).join();
            if(!response.isReady()) {
                throw new Exception("Book already borrowed");
            }

            SagaLifecycle.associateWith("bookId", event.getBookId());
            UpdateBookStatusCommand command = new UpdateBookStatusCommand(event.getBookId(), false, event.getEmployeeId(), event.getId());
            commandGateway.sendAndWait(command);
        } catch (Exception exception) {
            rollbackBorrowingRecord(event.getId());
            log.error(exception.getMessage(), exception);
        }
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookStatusUpdatedEvent event) {
        log.info("BookStatusUpdatedEvent in Saga for bookId: {}", event.getBookId());
        try {
            GetDetailEmployeeQuery query = new GetDetailEmployeeQuery(event.getEmployeeId());
            EmployeeDTO employee = queryGateway.query(query, ResponseTypes.instanceOf(EmployeeDTO.class)).join();
            if(employee.getIsDiscipline()) {
                throw new Exception("Employee is disciplined. Can't allow borrowing books");
            }
            log.info("Borrowing book successfully");
            SagaLifecycle.end();
        } catch(Exception exception) {
            rollbackUpdateBookStatus(event.getBookId(), event.getEmployeeId(), event.getBorrowingId());
            log.error(exception.getMessage(), exception);
        }
    }

    private void rollbackBorrowingRecord(String id) {
        DeleteBorrowingCommand command = new DeleteBorrowingCommand(id);
        commandGateway.send(command);
    }

    private void rollbackUpdateBookStatus(String bookId, String employeeId, String borrowingId) {
        SagaLifecycle.associateWith("bookId", bookId);
        RollbackBookStatusCommand command = new RollbackBookStatusCommand(bookId, true, employeeId, borrowingId);
        commandGateway.send(command);
    }

    @SagaEventHandler(associationProperty = "bookId")
    private void handle(BookStatusRollbackEvent event){
        log.info("BookRollBackStatusEvent in Saga for book Id : {} ", event.getBookId());
        rollbackBorrowingRecord(event.getBorrowingId());
    }

    @SagaEventHandler(associationProperty = "id")
    @EndSaga
    private void handle(DeleteBorrowingEvent event){
        log.info("BorrowDeletedEvent in Saga for Borrowing Id : {} ", event.getId());
    }
}
