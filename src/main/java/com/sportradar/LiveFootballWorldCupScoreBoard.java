package com.sportradar;

import com.sportradar.factory.MatchFactoryImpl;
import com.sportradar.service.Scoreboard;

/**
 * Main class for running the live football World Cup scoreboard.
 *
 * @author Anatolii Stepanchuk
 * @version 1.0
 */
public class LiveFootballWorldCupScoreBoard {
    public static void main( String[] args ) {
        Scoreboard scoreboard = new Scoreboard(new MatchFactoryImpl());
        int startTimeShift = -1;

        // Starting matches
        scoreboard.startMatch("Mexico", "Canada", ++startTimeShift);
        scoreboard.startMatch("Spain", "Brazil", ++startTimeShift);
        scoreboard.startMatch("Germany", "France", ++startTimeShift);
        scoreboard.startMatch("Uruguay", "Italy", ++startTimeShift);
        scoreboard.startMatch("Argentina", "Australia", ++startTimeShift);

        // Updating scores
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        // Displaying scoreboard summary
        scoreboard.getSummary().forEach(System.out::println);
        System.out.println();

        // Finishing a match
        scoreboard.finishMatch("Uruguay", "Italy");

        // Displaying scoreboard summary again after finishing a match
        scoreboard.getSummary().forEach(System.out::println);
    }
}
