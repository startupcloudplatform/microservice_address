package com.crossent.microservice.address.controller;


import com.crossent.microservice.TestConfiguration;
import com.crossent.microservice.address.service.AddressService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AddressControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(AddressControllerTest.class);

    private MockMvc mockMvc;

    @InjectMocks
    private AddressController addressController;

    @Mock
    private AddressService addressService;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void listAddress() throws Exception {
        when(addressService.listAddress(Matchers.any(Pageable.class))).thenReturn(TestConfiguration.setAddressListPage());

        MvcResult result = this.mockMvc.perform(get("/v1/address/list/all")
                .param("size", "1")
                .param("page", "0")
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.content").isArray())
                .andReturn();

    }

    @Test
    public void listAllSido() throws Exception {
        when(addressService.listAllSido()).thenReturn(TestConfiguration.setSidoList());

        mockMvc.perform(get("/v1/address/sido/list")
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();;
    }

    @Test
    public void listAllGuGun() throws Exception {
        when(addressService.listAllGuGunBySido(TestConfiguration.SIDO1)).thenReturn(TestConfiguration.setGuGunList());

        mockMvc.perform(get("/v1/address/gugun/list?sido="+TestConfiguration.SIDO1)
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();;
    }

    @Test
    public void listAllDong() throws Exception {
        when(addressService.listAllGuGunBySido(TestConfiguration.SIDO1)).thenReturn(TestConfiguration.setGuGunList());

        mockMvc.perform(get("/v1/address/dong/list?sido="+TestConfiguration.SIDO2 +"&gugun="+TestConfiguration.GUGUN)
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andReturn();;
    }
}
