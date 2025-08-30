package com.kyj.fmk.model.batch;

import lombok.Getter;
import lombok.Setter;

/**
 * 2025-08-30
 * @author 김용준
 * 배치 리더 수행 후 DTO변환
 */
@Getter
@Setter
public class BatchActiveMemDTO {
    private  String usrSeqId;
    private String lat;
    private String lot;

}
