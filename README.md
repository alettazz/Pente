## Pente
**Game basics**
- turn-based strategy board game created 
- played on the same board as Go 19x19 grid.

Players take turns placing black and white marbles on the open intersections of the grid, and always intersection not inside the square. 

**Winning rules**
Players can win in two ways, either by connecting five or more stones in a row, or by capturing five or more pairs of an opponent’s stones. 
The connected stones can be aligned vertically, horizontally or diagonally. Similarly, a player makes a capture by surrounding a pair of an opponent’s stones vertically, horizontally or diagonally. The captured stones are removed from the board and the exposed intersections are considered free again. The first player begins the game by placing a stone on the center intersection of the board. Further either player can place stones on any open intersection on the board. 

Pente itself is not a difficult game, and a relatively shallow depth of 4 . 
**AplhaBeta search**
As Pente has a large branching factor, due to the 361 positions that may be played AlphaBeta player is relativelz the best optimization technique that can be used. In determining the depth of AlphaBeta's search should ideally be at least 10, so that a player is able to see a terminal result at the bottom of the search tree, and is not forced to rely on an evaluation function for all nodes.


## The repository includes:
* Pente Implementation
* Alpha/Beta Player

Pente's implementation is played under the standard Pente rules in the project. Pente is evaluated using 8 features:

1.Number of Black pcs

2.Number of White pcs

3.Number of Black doubles

4.Number of White doubles

5.Number of Black triples

6.Number of White triples

7.Number of Black 4fours

8.Number of White fours


These 8 features are multiplied by the weight vector to generate the value of the game: {0.1, -0.1, 0.2, -0.2, 0.3, -0.3, 0.4, -0.4}. The Black player plays with the goal of maximizing the value of the game, and the White player tries to minimize the value of the game. Basic concept for nullpoint game.

## Example
**Depth of 3 AlphaBeta pruning used**

**Turn 1:**

**Best move is:**
BoardCoords: (4, 0) 
GameCoords: (5, 1) 
**Return move:**
BoardCoords: (4, 0) 
GameCoords: (5, 1)

0  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

1  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

2  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

3  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

4  [B] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

5  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

6  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

7  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

8  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [W] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

9  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

10 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

11 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

12 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

13 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

14 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

15 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

16 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

17 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

18 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]

Turns: 2 

Turn: WHITE 

Over: false 

Winner: null 

Feature 0: 1.0 

Feature 1: 1.0 

Feature 2: 0.0 

Feature 3: 0.0 

Feature 4: 0.0 

Feature 5: 0.0 

Feature 6: 0.0 

Feature 7: 0.0 

Capture: false  Move: BoardCoords: (4, 0) GameCoords: (5, 1)

**Turn 2**

0  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

1  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]

2  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

3  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

4  [B] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

5  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

6  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

7  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

8  [W] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [W] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

9  [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

10 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

11 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

12 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

13 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

14 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

15 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

16 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

17 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] 

18 [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ] [ ]



Turns: 3 

Turn: BLACK 

Over: false 

Winner: null 

Feature 0: 1.0 

Feature 1: 2.0 

Feature 2: 0.0 

Feature 3: 0.0 

Feature 4: 0.0 

Feature 5: 0.0 

Feature 6: 0.0 

Feature 7: 0.0 

Capture: false Move: BoardCoords: (8, 0) GameCoords: (9, 1)

