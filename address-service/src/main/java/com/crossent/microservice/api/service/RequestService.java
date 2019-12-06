package com.crossent.microservice.api.service;

import com.crossent.microservice.api.dto.AddressResponse;
import com.crossent.microservice.api.dto.AddressResultResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.io.StringReader;

public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Value("${address.api.key:}")
    private String confmKey;

    @Value("${address.api.uri:}")
    public String uri;

    private OkHttpClient client = null;

    private void setClient(){
        if(client == null){
            client = new OkHttpClient();
        }
    }

    public String getHttp(String url){
        setClient();

        Response response = null;
        String contents = "";
        try{
            Request request = new Request.Builder()
                    .url(url)
                    .header("Content-Type", "application/json")
                    .addHeader("ACCEPT", "application/json")
                    .build();
            response = client.newCall(request).execute();

            if( response.code() == HttpStatus.OK.value() && response.body() != null){
                contents = response.body().string();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(response != null){
                response.close();
            }
        }
        return contents;
    }

    /**
     * 키워드를 통한 주소 조회 API 요청
     * @param currentPage  현재 페이지
     * @param countPerPage 페이지당 게시물 수
     * @param keyword      주소 키워드
     */
    protected AddressResultResponse requestAddressByKeyword(int currentPage, int countPerPage, String keyword){

        AddressResultResponse response = null;

        AddressResponse<AddressResultResponse> contents;
        try{
            HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder()
                    .addQueryParameter("confmKey", confmKey)
                    .addQueryParameter("currentPage", String.valueOf(currentPage))
                    .addQueryParameter("countPerPage", String.valueOf(countPerPage))
                    .addQueryParameter("resultType", "json")
                    .addQueryParameter("keyword", keyword);

            String url = urlBuilder.build().toString();
            String result  = getHttp(url);
            logger.debug(result);

            if(result != null ){
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                StringReader reader = new StringReader(result);
                contents  = mapper.readValue(reader, new TypeReference<AddressResponse<AddressResultResponse>>() {});
                response = contents.getResults();

            }else{
                response = new AddressResultResponse();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
