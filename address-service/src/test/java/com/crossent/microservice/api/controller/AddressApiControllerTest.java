package com.crossent.microservice.api.controller;

import com.crossent.microservice.TestConfiguration;
import com.crossent.microservice.address.controller.AddressControllerTest;
import com.crossent.microservice.api.service.AddressApiService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressApiControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    private MockMvc mockMvc;

    @InjectMocks
    private AddressApiController addressApiController;

    @Mock
    private AddressApiService addressApiService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressApiController).build();
    }

    @Test
    public void listAddressByKeyword() throws Exception {
        when(addressApiService.listAddressByKeyword(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE, TestConfiguration.KEYWORD)).thenReturn(TestConfiguration.setListAddressByKeyword());

        MvcResult result = this.mockMvc.perform(get("/api/address/list")
                .param("currentPage", TestConfiguration.CURRENT_PAGE+"")
                .param("keyword", TestConfiguration.KEYWORD)
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.juso").isArray())
                .andExpect(jsonPath("$.common.totalCount", is("1")))
                .andReturn();
    }

    @Test
    public void listAllAddressByZipNo() throws Exception {
        when(addressApiService.listAddressByKeywordAndZipNo(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE,  TestConfiguration.KEYWORD, TestConfiguration.ZIP_NO)).thenReturn(TestConfiguration.setListDetailData());

        MvcResult result = this.mockMvc.perform(get("/api/address/zipNo/list")
                .param("currentPage", TestConfiguration.CURRENT_PAGE+"")
                .param("keyword", TestConfiguration.KEYWORD)
                .param("zipNo", TestConfiguration.ZIP_NO)
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Test
    public void listAllAddressByAdmCode() throws Exception {
        when(addressApiService.listAddressByKeywordAndAdmCode(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE, TestConfiguration.KEYWORD, TestConfiguration.ADM_CD)).thenReturn(TestConfiguration.setListDetailData());

        MvcResult result = this.mockMvc.perform(get("/api/address/admCode/list")
                .param("currentPage", TestConfiguration.CURRENT_PAGE+"")
                .param("keyword", TestConfiguration.KEYWORD)
                .param("admCode", TestConfiguration.ADM_CD)
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

}
