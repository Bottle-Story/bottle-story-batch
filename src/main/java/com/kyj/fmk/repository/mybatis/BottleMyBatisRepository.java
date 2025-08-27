package com.kyj.fmk.repository.mybatis;

import com.kyj.fmk.mapper.BottleMapper;
import com.kyj.fmk.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BottleMyBatisRepository {

    private final BottleMapper mapper;
}
