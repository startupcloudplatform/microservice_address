package com.crossent.microservice;

import com.crossent.microservice.address.domain.Address;
import com.crossent.microservice.api.dto.AddressCommonResponse;
import com.crossent.microservice.api.dto.AddressDetailResponse;
import com.crossent.microservice.api.dto.AddressResultResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class TestConfiguration {

    public static final String SIDO1 = "강원도";
    public static final String SIDO2 = "서울특별시";
    public static final String GUGUN = "서울";

    public static final Integer CURRENT_PAGE = 1;
    public static final Integer COUNT_PER_PAGE = 15;
    public static final String  KEYWORD = "운양동";
    public static final String  KEYWORD2 = "운양동 1250-3";
    public static final String  ZIP_NO  = "100";
    public static final String  ADM_CD  = "1000";



    public static Page<Address> setAddressListPage(){
        List<Address> list = new ArrayList<>();
        Address address = new Address("강원도", "강릉시", "강동면", "");
        list.add(address);

        Page<Address> page = new PageImpl<Address>(list,new PageRequest(0, 1),1L);
        return page;
    }

    public static List<Address> setAddressList(){
        List<Address> list = new ArrayList<>();
        Address address = new Address("강원도", "강릉시", "강동면", "");
        list.add(address);

        return list;
    }

    public static List<String> setSidoList(){
        List<String> list =  new ArrayList<>();
        list.add("강원도");
        list.add("경기도");
        list.add("서울특별시");

        return list;
    }

    public static List<String> setGuGunList(){
        List<String> list =  new ArrayList<>();
        list.add("강릉시");
        list.add("고성군");
        list.add("동해시");
        return list;
    }

    public static List<String> setDongList(){
        List<String> list =  new ArrayList<>();
        list.add("종로1");
        list.add("종로2가");
        list.add("종로3가");

        return list;
    }

    public static AddressResultResponse setListAddressByKeyword(){
        AddressResultResponse result = new AddressResultResponse();

        result.setCommon(new AddressCommonResponse("1", "정상", "0", "1", "30" ));
        result.setDetail(setListDetailData());

        return result;
    }

    public static List<AddressDetailResponse> setListDetailData(){
        List<AddressDetailResponse> detailList = new ArrayList<>();
        AddressDetailResponse detail = new AddressDetailResponse("",
                "경기도 김포시 금포로 1127 (운양동)",
                "경기도 김포시 운양동 5-6",
                "10092",
                "4157010300",
                "415703209136",
                "4157010300100020005000001",
                "",
                "0",
                "경기도",
                "김포시",
                "운양동",
                "금포로",
                "1127",
                "0",
                "5",
                "6",
                "0");

        detailList.add(detail);

        return detailList;
    }

    public static String setRequestApiData(){
        return "{\"results\":{" +
                "   \"common\":{\"errorMessage\":\"정상\",\"countPerPage\":\"10\",\"totalCount\":\"1\",\"errorCode\":\"0\",\"currentPage\":\"1\"}," +
                    "\"juso\":[" +
                        "{\"detBdNmList\":\"201동,202동,203동,204동,205동,206동,207동,208동,209동,210동,211동,212동,213동,214동,상가동\"," +
                        "\"engAddr\":\"287, Gimpohangang 11-ro, Gimpo-si, Gyeonggi-do\"," +
                        "\"rn\":\"김포한강11로\"," +
                        "\"emdNm\":\"운양동\"," +
                        "\"zipNo\":\"10073\"," +
                        "\"roadAddrPart2\":\" (운양동, 한강신도시 e편한세상)\"," +
                        "\"emdNo\":\"01\"," +
                        "\"sggNm\":\"김포시\"," +
                        "\"jibunAddr\":\"경기도 김포시 운양동 1250-3 한강신도시 e편한세상\"," +
                        "\"siNm\":\"경기도\"," +
                        "\"roadAddrPart1\":\"경기도 김포시 김포한강11로 287\"," +
                        "\"bdNm\":\"한강신도시 e편한세상\"," +
                        "\"admCd\":\"4157010300\"," +
                        "\"udrtYn\":\"0\"," +
                        "\"lnbrMnnm\":\"1250\"," +
                        "\"roadAddr\":\"경기도 김포시 김포한강11로 287 (운양동, 한강신도시 e편한세상)\"," +
                        "\"lnbrSlno\":\"3\"," +
                        "\"buldMnnm\":\"287\"," +
                        "\"bdKdcd\":\"1\"," +
                        "\"liNm\":\"\"," +
                        "\"rnMgtSn\":\"415703209174\"," +
                        "\"mtYn\":\"0\"," +
                        "\"bdMgtSn\":\"4157010300112500003000001\"," +
                        "\"buldSlno\":\"0\"" +
                "}" +
            "]}}";
    }
}
