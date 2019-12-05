package com.crossent.microservice.address.service;

import com.crossent.microservice.address.domain.Address;
import com.crossent.microservice.address.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	/**
	 * 행정구역 전체 목록 조회
	 * @param pageable
	 */
	public Page<Address> listAddress(Pageable pageable){
		return addressRepository.findAll(pageable);
	}

	/**
	 * 행정구역 시/도 목록 조회
	 */
	public List<String> listAllSido(){
		return addressRepository.findAllSidoGroupBy();
	}

	/**
	 * 행정구역 구/군 목록 조회
	 * @param sido
	 */
	public List<String> listAllGuGunBySido(String sido){
		return addressRepository.findAllGuGunGroupBy(sido);
	}

	/**
	 * 행정구역 동 목록 조회
	 * @param sido
	 * @param gugun
	 */
	public List<String> listAllDongBySidoAndGuGun(String sido, String gugun){
		return addressRepository.findAllDongGroupBy(sido, gugun);
	}

	/**
	 * 행정구역 조회 by 시/도, 구/군, 동
	 * @param sido
	 * @param gugun
	 * @param dong
	 */
	public Address getAddress(String sido, String gugun, String dong){
		return addressRepository.findBySidoAndGugunAndDong(sido, gugun, dong);
	}

}
