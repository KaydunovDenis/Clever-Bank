package com.github.kaydunov.percentage_processor;

import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Test;
import com.github.kaydunov.exception.DatabaseConfigurationException;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class PercentageProcessorSapientGeneratedTest {

    //Sapient generated method id: ${getPercentageFromYamlTest}, hash: 64C3D46780FAC0460DE3119C7F004F3F
    @Test()
    void getPercentageFromYamlTest() {
        /*
         * TODO: Help needed! This method is not unit testable!
         *  A variable could not be isolated/mocked when calling a method - Variable name: yaml - Method: load
         *  Suggestions:
         *  You can pass them as constructor arguments or create a setter for them (avoid new operator)
         *  or adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
         
        //Act Statement(s)
        double result = PercentageProcessor.getPercentageFromYaml();
        
        //Assert statement(s)
        assertAll("result", () -> assertTrue(result > 0 ));
    }

    //Sapient generated method id: ${getPercentageFromYamlWhenCaughtIOExceptionThrowsDatabaseConfigurationException}, hash: A9D52B6338CEB3AB84EBD5C4A5E4ECF7
    @Test()
    void getPercentageFromYamlWhenCaughtIOExceptionThrowsDatabaseConfigurationException() {
        /* Branches:
         * (catch-exception (IOException)) : true
         *
         * TODO: Help needed! This method is not unit testable!
         *  A variable could not be isolated/mocked when calling a method - Variable name: yaml - Method: load
         *  Suggestions:
         *  You can pass them as constructor arguments or create a setter for them (avoid new operator)
         *  or adjust the input/test parameter values manually to satisfy the requirements of the given test scenario.
         *  The test code, including the assertion statements, has been successfully generated.
         */
         //Arrange Statement(s)
        IOException iOException = new IOException();
        DatabaseConfigurationException databaseConfigurationException = new DatabaseConfigurationException("Incorrect config.yaml", iOException);
        //Act Statement(s)
        final DatabaseConfigurationException result = assertThrows(DatabaseConfigurationException.class, () -> {
            PercentageProcessor.getPercentageFromYaml();
        });
        
        //Assert statement(s)
        assertAll("result", () -> {
            assertThat(result, is(notNullValue()));
            assertThat(result.getMessage(), equalTo(databaseConfigurationException.getMessage()));
            assertThat(result.getCause(), is(instanceOf(iOException.getClass())));
        });
    }
}
