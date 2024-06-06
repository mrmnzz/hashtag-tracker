package com.prodigy.hashtag.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodigy.hashtag.dtos.HashtagDto;
import com.prodigy.hashtag.dtos.TrendingHashtagDto;
import com.prodigy.hashtag.repositories.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class HashtagService {

    private final String key = "hashtag";
    private final RedisRepository redisRepository;

    @Autowired
    public HashtagService(RedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }

    public ResponseEntity<String> addHashtag(List<HashtagDto> hashtagDtos) {
        try {
            for(HashtagDto hashtagDto : hashtagDtos) {
                redisRepository.incrementScore(key, hashtagDto.getHashtag(), 1);
            }
            return new ResponseEntity<>("Hashtags added sucessfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getRank(String hashtag) {
        try {
            Long rank = redisRepository.getWithRank(key, hashtag) + 1;
            return new ResponseEntity<>(rank.toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> getTopNTrendingHashtag(Integer n) {
        try {
            AtomicLong counter = new AtomicLong(1);
            Set<ZSetOperations.TypedTuple<String>> trendingHashtags = redisRepository.getTopNRanksWithScores(key, n);
            List<TrendingHashtagDto> trendingHashtagDtoList = trendingHashtags.stream().map(mapper -> new TrendingHashtagDto.TrendingHashtagDtoBuilder().score(mapper.getScore()).hashtag(mapper.getValue()).rank(counter.getAndIncrement()).build()).collect(Collectors.toList());
            ObjectMapper om = new ObjectMapper();

            String json = om.writeValueAsString(trendingHashtagDtoList);
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
