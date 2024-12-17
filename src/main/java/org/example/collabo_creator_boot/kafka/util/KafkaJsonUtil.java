package org.example.collabo_creator_boot.kafka.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class KafkaJsonUtil {

    private final ObjectMapper objectMapper;

    // JSON 을 DTO로 바꾸는 메서드
    public <E> E jsonToDTO(String json, Class<E> dtoClass) throws IOException {
        return objectMapper.readValue(json, dtoClass);
    }

    // DTO를 JSON으로 바꾸는 메서드
    public String dtoToJson(Object dto) throws IOException {
        return objectMapper.writeValueAsString(dto);
    }
}
