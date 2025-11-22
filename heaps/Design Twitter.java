package heaps;

import java.util.*;

class Tweet implements Comparable<Tweet> {
  int tweetId;
  int time;

  public Tweet(int tweetId, int time) {
    this.tweetId = tweetId;
    this.time = time;
  }

  public int compareTo(Tweet that) {
    return that.time - this.time; // descending order based on time
  }
}

class User {
  int userId;
  Set<Integer> followees; 
  LinkedList<Tweet> tweets;

  User (int userId) {
    this.userId = userId;
    this.followees = new HashSet<>();
    this.tweets = new LinkedList<>();
  }
}

class Twitter {
 
  HashMap<Integer, User> userMap;
  int timeCounter;
  public Twitter() {
    this.userMap = new HashMap<>();
    this.timeCounter = 0;
  }

  public void postTweet(int userId, int tweetId) {
    if (!userMap.containsKey(userId)) {
      userMap.put(userId, new User(userId));
    }

    userMap.get(userId).tweets.addFirst(new Tweet(tweetId, timeCounter++));

  }

  public List<Integer> getNewsFeed(int userId) {
    if (!userMap.containsKey(userId)) {
      return new ArrayList<>(); // empty list
    }

    PriorityQueue<Tweet> pq = new PriorityQueue<>();

    User user = userMap.get(userId);
    int count = 0;

    /**
     * Since I used linkedList
     * user.tweets is in reverse order
     * means latest tweet is at the front
     * 
     * if I had used arraylist
     * user.tweets is in forward order
     * means latest tweet is at the end
     * 
     * to tackle that with arraylist
     * I had to use reverse iterator 
     */

    for (Tweet tweet : user.tweets) {
      pq.offer(tweet); // custom sorting logic is already implemented in Tweet class
      count++;
      if (count > 10) {
        break;
      }
    }

    
    
    for (int followeeId : user.followees) {
      count = 0; // reset count for each followee
      for (Tweet tweet : userMap.get(followeeId).tweets) {
        pq.offer(tweet);
        count++;
        if (count > 10) {
          break;
        }
      }
    }
    
    count = 0; // reset count
    List<Integer> result = new ArrayList<>();
    while (!pq.isEmpty() && count < 10) {
      result.add(pq.poll().tweetId);
      count++;
    }
    
    return result;
  }

  public void follow(int followerId, int followeeId) {
    if (!userMap.containsKey(followerId)) {
      userMap.put(followerId, new User(followerId));
    }
    
    if (!userMap.containsKey(followeeId)) {
      userMap.put(followeeId, new User(followeeId));
    }
    
    userMap.get(followerId).followees.add(followeeId);
  }

  public void unfollow(int followerId, int followeeId) {
    if (!userMap.containsKey(followerId)) {
      return;
    }
    
    userMap.get(followerId).followees.remove(followeeId);
  }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */