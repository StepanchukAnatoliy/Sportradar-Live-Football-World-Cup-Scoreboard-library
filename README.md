# Sportradar Live Football World Cup Scoreboard library

## Overview
This project implements a live football World Cup scoreboard. The main components include `Match`, `MatchFactory`, and `Scoreboard` classes, which handle the creation and management of football matches and their scores.

## Components

### Match Class
- Represents a football match with details like team names, scores, and start time.
- Ensures team names are not null and start time is valid.

### MatchFactory Interface and Implementation
- Defines and implements the creation of Match instances.
- Allows flexibility and easier testing/mocking for match creation.

### Scoreboard Class
- Manages a collection of matches, offering functionalities to start, update, finish matches, and get a summary.
- Uses `MatchFactory` for creating new Match instances.

## Assumptions and Notes
- The `Match` constructor assumes that the start time of a match is adjusted based on a provided time shift to ensure unique start times.
- Exception handling is crucial, particularly for null inputs and negative scores.
- The sorting mechanism in `Scoreboard.getSummary()` prioritizes total score and then start time in descending order.
- Test cases for each class are designed to cover both normal operations and edge cases.

## Usage
To use the scoreboard:
1. Create matches with different team names and start times.
2. Update scores as the matches progress.
3. Get a summary of matches, which will be sorted by score and start time.
4. Finish matches as they conclude.

## Future Enhancements
- Extend the application to handle different types of sports.
- Implement a real-time update feature for match scores.