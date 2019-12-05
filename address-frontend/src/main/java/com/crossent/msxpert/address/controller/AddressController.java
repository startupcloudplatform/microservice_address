package com.crossent.msxpert.address.controller;

import com.crossent.msxpert.address.dto.AddressDTO;
import com.crossent.msxpert.address.dto.AddressResultResponse;
import com.crossent.msxpert.address.service.AddressService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/v1/address")
@RestController
public class AddressController {

	@Autowired
	private AddressService addressService;

	@ApiOperation(value = "키워드 별 주소 목록 조회 Page")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "page number", required = true, dataType = "int", paramType = "query", defaultValue = ""),
			@ApiImplicitParam(name = "pageSize", value = "page 크기", required = true, dataType = "int", paramType = "query", defaultValue = ""),
			@ApiImplicitParam(name = "keyword", value = "검색 키워드", required = true, dataType = "string", paramType = "query", defaultValue = "")
	})
	@RequestMapping(value = "/list", method= RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<AddressResultResponse> listByKeyword(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
															   @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize,
															   @RequestParam String keyword){
		return addressService.listByKeyword(page,pageSize, keyword);

	}


	@ApiOperation(value = "행정구역 시/도 조회 ")
	@RequestMapping(value = "/sido/list", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Object listAllSido() {
		return addressService.listAllSido();
	}

	@ApiOperation(value = "행정구역 구/ 조회 ")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sido", value = "시/도", required = true, dataType = "string", paramType = "query")
	})
	@RequestMapping(value = "/gugun/list", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<String> listAllGuGun(@RequestParam(value = "sido") String sido) {
		return addressService.listAllGuGunBySido(sido);
	}

	@ApiOperation(value = "행정구역 동 조회 ")
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

	@ApiOperation(value = "행정구역 조회 by 시/도, 구/군, 동 ")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sido",  value = "시/도", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "gugun", value = "구/군", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "dong",  value = "동",    required = true, dataType = "string", paramType = "query")
	})
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public AddressDTO getAddress(@RequestParam(value = "sido") String sido,
								 @RequestParam(value= "gugun") String gugun,
								 @RequestParam(value= "dong") String dong) {
		return addressService.getAddress(sido, gugun, dong);
	}


}
