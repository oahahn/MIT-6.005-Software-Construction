package twitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Filter consists of methods that filter a list of tweets for those matching a
 * condition.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Filter {

    /**
     * Find tweets written by a particular user.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param username
     *            Twitter username, required to be a valid Twitter username as
     *            defined by Tweet.getAuthor()'s spec.
     * @return all and only the tweets in the list whose author is username,
     *         in the same order as in the input list.
     */
    public static List<Tweet> writtenBy(List<Tweet> tweets, String username) {
        List<Tweet> writtenBy = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getAuthor().equalsIgnoreCase(username)) {
                writtenBy.add(tweet);
            }
        }
        return writtenBy;
    }

    /**
     * Find tweets that were sent during a particular timespan.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param timespan
     *            timespan
     * @return all and only the tweets in the list that were sent during the timespan,
     *         in the same order as in the input list.
     */
    public static List<Tweet> inTimespan(List<Tweet> tweets, Timespan timespan) {
        List<Tweet> inTimespan = new ArrayList<>();
        for (Tweet tweet : tweets) {
            // if (timespan start <= tweet timestamp <= timespan end)
            // add the tweet to the list
            if ((tweet.getTimestamp().isAfter(timespan.getStart()) || tweet.getTimestamp().equals(timespan.getStart())) &&
                 (tweet.getTimestamp().isBefore(timespan.getEnd()) || tweet.getTimestamp().equals(timespan.getEnd()))) {
                inTimespan.add(tweet);
            }
        }
        return inTimespan;
    }

    /**
     * Find tweets that contain certain words.
     * 
     * @param tweets
     *            a list of tweets with distinct ids, not modified by this method.
     * @param words
     *            a list of words to search for in the tweets. 
     *            A word is a nonempty sequence of nonspace characters.
     * @return all and only the tweets in the list such that the tweet text (when 
     *         represented as a sequence of nonempty words bounded by space characters 
     *         and the ends of the string) includes *at least one* of the words 
     *         found in the words list. Word comparison is not case-sensitive,
     *         so "Obama" is the same as "obama".  The returned tweets are in the
     *         same order as in the input list.
     */
    public static List<Tweet> containing(List<Tweet> tweets, List<String> words) {
        List<Tweet> tweetList = new ArrayList<>();
        for (Tweet tweet : tweets) {
            String[] textWords = tweet.getText().toLowerCase().split("\\s");
            for (String word : textWords) {
                if (words.contains(word)) {
                    tweetList.add(tweet);
                    break;
                }
            }
        }
        return tweetList;
    }
}