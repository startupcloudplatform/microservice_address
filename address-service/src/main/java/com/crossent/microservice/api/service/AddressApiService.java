package com.crossent.microservice.api.service;

import com.crossent.microservice.api.dto.AddressDetailResponse;
import com.crossent.microservice.api.dto.AddressResponse;
import com.crossent.microservice.api.dto.AddressResultResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
@RefreshScope
public class AddressApiService {

    private static final Logger logger = LoggerFactory.getLogger(AddressApiService.class);

    @Value("${address.api.key:}")
    private String confmKey;

    @Value("${address.api.uri:}")
    public String uri;


    @Autowired
    private RequestService requestService;


    public AddressResultResponse listAddressByKeyword(int currentPage, int countPerPage, String keyword){
        return requestAddressByKeyword(currentPage,countPerPage,  keyword);
    }

    public List<AddressDetailResponse> listAddressByKeywordAndZipNo(int currentPage,  int countPerPage, String keyword, String zipNo){
        List<AddressDetailResponse> result = new ArrayList<>();

        AddressResultResponse list = requestAddressByKeyword(currentPage, countPerPage, keyword);
        if(list != null) {
            if(list.getDetail().size() > 0 ){
                for( int i=0; i<list.getDetail().size(); i++ ){

                    if( list.getDetail().get(i).getZipNo().contains(zipNo) ){
                        result.add(list.getDetail().get(i));
                    }
                }

                float totalPage = (Float.parseFloat(list.getCommon().getTotalCount()) / countPerPage ) - currentPage;

                if( totalPage >= 0 ){
                    listAddressByKeywordAndZipNo(currentPage+1 , countPerPage, keyword, zipNo);
                }
            }
        }


        return result;
    }

    /**
     * 행정구역코드 및 키워드를 통한 주소 목록 조회
     * @param currentPage 현재 page
     * @param countPerPage page size
     * @param keyword 검색 keyword
     * @param admCode 행정구역코드
     * @return List<AddressDetailResponse>
     */
    public List<AddressDetailResponse> listAddressByKeywordAndAdmCode(int currentPage, int countPerPage, String keyword, String admCode){
        List<AddressDetailResponse> result = new ArrayList<>();

        AddressResultResponse list = requestAddressByKeyword(currentPage, countPerPage, keyword);
        if(list != null) {
            if(list.getDetail().size() > 0 ){
                for( int i=0; i<list.getDetail().size(); i++ ){
                    if( list.getDetail().get(i).getAdmCd().contains(admCode) ){
                        result.add(list.getDetail().get(i));
                    }
                }

                float totalPage = (Float.parseFloat(list.getCommon().getTotalCount()) / countPerPage ) - currentPage;
//            logger.debug("page :: " + currentPage);

                if( totalPage >= 0 ){
                    currentPage ++;
                    listAddressByKeywordAndAdmCode(currentPage , countPerPage, keyword, admCode);
                }
            }
        }

        return result;
    }


    private AddressResultResponse requestAddressByKeyword(int currentPage, int countPerPage, String keyword){
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
            String result  = requestService.getHttp(url);

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
