package com.github.guiziin227.restspringboot.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class GenderSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String formatedGender;
        if(value.equals("Male") || value.equals("Masculino")) {
            formatedGender = "M";
        } else {
            formatedGender = "F";
        }

        gen.writeString(formatedGender);
    }
}
