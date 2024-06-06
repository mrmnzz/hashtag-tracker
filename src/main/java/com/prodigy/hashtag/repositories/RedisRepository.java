package com.prodigy.hashtag.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Double incrementScore(String key, String value, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Long getWithRank(String key, String value) {
        return redisTemplate.opsForZSet().reverseRank(key, value);
    }

    public Set<String> getTopNRanks(String key, Integer n) {
        return redisTemplate.opsForZSet().reverseRange(key, 0, n);
    }

    public Set<ZSetOperations.TypedTuple<String>> getTopNRanksWithScores(String key, Integer n) {
        return redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, n);
    }
}
