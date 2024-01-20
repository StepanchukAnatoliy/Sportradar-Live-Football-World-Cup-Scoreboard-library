package com.sportradar.service;

import com.sportradar.factory.MatchFactory;
import com.sportradar.model.Match;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unit tests for a Scoreboard class
 *
 * @author Anatolii Stepanchuk
 *
 * @see Scoreboard
 */
@ExtendWith(MockitoExtension.class)
public class ScoreboardTest {
    @Mock private MatchFactory matchFactory;
    @Mock private Match match;
    @InjectMocks private Scoreboard scoreboard;

    /**
     * Tests the constructor of the {@link Scoreboard} class with a valid instance of {@link MatchFactory}.
     * This test ensures that the Scoreboard object is successfully created without throwing any exceptions
     * when provided with a valid MatchFactory. It verifies the correct behavior of the Scoreboard
     * constructor in handling a valid dependency.
     */
    @Test
    void testConstructorWithValidMatchFactory() {
        assertDoesNotThrow(() -> new Scoreboard(matchFactory));
    }

    /**
     * Tests the constructor of the {@link Scoreboard} class with a null {@link MatchFactory} instance.
     * This test ensures that the Scoreboard constructor throws an {@link IllegalArgumentException}
     * when a null MatchFactory is passed as an argument. The aim is to verify the constructor's
     * validation mechanism for its parameters, particularly to confirm that it correctly handles
     * invalid or null inputs.
     */
    @Test
    void testConstructorWithNullMatchFactory() {
        assertThrows(IllegalArgumentException.class, () -> new Scoreboard(null));
    }

