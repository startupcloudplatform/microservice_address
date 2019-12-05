package com.crossent.microservice.address.service;

import com.crossent.microservice.AddressServiceApplication;
import com.crossent.microservice.TestConfiguration;
import com.crossent.microservice.address.domain.Address;
import com.crossent.microservice.address.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressServiceApplication.class)
public class AddressServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressService)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void listAddress() throws Exception {
        when(addressRepository.findAll(Matchers.any(Pageable.class))).thenReturn(TestConfiguration.setAddressListPage());
        addressService.listAddress(Matchers.any(Pageable.class));
    }

    @Test
    public void listAllSido() throws Exception {
        when(addressRepository.findAllSidoGroupBy()).thenReturn(TestConfiguration.setSidoList());
        List<String> list = addressService.listAllSido();
        assertEquals(list.get(0), "강원도");
    }

    @Test
    public void listAllGuGunBySido() throws Exception {
        when(addressRepository.findAllGuGunGroupBy(TestConfiguration.SIDO1)).thenReturn(TestConfiguration.setGuGunList());
        List<String> list = addressService.listAllGuGunBySido(TestConfiguration.SIDO1);
        assertEquals(list.get(0), "강릉시");
    }

    @Test
    public void listAllDongBySidoAndGuGun() throws Exception {
        when(addressRepository.findAllDongGroupBy(TestConfiguration.SIDO2, TestConfiguration.GUGUN)).thenReturn(TestConfiguration.setDongList());
        List<String> list = addressService.listAllDongBySidoAndGuGun(TestConfiguration.SIDO2, TestConfiguration.GUGUN);
        assertEquals(list.get(0), "종로1");
    }

}
