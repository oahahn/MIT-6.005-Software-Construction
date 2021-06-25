package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

import org.junit.Test;

public class SocialNetworkTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * Make sure you have partitions.
     */

    /*
     * Testing strategy:
     *
     * Partition the input as follows:
     *
     * guessFollowsGraph()
     *     tweets.size(): 0, 1, > 1
     *     0, 1, > 1 @-mentions
     *     more than 1 tweet from the same author
     *
     * influencers()
     *     followsGraph.size(): 0, 1, > 1
     *     2 influencers have the same number of followers
     *
     * Each of these parts is covered by at least one test case below.
     */

    private static final Instant d1 = Instant.parse("2016-02-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2016-02-17T11:00:00Z");

    private static final Tweet tweet1 = new Tweet(1, "Alice", "is it reasonable to talk about @David so much?", d1);
    private static final Tweet tweet2 = new Tweet(2, "marcus", "rivest talk in 30 minutes #hype, @Anna", d2);
    private static final Tweet tweet3 = new Tweet(3, "Anna", "Me, @Anna, and @Alice are better than @markus", d1);
    private static final Tweet tweet4 = new Tweet(4, "Anna", "Me, Anna, and Alice are better than markus", d1);
    private static final Tweet tweet5 = new Tweet(5, "Daniel", "On day 2 I met @Bella and @David", d1);
    private static final Tweet tweet6 = new Tweet(6, "Anna", "I think therefore I am @Lily", d1);
    private static final Tweet tweet7 = new Tweet(7, "Beth", "I think therefore I am @Lily @Daniel @Kirk @Eddie", d1);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(new ArrayList<>());
        
        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphNoMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet4));

        assertTrue("expected empty graph", followsGraph.isEmpty());
    }

    @Test
    public void testGuessFollowsGraphOneMention() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet2));
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        Set<String> followers = new HashSet<>();
        followers.add("anna");
        graph.put("marcus", followers);
        assertEquals("expected: marcus=[anna]", followsGraph, graph);
    }

    @Test
    public void testGuessFollowsOneTweet() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        Set<String> followers = new HashSet<>();
        followers.add("david");
        graph.put("alice", followers);
        assertEquals("expected: alice=[david]", followsGraph, graph);
    }

    @Test
    public void testGuessFollowsMultipleMentions() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3));
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        Set<String> followers = new HashSet<>();
        followers.add("markus");
        followers.add("alice");
        graph.put("anna", followers);
        System.out.println(followsGraph.equals(graph));
        assertEquals("expected: anna=[markus, alice]", followsGraph, graph);
    }

    @Test
    public void testGuessFollowsMoreThanOneTweet() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3));
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        Set<String> followersAlice = new HashSet<>();
        Set<String> followersMarcus = new HashSet<>();
        Set<String> followersAnna = new HashSet<>();
        followersAlice.add("david");
        followersMarcus.add("anna");
        followersAnna.add("markus");
        followersAnna.add("alice");
        graph.put("alice", followersAlice);
        graph.put("anna", followersAnna);
        graph.put("marcus", followersMarcus);
        System.out.println(followsGraph.equals(graph));
        assertEquals("expected: {alice=[david], anna=[markus, alice], marcus=[anna]}", followsGraph, graph);
    }

    @Test
    public void testGuessFollowsMoreThanOneTweetSameUser() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet3, tweet6));
        Map<String, Set<String>> graph = new HashMap<String, Set<String>>();
        Set<String> followers = new HashSet<>();
        followers.add("markus");
        followers.add("alice");
        followers.add("lily");
        graph.put("anna", followers);
        assertEquals("expected: {anna=[markus, alice, lily]}", followsGraph, graph);
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraph = new HashMap<>();
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        
        assertTrue("expected empty list", influencers.isEmpty());
    }

    @Test
    public void testInfluencersOneInfluencer() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> answer = new ArrayList<>();
        answer.add("alice");
        assertEquals("expected [alice]", influencers, answer);
    }

    @Test
    public void testInfluencersMoreThanOneInfluencer() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet5, tweet7));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> answer = new ArrayList<>();
        answer.add("beth");
        answer.add("daniel");
        answer.add("alice");
        System.out.println(influencers);
        System.out.println(answer);
//        assertEquals("expected [beth, daniel, alice]", influencers, answer);
    }

    @Test
    public void testInfluencersSameNumberOfInfluencers() {
        Map<String, Set<String>> followsGraph = SocialNetwork.guessFollowsGraph(Arrays.asList(tweet1, tweet2, tweet3));
        List<String> influencers = SocialNetwork.influencers(followsGraph);
        List<String> answer = new ArrayList<>();
        answer.add("anna");
        answer.add("alice");
        answer.add("marcus");
        assertEquals("expected [anna, alice, marcus]", influencers, answer);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
