package com.kyj.fmk.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyj.fmk.core.redis.RedisKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 2025-08-30
 * 날씨조회를 위한 배치
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ActiveMemberReader implements ItemReader<String> {

    private final RedisTemplate<String, Object> redisTemplate;
    private Iterator<Object> iterator;
    private final ObjectMapper objectMapper;

    @Override
    public String read() throws Exception {
        LocalDateTime startTime = LocalDateTime.now();

        if (iterator == null) {
            Set<Object> active = redisTemplate.opsForZSet()
                    .rangeByScore(RedisKey.WS_SESSION_Z_SET_KEY, System.currentTimeMillis(), Double.MAX_VALUE);

            if (active.isEmpty()) {
                log.info("조회된 회원이 없습니다. {}", startTime);
            } else {
                log.info("조회된 회원 수: {}", active.size());
                log.info("조회된 회원 데이터: {}", active);
            }
            iterator = active.iterator();
        }

        while (iterator.hasNext()) {
            String memberId = (String) iterator.next();

            // Redis GEO에서 회원 좌표 조회
            List<Point> positions = redisTemplate.opsForGeo()
                    .position(RedisKey.GEO_MEMBER, memberId);

            if (positions != null && !positions.isEmpty() && positions.get(0) != null) {
                Point point = positions.get(0);

                Map<String, Object> jsonMap = new HashMap<>();
                jsonMap.put("usrSeqId", memberId);
                jsonMap.put("lat", point.getY()); // 위도
                jsonMap.put("lot", point.getX()); // 경도

                String json = objectMapper.writeValueAsString(jsonMap);

                log.info("읽은 회원 JSON: {}", json);
                return json;
            } else {
                log.info("회원 {} 의 위치 정보 없음 → 스킵", memberId);
            }
        }

        LocalDateTime endTime = LocalDateTime.now();
        log.info("모든 회원 조회 완료: {}", endTime);
        return null; // 더 이상 없음
    }
}