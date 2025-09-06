package com.kyj.fmk.reader;

import com.kyj.fmk.core.redis.RedisKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;

/**
 * 2025-08-30
 * 웹소켓 보안을 위한 조치로 엑세스 토큰 만료시간에 맞추어
 * 배치로 강제로 웹소켓 세션을 종료하는 리더
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredMemberReader implements ItemReader<String> {

    private final RedisTemplate<String, Object> redisTemplate;
    private Iterator<Object> iterator;

    @Override
    public String read() {
        LocalDateTime startTime = LocalDateTime.now();
        log.info("만료회원 조회 시작: {}", startTime);
        log.info("타임={}",System.currentTimeMillis());

        if (iterator == null) {
            Set<Object> expired = redisTemplate.opsForZSet()
                    .rangeByScore(RedisKey.WS_SESSION_Z_SET_KEY, 0, System.currentTimeMillis());

            if (expired.isEmpty()) {
                log.info("조회된 만료회원이 없습니다.");
            } else {
                log.info("조회된 만료회원 수: {}", expired.size());
                log.info("조회된 만료회원 데이터: {}", expired);
            }

            iterator = expired.iterator();
        }

        String nextMember = iterator.hasNext() ? (String) iterator.next() : null;

        if (nextMember != null) {
            log.info("읽은 만료회원: {}", nextMember);
        } else {
            LocalDateTime endTime = LocalDateTime.now();
            log.info("모든 만료회원 조회 완료: {}", endTime);
        }

        return nextMember;
    }
}
