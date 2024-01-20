package com.sportradar.factory;

import com.sportradar.model.Match;

/**
 * This class is an implementation of the MatchFactory interface, providing the concrete logic
 * for creating Match objects. MatchFactoryImpl is responsible for instantiating Match objects
 * with specified parameters.
 * This implementation can be used throughout the application wherever Match objects need to be created,
 * allowing for a consistent approach to Match instantiation.
 *
 * @author Anatolii Stepanchuk
 *
 * @see MatchFactory
 * @see com.sportradar.model.Match
 */
public class MatchFactoryImpl implements MatchFactory{
    /**
     * Creates and returns a new Match object initialized with the specified home team, away team,
     * and start time shift.
     * The startTimeShift parameter is used to adjust the match's start time, ensuring a unique
     * timestamp for each match and avoiding potential conflicts in match scheduling.
     *
     * @param homeTeam       The name of the home team for the match.
     * @param awayTeam       The name of the away team for the match.
     * @param startTimeShift The time shift in milliseconds to adjust the match's start time.
     * @return A new Match object initialized with the given home team, away team, and adjusted start time.
     */
    @Override
    public Match createMatch(String homeTeam, String awayTeam, int startTimeShift) {
        return new Match(homeTeam, awayTeam, startTimeShift);
    }
}
