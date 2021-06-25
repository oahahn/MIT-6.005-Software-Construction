package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class ExtractTest {

    /*
     * Testing strategy:
     *
     * Partition the input as follows:
     *
     * getTimeSpan()
     *     tweets.size(): 0, 1, > 1
     *     same timestamps, different timestamps
     *
     * getMentionedUsers()
     *     The list of tweets includes a text which:
     *     - has a username-mention immediately preceded by a valid character
     *     - has no username-mention
     *     - has one username-mention
     *     - has multiple username-mentions
     *     - includes "@@"
     *
     * Each of these parts is covered by at least one test case below.
     */
    
    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "alyssa", "is it reasonable to talk about rivest so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "bbitdiddle", "rivest talk in 30 minutes #hype", d2);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetTimespanTwoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1, tweet2));
        
        assertEquals("expected start", d1, timespan.getStart());
        assertEquals("expected end", d2, timespan.getEnd());
    }
    
    @Test
    public void testGetMentionedUsersNoMention() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1));
        
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetTimeSpanNoTweets() {
        Timespan timespan = Extract.getTimespan(Arrays.asList());
        assertEquals(timespan.getStart(), timespan.getEnd());
    }

    @Test
    public void testGetTimeSpanOneTweet() {
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet1));
        assertEquals("expected start", timespan.getStart(), tweet1.getTimestamp());
        assertEquals("expected end", timespan.getEnd(), tweet1.getTimestamp());
    }

    @Test
    public void testGetTimeSpanMoreThanOneTweet() {
        Instant d3 = Instant.parse("2016-02-17T18:00:00Z");
        Instant d4 = Instant.parse("2016-02-17T15:00:00Z");
        Tweet tweet3 = new Tweet(3, "123", "a different text bitdiddle@mit.edu", d3);
        Tweet tweet4 = new Tweet(4, "ma-_ya", "even shorter @@", d4);
        Tweet tweet5 = new Tweet(5, "dlkalkdkf", "ra @abc forty", d2);
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);
        Timespan timespan = Extract.getTimespan(tweets);
        assertEquals("expected start", timespan.getStart(), tweet1.getTimestamp());
        assertEquals("expected end", timespan.getEnd(), tweet3.getTimestamp());
    }

    @Test
    public void testGetTimeSpanSameTimestamp() {
        Tweet tweet5 = new Tweet(5, "dlkalkdkf", "ra @abc forty", d2);
        Timespan timespan = Extract.getTimespan(Arrays.asList(tweet2, tweet5));
        assertEquals("expected start", timespan.getStart(), tweet2.getTimestamp());
        assertEquals("expected start", timespan.getStart(), tweet5.getTimestamp());
        assertEquals("expected end", timespan.getEnd(), tweet2.getTimestamp());
        assertEquals("expected end", timespan.getEnd(), tweet5.getTimestamp());
    }

    @Test
    public void testGetTimeSpanDifferentTimestamp() {
        Instant d3 = Instant.parse("2016-02-17T18:00:00Z");
        Instant d4 = Instant.parse("2016-02-17T15:00:00Z");
        Tweet tweet3 = new Tweet(3, "123", "a different text bitdiddle@mit.edu", d3);
        Tweet tweet4 = new Tweet(4, "ma-_ya", "even shorter @@", d4);
        Tweet tweet5 = new Tweet(5, "dlkalkdkf", "ra @abc forty", d2);
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4, tweet5);

        Timespan timespan = Extract.getTimespan(tweets);
        assertEquals("expected start", timespan.getStart(), tweet1.getTimestamp());
        assertEquals("expected end", timespan.getEnd(), tweet3.getTimestamp());
    }

    @Test
    public void testGetMentionedUsersNoMentions() {
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    @Test
    public void testGetMentionedUsersOneMention() {
        Tweet tweet3 = new Tweet(3, "dlkalkdkf", "ra @abc forty", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));
        Set<String> goal = new HashSet<>();
        goal.add("abc");
        assertTrue("expected: {abc}", mentionedUsers.equals(goal));
    }

    @Test
    public void testGetMentionedUsersMultipleMentions() {
        Tweet tweet3 = new Tweet(3, "dlkalkdkf", "ra @abc @silver forty", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));
        Set<String> goal = new HashSet<>();
        goal.add("abc");
        goal.add("silver");
        assertEquals("expected: {abc, silver}", mentionedUsers, goal);
    }

    @Test
    public void testGetMentionedUsersDoubleAtSign() {
        Tweet tweet3 = new Tweet(3, "dlkalkdkf", "ra @@abc forty", d2);
        Set<String> mentionedUsers = Extract.getMentionedUsers(Arrays.asList(tweet1, tweet2, tweet3));
        assertTrue("expected empty set", mentionedUsers.isEmpty());
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
