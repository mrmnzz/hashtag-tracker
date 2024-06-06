package com.prodigy.hashtag.dtos;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Data
public class TrendingHashtagDto {
    private Long rank;
    private String hashtag;
    private Double score;

    private TrendingHashtagDto() {
        // Private constructor for builder pattern
    }

    public static TrendingHashtagDtoBuilder builder() {
        return new TrendingHashtagDtoBuilder();
    }

    public static class TrendingHashtagDtoBuilder {
        private Long rank;
        private String hashtag;
        private Double score;

        public TrendingHashtagDtoBuilder rank(Long rank) {
            this.rank = rank;
            return this;
        }

        public TrendingHashtagDtoBuilder hashtag(String hashtag) {
            this.hashtag = hashtag;
            return this;
        }

        public TrendingHashtagDtoBuilder score(Double score) {
            this.score = score;
            return this;
        }

        public TrendingHashtagDto build() {
            TrendingHashtagDto dto = new TrendingHashtagDto();
            dto.rank = this.rank;
            dto.hashtag = this.hashtag;
            dto.score = this.score;
            return dto;
        }
    }
}
