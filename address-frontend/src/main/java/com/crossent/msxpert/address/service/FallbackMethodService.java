package com.crossent.msxpert.address.service;

import com.crossent.msxpert.address.dto.AddressResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FallbackMethodService {

    private static final Logger logger = LoggerFactory.getLogger(FallbackMethodService.class);

    public ResponseEntity<AddressResultResponse> notFound(int page, int pageSize, String keyword) {
        logger.warn("================================== >Invoking fallback for notFound");
        return new ResponseEntity<AddressResultResponse>(HttpStatus.NOT_FOUND);
    }

    public Object  v1MotFound() {
        logger.warn("================================== >Invoking fallback for notFound");
        return new Object();
    }


}
