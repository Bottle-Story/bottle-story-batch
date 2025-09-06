package com.kyj.fmk.processor;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.exception.custom.KyjSysException;
import com.kyj.fmk.core.model.enm.CmErrCode;
import com.kyj.fmk.model.batch.BatchActiveMemDTO;
import com.kyj.fmk.model.batch.BatchExprMemDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 2025-08-30
 * 날씨를 주기적으로 업데이트하기 위한배치
 * 배치로 날씨를 업데이트 하기위해 카프카 이벤트 발행하는 프로세서
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ActiveMemberProcessor implements ItemProcessor<String, BatchActiveMemDTO> {

    private final ObjectMapper objectMapper;

    @Override
    public BatchActiveMemDTO process(String json) {
        LocalDateTime startTime = LocalDateTime.now();

        log.info("회원 PROCESS -> DTO변환 시작: {}", startTime);

        BatchActiveMemDTO batchMemDTO = null;
        try {
            batchMemDTO = objectMapper.readValue(json, BatchActiveMemDTO.class);
        } catch (JsonProcessingException e) {
            throw new KyjSysException(CmErrCode.CM016);
        }

        LocalDateTime endTime = LocalDateTime.now();

        log.info("회원 PROCESS -> DTO변환 종료: {}", endTime);

        return batchMemDTO;
    }
}

