## Problem Set 2 - 

The aim of this problem set was to build a toolbox of methods that can extract information from a set of tweets downloaded from Twitter. Below is a description of the Java classes I implemented for this problem set. 

* ExtractTest.java:
	* We were given the specifications of two functions written in Extract.java and our task was to write JUnit tests for these methods according to the specs and then implement the functions. My testing strategy is outlined at the top of the class. 
* Extract.java:
	* This is where we implemented the functions after we had written the JUnits tests in ExtractTest.java. These methods extract information from a list of tweets like the timespan between the earliest and latest tweets as well as creating a list of users mentioned in the tweets. 
* FilterTest.java:
	* Similar to the task with ExtractTest.java, we were given the specifications of three functions written in Filter.java and our task was to write JUnit tests for these methods according to the specs and then implement the functions. My testing strategy is outlined at the top of the class. 
* Filter.java:
	* This is where we implemented the functions after we had written the JUnits tests in FilterTest.java. These methods find tweets that:
		* were written by a particular user
		* were sent during a particular timespan and
		* contain certain words.
* SocialNetwork.java:
	* This class represents a social network using a Map\<String, Set<String>>, where Map[A] is the set of people that person A follows on Twitter. I implemented two methods:
		* guessFollowsGraph() builds a social network from a list of tweets by analysing tags
		* influencers() takes a social network graph and returns a list of users in order of their influence
* SocialNetworkTest.java:
	* Here is where I wrote tests for the methods in SocialNetwork.java partitioning the input space into logical boundary cases based in the spec which was written by the course staff. My testing strategy is outlined at the top of the class. 