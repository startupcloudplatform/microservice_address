package com.crossent.microservice.api.controller;

import com.crossent.microservice.api.dto.AddressDetailResponse;
import com.crossent.microservice.api.dto.AddressResultResponse;
import com.crossent.microservice.api.service.AddressApiService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressApiController {

    @Autowired
    private AddressApiService addressApiService;

    @ApiOperation(value = "키워드를 통한 주소 목록 조회 API : PAGE ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",  value = "page number", required = true, dataType = "int",    paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "countPerPage", value = "page size",   required = true, dataType = "int",    paramType = "query", defaultValue = "15"),
            @ApiImplicitParam(name = "keyword",      value = "키워드",        required = true, dataType = "string", paramType = "query", defaultValue = "")
    })
    @RequestMapping(value="/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AddressResultResponse listAddressByKeyword(@RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
                                                      @RequestParam(value= "countPerPage", defaultValue = "15", required = false) int countPerPage,
                                                      @RequestParam String keyword ){
        return addressApiService.listAddressByKeyword(currentPage, countPerPage, keyword);

    }

    @ApiOperation(value = "키워드 및 우편번호를 통한 목록 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "page number", required = true, dataType = "int",    paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "countPerPage", value = "page size",   required = true, dataType = "int",    paramType = "query", defaultValue = "15"),
            @ApiImplicitParam(name = "keyword",     value = "키워드",        required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "zipNo",       value = "우편번호",       required = true, dataType = "long",   paramType = "query", defaultValue = "")
    })
    @RequestMapping(value="/zipNo/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDetailResponse> listAllAddressByZipNo(@RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
                                                             @RequestParam(value= "countPerPage", defaultValue = "15", required = false) int countPerPage,
                                                             @RequestParam String zipNo,
                                                             @RequestParam String keyword){
        return addressApiService.listAddressByKeywordAndZipNo(currentPage, countPerPage, keyword, zipNo);
    }

    @ApiOperation(value = "키워드 및 행정구역 코드를 통한 목록 조회 API : ALL ")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "page number", required = true, dataType = "int",    paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "countPerPage", value = "page size",   required = true, dataType = "int",    paramType = "query", defaultValue = "15"),
            @ApiImplicitParam(name = "keyword",     value = "키워드",        required = true, dataType = "string", paramType = "query", defaultValue = ""),
            @ApiImplicitParam(name = "admCode",     value = "행정구역코드",    required = true, dataType = "long",   paramType = "query", defaultValue = "")
    })
    @RequestMapping(value="/admCode/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AddressDetailResponse> listAllAddressByAdmCode(@RequestParam(value = "currentPage", defaultValue = "1", required = false) int currentPage,
                                                               @RequestParam(value= "countPerPage", defaultValue = "15", required = false) int countPerPage,
                                                               @RequestParam String admCode,
                                                               @RequestParam String keyword){

        return addressApiService.listAddressByKeywordAndAdmCode(currentPage, countPerPage, keyword, admCode);

    }


}
