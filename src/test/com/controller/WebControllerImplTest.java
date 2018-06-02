package com.controller;

import com.converter.ConvertToMongoQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WebController controller;

    @MockBean
    private ConvertToMongoQuery convert;

    @Test
    public void testConvertEndpoint(){}
}