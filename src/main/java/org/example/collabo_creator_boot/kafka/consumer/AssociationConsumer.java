package org.example.collabo_creator_boot.kafka.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.example.collabo_creator_boot.creator.domain.CreatorEntity;
import org.example.collabo_creator_boot.creator.repository.CreatorRepository;
import org.example.collabo_creator_boot.kafka.util.KafkaJsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AssociationConsumer {
    private final KafkaJsonUtil kafkaJsonUtil;
    private final CreatorRepository creatorRepository;

    @KafkaListener(topics = "registry-creator")
    public void consumeMessage(ConsumerRecord<String, String> record) {
        log.info("CONSUME TEST====================================================");
        log.info("Consumed message: " + record);

        try {
            // 메시지 값을 JSON으로 받음
            String jsonMessage = record.value();
            log.info(jsonMessage);

            CreatorEntity creatorEntity = kafkaJsonUtil.jsonToDTO(jsonMessage, CreatorEntity.class);
            creatorRepository.save(creatorEntity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
