/**
 * Created by @alettazz.
 * Date: 4/18/2019
 */

public class Move {
	private int row;
	private int col;
	private boolean isUndoMove;
	public String undoOrPos;

	public Move(int passedRow, int passedCol) throws Exception {

		this.row = --passedRow;

		this.col = --passedCol;


		isUndoMove = false;
		if (this.row > 18 || this.row < 0 || this.col > 18 || this.col < 0) {
			throw new Exception();
		}
		undoOrPos = "pos";
	}

	public Move(String undo) {
		isUndoMove = true;
		undoOrPos = "undo";
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public boolean isUndoMove() {
		return isUndoMove;
	}

	public boolean equals(Move move) {
		boolean same = false;

		if (isUndoMove = move.isUndoMove()) {
			same = true;
		} else if (row == move.getRow() && col == move.getCol()) {
			same = true;
		}

		return same;
	}
	
	public String toString(){
		return "BoardCoords: ("+row+", "+col+")\tGameCoords: ("+(row+1)+", "+(col+1)+")";
	}
}