    /**
     * Tests the {@code startMatch} method of the {@link Scoreboard} class with valid input parameters.
     * This test checks if the method correctly interacts with the {@link MatchFactory} to create a match
     * when provided with valid team names and a score. It ensures that the method calls the
     * {@code createMatch} method of the MatchFactory with the correct arguments.
     */
    @Test
    public void testStartNewMatchValidInput() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
    }

    /**
     * Tests the {@code startMatch} method of the {@link Scoreboard} class when the home team name is null.
     * This test verifies that the method throws an {@link IllegalArgumentException} when provided with a null
     * value for the home team parameter, while the away team name and score are valid. The purpose is to
     * ensure that the {@code startMatch} method correctly validates its input parameters and handles
     * invalid or null inputs appropriately.
     */
    @Test
    public void testStartNewMatchNullHomeTeam() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch(null, "AwayTeam", 0);
        });
    }

    /**
     * Tests the {@code startMatch} method of the {@link Scoreboard} class when the away team name is null.
     * This test aims to verify that an {@link IllegalArgumentException} is thrown when the away team parameter
     * is null, while the home team name and score are valid. It checks the method's ability to validate
     * input parameters and to handle invalid or null inputs correctly.
     */
    @Test
    public void testStartNewMatchNullAwayTeam() {
        assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.startMatch("HomeTeam", null, 0);
        });
    }

    /**
     * Tests the {@code updateScore} method of the {@link Scoreboard} class with valid input parameters.
     * This test verifies that the method correctly updates the score for a match using valid team names
     * and score values. It ensures that {@code updateScore} calls the appropriate methods on the {@link Match}
     * instance with the expected arguments, thereby confirming the correct functionality of score updating.
     */
    @Test
    public void testUpdateMatchScoreValidInput() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);
        when(match.getHomeTeam()).thenReturn("HomeTeam");
        when(match.getAwayTeam()).thenReturn("AwayTeam");

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);
        scoreboard.updateScore("HomeTeam", "AwayTeam", 3, 2);

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match).getHomeTeam();
        verify(match).getAwayTeam();
        verify(match).updateScore(3, 2);
    }

    /**
     * Tests the {@code updateScore} method of the {@link Scoreboard} class with a negative score for the home team.
     * This test checks if the method throws an {@link IllegalArgumentException} when provided with a negative score
     * for the home team, ensuring that the method correctly validates score values and rejects invalid input.
     */
    @Test
    public void testUpdateMatchScoreNegativeHomeScore() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);

        assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("HomeTeam", "AwayTeam", -1, 0);
        });

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match, never()).getHomeTeam();
        verify(match, never()).getAwayTeam();
        verify(match, never()).updateScore(anyInt(), anyInt());
    }

    /**
     * Tests the {@code updateScore} method of the {@link Scoreboard} class with a negative score for the away team.
     * This test verifies that the method throws an {@link IllegalArgumentException} when a negative score
     * is provided for the away team, ensuring that score inputs are properly validated against negative values.
     */
    @Test
    public void testUpdateMatchScoreNegativeAwayScore() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);

        assertThrows(IllegalArgumentException.class, () -> {
            scoreboard.updateScore("HomeTeam", "AwayTeam", 0, -1);
        });

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match, never()).getHomeTeam();
        verify(match, never()).getAwayTeam();
        verify(match, never()).updateScore(anyInt(), anyInt());
    }

    /**
     * Tests the {@code updateScore} method of the {@link Scoreboard} class for a scenario where the match
     * is not found. This test ensures that a {@link NoSuchElementException} is thrown when attempting to
     * update the score for a match with team names that do not correspond to any existing match in the
     * scoreboard. It validates the method's ability to handle cases where the specified match is not present
     * in the system.
     */
    @Test
    public void testUpdateScoreMatchNotFound() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);
        when(match.getHomeTeam()).thenReturn("HomeTeam");

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);

        assertThrows(NoSuchElementException.class, () -> {
            scoreboard.updateScore("NonExistentTeam1", "NonExistentTeam2", 1, 1);
        });

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match).getHomeTeam();
        verify(match, never()).getAwayTeam();
    }

    /**
     * Tests the {@code finishMatch} method of the {@link Scoreboard} class. This method checks whether
     * finishing a match removes it from the scoreboard as expected. The test involves starting a match
     * and then finishing it, followed by verifying that the scoreboard's summary is empty, indicating
     * the match has been successfully concluded and removed.
     */
    @Test
    public void testFinishMatch() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);
        when(match.getHomeTeam()).thenReturn("HomeTeam");
        when(match.getAwayTeam()).thenReturn("AwayTeam");

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);
        scoreboard.finishMatch("HomeTeam", "AwayTeam");

        assertTrue(scoreboard.getSummary().isEmpty());

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match).getHomeTeam();
        verify(match).getAwayTeam();
    }

    /**
     * Tests the {@code finishMatch} method of the {@link Scoreboard} class for a scenario where the match
     * does not exist. This test verifies that a {@link NoSuchElementException} is thrown when attempting to
     * finish a match with team names that do not correspond to any existing match in the scoreboard. It checks
     * the method's ability to correctly handle attempts to conclude a non-existent match.
     */
    @Test
    public void testFinishMatchNotExisting() {
        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match);
        when(match.getHomeTeam()).thenReturn("HomeTeam");

        scoreboard.startMatch("HomeTeam", "AwayTeam", 0);

        assertThrows(NoSuchElementException.class, () -> {
            scoreboard.finishMatch("NonExistentTeam1", "NonExistentTeam2");
        });

        verify(matchFactory).createMatch("HomeTeam", "AwayTeam", 0);
        verify(match).getHomeTeam();
        verify(match, never()).getAwayTeam();
    }

    /**
     * Tests the {@code getSummary} method of the {@link Scoreboard} class to ensure it returns matches sorted
     * by score and start time. This test verifies that the method sorts the matches first by total score in
     * descending order, and then by start time in descending order for matches with the same score. Three mock
     * {@link Match} objects with different scores and start times are used to validate this sorting.
     */
    @Test
    void testGetSummaryShouldReturnMatchesSortedByScoreAndStartTime() {
        Match match1 = mock(Match.class);
        Match match2 = mock(Match.class);
        Match match3 = mock(Match.class);

        when(match1.getTotalScore()).thenReturn(10);
        when(match1.getStartTime()).thenReturn(100L);

        when(match2.getTotalScore()).thenReturn(15); // Highest score
        Mockito.lenient().when(match2.getStartTime()).thenReturn(90L);

        when(match3.getTotalScore()).thenReturn(10);
        when(match3.getStartTime()).thenReturn(110L); // Latest start time for same score

        when(matchFactory.createMatch(anyString(), anyString(), anyInt())).thenReturn(match1, match2, match3);

        scoreboard.startMatch("Team1", "Team2", 0);
        scoreboard.startMatch("Team3", "Team4", 1);
        scoreboard.startMatch("Team5", "Team6", 2);

        List<Match> summary = scoreboard.getSummary();

        assertEquals(3, summary.size());

        assertSame(match2, summary.get(0)); // match2 has the highest score
        assertSame(match3, summary.get(1)); // match3 and match1 have same score but match3 started later
        assertSame(match1, summary.get(2));
    }
}

