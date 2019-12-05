package com.crossent.microservice.api.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    OkHttpClient client = new OkHttpClient();

    public String getHttp(String url){
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
}
