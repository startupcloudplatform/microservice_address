package com.crossent.microservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressResponse<T> {

    @JsonProperty("results")
    private T results;

    public T getResults() {
        return results;
    }

}
