# Technical Design Document: Hashtag Tracker

## Overview
The Hashtag Tracker system is designed to track the usage of hashtags on a social media platform. Users can create posts containing hashtags, and the system keeps track of the count of each hashtag used. Real-time updates on the count of hashtags are provided as new posts are made.

## Architecture

![Untitled Diagram drawio](https://github.com/mrmnzz/design/assets/22643248/eb70fc75-be5c-4e37-97e9-bfb5a63e3951)

The system is built using the Spring Boot framework and follows the MVC (Model-View-Controller) architectural pattern. The main components of the system include:

### HashtagController
- **Description**: Handles incoming HTTP requests related to hashtags.
- **Responsibilities**: Interacts with the HashtagService to perform CRUD operations on hashtags.
- **Endpoints**:
    - `GET /rank/{hashtag}`: Retrieves the rank of a specific hashtag.
    - `GET /trending`: Retrieves the top trending hashtags.
    - `POST /`: Adds new hashtags to the system.

### HashtagService
- **Description**: Provides business logic for hashtags.
- **Responsibilities**: Interacts with the RedisRepository to store and retrieve hashtag data.
- **Methods**:
    - `getRank(String hashtag)`: Retrieves the rank of a specific hashtag.
    - `getTopNTrendingHashtag(int n)`: Retrieves the top N trending hashtags.
    - `addHashtag(List<HashtagDto> hashtags)`: Adds new hashtags to the system.

### RedisRepository
- **Description**: Implements the storage and retrieval of hashtag data using Redis.
- **Responsibilities**: Stores and retrieves hashtag data from the Redis database.

## Data Flow
1. User creates a post with hashtags.
2. HashtagController receives the post request and processes the hashtags.
3. HashtagService interacts with the RedisRepository to store the hashtag data.
4. Real-time updates on hashtag counts are provided to users.

## Technologies Used
- Spring Boot: Framework for building the application.
- Redis: SortedSet data structure for storing hashtag data.
- Maven: Dependency management tool.

## Approaches Considered

### Approach 1: Key-Value Pairs
In this approach, we store each hashtag as a key-value pair in Redis, with the key being the hashtag itself and the value being the count of the hashtag. This approach is simple and efficient for retrieving the count of a specific hashtag. However, it may not be efficient for retrieving the top N trending hashtags, as we would need to iterate over all the keys and retrieve their values.

### Approach 2: Sorted Set
In this approach, we store each hashtag as a member in a sorted set in Redis, with the score of each member being the count of the hashtag. This approach allows us to efficiently retrieve the top N trending hashtags based on their counts. Additionally, incrementing the count of a specific hashtag becomes straightforward. However, retrieving the count of a specific hashtag requires a lookup operation, which may have a performance impact for large datasets.

## Tradeoffs
- Approach 1: Simplified retrieval of count for a specific hashtag, but less efficient retrieval of top N trending hashtags.

## Assumptions and Limitations
- **Assumption 1**: Hashtags are unique within the system.
- **Assumption 2**: Hashtags are ASCII characters.
- **Limitation 1**: The system may not handle an extremely large number of hashtags due to Redis limitations.
- **Limitation 2**: Non-ASCII characters in hashtags may not be handled correctly.

## Future Enhancements
- Implement user authentication and authorization.
- Add analytics for hashtag usage trends.
- Enhance real-time updates with WebSocket technology.

This technical design document provides an overview of the architecture and components of the Hashtag Tracker system.