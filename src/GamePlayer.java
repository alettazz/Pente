/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public interface GamePlayer {
	public Move move() throws Exception;
	public void setGameManager(GameManager mgmt);
	public void updateWeights();
}
