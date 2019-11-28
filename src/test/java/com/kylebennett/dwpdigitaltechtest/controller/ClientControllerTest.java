
package com.kylebennett.dwpdigitaltechtest.controller;

import com.kylebennett.dwpdigitaltechtest.service.UserClientImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;

class ClientControllerTest {

    @Mock
    UserClientImpl mockUserClient;

    private Model model = new ConcurrentModel();

    @InjectMocks
    ClientController controller;

    @BeforeEach
    void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void testMainPage() throws Exception {

        String retVal = controller.mainPage(model);
        assertThat(retVal).isEqualTo("main");
    }
}
