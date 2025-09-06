package com.kyj.fmk.processor;


import com.kyj.fmk.model.batch.BatchExprMemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
/**
 * 2025-08-30
 * 웹소켓 보안을 위한 조치로 엑세스 토큰 만료시간에 맞추어
 * 배치로 강제로 웹소켓 세션을 종료하는 프로세서
 */
@Component
@Slf4j
public class ExpiredMemberProcessor implements ItemProcessor<String, BatchExprMemDTO> {

    @Override
    public BatchExprMemDTO process(String usrSeqId) {
        LocalDateTime startTime = LocalDateTime.now();

        log.info("만료회원 PROCESS -> DTO변환 시작: {}", startTime);

        BatchExprMemDTO batchExprMemDTO = new BatchExprMemDTO();
        batchExprMemDTO.setUsrSeqId(usrSeqId);
        LocalDateTime endTime = LocalDateTime.now();

        log.info("만료회원 PROCESS -> DTO변환 종료: {}", endTime);

        return batchExprMemDTO;
    }
}

