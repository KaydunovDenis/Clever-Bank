package com.github.kaydunov.percentage_processor;

import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import com.github.kaydunov.util.YamlConfigReader;
import com.github.kaydunov.service.AccountService;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

@Timeout(value = 5, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class PercentageProcessorSapientCodelessTest {

    private final AccountService fieldAccountServiceMock = mock(AccountService.class, "accountService");

    private AutoCloseable autoCloseableMocks;

    @InjectMocks()
    private PercentageProcessor target;

    @AfterEach()
    public void afterTest() throws Exception {
        if (autoCloseableMocks != null)
            autoCloseableMocks.close();
    }

    //Sapient generated method id: ${chargePercentageTest}, hash: 2542865517AE4F494FB32F2A109F1396
    @Test()
    void chargePercentageTest() {
        //Arrange Statement(s)
        try (MockedStatic<YamlConfigReader> yamlConfigReader = mockStatic(YamlConfigReader.class)) {
            doNothing().when(fieldAccountServiceMock).chargePercents(Double.parseDouble("0.0"));
            yamlConfigReader.when(() -> YamlConfigReader.getPercentageFromYaml()).thenReturn(Double.parseDouble("0.0"));
            target = new PercentageProcessor();
            autoCloseableMocks = MockitoAnnotations.openMocks(this);
            //Act Statement(s)
            target.chargePercentage();
            //Assert statement(s)
            assertAll("result", () -> {
                verify(fieldAccountServiceMock).chargePercents(Double.parseDouble("0.0"));
                yamlConfigReader.verify(() -> YamlConfigReader.getPercentageFromYaml(), atLeast(1));
            });
        }
    }
}
