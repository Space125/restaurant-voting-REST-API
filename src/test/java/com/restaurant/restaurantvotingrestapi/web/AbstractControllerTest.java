package com.restaurant.restaurantvotingrestapi.web;

import com.restaurant.restaurantvotingrestapi.TimingExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ivan Kurilov on 11.01.2021
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ExtendWith(TimingExtension.class)
public abstract class AbstractControllerTest {
    @Autowired
    private MockMvc mockMvc;

    protected ResultActions perform(MockHttpServletRequestBuilder builder) throws Exception {
        return mockMvc.perform(builder);
    }
}
