import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by @alettazz.
 * Date: 4/19/2019
 */

public class GameManager {
	protected Game game;
	private ArrayList<GamePlayer> player;
	private final LinkedList<Move> moves;

	public GameManager(Game game, GamePlayer player) {
		this.game = game;
		this.player = new ArrayList<GamePlayer>();
		this.player.add(player);

		moves = new LinkedList<Move>();
		for (int i = 1; i < 20; i++) {
			for (int j = 1; j < 20; j++) {
				try {
					moves.add(new Move(i, j));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void addPlayer (GamePlayer gp){
		player.add(gp);
	}
	public boolean isValidMove(Move move) {
		boolean validity = false;

		return validity;
	}

	public void refreshGame(Game game) {
		this.game = game;
	}

	public static void main(String args[]) throws Exception {
		int numPlayers = 2;
		AlphaBetaPlayer rpMax = new AlphaBetaPlayer(true);
		AlphaBetaPlayer rpMin = new AplhaBetaPlayer(false);
		Pente pente = new Pente();
		GameManager mgmt = new GameManager(pente, rpMax);
		long start = new Date().getTime();
		for (int games = 0; games < 1; games++) {

			pente = new Pente();
			mgmt.refreshGame(pente);

			mgmt.player.add(rpMin);
			mgmt.player.get(0).setGameManager(mgmt);

			mgmt.player.get(1).setGameManager(mgmt);

			int lastPlayer = -1;
			int turn = 0;
			int playerTurn = -1;
			while (!mgmt.game.isOver() && turn < 360) {

				playerTurn = turn % numPlayers;
				Move make = mgmt.player.get(playerTurn).move();
				System.out.println("Move Returned: "+ make);


				try {
					((ReinforcementPlayer) mgmt.player.get(playerTurn)).receiveReward(mgmt.game.move(make));
					if (lastPlayer != -1){
						mgmt.player.get(lastPlayer).updateWeights();
					}
				}
				catch (Exception e){

					e.printStackTrace();
				}
				lastPlayer = playerTurn;
				turn++;
				

				
			}
			if (games%100 == 0){

				long gameTime = new Date().getTime();

				double[] one = ((ReinforcementPlayer) mgmt.player.get(0)).getWeights();
				double[] two = ((ReinforcementPlayer) mgmt.player.get(1)).getWeights();
				System.out.println(gameTime);
				System.out.println("One, "+one[0]+", "+one[1]+", "+one[2]+", "+one[3]+", "+one[4]+", "+one[5]+", "+one[6]+", "+one[7]);
				System.out.println("Two, "+two[0]+", "+two[1]+", "+two[2]+", "+two[3]+", "+two[4]+", "+two[5]+", "+two[6]+", "+two[7]);
			}
		}

		ReinforcementPlayer one = (ReinforcementPlayer) mgmt.player.get(0);
		ReinforcementPlayer two = (ReinforcementPlayer) mgmt.player.get(1);

		System.out.println("WEIGHTS");
		for (int i = 0; i < 8; i++) {
			System.out.println("\tMax: " + one.weights[i] + "\tMin: " + two.weights[i]);
			
		}

	}
}
