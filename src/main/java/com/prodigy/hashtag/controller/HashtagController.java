package com.prodigy.hashtag.controller;

import com.prodigy.hashtag.dtos.HashtagDto;
import com.prodigy.hashtag.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hashtag")
public class HashtagController {

    private final HashtagService hashtagService;

    @Autowired
    public HashtagController(HashtagService hashtagService) {
        this.hashtagService = hashtagService;
    }

    @GetMapping("/rank/{hashtag}")
    public ResponseEntity<String> getHashtagRank(@PathVariable("hashtag") String hashtag) {
        return hashtagService.getRank(hashtag);
    }

    @GetMapping("/trending")
    public ResponseEntity<String> getTrendingHashtag() {
        return hashtagService.getTopNTrendingHashtag(10);
//        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<String> addHashtags(@RequestBody List<HashtagDto> hashtags) {
        return hashtagService.addHashtag(hashtags);
    }
}
