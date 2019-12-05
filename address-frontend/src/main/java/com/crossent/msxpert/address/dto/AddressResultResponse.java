package com.crossent.msxpert.address.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResultResponse {

    @JsonProperty("common")
    private AddressCommonResponse common;

    @JsonProperty("juso")
    private List<AddressDetailResponse> detail;


}
