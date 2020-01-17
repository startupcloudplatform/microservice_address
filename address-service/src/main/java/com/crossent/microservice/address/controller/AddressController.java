package com.crossent.microservice.address.controller;

import com.crossent.microservice.address.domain.Address;
import com.crossent.microservice.address.service.AddressService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1/address")
@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "행정구역 전체 조회 : Page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "page 크기", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "page number", required = true, dataType = "int", paramType = "query"),
    })
    @RequestMapping(value = "/list/all", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Page<Address> listAddress(Pageable pageable) {
        return addressService.listAddress(pageable);
    }

    @ApiOperation(value = "행정구역 시/도 조회 : ALL")
    @RequestMapping(value = "/sido/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> listAllSido() {
        return addressService.listAllSido();
    }

    @ApiOperation(value = "행정구역 구/ 조회 : ALL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sido", value = "시/도", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/gugun/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> listAllGuGun(@RequestParam(value = "sido") String sido) {
        return addressService.listAllGuGunBySido(sido);
    }

    @ApiOperation(value = "행정구역 동 조회 : ALL")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sido", value = "시/도", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gugun", value = "구/군", required = true, dataType = "string", paramType = "query")
    })
    @RequestMapping(value = "/dong/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<String> listAllDong(@RequestParam(value = "sido") String sido,
                                    @RequestParam(value= "gugun") String gugun) {
        return addressService.listAllDongBySidoAndGuGun(sido, gugun);
    }

    @ApiOperation(value = "행정구역 조회 by 시/군/동")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sido",  value = "시/도", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "gugun", value = "구/군", required = true, dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "dong",  value = "동",   required = true, dataType = "string", paramType = "query")
    })
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Address getAddress(@RequestParam(value = "sido") String sido,
                              @RequestParam(value= "gugun") String gugun,
                              @RequestParam(value= "dong") String dong ) {
        return addressService.getAddress(sido, gugun, dong);
    }


}
