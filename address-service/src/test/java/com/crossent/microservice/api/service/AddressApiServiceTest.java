package com.crossent.microservice.api.service;

import com.crossent.microservice.AddressServiceApplication;
import com.crossent.microservice.TestConfiguration;
import com.crossent.microservice.api.dto.AddressDetailResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddressServiceApplication.class)
public class AddressApiServiceTest {

    MockMvc mockMvc;

    @InjectMocks
    private AddressApiService addressApiService;

    @Mock
    private RequestService requestService;

    @Value("${address.api.key:}")
    private String confmKey;

    @Value("${address.api.uri:}")
    private String uri;

    @Before
    public void setup() throws IOException {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressApiService).build();

    }

    @Test
    public void listAddressByKeyword() throws Exception {
        when(requestService.getHttp(uri)).thenReturn(TestConfiguration.setRequestApiData());

        ReflectionTestUtils.setField(addressApiService, "uri", uri);
        ReflectionTestUtils.setField(addressApiService, "confmKey", confmKey);

        addressApiService.listAddressByKeyword(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE, TestConfiguration.KEYWORD2);

    }

    @Test
    public void listAddressByKeywordAndZipNoIsNull() throws Exception {
        ReflectionTestUtils.setField(addressApiService, "uri", uri);
        ReflectionTestUtils.setField(addressApiService, "confmKey", confmKey);

        when(requestService.getHttp(uri)).thenReturn(TestConfiguration.setRequestApiData());

        List<AddressDetailResponse> list = addressApiService.listAddressByKeywordAndZipNo(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE, TestConfiguration.KEYWORD2, TestConfiguration.ZIP_NO);
        assertEquals(list.size() , 0);

    }

    @Test
    public void listAddressByKeywordAndAdmCodeIsNull() throws Exception {
        ReflectionTestUtils.setField(addressApiService, "uri", uri);
        ReflectionTestUtils.setField(addressApiService, "confmKey", confmKey);

        when(requestService.getHttp(uri)).thenReturn(TestConfiguration.setRequestApiData());
//        when(addressApiService.requestAddressByKeyword(TestConfiguration.CURRENT_PAGE, TestConfiguration.KEYWORD2)).thenReturn(TestConfiguration.setListAddressByKeyword());

        List<AddressDetailResponse> list = addressApiService.listAddressByKeywordAndAdmCode(TestConfiguration.CURRENT_PAGE, TestConfiguration.COUNT_PER_PAGE, TestConfiguration.KEYWORD2, TestConfiguration.ADM_CD);
        assertEquals(list.size() , 0);
    }

}
