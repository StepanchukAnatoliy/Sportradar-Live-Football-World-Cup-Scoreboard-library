package com.sportradar.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for a Match class
 *
 * @author Anatolii Stepanchuk
 *
 * @see Match
 */
class MatchTest {

    /**
     * Tests the constructor of the Match class and the getter methods.
     * Verifies if the constructor correctly initializes a match with given team names
     * and a start time shift, and if the getter methods return the correct values.
     */
    @Test
    void testConstructorAndGetters() {
        int startTimeShift = 1;
        Match match = new Match("HomeTeam", "AwayTeam", startTimeShift);

        assertEquals("HomeTeam", match.getHomeTeam());
        assertEquals("AwayTeam", match.getAwayTeam());
        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());

        assertTrue(match.getStartTime() > System.currentTimeMillis());
    }

    /**
     * Tests the constructor of the Match class with a null home team name.
     * Verifies that the constructor throws an IllegalArgumentException when a null value
     * is passed for the home team name, ensuring robust input validation.
     */
    @Test
    void testConstructorWithNullHomeTeamName() {
        assertThrows(IllegalArgumentException.class, () -> new Match(null, "AwayTeam", 0));
    }

    /**
     * Tests the constructor of the Match class with a null away team name.
     * Verifies that the constructor throws an IllegalArgumentException when a null value
     * is passed for the away team name, confirming proper handling of invalid inputs.
     */
    @Test
    void testConstructorWithNullAwayTeamName() {
        assertThrows(IllegalArgumentException.class, () -> new Match("HomeTeam", null, 0));
    }

    /**
     * Tests the constructor of the Match class with a negative start time shift.
     * Verifies that the constructor throws an IllegalArgumentException for negative start time shift values.
     */
    @Test
    void testConstructorWithNegativeStartTimeShift() {
        assertThrows(IllegalArgumentException.class, () -> new Match("HomeTeam", "AwayTeam", -1));
    }

    /**
     * Tests the updateScore method of the Match class with valid score inputs.
     * Verifies that the method correctly updates the scores for both home and away teams.
     */
    @Test
    void testUpdateScoreValidInput() {
        Match match = new Match("HomeTeam", "AwayTeam", 0);
        match.updateScore(3, 2);

        assertEquals(3, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    /**
     * Tests the updateScore method of the Match class with a negative score for the home team.
     * Verifies that the method throws an IllegalArgumentException for negative home team score.
     */
    @Test
    void testUpdateScoreNegativeHomeScore() {
        Match match = new Match("HomeTeam", "AwayTeam", 0);

        assertThrows(IllegalArgumentException.class, () -> match.updateScore(-1, 0));
    }

    /**
     * Tests the updateScore method of the Match class with a negative score for the away team.
     * Verifies that the method throws an IllegalArgumentException for negative away team score.
     */
    @Test
    void testUpdateScoreNegativeAwayScore() {
        Match match = new Match("HomeTeam", "AwayTeam", 0);

        assertThrows(IllegalArgumentException.class, () -> match.updateScore(0, -1));
    }

    /**
     * Tests the getTotalScore method of the Match class.
     * Verifies if the method correctly returns the total score of the match
     * after updating the scores of both teams.
     */
    @Test
    void testGetTotalScore() {
        Match match = new Match("HomeTeam", "AwayTeam", 0);
        match.updateScore(3, 2);

        assertEquals(5, match.getTotalScore());
    }
}

