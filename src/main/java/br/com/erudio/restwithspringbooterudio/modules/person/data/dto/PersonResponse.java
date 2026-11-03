package br.com.erudio.restwithspringbooterudio.modules.person.data.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Builder;

//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
@Builder
public record PersonResponse(
        Long id,

//        @JsonProperty("first_name")
        String firstName,

//        @JsonProperty("last_name")
        String lastName,

        String address,

//        @JsonIgnore
        String gender
) {
}
