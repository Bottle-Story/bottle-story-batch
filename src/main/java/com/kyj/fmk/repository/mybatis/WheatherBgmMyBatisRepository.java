package com.kyj.fmk.repository.mybatis;

import com.kyj.fmk.mapper.BottleMapper;
import com.kyj.fmk.mapper.WheatherBgmMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WheatherBgmMyBatisRepository {

    private final WheatherBgmMapper mapper;
}
