package com.github.kaydunov.service;

import com.github.kaydunov.dao.crud.BankDao;
import com.github.kaydunov.entity.Bank;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class BankServiceSapientGeneratedTest {

    @Mock
    private BankDao bankDaoMock;
    @Mock
    private Bank bankMock;
    @InjectMocks()
    private BankService target;
    private AutoCloseable autoCloseableMocks;

    @BeforeEach
    public void beforeTest() {
        autoCloseableMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${findByIdTest}, hash: 5472E9FA51FBBB60C83CD43CBFFE7CE4
    @Test()
    void findByIdTest() {
        //Arrange Statement(s)
        doReturn(Optional.empty()).when(bankDaoMock).findById(0L);

        //Act Statement(s)
        Bank result = target.findById(0L);
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(nullValue()));
            verify(bankDaoMock).findById(0L);
        });
    }

    //Sapient generated method id: ${createTest}, hash: 8543557BF343883943A3E29F0A7E1D04
    @Test()
    void createTest() {
        //Arrange Statement(s)
        Bank bankMock2 = mock(Bank.class);
        doReturn(bankMock).when(bankDaoMock).create(bankMock2);

        //Act Statement(s)
        Bank result = target.create(bankMock2);
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(bankMock));
            verify(bankDaoMock).create(bankMock2);
        });
    }

    //Sapient generated method id: ${updateTest}, hash: 08BBAF32F775773F36AF7861C37511F5
    @Test()
    void updateTest() {
        //Arrange Statement(s)
        doNothing().when(bankDaoMock).update(bankMock);

        //Act Statement(s)
        Bank result = target.update(bankMock);
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(bankMock));
            verify(bankDaoMock).update(bankMock);
        });
    }

    //Sapient generated method id: ${deleteTest}, hash: 59CCCAF3E0ABDF21C388AC23D031EBCC
    @Test()
    void deleteTest() {
        //Arrange Statement(s)
        doNothing().when(bankDaoMock).deleteById(0L);

        //Act Statement(s)
        target.delete(0L);
        
        //Assert statement(s)
        assertAll("result", () -> verify(bankDaoMock).deleteById(0L));
    }

    //Sapient generated method id: ${findAllTest}, hash: E2E5EB6CFF07A280DCC220D70BC1A219
    @Test()
    void findAllTest() {
        //Arrange Statement(s)
        List<Bank> bankList = new ArrayList<>();
        doReturn(bankList).when(bankDaoMock).findAll();

        //Act Statement(s)
        List<Bank> result = target.findAll();
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, equalTo(bankList));
            verify(bankDaoMock).findAll();
        });
    }

    @Test
    void getByAccountIdTest() {
        //Arrange Statement(s)
        when(bankDaoMock.getByAccountId("accountId")).thenReturn(Optional.of(bankMock));

        //Act Statement(s)
        Optional<Bank> result = target.getByAccountId("accountId");

        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result.get(), equalTo(bankMock));
            verify(bankDaoMock).getByAccountId("accountId");
        });
    }
}
