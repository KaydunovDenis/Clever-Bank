package com.github.kaydunov.service;

import com.github.kaydunov.dto.Statement;
import com.github.kaydunov.entity.StatementTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

class StatementServiceTest {

    private final StatementService target = new StatementService();

    @Test
    void process() {
        //Arrange Statement(s)
        Statement statement = StatementTest.getStatement();
        String filename = "statement/statement_" + statement.getNumber() + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
        //Act Statement(s)
        target.process(statement);
        assertTrue(file.exists());
    }


}