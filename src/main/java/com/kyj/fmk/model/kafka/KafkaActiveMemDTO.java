package com.kyj.fmk.model.kafka;

import com.kyj.fmk.core.model.KafkaTopic;
import com.kyj.fmk.core.model.dto.BaseKafkaDTO;
import com.kyj.fmk.model.batch.BatchActiveMemDTO;
import com.kyj.fmk.model.batch.BatchExprMemDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-30
 * @author 김용준
 * 배치 프로세스 후 카프카 전송 DTO
 */
@Getter
@Setter
public class KafkaActiveMemDTO extends BaseKafkaDTO {
    private String usrSeqId;
    private String lat;
    private String lot;

    public KafkaActiveMemDTO(BatchActiveMemDTO batchActiveMemDTO){
        this.usrSeqId = batchActiveMemDTO.getUsrSeqId();
        this.lat=batchActiveMemDTO.getLat();
        this.lot = batchActiveMemDTO.getLot();
        super.setFrom("BATCH");
        super.setTopic(KafkaTopic.BATCH_WHEATHER_UPDATE);
    }
}
