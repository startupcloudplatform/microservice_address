package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResultResponse {

    @JsonProperty("common")
    private AddressCommonResponse common;

    @JsonProperty("juso")
    private List<AddressDetailResponse> detail;

}
