package br.com.erudio.restwithspringbooterudio.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String gender, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (gender == null) {
            gen.writeNull();
            return;
        }
        String formattedGender = ("Male".equalsIgnoreCase(gender) || "M".equalsIgnoreCase(gender)) ? "M" : "F";
        gen.writeString(formattedGender);
    }
}
