import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by @alettazz.
 * Date: 4/18/2019
 */

public class Pente implements Game {

	enum player {
		BLACK, WHITE;
	}

	private enum increment {
		INCREMENT, DECREMENT, SAME;
	}

	PenteBoard board;
	Pente.player turn;
	Pente.player winner;
	GameManager manager;
	int turns;
	private double[] gameWeights;
	protected double[] gameValue;

	private int[][] capturedCoord;

	private boolean moveIsCapture;
	LinkedList<undoMoveObject> undoData;
	private boolean over;
	private boolean debug;
	private boolean[][] available;

	public static void main(String[] args) throws Exception {
		Pente pente = new Pente();
		pente.move(new Move(1, 1)); // Black marble move
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(1, 10));// White
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(2, 2)); // B
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(1, 15));// W
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(3, 3)); // B
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(1, 19));// W
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(4, 4)); // B
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(1, 12));// W
		pente.diagnose();
		System.out.println(pente);
		pente.move(new Move(5, 5)); // B
		pente.diagnose();


		System.out.println("Value: " + pente.getGameValue());

	}
	
	public void diagnose(){
		System.out.println(board);
		System.out.println("Turns: "+ turns);
		System.out.println("Turn: "+turn);
		System.out.println("Over: "+ over);
		System.out.println("Winner: "+winner);
		for (int i = 0; i<gameValue.length;i++){
			System.out.println("Feature "+i+": "+gameValue[i]);
		}
		System.out.println("Capture: "+moveIsCapture);
		
		undoMoveObject undo = undoData.peekFirst();
		System.out.println("Move: "+undo.getMove());
		for (int i = 0; i<undo.getCapturedCoord().size();i++){
			System.out.println("Captured: ("+undo.getCapturedCoord().get(i)[0]+","+undo.getCapturedCoord().get(i)[1]+")");
		}
		System.out.println();
		
	}
	
	public Pente() {
		debug = false; //for diagnosig
		board = new PenteBoard();
		board.board[8][8].setStatus(Space.status.WHITE);
		turn = player.BLACK; // arbitrarely decided black goes first

		this.turns = 1;
		moveIsCapture = false;
		undoData = new LinkedList<undoMoveObject>();
		gameValue = new double[]{0,1,0,0,0,0,0,0};


		initGameWeights();

		over = false;
		available = new boolean[19][19];
		for (int i = 0; i < 19; i++)
			for (int j = 0; j < 19; j++)
				available[i][j] = true;
		available[8][8] = false;
	}
	

	public double move(Move move) throws MoveException {
		if (over) {
			if (move.isUndoMove() == false) {
				throw new MoveException("Game Over");
			}
		}
		if (move == null){
			throw new Exception();
		}
		int row = move.getRow();
		int col = move.getCol();
		if (!move.isUndoMove()) {
			boolean empty = board.checkEmpty(row, col);
			if (empty) {

				board.move(turn, row, col);
				available[row][col] = false;


				turns++;

				updateGameState(move);

				double[] valDiff = undoData.peek().getValueDifference();
				int len = valDiff.length;
				for (int i = 0; i < len; i++) {
					gameValue[i] += valDiff[i];
				}

				available[move.getRow()][move.getCol()] = false;

				// change turns
				switch (turn) {
				case BLACK:
					turn = player.WHITE;
					break;
				case WHITE:
					turn = player.BLACK;
					break;
				}
			} else {
				System.out.println("Row: "+move.getRow()+"\tCol: "+move.getCol());
				throw new Exception("Move has been taken");
			}
		} else {
			undoMove();
		}

		if (debug)
			diagnose();
		
		if (over)
			return 100;
		else
			return 0;
	}

	private void initGameWeights() {

		gameWeights = new double[gameValue.length];
		gameWeights[0] = 0.1;
		gameWeights[1] = -0.1;
		gameWeights[2] = 0.2;
		gameWeights[3] = -0.2;
		gameWeights[4] = 0.3;
		gameWeights[5] = -0.3;
		gameWeights[6] = 0.4;
		gameWeights[7] = -0.4;

	}

	public double getGameValue() {
		double valueofGame = 0.0;
		if (!over) {
			for (int i = 0; i < gameValue.length; i++)
				valueofGame += (gameValue[i] * gameWeights[i]);
		} else {
			if (winner==Pente.player.BLACK){
				valueofGame = 100;
			} else {
				valueofGame = -100;
			}
		}
		return valueofGame;
	}

	public void undoMove() {
		undoMoveObject undo;
		try {
			undo = undoData.pop();
		} catch (Exception e) {
			e.printStackTrace();
			undo = null;
		}

		board.board[undo.getMove().getRow()][undo.getMove().getCol()].setStatus(Space.status.EMPTY);

		available[undo.getMove().getRow()][undo.getMove().getCol()] = true;


		if (turn == player.BLACK) {
			turn = player.WHITE;
		} else {
			turn = player.BLACK;
		}
		turns--;

		double[] valueDiff = undo.getValueDifference();
		int iMax = valueDiff.length;
		for (int i = 0; i < iMax; i++) {
			gameValue[i] -= valueDiff[i];
		}

		if (over) {
			over = false;
			winner = null;
		}
	}

	public String toString() {
		return board.toString();
	}


	private void updateGameState(Move move) {
		int direction = 0;

		undoMoveObject moveData = new undoMoveObject(move);

		while (direction < 8) {
			switch (direction) {
			case 0:
				moveData.concatonate(updateGameStateHelper(increment.DECREMENT,
						increment.SAME, move));
				break;
			case 1:
				moveData.concatonate(updateGameStateHelper(increment.DECREMENT,
						increment.INCREMENT, move));
				break;
			case 2:
				moveData.concatonate(updateGameStateHelper(increment.SAME,
						increment.INCREMENT, move));

				break;
			case 3:
				moveData.concatonate(updateGameStateHelper(increment.INCREMENT,
						increment.INCREMENT, move));
				break;
			case 4:
				moveData.concatonate(updateGameStateHelper(increment.INCREMENT,
						increment.SAME, move));

				break;
			case 5:
				moveData.concatonate(updateGameStateHelper(increment.INCREMENT,
						increment.DECREMENT, move));

				break;
			case 6:
				moveData.concatonate(updateGameStateHelper(increment.SAME,
						increment.DECREMENT, move));
				break;
			case 7:
				moveData.concatonate(updateGameStateHelper(increment.DECREMENT,
						increment.DECREMENT, move));

				break;
			default:
				System.out.println("Your direction is not correct.");
				break;
			}
			direction++;
		}

		if (turn == player.BLACK) {
			moveData.addValueDiff(0, 1);
		} else {
			moveData.addValueDiff(1, 1);
		}


		undoData.push(moveData);

	}

	private undoMoveObject updateGameStateHelper(Pente.increment x,
			Pente.increment y, Move move) {
		int row = move.getRow();
		int col = move.getCol();

		int currentRow = row;

		int currentCol = col;


		undoMoveObject undo = new undoMoveObject(move);
		double[] gameValueDiff = new double[] {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
		char[] area = { 'e', 'e', 'e', 'e', 'e' };


		for (int distance = 1; distance < 5; distance++) {
			switch (x) {
			case INCREMENT:
				currentRow = row + distance;
				break;
			case DECREMENT:
				currentRow = row - distance;
				break;
			case SAME:
				currentRow = row;
				break;
			}
			switch (y) {
			case INCREMENT:
				currentCol = col + distance;
				break;
			case DECREMENT:
				currentCol = col - distance;
				break;
			case SAME:
				currentCol = col;
				break;
			}

			if (currentRow < 0 || currentRow > 18 || currentCol < 0 || currentCol > 18) {
			} else {
				if (board.board[currentRow][currentCol].getStatus() == Space.status.BLACK) {
					area[distance] = 'b';
				} else if (board.board[currentRow][currentCol].getStatus() == Space.status.WHITE) {
					area[distance] = 'w';
				}
			}

		}

		char player = '\n';
		switch (board.board[row][col].getStatus()) {
		case BLACK:
			player = 'b';
			break;
		case WHITE:
			player = 'w';
			break;
		}
	
		
		area[0] = player;
		boolean two = (area[0] == player && area[1] == player);
		boolean three = (two && area[2] == player);
		boolean four = (three && area[3] == player);
		boolean five = (four && area[4] == player);
		if (five) {
			over = true;
			winner = turn;
		} else if (four) {
			if (area[4] == 'e') {

				if (area[0] == 'b') {
					gameValueDiff[6] += 1; // add a four
					gameValueDiff[4] -= 1; // subtsract a triple
				} else {
					gameValueDiff[7] += 1; // add a four
					gameValueDiff[5] -= 1; // substract a triple
				}
			} else {

				if (area[0] == 'b') {
					//gameValueDiff[6] -= 1;
				} else {
					//gameValueDiff[7] -= 1;
				}
			}
		} else if (three) {
			if (area[3] == 'e') {
				if (area[0] == 'b') {
					gameValueDiff[4] += 1;// add a triple
					gameValueDiff[2] -= 1; // substract a double
				} else {
					gameValueDiff[5] += 1; // add a triple
					gameValueDiff[3] -= 1; // substract a double
				}
			} else {
				if (area[0] == 'b') {
					//gameValueDiff[4] -= 1;
				} else {
					//gameValueDiff[5] -= 1;
				}
			}
		} else if (two) {
			if (area[2] == 'e') {
				// double
				if (area[0] == 'b') {
					gameValueDiff[2] += 1; // add a double
				} else {
					gameValueDiff[3] += 1; // add a double
				}
			} else {
				if (area[0] == 'b') {
					//gameValueDiff[2] -= 1; // subtract a double
				} else {
					//gameValueDiff[3] -= 1; // subtract a double
				}
			}
		}
		
		
		undo.setValueDifference(gameValueDiff);

		return undo;
	}

	public boolean isOver() {
		return over;
	}

	public char[][] getState() {

		char[][] state = new char[19][19];
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				switch (board.board[i][j].getStatus()) {
				case WHITE:
					state[i][j] = 'W';
					break;
				case BLACK:
					state[i][j] = 'B';
					break;
				case EMPTY:
					state[i][j] = 'E';
					break;
				}
			}
		}
		return state;
	}

	public void displayState() {
		for (int i = 0; i < available.length; i++) {
			System.out.println();
			for (int j = 0; j < available[0].length; j++) {
				if (available[i][j])
					System.out.print('True');
				else
					System.out.print('False');
			}
		}
	};

	public List<Move> getAvailableMoves() {

		List<Move> moves = new LinkedList<Move>();

		for (int i = 0; i < available.length; i++) {

			for (int j = 0; j < available[0].length; j++) {
				if (available[i][j]) {
					try {
						moves.add(new Move(i + 1, j + 1));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return moves;
	}

	public double[] getFeatures() {
		return gameValue;
	}
}
class PenteBoard {
	Space[][] board;

	public PenteBoard() {
		this.board = new Space[19][19];

		// init empty board
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++)
			{	board[i][j] = new Space();
		}
		}
	}

	public void move(Pente.player player, int row, int col) {

		switch (player) {
		case BLACK: {
			board[row][col].setStatus(Space.status.BLACK);
			break;
		}
		case WHITE: {
			board[row][col].setStatus(Space.status.WHITE);
			break;
		}
		}
	}

	public void clear(int row, int col) {

		board[row][col].currentStatus = Space.status.EMPTY;
	}

	public boolean checkEmpty(int row, int col) {
		boolean empty = false;

		switch (board[row][col].currentStatus) {
		case EMPTY:
			empty = true;
			break;
		default:
			break;
		}
		return empty;
	}

	public String toString() {
		String display = "";
		display += "-----------------------------------------------------------------------------------" + '\n';

		for (int row = 0; row < board.length; row++) {
			display += row + "\t";
			for (int col = 0; col < board.length; col++) {
				// for each column in a row
				display += "[";
				switch (board[row][col].currentStatus) {
				case BLACK:
					display += "B] ";
					break;
				case WHITE:
					display += "W] ";
					break;
				}
			}
			display += '\n';

		}
		display += "-----------------------------------------------------------------------------------" + '\n';
		return display;
	}

}

class Space {
	static enum status {
		EMPTY, WHITE, BLACK;
	}

	Space.status currentStatus;

	public Space() {
		this.currentStatus = status.EMPTY;
	}

	public Space.status getStatus() {
		return currentStatus;
	}

	public void setStatus(Space.status newStatus) {
		currentStatus = newStatus;
	}
}
