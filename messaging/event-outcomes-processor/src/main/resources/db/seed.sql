-- =========================================================
-- ODDS TABLE
-- =========================================================
INSERT INTO odds (event_id, sport_id, market_id, specifiers, outcome_id, producer_id, odds, status, version, last_update)
VALUES
-- Event 1001: Football 1X2
(1001, 1, 10, null, 1, 101, 1.85, 'ACTIVE', 1, NOW()),
(1001, 1, 10, null, 2, 101, 3.25, 'ACTIVE', 1, NOW()),
(1001, 1, 10, null, 3, 101, 4.10, 'ACTIVE', 1, NOW()),

-- Event 1002: Basketball Over/Under
(1002, 2, 20, 'over_200', 4, 102, 1.90, 'ACTIVE', 1, NOW()),
(1002, 2, 20, 'under_200', 5, 102, 1.95, 'ACTIVE', 1, NOW()),

-- Event 1003: Tennis Match Winner
(1003, 3, 30, 'player1_win', 6, 103, 1.70, 'ACTIVE', 1, NOW()),
(1003, 3, 30, 'player2_win', 7, 103, 2.10, 'ACTIVE', 1, NOW()),

-- Event 1004: Football with handicaps (specifiers)
(1004, 1, 11, 'home_win;handicap=-1', 8, 101, 2.00, 'ACTIVE', 1, NOW()),
(1004, 1, 11, 'away_win;handicap=+1', 9, 101, 1.90, 'ACTIVE', 1, NOW()),

-- Event 1005: Suspended Basketball
(1005, 2, 21, 'over_180', 10, 102, 1.80, 'SUSPENDED', 1, NOW()),
(1005, 2, 21, 'under_180', 11, 102, 2.00, 'SUSPENDED', 1, NOW()),

-- Event 1006: Football 1X2
(1006, 1, 12, null, 12, 101, 2.10, 'ACTIVE', 1, NOW()),
(1006, 1, 12, null, 13, 101, 3.00, 'ACTIVE', 1, NOW()),
(1006, 1, 12, null, 14, 101, 3.80, 'ACTIVE', 1, NOW()),

-- Event 1007: Tennis Match Winner
(1007, 3, 31, 'player1_win', 15, 103, 1.60, 'ACTIVE', 1, NOW()),
(1007, 3, 31, 'player2_win', 16, 103, 2.30, 'ACTIVE', 1, NOW()),

-- Event 1008: Football with specifier
(1008, 1, 13, 'home_win;handicap=-2', 17, 101, 2.50, 'ACTIVE', 1, NOW()),
(1008, 1, 13, 'away_win;handicap=+2', 18, 101, 1.70, 'ACTIVE', 1, NOW());



-- =========================================================
-- EVENT_OUTCOME TABLE
-- Only one row per event-market; select winning odd
-- =========================================================
INSERT INTO event_outcome
(event_id, sport_id, market_id, outcome_id, outcome_name, void_outcome_id, void_factor, specifier, status, created)
VALUES
-- Event 1001: Football -> Home Win wins
(1001, 1, 10, 1, 'Home Win', 0, null, null, 1, NOW()),

-- Event 1002: Basketball -> Over wins
(1002, 2, 20, 4, 'Over 200 Points', 0, null, 'over_200', 1, NOW()),

-- Event 1003: Tennis -> Player 1 wins
(1003, 3, 30, 6, 'Player 1 Wins', 0, null, 'player1_win', 1, NOW()),

-- Event 1004: Football Handicap -> Home Win with handicap wins
(1004, 1, 11, 8, 'Home Win', 0, null, null, 1, NOW()),

-- Event 1005: Basketball -> Over wins even though odds suspended
(1005, 2, 21, 10, 'Over 180 Points', 0 ,null, 'over_180', 1, NOW()),

-- Event 1006: Football -> Home Win wins
(1006, 1, 12, 12, 'Home Win', 0, null, null, 1, NOW()),

-- Event 1007: Tennis -> Player 1 wins
(1007, 3, 31, 15, 'Player 1 Wins', 0, null, 'player1_win', 1, NOW()),

-- Event 1008: Football Handicap -> Home Win wins
(1008, 1, 13, 17, 'Home Win', 0, null, null, 1, NOW());


-- =========================================================
-- BET TABLE
-- =========================================================
INSERT INTO bet (player_id, event_id, stake, potential_win, status, bet_type, created, total_odds)
VALUES
-- Bet 1: Player 501 bets on Football Event 1001 (Home Win)
(501, 1001, 50.00, 50.00 * 1.85, 0, 1, NOW(), 1.85),

-- Bet 2: Player 502 bets on Basketball Event 1002 (Over 200)
(502, 1002, 30.00, 30.00 * 1.90, 0, 1, NOW(), 1.90),

-- Bet 3: Player 503 bets on Tennis Event 1003 (Player 1 Wins)
(503, 1003, 20.00, 20.00 * 1.70, 0, 1, NOW(), 1.70),

-- Bet 4: Player 504 places a multi (Football Event 1004 + Tennis Event 1007)
(504, 1004, 10.00, 10.00 * 2.00 * 1.60, 0, 2, NOW(), 3.20);


-- =========================================================
-- BET_SLIP TABLE
-- =========================================================
-- Bet 1: Single on Football Event 1001
INSERT INTO bet_slip (user_id, bet_id, event_id, market_id, outcome_id, outcome_name, specifier, odds, status, created)
VALUES
(501, 1, 1001, '10', '1', 'Home Win', 'home_win', 1.85, 0, NOW());

-- Bet 2: Single on Basketball Event 1002
INSERT INTO bet_slip (user_id, bet_id, event_id, market_id, outcome_id, outcome_name, specifier, odds, status, created)
VALUES
(502, 2, 1002, '20', '4', 'Over 200 Points', 'over_200', 1.90, 0, NOW());

-- Bet 3: Single on Tennis Event 1003
INSERT INTO bet_slip (user_id, bet_id, event_id, market_id, outcome_id, outcome_name, specifier, odds, status, created)
VALUES
(503, 3, 1003, '30', '6', 'Player 1 Wins', 'player1_win', 1.70, 0, NOW());

-- Bet 4: Multi-bet with two selections
INSERT INTO bet_slip (user_id, bet_id, event_id, market_id, outcome_id, outcome_name, specifier, odds, status, created)
VALUES
-- Selection 1: Football Event 1004
(504, 4, 1004, '11', '8', 'Home Win', 'home_win;handicap=-1', 2.00, 0, NOW()),
-- Selection 2: Tennis Event 1007
(504, 4, 1007, '31', '15', 'Player 1 Wins', 'player1_win', 1.60, 0, NOW());