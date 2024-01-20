package com.sportradar.factory;

import com.sportradar.model.Match;

/**
 * The MatchFactory interface defines a method for creating instances of the Match class.
 * This interface allows for abstraction in the creation of Match objects, facilitating
 * flexibility in how matches are instantiated and enabling easier testing and mocking.
 * Implementations of this interface can customize the creation process, potentially
 * incorporating additional logic or dependencies as required.
 *
 * @author Anatolii Stepanchuk
 *
 * @see com.sportradar.model.Match
 */
public interface MatchFactory {
    /**
     * Creates a new instance of the Match class.
     * This method is responsible for generating a Match object with the provided team names and
     * a start time shift. The start time shift is used to adjust the start time of the match,
     * which is important for ensuring unique start times for matches and avoiding conflicts.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeTeam       The name of the home team participating in the match.
     * @param awayTeam       The name of the away team participating in the match.
     * @param startTimeShift The time shift in milliseconds to be applied to the match's start time.
     *                       This is used to ensure a unique timestamp for the start time of each match.
     * @return               A new instance of the Match class, initialized with the given parameters.
     */
    Match createMatch(String homeTeam, String awayTeam, int startTimeShift);
}
