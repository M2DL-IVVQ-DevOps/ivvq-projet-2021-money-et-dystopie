package org.ups.m2dl.moneyetdystopieback.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class BusinessMessageTest {

    BusinessMessage businessException;

    @Test
    void whenCreateBusinessException_thenCanGetMessage(){
        // GIVEN
        String messageTest = "Excep";

        // WHEN
        businessException = new BusinessMessage(new BusinessException(messageTest));

        // THEN
        Assertions.assertEquals(messageTest, businessException.getErrorMessage(), "This is not the same message");
    }
}