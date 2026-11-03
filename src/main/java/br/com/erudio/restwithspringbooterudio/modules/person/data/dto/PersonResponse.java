package br.com.erudio.restwithspringbooterudio.modules.person.data.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.erudio.restwithspringbooterudio.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;

import java.time.LocalDate;

//@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"})
@Builder
public record PersonResponse(
        Long id,

//        @JsonProperty("first_name")
        String firstName,

//        @JsonProperty("last_name")
        String lastName,

        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,

        String address,

//        @JsonIgnore
//        @JsonSerialize(using = GenderSerializer.class) // Don't work in records
        String gender
) {
    @Override
    @JsonSerialize(using = GenderSerializer.class)  // Don't work in records
    public String gender() {
        if (this.gender == null) return null;
        return ("Male".equalsIgnoreCase(this.gender) || "M".equalsIgnoreCase(this.gender)) ? "M" : "F";
    }
}
