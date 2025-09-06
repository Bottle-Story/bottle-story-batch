package com.kyj.fmk.writer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.model.batch.BatchActiveMemDTO;
import com.kyj.fmk.model.batch.BatchExprMemDTO;
import com.kyj.fmk.model.kafka.KafkaActiveMemDTO;
import com.kyj.fmk.model.kafka.KafkaExprMemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * 2025-08-30
 * 웹소켓 보안을 위한 조치로 엑세스 토큰 만료시간에 맞추어
 * 배치로 강제로 웹소켓 세션을 종료하는 writer
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ActiveMemberWriter implements ItemWriter<BatchActiveMemDTO>  {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public void write(Chunk<? extends BatchActiveMemDTO> chunk) throws Exception {
        LocalDateTime startTime = LocalDateTime.now();

        log.info("만료회원 writer -> 카프카 전송 시작: {}", startTime);
        int cnt = 0;
        for (BatchActiveMemDTO event : chunk) {
            KafkaActiveMemDTO kafkaExprMemDTO = new KafkaActiveMemDTO(event);
            String data = objectMapper.writeValueAsString(kafkaExprMemDTO);
            // Kafka 전송
            kafkaTemplate.send(KafkaTopic.BATCH_WHEATHER_UPDATE,data);
            cnt++;
        }

        LocalDateTime endTime = LocalDateTime.now();
        log.info("회원 writer -> 카프카 전송 건수: {}", cnt);
        log.info("회원 writer -> 카프카 전송 종료: {}", endTime);
    }
}
