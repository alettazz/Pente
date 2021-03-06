import java.util.List;

/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public interface Game{
	public double move(Move moveToMake) throws Exception;
	public void undoMove();
	public boolean isOver();
	public double getGameValue();
	public List<Move> getAvailableMoves();
	public char[][] getState();
	public double[] getFeatures();
}
