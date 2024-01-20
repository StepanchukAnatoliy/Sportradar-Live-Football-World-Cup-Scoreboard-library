package com.sportradar.model;

/**
 * Represents a match with its details such as team names, scores, and start time.
 *
 * @author Anatolii Stepanchuk
 * @version 1.0
 */
public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private final long startTime;

    /**
     * Constructs a Match instance.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeTeam       The name of the home team.
     * @param awayTeam       The name of the away team.
     * @param startTimeShift The time shift for the start time in milliseconds. This parameter
     *                       is necessary to prevent duplicate millisecond timestamps for
     *                       consecutively added matches. It enforces a minimum time difference
     *                       of at least 1 millisecond between timestamps.
     * @throws IllegalArgumentException if any team name is null or the start time shift is negative.
     */
    public Match(String homeTeam, String awayTeam, int startTimeShift) {
        if (homeTeam == null || awayTeam == null)
            throw new IllegalArgumentException("Team names cannot be null");

        if (startTimeShift < 0)
            throw new IllegalArgumentException("Start time shift cannot be negative");

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startTime = System.currentTimeMillis() + startTimeShift;
    }

    /**
     * Updates the score of the match.
     *
     * @author Anatolii Stepanchuk
     *
     * @param homeScore New score of the home team.
     * @param awayScore New score of the away team.
     * @throws IllegalArgumentException if any score is negative.
     */
    public void updateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0)
            throw new IllegalArgumentException("Scores cannot be negative");

        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public long getStartTime() {
        return startTime;
    }

    /**
     * Returns the total score of the match.
     *
     * @author Anatolii Stepanchuk
     *
     * @return Sum of the home and away team scores.
     */
    public int getTotalScore() {
        return homeScore + awayScore;
    }

    @Override
    public String toString() {
        return homeTeam + " " + homeScore + " - " + awayTeam + " " + awayScore;
    }
}

