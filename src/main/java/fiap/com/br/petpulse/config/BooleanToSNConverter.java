package fiap.com.br.petpulse.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanToSNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return Boolean.TRUE.equals(value) ? "S" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "S".equalsIgnoreCase(value);
    }
}