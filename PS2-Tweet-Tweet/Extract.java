package twitter;

import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        if (tweets.isEmpty()) {
            return new Timespan(Instant.now(), Instant.now());
        }
        // Initialise the first tweets timestamp as the timespan
        Instant firstTweetTime = tweets.get(0).getTimestamp();
        Instant earliestTweetTime = firstTweetTime;
        Instant latestTweetTime = firstTweetTime;
        // Look through all the tweets to find earlier or later timestamps
        for (Tweet tweet : tweets) {
            if (tweet.getTimestamp().isBefore(earliestTweetTime)) {
                earliestTweetTime = tweet.getTimestamp();
            }
            else if (tweet.getTimestamp().isAfter(latestTweetTime)) {
                latestTweetTime = tweet.getTimestamp();
            }
        }
        return new Timespan(earliestTweetTime, latestTweetTime);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentionedUsers = new HashSet<>();
        if (tweets.isEmpty()) {
            return mentionedUsers;
        }
        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            // Create an array of the words in the text
            String[] words = text.split("\\s");
            for (String word : words) {
                // If a word matches the valid user name criteria add it to the set
                if (Pattern.matches("@([A-Za-z0-9_-]+)", word)) {
                    word = word.substring(1).toLowerCase();
                    mentionedUsers.add(word);
                }
            }
        }
        return mentionedUsers;
    }
}
