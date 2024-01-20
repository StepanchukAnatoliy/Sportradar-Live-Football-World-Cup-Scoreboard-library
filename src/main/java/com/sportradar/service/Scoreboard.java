package com.sportradar.service;

import com.sportradar.factory.MatchFactory;
import com.sportradar.model.Match;

import java.util.*;

/**
 * Manages a scoreboard for matches, allowing operations such as starting, updating, and finishing matches.
 *
 * @author Anatolii Stepanchuk
 * @version 1.0
 */
public class Scoreboard {
    private final List<Match> matches = new ArrayList<>();
    private final MatchFactory matchFactory;

    /**
     * Constructs a new Scoreboard instance with a specified MatchFactory.
     * The MatchFactory is used for creating new Match instances when starting a match.
     * This design allows for easier testing and flexibility in Match object creation, as the factory can be
     * mocked or customized as needed.
     *
     * @author Anatolii Stepanchuk

     * @param matchFactory The factory to be used for creating Match instances. This factory is typically provided
     *                     by dependency injection, allowing for different implementations or mocked versions
     *                     to be used, especially in testing scenarios.
     * @throws IllegalArgumentException if match factory is null.
     */
    public Scoreboard(MatchFactory matchFactory) {
        if (matchFactory == null)
            throw new IllegalArgumentException("Match factory cannot be null");

        this.matchFactory = matchFactory;
    }

    /**
     * Starts a new match and adds it to the scoreboard.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeTeam       The name of the home team.
     * @param awayTeam       The name of the away team.
     * @param startTimeShift The time shift for the start time in milliseconds. This parameter
     *                       is necessary to prevent duplicate millisecond timestamps for
     *                       consecutively added matches. It enforces a minimum time difference
     *                       of at least 1 millisecond between timestamps.
     * @throws IllegalArgumentException if any team name is null.
     */
    public void startMatch(String homeTeam, String awayTeam, int startTimeShift) {
        if (homeTeam == null || awayTeam == null)
            throw new IllegalArgumentException("Team names cannot be null");

        matches.add(matchFactory.createMatch(homeTeam, awayTeam, startTimeShift));
    }

    /**
     * Updates the score for a specific match identified by home and away team names.
     * This method performs the following:
     * 1. Searches the match list using a stream.
     * 2. Filters for a match with matching home and away team names.
     * 3. If found, updates the match's score with the given home and away scores.
     * 4. If not found, throws a NoSuchElementException.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeTeam  The name of the home team in the match.
     * @param awayTeam  The name of the away team in the match.
     * @param homeScore The new score for the home team.
     * @param awayScore The new score for the away team.
     * @throws NoSuchElementException if the match is not found.
     * @throws IllegalArgumentException if any score is negative.
     */
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0)
            throw new IllegalArgumentException("Scores cannot be negative");

        matches.stream()
            .filter(m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam))
            .findAny()
            .orElseThrow(() -> new NoSuchElementException("Match not found"))
            .updateScore(homeScore, awayScore);
    }

    /**
     * Finishes a match by removing it from the scoreboard. This method identifies
     * the match based on the home and away team names. It performs the following:
     * 1. Checks if a match with the specified home and away team names exists.
     * 2. If found, removes the match, signifying its end.
     * 3. If not found, throws a NoSuchElementException indicating the match does
     *    not exist on the scoreboard.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeTeam The name of the home team in the match.
     * @param awayTeam The name of the away team in the match.
     * @throws NoSuchElementException if the match to finish is not found.
     */
    public void finishMatch(String homeTeam, String awayTeam) {
        boolean removed = matches.removeIf(
            m -> m.getHomeTeam().equals(homeTeam) && m.getAwayTeam().equals(awayTeam)
        );

        if (!removed) {
            throw new NoSuchElementException("Match to finish not found");
        }
    }

    /**
     * Retrieves a summary of all matches, sorted according to specific criteria. This method
     * first creates a new list from the existing collection of matches to avoid modifying the
     * original list. It then sorts this new list based on two criteria: the total score of each
     * match and the start time of each match. The sorting is done in two tiers:
     * 1. Primary sorting is based on the total score of the matches, in descending order. This
     *    means matches with higher total scores are placed higher in the list.
     * 2. For matches with the same total score, secondary sorting is applied based on the start
     *    time, also in descending order. This means among matches with equal scores, the ones
     *    that started later are placed higher.
     *
     * @author Anatolii Stepanchuk
     *
     * @return A list of matches sorted first by total score in descending order and then by
     *         start time in descending order.
     */
    public List<Match> getSummary() {
        List<Match> sortedMatches = new ArrayList<>(matches);

        sortedMatches.sort(Comparator
            .comparing(Match::getTotalScore, Comparator.reverseOrder())
            .thenComparing(Match::getStartTime, Comparator.reverseOrder()));

        return sortedMatches;
    }
}

