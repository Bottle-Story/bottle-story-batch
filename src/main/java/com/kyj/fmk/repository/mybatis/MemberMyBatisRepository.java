package com.kyj.fmk.repository.mybatis;

import com.kyj.fmk.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberMyBatisRepository {

    private final MemberMapper mapper;
}
