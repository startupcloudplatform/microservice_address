package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressCommonResponse<T> {

    @JsonProperty("totalCount")
    private String totalCount;

    @JsonProperty("errorMessage")
    private String message;

    @JsonProperty("errorCode")
    private String code;

    @JsonProperty("currentPage")
    private String currentPage;

    @JsonProperty("countPerPage")
    private String countPerPage;

}
