package com.crossent.msxpert.address.service;

import com.crossent.msxpert.address.dto.AddressDTO;
import com.crossent.msxpert.address.dto.AddressResultResponse;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
@RefreshScope
public class AddressService extends FallbackMethodService {

    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Value("${gateway.basic.user:}")
    String user;

    @Value("${gateway.basic.password:}")
    String password;


    private HttpHeaders getHeaders(){
        String basicAuth = String.format("%s:%s", user, password);
        String base64Auth = Base64Utils.encodeToString(basicAuth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Basic %s", base64Auth));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private URI setURI(String uri, MultiValueMap<String, String> maps) {
        return UriComponentsBuilder.fromHttpUrl(uri)
                                    .queryParams(maps)
                                    .build()
                                    .encode()
                                    .toUri();
    }

    /**
     * 주소 키워드를 통해 주소 목록 조회
     * @param page     현재 페이지
     * @param pageSize 페이지 당 게시글 수
     * @param keyword  주소 키워드
     */
    @HystrixCommand(commandKey = "exception1", fallbackMethod = "notFound", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000"),
            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "20000")
    })
    public ResponseEntity<AddressResultResponse>  listByKeyword(int page, int pageSize, String keyword) {
        ResponseEntity<AddressResultResponse> result = null;
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("currentPage", String.valueOf(page));
        maps.add("countPerPage", String.valueOf(pageSize));
        maps.add("keyword", keyword);

        try{

            URI uri = setURI("http://apigateway/address-service/api/address/list", maps);
            HttpEntity<?> request = new HttpEntity<>(getHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, request, AddressResultResponse.class);

            logger.debug("Result ===================================== >");
            logger.debug(result.getBody().getCommon().toString());

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * (행정구역) 시/도 전체 목록 조회
     */
    @HystrixCommand(commandKey = "exception2", fallbackMethod = "v1MotFound")
    public Object  listAllSido() {
        Object result = null;
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        try{

            URI uri = setURI("http://apigateway/address-service/v1/address/sido/list", maps);
            HttpEntity<?> request = new HttpEntity<>(getHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * (행정구역) 구/군 목록 전체 조회 by sido
     * @param sido 시/도
     */
    @HystrixCommand(commandKey = "exception2")
    public List<String>  listAllGuGunBySido(String sido) {
        ResponseEntity<Object> result = null;
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("sido", sido);

        try{

            URI uri = setURI("http://apigateway/address-service/v1/address/gugun/list", maps);
            HttpEntity<?> request = new HttpEntity<>(getHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);
            if( result == null ){
                return new ArrayList<>();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return (ArrayList)result.getBody();

    }

    /**
     * (행정구역) 동 전체 조회 by sido, gugun
     * @param sido  시/도
     * @param gugun 구/군
     */
    @HystrixCommand(commandKey = "exception2")
    public List<String> listAllDongBySidoAndGuGun(String sido, String gugun) {
        ResponseEntity<Object> result = null;
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("sido",  sido);
        maps.add("gugun", gugun);

        try{

            URI uri = setURI("http://apigateway/address-service/v1/address/dong/list", maps);
            HttpEntity<?> request = new HttpEntity<>(getHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, request, Object.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return (ArrayList)result.getBody();
    }

    /**
     * (행정구역) 주소 조회 by sido, gugun, dong
     * @param sido  시/도
     * @param gugun 구/군
     * @param dong  동
     */
    @HystrixCommand(commandKey = "exception2")
    public AddressDTO getAddress(String sido, String gugun, String dong) {
        ResponseEntity<AddressDTO> result = null;
        MultiValueMap<String, String> maps = new LinkedMultiValueMap<>();
        maps.add("sido",  sido);
        maps.add("gugun", gugun);
        maps.add("dong",  dong);

        try{

            URI uri = setURI("http://apigateway/address-service/v1/address", maps);
            HttpEntity<?> request = new HttpEntity<>(getHeaders());
            result = restTemplate.exchange(uri, HttpMethod.GET, request, AddressDTO.class);

        }catch (Exception e){
            e.printStackTrace();
        }

        return result.getBody();
    }


}
