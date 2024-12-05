package com.github.kaydunov.service;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javassist.NotFoundException;
import java.util.List;
import org.mockito.MockitoAnnotations;
import com.github.kaydunov.entity.Transaction;
import com.github.kaydunov.dao.crud.TransactionDao;
import java.util.Optional;
import java.util.ArrayList;
import java.sql.Timestamp;
import static org.mockito.Mockito.doNothing;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.Matchers.is;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class TransactionServiceBaseRockGeneratedTest {

    private final TransactionDao transactionDaoMock = mock(TransactionDao.class, "transactionDao");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private TransactionService target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${saveTest}, hash: 9AF227589E0684E9E1178D0AF4F30162
    @Test()
    void saveTest() {
        //Arrange Statement(s)
        Transaction transactionMock = mock(Transaction.class);
        Transaction transactionMock2 = mock(Transaction.class);
        doReturn(transactionMock).when(transactionDaoMock).create(transactionMock2);
        target = new TransactionService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        Transaction result = target.save(transactionMock2);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(transactionMock));
            verify(transactionDaoMock).create(transactionMock2);
        });
    }

    //Sapient generated method id: ${findByIdThrowsNotFoundException}, hash: 6653AF0FE050B6A7F0D7C1CC39E91974
    @Test()
    void findByIdThrowsNotFoundException() throws NotFoundException {
        //Arrange Statement(s)
        doReturn(Optional.empty()).when(transactionDaoMock).findById(0L);
        target = new TransactionService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        final NotFoundException result = assertThrows(NotFoundException.class, () -> {
            target.findById(0L);
        });
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            verify(transactionDaoMock).findById(0L);
        });
    }

    //Sapient generated method id: ${findAllTest}, hash: FA45ECEFEA6D125213854B6EEAB30AB7
    @Test()
    void findAllTest() {
        //Arrange Statement(s)
        List<Transaction> transactionList = new ArrayList<>();
        doReturn(transactionList).when(transactionDaoMock).findAll();
        target = new TransactionService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        List<Transaction> result = target.findAll();
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(transactionList));
            verify(transactionDaoMock).findAll();
        });
    }

    //Sapient generated method id: ${deleteTest}, hash: CA13C3438A8705D386DB098C95C9B7D9
    @Test()
    void deleteTest() {
        //Arrange Statement(s)
        doNothing().when(transactionDaoMock).deleteById(0L);
        target = new TransactionService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        target.delete(0L);
        //Assert statement(s)
        assertAll("result", () -> verify(transactionDaoMock).deleteById(0L));
    }

    //Sapient generated method id: ${findByAccountIdAndDateRangeTest}, hash: 6E636C638636F4DFA607C4E1C488EFBD
    @Test()
    void findByAccountIdAndDateRangeTest() {
        //Arrange Statement(s)
        List<Transaction> transactionList = new ArrayList<>();
        Timestamp timestamp = new Timestamp(0L);
        Timestamp timestamp2 = new Timestamp(0L);
        doReturn(transactionList).when(transactionDaoMock).findByAccountIdAndDateRange("accountId1", timestamp, timestamp2);
        target = new TransactionService();
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
        //Act Statement(s)
        List<Transaction> result = target.findByAccountIdAndDateRange("accountId1", timestamp, timestamp2);
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(transactionList));
            verify(transactionDaoMock).findByAccountIdAndDateRange("accountId1", timestamp, timestamp2);
        });
    }
}
